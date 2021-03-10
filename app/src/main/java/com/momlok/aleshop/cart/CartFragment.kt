package com.momlok.aleshop.cart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.type.DateTime
import com.momlok.aleshop.activites.BaseFragment
import com.momlok.aleshop.categories.ItemsAdapter
import com.momlok.aleshop.categories.OnItemsLongClick
import com.momlok.aleshop.data.Items
import com.momlok.aleshop.data.Order
import com.momlok.aleshop.data.User
import com.momlok.aleshop.databinding.FragmentCartBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*


class CartFragment : BaseFragment(), OnItemsLongClick {
    private  var _binding: FragmentCartBinding? =null
    private val binding get() = _binding!!
    private val cartVM by viewModels<CartViewModel>()
    private val adapter = ItemsAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCartBinding.inflate(inflater,container,false)


        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewCart.layoutManager = GridLayoutManager(requireContext(),2)
        binding.recyclerViewCart.adapter = adapter

        createOrderClick()
    }

    private fun createOrderClick() {
        binding.createOrderCartBT.setOnClickListener {
            try {
                var itemsList = adapter.itemsList
                var numberOfOrder = Date().time.toString()
                val sdf = SimpleDateFormat("yyyy-MM-dd")
                var date = sdf.format(Date())
                val order = Order(
                        numberOfOrder,
                        "realizowany",
                        cartVM.user.value?.uid,
                        date,
                        listOf())
                cartVM.createOrder(order)
                for (i in 0 until itemsList.size){
                    var simpleItem =itemsList.get(i)
                    cartVM.updateOrder(simpleItem,order)
                }
                cartVM.removeCart()
                adapter.removeCart()
                Snackbar.make(requireView(),"Pomyslnie zlozone zamowienie o numerze: $numberOfOrder", Snackbar.LENGTH_SHORT).show()
            }catch (e: Exception){ Snackbar.make(requireView(),"Przepraszamy, sprobuj ponownie", Snackbar.LENGTH_SHORT).show()}

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        cartVM.cart.observe(viewLifecycleOwner,{list ->
            list?.let{
                adapter.setItems(it)
            }

        })
    }

    override fun onItemsLongClick(items: Items, position: Int) {
        cartVM.removeCartItem(items)
        adapter.removeCartItem(items,position)
    }
}