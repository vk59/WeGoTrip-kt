package com.vk59.wegotrip_kt.model

import java.io.Serializable

class StepTour(var name: String,
               var description: String,
               var picturesLinks: ArrayList<String>,
               var audio: Any) : Serializable