package com.vk59.wegotrip_kt.ui.tour

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.vk59.wegotrip_kt.R
import com.vk59.wegotrip_kt.audio.PlayerStatus
import com.vk59.wegotrip_kt.databinding.TourFragmentBinding
import com.vk59.wegotrip_kt.ui.steps_list.StepsListFragment
import com.vk59.wegotrip_kt.ui.text_player.TextPlayerFragment

class TourFragment : Fragment() {
    private lateinit var viewModel: TourViewModel
    private lateinit var tourFragmentBinding: TourFragmentBinding
    private lateinit var stepAdapter: StepAdapter
    private lateinit var viewPagerSteps: ViewPager2
    private lateinit var runnable: Runnable
    private var stepNumber: Int = -1
    private val handler = Handler()
    private lateinit var seekBar: LinearProgressIndicator

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(TourViewModel::class.java)
        viewModel.createTour()
        return initFragment(inflater, container)
    }

    override fun onResume() {
        super.onResume()
        runnable.run()
    }

    private fun initFragment(inflater: LayoutInflater, container: ViewGroup?): View {
        tourFragmentBinding = TourFragmentBinding
                .bind(inflater.inflate(R.layout.tour_fragment, container, false))
        initViewPagerSteps()
        initButtons()
        initToolbar()
        initAudio()
        return tourFragmentBinding.root
    }

    private fun initButtons() {
        tourFragmentBinding.toolbar.setNavigationOnClickListener {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_tourFragment_to_mainFragment)
        }

        tourFragmentBinding.toolbar.setOnMenuItemClickListener {
            launchStepsListFragment()
            true
        }
    }

    private fun launchStepsListFragment() {
        val stepsListFragment = StepsListFragment(viewModel)
        stepsListFragment.show(requireFragmentManager(), "Steps list")
    }

    private fun initToolbar() {
        tourFragmentBinding.toolbar.title = viewModel.tour.name
        tourFragmentBinding.toolbar.subtitle = "Аудио-экскурсия"
    }

    private fun initAudio() {
        tourFragmentBinding.bottomLayout.cardViewLayout.setOnClickListener {
            launchTextPlayerFragment()
        }
        seekBar = tourFragmentBinding.bottomLayout.seekBar

        initAudioPanelsButtons()

        viewModel.audioStart(requireContext())
    }

    private fun audioView() {
        seekBar.max = viewModel.getDuration()
        runnable = Runnable {
            seekBar.progress = viewModel.getCurrentPosition()

            handler.postDelayed(runnable, (viewModel.speed.value?.times(500))?.toLong() ?: 500)
        }
    }


    private fun initAudioPanelsButtons() {
        val audioPanel = tourFragmentBinding.bottomLayout
        audioPanel.playButton.setOnClickListener {
            viewModel.audioPlay()
        }

        audioPanel.pauseButton.setOnClickListener {
            viewModel.audioPause()
        }

        audioPanel.moreButton.setOnClickListener {
            launchTextPlayerFragment()
        }

        viewModel.audioStatus.observeForever {
            viewAccordingToStatus(it, audioPanel.playButton, audioPanel.pauseButton)
        }

        audioPanel.forwardButton.setOnClickListener {
            audioPanel.seekBar.progress = viewModel.audioGoForward()
        }

        audioPanel.backwardButton.setOnClickListener {
            audioPanel.seekBar.progress = viewModel.audioGoBackward()
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            audioPanel.speedPlayer.setOnClickListener {
                viewModel.changeSpeed()
            }
        } else {
            audioPanel.speedPlayer.visibility = View.GONE
        }

        viewModel.speed.observe(viewLifecycleOwner) {
            tourFragmentBinding.bottomLayout.speedPlayer.text = it.toString() + "x"
        }
        audioView()
    }

    private fun viewAccordingToStatus(
            status: PlayerStatus?,
            playButton: ImageView,
            pauseButton: ImageView
    ) {
        Log.d("STATUS", status.toString())
        when (status) {
            PlayerStatus.PLAYING -> {
                audioView()
                playButton.visibility = View.GONE
                pauseButton.visibility = View.VISIBLE
            }
            PlayerStatus.PAUSED -> {
                pauseButton.visibility = View.GONE
                playButton.visibility = View.VISIBLE
            }
            PlayerStatus.STOPPED -> {
                pauseButton.visibility = View.GONE
                playButton.visibility = View.VISIBLE
            }
            else -> {

            }
        }
    }

    private fun nextPage() {
        viewModel.nextStep()
    }

    private fun launchTextPlayerFragment() {
        val textPlayerFragment = TextPlayerFragment(viewModel)
        textPlayerFragment.show(requireFragmentManager(), "Text player")
    }

    private fun initViewPagerSteps() {
        viewPagerSteps = tourFragmentBinding.viewPagerSteps
        stepAdapter = StepAdapter(this, viewModel.tour)
        viewPagerSteps.adapter = stepAdapter

        viewPagerSteps.requestDisallowInterceptTouchEvent(true)
        setOnChangedFragmentFocus()
    }

    private fun setOnChangedFragmentFocus() {
        TourViewModel.currentStepNumber.observe(viewLifecycleOwner) {
            /* Check if called onResume of the same page. */
            if (it != stepNumber) {
                stepNumber = it
                viewModel.currentStep.value = viewModel.tour.steps[it]
                changeStepNumberOnView(it)
            }
        }
    }

    private fun changeStepNumberOnView(it: Int) {
        tourFragmentBinding.stepNumber.text = "Step ${it + 1}/${viewModel.getStepsCount()}"
        tourFragmentBinding.bottomLayout.titleAudio.text = viewModel.currentStep.value!!.name
        viewModel.audioStop()
        viewModel.audioStart(requireContext())
    }
}