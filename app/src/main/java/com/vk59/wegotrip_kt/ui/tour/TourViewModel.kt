package com.vk59.wegotrip_kt.ui.tour

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ReportFragment
import androidx.lifecycle.ViewModel
import com.vk59.wegotrip_kt.Repository
import com.vk59.wegotrip_kt.model.StepTour
import com.vk59.wegotrip_kt.model.Tour
import com.vk59.wegotrip_kt.sample.Config

class TourViewModel : ViewModel() {
    lateinit var tour: Tour

    companion object {
        var currentStepNumber = MutableLiveData(0)
    }

    fun getStepsCount() : Int {
        return tour.steps.size
    }

    fun createTour() {
        val steps = Repository.getConfigStepsOfTour()
        tour = Tour(Repository.getTourName(), steps)
    }
}