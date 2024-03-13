package com.example.d2m.screens

import com.example.d2m.R
import com.example.d2m.databinding.FragmentTestBinding
import com.example.d2m.screens.utils.BaseFragment
import com.example.d2m.screens.utils.BaseViewModel

class TestFragment : BaseFragment<FragmentTestBinding, BaseViewModel>(
    R.layout.fragment_test,
    BaseViewModel::class.java
)