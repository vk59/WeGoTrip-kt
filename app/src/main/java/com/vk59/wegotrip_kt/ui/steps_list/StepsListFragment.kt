package com.vk59.wegotrip_kt.ui.steps_list

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vk59.wegotrip_kt.R
import com.vk59.wegotrip_kt.databinding.StepsListFragmentBinding
import com.vk59.wegotrip_kt.ui.tour.TourViewModel

class StepsListFragment(private val viewModel: TourViewModel) : BottomSheetDialogFragment() {
    private lateinit var binding: StepsListFragmentBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        binding = StepsListFragmentBinding
            .bind(View.inflate(context, R.layout.steps_list_fragment, null))
        bottomSheet.setContentView(binding.root)
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

        initRecycler()
        return bottomSheet
    }

    private fun initRecycler() {
        binding.stepsRecycler.adapter = RecyclerStepsAdapter(viewModel.tour)
        binding.stepsRecycler.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }
}