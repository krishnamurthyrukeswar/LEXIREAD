package com.lexiread.app.utils

object Constants {
    // Database
    const val DATABASE_NAME = "lexiread_database"

    // File types
    const val FORMAT_PDF = "PDF"
    const val FORMAT_EPUB = "EPUB"
    const val MIME_TYPE_PDF = "application/pdf"
    const val MIME_TYPE_EPUB = "application/epub+zip"

    // Reader defaults
    const val DEFAULT_FONT_SIZE = 16f
    const val DEFAULT_LINE_SPACING = 1.5f
    const val DEFAULT_FONT_FAMILY = "sans-serif"
    const val MIN_FONT_SIZE = 12f
    const val MAX_FONT_SIZE = 32f

    // Highlight colors
    val HIGHLIGHT_COLORS = listOf(
        "#80FFEB3B", // Yellow
        "#804CAF50", // Green
        "#802196F3", // Blue
        "#80E91E63", // Pink
        "#80FF9800"  // Orange
    )

    // Sync
    const val SYNC_WORK_NAME = "lexiread_sync"
    const val SYNC_INTERVAL_HOURS = 1L
}
