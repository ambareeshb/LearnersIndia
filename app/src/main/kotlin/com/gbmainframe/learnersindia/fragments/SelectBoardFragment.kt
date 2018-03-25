package com.gbmainframe.learnersindia.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gbmainframe.learnersindia.R
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by ambareeshb on 25/03/18.
 */
class SelectBoardFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.layout_select_board, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backButton.setOnClickListener{activity?.onBackPressed()}


//        val apiInterface = RetrofitUtils.initRetrofit(ApiInterface::class.java)
//        apiInterface.getAvailableBoards().
//                subscribeOn(rx.schedulers.Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({data ->
//                    recyclerBoard.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
//                    recyclerBoard.adapter = BoardsRecyclerAdapter(data)
//                },{
//                    error ->
//                    error.printStackTrace()
//                    Snackbar.make(getView()!!,R.string.something_went_wrong,Snackbar.LENGTH_SHORT).show()
//                })
    }

}