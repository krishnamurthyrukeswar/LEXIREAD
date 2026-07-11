package com.lexiread.app.ui.dictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lexiread.app.R
import com.lexiread.app.data.local.entity.DictionaryCacheEntity
import com.lexiread.app.databinding.BottomSheetDictionaryBinding
import com.lexiread.app.domain.repository.DictionaryResult
import com.lexiread.app.utils.UiState
import com.lexiread.app.utils.hide
import com.lexiread.app.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Bottom sheet that shows dictionary definitions.
 * Works 100% offline using the bundled SQLite dictionary.
 */
@AndroidEntryPoint
class DictionaryBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetDictionaryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DictionaryViewModel by viewModels()

    private var onAddHighlight: ((String) -> Unit)? = null

    private val recentAdapter = RecentLookupAdapter { word ->
        viewModel.lookupWord(word)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetDictionaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupRecentLookups()
        observeState()

        // Look up the word passed via arguments
        val word = arguments?.getString(ARG_WORD) ?: ""
        if (word.isNotBlank()) {
            viewModel.lookupWord(word)
        }
    }

    private fun setupUI() {
        binding.btnClose.setOnClickListener { dismiss() }

        binding.btnAlternate.setOnClickListener {
            viewModel.toggleAlternateDefinitions()
        }

        binding.btnAddHighlight.setOnClickListener {
            val word = viewModel.currentWord.value
            onAddHighlight?.invoke(word)
            Toast.makeText(requireContext(), "\"$word\" added to highlights", Toast.LENGTH_SHORT).show()
        }

        binding.btnRecentLookups.setOnClickListener {
            viewModel.toggleRecentLookups()
        }
    }

    private fun setupRecentLookups() {
        binding.rvRecentLookups.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recentAdapter
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                // Lookup state
                launch {
                    viewModel.lookupState.collect { state ->
                        when (state) {
                            is UiState.Loading -> showLoading()
                            is UiState.Success -> showDefinition(state.data)
                            is UiState.Error -> showNotFound()
                            is UiState.Idle -> {}
                        }
                    }
                }

                // Current word
                launch {
                    viewModel.currentWord.collect { word ->
                        binding.tvWord.text = word
                    }
                }

                // Show alternates toggle
                launch {
                    viewModel.showAlternates.collect { show ->
                        binding.containerAlternates.visibility =
                            if (show) View.VISIBLE else View.GONE
                        binding.btnAlternate.text =
                            if (show) "Show primary definition"
                            else "Not the right meaning?"
                    }
                }

                // Show recent lookups toggle
                launch {
                    viewModel.showRecentLookups.collect { show ->
                        binding.containerRecent.visibility =
                            if (show) View.VISIBLE else View.GONE
                    }
                }

                // Recent lookups data
                launch {
                    viewModel.recentLookups.collect { lookups ->
                        recentAdapter.submitList(lookups)
                    }
                }
            }
        }
    }

    // ═══════════════════════════════════
    //  UI State Helpers
    // ═══════════════════════════════════

    private fun showLoading() {
        binding.progressLoading.show()
        binding.tvDefinition.hide()
        binding.emptyState.hide()
        binding.actionButtons.hide()
        binding.chipPos.hide()
        binding.tvPhonetic.hide()
        binding.btnAlternate.hide()
        binding.containerAlternates.hide()
    }

    private fun showDefinition(result: DictionaryResult) {
        binding.progressLoading.hide()
        binding.emptyState.hide()

        binding.tvWord.text = result.word
        binding.tvDefinition.text = result.definition
        binding.tvDefinition.show()
        binding.actionButtons.show()

        // Phonetic
        if (!result.phonetic.isNullOrEmpty()) {
            binding.tvPhonetic.text = result.phonetic
            binding.tvPhonetic.show()
        } else {
            binding.tvPhonetic.hide()
        }

        // Part of speech
        if (!result.partOfSpeech.isNullOrEmpty()) {
            binding.chipPos.text = result.partOfSpeech
            binding.chipPos.show()
        } else {
            binding.chipPos.hide()
        }

        // Alternate definitions
        if (result.alternateDefinitions.isNotEmpty()) {
            binding.btnAlternate.show()
            binding.containerAlternates.removeAllViews()
            result.alternateDefinitions.forEachIndexed { index, alt ->
                val tv = TextView(requireContext()).apply {
                    text = "${index + 2}. $alt"
                    setTextAppearance(com.google.android.material.R.style.TextAppearance_Material3_BodyMedium)
                    setTextColor(context.getColor(R.color.on_surface))
                    setPadding(0, 8, 0, 8)
                }
                binding.containerAlternates.addView(tv)
            }
        } else {
            binding.btnAlternate.hide()
        }
    }

    private fun showNotFound() {
        binding.progressLoading.hide()
        binding.tvDefinition.hide()
        binding.actionButtons.hide()
        binding.chipPos.hide()
        binding.tvPhonetic.hide()
        binding.btnAlternate.hide()
        binding.containerAlternates.hide()
        binding.emptyState.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "DictionaryBottomSheet"
        private const val ARG_WORD = "arg_word"

        fun newInstance(
            word: String,
            onHighlight: ((String) -> Unit)? = null
        ): DictionaryBottomSheet {
            return DictionaryBottomSheet().apply {
                arguments = Bundle().apply {
                    putString(ARG_WORD, word)
                }
                onAddHighlight = onHighlight
            }
        }
    }
}

// ═══════════════════════════════════
//  Recent Lookups Adapter
// ═══════════════════════════════════

class RecentLookupAdapter(
    private val onItemClick: (String) -> Unit
) : ListAdapter<DictionaryCacheEntity, RecentLookupAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<DictionaryCacheEntity>() {
        override fun areItemsTheSame(old: DictionaryCacheEntity, new: DictionaryCacheEntity) =
            old.word == new.word
        override fun areContentsTheSame(old: DictionaryCacheEntity, new: DictionaryCacheEntity) =
            old == new
    }
) {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvWord: TextView = itemView.findViewById(R.id.tv_recent_word)
        val tvDefinition: TextView = itemView.findViewById(R.id.tv_recent_definition)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recent_lookup, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.tvWord.text = item.word
        holder.tvDefinition.text = item.definition
        holder.itemView.setOnClickListener { onItemClick(item.word) }
    }
}
