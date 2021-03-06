package com.gbmainframe.learnersindia.views

import android.content.Context
import android.support.design.widget.Snackbar
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.utils.ApiInterface
import com.gbmainframe.learnersindia.utils.RetrofitUtils
import com.gbmainframe.learnersindia.utils.sharedPrefManager
import kotlinx.android.synthetic.main.go_to_premium_fragment.view.*
import kotlinx.android.synthetic.main.premium_card.view.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class GoPremiumView : RelativeLayout {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.premium_card, this)
        val user = sharedPrefManager.getUser(context)
        progress.visibility = View.VISIBLE
        premiumText.visibility = View.GONE
        tryPremium.visibility = View.GONE

        RetrofitUtils.initRetrofit(ApiInterface::class.java)
                .checkPaidStatus(user.tocken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    progress.visibility = View.GONE
                    if (it.response_type == context.getString(R.string.response_type_error)) {
                        premiumText.text = context.getString(R.string.unlock_rest_premium)
                        premiumText.visibility = View.VISIBLE
                        tryPremium.visibility = View.VISIBLE
                        return@subscribe
                    }
                    if (it.payment_status != "unpaid") {
                        premiumText.text = context.getString(R.string.you_are_premium)
                        premiumText.visibility = View.VISIBLE
                        tryPremium.visibility = View.GONE
                    } else {
                        progress.visibility = View.GONE
                        premiumText.text = context.getString(R.string.unlock_rest_premium)
                        tryPremium.visibility = View.VISIBLE
                    }

                },
                        { error ->
                            premiumText.text = context.getString(R.string.unlock_rest_premium)
                            tryPremium.visibility = View.VISIBLE
                            error.printStackTrace()
                        }
                )
    }
}
