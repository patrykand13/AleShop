package com.momlok.aleshop.categories

import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.momlok.aleshop.R
import com.momlok.aleshop.data.Items

class ShearAdapter(private val listener: OnItemsLongClick): RecyclerView.Adapter<ShearAdapter.ShearViewHolder>() {

     val itemsList = ArrayList<Items>()

    fun setItems(list: List<Items>){
        itemsList.clear()
        itemsList.addAll(list)
        notifyDataSetChanged()
    }

    fun removeCartItem(items: Items, position: Int){
        itemsList.remove(items)
        notifyItemRemoved(position)
    }
    fun removeCart(){
        itemsList.clear()
        notifyDataSetChanged()
    }

    inner class ShearViewHolder(view: View) : RecyclerView.ViewHolder(view){
        init {
            view.setOnLongClickListener{
                listener.onItemsLongClick(itemsList[adapterPosition],adapterPosition)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShearViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.shear_row, parent, false)
        return ShearViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShearViewHolder, position: Int) {
        val name = holder.itemView.findViewById<TextView>(R.id.nameShearRowTV)
        val categories = holder.itemView.findViewById<TextView>(R.id.categoriesShearRowTV)
        val image = holder.itemView.findViewById<ImageView>(R.id.shearRowImage)

        name.text = itemsList[holder.adapterPosition].name
        categories.text = itemsList[holder.adapterPosition].categories
        Glide.with(holder.itemView)
                .load(itemsList[holder.adapterPosition].image)
                .into(image)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }
}
interface OnItemsLongClick{
    fun onItemsLongClick(items: Items, position: Int)
}