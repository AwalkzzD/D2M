package com.example.d2m.screens.utils.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.d2m.BR
import com.example.d2m.data.local.car.CarBrand
import com.example.d2m.data.local.car.CarModel
import com.example.d2m.data.local.car.FuelType
import com.example.d2m.data.local.home.Banner
import com.example.d2m.data.local.home.Service
import com.example.d2m.data.local.home.ServiceX
import com.example.d2m.data.local.orders.DataX
import com.example.d2m.data.local.time_slots.TimeSlots
import com.example.d2m.data.remote.otp.verify.GetCityAreaDetail
import com.example.d2m.screens.utils.base_classes.BaseViewModel

class GenericDataAdapter<T : Any>(
    private var dataList: List<T>,
    @LayoutRes val layoutID: Int,
    private val onItemClick: (T) -> Unit,
) : RecyclerView.Adapter<GenericDataAdapter<T>.ViewHolder>() {

    lateinit var viewModel: BaseViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ViewDataBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutID, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onItemClick(item)
        }

    }

    fun setVM(viewModel: BaseViewModel) {
        this.viewModel = viewModel
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
                    binding.setVariable(BR.serviceVM, viewModel)
                    binding.setVariable(BR.cartVM, viewModel)
                }

                is GetCityAreaDetail -> {
                    binding.setVariable(BR.area, item)
                    binding.setVariable(BR.selectAreaVM, viewModel)
                }

                is TimeSlots -> {
                    binding.setVariable(BR.timeSlots, item)
                    binding.setVariable(BR.timeSlotVM, viewModel)
                }

                is DataX -> {
                    binding.setVariable(BR.order, item)
                }
            }
            binding.executePendingBindings()
        }
    }
}