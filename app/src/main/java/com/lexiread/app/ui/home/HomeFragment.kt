package com.lexiread.app.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.lexiread.app.R
import com.lexiread.app.databinding.FragmentHomeBinding
import com.lexiread.app.domain.model.Book
import com.lexiread.app.utils.UiState
import com.lexiread.app.utils.hide
import com.lexiread.app.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var bookAdapter: BookAdapter
    private lateinit var searchAdapter: BookAdapter

    // ── SAF file picker ──
    private val filePickerLauncher = registerForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            viewModel.importBook(uri)
            binding.root.postDelayed({ viewModel.forceRefresh() }, 2000)
        }
    }

    // ── Permission request ──
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            openFilePicker()
        } else {
            view?.let {
                Snackbar.make(
                    it,
                    R.string.permission_storage_denied,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViews()
        setupTabs()
        setupSearch()
        setupFab()
        observeState()
    }

    // ── RecyclerView Setup ──

    private fun setupRecyclerViews() {
        bookAdapter = BookAdapter(
            onBookClick = { book -> viewModel.openBook(book) },
            onBookLongClick = { book, anchor -> showContextMenu(book, anchor) }
        )

        binding.rvBooks.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = bookAdapter
            setHasFixedSize(true)
        }

        searchAdapter = BookAdapter(
            onBookClick = { book ->
                binding.searchView.hide()
                viewModel.openBook(book)
            },
            onBookLongClick = { book, anchor -> showContextMenu(book, anchor) }
        )

        binding.rvSearchResults.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = searchAdapter
        }
    }

    // ── Tabs ──

    private fun setupTabs() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> viewModel.selectTab(LibraryTab.MY_LIBRARY)
                    1 -> viewModel.selectTab(LibraryTab.RECENT)
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    // ── Search ──

    private fun setupSearch() {
        binding.searchView.editText.addTextChangedListener { text ->
            val query = text?.toString() ?: ""
            viewModel.setSearchQuery(query)
        }

        // Key listener for search submit
        binding.searchView.editText.setOnEditorActionListener { _, _, _ ->
            val query = binding.searchView.editText.text?.toString() ?: ""
            viewModel.setSearchQuery(query)
            false
        }
    }

    // ── FAB ──

    private fun setupFab() {
        binding.fabAddBook.visibility = View.VISIBLE
        binding.fabAddBook.bringToFront()
        binding.fabAddBook.setOnClickListener {
            requestStorageAndPick()
        }
    }

    private fun requestStorageAndPick() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13+: SAF does not require READ_EXTERNAL_STORAGE
            openFilePicker()
        } else {
            // Android 12 and below
            val permission = Manifest.permission.READ_EXTERNAL_STORAGE
            if (ContextCompat.checkSelfPermission(requireContext(), permission)
                == PackageManager.PERMISSION_GRANTED
            ) {
                openFilePicker()
            } else {
                permissionLauncher.launch(permission)
            }
        }
    }

    private fun openFilePicker() {
        filePickerLauncher.launch(
            arrayOf(
                "application/pdf",
                "application/epub+zip",
                "text/plain"
            )
        )
    }

    // ── Context Menu ──

    private fun showContextMenu(book: Book, anchor: View) {
        PopupMenu(requireContext(), anchor).apply {
            menu.add(0, 1, 0, "Open")
            menu.add(0, 2, 1, "Delete")
            menu.add(0, 3, 2, "Mark as Finished")
            menu.add(0, 4, 3, "Book Details")

            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    1 -> {
                        viewModel.openBook(book)
                        true
                    }
                    2 -> {
                        showDeleteConfirmation(book)
                        true
                    }
                    3 -> {
                        viewModel.markAsFinished(book)
                        true
                    }
                    4 -> {
                        showBookDetails(book)
                        true
                    }
                    else -> false
                }
            }
            show()
        }
    }

    private fun showDeleteConfirmation(book: Book) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.delete_book)
            .setMessage(getString(R.string.delete_confirm))
            .setNegativeButton(R.string.cancel, null)
            .setPositiveButton(R.string.delete) { _, _ ->
                viewModel.deleteBook(book)
            }
            .show()
    }

    private fun showBookDetails(book: Book) {
        val details = buildString {
            appendLine("Title: ${book.title}")
            appendLine("Author: ${book.author}")
            appendLine("Format: ${book.format}")
            appendLine("Pages: ${if (book.totalPages > 0) book.totalPages else "Unknown"}")
            book.language?.let { appendLine("Language: $it") }
            book.publisher?.let { appendLine("Publisher: $it") }
            book.isbn?.let { appendLine("ISBN: $it") }
            appendLine("Size: ${formatFileSize(book.fileSize)}")
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(book.title)
            .setMessage(details)
            .setPositiveButton(R.string.ok, null)
            .show()
    }

    private fun formatFileSize(bytes: Long): String {
        val kb = bytes / 1024.0
        val mb = kb / 1024.0
        return when {
            mb >= 1.0 -> String.format("%.1f MB", mb)
            kb >= 1.0 -> String.format("%.0f KB", kb)
            else -> "$bytes B"
        }
    }

    // ── Observe ViewModel State ──

    private fun observeState() {
        // Books list
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.books.collect { state ->
                when (state) {
                    is UiState.Loading -> {
                        binding.progressLoading.show()
                        binding.rvBooks.hide()
                        binding.emptyState.hide()
                    }
                    is UiState.Idle -> {
                        binding.progressLoading.hide()
                        binding.rvBooks.hide()
                        binding.emptyState.show()
                    }
                    is UiState.Success -> {
                        binding.progressLoading.hide()
                        if (state.data.isEmpty()) {
                            binding.emptyState.show()
                            binding.rvBooks.hide()
                        } else {
                            binding.emptyState.hide()
                            binding.rvBooks.show()
                            bookAdapter.submitList(null)
                            bookAdapter.submitList(state.data)
                        }
                    }
                    is UiState.Error -> {
                        binding.progressLoading.hide()
                        binding.emptyState.show()
                        binding.rvBooks.hide()
                    }
                }
                binding.swipeRefresh.isRefreshing = false
            }
        }

        // Import state
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.importState.collect { state ->
                when (state) {
                    is UiState.Loading -> {
                        binding.progressLoading.show()
                    }
                    is UiState.Success -> {
                        binding.progressLoading.hide()
                        viewModel.resetImportState()
                    }
                    is UiState.Error -> {
                        binding.progressLoading.hide()
                        viewModel.resetImportState()
                    }
                    is UiState.Idle -> {
                        // no-op
                    }
                }
            }
        }

        // One-shot events (toasts, navigation)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.events.collect { event ->
                when (event) {
                    is HomeEvent.ShowSuccess -> {
                        view?.let {
                            Snackbar.make(it, event.message, Snackbar.LENGTH_SHORT).show()
                        }
                    }
                    is HomeEvent.ShowError -> {
                        view?.let {
                            Snackbar.make(it, event.message, Snackbar.LENGTH_LONG)
                                .setBackgroundTint(
                                    requireContext().getColor(R.color.error)
                                )
                                .show()
                        }
                    }
                    is HomeEvent.NavigateToReader -> {
                        findNavController().navigate(
                            HomeFragmentDirections.actionHomeToReader(event.bookId)
                        )
                    }
                }
            }
        }

        // Search results
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.books.collect { state ->
                if (state is UiState.Success) {
                    searchAdapter.submitList(state.data)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.forceRefresh()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}