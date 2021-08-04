package com.vk59.wegotrip_kt.ui.text_player

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vk59.wegotrip_kt.R
import com.vk59.wegotrip_kt.audio.AudioService
import com.vk59.wegotrip_kt.audio.PlayerStatus
import com.vk59.wegotrip_kt.databinding.SheetPlayerDescriptionBinding
import com.vk59.wegotrip_kt.ui.tour.TourViewModel


class TextPlayerFragment(private val viewModel: TourViewModel) : BottomSheetDialogFragment() {
    private lateinit var binding: SheetPlayerDescriptionBinding
    private lateinit var runnable: Runnable

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        binding = SheetPlayerDescriptionBinding
                .bind(View.inflate(context, R.layout.sheet_player_description, null))
        bottomSheet.setContentView(binding.root)
        initView()
        initAudio()

        bottomSheet.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout = bottomSheetDialog
                            .findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { it ->
                val behaviour = BottomSheetBehavior.from(it)
                setupFullHeight(it)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return bottomSheet
    }

    override fun onResume() {
        super.onResume()
        runnable.run()
    }

    private fun initAudio() {
        audioView(requireContext(), binding.seekBar, binding.textTimePassed,
            binding.textTimeLeft, Handler())
        initAudioButtons()
        viewModel.audioStatus.observeForever {
            viewAccordingToStatus(it, binding.playButton, binding.pauseButton)
        }

    }

    private fun initAudioButtons() {
        binding.playButton.setOnClickListener {
            viewModel.audioPlay()
        }

        binding.pauseButton.setOnClickListener {
            viewModel.audioPause()
        }

        binding.forwardButton.setOnClickListener {
            viewModel.audioGoForward()
        }

        binding.replayButton.setOnClickListener {
            viewModel.audioGoBackward()
        }

        binding.seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    viewModel.audioSeekTo(progress)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })
    }

    private fun viewAccordingToStatus(
        status: PlayerStatus?,
        playButton: ImageView,
        pauseButton: ImageView
    ) {
        when (status) {
            PlayerStatus.PLAYING -> {
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
            PlayerStatus.FINISHED -> {
                this.onDestroyView()
            }
        }
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }


    private fun initView() {
        viewModel.currentStep.observe(this) {
            binding.playerDescription.text = it!!.description
            binding.sheetPlayerTitle.text = it.name
        }
    }

    fun audioView(
        context: Context,
        seekBar: SeekBar,
        timePassed: TextView,
        timeLeft: TextView,
        handler: android.os.Handler
    ) {
        seekBar.max = AudioService.getFullDuration()
        runnable = Runnable {
            seekBar.progress = AudioService.getCurrentPosition()
            timePassed.text = viewModel.convertFormat(AudioService.getCurrentPosition().toLong())
            handler.postDelayed(runnable, 500)
        }
        timeLeft.text = viewModel.convertFormat(
                AudioService.getFullDuration().toLong()
        )
    }
}