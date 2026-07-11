package com.lexiread.app.ui.reader

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem

/**
 * Custom ActionMode.Callback for text selection in the reader.
 * Provides: Define, Highlight, Note, Copy, Share actions.
 */
class TextSelectionCallback(
    private val context: Context,
    private val onDefine: (String) -> Unit,
    private val onHighlight: (String, Int, Int) -> Unit,
    private val onNote: (String) -> Unit,
    private val getSelectedTextAndIndices: () -> Triple<String, Int, Int>
) : ActionMode.Callback {

    companion object {
        private const val MENU_DEFINE = 100
        private const val MENU_HIGHLIGHT = 101
        private const val MENU_NOTE = 102
        private const val MENU_COPY = 103
        private const val MENU_SHARE = 104
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        menu?.apply {
            add(0, MENU_DEFINE, 0, "Define")
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
            add(0, MENU_HIGHLIGHT, 1, "Highlight")
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
            add(0, MENU_NOTE, 2, "Note")
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
            add(0, MENU_COPY, 3, "Copy")
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
            add(0, MENU_SHARE, 4, "Share")
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        }
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean = false

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        val details = getSelectedTextAndIndices()
        val selectedText = details.first
        if (selectedText.isBlank()) return false

        return when (item?.itemId) {
            MENU_DEFINE -> {
                onDefine(selectedText.trim())
                mode?.finish()
                true
            }
            MENU_HIGHLIGHT -> {
                onHighlight(selectedText, details.second, details.third)
                mode?.finish()
                true
            }
            MENU_NOTE -> {
                onNote(selectedText)
                mode?.finish()
                true
            }
            MENU_COPY -> {
                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                clipboard.setPrimaryClip(ClipData.newPlainText("LexiRead", selectedText))
                mode?.finish()
                true
            }
            MENU_SHARE -> {
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, selectedText)
                }
                context.startActivity(Intent.createChooser(shareIntent, "Share via"))
                mode?.finish()
                true
            }
            else -> false
        }
    }

    override fun onDestroyActionMode(mode: ActionMode?) {}
}
