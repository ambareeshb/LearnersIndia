package com.gbmainframe.learnersindia

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import fr.rolandl.carousel.CarouselItem

/**
 * Created by ambareesh on 21/3/18.
 */
class CarousalItem(private val currentContext: Context) : CarouselItem<Role>(currentContext, R.layout.layout_carousal) {
    private lateinit var image: ImageView
    private lateinit var name: TextView
    override fun update(role: Role?) {
        name.text = role?.name
        image.setImageDrawable(ContextCompat.getDrawable(currentContext, role?.image!!))
    }

    override fun extractView(view: View?) {
        name = view?.findViewById(R.id.carousalRoleText) as TextView
        image = view?.findViewById(R.id.carousalRoleImage) as ImageView
    }

}