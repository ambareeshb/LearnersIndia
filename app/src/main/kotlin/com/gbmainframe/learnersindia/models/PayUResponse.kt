package com.gbmainframe.learnersindia.models

data class PayUResponse(val response_type: String,
                        val response_data: String,
                        val token: String,
                        val package_id: Int)