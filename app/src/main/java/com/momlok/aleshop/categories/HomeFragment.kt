package com.momlok.aleshop.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.momlok.aleshop.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private  var _binding: FragmentHomeBinding? =null
    private val binding get() = _binding!!
    private val fbAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        binding.chairHomeBT.setOnClickListener {
            var action = HomeFragmentDirections.actionHomeFragmentToShearFragment("chair")
            Navigation.findNavController(binding.root).navigate(action)
        }
        binding.logoutBT.setOnClickListener {
            fbAuth.signOut()
            requireActivity().finish()
        }

        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}