package com.momlok.aleshop.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.momlok.aleshop.R
import com.momlok.aleshop.categories.OnItemsClick
import com.momlok.aleshop.data.Items

class CartAdapter(private val listener: OnItemsClickCart): RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    val itemsList = ArrayList<Items>()
    var numberOfItemList: List<Any> = emptyList<Int>()
    var priceAll = 0.0

    fun setList(list: List<Items>,number: Collection<Any>){
        itemsList.clear()
        itemsList.addAll(list)
        numberOfItemList = emptyList<Int>()
        numberOfItemList = number.toList().asReversed()
        notifyDataSetChanged()
        for (i in 0..itemsList.size-1){
            priceAll += (itemsList[i].price*numberOfItemList!![i].toString().toInt())
        }
    }

    fun removeCartItem(items: Items, position: Int){
        itemsList.remove(items)
        notifyItemRemoved(position)
    }
    fun removeCart(){
        itemsList.clear()
        notifyDataSetChanged()
    }

    inner class CartViewHolder(view: View) : RecyclerView.ViewHolder(view){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.cart_row, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val name = holder.itemView.findViewById<TextView>(R.id.nameCartRowTV)
        val price = holder.itemView.findViewById<TextView>(R.id.priceCartRowTV)
        val image = holder.itemView.findViewById<ImageView>(R.id.cartRowImage)
        val number = holder.itemView.findViewById<TextView>(R.id.numberCartRowTV)
        val toPay = holder.itemView.findViewById<TextView>(R.id.toPayCartRowTV)
        val plus = holder.itemView.findViewById<Button>(R.id.plusCartRowBT)
        val minus = holder.itemView.findViewById<Button>(R.id.minusCartRowBT)
        val delete = holder.itemView.findViewById<Button>(R.id.deleteCartRowBT)
        var updateNumber = 0
        var p = itemsList[holder.adapterPosition].price * numberOfItemList!![holder.adapterPosition].toString().toInt()

        delete.setOnClickListener {
            listener.onDeleteItemClickCart(itemsList[holder.adapterPosition],holder.adapterPosition)
            true
        }


        name.text = itemsList[holder.adapterPosition].name
        price.text = "Sztuka: ${itemsList[holder.adapterPosition].price}zł"
        number.text = "Ilość sztuk: ${numberOfItemList!![holder.adapterPosition]}"
        toPay.text = "Razem: %.2f zł".format(p)
        minus.setOnClickListener {
            if(numberOfItemList!![holder.adapterPosition].toString().toInt() > 1) {
                updateNumber = numberOfItemList!![holder.adapterPosition].toString().toInt()
                updateNumber--
                listener.onUpdateItemClickCart(itemsList[holder.adapterPosition], updateNumber)
            }
        }
        plus.setOnClickListener {
            updateNumber = numberOfItemList!![holder.adapterPosition].toString().toInt()
            updateNumber++
            listener.onUpdateItemClickCart(itemsList[holder.adapterPosition], updateNumber)

        }

         Glide.with(holder.itemView)
             .load(itemsList[holder.adapterPosition].image)
             .into(image)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }
}
interface OnItemsClickCart {
    fun onDeleteItemClickCart(items: Items, position: Int)
    fun onUpdateItemClickCart(items: Items, updateNumber: Int)
}
