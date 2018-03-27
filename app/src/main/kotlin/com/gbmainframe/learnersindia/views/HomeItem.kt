package com.gbmainframe.learnersindia.views

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.gbmainframe.learnersindia.R
import kotlinx.android.synthetic.main.home_item.view.*

/**
 * Created by ambareeshb on 26/03/18.
 */
class HomeItem : LinearLayout {

    private var headerColor: Int? = null
    private var itemText: String? = null
    private var itemImage: Int? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val typedArray = context.theme.obtainStyledAttributes(attrs,
                R.styleable.HomeItem
                , 0, 0)
        try {
            headerColor = typedArray.getResourceId(R.styleable.HomeItem_headerColor,R.color.paleBrown)
            itemImage = typedArray.getResourceId(R.styleable.HomeItem_homeItemImage, R.drawable.progress_o)
            itemText = typedArray.getString(R.styleable.HomeItem_homeItemText)
        } finally {
            typedArray.recycle()
        }

        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.home_item, this)
        headerColor?.let {
            homeItemTopColor.setBackgroundColor(ContextCompat.getColor(context, it))
        }
        itemImage?.let {
            homeItemImage.setImageDrawable(ContextCompat.getDrawable(context, it))
        }
        itemText?.let {
            homeItemText.text = it
        }
    }
}