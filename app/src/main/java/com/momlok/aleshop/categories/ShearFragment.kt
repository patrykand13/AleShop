package com.momlok.aleshop.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.momlok.aleshop.activites.BaseFragment
import com.momlok.aleshop.data.Items
import com.momlok.aleshop.databinding.FragmentShearBinding



class ShearFragment : BaseFragment(), OnItemsClick {

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
        var media = args.image
        if(media != null){
            Glide.with(this)
                    .load(media)
                    .into(binding.shearRowImage)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        shearVM.getItemsData(args.type).observe(viewLifecycleOwner, {list ->
            adapter.setItems(list)
        })
    }

    override fun onItemsClick(items: Items, position: Int) {
        var action = ShearFragmentDirections.actionShearFragmentToItemFragment(items.image.toString(),items.name.toString(),items.price.toFloat(),items.id.toString())
        Navigation.findNavController(binding.root).navigate(action)
    }


}