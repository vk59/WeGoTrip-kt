package com.vk59.wegotrip_kt.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.vk59.wegotrip_kt.R
import com.vk59.wegotrip_kt.databinding.MainFragmentBinding

class MainFragment : Fragment() {
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val mainFragmentBinding = MainFragmentBinding
                .bind(inflater.inflate(R.layout.main_fragment, container, false))
        val navController = NavHostFragment.findNavController(this)

        mainFragmentBinding.buttonStart.setOnClickListener { navController
            .navigate(R.id.action_mainFragment_to_tourFragment) }

        return mainFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

}