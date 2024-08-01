package com.example.funprimeassesment.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.funprimeassesment.databinding.FragmentExitBinding


class ExitFragment : Fragment() {
    private var binding:FragmentExitBinding?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentExitBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.btnExit?.setOnClickListener{
            activity?.finishAffinity()
        }
        binding?.btnCancel?.setOnClickListener {
            findNavController().navigateUp()
        }
    }


}