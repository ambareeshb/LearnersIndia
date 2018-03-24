package com.gbmainframe.learnersindia.adapters

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.Role
import com.gbmainframe.learnersindia.fragments.SelectRoleFragment
import kotlinx.android.synthetic.main.layout_carousal.view.*
import java.util.prefs.NodeChangeListener

/**
 * Created by ambareeshb on 22/03/18.
 */
class RoleSelectAdapter(private val context: Context,
                        private val roles: ArrayList<Role>,
                        private val listener: RoleSelectListener) : PagerAdapter() {


    override fun getCount(): Int = roles.size


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as LinearLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.layout_carousal, container, false)
        view.carousalRoleImage.setImageDrawable(ContextCompat.getDrawable(context, roles[position].image))
        view.carousalRoleText.text = roles[position].name
        container.addView (view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView((`object`) as LinearLayout)
    }


    public interface RoleSelectListener {
        fun selectedRole(role: Role)
    }
}