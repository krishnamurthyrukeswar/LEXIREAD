package com.lexiread.app.ui.reader

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lexiread.app.data.local.dao.BookmarkDao
import com.lexiread.app.data.local.dao.HighlightDao
import com.lexiread.app.data.local.dao.NoteDao
import com.lexiread.app.data.local.datastore.UserPreferences
import com.lexiread.app.data.local.entity.BookmarkEntity
import com.lexiread.app.data.local.entity.HighlightEntity
import com.lexiread.app.data.local.entity.NoteEntity
import com.lexiread.app.domain.model.Book
import com.lexiread.app.domain.model.ReadingProgress
import com.lexiread.app.domain.repository.BookRepository
import com.lexiread.app.domain.repository.ReadingProgressRepository
import com.lexiread.app.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReaderViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val bookRepository: BookRepository,
    private val progressRepository: ReadingProgressRepository,
    private val bookmarkDao: BookmarkDao,
    private val highlightDao: HighlightDao,
    private val noteDao: NoteDao,
    val userPreferences: UserPreferences
) : ViewModel() {

    val bookId: String = savedStateHandle.get<String>("bookId")
        ?: throw IllegalArgumentException("bookId is required")

    // ── Book ──
    private val _book = MutableStateFlow<UiState<Book>>(UiState.Loading)
    val book: StateFlow<UiState<Book>> = _book.asStateFlow()

    // ── Reading progress ──
    private val _currentPage = MutableStateFlow(0)
    val currentPage: StateFlow<Int> = _currentPage.asStateFlow()

    private val _totalPages = MutableStateFlow(0)
    val totalPages: StateFlow<Int> = _totalPages.asStateFlow()

    private val _percentage = MutableStateFlow(0f)
    val percentage: StateFlow<Float> = _percentage.asStateFlow()

    // ── UI controls ──
    private val _barsVisible = MutableStateFlow(true)
    val barsVisible: StateFlow<Boolean> = _barsVisible.asStateFlow()

    // ── Reading timer ──
    private var readingStartTime = 0L
    private var accumulatedReadingTimeMs = 0L

    // ── Events ──
    private val _events = MutableSharedFlow<ReaderEvent>()
    val events = _events.asSharedFlow()

    // ══════════════════════════════════════
    //  Settings (exposed as StateFlow from DataStore)
    // ══════════════════════════════════════

    val fontSize: StateFlow<Float> = userPreferences.fontSize
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 16f)

    val fontFamily: StateFlow<String> = userPreferences.fontFamily
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "system")

    val readerTheme: StateFlow<Int> = userPreferences.readerTheme
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val lineSpacing: StateFlow<Float> = userPreferences.lineSpacing
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 1.5f)

    val marginLevel: StateFlow<Int> = userPreferences.marginLevel
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 1)

    val textAlignment: StateFlow<Int> = userPreferences.textAlignment
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val customBgColor: StateFlow<String> = userPreferences.customBgColor
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "#FFFFFF")

    val customTextColor: StateFlow<String> = userPreferences.customTextColor
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "#212121")

    val brightness: StateFlow<Float> = userPreferences.brightness
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), -1f)

    val keepScreenAwake: StateFlow<Boolean> = userPreferences.keepScreenAwake
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val volumeButtonsTurn: StateFlow<Boolean> = userPreferences.volumeButtonsTurn
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val scrollDirection: StateFlow<Int> = userPreferences.scrollDirection
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val pageTurnAnimation: StateFlow<Int> = userPreferences.pageTurnAnimation
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    // ── Debounce save job ──
    private var saveJob: Job? = null

    init {
        loadBook()
    }

    private fun loadBook() {
        viewModelScope.launch {
            try {
                val bookData = bookRepository.getBookById(bookId)
                if (bookData != null) {
                    _book.value = UiState.Success(bookData)
                    bookRepository.updateLastOpened(bookId)
                    restoreProgress()
                } else {
                    _book.value = UiState.Error("Book not found")
                    _events.emit(ReaderEvent.ShowError("Book not found in database"))
                }
            } catch (e: Exception) {
                _book.value = UiState.Error(e.message ?: "Failed to load book")
                _events.emit(ReaderEvent.ShowError(e.message ?: "Failed to load book"))
            }
        }
    }

    private suspend fun restoreProgress() {
        val progress = progressRepository.getProgress(bookId)
        if (progress != null) {
            _currentPage.value = progress.currentPage
            _totalPages.value = progress.totalPages
            _percentage.value = progress.percentage
            accumulatedReadingTimeMs = progress.totalReadingTimeMs
        }
    }

    // ── Page tracking ──

    fun onPageChanged(page: Int, total: Int) {
        _currentPage.value = page
        _totalPages.value = total
        _percentage.value = if (total > 0) (page.toFloat() / total * 100f) else 0f

        // Debounced save
        saveJob?.cancel()
        saveJob = viewModelScope.launch {
            delay(500L)
            saveProgress()
        }
    }

    fun seekToPage(page: Int) {
        _currentPage.value = page
        viewModelScope.launch {
            _events.emit(ReaderEvent.SeekToPage(page))
        }
    }

    // ── Reading timer ──

    fun startReadingTimer() {
        readingStartTime = System.currentTimeMillis()
    }

    fun pauseReadingTimer() {
        if (readingStartTime > 0) {
            accumulatedReadingTimeMs += System.currentTimeMillis() - readingStartTime
            readingStartTime = 0L
        }
    }

    // ── Bar visibility ──

    fun toggleBars() {
        _barsVisible.value = !_barsVisible.value
    }

    // ── Progress persistence ──

    private suspend fun saveProgress() {
        val currentReadingTime = if (readingStartTime > 0) {
            accumulatedReadingTimeMs + (System.currentTimeMillis() - readingStartTime)
        } else {
            accumulatedReadingTimeMs
        }

        val progress = ReadingProgress(
            id = "",
            bookId = bookId,
            currentPage = _currentPage.value,
            totalPages = _totalPages.value,
            percentage = _percentage.value,
            lastReadAt = System.currentTimeMillis(),
            totalReadingTimeMs = currentReadingTime
        )
        progressRepository.saveProgress(progress)
    }

    fun saveProgressNow() {
        viewModelScope.launch { saveProgress() }
    }

    // ── Estimated reading time ──

    fun getEstimatedTimeRemaining(): String {
        val remainingPages = _totalPages.value - _currentPage.value
        if (remainingPages <= 0) return "Done"

        val minutesRemaining = remainingPages
        val hours = minutesRemaining / 60
        val minutes = minutesRemaining % 60

        return when {
            hours > 0 -> "${hours}h ${minutes}m left"
            minutes > 0 -> "${minutes}m left"
            else -> "< 1m left"
        }
    }

    // ── Text selection actions ──

    fun onDefineText(text: String) {
        viewModelScope.launch {
            _events.emit(ReaderEvent.ShowDictionary(text))
        }
    }

    fun saveHighlight(text: String, page: Int, color: Int, start: Int, end: Int) {
        val bookData = (_book.value as? UiState.Success)?.data ?: return
        viewModelScope.launch {
            highlightDao.insertHighlight(
                HighlightEntity(
                    bookId = bookId,
                    text = text,
                    color = String.format("#%08X", color),
                    pageNumber = page,
                    charStart = start,
                    charEnd = end,
                    chapter = bookData.title
                )
            )
            _events.emit(ReaderEvent.ShowSuccess("Highlight saved"))
        }
    }

    fun saveNote(selectedText: String, note: String, page: Int) {
        val bookData = (_book.value as? UiState.Success)?.data ?: return
        viewModelScope.launch {
            val content = if (selectedText.isBlank()) {
                note.trim()
            } else {
                "\"${selectedText.trim()}\"\n\n${note.trim()}"
            }
            noteDao.insertNote(
                NoteEntity(
                    bookId = bookId,
                    content = content,
                    pageNumber = page,
                    chapter = bookData.title
                )
            )
            _events.emit(ReaderEvent.ShowSuccess("Note saved"))
        }
    }

    fun addBookmark() {
        val bookData = (_book.value as? UiState.Success)?.data ?: return
        val current = _currentPage.value
        viewModelScope.launch {
            bookmarkDao.insertBookmark(
                BookmarkEntity(
                    bookId = bookId,
                    pageNumber = current,
                    title = "Page ${current + 1}",
                    chapter = bookData.title
                )
            )
            _events.emit(ReaderEvent.ShowSuccess("Bookmark saved"))
        }
    }

    override fun onCleared() {
        super.onCleared()
        pauseReadingTimer()
        saveProgressNow()
    }
}

sealed class ReaderEvent {
    data class ShowSuccess(val message: String) : ReaderEvent()
    data class ShowError(val message: String) : ReaderEvent()
    data class SeekToPage(val page: Int) : ReaderEvent()
    data class ShowDictionary(val word: String) : ReaderEvent()
}
