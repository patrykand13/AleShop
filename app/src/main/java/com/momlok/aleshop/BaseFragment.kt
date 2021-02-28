package com.momlok.aleshop

import android.content.Intent
import androidx.fragment.app.Fragment
import com.momlok.aleshop.activites.MainActivity

abstract class BaseFragment: Fragment() {
    protected fun goMain(){
        val intent = Intent(requireContext(), MainActivity::class.java).apply {
            flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        startActivity(intent)
    }
}