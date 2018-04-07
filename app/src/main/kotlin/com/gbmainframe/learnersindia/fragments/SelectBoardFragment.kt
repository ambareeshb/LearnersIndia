package com.gbmainframe.learnersindia.fragments

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.activities.SignIn
import com.gbmainframe.learnersindia.adapters.BoardsRecyclerAdapter
import com.gbmainframe.learnersindia.models.Board
import com.gbmainframe.learnersindia.utils.ApiInterface
import com.gbmainframe.learnersindia.utils.RetrofitUtils
import kotlinx.android.synthetic.main.layout_select_board.*
import kotlinx.android.synthetic.main.toolbar.*
import rx.android.schedulers.AndroidSchedulers

/**
 * Created by ambareeshb on 25/03/18.
 */
class SelectBoardFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.layout_select_board, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backButton.setOnClickListener { activity?.onBackPressed() }
        toolbarButton.setOnClickListener { (activity as SignIn).loadSignInFragment() }
        showProgress()


        val apiInterface = RetrofitUtils.initRetrofit(ApiInterface::class.java)
        apiInterface.getAvailableBoards().subscribeOn(rx.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ data ->
                    recyclerBoard.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    recyclerBoard.adapter = BoardsRecyclerAdapter(
                            data,
                            object : BoardsRecyclerAdapter.BoardSelectListener {
                                override fun boardSelected(board: Board) {
                                    (activity as SignIn).loadClassListFragment(board)
                                }
                            })
                    hideProgress()

                }, { error ->
                    error.printStackTrace()
                    Snackbar.make(getView()!!, R.string.something_went_wrong, Snackbar.LENGTH_SHORT).show()
                })

    }


    private fun showProgress() {
        progress.visibility = View.VISIBLE
        recyclerBoard.visibility = View.GONE
    }

    private fun hideProgress() {
        progress.visibility = View.GONE
        recyclerBoard.visibility = View.VISIBLE
    }

}