package com.example.d2m.screens.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.d2m.R
import com.example.d2m.databinding.FragmentLoginDetailsBinding


class LoginDetailsFragment : Fragment() {
    private lateinit var loginDetailsBinding: FragmentLoginDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        loginDetailsBinding = FragmentLoginDetailsBinding.inflate(inflater)
        return loginDetailsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginDetailsBinding.getOtp.setOnClickListener {
            findNavController().navigate(R.id.action_loginDetailsFragment_to_otpFragment)
        }
    }
}