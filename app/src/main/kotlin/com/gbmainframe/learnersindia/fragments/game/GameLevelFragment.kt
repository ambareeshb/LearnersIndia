package com.gbmainframe.learnersindia.fragments.game

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.activities.GameActivity
import com.gbmainframe.learnersindia.adapters.GameLevelAdapter
import com.gbmainframe.learnersindia.utils.ApiInterface
import com.gbmainframe.learnersindia.utils.RetrofitUtils
import kotlinx.android.synthetic.main.layout_game_level.*
import kotlinx.android.synthetic.main.simple_toolbar.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by ambareeshb on 10/04/18.
 */
class GameLevelFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.layout_game_level, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        toolbarTitle.text = getString(R.string.game)
        backButton.setOnClickListener {
            (activity as GameActivity).onBackPressed()
        }

        RetrofitUtils.initRetrofit(ApiInterface::class.java).getGameLevel()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    recyclerGame.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    recyclerGame.adapter = GameLevelAdapter(it)
                }, {
                    it.printStackTrace()
                })
    }
}