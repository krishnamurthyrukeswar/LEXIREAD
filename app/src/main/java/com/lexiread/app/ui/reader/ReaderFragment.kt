package com.lexiread.app.ui.reader

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener
import com.github.barteksc.pdfviewer.listener.OnTapListener
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import com.google.android.material.slider.Slider
import com.google.android.material.snackbar.Snackbar
import com.lexiread.app.R
import com.lexiread.app.databinding.FragmentReaderBinding
import com.lexiread.app.domain.model.Book
import com.lexiread.app.ui.dictionary.DictionaryBottomSheet
import com.lexiread.app.utils.FontHelper
import com.lexiread.app.utils.UiState
import com.lexiread.app.utils.hide
import com.lexiread.app.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File
import androidx.core.os.bundleOf
import androidx.fragment.app.commitNow
import org.readium.r2.navigator.epub.EpubNavigatorFactory
import org.readium.r2.navigator.epub.EpubNavigatorFragment
import org.readium.r2.shared.publication.Publication
import org.readium.r2.shared.util.toUrl
import org.readium.r2.shared.util.http.DefaultHttpClient
import org.readium.r2.shared.util.asset.AssetRetriever
import org.readium.r2.streamer.PublicationOpener
import org.readium.r2.streamer.parser.DefaultPublicationParser

@AndroidEntryPoint
class ReaderFragment : Fragment() {

    private var _binding: FragmentReaderBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ReaderViewModel by viewModels()

    // Track if user is dragging the slider (to avoid feedback loops)
    private var isSliderTracking = false
    private var isTxtReaderActive = false
    private var estimatedTxtPages = 1

    // Readium 3
    private var publication: Publication? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReaderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupBottomBar()
        observeState()
        observeSettings()
    }

    // ═══════════════════════════════════
    //  Toolbar
    // ═══════════════════════════════════

    private fun setupToolbar() {
        binding.readerToolbar.setNavigationOnClickListener {
            viewModel.saveProgressNow()
            findNavController().navigateUp()
        }

        binding.btnSearchInBook.setOnClickListener {
            if (isTxtReaderActive) {
                showTextSearchDialog()
            } else {
                toast("Search is available for TXT books")
            }
        }

        binding.btnBookmark.setOnClickListener {
            viewModel.addBookmark()
        }

        binding.btnMoreOptions.setOnClickListener { anchor ->
            PopupMenu(requireContext(), anchor).apply {
                menu.add(0, 1, 0, "Text-to-Speech")
                menu.add(0, 2, 1, "Auto-scroll")
                menu.add(0, 3, 2, "Sleep Timer")
                setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        1 -> { toast("Text-to-speech is not enabled in this local build"); true }
                        2 -> { toast("Auto-scroll is not enabled in this local build"); true }
                        3 -> { toast("Sleep timer is not enabled in this local build"); true }
                        else -> false
                    }
                }
                show()
            }
        }
    }

    // ═══════════════════════════════════
    //  Bottom Bar + Slider
    // ═══════════════════════════════════

    private fun setupBottomBar() {
        binding.sliderProgress.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
                isSliderTracking = true
            }

            override fun onStopTrackingTouch(slider: Slider) {
                isSliderTracking = false
                val targetPage = slider.value.toInt()
                viewModel.seekToPage(targetPage)
            }
        })
    }

    private fun updateBottomBar(page: Int, total: Int) {
        if (!isSliderTracking && total > 0) {
            binding.sliderProgress.valueTo = total.toFloat().coerceAtLeast(1f)
            binding.sliderProgress.value = page.toFloat().coerceIn(0f, total.toFloat())
        }
        binding.tvPageInfo.text = getString(R.string.page_of, page + 1, total)
        binding.tvTimeRemaining.text = viewModel.getEstimatedTimeRemaining()
    }

    // ═══════════════════════════════════
    //  Toggle bars visibility
    // ═══════════════════════════════════

    private fun toggleBars(visible: Boolean) {
        val duration = 200L
        if (visible) {
            binding.topBar.animate().translationY(0f).setDuration(duration).start()
            binding.bottomBar.animate().translationY(0f).setDuration(duration).start()
            binding.topBar.show()
            binding.bottomBar.show()
        } else {
            binding.topBar.animate()
                .translationY(-binding.topBar.height.toFloat())
                .setDuration(duration)
                .withEndAction { binding.topBar.hide() }
                .start()
            binding.bottomBar.animate()
                .translationY(binding.bottomBar.height.toFloat())
                .setDuration(duration)
                .withEndAction { binding.bottomBar.hide() }
                .start()
        }
    }

    // ═══════════════════════════════════
    //  Observe ViewModel
    // ═══════════════════════════════════

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                // Book loaded → dispatch to correct reader
                launch {
                    viewModel.book.collect { state ->
                        when (state) {
                            is UiState.Loading -> {
                                binding.progressLoading.show()
                            }

                            is UiState.Success -> {
                                binding.progressLoading.hide()
                                val book = state.data
                                binding.readerToolbar.title = book.title
                                launchReader(book)
                            }

                            is UiState.Error -> {
                                binding.progressLoading.hide()
                                showError(state.message)
                            }

                            is UiState.Idle -> {}
                        }
                    }
                }

                // Page changes
                launch {
                    viewModel.currentPage.collect { page ->
                        updateBottomBar(page, viewModel.totalPages.value)
                    }
                }

                launch {
                    viewModel.totalPages.collect { total ->
                        updateBottomBar(viewModel.currentPage.value, total)
                    }
                }

                // Bar visibility
                launch {
                    viewModel.barsVisible.collect { visible ->
                        toggleBars(visible)
                    }
                }

                // Events
                launch {
                    viewModel.events.collect { event ->
                        when (event) {
                            is ReaderEvent.ShowSuccess -> toast(event.message)
                            is ReaderEvent.ShowError -> showError(event.message)
                            is ReaderEvent.SeekToPage -> {
                                if (isTxtReaderActive) {
                                    seekTxtToPage(event.page)
                                } else {
                                    seekPdfToPage(event.page)
                                }
                            }
                            is ReaderEvent.ShowDictionary -> {
                                DictionaryBottomSheet.newInstance(event.word)
                                    .show(childFragmentManager, DictionaryBottomSheet.TAG)
                            }
                        }
                    }
                }
            }
        }
    }

    // ═══════════════════════════════════
    //  Observe Settings (real-time apply)
    // ═══════════════════════════════════

    private fun observeSettings() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                // Reader Theme
                launch {
                    viewModel.readerTheme.collect { theme ->
                        applyReaderTheme(theme)
                    }
                }

                // Custom colors (for custom theme)
                launch {
                    viewModel.customBgColor.collect {
                        if (viewModel.readerTheme.value == 4) applyReaderTheme(4)
                    }
                }
                launch {
                    viewModel.customTextColor.collect {
                        if (viewModel.readerTheme.value == 4) applyReaderTheme(4)
                    }
                }

                // Font size
                launch {
                    viewModel.fontSize.collect { size ->
                        binding.tvTxtContent.textSize = size
                        binding.txtScrollView.setCurrentFontSize(size)
                    }
                }

                // Font family
                launch {
                    viewModel.fontFamily.collect { family ->
                        binding.tvTxtContent.typeface =
                            FontHelper.getTypeface(requireContext(), family)
                    }
                }

                // Line spacing
                launch {
                    viewModel.lineSpacing.collect { spacing ->
                        binding.tvTxtContent.setLineSpacing(0f, spacing)
                    }
                }

                // Margins
                launch {
                    viewModel.marginLevel.collect { level ->
                        val paddingPx = when (level) {
                            0 -> dpToPx(8)   // Narrow
                            2 -> dpToPx(40)  // Wide
                            3 -> dpToPx(56)  // Extra Wide
                            else -> dpToPx(24) // Normal
                        }
                        val vPad = dpToPx(16)
                        binding.tvTxtContent.setPadding(paddingPx, vPad, paddingPx, vPad)
                    }
                }

                // Text alignment
                launch {
                    viewModel.textAlignment.collect { alignment ->
                        binding.tvTxtContent.textAlignment = when (alignment) {
                            1 -> View.TEXT_ALIGNMENT_TEXT_START
                            else -> View.TEXT_ALIGNMENT_TEXT_START
                        }
                        // For justified text on API 26+
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            binding.tvTxtContent.justificationMode = when (alignment) {
                                1 -> android.text.Layout.JUSTIFICATION_MODE_INTER_WORD
                                else -> android.text.Layout.JUSTIFICATION_MODE_NONE
                            }
                        }
                    }
                }

                // Brightness
                launch {
                    viewModel.brightness.collect { value ->
                        activity?.window?.let { window ->
                            val params = window.attributes
                            params.screenBrightness = if (value < 0f) {
                                WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE
                            } else {
                                value.coerceIn(0.01f, 1f)
                            }
                            window.attributes = params
                        }
                    }
                }

                // Keep screen awake
                launch {
                    viewModel.keepScreenAwake.collect { enabled ->
                        if (enabled) {
                            activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                        } else {
                            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                        }
                    }
                }
            }
        }
    }

    // ═══════════════════════════════════
    //  Format Dispatch
    // ═══════════════════════════════════

    private fun launchReader(book: Book) {
        val file = File(book.filePath)
        if (!file.exists()) {
            showError("File not found: ${book.filePath}")
            return
        }

        when (book.format.uppercase()) {
            "PDF" -> setupPdfReader(file, book)
            "EPUB" -> setupEpubReader(file, book)
            "TXT" -> setupTxtReader(file, book)
            "MOBI" -> {
                showError("MOBI is not supported. Please convert it to EPUB or TXT.")
            }
            else -> {
                showError("Unsupported format: ${book.format}")
            }
        }
    }

    // ═══════════════════════════════════
    //  PDF Reader
    // ═══════════════════════════════════

    private fun setupPdfReader(file: File, book: Book) {
        isTxtReaderActive = false
        binding.txtScrollView.hide()
        binding.pdfView.show()

        binding.pdfView.fromFile(file)
            .enableSwipe(true)
            .swipeHorizontal(false)
            .enableDoubletap(true)
            .defaultPage(viewModel.currentPage.value)
            .onLoad(OnLoadCompleteListener { nbPages ->
                viewModel.onPageChanged(viewModel.currentPage.value, nbPages)
            })
            .onPageChange(OnPageChangeListener { page, pageCount ->
                viewModel.onPageChanged(page, pageCount)
            })
            .onTap(OnTapListener { e ->
                viewModel.toggleBars()
                true
            })
            .scrollHandle(DefaultScrollHandle(requireContext()))
            .enableAnnotationRendering(true)
            .spacing(10)
            .load()
    }

    private fun seekPdfToPage(page: Int) {
        binding.pdfView.jumpTo(page, true)
    }

    private fun seekTxtToPage(page: Int) {
        val maxScroll = (binding.tvTxtContent.height - binding.txtScrollView.height).coerceAtLeast(0)
        val fraction = if (estimatedTxtPages <= 0) 0f else page.toFloat() / estimatedTxtPages
        binding.txtScrollView.smoothScrollTo(0, (maxScroll * fraction).toInt())
    }

    // ═══════════════════════════════════
    //  EPUB Reader (Readium 3)
    // ═══════════════════════════════════

    private fun setupEpubReader(file: File, book: Book) {
        isTxtReaderActive = false
        binding.txtScrollView.hide()
        binding.pdfView.hide()
        binding.progressLoading.show()

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val httpClient = DefaultHttpClient()
                val assetRetriever = AssetRetriever(requireContext().contentResolver, httpClient)
                val publicationOpener = PublicationOpener(
                    DefaultPublicationParser(requireContext(), httpClient, assetRetriever, null)
                )

                val url = file.toUrl()
                val asset = assetRetriever.retrieve(url).let {
                    if (it.isFailure) throw Exception("Failed to retrieve EPUB asset")
                    it.getOrNull()!!
                }
                val pub = publicationOpener.open(asset, allowUserInteraction = false).let {
                    if (it.isFailure) throw Exception("Failed to open EPUB")
                    it.getOrNull()!!
                }

                publication = pub

                val navigatorFactory = EpubNavigatorFactory(pub)
                val fragmentFactory = navigatorFactory.createFragmentFactory(initialLocator = null)

                childFragmentManager.fragmentFactory = fragmentFactory
                childFragmentManager.commitNow {
                    replace(
                        R.id.reader_container,
                        EpubNavigatorFragment::class.java,
                        bundleOf(),
                        "epub_navigator"
                    )
                }

                (childFragmentManager.findFragmentByTag("epub_navigator") as? EpubNavigatorFragment)
                    ?.let { nav ->
                        // Hide bars so they don't intercept touches
                        if (viewModel.barsVisible.value) viewModel.toggleBars()

                        // Wire up tap to toggle bars
                        nav.addInputListener(object : org.readium.r2.navigator.input.InputListener {
                            override fun onTap(event: org.readium.r2.navigator.input.TapEvent): Boolean {
                                viewModel.toggleBars()
                                return true
                            }
                        })

                        viewLifecycleOwner.lifecycleScope.launch {
                            nav.currentLocator.collect { locator ->
                                val index = pub.readingOrder.indexOfFirst { link -> link.href.toString() == locator.href.toString() }
                                if (index >= 0) viewModel.onPageChanged(index, pub.readingOrder.size)
                            }
                        }
                    }

                binding.progressLoading.hide()

            } catch (e: Exception) {
                binding.progressLoading.hide()
                showError("EPUB error: ${e.message}")
            }
        }
    }

    // ═══════════════════════════════════
    //  TXT Reader
    // ═══════════════════════════════════

    private fun setupTxtReader(file: File, book: Book) {
        isTxtReaderActive = true
        binding.pdfView.hide()
        binding.txtScrollView.show()

        try {
            val content = file.readText(Charsets.UTF_8)
            setupTxtView(content, book)
        } catch (e: Exception) {
            showError("Failed to read file: ${e.message}")
        }
    }

    private fun setupTxtView(content: String, book: Book) {
        val textView = binding.tvTxtContent

        // Set content
        textView.text = content

        // Custom text selection callback
        textView.customSelectionActionModeCallback = TextSelectionCallback(
            context = requireContext(),
            onDefine = { text -> viewModel.onDefineText(text) },
            onHighlight = { text, start, end -> 
                showColorPicker { color ->
                    applyHighlight(start, end, color)
                    viewModel.saveHighlight(text, currentTxtPage(), color, start, end)
                }
            },
            onNote = { text -> showNoteDialog(text) },
            getSelectedTextAndIndices = {
                val start = textView.selectionStart
                val end = textView.selectionEnd
                if (start >= 0 && end > start) Triple(textView.text.substring(start, end), start, end) else Triple("", -1, -1)
            }
        )

        // Pinch-to-zoom font size change
        binding.txtScrollView.onFontSizeChanged = { newSize ->
            textView.textSize = newSize
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.userPreferences.setFontSize(newSize)
            }
        }

        // Tap to toggle bars
        binding.txtScrollView.setOnClickListener {
            viewModel.toggleBars()
        }

        // Estimate pages: ~250 words per page, avg 5 chars per word
        estimatedTxtPages = (content.length / (250 * 5)).coerceAtLeast(1)
        viewModel.onPageChanged(0, estimatedTxtPages)

        // Track scroll position as "page"
        binding.txtScrollView.viewTreeObserver.addOnScrollChangedListener {
            val scrollY = binding.txtScrollView.scrollY
            val maxScroll = (textView.height - binding.txtScrollView.height).coerceAtLeast(1)
            val scrollFraction = (scrollY.toFloat() / maxScroll).coerceIn(0f, 1f)
            val currentPage = (scrollFraction * estimatedTxtPages).toInt()
            viewModel.onPageChanged(currentPage, estimatedTxtPages)
        }

        binding.readerToolbar.title = book.title
        binding.readerToolbar.subtitle = "TXT"

        viewModel.startReadingTimer()
    }

    private fun applyReaderTheme(theme: Int) {
        val bgColor: Int
        val textColor: Int

        when (theme) {
            1 -> { // Dark
                bgColor = resources.getColor(R.color.reader_bg_dark, null)
                textColor = resources.getColor(R.color.reader_text_dark, null)
            }
            2 -> { // Sepia
                bgColor = resources.getColor(R.color.reader_bg_sepia, null)
                textColor = resources.getColor(R.color.reader_text_sepia, null)
            }
            3 -> { // AMOLED Black
                bgColor = resources.getColor(R.color.reader_bg_amoled, null)
                textColor = resources.getColor(R.color.reader_text_amoled, null)
            }
            4 -> { // Custom
                bgColor = try {
                    Color.parseColor(viewModel.customBgColor.value)
                } catch (e: Exception) { Color.WHITE }
                textColor = try {
                    Color.parseColor(viewModel.customTextColor.value)
                } catch (e: Exception) { Color.BLACK }
            }
            else -> { // Light
                bgColor = resources.getColor(R.color.reader_bg_light, null)
                textColor = resources.getColor(R.color.reader_text_light, null)
            }
        }

        binding.readerRoot.setBackgroundColor(bgColor)
        binding.tvTxtContent.setTextColor(textColor)
    }

    private fun showColorPicker(onColorSelected: (Int) -> Unit) {
        val colors = intArrayOf(
            Color.parseColor("#FFF59D"), // Yellow
            Color.parseColor("#A5D6A7"), // Green
            Color.parseColor("#81D4FA"), // Blue
            Color.parseColor("#F48FB1"), // Pink
            Color.parseColor("#CE93D8")  // Purple
        )
        
        val layout = android.widget.LinearLayout(requireContext()).apply {
            orientation = android.widget.LinearLayout.HORIZONTAL
            gravity = android.view.Gravity.CENTER
            setPadding(0, dpToPx(24), 0, dpToPx(24))
        }

        val dialog = com.google.android.material.dialog.MaterialAlertDialogBuilder(requireContext())
            .setTitle("Choose Highlight Color")
            .setView(layout)
            .create()

        for (color in colors) {
            val view = View(requireContext()).apply {
                layoutParams = android.widget.LinearLayout.LayoutParams(dpToPx(48), dpToPx(48)).apply {
                    setMargins(dpToPx(8), 0, dpToPx(8), 0)
                }
                background = android.graphics.drawable.GradientDrawable().apply {
                    shape = android.graphics.drawable.GradientDrawable.OVAL
                    setColor(color)
                    setStroke(dpToPx(1), Color.LTGRAY)
                }
                setOnClickListener {
                    onColorSelected(color)
                    dialog.dismiss()
                }
            }
            layout.addView(view)
        }
        dialog.show()
    }

    private fun showNoteDialog(selectedText: String) {
        val input = android.widget.EditText(requireContext()).apply {
            minLines = 3
            hint = "Write a note"
        }
        com.google.android.material.dialog.MaterialAlertDialogBuilder(requireContext())
            .setTitle("Add Note")
            .setView(input)
            .setNegativeButton(R.string.cancel, null)
            .setPositiveButton(R.string.save) { _, _ ->
                val note = input.text?.toString().orEmpty()
                if (note.isBlank()) {
                    toast("Note is empty")
                } else {
                    viewModel.saveNote(selectedText, note, currentTxtPage())
                }
            }
            .show()
    }

    private fun showTextSearchDialog() {
        val input = android.widget.EditText(requireContext()).apply {
            hint = "Search text"
            maxLines = 1
            inputType = android.text.InputType.TYPE_CLASS_TEXT
        }
        com.google.android.material.dialog.MaterialAlertDialogBuilder(requireContext())
            .setTitle("Search Book")
            .setView(input)
            .setNegativeButton(R.string.cancel, null)
            .setPositiveButton(R.string.ok) { _, _ ->
                findInTxt(input.text?.toString().orEmpty())
            }
            .show()
    }

    private fun findInTxt(query: String) {
        if (query.isBlank()) return
        val text = binding.tvTxtContent.text.toString()
        val index = text.indexOf(query, ignoreCase = true)
        if (index < 0) {
            toast("No match found")
            return
        }
        val layout = binding.tvTxtContent.layout
        if (layout != null) {
            val line = layout.getLineForOffset(index)
            binding.txtScrollView.smoothScrollTo(0, layout.getLineTop(line))
        }
        binding.tvTxtContent.requestFocus()
    }

    private fun currentTxtPage(): Int {
        val textView = binding.tvTxtContent
        val maxScroll = (textView.height - binding.txtScrollView.height).coerceAtLeast(1)
        val scrollFraction = (binding.txtScrollView.scrollY.toFloat() / maxScroll).coerceIn(0f, 1f)
        return (scrollFraction * estimatedTxtPages).toInt()
    }

    private fun applyHighlight(start: Int, end: Int, color: Int) {
        val text = binding.tvTxtContent.text
        val spannable = if (text is android.text.Spannable) text else android.text.SpannableString(text)
        spannable.setSpan(
            android.text.style.BackgroundColorSpan(color),
            start, end,
            android.text.Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.tvTxtContent.setText(spannable, android.widget.TextView.BufferType.SPANNABLE)
    }

    // ═══════════════════════════════════
    //  Helpers
    // ═══════════════════════════════════

    private fun showError(message: String) {
        view?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_LONG)
                .setBackgroundTint(requireContext().getColor(R.color.error))
                .show()
        }
    }

    private fun toast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }

    // ═══════════════════════════════════
    //  Lifecycle
    // ═══════════════════════════════════

    override fun onResume() {
        super.onResume()
        viewModel.startReadingTimer()
    }

    override fun onPause() {
        super.onPause()
        viewModel.pauseReadingTimer()
        viewModel.saveProgressNow()

        // Reset brightness to system default when leaving reader
        activity?.window?.let { window ->
            val params = window.attributes
            params.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE
            window.attributes = params
        }
        // Clear keep screen awake
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
