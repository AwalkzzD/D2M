package com.example.d2m.screens.utils.calendar_utils.selection

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import com.example.d2m.screens.utils.calendar_utils.calendar.SingleRowCalendarAdapter


class CalendarDetailsLookup(private val recyclerView: RecyclerView) :
    ItemDetailsLookup<Long>() {
    override fun getItemDetails(event: MotionEvent): ItemDetails<Long>? {
        val view = recyclerView.findChildViewUnder(event.x, event.y)
        if (view != null) {
            return (recyclerView.getChildViewHolder(view) as SingleRowCalendarAdapter.CalendarViewHolder).getItemDetails()
        }
        return null
    }
}