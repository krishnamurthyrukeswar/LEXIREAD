package com.lexiread.app.ui.home

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lexiread.app.data.repository.BookRepositoryImpl
import com.lexiread.app.domain.model.Book
import com.lexiread.app.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class LibraryTab { MY_LIBRARY, RECENT }

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val bookRepository: com.lexiread.app.domain.repository.BookRepository,
    private val progressRepository: com.lexiread.app.domain.repository.ReadingProgressRepository
) : ViewModel() {

    // ── Search ──
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // ── Tab selection ──
    private val _selectedTab = MutableStateFlow(LibraryTab.MY_LIBRARY)
    val selectedTab: StateFlow<LibraryTab> = _selectedTab.asStateFlow()

    // ── Import state ──
    private val _importState = MutableStateFlow<UiState<Book>>(UiState.Idle)
    val importState: StateFlow<UiState<Book>> = _importState.asStateFlow()

    // ── Events (one-shot, e.g. toast messages) ──
    private val _events = MutableSharedFlow<HomeEvent>()
    val events = _events.asSharedFlow()

    // ── Books list (reactive to tab + search query + progress) ──
    val books: StateFlow<UiState<List<BookAdapterItem>>> = combine(
        _selectedTab,
        _searchQuery
    ) { tab, query ->
        Pair(tab, query)
    }.flatMapLatest { (tab, query) ->
        val bookFlow = when {
            query.isNotBlank() -> bookRepository.searchBooks(query)
            tab == LibraryTab.RECENT -> bookRepository.getRecentBooks()
            else -> bookRepository.getAllBooks()
        }
        
        combine(bookFlow, progressRepository.getAllProgress()) { books, progressList ->
            books.map { book ->
                val progress = progressList.find { it.bookId == book.id }
                BookAdapterItem(book, progress?.percentage?.toInt() ?: 0)
            }
        }.catch { e ->
            emit(emptyList())
            _events.emit(HomeEvent.ShowError(e.message ?: "Failed to load books"))
        }
    }.combine(_searchQuery) { bookItems, query ->
        if (bookItems.isEmpty() && query.isBlank()) {
            UiState.Idle // Show empty state
        } else {
            UiState.Success(bookItems)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UiState.Loading
    )

    // ── Book count ──
    val bookCount: StateFlow<Int> = bookRepository.getBookCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    // ── Actions ──

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectTab(tab: LibraryTab) {
        _selectedTab.value = tab
    }

    fun importBook(uri: Uri) {
        viewModelScope.launch {
            _importState.value = UiState.Loading
            try {
                val book: Book? = bookRepository.importBook(uri)
                if (book != null) {
                    _importState.value = UiState.Success<Book>(book)
                    _events.emit(HomeEvent.ShowSuccess("\"${book.title}\" added to library"))
                } else {
                    _importState.value = UiState.Error("Unsupported file format or import failed")
                    _events.emit(HomeEvent.ShowError("Could not import file. Supported: PDF, EPUB, TXT"))
                }
            } catch (e: Exception) {
                _importState.value = UiState.Error(e.message ?: "Import failed")
                _events.emit(HomeEvent.ShowError(e.message ?: "Import failed"))
            }
        }
    }

    fun deleteBook(book: Book) {
        viewModelScope.launch {
            try {
                bookRepository.deleteBook(book)
                _events.emit(HomeEvent.ShowSuccess("\"${book.title}\" deleted"))
            } catch (e: Exception) {
                _events.emit(HomeEvent.ShowError("Failed to delete book"))
            }
        }
    }

    fun toggleFavorite(book: Book) {
        viewModelScope.launch {
            bookRepository.toggleFavorite(book.id, !book.isFavorite)
        }
    }

    fun markAsFinished(book: Book) {
        viewModelScope.launch {
            try {
                val updated = book.copy(totalPages = book.totalPages.coerceAtLeast(1))
                bookRepository.updateBook(updated)
                _events.emit(HomeEvent.ShowSuccess("\"${book.title}\" marked as finished"))
            } catch (e: Exception) {
                _events.emit(HomeEvent.ShowError("Failed to update book"))
            }
        }
    }

    fun openBook(book: Book) {
        viewModelScope.launch {
            bookRepository.updateLastOpened(book.id)
            _events.emit(HomeEvent.NavigateToReader(book.id))
        }
    }

    fun resetImportState() {
        _importState.value = UiState.Idle
    }
}

sealed class HomeEvent {
    data class ShowSuccess(val message: String) : HomeEvent()
    data class ShowError(val message: String) : HomeEvent()
    data class NavigateToReader(val bookId: String) : HomeEvent()
}
