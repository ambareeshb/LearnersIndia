package com.gbmainframe.learnersindia.fragments

import android.opengl.Visibility
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
import com.gbmainframe.learnersindia.adapters.ClassRecyclerAdapter
import com.gbmainframe.learnersindia.models.Board
import com.gbmainframe.learnersindia.utils.ApiInterface
import com.gbmainframe.learnersindia.utils.RetrofitUtils
import kotlinx.android.synthetic.main.layout_select_class.*
import kotlinx.android.synthetic.main.toolbar.*
import rx.android.schedulers.AndroidSchedulers


/**
 * Created by ambareeshb on 26/03/18.
 */
class SelectClassFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.layout_select_class, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backButton.setOnClickListener { activity?.onBackPressed() }
        toolbarButton.setOnClickListener { (activity as SignIn).loadSignInFragment() }
        showProgress()


        val apiInterface = RetrofitUtils.initRetrofit(ApiInterface::class.java)
        apiInterface.getAvailableClasses().subscribeOn(rx.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ data ->
                    recyclerClass.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    recyclerClass.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
                    recyclerClass.adapter = ClassRecyclerAdapter(data, { classInfo ->
                        (activity as SignIn).loadSignUpFragment(board = arguments?.getParcelable(SignIn.BOARD_PARCEL) as Board, classInfo = classInfo)
                    })
                    hideProgress()
                }, { error ->
                    error.printStackTrace()
                    Snackbar.make(getView()!!, R.string.something_went_wrong, Snackbar.LENGTH_SHORT).show()
                })
    }



    private fun showProgress() {
        progress.visibility = View.VISIBLE
        recyclerClass.visibility = View.GONE
    }
    private fun hideProgress() {
        progress.visibility = View.GONE
        recyclerClass.visibility = View.VISIBLE
    }
}
