package com.example.d2m.screens.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.d2m.data.models.car.CarBrand
import com.example.d2m.databinding.BrandListItemBinding

class CarBrandsAdapter(
    private var brandList: MutableList<CarBrand>,
    private val onItemClick: (CarBrand) -> Unit
) :
    RecyclerView.Adapter<CarBrandsAdapter.ViewHolder>() {

    private lateinit var binding: BrandListItemBinding

    inner class ViewHolder(
        private val binding: BrandListItemBinding,
        onItemClicked: (Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(carBrand: CarBrand) {
            binding.carBrand = carBrand
        }

        init {
            itemView.setOnClickListener {
                onItemClicked(adapterPosition)
            }
        }
    }

    fun filterList(filterlist: ArrayList<CarBrand>) {
        brandList = filterlist
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = BrandListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding) {
            onItemClick(brandList[it])
        }
    }

    override fun getItemCount(): Int = brandList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val carBrand = brandList[position]
        holder.bind(carBrand)
    }
}