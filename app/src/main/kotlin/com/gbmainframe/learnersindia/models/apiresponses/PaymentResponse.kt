package com.gbmainframe.learnersindia.models.apiresponses

/**
 * Created by ambareeshb on 30/03/18.
 */
data class PaymentResponse(val response_type: String,
                           val response_text: String,
                           val response_code: String,
                           val payment_status: String)