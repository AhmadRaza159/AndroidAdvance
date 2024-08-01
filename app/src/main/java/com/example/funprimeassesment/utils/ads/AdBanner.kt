package com.example.funprimeassesment.utils.ads

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.gms.ads.*

object AdBanner {
    var bannerAdView: AdView? = null

    fun loadBanner(activity:Activity, callback:()->Unit){

        bannerAdView = AdView(activity)
        bannerAdView?.setAdSize(AdSize.BANNER)
        bannerAdView?.adUnitId = "ca-app-pub-3940256099942544/9214589741"
        val adRequest = AdRequest.Builder().build()
        bannerAdView?.loadAd(adRequest)
        bannerAdView?.adListener = object : AdListener() {
            override fun onAdLoaded() {
                callback.invoke()
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                callback.invoke()
                bannerAdView=null
            }

            override fun onAdOpened() {
            }

            override fun onAdClicked() {
            }

            override fun onAdClosed() {
            }
        }
    }

    fun showBanner(frameLayout: FrameLayout) {
        if (bannerAdView!=null){
            frameLayout.removeAllViews()
            if (bannerAdView?.parent != null) {
                val parent = bannerAdView?.parent as ViewGroup
                parent.removeAllViews()
            }
            frameLayout.addView(bannerAdView)
            frameLayout.visibility = View.VISIBLE
        }
    }
}