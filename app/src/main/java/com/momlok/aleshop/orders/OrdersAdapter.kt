package com.momlok.aleshop.orders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.momlok.aleshop.R
import com.momlok.aleshop.data.Order

class OrdersAdapter(): RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder>() {

    private val orderList = ArrayList<Order>()

    fun setItems(list: List<Order>){
        orderList.clear()
        orderList.addAll(list)
        notifyDataSetChanged()
    }

    inner class OrdersViewHolder(view: View) : RecyclerView.ViewHolder(view){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.orders_row, parent, false)
        return OrdersViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        val status = holder.itemView.findViewById<TextView>(R.id.statusOrdersRowTV)
        val items = holder.itemView.findViewById<TextView>(R.id.itemsOrdersRowTV)

        status.text = orderList[holder.adapterPosition].status
        //items.text = orderList[holder.adapterPosition].cart.toString()
    }

    override fun getItemCount(): Int {
        return orderList.size
    }
}