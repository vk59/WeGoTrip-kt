package com.vk59.wegotrip_kt.audio

import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData

object AudioService : Player {
    var player: MediaPlayer? = null
    var status = MutableLiveData(PlayerStatus.STOPPED)
    private val speeds = floatArrayOf(0.75f, 1f, 1.25f, 1.5f)
    private var currentSpeed = 1

    fun initNewPlayer(context: Context, file: Int) {
        player = MediaPlayer.create(context, file)
        player!!.setOnCompletionListener {
            status.value = PlayerStatus.FINISHED
        }
    }

    fun getCurrentPosition(): Int {
        return player?.currentPosition ?: 0
    }

    fun getFullDuration() : Int {
        return player?.duration ?: 0
    }

    fun getProgress() : Int {
        return (player?.currentPosition ?: 0) / (player?.duration ?: 1)
    }

    override fun play() {
        if (player?.isPlaying != true) {
            player?.start()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                player!!.playbackParams = player!!.playbackParams.setSpeed(speeds[currentSpeed])
            }
            status.value = PlayerStatus.PLAYING
        }
    }

    override fun pause() {
        player?.pause()
        status.value = PlayerStatus.PAUSED
    }

    override fun goForward() : Int {
        var current = getCurrentPosition()
        val duration = getFullDuration()
        if (player != null && player!!.isPlaying && duration != current) {
            current += 5000
            player!!.seekTo(current)
        }
        return current
    }

    override fun goBackward() : Int {
        var current = getCurrentPosition()
        if (player != null && player!!.isPlaying && 0 != current) {
            current -= 5000
            player!!.seekTo(current)
        }
        return current
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun changeSpeed() : Float {
        currentSpeed = (currentSpeed + 1) % 4
        player!!.playbackParams = player!!.playbackParams.setSpeed(speeds[currentSpeed])
        if (status.value == PlayerStatus.STOPPED || status.value == PlayerStatus.PAUSED) {
            pause()
        }

        return speeds[currentSpeed]
    }

    fun seekTo(position: Int) {
        player!!.seekTo(position)
    }

}