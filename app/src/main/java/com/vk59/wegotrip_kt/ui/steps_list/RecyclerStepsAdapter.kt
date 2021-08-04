package com.vk59.wegotrip_kt.ui.steps_list

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.vk59.wegotrip_kt.R
import com.vk59.wegotrip_kt.databinding.StepItemBinding
import com.vk59.wegotrip_kt.model.Tour

class RecyclerStepsAdapter(private val tour: Tour) : RecyclerView.Adapter<RecyclerStepsAdapter.StepsViewHolder>() {
    private val steps = tour.steps

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepsViewHolder {
        val binding: StepItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.step_item,
                parent,
                false
            )
        return StepsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StepsViewHolder, position: Int) {
        holder.binding.step = steps[position]
        holder.binding.pos = position + 1
        holder.binding.tour = tour
    }

    override fun getItemCount(): Int {
        return tour.steps.size
    }

    class StepsViewHolder(val binding: StepItemBinding) : RecyclerView.ViewHolder(binding.root)
}