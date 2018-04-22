package com.gbmainframe.learnersindia.utils

import android.content.Context
import android.widget.Toast
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.models.PayUResponse
import com.gbmainframe.learnersindia.models.apiresponses.PaymentResponse
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class PaymentApiCall {
    companion object {
        fun submitPaymentResponse(context: Context, response: PayUResponse) {
            RetrofitUtils.initRetrofit(ApiInterface::class.java)
                    .payUMoneyResult(response = response)
                    .subscribeOn(Schedulers.io())
                    .subscribe({}, {
                        it.printStackTrace()
                    })

        }
    }
}