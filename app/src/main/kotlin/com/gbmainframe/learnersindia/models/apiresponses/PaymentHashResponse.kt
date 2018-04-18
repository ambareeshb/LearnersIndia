package com.gbmainframe.learnersindia.models.apiresponses

import com.gbmainframe.learnersindia.models.BaseApiModel
import com.gbmainframe.learnersindia.models.PaymentHash

data class PaymentHashResponse(val response_data: PaymentHash,
                               val key: String,
                               val salt: String,
                               val txnId: String,
                               val amount: String,
                               val productName: String,
                               val firstName: String,
                               val email: String,
                               val udf1: String,
                               val udf2: String,
                               val udf3: String,
                               val udf4: String,
                               val udf5: String) : BaseApiModel()