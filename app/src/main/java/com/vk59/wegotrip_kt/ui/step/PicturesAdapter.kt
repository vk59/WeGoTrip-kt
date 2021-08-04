package com.vk59.wegotrip_kt.ui.step

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vk59.wegotrip_kt.R

class PicturesAdapter(private val picturesReferences: ArrayList<String>) : RecyclerView.Adapter<PagerVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH =
        PagerVH(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.photo_item, parent, false))

    override fun onBindViewHolder(holder: PagerVH, position: Int) {
        Glide
            .with(holder.itemView)
            .load(picturesReferences[position])
            .into(holder.itemView.findViewById(R.id.photoItem))
    }

    override fun getItemCount(): Int {
        return picturesReferences.size
    }
}

class PagerVH(itemView: View) : RecyclerView.ViewHolder(itemView)