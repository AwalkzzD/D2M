package com.example.d2m.screens.login.phone_otp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.d2m.R
import com.example.d2m.databinding.FragmentLoginDetailsBinding

class LoginDetailsFragment : Fragment() {

    private lateinit var loginDetailsBinding: FragmentLoginDetailsBinding

    private val loginDetailsViewModel: LoginDetailsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        loginDetailsBinding = FragmentLoginDetailsBinding.inflate(inflater)
        return loginDetailsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginDetailsBinding.getOtp.setOnClickListener {

//            ----> login code
            var getWhatsappUpdates = "0"

            if (loginDetailsBinding.getWhatsappUpdates.isChecked) {
                getWhatsappUpdates = "1"
            }

            if (loginDetailsBinding.userPhoneNumber.text.toString().length == 10) {

                loginDetailsViewModel.let {
                    it.phoneNum.value = loginDetailsBinding.userPhoneNumber.text.toString()
                    it.getWhatsappUpdates.value = getWhatsappUpdates
                }
                findNavController().navigate(R.id.action_loginDetailsFragment_to_otpFragment)

            } else {
                Toast.makeText(requireActivity(), "Enter a valid phone number", Toast.LENGTH_SHORT)
                    .show()
            }

//            ----> bypass login
//            startActivity(Intent(activity, AddCarActivity::class.java))
        }
    }
}