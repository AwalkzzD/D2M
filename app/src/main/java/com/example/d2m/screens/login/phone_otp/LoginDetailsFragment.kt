package com.example.d2m.screens.login.phone_otp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.d2m.R
import com.example.d2m.databinding.FragmentLoginDetailsBinding

class LoginDetailsFragment : Fragment() {
    private lateinit var loginDetailsBinding: FragmentLoginDetailsBinding
    private val TAG = "LoginDetailsFragment"
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
            var getWhatsappUpdates: String = "0"
            if (loginDetailsBinding.getWhatsAppUpdates.isChecked) {
                getWhatsappUpdates = "1"
            }
            if (loginDetailsBinding.userPhoneNumber.text.toString().length == 10) {
                val bundle = Bundle()
                bundle.putString("phoneNum", loginDetailsBinding.userPhoneNumber.text.toString())
                bundle.putString("getWhatsappUpdates", getWhatsappUpdates)
                findNavController().navigate(
                    R.id.action_loginDetailsFragment_to_otpFragment, bundle
                )
            } else {
                Toast.makeText(requireActivity(), "Enter a valid phone number", Toast.LENGTH_SHORT)
                    .show()
            }

//            ----> bypass login
//            startActivity(Intent(activity, HomeActivity::class.java))
        }
    }
}