package com.example.d2m.screens.utils

import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import com.example.d2m.R
import com.example.d2m.data.local.home.ServiceX
import com.example.d2m.screens.home.main.service.ServiceViewModel
import com.squareup.picasso.Picasso

@BindingAdapter("imageUrl")
fun ImageView.loadImage(url: String?) {
    Picasso.get().load(url?.toUri()).placeholder(R.drawable.app_placeholder).fit().into(this)
}

@BindingAdapter("hrsText")
fun TextView.convertTimeToHrs(text: String?) {

    if (text != null) {
        val parts = text.split(":")
        val hours = parts[0].toInt()
        val minutes = parts[1].toInt()

        if (minutes < 1) this.text = buildString {
            append("Duration: ")
            append(hours)
            append("hrs*")
        }
        else this.text = buildString {
            append("Duration: ")
            append(hours)
            append("hrs ")
            append(minutes)
            append("min*")
        }
    }

}

@BindingAdapter("recommendedText")
fun TextView.setRecommendedDuration(text: Int?) {

    this.text = buildString {
        append("Recommended: Every ")
        append(text)
        append(" months")
    }

}

@BindingAdapter("priceText")
fun TextView.setPriceText(text: Double?) {

    this.apply {
        setText(buildString {
            append("₹ ")
            append(text?.toInt() ?: "Not Specified")
        })
        setTextColor(ContextCompat.getColor(context, R.color.green))
    }

}

@BindingAdapter(value = ["addService", "toVM"], requireAll = true)
fun Button.addToCart(serviceX: ServiceX, serviceViewModel: ServiceViewModel) {

    this.text = if (serviceViewModel.addedServiceX.size == 0) {
        "Add"
    } else {
        if (serviceViewModel.addedServiceX.contains(serviceX)) {
            "Remove"
        } else {
            "Add"
        }
    }
    this.setOnClickListener {
        text = if (serviceViewModel.addedServiceX.contains(serviceX)) {
            serviceViewModel.removeService(serviceX)
            "Add"
        } else {
            serviceViewModel.addService(serviceX)
            "Remove"
        }
    }

}

@BindingAdapter("cartTotal")
fun TextView.setCartTotal(addedServiceX: ObservableArrayList<ServiceX>) {

    var cartTotal = 0.0
    for (serviceX in addedServiceX) {
        cartTotal += serviceX.price
    }
    this.text = buildString {
        append("₹")
        append(cartTotal)
    }

}

@BindingAdapter("removeAll")
fun ImageButton.removeAllService(serviceViewModel: ServiceViewModel) {

    this.setOnClickListener {
        serviceViewModel.removeAllServices()
    }

}