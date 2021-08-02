package com.vk59.wegotrip_kt.ui.tour

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.viewpager2.widget.ViewPager2
import com.vk59.wegotrip_kt.R
import com.vk59.wegotrip_kt.databinding.TourFragmentBinding

class TourFragment : Fragment() {
    private lateinit var viewModel: TourViewModel
    private lateinit var tourFragmentBinding: TourFragmentBinding
    private lateinit var stepAdapter: StepAdapter
    private lateinit var viewPagerSteps: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(TourViewModel::class.java)
        viewModel.createTour()
        return initFragment(inflater, container)
    }

    private fun initFragment(inflater: LayoutInflater, container: ViewGroup?) : View {
        tourFragmentBinding = TourFragmentBinding
            .bind(inflater.inflate(R.layout.tour_fragment, container, false))
        initViewPagerSteps(tourFragmentBinding.root)
        initButtons()

        return tourFragmentBinding.root
    }

    private fun initButtons() {
        tourFragmentBinding.closeButton.setOnClickListener {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_tourFragment_to_mainFragment)
        }
    }

    private fun initViewPagerSteps(view: View) {
        viewPagerSteps = tourFragmentBinding.viewPagerSteps
        stepAdapter = StepAdapter(this, viewModel.tour)
        viewPagerSteps.adapter = stepAdapter

        viewPagerSteps.requestDisallowInterceptTouchEvent(true)
        setOnChangedFragmentFocus()
    }

    private fun setOnChangedFragmentFocus() {
        TourViewModel.currentStepNumber.observe(viewLifecycleOwner) {
            changeStepNumberOnView(it)
        }
    }

    private fun changeStepNumberOnView(it: Int) {
        tourFragmentBinding.stepNumber.text = "Step ${it + 1}/${viewModel.getStepsCount()}"
    }
}