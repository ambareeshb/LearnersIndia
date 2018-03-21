package com.gbmainframe.learnersindia.adapters

import android.content.Context
import com.gbmainframe.learnersindia.CarousalItem
import com.gbmainframe.learnersindia.Role
import fr.rolandl.carousel.CarouselAdapter
import fr.rolandl.carousel.CarouselItem

/**
 * Created by ambareesh on 21/3/18.
 */
public final class CarousalAdapter(private val context: Context, roles: List<Role>) : CarouselAdapter<Role>(context,roles) {
    override fun getCarouselItem(context: Context?): CarouselItem<Role> {
        return CarousalItem(context!!)
    }

}