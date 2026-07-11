package com.lexiread.app.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * DataStore-backed preferences for all app settings.
 * Provides reactive Flow-based access to theme, typography,
 * reader controls, and display preferences.
 */
@Singleton
class UserPreferences @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    // ══════════════════════════════════════
    //  Keys
    // ══════════════════════════════════════

    companion object {
        // App theme (System/Light/Dark — controls AppCompatDelegate night mode)
        val THEME_MODE = intPreferencesKey("theme_mode")              // 0=System, 1=Light, 2=Dark

        // Reader theme (in-reader background/text colors)
        val READER_THEME = intPreferencesKey("reader_theme")          // 0=Light, 1=Dark, 2=Sepia, 3=AMOLED, 4=Custom
        val CUSTOM_BG_COLOR = stringPreferencesKey("custom_bg_color") // hex e.g. "#FFFFFF"
        val CUSTOM_TEXT_COLOR = stringPreferencesKey("custom_text_color")

        // Typography
        val FONT_SIZE = floatPreferencesKey("font_size")              // 12f–32f sp
        val FONT_FAMILY = stringPreferencesKey("font_family")         // identifier string
        val LINE_SPACING = floatPreferencesKey("line_spacing")        // 1.0f–2.5f multiplier

        // Layout
        val MARGIN_LEVEL = intPreferencesKey("margin_level")          // 0=Narrow, 1=Normal, 2=Wide, 3=Extra Wide
        val TEXT_ALIGNMENT = intPreferencesKey("text_alignment")      // 0=Left, 1=Justified

        // Reading controls
        val PAGE_TURN_ANIMATION = intPreferencesKey("page_turn_anim") // 0=Curl, 1=Slide, 2=Fade, 3=None
        val SCROLL_DIRECTION = intPreferencesKey("scroll_direction")  // 0=Horizontal, 1=Vertical
        val BRIGHTNESS = floatPreferencesKey("brightness")            // -1f=system, 0f–1f override
        val KEEP_SCREEN_AWAKE = booleanPreferencesKey("keep_screen_awake")
        val VOLUME_BUTTONS_TURN = booleanPreferencesKey("volume_buttons_turn")
    }

    // ══════════════════════════════════════
    //  App Theme Mode
    // ══════════════════════════════════════

    val themeMode: Flow<Int> = dataStore.data.map { it[THEME_MODE] ?: 0 }

    suspend fun setThemeMode(mode: Int) {
        dataStore.edit { it[THEME_MODE] = mode }
    }

    // ══════════════════════════════════════
    //  Reader Theme
    // ══════════════════════════════════════

    val readerTheme: Flow<Int> = dataStore.data.map { it[READER_THEME] ?: 0 }

    suspend fun setReaderTheme(theme: Int) {
        dataStore.edit { it[READER_THEME] = theme }
    }

    val customBgColor: Flow<String> = dataStore.data.map { it[CUSTOM_BG_COLOR] ?: "#FFFFFF" }

    suspend fun setCustomBgColor(color: String) {
        dataStore.edit { it[CUSTOM_BG_COLOR] = color }
    }

    val customTextColor: Flow<String> = dataStore.data.map { it[CUSTOM_TEXT_COLOR] ?: "#212121" }

    suspend fun setCustomTextColor(color: String) {
        dataStore.edit { it[CUSTOM_TEXT_COLOR] = color }
    }

    // ══════════════════════════════════════
    //  Typography
    // ══════════════════════════════════════

    val fontSize: Flow<Float> = dataStore.data.map { it[FONT_SIZE] ?: 16f }

    suspend fun setFontSize(size: Float) {
        dataStore.edit { it[FONT_SIZE] = size }
    }

    val fontFamily: Flow<String> = dataStore.data.map { it[FONT_FAMILY] ?: "system" }

    suspend fun setFontFamily(family: String) {
        dataStore.edit { it[FONT_FAMILY] = family }
    }

    val lineSpacing: Flow<Float> = dataStore.data.map { it[LINE_SPACING] ?: 1.5f }

    suspend fun setLineSpacing(spacing: Float) {
        dataStore.edit { it[LINE_SPACING] = spacing }
    }

    // ══════════════════════════════════════
    //  Layout
    // ══════════════════════════════════════

    val marginLevel: Flow<Int> = dataStore.data.map { it[MARGIN_LEVEL] ?: 1 }

    suspend fun setMarginLevel(level: Int) {
        dataStore.edit { it[MARGIN_LEVEL] = level }
    }

    val textAlignment: Flow<Int> = dataStore.data.map { it[TEXT_ALIGNMENT] ?: 0 }

    suspend fun setTextAlignment(alignment: Int) {
        dataStore.edit { it[TEXT_ALIGNMENT] = alignment }
    }

    // ══════════════════════════════════════
    //  Reading Controls
    // ══════════════════════════════════════

    val pageTurnAnimation: Flow<Int> = dataStore.data.map { it[PAGE_TURN_ANIMATION] ?: 0 }

    suspend fun setPageTurnAnimation(anim: Int) {
        dataStore.edit { it[PAGE_TURN_ANIMATION] = anim }
    }

    val scrollDirection: Flow<Int> = dataStore.data.map { it[SCROLL_DIRECTION] ?: 0 }

    suspend fun setScrollDirection(direction: Int) {
        dataStore.edit { it[SCROLL_DIRECTION] = direction }
    }

    val brightness: Flow<Float> = dataStore.data.map { it[BRIGHTNESS] ?: -1f }

    suspend fun setBrightness(value: Float) {
        dataStore.edit { it[BRIGHTNESS] = value }
    }

    val keepScreenAwake: Flow<Boolean> = dataStore.data.map { it[KEEP_SCREEN_AWAKE] ?: false }

    suspend fun setKeepScreenAwake(enabled: Boolean) {
        dataStore.edit { it[KEEP_SCREEN_AWAKE] = enabled }
    }

    val volumeButtonsTurn: Flow<Boolean> = dataStore.data.map { it[VOLUME_BUTTONS_TURN] ?: false }

    suspend fun setVolumeButtonsTurn(enabled: Boolean) {
        dataStore.edit { it[VOLUME_BUTTONS_TURN] = enabled }
    }
}
