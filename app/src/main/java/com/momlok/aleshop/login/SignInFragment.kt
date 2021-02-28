package com.momlok.aleshop.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.momlok.aleshop.BaseFragment
import com.momlok.aleshop.activites.MainActivity
import com.momlok.aleshop.databinding.FragmentSignInBinding


class SignInFragment : BaseFragment() {
    private  var _binding: FragmentSignInBinding? =null
    private val binding get() = _binding!!
    private val fbAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignInBinding.inflate(inflater,container,false)

        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginClick()
        registrationClick()
    }

    private fun registrationClick() {
        // go to registration fragment
        binding.registerLoginBT.setOnClickListener {
            findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment().actionId)
        }

    }

    private fun loginClick() {
        // sign in this account
        binding.loginLoginBT.setOnClickListener {
            val email = binding.emailLoginTE.text?.trim().toString()
            val password = binding.passwordLoginTE.text?.trim().toString()
            fbAuth.signInWithEmailAndPassword(email,password)
                .addOnSuccessListener {auth ->
                    if(auth.user != null) goMain()
                }
                .addOnFailureListener {
                    Snackbar.make(requireView(),"Login failed",Snackbar.LENGTH_SHORT).show()
                }
        }
    }
}