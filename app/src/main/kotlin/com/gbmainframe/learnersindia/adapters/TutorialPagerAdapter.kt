package com.gbmainframe.learnersindia.adapters

import android.content.Context
import android.graphics.Bitmap
import android.support.v4.content.ContextCompat
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.constants.Constants
import kotlinx.android.synthetic.main.layout_tutoria_single_item.view.*
import kotlinx.android.synthetic.main.layout_tutorial.view.*
import pl.droidsonroids.gif.GifDrawable
import java.io.ByteArrayOutputStream

/**
 * Created by ambareesh on 20/3/18.
 *
 */
class TutorialPagerAdapter(val context: Context) : PagerAdapter() {

    val resource = intArrayOf(R.drawable.gif_one, R.drawable.gif_two, R.drawable.gif_three, R.drawable.gif_four)
    val quotesPairs = arrayOf(Pair(Constants.FIRST_QUOTE,Constants.FIRST_AUTHOR),Pair(Constants.SECOND_QUOTE,Constants.SECOND_AUTHOR)
    ,Pair(Constants.THIRD_QUOTE,Constants.THIRD_AUTHOR), Pair(Constants.FOURTH_QUOTE,Constants.FOURTH_AUTHOR))

    override fun getCount(): Int = resource.size


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as LinearLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.layout_tutoria_single_item, container, false)
        val gifFromResource = GifDrawable(context.resources, resource[position])
        view.itemImage.setImageDrawable(gifFromResource)
        view.textQuote.text = quotesPairs[position].first
        view.textAuthor.text = quotesPairs[position].second
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView((`object`) as LinearLayout)
    }

    private fun Bitmap.toByteArray(): ByteArray {
        val stream = ByteArrayOutputStream()
        this.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }
}