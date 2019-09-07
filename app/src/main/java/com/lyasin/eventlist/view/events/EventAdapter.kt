package com.lyasin.eventlist.view.events

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lyasin.eventlist.R
import com.lyasin.eventlist.model.Event
import com.lyasin.eventlist.view.INTENT_EXTRA_ID
import com.lyasin.eventlist.view.details.DetailsActivity
import kotlinx.android.synthetic.main.view_item_event.view.*


class EventAdapter(
    private val list: MutableList<Event>
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.view_item_event, parent, false)
        return EventViewHolder(v)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) =
        holder.bind(list[position])

    class EventViewHolder(private val iv: View) : RecyclerView.ViewHolder(iv) {

        fun bind(e: Event) {
            iv.setOnClickListener {
                val intent = Intent(iv.context, DetailsActivity::class.java)
                intent.putExtra(INTENT_EXTRA_ID, e.id)
                iv.context.startActivity(intent)
            }
            iv.tv_item_event_title.text = e.title
            iv.tv_item_event_price.text = e.priceCurrency()
            iv.tv_item_event_date.text = e.convertedDate()
            Glide.with(iv).load(e.image).placeholder(R.drawable.lorem)
                .into(iv.iv_item_event)
        }
    }
}
