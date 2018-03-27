package com.gbmainframe.learnersindia.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.activities.Home
import com.gbmainframe.learnersindia.adapters.ChaptersAdapter
import com.gbmainframe.learnersindia.utils.ApiInterface
import com.gbmainframe.learnersindia.utils.RetrofitUtils
import com.gbmainframe.learnersindia.utils.sharedPrefManager
import kotlinx.android.synthetic.main.layout_chapters.*
import kotlinx.android.synthetic.main.simple_toolbar.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by ambareeshb on 27/03/18.
 */
class ChaptersFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.layout_chapters, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarTitle.text = getString(R.string.chapters)
        backButton.setOnClickListener { (activity as Home).popBackStack() }
        //Api to list chapters
        progress.visibility = View.VISIBLE
        activity?.let {
            val user = sharedPrefManager.getUser(it)
            RetrofitUtils.initRetrofit(ApiInterface::class.java).getChapters(
                    user.tocken,
                    user.syl_id.toInt(),
                    user.cls_id.toInt()
            ).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (it == null) {
                            textNoChaptersAvailable.visibility = View.VISIBLE
                            return@subscribe
                        }
                        textNoChaptersAvailable.visibility = View.GONE
                        progress.visibility = View.GONE
                        recyclerChapters.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        recyclerChapters.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
                        recyclerChapters.adapter = ChaptersAdapter(it)

                    }, {
                        progress.visibility = View.GONE
                        textNoChaptersAvailable.visibility = View.VISIBLE
                        it.printStackTrace()
                    })

        }
    }
}