package com.example.funprimeassesment.utils.ads

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.example.funprimeassesment.R
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView

object AdNative {
    var mNativeAd: NativeAd? = null
    private val natId="ca-app-pub-3940256099942544/2247696110"
    fun loadNative(context: Context, callback:()->Unit) {
        val adLoader = AdLoader.Builder(context, natId)
            .forNativeAd { ad: NativeAd ->
                mNativeAd = null
                mNativeAd = ad
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mNativeAd = null
                }
                override fun onAdClicked() {
                    super.onAdClicked()
                }

                override fun onAdLoaded() {
                    super.onAdLoaded()
                    callback.invoke()

                }
            })
            .build()
        adLoader.loadAd(AdRequest.Builder().build())
    }

    fun showNative(
        activity: Activity,
        adLayout: FrameLayout,
        callback: ()->Unit
    ) {
        if (mNativeAd != null) {
            val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
                    as LayoutInflater
            val adView =
                    inflater.inflate(R.layout.admob_native_large_hctr, null) as NativeAdView



            adView.bodyView = adView.findViewById(R.id.ad_body)

            adView.iconView = adView.findViewById(R.id.ad_app_icon)
            adView.headlineView = adView.findViewById(R.id.ad_headline)
            (adView.headlineView as TextView).text = mNativeAd?.headline
            adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)
                adView.mediaView = adView.findViewById(R.id.ad_media)
                mNativeAd?.mediaContent?.let { adView.mediaView?.setMediaContent(it) }
            if (mNativeAd?.body == null) {
                adView.bodyView?.visibility = View.INVISIBLE
            } else {
                adView.bodyView?.visibility = View.VISIBLE
                (adView.bodyView as TextView).text = mNativeAd?.body
            }

            if (mNativeAd?.callToAction == null) {
                adView.callToActionView?.visibility = View.INVISIBLE
            } else {
                adView.callToActionView?.visibility = View.VISIBLE
                (adView.callToActionView as Button).text = mNativeAd?.callToAction
            }

            if (mNativeAd?.icon == null) {
                adView.iconView?.visibility = View.GONE
            } else {
                (adView.iconView as ImageView).setImageDrawable(mNativeAd?.icon?.drawable)
                adView.iconView?.visibility = View.VISIBLE
            }

            adView.setNativeAd(mNativeAd!!)
            adLayout.removeAllViews()
            adLayout.addView(adView)
            adLayout.visibility = View.VISIBLE
            callback.invoke()

        } else {
            callback.invoke()
        }
    }
}