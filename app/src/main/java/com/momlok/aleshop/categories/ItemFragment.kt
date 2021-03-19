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
import com.momlok.aleshop.databinding.FragmentItemBinding

class ItemFragment : Fragment() {

    private  var _binding: FragmentItemBinding? =null
    private val binding get() = _binding!!
    private val fbAuth = FirebaseAuth.getInstance()
    val args: ItemFragmentArgs by navArgs()
    private val itemVM by viewModels<ItemViewModel>()
    var price = 0.0
    var quantity = 1
    var filterUserPrice: String? = ""

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
        binding.itemPriceTV.text = "Sztuka: ${args.price.toString()} zł"
        binding.itemDescriptionTV.text = args.description
        price = args.price.toDouble()
        setTV()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.itemAddToCartBT.setOnClickListener {
            itemVM.addItemsToCart(args.id,quantity)
            binding.itemAddToCartBT.text = "Dodano do Koszyka"
        }
        binding.itemLessBT.setOnClickListener {
            if (quantity >1){
                quantity--
                price = args.price * quantity.toDouble()
                setTV()
            }
        }
        binding.itemMoreBT.setOnClickListener {
            quantity++
            price = args.price * quantity.toDouble()
            setTV()
        }

    }
    fun setTV(){
        filterUserPrice = "%.2f".format(price)
        binding.itemNumberToOrderTV.text = "Ilość sztuk: $quantity"
        binding.itemAmountTV.text = "Razem do zapłaty: $filterUserPrice zł"
    }
}