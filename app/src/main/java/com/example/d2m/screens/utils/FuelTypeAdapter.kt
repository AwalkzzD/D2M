package com.example.d2m.screens.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.d2m.data.models.car.FuelType
import com.example.d2m.databinding.FuelListItemBinding

class FuelTypeAdapter(
    private var fuelTypeList: MutableList<FuelType>, private val onItemClick: (FuelType) -> Unit
) : RecyclerView.Adapter<FuelTypeAdapter.ViewHolder>() {

    private lateinit var binding: FuelListItemBinding

    inner class ViewHolder(
        private val binding: FuelListItemBinding, onItemClicked: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(fuelType: FuelType) {
            binding.fuelType = fuelType
        }

        init {
            itemView.setOnClickListener {
                onItemClicked(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = FuelListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding) {
            onItemClick(fuelTypeList[it])
        }
    }

    override fun getItemCount(): Int = fuelTypeList.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fuelType = fuelTypeList[position]
        holder.bind(fuelType)
    }
}