package com.gbmainframe.learnersindia.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.activities.SignIn
import com.gbmainframe.learnersindia.adapters.TutorialPagerAdapter
import com.gbmainframe.learnersindia.constants.Constants
import com.gbmainframe.learnersindia.utils.FragmentUtils
import kotlinx.android.synthetic.main.layout_tutoria_single_item.*
import kotlinx.android.synthetic.main.layout_tutorial.*
import kotlinx.android.synthetic.main.layout_tutorial.view.*

/**
 * Created by ambareesh on 20/3/18.
 */
class TutorialFragment : Fragment(),TutorialPagerAdapter.InteractionInterface {
    override fun selectRoleFragment(decision:Constants.LOGIN_DECISION) {
        (activity as SignIn).loadSelectRoleFragment(decision)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.layout_tutorial, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.apply {
            tutorialViewPager.adapter = TutorialPagerAdapter(this,this@TutorialFragment)
            indicator.setViewPager(tutorialViewPager)
            tutorialViewPager.adapter?.registerDataSetObserver(indicator.dataSetObserver)
        }
    }
}