package com.example.d2m.screens.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.d2m.R
import com.example.d2m.databinding.GetStartedListItemBinding

class IntroPagerAdapter() : RecyclerView.Adapter<IntroPagerAdapter.ViewHolder>() {
    private val images = arrayListOf(R.drawable.getstarted, R.drawable.getstarted2)

    class ViewHolder(val binding: GetStartedListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        GetStartedListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int = 2

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            binding.apply {
                this.introImageView.setImageResource(images[position])
            }
        }
    }
}
