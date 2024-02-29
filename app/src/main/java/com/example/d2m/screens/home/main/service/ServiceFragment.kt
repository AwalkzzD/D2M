package com.example.d2m.screens.home.main.service

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.d2m.R
import com.example.d2m.data.models.home.ServiceX
import com.example.d2m.databinding.FragmentServiceBinding
import com.example.d2m.screens.utils.GenericDataAdapter

class ServiceFragment : Fragment() {

    private lateinit var serviceBinding: FragmentServiceBinding
    private lateinit var serviceXAdapter: GenericDataAdapter<ServiceX>
    private val serviceViewModel: ServiceViewModel by activityViewModels()
    private var serviceXList: MutableList<ServiceX> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        serviceBinding = FragmentServiceBinding.inflate(inflater)
        return serviceBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initRecyclerView()

        serviceBinding.servicesRV.apply {
            adapter = serviceXAdapter
        }

    }

    private fun initRecyclerView() {
        serviceXAdapter = GenericDataAdapter(
            serviceXList, R.layout.servicex_list_item
        ) { serviceX: ServiceX ->
            Toast.makeText(
                requireActivity(), serviceX.service_title, Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun initViewModel() {
        serviceViewModel.serviceXLiveData.observe(viewLifecycleOwner) {
            serviceXList.clear()
            serviceXList.addAll(it)
            serviceXAdapter.notifyDataSetChanged()
        }
    }
}