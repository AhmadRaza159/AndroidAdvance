package com.example.funprimeassesment.ui.fragments

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.funprimeassesment.databinding.FragmentImageViewBinding
import com.example.funprimeassesment.utils.ads.AdBanner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

class ImageViewFragment : Fragment() {
   private var binding:FragmentImageViewBinding?=null
    private val args : ImageViewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentImageViewBinding.inflate(layoutInflater)
        AdBanner.loadBanner(activity?:return null){
            AdBanner.showBanner(binding?.frameBanner?:return@loadBanner)
        }

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.img?.load(args.imageItem.url)
        binding?.title?.text=args.imageItem.title

        binding?.button?.setOnClickListener {
            shareImageLogic(args.imageItem.url, args.imageItem.id.toString())
        }
    }

    private fun shareImageLogic(urlString:String, nameString: String) {
        val progress = ProgressDialog(context)
        progress.setTitle("Preparing to share")
        progress.setMessage("Wait while loading...")
        progress.setCancelable(false) // disable dismiss by tapping outside of the dialog
        progress.show()
        CoroutineScope(IO).launch {
            val url = URL(urlString)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()

            val file = File(context?.cacheDir, "$nameString.png")
            if (file.exists()) {
                file.delete()
            }

            val inputStream = connection.inputStream
            val outputStream = FileOutputStream(file)
            val buffer = ByteArray(1024)
            var len: Int

            while (inputStream.read(buffer).also { len = it } != -1) {
                outputStream.write(buffer, 0, len)
            }

            outputStream.close()
            inputStream.close()

            createShareIntent(file)
            withContext(Main){
                progress.dismiss()
            }
        }
    }

    private fun createShareIntent(file: File) {
        val uri: Uri = FileProvider.getUriForFile(context?:return, context?.applicationContext?.packageName + ".provider", file)
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, uri)
            type = "image/png"
        }
        startActivity(Intent.createChooser(shareIntent, "Share image using"))
    }

}