package com.mac.airtel

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.viewpager.widget.ViewPager

class OfferDialog : DialogFragment() {

    lateinit var layoutDots: LinearLayout
    lateinit var viewPager: ViewPager
    private lateinit var layouts: IntArray
    internal lateinit var dots: ArrayList<TextView>
    lateinit var onBoardingPagerAdapter: HomeScreen.OnBoardingPagerAdapter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(activity).inflate(R.layout.dialog_offer, null, false)

        layoutDots = view.findViewById(R.id.layoutDots)
        viewPager = view.findViewById(R.id.view_pager)
        onBoardingPagerAdapter = HomeScreen.OnBoardingPagerAdapter(viewPager, this)
        layouts = intArrayOf(
            R.layout.first_offer,
            R.layout.second_offer,
            R.layout.third_offer,
            R.layout.fourth_offer
        )
        addBottomDots(0)
        onBoardingPagerAdapter.addItems(layouts)


        viewPager.setPageTransformer(true, DepthPageTransformer())
        viewPager.adapter = onBoardingPagerAdapter

        viewPager.addOnPageChangeListener(object :
            ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                Log.d("Position", position.toString())
                addBottomDots(position)
            }
        })

        val dialog = Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(view)
        if (dialog.window != null) {
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    private fun addBottomDots(currentPage: Int) {
        dots = ArrayList<TextView>()
        val colorsActive = resources.getIntArray(R.array.array_dot_active)
        val colorsInactive = resources.getIntArray(R.array.array_dot_inactive)

        if (layoutDots != null) {
            layoutDots.removeAllViews()
        }
        for (i in 0 until layouts.size) {
            val textView = TextView(activity)
            textView.text = Html.fromHtml("&#8226;")
            textView.textSize = 45F
            textView.setTextColor(colorsInactive[0])
            dots.add(textView)
            if (layoutDots != null) {
                layoutDots.addView(dots[i])
            }
        }

        if (dots.size > 0)
            for (i in 0 until layouts.size) {
                if (i == currentPage) {
                    dots[currentPage].setTextColor(colorsActive[0])
                } else {
                    dots[i].setTextColor(colorsInactive[0])
                }
            }

    }

    class DepthPageTransformer : ViewPager.PageTransformer {
        private val MIN_SCALE = 0.5f

        override fun transformPage(view: View, position: Float) {
            view.apply {
                val pageWidth = width
                when {
                    position < -1 -> { // [-Infinity,-1)
                        // This page is way off-screen to the left.
                        alpha = 0f
                    }
                    position <= 0 -> { // [-1,0]
                        // Use the default slide transition when moving to the left page
                        alpha = 1f
                        translationX = 0f
                        scaleX = 1f
                        scaleY = 1f
                    }
                    position <= 1 -> { // (0,1]
                        // Fade the page out.
                        alpha = 1 - position

                        // Counteract the default slide transition
                        translationX = pageWidth * -position

                        // Scale the page down (between MIN_SCALE and 1)
                        val scaleFactor = (MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position)))
                        scaleX = scaleFactor
                        scaleY = scaleFactor
                    }
                    else -> { // (1,+Infinity]
                        // This page is way off-screen to the right.
                        alpha = 0f
                    }
                }
            }
        }
    }
}