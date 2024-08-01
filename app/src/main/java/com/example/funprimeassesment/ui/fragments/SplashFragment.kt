package com.example.funprimeassesment.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.funprimeassesment.R
import com.example.funprimeassesment.databinding.FragmentSplashBinding
import com.example.funprimeassesment.utils.ads.AdIntersitial
import com.example.funprimeassesment.utils.ads.AdNative
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {
    private var binding:FragmentSplashBinding?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentSplashBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AdIntersitial.loadInt(context?:return,null)

        AdNative.loadNative(context?:return){
            AdNative.showNative(activity?:return@loadNative, binding?.frameLayout?:return@loadNative){

            }
        }
        lifecycleScope.launch {
            delay(5000)
            if (findNavController().currentDestination?.id==R.id.splashFragment) {
                AdIntersitial.showInt(activity?:return@launch){
                    findNavController().navigate(R.id.action_splashFragment_to_recyclerFragment)
                }
            }
        }
    }


}