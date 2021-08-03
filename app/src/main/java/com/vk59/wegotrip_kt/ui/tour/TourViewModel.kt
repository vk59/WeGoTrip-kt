package com.vk59.wegotrip_kt.ui.tour

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vk59.wegotrip_kt.model.StepTour
import com.vk59.wegotrip_kt.model.Tour
import com.vk59.wegotrip_kt.repository.Repository

class TourViewModel : ViewModel() {
    lateinit var tour: Tour

    companion object {
        var currentStepNumber = MutableLiveData(0)
    }

    var currentStep: MutableLiveData<StepTour?> = MutableLiveData()

    fun getStepsCount() : Int {
        return tour.steps.size
    }

    fun createTour() {
        tour = Repository.getTour()
    }
}