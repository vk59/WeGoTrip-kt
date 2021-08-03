package com.vk59.wegotrip_kt.ui.tour.text_player

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vk59.wegotrip_kt.R
import com.vk59.wegotrip_kt.databinding.SheetPlayerDescriptionBinding
import com.vk59.wegotrip_kt.ui.tour.TourViewModel


class TextPlayerFragment(private val viewModel: TourViewModel) : BottomSheetDialogFragment() {
    private lateinit var binding: SheetPlayerDescriptionBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        binding = SheetPlayerDescriptionBinding
                .bind(View.inflate(context, R.layout.sheet_player_description, null))
        bottomSheet.setContentView(binding.root)
        initView()

        bottomSheet.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout = bottomSheetDialog
                            .findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { it ->
                val behaviour = BottomSheetBehavior.from(it)
                setupFullHeight(it)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return bottomSheet
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }


    private fun initView() {
        viewModel.currentStep.observe(this) {
            binding.playerDescription.text = it!!.description
        }
    }
}