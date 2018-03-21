package com.gbmainframe.learnersindia.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.Role
import com.gbmainframe.learnersindia.adapters.CarousalAdapter
import fr.rolandl.carousel.CarouselAdapter
import fr.rolandl.carousel.CarouselBaseAdapter
import fr.rolandl.carousel.CarouselItem
import kotlinx.android.synthetic.main.layout_select_role.*

/**
 * Created by ambareesh on 20/3/18.
 */
class SelectRoleFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.layout_select_role, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rolesList = arrayListOf<Role>(Role("Student",R.drawable.role_student),
                Role("Teacher",R.drawable.role_techer),Role("Parent",R.drawable.role_parent))
        val adapter = CarousalAdapter(context!!,rolesList)
       carousalRoles.adapter = adapter
        carousalRoles.setSelection(1,true)
        adapter.notifyDataSetChanged()
        carousalRoles.onItemClickListener = object :  CarouselBaseAdapter.OnItemClickListener {
            override fun onItemClick(parent: CarouselBaseAdapter<*>?, view: View?, position: Int, id: Long) {
            }
        }
    }
}