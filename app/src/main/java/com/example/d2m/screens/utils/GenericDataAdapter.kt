package com.example.d2m.screens.utils

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

class GenericDataAdapter<T : Any>(
    private val context: Activity,
    @LayoutRes val layoutID: Int,
    private val onItemClick: (T) -> Unit,
    private val bindFunction: (item: T, itemView: View) -> Unit
) : RecyclerView.Adapter<GenericDataAdapter.ViewHolder>() {

    private var dataList: MutableList<T> = emptyList<T>().toMutableList()

    fun addData(data: List<T>) {
        this.dataList.addAll(data)
    }

    class ViewHolder(itemView: View, onItemClicked: (Int) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                onItemClicked(adapterPosition)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(layoutID, parent, false)
        return ViewHolder(itemView) {
            onItemClick(dataList[it])
        }
    }

    fun filterList(filterList: ArrayList<T>) {
        dataList = filterList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        bindFunction(item, holder.itemView)
    }
}