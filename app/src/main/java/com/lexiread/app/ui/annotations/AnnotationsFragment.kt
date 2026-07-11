package com.lexiread.app.ui.annotations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.lexiread.app.data.local.dao.BookmarkDao
import com.lexiread.app.data.local.dao.HighlightDao
import com.lexiread.app.data.local.dao.NoteDao
import com.lexiread.app.databinding.FragmentAnnotationsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AnnotationsFragment : Fragment() {

    private var _binding: FragmentAnnotationsBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var bookmarkDao: BookmarkDao
    @Inject lateinit var highlightDao: HighlightDao
    @Inject lateinit var noteDao: NoteDao

    private var contentJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnnotationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvAnnotations.layoutManager = LinearLayoutManager(requireContext())

        val highlightsTab = binding.tabLayout.newTab().setText("Highlights")
        val notesTab = binding.tabLayout.newTab().setText("Notes")
        val bookmarksTab = binding.tabLayout.newTab().setText("Bookmarks")

        binding.tabLayout.addTab(highlightsTab)
        binding.tabLayout.addTab(notesTab)
        binding.tabLayout.addTab(bookmarksTab)

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                loadTabContent(tab?.position ?: 0)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
            override fun onTabReselected(tab: TabLayout.Tab?) = Unit
        })

        binding.tabLayout.selectTab(highlightsTab)
        loadTabContent(0)
    }

    private fun loadTabContent(position: Int) {
        contentJob?.cancel()
        contentJob = viewLifecycleOwner.lifecycleScope.launch {
            when (position) {
                0 -> highlightDao.getAllHighlights().collectLatest { highlights ->
                    val rows = highlights.map {
                        "Highlight\n${it.text}\n${it.chapter ?: "Unknown book"} - Page ${it.pageNumber + 1}"
                    }
                    showRows(rows, "No highlights yet")
                }

                1 -> noteDao.getAllNotes().collectLatest { notes ->
                    val rows = notes.map {
                        "Note\n${it.content}\n${it.chapter ?: "Unknown book"} - Page ${it.pageNumber + 1}"
                    }
                    showRows(rows, "No notes yet")
                }

                else -> bookmarkDao.getAllBookmarks().collectLatest { bookmarks ->
                    val rows = bookmarks.map {
                        "Bookmark\n${it.chapter ?: "Unknown book"} - Page ${it.pageNumber + 1}"
                    }
                    showRows(rows, "No bookmarks yet")
                }
            }
        }
    }

    private fun showRows(rows: List<String>, emptyText: String) {
        binding.rvAnnotations.adapter = TextRowAdapter(if (rows.isEmpty()) listOf(emptyText) else rows)
    }

    override fun onDestroyView() {
        contentJob?.cancel()
        super.onDestroyView()
        _binding = null
    }

    private class TextRowAdapter(private val rows: List<String>) :
        RecyclerView.Adapter<TextRowAdapter.RowViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowViewHolder {
            val tv = TextView(parent.context).apply {
                setPadding(32, 28, 32, 28)
                textSize = 16f
                setLineSpacing(0f, 1.2f)
            }
            return RowViewHolder(tv)
        }

        override fun onBindViewHolder(holder: RowViewHolder, position: Int) {
            holder.textView.text = rows[position]
        }

        override fun getItemCount(): Int = rows.size

        class RowViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)
    }
}
