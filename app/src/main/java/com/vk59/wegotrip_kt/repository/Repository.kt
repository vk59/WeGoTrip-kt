package com.vk59.wegotrip_kt.repository

import com.vk59.wegotrip_kt.R
import com.vk59.wegotrip_kt.model.StepTour
import com.vk59.wegotrip_kt.model.Tour
import com.vk59.wegotrip_kt.sample.Config

class Repository {
    companion object {
        private fun getConfigStepsOfTour(): ArrayList<StepTour> {
            val result = arrayListOf<StepTour>()
            for (i: Int in 0 until Config.stepTitles.size) {
                result.add(
                    StepTour(
                        Config.stepTitles[i], Config.stepsDescriptions[i],
                        Config.pictureReferences[i],
                        when (i) {
                            0 -> R.raw.mozart_symph_41_1
                            1 -> R.raw.mozart_symph_41_2
                            2 -> R.raw.mozart_symph_41_4
                            else -> R.raw.mozart_symph_41_4
                        }
                    )
                )
            }
            return result
        }

        private fun getTourName(): String {
            return Config.tourName
        }

        fun getTour(): Tour {
            val steps = getConfigStepsOfTour()
            return Tour(getTourName(), steps)
        }
    }
}