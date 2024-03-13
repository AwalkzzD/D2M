package com.example.d2m.screens.login.phone_otp

import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.d2m.R
import com.example.d2m.databinding.FragmentLoginDetailsBinding
import com.example.d2m.screens.utils.BaseFragment

class LoginDetailsFragment : BaseFragment<FragmentLoginDetailsBinding, LoginDetailsViewModel>(
    R.layout.fragment_login_details, LoginDetailsViewModel::class.java
) {

    override fun setUpView() {
        setupClickListener()
    }

    private fun setupClickListener() {
        fragmentBinding.getOtp.setOnClickListener {

            var getWhatsappUpdates = "0"

            if (fragmentBinding.getWhatsappUpdates.isChecked) {
                getWhatsappUpdates = "1"
            }

            if (fragmentBinding.userPhoneNumber.text.toString().length == 10) {

                fragmentViewModel.let {
                    it.phoneNum.value = fragmentBinding.userPhoneNumber.text.toString()
                    it.getWhatsappUpdates.value = getWhatsappUpdates
                }
                findNavController().navigate(R.id.action_loginDetailsFragment_to_otpFragment)

            } else {
                Toast.makeText(requireActivity(), "Enter a valid phone number", Toast.LENGTH_SHORT)
                    .show()
            }

        }
    }

}