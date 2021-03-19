package com.momlok.aleshop.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.momlok.aleshop.activites.BaseFragment
import com.momlok.aleshop.data.User
import com.momlok.aleshop.databinding.FragmentSignUpBinding


class SignUpFragment : BaseFragment() {
    private  var _binding: FragmentSignUpBinding? =null
    private val binding get() = _binding!!
    private val fbAuth = FirebaseAuth.getInstance()
    private val signUpVM by viewModels<SignUpViewModel>()

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
            var email = binding.emailRegiTE.text.toString()
            var password = binding.passwordRegiTE.text.toString()
            var repeatPassword = binding.repeatPassRegiTE.text.toString()

            if (password==repeatPassword){
                fbAuth.createUserWithEmailAndPassword(email,password)
                    .addOnSuccessListener {auth ->
                            val user = User(
                                    auth.user!!.uid,
                                    auth.user!!.email,
                                    "")
                            signUpVM.createNewUs(user)
                            goMain()
                    }
                    .addOnFailureListener {
                        Snackbar.make(requireView(),"Registration failed", Snackbar.LENGTH_SHORT).show()
                    }
            }
        }
    }
}