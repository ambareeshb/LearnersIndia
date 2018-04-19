package com.gbmainframe.learnersindia.models

data class PayUResponse(val response_type: String,
                        val response_date: String,
                        val token: String,
                        val package_id: Int)