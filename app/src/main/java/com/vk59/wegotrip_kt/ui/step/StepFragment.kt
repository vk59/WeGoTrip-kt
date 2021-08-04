package com.vk59.wegotrip_kt.ui.step

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vk59.wegotrip_kt.R
import com.vk59.wegotrip_kt.databinding.StepFragmentBinding
import com.vk59.wegotrip_kt.model.StepTour
import com.vk59.wegotrip_kt.ui.tour.TourViewModel

class StepFragment : Fragment() {
    private lateinit var step: StepTour
    private var stepNumber = 0
    private lateinit var binding: StepFragmentBinding

    companion object {
        const val ARGUMENT_STEP = "arg_step"
        const val ARGUMENT_STEP_NUMBER = "arg_step_number"
        fun newInstance(step: StepTour, stepNumber: Int) : StepFragment {
            val stepFragment = StepFragment()
            val args = Bundle()
            args.putSerializable(ARGUMENT_STEP, step)
            args.putInt(ARGUMENT_STEP_NUMBER, stepNumber)
            stepFragment.arguments = args
            return stepFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        step = arguments?.getSerializable(ARGUMENT_STEP)!! as StepTour
        stepNumber = arguments?.getInt(ARGUMENT_STEP_NUMBER)!!
        binding = StepFragmentBinding
                .bind(inflater.inflate(R.layout.step_fragment, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initStepView()
    }

    private fun initStepView() {
        binding.step = step
        setPicturesToView()
    }

    private fun setPicturesToView() {
        val pictures = step.picturesLinks
        binding.viewPagerPictures.adapter = PicturesAdapter(pictures)
        binding.viewPagerPictures.bringToFront()
    }

    override fun onResume() {
        super.onResume()
        TourViewModel.currentStepNumber.value = stepNumber
    }
}