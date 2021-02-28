package com.momlok.aleshop.cart

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.momlok.aleshop.categories.OnItemsLongClick
import com.momlok.aleshop.categories.ShearAdapter
import com.momlok.aleshop.categories.ShearViewModel
import com.momlok.aleshop.data.Items
import com.momlok.aleshop.data.User
import com.momlok.aleshop.databinding.FragmentCartBinding


class CartFragment : Fragment(), OnItemsLongClick {
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
        TODO("Not yet implemented")
    }
}