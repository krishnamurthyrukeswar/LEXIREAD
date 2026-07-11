package com.lexiread.app.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

// ── View Extensions ──

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

// ── Fragment Extensions ──

fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), message, duration).show()
}

fun Fragment.showSnackbar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    view?.let { Snackbar.make(it, message, duration).show() }
}

// ── Date/Time Extensions ──

fun Long.toFormattedDate(pattern: String = "MMM dd, yyyy"): String {
    return SimpleDateFormat(pattern, Locale.getDefault()).format(Date(this))
}

fun Long.toReadingTime(): String {
    val hours = TimeUnit.MILLISECONDS.toHours(this)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(this) % 60
    return when {
        hours > 0 -> "${hours}h ${minutes}m"
        minutes > 0 -> "${minutes}m"
        else -> "< 1m"
    }
}

// ── File Extensions ──

fun Long.toFileSize(): String {
    val kb = this / 1024.0
    val mb = kb / 1024.0
    return when {
        mb >= 1.0 -> String.format(Locale.US, "%.1f MB", mb)
        kb >= 1.0 -> String.format(Locale.US, "%.0f KB", kb)
        else -> "$this B"
    }
}

fun String.getFileExtension(): String {
    return substringAfterLast('.', "").uppercase()
}
