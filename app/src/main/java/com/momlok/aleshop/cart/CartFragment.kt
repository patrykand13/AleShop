package com.momlok.aleshop.cart

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.momlok.aleshop.BaseFragment
import com.momlok.aleshop.categories.OnItemsLongClick
import com.momlok.aleshop.categories.ShearAdapter
import com.momlok.aleshop.data.Items
import com.momlok.aleshop.data.Order
import com.momlok.aleshop.data.User
import com.momlok.aleshop.databinding.FragmentCartBinding
import java.util.*


class CartFragment : BaseFragment(), OnItemsLongClick {
    private  var _binding: FragmentCartBinding? =null
    private val binding get() = _binding!!
    private val cartVM by viewModels<CartViewModel>()
    private val adapter = ShearAdapter(this)

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

        binding.recyclerViewCart.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewCart.adapter = adapter

        createOrderClick()
    }

    private fun createOrderClick() {
        binding.createOrderCartBT.setOnClickListener {
            try {
                var itemsList = adapter.itemsList
                val order = Order(
                        Date().time.toString(),
                        "realizowany",
                        cartVM.user.value?.uid,
                        listOf())
                cartVM.createOrder(order)

                var co = itemsList.get(0)
                for (i in 0 until itemsList.size){
                    co =itemsList.get(i)
                    cartVM.updateOrder(co,order)
                }

                cartVM.removeCart()
                adapter.removeCart()
            }catch (e: Exception){ Snackbar.make(requireView(),"Przepraszamy, sprobuj ponownie", Snackbar.LENGTH_SHORT).show()}

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        cartVM.user.observe(viewLifecycleOwner, {user ->
            bindUserData(user)
        })

        cartVM.cart.observe(viewLifecycleOwner,{list ->
            list?.let{
                adapter.setItems(it)
            }

        })
    }

    private fun bindUserData(user: User) {
        Log.d("info", user.toString())
    }

    override fun onItemsLongClick(items: Items, position: Int) {
        cartVM.removeCartItem(items)
        adapter.removeCartItem(items,position)
    }
}