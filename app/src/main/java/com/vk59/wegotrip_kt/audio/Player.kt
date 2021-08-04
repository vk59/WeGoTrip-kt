package com.vk59.wegotrip_kt.audio

interface Player {
    fun play()

    fun pause()

    fun goForward() : Int

    fun goBackward() : Int
}