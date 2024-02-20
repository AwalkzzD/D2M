package com.example.d2m.screens.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.d2m.R
import com.example.d2m.data.models.car.brand.CarBrand
import com.squareup.picasso.Picasso

class CarBrandsAdapter(private val brandList: MutableList<CarBrand>) :
    RecyclerView.Adapter<CarBrandsAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val brandImage: ImageView = itemView.findViewById<ImageView>(R.id.brandImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.brand_list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = brandList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(brandList[position].carBrandImage)
            .placeholder(R.drawable.app_placeholder).into(holder.brandImage)
    }
}