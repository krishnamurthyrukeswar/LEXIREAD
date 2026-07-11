package com.lexiread.app.ui.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lexiread.app.data.local.datastore.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Settings screen.
 * Exposes all user preferences as StateFlow for real-time UI observation.
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val prefs: UserPreferences
) : ViewModel() {

    private fun <T> Flow(flow: kotlinx.coroutines.flow.Flow<T>, default: T): StateFlow<T> =
        flow.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), default)

    // ── App Theme ──
    val themeMode = Flow(prefs.themeMode, 0)

    // ── Reader Theme ──
    val readerTheme = Flow(prefs.readerTheme, 0)
    val customBgColor = Flow(prefs.customBgColor, "#FFFFFF")
    val customTextColor = Flow(prefs.customTextColor, "#212121")

    // ── Typography ──
    val fontSize = Flow(prefs.fontSize, 16f)
    val fontFamily = Flow(prefs.fontFamily, "system")
    val lineSpacing = Flow(prefs.lineSpacing, 1.5f)

    // ── Layout ──
    val marginLevel = Flow(prefs.marginLevel, 1)
    val textAlignment = Flow(prefs.textAlignment, 0)

    // ── Reading Controls ──
    val pageTurnAnimation = Flow(prefs.pageTurnAnimation, 0)
    val scrollDirection = Flow(prefs.scrollDirection, 0)
    val brightness = Flow(prefs.brightness, -1f)
    val keepScreenAwake = Flow(prefs.keepScreenAwake, false)
    val volumeButtonsTurn = Flow(prefs.volumeButtonsTurn, false)

    // ══════════════════════════════════════
    //  Setters
    // ══════════════════════════════════════

    fun setThemeMode(mode: Int) {
        viewModelScope.launch {
            prefs.setThemeMode(mode)
            // Apply system night mode immediately
            val nightMode = when (mode) {
                1 -> AppCompatDelegate.MODE_NIGHT_NO
                2 -> AppCompatDelegate.MODE_NIGHT_YES
                else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
            AppCompatDelegate.setDefaultNightMode(nightMode)
        }
    }

    fun setReaderTheme(theme: Int) {
        viewModelScope.launch { prefs.setReaderTheme(theme) }
    }

    fun setCustomBgColor(color: String) {
        viewModelScope.launch { prefs.setCustomBgColor(color) }
    }

    fun setCustomTextColor(color: String) {
        viewModelScope.launch { prefs.setCustomTextColor(color) }
    }

    fun setFontSize(size: Float) {
        viewModelScope.launch { prefs.setFontSize(size) }
    }

    fun setFontFamily(family: String) {
        viewModelScope.launch { prefs.setFontFamily(family) }
    }

    fun setLineSpacing(spacing: Float) {
        viewModelScope.launch { prefs.setLineSpacing(spacing) }
    }

    fun setMarginLevel(level: Int) {
        viewModelScope.launch { prefs.setMarginLevel(level) }
    }

    fun setTextAlignment(alignment: Int) {
        viewModelScope.launch { prefs.setTextAlignment(alignment) }
    }

    fun setPageTurnAnimation(anim: Int) {
        viewModelScope.launch { prefs.setPageTurnAnimation(anim) }
    }

    fun setScrollDirection(direction: Int) {
        viewModelScope.launch { prefs.setScrollDirection(direction) }
    }

    fun setBrightness(value: Float) {
        viewModelScope.launch { prefs.setBrightness(value) }
    }

    fun setKeepScreenAwake(enabled: Boolean) {
        viewModelScope.launch { prefs.setKeepScreenAwake(enabled) }
    }

    fun setVolumeButtonsTurn(enabled: Boolean) {
        viewModelScope.launch { prefs.setVolumeButtonsTurn(enabled) }
    }
}
