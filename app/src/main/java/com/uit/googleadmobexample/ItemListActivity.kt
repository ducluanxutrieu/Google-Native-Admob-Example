package com.uit.googleadmobexample

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.formats.UnifiedNativeAdView
import com.google.android.material.snackbar.Snackbar
import com.uit.googleadmobexample.dummy.DummyContent
import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list.*
import kotlinx.android.synthetic.main.item_list_content.view.*
import kotlin.random.Random

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ItemDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class ItemListActivity : AppCompatActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        if (item_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        setupRecyclerView(item_list)

        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713")
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = SimpleItemRecyclerViewAdapter(this, DummyContent.ITEMS, twoPane)
    }

    class SimpleItemRecyclerViewAdapter(
        private val parentActivity: ItemListActivity,
        private val values: ArrayList<DummyContent.DummyItem?>,
        private val twoPane: Boolean
    ) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        private val AD_TYPE = 1
        private val NON_AD_TYPE = 0
        private val onClickListener: View.OnClickListener

        init {
            setupListValue()

            onClickListener = View.OnClickListener { v ->
                val item = v.tag as DummyContent.DummyItem
                if (twoPane) {
                    val fragment = ItemDetailFragment().apply {
                        arguments = Bundle().apply {
                            putString(ItemDetailFragment.ARG_ITEM_ID, item.id)
                        }
                    }
                    parentActivity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit()
                } else {
                    val intent = Intent(v.context, ItemDetailActivity::class.java).apply {
                        putExtra(ItemDetailFragment.ARG_ITEM_ID, item.id)
                    }
                    v.context.startActivity(intent)
                }
            }
        }

        private fun setupListValue() {
            val randomPosAd = Random.nextInt(2, 10)
            values.add(randomPosAd, null)

            /*var i = 0
            while (i < values.size){
                if (i % 10 == 0 && i != 0){
                    values.add(i, null)
                }
                i++
            }*/
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return if (viewType == NON_AD_TYPE) {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_list_content, parent, false)
                ItemViewHolder(view)
            } else {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_ad_content, parent, false)
                AdViewHolder(view)
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if (holder is ItemViewHolder) {
                val item = values[position]
                holder.idView.text = item?.id
                holder.contentView.text = item?.content

                with(holder.itemView) {
                    tag = item
                    setOnClickListener(onClickListener)
                }
            } else {
                (holder as AdViewHolder).bindData()
            }
        }

        override fun getItemViewType(position: Int): Int {
            return if (values[position] == null)
                AD_TYPE
            else NON_AD_TYPE
        }

        override fun getItemCount() = values.size

        class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val idView: TextView = view.id_text
            val contentView: TextView = view.content
        }

        class AdViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
            @SuppressLint("InflateParams")
            fun bindData() {
                val adLoader =
                    AdLoader.Builder(view.context, "ca-app-pub-3940256099942544/2247696110")
                        .forUnifiedNativeAd {
                            val adView = LayoutInflater.from(view.context).inflate(R.layout.ad_unified, null) as UnifiedNativeAdView
                            populateUnifiedNativeAdView(it, adView)
                            view.findViewById<FrameLayout>(R.id.ad_frame).addView(adView)
                        }
                        .build()
                adLoader.loadAd(AdRequest.Builder().build())
            }

            private fun populateUnifiedNativeAdView(nativeAd: UnifiedNativeAd, adView: UnifiedNativeAdView) {
                // You must call destroy on old ads when you are done with them,
                // otherwise you will have a memory leak.
//                currentNativeAd?.destroy()
//                currentNativeAd = nativeAd

                // Set the media view.
                adView.mediaView = adView.findViewById(R.id.ad_media)

                // Set other ad assets.
                adView.headlineView = adView.findViewById(R.id.ad_headline)
                adView.bodyView = adView.findViewById(R.id.ad_body)
                adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)
                adView.iconView = adView.findViewById(R.id.ad_app_icon)
                adView.priceView = adView.findViewById(R.id.ad_price)
                adView.starRatingView = adView.findViewById(R.id.ad_stars)
                adView.storeView = adView.findViewById(R.id.ad_store)
                adView.advertiserView = adView.findViewById(R.id.ad_advertiser)

                // The headline and media content are guaranteed to be in every UnifiedNativeAd.
                (adView.headlineView as TextView).text = nativeAd.headline
                adView.mediaView.setMediaContent(nativeAd.mediaContent)

                // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
                // check before trying to display them.
                if (nativeAd.body == null) {
                    adView.bodyView.visibility = View.INVISIBLE
                } else {
                    adView.bodyView.visibility = View.VISIBLE
                    (adView.bodyView as TextView).text = nativeAd.body
                }

                if (nativeAd.callToAction == null) {
                    adView.callToActionView.visibility = View.INVISIBLE
                } else {
                    adView.callToActionView.visibility = View.VISIBLE
                    (adView.callToActionView as Button).text = nativeAd.callToAction
                }

                if (nativeAd.icon == null) {
                    adView.iconView.visibility = View.GONE
                } else {
                    (adView.iconView as ImageView).setImageDrawable(
                        nativeAd.icon.drawable)
                    adView.iconView.visibility = View.VISIBLE
                }

                if (nativeAd.price == null) {
                    adView.priceView.visibility = View.INVISIBLE
                } else {
                    adView.priceView.visibility = View.VISIBLE
                    (adView.priceView as TextView).text = nativeAd.price
                }

                if (nativeAd.store == null) {
                    adView.storeView.visibility = View.INVISIBLE
                } else {
                    adView.storeView.visibility = View.VISIBLE
                    (adView.storeView as TextView).text = nativeAd.store
                }

                if (nativeAd.starRating == null) {
                    adView.starRatingView.visibility = View.INVISIBLE
                } else {
                    (adView.starRatingView as RatingBar).rating = nativeAd.starRating!!.toFloat()
                    adView.starRatingView.visibility = View.VISIBLE
                }

                if (nativeAd.advertiser == null) {
                    adView.advertiserView.visibility = View.INVISIBLE
                } else {
                    (adView.advertiserView as TextView).text = nativeAd.advertiser
                    adView.advertiserView.visibility = View.VISIBLE
                }

                // This method tells the Google Mobile Ads SDK that you have finished populating your
                // native ad view with this native ad.
                adView.setNativeAd(nativeAd)

                // Get the video controller for the ad. One will always be provided, even if the ad doesn't
                // have a video asset.
//                val vc = nativeAd.videoController

                // Updates the UI to say whether or not this ad has a video asset.
                /*if (vc.hasVideoContent()) {
                    videostatus_text.text = String.format(
                        Locale.getDefault(),
                        "Video status: Ad contains a %.2f:1 video asset.",
                        vc.aspectRatio)

                    // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
                    // VideoController will call methods on this object when events occur in the video
                    // lifecycle.
                    vc.videoLifecycleCallbacks = object : VideoController.VideoLifecycleCallbacks() {
                        override fun onVideoEnd() {
                            // Publishers should allow native ads to complete video playback before
                            // refreshing or replacing them with another ad in the same UI location.
                            refresh_button.isEnabled = true
                            videostatus_text.text = "Video status: Video playback has ended."
                            super.onVideoEnd()
                        }
                    }
                } else {
                    videostatus_text.text = "Video status: Ad does not contain a video asset."
                    refresh_button.isEnabled = true
                }*/
            }
        }
    }
}
