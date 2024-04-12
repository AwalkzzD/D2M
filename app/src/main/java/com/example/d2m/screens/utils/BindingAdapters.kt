package com.example.d2m.screens.utils

import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.example.d2m.R
import com.example.d2m.data.local.home.ServiceX
import com.example.d2m.data.local.time_slots.TimeSlots
import com.example.d2m.data.remote.otp.verify.GetCityAreaDetail
import com.example.d2m.screens.home.main.address.SelectAreaViewModel
import com.example.d2m.screens.home.main.cart.CartViewModel
import com.example.d2m.screens.home.main.checkout.CheckoutViewModel
import com.example.d2m.screens.home.main.service.ServiceViewModel
import com.squareup.picasso.Picasso

private const val TAG = "BindingAdapters"

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
fun TextView.setCartTotal(addedCartServices: List<ServiceX>) {

    var cartTotal = 0.0
    for (serviceX in addedCartServices) {
        cartTotal += serviceX.price
    }
    this.text = buildString {
        append("₹")
        append(cartTotal)
    }

}

@BindingAdapter(value = ["removeService", "fromVM"], requireAll = true)
fun ImageButton.removeFromCart(serviceX: ServiceX, cartViewModel: CartViewModel) {
    this.setOnClickListener {
        cartViewModel.removeService(serviceX)
    }
}

@BindingAdapter(value = ["setAreaDetails", "areaVM"], requireAll = true)
fun CheckBox.setAreaDetails(area: GetCityAreaDetail, areaVM: SelectAreaViewModel) {
    this.setOnClickListener {
        areaVM.area.postValue(area)
    }
}

@BindingAdapter(value = ["onTimeSlotClick", "timeSlot"], requireAll = true)
fun RadioButton.saveTimeSlot(timeSlotVM: CheckoutViewModel, timeSlot: TimeSlots) {
    this.setOnClickListener {
        timeSlotVM.checkTimeSlots(timeSlot)
    }
}