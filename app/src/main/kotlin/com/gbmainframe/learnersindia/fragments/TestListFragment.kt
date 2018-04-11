package com.gbmainframe.learnersindia.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.activities.TestActivity
import com.gbmainframe.learnersindia.adapters.TestListAdapter
import com.gbmainframe.learnersindia.adapters.TestQuestionsRecyclerAdapter
import com.gbmainframe.learnersindia.utils.ApiInterface
import com.gbmainframe.learnersindia.utils.RetrofitUtils
import com.gbmainframe.learnersindia.utils.sharedPrefManager
import kotlinx.android.synthetic.main.layout_test_list.*
import kotlinx.android.synthetic.main.simple_toolbar.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by ambareeshb on 09/04/18.
 */
class TestListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.layout_test_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        toolbarTitle.text = "Tests"
        backButton.setOnClickListener {
            (activity as TestActivity).onBackPressed()
        }
        activity?.let {
            progress.visibility = View.VISIBLE
            val user = sharedPrefManager.getUser(it)
            RetrofitUtils.initRetrofit(ApiInterface::class.java).getTests(user.tocken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (it?.response_data == null) {
                            textNoTestsAvailable.visibility = View.VISIBLE
                            return@subscribe
                        }
                        progress.visibility = View.GONE
                        recyclerTest.layoutManager = LinearLayoutManager(activity!!, LinearLayoutManager.VERTICAL, false)
                        recyclerTest.adapter = TestListAdapter(it.response_data, { test ->
//                            (activity as TestActivity).loadTestStartFragment(test)
                        })

                    }, {
                        textNoTestsAvailable.visibility = View.VISIBLE
                    })

        }

    }
}
