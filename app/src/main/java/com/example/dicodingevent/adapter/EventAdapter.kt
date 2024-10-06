package com.example.dicodingevent.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodingevent.ui.detail.DetailEventActivity
import com.example.dicodingevent.R
import com.example.dicodingevent.data.api.response.ListEventsItem
import de.hdodenhof.circleimageview.CircleImageView

class EventAdapter(private val eventList: List<ListEventsItem>) : RecyclerView.Adapter<EventAdapter.EventViewHolder>(){
    class EventViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        val nameEvent:TextView = itemView.findViewById(R.id.event_fill)
        val photoEvent:CircleImageView = itemView.findViewById(R.id.photos_event)
        val sumaryFill:TextView = itemView.findViewById(R.id.sumary_fill)
        val category_fill:TextView = itemView.findViewById(R.id.category_fill)
        val button_detail:Button = itemView.findViewById(R.id.move_detail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_event,parent,false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = eventList[position]
        holder.nameEvent.text = event.name
        holder.sumaryFill.text = event.summary
        holder.category_fill.text = event.category
        Glide.with(holder.photoEvent.context).load(event.imageLogo).into(holder.photoEvent)

        holder.button_detail.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailEventActivity::class.java)
            intent.putExtra("id", event.id.toString())
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return eventList.size
    }
}