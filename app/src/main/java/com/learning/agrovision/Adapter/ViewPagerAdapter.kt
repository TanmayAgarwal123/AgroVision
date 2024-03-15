package com.learning.agrovision.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.learning.agrovision.R

class ViewPagerAdapter(var context: Context) : PagerAdapter() {
    var images = intArrayOf(
        R.drawable.crop_pricdiction,
        R.drawable.crop_yield,
        R.drawable.crop_price,
        R.drawable.crop_fertilizers,
        R.drawable.crop_pest_and_disease
    )
    var headings = intArrayOf(
        R.string.crop_prediction_h,
        R.string.crop_yield_prediction_h,
        R.string.crop_price_prediction_h,
        R.string.fertilizer_prediction_h,
        R.string.crop_pest_pridiction_h
    )
    var descriptions = intArrayOf(
        R.string.crop_prediction_d,
        R.string.crop_yield_prediction_d,
        R.string.crop_price_prediction_d,
        R.string.fertilizer_prediction_d,
        R.string.crop_pest_pridiction_d
    )

    override fun getCount(): Int {
        return headings.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as LinearLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = LayoutInflater.from(context)
        val view: View = layoutInflater.inflate(R.layout.slider_layout, container, false)
        val slideTitleImage = view.findViewById<ImageView>(R.id.animation_view)
        val slideHeading = view.findViewById<TextView>(R.id.texttitle)
        val slideDescription = view.findViewById<TextView>(R.id.textdeccription)

        // Set Lottie animation
//        slideTitleImage.setImageResource(images[position])
        Glide
            .with(context)
            .load(images[position])
            .centerCrop()
            .placeholder(R.drawable.crop_pest_and_disease)
            .into(slideTitleImage);
        // Set heading and description
        slideHeading.setText(headings[position])
        slideDescription.setText(descriptions[position])

        container.addView(view)
        return view
    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }
}