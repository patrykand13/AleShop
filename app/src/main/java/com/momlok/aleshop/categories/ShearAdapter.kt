package com.momlok.aleshop.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.momlok.aleshop.R
import com.momlok.aleshop.data.Items

class ShearAdapter(private val listener: OnItemsClick): RecyclerView.Adapter<ShearAdapter.ShearViewHolder>() {

     val itemsList = ArrayList<Items>()

    fun setItems(list: List<Items>){
        itemsList.clear()
        itemsList.addAll(list)
        notifyDataSetChanged()
    }

    inner class ShearViewHolder(view: View) : RecyclerView.ViewHolder(view){
        init {
            view.setOnClickListener{
                listener.onItemsClick(itemsList[adapterPosition],adapterPosition)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShearViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.items_row, parent, false)
        return ShearViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShearViewHolder, position: Int) {
        val name = holder.itemView.findViewById<TextView>(R.id.nameItemsRowTV)
        val price = holder.itemView.findViewById<TextView>(R.id.priceItemsRowTV)
        val image = holder.itemView.findViewById<ImageView>(R.id.itemsRowImage)

        name.text = itemsList[holder.adapterPosition].name
        price.text = "${itemsList[holder.adapterPosition].price.toString()} zł"
        Glide.with(holder.itemView)
                .load(itemsList[holder.adapterPosition].image)
                .into(image)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }
}
interface OnItemsClick{
    fun onItemsClick(items: Items, position: Int)
}