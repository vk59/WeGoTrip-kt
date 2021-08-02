package com.vk59.wegotrip_kt

import com.vk59.wegotrip_kt.model.StepTour
import com.vk59.wegotrip_kt.sample.Config

class Repository {
    companion object {
        fun getConfigStepsOfTour(): ArrayList<StepTour> {
            val result = arrayListOf<StepTour>()
            for (i: Int in 0 until Config.stepTitles.size) {
                result.add(
                    StepTour(
                        Config.stepTitles[i], Config.stepsDescriptions[i],
                        Config.pictureReferences[i], Any()
                    )
                )
            }
            return result
        }

        fun getTourName() : String {
            return Config.tourName
        }
    }
}