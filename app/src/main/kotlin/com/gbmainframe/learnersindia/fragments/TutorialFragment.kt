package com.gbmainframe.learnersindia.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.adapters.TutorialPagerAdapter
import kotlinx.android.synthetic.main.layout_tutorial.*
import kotlinx.android.synthetic.main.layout_tutorial.view.*

/**
 * Created by ambareesh on 20/3/18.
 */
class TutorialFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.layout_tutorial, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.apply {
            tutorialViewPager.adapter = TutorialPagerAdapter(this)
            indicator.setViewPager(tutorialViewPager)
            tutorialViewPager.adapter?.registerDataSetObserver(indicator.dataSetObserver)
        }
    }
}