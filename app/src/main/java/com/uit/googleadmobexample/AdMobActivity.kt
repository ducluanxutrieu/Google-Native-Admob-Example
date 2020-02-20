package com.uit.googleadmobexample

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.formats.MediaView
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.formats.UnifiedNativeAdView

class AdMobActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad_mob)

//        setupAdmob()
    }

    /*private fun setupAdmob() {
        val builder = AdLoader.Builder(this, "<your ad unit ID>")
            .forUnifiedNativeAd { unifiedNativeAd ->
                // Assumes that your ad layout is in a file call ad_unified.xml
                // in the res/layout folder
                val adView = layoutInflater
                    .inflate(R.layout.ad_unified, null) as UnifiedNativeAdView
                // This method sets the text, images and the native ad, etc into the ad
                // view.
                populateUnifiedNativeAdView(unifiedNativeAd, adView)
                // Assumes you have a placeholder FrameLayout in your View layout
                // (with id ad_frame) where the ad is to be placed.
                ad_frame.removeAllViews()
                ad_frame.addView(adView)
            }
    }
*/
    /*fun displayUnifiedNativeAd(parent: ViewGroup, ad: UnifiedNativeAd) {

        // Inflate a layout and add it to the parent ViewGroup.
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
                as LayoutInflater
        val adView = inflater.inflate(R.layout.my_ad_layout, parent) as UnifiedNativeAdView

        // Locate the view that will hold the headline, set its text, and use the
        // UnifiedNativeAdView's headlineView property to register it.
        val headlineView = adView.findViewById<TextView>(R.id.ad_headline)
        headlineView.text = ad.headline
        adView.headlineView = headlineView

        ...
        // Repeat the above process for the other assets in the UnifiedNativeAd using
        // additional view objects (Buttons, ImageViews, etc).
        ...

        val mediaView = adView.findViewById<MediaView>(R.id.ad_media)
        adView.mediaView = mediaView

        // Call the UnifiedNativeAdView's setNativeAd method to register the
        // NativeAdObject.
        adView.setNativeAd(ad)

        // Ensure that the parent view doesn't already contain an ad view.
        parent.removeAllViews()

        // Place the AdView into the parent.
        parent.addView(adView)
    }*/
}
