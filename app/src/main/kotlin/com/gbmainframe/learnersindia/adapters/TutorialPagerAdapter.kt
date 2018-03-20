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
import kotlinx.android.synthetic.main.layout_tutoria_single_item.view.*
import java.io.ByteArrayOutputStream

/**
 * Created by ambareesh on 20/3/18.
 *
 */
class TutorialPagerAdapter(val context: Context) : PagerAdapter() {

    val resource = intArrayOf(R.drawable.gif_tutoria, R.drawable.gif_tutoria, R.drawable.gif_tutoria, R.drawable.gif_tutoria)

    override fun getCount(): Int = resource.size


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as LinearLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.layout_tutoria_single_item, container, false)
        view.itemImage.setImageDrawable(ContextCompat.getDrawable(context,resource[position]))
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