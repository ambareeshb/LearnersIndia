package com.gbmainframe.learnersindia.models

/**
 * Created by ambareeshb on 13/04/18.
 */
data class PaymentPackage(val package_id: Int,
                          val package_title: String,
                          val package_desc: String,
                          val package_price_offer: Int,
                          val package_price: Int,
                          val package_price_usd: Int,
                          val package_price_usd_offer: Int,
                          val package_price_kwd: Int,
                          val package_price_kwd_offer: Int,
                          val package_price_aed: Int,
                          val package_price_aed_offer: Int,
                          val package_image: String)