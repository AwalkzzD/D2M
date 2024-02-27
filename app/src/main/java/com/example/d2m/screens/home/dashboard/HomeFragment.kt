package com.example.d2m.screens.home.dashboard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.d2m.R
import com.example.d2m.data.models.home.Banner
import com.example.d2m.data.models.home.Service
import com.example.d2m.databinding.FragmentHomeBinding
import com.example.d2m.screens.utils.GenericDataAdapter
import com.squareup.picasso.Picasso

class HomeFragment : Fragment() {

    private lateinit var homeBinding: FragmentHomeBinding
    private lateinit var bannerAdapter: GenericDataAdapter<Banner>
    private lateinit var serviceAdapter: GenericDataAdapter<Service>
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        homeBinding = FragmentHomeBinding.inflate(inflater)
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initRecyclerView()

        val userID = activity?.getSharedPreferences("userData", Context.MODE_PRIVATE)
            ?.getString("userID", "")
        if (userID != null) {
            homeViewModel.requestUserData(userID)
        }

        homeBinding.featuresRV.apply {
            // FIXME: use carousel layout with hero carousel strategy with carousel snap in horizontal direction instead of linear layout 
            /*layoutManager =
                CarouselLayoutManager(HeroCarouselStrategy(), RecyclerView.HORIZONTAL)*/
            adapter = bannerAdapter
        }

        homeBinding.servicesRV.apply {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = serviceAdapter
        }

    }

    private fun initRecyclerView() {
        bannerAdapter =
            GenericDataAdapter(requireActivity(), R.layout.feature_list_item, { banner: Banner ->
                Toast.makeText(
                    requireActivity(), banner.banner_name, Toast.LENGTH_SHORT
                ).show()
            }) { item, itemView ->
                val featureBanner = itemView.findViewById<ImageView>(R.id.featureBanner)
                with(item) {
                    Picasso.get().load(this.banner_url.toUri())
                        .placeholder(R.drawable.app_placeholder).fit().into(featureBanner)
                }
            }

        serviceAdapter =
            GenericDataAdapter(requireActivity(), R.layout.service_list_item, { service: Service ->
                Toast.makeText(
                    requireActivity(), service.service_category_name, Toast.LENGTH_SHORT
                )
                    .show() // FIXME: add redirection to sub category of services --> ServiceX (refer JSON OUTPUT)
            }) { item, itemView ->
                val serviceName = itemView.findViewById<TextView>(R.id.serviceName)
                val serviceImage = itemView.findViewById<ImageView>(R.id.serviceImage)

                with(item) {
                    serviceName.text = this.service_category_name
                    Picasso.get().load(this.image.toUri()).placeholder(R.drawable.app_placeholder)
                        .into(serviceImage)
                }
            }
    }

    private fun initViewModel() {
        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]

        homeViewModel.userLiveData.observe(viewLifecycleOwner) {
            if (it.success) {
                bannerAdapter.addData(it.data.banners)
                bannerAdapter.notifyDataSetChanged()

                serviceAdapter.addData(it.data.services)
                serviceAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(requireActivity(), "Something went wrong!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

}