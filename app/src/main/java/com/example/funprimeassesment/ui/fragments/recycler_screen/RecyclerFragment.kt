package com.example.funprimeassesment.ui.fragments.recycler_screen

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.funprimeassesment.R
import com.example.funprimeassesment.databinding.FragmentRecyclerBinding
import com.example.funprimeassesment.utils.ads.AdIntersitial
import com.example.funprimeassesment.utils.isInternetAvailable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class RecyclerFragment : Fragment() {

    private val viewModel: RecyclerViewModel by viewModels()
    private var binding: FragmentRecyclerBinding? = null
    private var recyclerAdapter: RecyclerAdapter? = null
    var progressData : ProgressDialog?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (findNavController().currentDestination?.id == R.id.recyclerFragment) {
                    val progress = ProgressDialog(context)
                    progress.setTitle("Loading Ad")
                    progress.setMessage("Wait while loading...")
                    progress.setCancelable(false) // disable dismiss by tapping outside of the dialog
                    progress.show()

                    AdIntersitial.loadInt(context ?: return) {
                        progress.dismiss()
                        AdIntersitial.showInt(activity ?: return@loadInt) {
                            findNavController().navigate(R.id.action_recyclerFragment_to_exitFragment)
                        }
                    }
                }
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(this, onBackPressedCallback)
        progressData = ProgressDialog(context)
        progressData?.setTitle("Loading images")
        progressData?.setMessage("Wait while loading...")
        progressData?.setCancelable(false) // disable dismiss by tapping outside of the dialog
        progressData?.show()

        recyclerAdapter = RecyclerAdapter { imageItem ->
            val action =
                RecyclerFragmentDirections.actionRecyclerFragmentToImageViewFragment(imageItem)
            if (findNavController().currentDestination?.id == R.id.recyclerFragment) {
                val progress = ProgressDialog(context)
                progress.setTitle("Loading Ad")
                progress.setMessage("Wait while loading...")
                progress.setCancelable(false) // disable dismiss by tapping outside of the dialog
                progress.show()

                AdIntersitial.loadInt(context ?: return@RecyclerAdapter) {
                    progress.dismiss()
                    AdIntersitial.showInt(activity ?: return@loadInt) {
                        findNavController().navigate(action)
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRecyclerBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.recyclerview?.adapter = recyclerAdapter

        setDataOnRecyclerView()
    }

    private fun setDataOnRecyclerView() {

        if (context?.isInternetAvailable()==true){
            lifecycleScope.launch(IO) {
                viewModel.getImages().data?.let {
                    withContext(Main) {
                        recyclerAdapter?.setListData(it)
                        progressData?.dismiss()
                    }
                }
            }
        }else{
            progressData?.dismiss()
            Toast.makeText(context?:return,context?.getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
        }

    }

}