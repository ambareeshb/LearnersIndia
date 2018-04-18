package com.gbmainframe.learnersindia.models

data class PaymentHash( val result:String,
                        val status:Int,
                        val error_code:String,
                        val response_code:String)