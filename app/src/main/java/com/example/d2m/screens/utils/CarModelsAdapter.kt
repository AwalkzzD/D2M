package com.example.d2m.screens.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.d2m.data.models.car.CarModel
import com.example.d2m.databinding.ModelListItemBinding

class CarModelsAdapter(
    private var modelList: MutableList<CarModel>, private val onItemClick: (CarModel) -> Unit
) : RecyclerView.Adapter<CarModelsAdapter.ViewHolder>() {

    private lateinit var binding: ModelListItemBinding

    inner class ViewHolder(
        private val binding: ModelListItemBinding, onItemClicked: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(carModel: CarModel) {
            binding.carModel = carModel
        }

        init {
            itemView.setOnClickListener {
                onItemClicked(adapterPosition)
            }
        }
    }

    fun filterList(filterlist: ArrayList<CarModel>) {
        modelList = filterlist
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ModelListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding) {
            onItemClick(modelList[it])
        }
    }

    override fun getItemCount(): Int = modelList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val carModel = modelList[position]
        holder.bind(carModel)
    }
}