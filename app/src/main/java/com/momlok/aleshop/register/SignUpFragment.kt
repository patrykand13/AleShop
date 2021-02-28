package com.momlok.aleshop.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.momlok.aleshop.BaseFragment
import com.momlok.aleshop.databinding.FragmentSignUpBinding


class SignUpFragment : BaseFragment() {
    private  var _binding: FragmentSignUpBinding? =null
    private val binding get() = _binding!!
    private val fbAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signUpClick()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun signUpClick(){
        binding.registerRegiBT.setOnClickListener {
            var email = binding.emailRegiTE.text?.trim().toString()
            var password = binding.passwordRegiTE.text?.trim().toString()
            var repeatPassword = binding.repeatPassRegiTE.text?.trim().toString()

            if (password==repeatPassword){
                fbAuth.createUserWithEmailAndPassword(email,password)
                    .addOnSuccessListener { auth ->
                        if(auth.user !=null) goMain()

                    }
                    .addOnFailureListener {
                        Snackbar.make(requireView(),"Registration failed", Snackbar.LENGTH_SHORT).show()
                    }
            }
        }
    }
}