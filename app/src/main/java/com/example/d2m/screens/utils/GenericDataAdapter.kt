package com.example.d2m.screens.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.d2m.BR
import com.example.d2m.data.models.car.CarBrand
import com.example.d2m.data.models.car.CarModel
import com.example.d2m.data.models.car.FuelType
import com.example.d2m.data.models.home.Banner
import com.example.d2m.data.models.home.Service
import com.example.d2m.data.models.home.ServiceX

class GenericDataAdapter<T : Any>(
    private var dataList: List<T>,
    @LayoutRes val layoutID: Int,
    private val onItemClick: (T) -> Unit,
) : RecyclerView.Adapter<GenericDataAdapter<T>.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(inflater, layoutID, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    fun filterList(filterList: List<T>) {
        dataList = filterList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = dataList.size

    inner class ViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: T) {
            when (item) {
                is CarBrand -> {
                    binding.setVariable(BR.carBrand, item)
                }

                is CarModel -> {
                    binding.setVariable(BR.carModel, item)
                }

                is FuelType -> {
                    binding.setVariable(BR.fuelType, item)
                }

                is Banner -> {
                    binding.setVariable(BR.banners, item)
                }

                is Service -> {
                    binding.setVariable(BR.services, item)
                }

                is ServiceX -> {
                    binding.setVariable(BR.serviceX, item)
                }
            }
            binding.executePendingBindings()
        }
    }
}