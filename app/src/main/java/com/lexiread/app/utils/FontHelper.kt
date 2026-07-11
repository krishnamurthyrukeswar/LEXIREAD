package com.lexiread.app.utils

import android.content.Context
import android.graphics.Typeface
import android.util.LruCache

/**
 * Utility for loading and caching custom fonts from assets/fonts/.
 *
 * Supported font families:
 *   "system"       → Typeface.DEFAULT
 *   "serif"        → Typeface.SERIF
 *   "opendyslexic" → assets/fonts/OpenDyslexic-Regular.ttf
 *   "literata"     → assets/fonts/Literata-Regular.ttf
 *   "merriweather" → assets/fonts/Merriweather-Regular.ttf
 *   "lato"         → assets/fonts/Lato-Regular.ttf
 *   "roboto_mono"  → assets/fonts/RobotoMono-Regular.ttf
 */
object FontHelper {

    // Font identifier → asset filename
    private val FONT_MAP = mapOf(
        "opendyslexic" to "fonts/OpenDyslexic-Regular.ttf",
        "literata" to "fonts/Literata-Regular.ttf",
        "merriweather" to "fonts/Merriweather-Regular.ttf",
        "lato" to "fonts/Lato-Regular.ttf",
        "roboto_mono" to "fonts/RobotoMono-Regular.ttf"
    )

    // Display names for the settings UI
    val FONT_OPTIONS = listOf(
        FontOption("system", "Default (System)"),
        FontOption("serif", "Serif (Georgia)"),
        FontOption("opendyslexic", "OpenDyslexic"),
        FontOption("literata", "Literata"),
        FontOption("merriweather", "Merriweather"),
        FontOption("lato", "Lato"),
        FontOption("roboto_mono", "Roboto Mono")
    )

    // LRU cache so we don't reload from assets each time
    private val cache = LruCache<String, Typeface>(7)

    /**
     * Returns the Typeface for the given font family identifier.
     * Falls back to system default if the asset file is missing.
     */
    fun getTypeface(context: Context, family: String): Typeface {
        // System built-in fonts
        when (family) {
            "system" -> return Typeface.DEFAULT
            "serif" -> return Typeface.SERIF
        }

        // Check cache
        cache.get(family)?.let { return it }

        // Try loading from assets
        val assetPath = FONT_MAP[family] ?: return Typeface.DEFAULT
        return try {
            val typeface = Typeface.createFromAsset(context.assets, assetPath)
            cache.put(family, typeface)
            typeface
        } catch (e: Exception) {
            // Font file not found or invalid — fall back gracefully
            Typeface.DEFAULT
        }
    }

    data class FontOption(
        val id: String,
        val displayName: String
    )
}
