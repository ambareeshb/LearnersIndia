package com.gbmainframe.learnersindia.activities

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.adapters.RecommendedQuestionsAdapter
import com.gbmainframe.learnersindia.utils.ApiInterface
import com.gbmainframe.learnersindia.utils.RetrofitUtils
import com.gbmainframe.learnersindia.utils.sharedPrefManager
import kotlinx.android.synthetic.main.activity_search_activiity.*
import kotlinx.android.synthetic.main.toolbar_search.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
        setContentView(R.layout.activity_search_activiity)

        backButton.setOnClickListener { super.onBackPressed() }
        searchbox.setOnSearchClickListener {
        }
        searchbox.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                hideSoftKeyboard()
                search(query!!)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

    }

    /**
     * When a keyword is passed search it down.
     */
    fun search(keyword: String) {
        questionListSearch.visibility = View.VISIBLE
        questionBankSearch.visibility = View.VISIBLE
        questionUploadSearch.visibility = View.VISIBLE


        val user = sharedPrefManager.getUser(this)
        user?.let {
            progressSearch1.visibility = View.VISIBLE
            progressSearch2.visibility = View.VISIBLE
            progressSearch3.visibility = View.VISIBLE
            RetrofitUtils.initRetrofit(ApiInterface::class.java).search(keyword = keyword,
                    sylId = user.syl_id.toInt(),
                    classId = 10,
                    subId = 1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        progressSearch1.visibility = View.GONE
                        progressSearch2.visibility = View.GONE
                        progressSearch3.visibility = View.GONE
                        if (it == null || it.response_type == getString(R.string.response_type_error)) {
                            noContentAvailable1.visibility = View.VISIBLE
                            noContentAvailable2.visibility = View.VISIBLE
                            noContentAvailable3.visibility = View.VISIBLE
                            return@subscribe
                        }

                        if (it.question_ask_data == null) {
                            noContentAvailable1.visibility = View.VISIBLE
                            noContentAvailable2.visibility = View.VISIBLE
                            noContentAvailable3.visibility = View.VISIBLE
                            return@subscribe
                        }
                        recyclerSearchResult.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                        recyclerSearchResult.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
                        recyclerSearchResult.adapter = RecommendedQuestionsAdapter(it.question_ask_data!!, {})
                        noContentAvailable2.visibility = View.VISIBLE
                        noContentAvailable3.visibility = View.VISIBLE

                    }, { error ->
                        error.printStackTrace()
                        noContentAvailable1.visibility = View.VISIBLE
                        noContentAvailable2.visibility = View.VISIBLE
                        noContentAvailable3.visibility = View.VISIBLE
                    })
        }

    }

    /**
     * Hide softkey board
     */
    fun hideSoftKeyboard() {
        this.currentFocus?.let {
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                    .hideSoftInputFromInputMethod(it.windowToken, 0)
        }
    }

}
