package com.momlok.aleshop.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.momlok.aleshop.activites.BaseFragment
import com.momlok.aleshop.categories.ItemsAdapter
import com.momlok.aleshop.categories.ShearViewModel
import com.momlok.aleshop.databinding.FragmentOrdersBinding


class OrdersFragment : BaseFragment() {
    private  var _binding: FragmentOrdersBinding? =null
    private val binding get() = _binding!!
    private val ordersVM by viewModels<OrdersViewModel>()
    private val adapter = OrdersAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOrdersBinding.inflate(inflater,container,false)

        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewOrder.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewOrder.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        ordersVM.orders.observe(viewLifecycleOwner, { list ->
                    adapter.setItems(list)
                })
    }
}