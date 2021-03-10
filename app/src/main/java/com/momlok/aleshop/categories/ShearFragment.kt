package com.momlok.aleshop.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.momlok.aleshop.R
import com.momlok.aleshop.activites.BaseFragment
import com.momlok.aleshop.data.Items
import com.momlok.aleshop.databinding.FragmentShearBinding



class ShearFragment : BaseFragment(), OnItemsLongClick {

    private  var _binding: FragmentShearBinding? =null
    private val binding get() = _binding!!
    val args: ShearFragmentArgs by navArgs()
    private val shearVM by viewModels<ShearViewModel>()
    private val adapter = ItemsAdapter(this)


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
        binding.recyclerViewShear.layoutManager = GridLayoutManager(requireContext(),2)
        binding.recyclerViewShear.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        shearVM.getItemsData(args.type).observe(viewLifecycleOwner, {list ->
            adapter.setItems(list)
        })
    }

    override fun onItemsLongClick(items: Items, position: Int) {
        Snackbar.make(requireView(),items.name.toString(), Snackbar.LENGTH_SHORT).show()
        shearVM.addItemsToCart(items)

    }


}