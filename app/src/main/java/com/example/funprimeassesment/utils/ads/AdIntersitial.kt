package com.example.funprimeassesment.utils.ads

import android.app.Activity
import android.content.Context
import com.example.funprimeassesment.utils.isInternetAvailable
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

object AdIntersitial {
    var mInterstitialAd: InterstitialAd? = null
    private val intId="ca-app-pub-3940256099942544/1033173712"
    fun loadInt(context: Context, callback:(()->Unit)?) {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(context, intId,
            adRequest, object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                    callback?.invoke()
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                    callback?.invoke()
                }
            })
    }

    fun showInt(activity: Activity, callback: () -> Unit){
        if (!activity.isInternetAvailable()){
            callback.invoke()
            return
        }
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(activity)
        }
        mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdClicked() {
            }

            override fun onAdDismissedFullScreenContent() {
                mInterstitialAd = null
                callback.invoke()
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                super.onAdFailedToShowFullScreenContent(adError)
                mInterstitialAd = null
                callback.invoke()
            }

            override fun onAdImpression() {
            }

            override fun onAdShowedFullScreenContent() {
            }
        }

    }

}