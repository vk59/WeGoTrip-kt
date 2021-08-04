package com.vk59.wegotrip_kt.ui.tour

import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vk59.wegotrip_kt.audio.AudioService
import com.vk59.wegotrip_kt.audio.PlayerStatus
import com.vk59.wegotrip_kt.model.StepTour
import com.vk59.wegotrip_kt.model.Tour
import com.vk59.wegotrip_kt.repository.Repository
import java.util.concurrent.TimeUnit

class TourViewModel : ViewModel() {
    lateinit var tour: Tour
    var audioStatus = MutableLiveData(PlayerStatus.STOPPED)
    var speed = MutableLiveData(1f)

    var currentStep: MutableLiveData<StepTour?> = MutableLiveData()

    private lateinit var runnable: Runnable

    companion object {
        var currentStepNumber = MutableLiveData(0)
    }

    fun getStepsCount(): Int {
        return tour.steps.size
    }

    fun createTour() {
        tour = Repository.getTour()
    }

    fun audioStart(context: Context) {
        if (AudioService.status.value != PlayerStatus.PLAYING) {
            currentStep.value?.audio?.let {
                AudioService.initNewPlayer(context, it)
            }
        }

        AudioService.status.observeForever {
            if (it == PlayerStatus.FINISHED) {
                audioStatus.value = PlayerStatus.FINISHED
            }
        }
    }

    fun convertFormat(duration: Long): String {
        return String.format(
            "%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(duration),
            TimeUnit.MILLISECONDS.toSeconds(duration) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))
        )
    }

    fun audioPlay() {
        AudioService.play()
        audioStatus.value = PlayerStatus.PLAYING
    }

    fun audioPause() {
        AudioService.pause()
        audioStatus.value = PlayerStatus.PAUSED
    }

    fun audioGoForward(): Int {
        return AudioService.goForward()
    }

    fun audioGoBackward(): Int {
        return AudioService.goBackward()
    }

    fun audioStop() {
        AudioService.pause()
        audioStatus.value = PlayerStatus.STOPPED
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun changeSpeed() {
        speed.value = AudioService.changeSpeed()
    }

    fun audioSeekTo(position: Int) {
        AudioService.seekTo(position)
    }

    fun getAudioPlayer(): MediaPlayer {
        return AudioService.player!!
    }

    fun nextStep() {
        currentStepNumber.value = currentStepNumber.value?.plus(1)?.rem(tour.steps.size)
    }

    fun getDuration() : Int {
        return AudioService.getFullDuration()
    }


    fun getCurrentPosition() : Int {
        return AudioService.getCurrentPosition()
    }
}