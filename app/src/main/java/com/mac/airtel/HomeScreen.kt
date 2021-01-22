package com.mac.airtel

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager

lateinit var serviceC: LinearLayout
lateinit var banking: LinearLayout
lateinit var exploreC: LinearLayout
lateinit var moreC: LinearLayout

lateinit var serviceIc: ImageView
lateinit var bankingIc: ImageView
lateinit var exploreIc: ImageView
lateinit var moreIc: ImageView

lateinit var serviceText: TextView
lateinit var bankingText: TextView
lateinit var exploreText: TextView
lateinit var moreText: TextView
lateinit var errorImg: ImageView
lateinit var progress: ProgressBar
lateinit var retry: Button
var progressOn = false


class HomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_screen)



        serviceC = findViewById<LinearLayout>(R.id.service)
        banking = findViewById<LinearLayout>(R.id.bank)
        exploreC = findViewById<LinearLayout>(R.id.explore)
        moreC = findViewById<LinearLayout>(R.id.more)

        serviceIc = findViewById<ImageView>(R.id.service_ic)
        bankingIc = findViewById<ImageView>(R.id.bank_ic)
        exploreIc = findViewById<ImageView>(R.id.explore_ic)
        moreIc = findViewById<ImageView>(R.id.more_ic)

        serviceText = findViewById<TextView>(R.id.service_text)
        bankingText = findViewById<TextView>(R.id.bank_text)
        exploreText = findViewById<TextView>(R.id.explore_text)
        moreText = findViewById<TextView>(R.id.more_text)

        progress = findViewById(R.id.progressbar)
        errorImg = findViewById(R.id.error_img)
        retry = findViewById(R.id.retry)


        resetColors()
        setButtons(1)

        serviceC.setOnClickListener {
            resetColors()
            setButtons(1)
            if (!progressOn)
                progressFunction()
        }

        banking.setOnClickListener {
            resetColors()
            setButtons(2)
            if (!progressOn)
                progressFunction()
        }

        exploreC.setOnClickListener {
            resetColors()
            setButtons(3)
            if (!progressOn)
                progressFunction()
        }

        moreC.setOnClickListener {
            resetColors()
            setButtons(4)
            if (!progressOn)
                progressFunction()
        }

        retry.setOnClickListener {
            progressFunction()
        }

        openOfferDialog()
    }

    fun resetColors() {
        serviceIc.setBackgroundResource(R.drawable.grey_ring)
        bankingIc.setBackgroundResource(R.drawable.grey_ring)
        exploreIc.setBackgroundResource(R.drawable.grey_ring)
        moreIc.setBackgroundResource(R.drawable.grey_ring)

        serviceIc.setColorFilter(
            ContextCompat.getColor(this, R.color.grey),
            android.graphics.PorterDuff.Mode.MULTIPLY
        )
        bankingIc.setColorFilter(
            ContextCompat.getColor(this, R.color.grey),
            android.graphics.PorterDuff.Mode.MULTIPLY
        )
        exploreIc.setColorFilter(
            ContextCompat.getColor(this, R.color.grey),
            android.graphics.PorterDuff.Mode.MULTIPLY
        )
        moreIc.setColorFilter(
            ContextCompat.getColor(this, R.color.grey),
            android.graphics.PorterDuff.Mode.MULTIPLY
        )

        serviceText.setTextColor(resources.getColor(R.color.grey))
        bankingText.setTextColor(resources.getColor(R.color.grey))
        exploreText.setTextColor(resources.getColor(R.color.grey))
        moreText.setTextColor(resources.getColor(R.color.grey))
    }

    fun setButtons(type: Int) {
        when (type) {
            1 -> {
                serviceIc.setBackgroundResource(R.drawable.red_circle)
                serviceIc.setColorFilter(
                    ContextCompat.getColor(this, R.color.white),
                    android.graphics.PorterDuff.Mode.MULTIPLY
                )
                serviceText.setTextColor(resources.getColor(R.color.red))
            }

            2 -> {
                bankingIc.setColorFilter(Color.argb(255, 255, 255, 255))
                bankingIc.setBackgroundResource(R.drawable.red_circle)
                bankingText.setTextColor(resources.getColor(R.color.red))
            }

            3 -> {
                exploreIc.setBackgroundResource(R.drawable.red_circle)
                exploreIc.setColorFilter(Color.argb(255, 255, 255, 255))
                exploreText.setTextColor(resources.getColor(R.color.red))
            }

            4 -> {
                moreIc.setBackgroundResource(R.drawable.red_circle)
                moreIc.setColorFilter(Color.argb(255, 255, 255, 255))
                moreText.setTextColor(resources.getColor(R.color.red))
            }
        }
    }

    fun openOfferDialog() {
        val offerDialog = OfferDialog()
        offerDialog.show(supportFragmentManager, "Offer")
    }

    class OnBoardingPagerAdapter(val pager: ViewPager, val dialog: OfferDialog) : PagerAdapter() {
        lateinit var screensArray: IntArray

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = LayoutInflater.from(container.context)
                .inflate(screensArray[position], container, false)
            container.addView(view)
            val next = view.findViewById<TextView>(R.id.next)

            next.setOnClickListener {
                if (position < 3) {
                    pager.currentItem = position + 1
                } else {
                    dialog.dismiss()
                }
            }
            return view
        }

        override fun getCount(): Int {
            if (screensArray != null) {
                return screensArray.size
            } else return 0
        }

        fun addItems(screenArray: IntArray) {
            this.screensArray = screenArray
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            val view = `object` as View
            container.removeView(view)
        }
    }

    fun progressFunction() {
        showError(false)
        progressOn = true
        val handler = Handler()
        handler.postDelayed(Runnable { // Do something after 5s = 5000ms
            showError(true)
            progressOn = false
        }, 3000)
    }

    fun showError(show: Boolean) {
        retry.visibility = if (show) View.VISIBLE else View.GONE
        errorImg.visibility = if (show) View.VISIBLE else View.GONE
        progress.visibility = if (show) View.GONE else View.VISIBLE
    }
}