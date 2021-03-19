package com.momlok.aleshop.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.momlok.aleshop.R
import com.momlok.aleshop.data.Categories

class HomeAdapter(private val listener: OnClickListener): RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private val homeList = ArrayList<Categories>()

    fun setHome(list: List<Categories>){
        homeList.clear()
        homeList.addAll(list)
        notifyDataSetChanged()
    }

    inner class HomeViewHolder(view: View) : RecyclerView.ViewHolder(view){
        init {
            view.setOnClickListener{
                listener.onClickListener(homeList[adapterPosition],adapterPosition)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.home_row, parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val image = holder.itemView.findViewById<ImageView>(R.id.homeRowImage)
        Glide.with(holder.itemView)
                .load(homeList[holder.adapterPosition].image)
                .into(image)
    }

    override fun getItemCount(): Int {
        return homeList.size
    }
}
interface OnClickListener{
    fun onClickListener(categories: Categories, position: Int)
}