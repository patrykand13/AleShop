package com.momlok.aleshop.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.momlok.aleshop.activites.BaseFragment
import com.momlok.aleshop.data.Categories
import com.momlok.aleshop.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment(), OnClickListener {

    private  var _binding: FragmentHomeBinding? =null
    private val binding get() = _binding!!
    private val fbAuth = FirebaseAuth.getInstance()
    private val homeVM by viewModels<HomeViewModel>()
    private val adapter = HomeAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater,container,false)

        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewHome.layoutManager = GridLayoutManager(requireContext(),1)
        binding.recyclerViewHome.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        homeVM.categories.observe(viewLifecycleOwner, {categories ->
            adapter.setHome(categories)
        })
        binding.logoutBT.setOnClickListener {
            fbAuth.signOut()
            requireActivity().finish()
        }
    }

    override fun onClickListener(categories: Categories, position: Int) {
        var action = HomeFragmentDirections.actionHomeFragmentToShearFragment(categories.name.toString(),categories.image.toString())
        Navigation.findNavController(binding.root).navigate(action)
    }
}