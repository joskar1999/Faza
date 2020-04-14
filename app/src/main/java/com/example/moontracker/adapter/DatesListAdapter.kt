package com.example.moontracker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moontracker.R
import kotlinx.android.synthetic.main.date_item_list_cell.view.*

class DatesListAdapter(private val items: ArrayList<String>, private val context: Context) :
    RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.date_item_list_cell, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.date.text = items[position]
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val date: TextView = view.date_text
}