package com.example.d2m.screens.account

import android.content.Context
import android.content.Intent
import com.example.d2m.R
import com.example.d2m.databinding.FragmentProfileBinding
import com.example.d2m.screens.login.LoginActivity
import com.example.d2m.screens.utils.base_classes.BaseActivity
import com.example.d2m.screens.utils.base_classes.BaseFragment

class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>(
    R.layout.fragment_profile, ProfileViewModel::class.java
) {
    override fun setUpView() {
        setUpToolBar()
        setUpListeners()
        setUpBindingVariable()
    }

    private fun setUpToolBar() {
        (activity as BaseActivity<*, *>).setupToolbar(
            fragmentBinding.appBar.toolbar, "Account", true
        )
    }

    private fun setUpListeners() {
        fragmentBinding.logoutButton.setOnClickListener {
            showDialog(
                "Confirm Logout",
                "Are you sure you want to log out?",
                "LOGOUT",
                "CANCEL"
            ) {
                val sharedPreferencesLogin =
                    activity?.getSharedPreferences("userData", Context.MODE_PRIVATE)
                sharedPreferencesLogin?.edit()?.putBoolean("isLoggedIn", false)?.apply()

                startActivity(Intent(activity, LoginActivity::class.java))
                activity?.finish()
            }
        }
    }

    private fun setUpBindingVariable() {
        fragmentBinding.userProfile = fragmentViewModel.getProfileData()
    }

}
