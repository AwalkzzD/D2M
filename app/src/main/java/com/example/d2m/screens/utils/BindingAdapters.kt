package com.example.d2m.screens.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.example.d2m.R
import com.squareup.picasso.Picasso

@BindingAdapter("imageUrl")
fun ImageView.loadImage(url: String) {
    Picasso.get().load(url.toUri()).placeholder(R.drawable.app_placeholder).fit().into(this)
}

@BindingAdapter("hrsText")
fun TextView.convertTimeToHrs(text: String) {

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

@BindingAdapter("recommendedText")
fun TextView.setRecommendedDuration(text: Int) {

    this.text = buildString {
        append("Recommended: Every ")
        append(text)
        append(" months")
    }

}

@BindingAdapter("priceText")
fun TextView.setPriceText(text: Double) {

    this.apply {
        setText(buildString {
            append("₹ ")
            append(text.toInt() ?: "Unspecified")
        })
        setTextColor(ContextCompat.getColor(context, R.color.green))
    }

}
