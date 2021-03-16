package com.momlok.aleshop.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.momlok.aleshop.R
import com.momlok.aleshop.databinding.FragmentHomeBinding
import com.momlok.aleshop.databinding.FragmentItemBinding

class ItemFragment : Fragment() {

    private  var _binding: FragmentItemBinding? =null
    private val binding get() = _binding!!
    private val fbAuth = FirebaseAuth.getInstance()
    val args: ItemFragmentArgs by navArgs()
    private val itemVM by viewModels<ItemViewModel>()
    var price = 0.0
    var quantity = 1

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentItemBinding.inflate(inflater,container,false)

        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var media = args.image
        if(media != null){
            Glide.with(this)
                .load(media)
                .into(binding.itemImageView)
        }
        binding.itemNameTV.text = args.name
        binding.itemPriceTV.text = args.price.toString()
        price = args.price.toDouble()
        var filterUserPrice: String? = "%.2f".format(price)
        binding.itemNumberToOrderTV.text = "Ilość sztuk: $quantity"
        binding.itemAmountTV.text = "Razem do zapłaty: $filterUserPrice"
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.itemAddToCartBT.setOnClickListener {
            itemVM.addItemsToCart(args.id,quantity)
        }
        binding.itemLessBT.setOnClickListener {
            if (quantity >1){
                quantity--
                price = args.price * quantity.toDouble()
                var filterUserPrice: String? = "%.2f".format(price)
                binding.itemNumberToOrderTV.text = "Ilość sztuk: $quantity"
                binding.itemAmountTV.text = "Razem do zapłaty: $filterUserPrice"
            }
        }
        binding.itemMoreBT.setOnClickListener {
            quantity++
            price = args.price * quantity.toDouble()
            var filterUserPrice: String? = "%.2f".format(price)
            binding.itemNumberToOrderTV.text = "Ilość sztuk: $quantity"
            binding.itemAmountTV.text = "Razem do zapłaty: $filterUserPrice"
        }

    }
}