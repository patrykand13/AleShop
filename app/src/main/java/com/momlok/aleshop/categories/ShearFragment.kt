package com.momlok.aleshop.categories

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.api.Distribution
import com.momlok.aleshop.BaseFragment
import com.momlok.aleshop.R
import com.momlok.aleshop.cart.CartViewModel
import com.momlok.aleshop.data.Items
import com.momlok.aleshop.data.User
import com.momlok.aleshop.databinding.FragmentHomeBinding
import com.momlok.aleshop.databinding.FragmentShearBinding


class ShearFragment : BaseFragment(), OnItemsLongClick {

    private  var _binding: FragmentShearBinding? =null
    private val binding get() = _binding!!
    val args: ShearFragmentArgs by navArgs()
    private val shearVM by viewModels<ShearViewModel>()
    private val adapter = ShearAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentShearBinding.inflate(inflater,container,false)
        val myArgs = args.type
        binding.textor.text = myArgs

        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewShear.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewShear.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        shearVM.items.observe(viewLifecycleOwner, {list ->
            adapter.setItems(list)
        })
    }

    override fun onItemsLongClick(items: Items, position: Int) {
        Snackbar.make(requireView(),items.name.toString(), Snackbar.LENGTH_SHORT).show()
        shearVM.addItemsToCart(items)

    }


}