package com.gbmainframe.learnersindia.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.models.TestModel
import kotlinx.android.synthetic.main.layout_single_test.view.*

/**
 * Created by ambareeshb on 09/04/18.
 */
class TestListAdapter(private val testList: ArrayList<TestModel>,
                      private val testSelected: (TestModel) -> Unit) : RecyclerView.Adapter<TestListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.layout_single_test, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindView(testList[position])
    }

    override fun getItemCount(): Int = testList.size


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(test: TestModel) {
            itemView.testName.text = test.chapter
            itemView.setOnClickListener {
                testSelected(test)
            }
        }
    }
}