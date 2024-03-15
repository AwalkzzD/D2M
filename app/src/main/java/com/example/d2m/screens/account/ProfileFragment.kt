package com.example.d2m.screens.account

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import com.example.d2m.R
import com.example.d2m.databinding.FragmentProfileBinding
import com.example.d2m.screens.login.LoginActivity
import com.example.d2m.screens.utils.BaseActivity
import com.example.d2m.screens.utils.BaseFragment

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
            showLogoutDialog()
        }
    }

    private fun setUpBindingVariable() {
        fragmentBinding.userProfile = fragmentViewModel.getProfileData()
    }

    private fun showLogoutDialog() {
        val dialogBuilder = AlertDialog.Builder(requireActivity())

        dialogBuilder.setMessage("Are you sure you want to log out?").setCancelable(true)
            .setPositiveButton("LOGOUT") { dialog, _ ->
                val sharedPreferencesLogin =
                    activity?.getSharedPreferences("userData", Context.MODE_PRIVATE)
                sharedPreferencesLogin?.edit()?.putBoolean("isLoggedIn", false)?.apply()
                dialog.dismiss()

                startActivity(Intent(activity, LoginActivity::class.java))
                activity?.finish()

            }.setNegativeButton("CANCEL") { dialog, _ ->
                dialog.dismiss()
            }

        val alert = dialogBuilder.create()
        alert.setTitle("Confirm Logout")
        alert.show()
    }
}
