package com.lexiread.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lexiread.app.databinding.BottomSheetImportBinding

/**
 * Bottom sheet with import options:
 * - From Device Storage (triggers file picker)
 * - From Google Drive (disabled until cloud setup is configured)
 */
class ImportBottomSheet(
    private val onDeviceStorageClick: () -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: BottomSheetImportBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetImportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cardDeviceStorage.setOnClickListener {
            onDeviceStorageClick()
            dismiss()
        }

        binding.cardGoogleDrive.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Google Drive import requires cloud setup",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "ImportBottomSheet"
    }
}
