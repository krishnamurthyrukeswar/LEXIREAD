package com.lexiread.app.ui.settings

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.lexiread.app.BuildConfig
import com.lexiread.app.R
import com.lexiread.app.databinding.FragmentSettingsBinding
import com.lexiread.app.utils.FontHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Full settings screen with live preview card.
 * Grouped into: Appearance, Typography, Reading, About.
 * All changes persist via DataStore and update the UI in real-time.
 */
@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SettingsViewModel by viewModels()

    // Suppress listener callbacks during programmatic setup
    private var isInitializing = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvVersion.text = getString(R.string.version) + " " + BuildConfig.VERSION_NAME

        setupListeners()
        observeSettings()

        binding.btnSignOut.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "You are using LexiRead offline",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.tvRateUs.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW,
                Uri.parse("https://github.com/krishnamurthyrukeswar/LEXIREAD"))
            startActivity(intent)
        }

        binding.tvPrivacyPolicy.setOnClickListener {
            com.google.android.material.dialog.MaterialAlertDialogBuilder(requireContext())
                .setTitle("Privacy Policy")
                .setMessage("LexiRead does not collect or share any personal data. All your books and reading progress are stored locally on your device.")
                .setPositiveButton("OK", null)
                .show()
        }
    }

    // ══════════════════════════════════════
    //  Setup Listeners (one-time wiring)
    // ══════════════════════════════════════

    private fun setupListeners() {
        // ── App Theme ──
        binding.rgAppTheme.setOnCheckedChangeListener { _, checkedId ->
            if (isInitializing) return@setOnCheckedChangeListener
            val mode = when (checkedId) {
                R.id.rb_light -> 1
                R.id.rb_dark -> 2
                else -> 0
            }
            viewModel.setThemeMode(mode)
        }

        // ── Reader Theme ──
        binding.chipGroupReaderTheme.setOnCheckedStateChangeListener { _, checkedIds ->
            if (isInitializing) return@setOnCheckedStateChangeListener
            val theme = when (checkedIds.firstOrNull()) {
                R.id.chip_theme_dark -> 1
                R.id.chip_theme_sepia -> 2
                R.id.chip_theme_amoled -> 3
                R.id.chip_theme_custom -> 4
                else -> 0
            }
            viewModel.setReaderTheme(theme)
        }

        // ── Font Family ──
        binding.chipGroupFont.setOnCheckedStateChangeListener { _, checkedIds ->
            if (isInitializing) return@setOnCheckedStateChangeListener
            val family = when (checkedIds.firstOrNull()) {
                R.id.chip_font_serif -> "serif"
                R.id.chip_font_dyslexic -> "opendyslexic"
                R.id.chip_font_literata -> "literata"
                R.id.chip_font_merriweather -> "merriweather"
                R.id.chip_font_lato -> "lato"
                R.id.chip_font_roboto_mono -> "roboto_mono"
                else -> "system"
            }
            viewModel.setFontFamily(family)
        }

        // ── Font Size ──
        binding.sliderFontSize.addOnChangeListener { _, value, fromUser ->
            if (fromUser) viewModel.setFontSize(value)
        }

        // ── Line Spacing ──
        binding.sliderLineSpacing.addOnChangeListener { _, value, fromUser ->
            if (fromUser) viewModel.setLineSpacing(value)
        }

        // ── Margins ──
        binding.chipGroupMargin.setOnCheckedStateChangeListener { _, checkedIds ->
            if (isInitializing) return@setOnCheckedStateChangeListener
            val level = when (checkedIds.firstOrNull()) {
                R.id.chip_margin_narrow -> 0
                R.id.chip_margin_wide -> 2
                R.id.chip_margin_extra_wide -> 3
                else -> 1
            }
            viewModel.setMarginLevel(level)
        }

        // ── Text Alignment ──
        binding.chipGroupAlignment.setOnCheckedStateChangeListener { _, checkedIds ->
            if (isInitializing) return@setOnCheckedStateChangeListener
            val alignment = when (checkedIds.firstOrNull()) {
                R.id.chip_align_justified -> 1
                else -> 0
            }
            viewModel.setTextAlignment(alignment)
        }

        // ── Scroll Direction ──
        binding.chipGroupScroll.setOnCheckedStateChangeListener { _, checkedIds ->
            if (isInitializing) return@setOnCheckedStateChangeListener
            val dir = when (checkedIds.firstOrNull()) {
                R.id.chip_scroll_vertical -> 1
                else -> 0
            }
            viewModel.setScrollDirection(dir)
        }

        // ── Page Turn Animation ──
        binding.chipGroupAnimation.setOnCheckedStateChangeListener { _, checkedIds ->
            if (isInitializing) return@setOnCheckedStateChangeListener
            val anim = when (checkedIds.firstOrNull()) {
                R.id.chip_anim_slide -> 1
                R.id.chip_anim_fade -> 2
                R.id.chip_anim_none -> 3
                else -> 0
            }
            viewModel.setPageTurnAnimation(anim)
        }

        // ── Brightness ──
        binding.sliderBrightness.addOnChangeListener { _, value, fromUser ->
            if (fromUser) {
                viewModel.setBrightness(if (value <= 0f) -1f else value / 100f)
            }
        }

        // ── Toggles ──
        binding.switchKeepAwake.setOnCheckedChangeListener { _, isChecked ->
            if (isInitializing) return@setOnCheckedChangeListener
            viewModel.setKeepScreenAwake(isChecked)
        }

        binding.switchVolumeButtons.setOnCheckedChangeListener { _, isChecked ->
            if (isInitializing) return@setOnCheckedChangeListener
            viewModel.setVolumeButtonsTurn(isChecked)
        }
    }

    // ══════════════════════════════════════
    //  Observe & Apply Settings
    // ══════════════════════════════════════

    private fun observeSettings() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                // ── App Theme ──
                launch {
                    viewModel.themeMode.collect { mode ->
                        isInitializing = true
                        when (mode) {
                            1 -> binding.rbLight.isChecked = true
                            2 -> binding.rbDark.isChecked = true
                            else -> binding.rbSystem.isChecked = true
                        }
                        isInitializing = false
                    }
                }

                // ── Reader Theme ──
                launch {
                    viewModel.readerTheme.collect { theme ->
                        isInitializing = true
                        when (theme) {
                            1 -> binding.chipThemeDark.isChecked = true
                            2 -> binding.chipThemeSepia.isChecked = true
                            3 -> binding.chipThemeAmoled.isChecked = true
                            4 -> binding.chipThemeCustom.isChecked = true
                            else -> binding.chipThemeLight.isChecked = true
                        }
                        binding.layoutCustomColors.visibility =
                            if (theme == 4) View.VISIBLE else View.GONE
                        updatePreviewTheme(theme)
                        isInitializing = false
                    }
                }

                // ── Custom Colors ──
                launch {
                    viewModel.customBgColor.collect { color ->
                        updateColorSwatch(binding.viewCustomBg, color)
                        if (viewModel.readerTheme.value == 4) updatePreviewTheme(4)
                    }
                }
                launch {
                    viewModel.customTextColor.collect { color ->
                        updateColorSwatch(binding.viewCustomText, color)
                        if (viewModel.readerTheme.value == 4) updatePreviewTheme(4)
                    }
                }

                // ── Font Family ──
                launch {
                    viewModel.fontFamily.collect { family ->
                        isInitializing = true
                        when (family) {
                            "serif" -> binding.chipFontSerif.isChecked = true
                            "opendyslexic" -> binding.chipFontDyslexic.isChecked = true
                            "literata" -> binding.chipFontLiterata.isChecked = true
                            "merriweather" -> binding.chipFontMerriweather.isChecked = true
                            "lato" -> binding.chipFontLato.isChecked = true
                            "roboto_mono" -> binding.chipFontRobotoMono.isChecked = true
                            else -> binding.chipFontSystem.isChecked = true
                        }
                        binding.tvPreview.typeface =
                            FontHelper.getTypeface(requireContext(), family)
                        isInitializing = false
                    }
                }

                // ── Font Size ──
                launch {
                    viewModel.fontSize.collect { size ->
                        binding.sliderFontSize.value = size.coerceIn(12f, 32f)
                        binding.tvFontSizeValue.text = "${size.toInt()}sp"
                        binding.tvPreview.textSize = size
                    }
                }

                // ── Line Spacing ──
                launch {
                    viewModel.lineSpacing.collect { spacing ->
                        binding.sliderLineSpacing.value = spacing.coerceIn(1.0f, 2.5f)
                        binding.tvLineSpacingValue.text = String.format("%.1fx", spacing)
                        binding.tvPreview.setLineSpacing(0f, spacing)
                    }
                }

                // ── Margins ──
                launch {
                    viewModel.marginLevel.collect { level ->
                        isInitializing = true
                        when (level) {
                            0 -> binding.chipMarginNarrow.isChecked = true
                            2 -> binding.chipMarginWide.isChecked = true
                            3 -> binding.chipMarginExtraWide.isChecked = true
                            else -> binding.chipMarginNormal.isChecked = true
                        }
                        // Update preview padding
                        val paddingPx = when (level) {
                            0 -> dpToPx(8)
                            2 -> dpToPx(40)
                            3 -> dpToPx(56)
                            else -> dpToPx(24)
                        }
                        binding.tvPreview.setPadding(paddingPx, 0, paddingPx, 0)
                        isInitializing = false
                    }
                }

                // ── Text Alignment ──
                launch {
                    viewModel.textAlignment.collect { alignment ->
                        isInitializing = true
                        when (alignment) {
                            1 -> binding.chipAlignJustified.isChecked = true
                            else -> binding.chipAlignLeft.isChecked = true
                        }
                        binding.tvPreview.textAlignment = when (alignment) {
                            1 -> View.TEXT_ALIGNMENT_TEXT_START // Justified not fully supported in preview
                            else -> View.TEXT_ALIGNMENT_TEXT_START
                        }
                        isInitializing = false
                    }
                }

                // ── Scroll Direction ──
                launch {
                    viewModel.scrollDirection.collect { dir ->
                        isInitializing = true
                        when (dir) {
                            1 -> binding.chipScrollVertical.isChecked = true
                            else -> binding.chipScrollHorizontal.isChecked = true
                        }
                        isInitializing = false
                    }
                }

                // ── Page Turn Animation ──
                launch {
                    viewModel.pageTurnAnimation.collect { anim ->
                        isInitializing = true
                        when (anim) {
                            1 -> binding.chipAnimSlide.isChecked = true
                            2 -> binding.chipAnimFade.isChecked = true
                            3 -> binding.chipAnimNone.isChecked = true
                            else -> binding.chipAnimCurl.isChecked = true
                        }
                        isInitializing = false
                    }
                }

                // ── Brightness ──
                launch {
                    viewModel.brightness.collect { value ->
                        val sliderValue = if (value < 0f) 0f else (value * 100f).coerceIn(0f, 100f)
                        binding.sliderBrightness.value = sliderValue
                        binding.tvBrightnessValue.text =
                            if (value < 0f) getString(R.string.brightness_system)
                            else "${sliderValue.toInt()}%"
                    }
                }

                // ── Toggles ──
                launch {
                    viewModel.keepScreenAwake.collect { enabled ->
                        isInitializing = true
                        binding.switchKeepAwake.isChecked = enabled
                        isInitializing = false
                    }
                }
                launch {
                    viewModel.volumeButtonsTurn.collect { enabled ->
                        isInitializing = true
                        binding.switchVolumeButtons.isChecked = enabled
                        isInitializing = false
                    }
                }
            }
        }
    }

    // ══════════════════════════════════════
    //  Preview Helpers
    // ══════════════════════════════════════

    private fun updatePreviewTheme(theme: Int) {
        val bgColor: Int
        val textColor: Int

        when (theme) {
            1 -> {
                bgColor = requireContext().getColor(R.color.reader_bg_dark)
                textColor = requireContext().getColor(R.color.reader_text_dark)
            }
            2 -> {
                bgColor = requireContext().getColor(R.color.reader_bg_sepia)
                textColor = requireContext().getColor(R.color.reader_text_sepia)
            }
            3 -> {
                bgColor = requireContext().getColor(R.color.reader_bg_amoled)
                textColor = requireContext().getColor(R.color.reader_text_amoled)
            }
            4 -> {
                bgColor = try {
                    Color.parseColor(viewModel.customBgColor.value)
                } catch (e: Exception) {
                    Color.WHITE
                }
                textColor = try {
                    Color.parseColor(viewModel.customTextColor.value)
                } catch (e: Exception) {
                    Color.BLACK
                }
            }
            else -> {
                bgColor = requireContext().getColor(R.color.reader_bg_light)
                textColor = requireContext().getColor(R.color.reader_text_light)
            }
        }

        binding.previewContainer.setBackgroundColor(bgColor)
        binding.tvPreview.setTextColor(textColor)
    }

    private fun updateColorSwatch(view: View, hexColor: String) {
        try {
            val color = Color.parseColor(hexColor)
            val drawable = GradientDrawable().apply {
                shape = GradientDrawable.OVAL
                setColor(color)
                setStroke(
                    dpToPx(1),
                    requireContext().getColor(R.color.outline)
                )
            }
            view.background = drawable
        } catch (e: Exception) {
            // Invalid color format — ignore
        }
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
