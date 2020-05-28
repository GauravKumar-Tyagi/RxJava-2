package com.example.demo_rxjava_android.view.pagination

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.demo_rxjava_android.R
import kotlinx.android.synthetic.main.item_pagination.view.*
import java.util.ArrayList

class PaginationAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal var items: MutableList<String> = ArrayList()

    fun addItems(items: List<String>) {
        this.items.addAll(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pagination, parent, false)  )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder)?.bind(items?.get(position))
    }

    inner class ItemViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        fun bind(content : String){
            itemView?.textView?.text = content
        }

    }
}