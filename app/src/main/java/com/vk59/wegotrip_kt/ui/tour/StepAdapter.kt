package com.vk59.wegotrip_kt.ui.tour

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vk59.wegotrip_kt.model.Tour
import com.vk59.wegotrip_kt.ui.tour.step.StepFragment

class StepAdapter(fragment: Fragment, var tour: Tour) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return tour.steps.size
    }

    override fun createFragment(position: Int): Fragment {
        return StepFragment.newInstance(tour.steps[position], position)
    }
}