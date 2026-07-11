package com.lexiread.app.ui.dictionary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lexiread.app.data.local.entity.DictionaryCacheEntity
import com.lexiread.app.domain.repository.DictionaryRepository
import com.lexiread.app.domain.repository.DictionaryResult
import com.lexiread.app.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DictionaryViewModel @Inject constructor(
    private val dictionaryRepository: DictionaryRepository
) : ViewModel() {

    // ── Lookup state ──
    private val _lookupState = MutableStateFlow<UiState<DictionaryResult>>(UiState.Idle)
    val lookupState: StateFlow<UiState<DictionaryResult>> = _lookupState.asStateFlow()

    // ── Current word ──
    private val _currentWord = MutableStateFlow("")
    val currentWord: StateFlow<String> = _currentWord.asStateFlow()

    // ── Show alternate definitions ──
    private val _showAlternates = MutableStateFlow(false)
    val showAlternates: StateFlow<Boolean> = _showAlternates.asStateFlow()

    // ── Show recent lookups ──
    private val _showRecentLookups = MutableStateFlow(false)
    val showRecentLookups: StateFlow<Boolean> = _showRecentLookups.asStateFlow()

    // ── Recent lookups from cache ──
    val recentLookups: StateFlow<List<DictionaryCacheEntity>> =
        dictionaryRepository.getRecentLookups()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    /**
     * Look up a word in the offline dictionary.
     */
    fun lookupWord(word: String) {
        val cleanWord = word.trim()
        if (cleanWord.isBlank()) return

        _currentWord.value = cleanWord
        _showAlternates.value = false
        _showRecentLookups.value = false

        viewModelScope.launch {
            _lookupState.value = UiState.Loading
            try {
                val result = dictionaryRepository.getDefinition(cleanWord)
                if (result != null) {
                    _lookupState.value = UiState.Success(result)
                } else {
                    _lookupState.value = UiState.Error("Word not found")
                }
            } catch (e: Exception) {
                _lookupState.value = UiState.Error(e.message ?: "Lookup failed")
            }
        }
    }

    fun toggleAlternateDefinitions() {
        _showAlternates.value = !_showAlternates.value
    }

    fun toggleRecentLookups() {
        _showRecentLookups.value = !_showRecentLookups.value
    }

    fun clearCache() {
        viewModelScope.launch {
            dictionaryRepository.clearCache()
        }
    }

    fun reset() {
        _lookupState.value = UiState.Idle
        _currentWord.value = ""
        _showAlternates.value = false
        _showRecentLookups.value = false
    }
}
