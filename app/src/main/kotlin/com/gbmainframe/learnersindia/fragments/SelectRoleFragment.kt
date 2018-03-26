package com.gbmainframe.learnersindia.fragments

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.Role
import com.gbmainframe.learnersindia.activities.SignIn
import com.gbmainframe.learnersindia.adapters.RoleSelectAdapter
import com.gbmainframe.learnersindia.constants.Constants
import kotlinx.android.synthetic.main.layout_select_role.*

/**
 * Created by ambareesh on 20/3/18.
 */
class SelectRoleFragment : Fragment(), RoleSelectAdapter.RoleSelectListener {
    override fun selectedRole(role: Role) {
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.layout_select_role, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rolesList = arrayListOf<Role>(Role("Student", R.drawable.student),
                Role("Teacher", R.drawable.teacher), Role("Parent", R.drawable.parent))
        context?.let {
            selectroleViewPager.adapter = RoleSelectAdapter(it, rolesList, this@SelectRoleFragment)

            selectroleViewPager.pageMargin = 24
        }
        arrowLeft.setOnClickListener {
            if (selectroleViewPager.currentItem != 0) {
                selectroleViewPager.currentItem = selectroleViewPager.currentItem - 1
            }
        }

        arrowRight.setOnClickListener {
            if (selectroleViewPager.currentItem != 2) {
                selectroleViewPager.currentItem = selectroleViewPager.currentItem + 1
            }
        }
        buttonNext.setOnClickListener {
            if(selectroleViewPager?.currentItem != 0){
                Snackbar.make(view,"Only student login available",Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (arguments?.getInt(SignIn.Companion.LOGIN_DECISION_KEY)?.equals(Constants.LOGIN_DECISION.LOGIN.ordinal)!!) {
                (activity as SignIn).loadSignInFragment()
            } else {
                (activity as SignIn).loadSelectBoardFragment()
            }

        }
    }
}