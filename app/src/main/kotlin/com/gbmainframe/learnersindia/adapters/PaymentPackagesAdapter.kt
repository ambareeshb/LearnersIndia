package com.gbmainframe.learnersindia.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.models.PaymentPackage
import kotlinx.android.synthetic.main.payment_packages.view.*

class PaymentPackagesAdapter(private val packageList: ArrayList<PaymentPackage>,
                             private val packageSelected:(PaymentPackage)-> Unit) : RecyclerView.Adapter<PaymentPackagesAdapter.ViewHolder>() {
    companion object {
        const val domain = "learnersindia.com/"
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.payment_packages, parent, false))

    override fun getItemCount(): Int = packageList.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindView(packageList[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(paymentPackage: PaymentPackage) {
            itemView.paymentPackageName.text = paymentPackage.package_title
            Glide.with(itemView.paymentPackageLogo)
                    .load(domain + paymentPackage.package_image)
            itemView.buttonCheckout.setOnClickListener {
                packageSelected(paymentPackage)
            }
            itemView.setOnClickListener {
                packageSelected(paymentPackage)
            }
            //USD
            when (paymentPackage.package_price_usd_offer) {
                0 -> {
                    itemView.priceUsdActualPrice.text = paymentPackage.package_price_usd.toString()
                    itemView.priceUsdOfferPrice.text = paymentPackage.package_price_usd_offer.toString()
                    itemView.usdDummyPrice.visibility = View.VISIBLE
                }
                else -> {
                    itemView.priceUsdOfferPrice.text = paymentPackage.package_price_usd.toString()
                    itemView.usdDummyPrice.visibility = View.GONE
                }
            }
            //KWD
            when (paymentPackage.package_price_kwd_offer) {
                0 -> {
                    itemView.kwdDummyPrice.visibility = View.VISIBLE
                    itemView.priceKwdOfferPrice.text = paymentPackage.package_price_kwd.toString()
                }
                else -> {
                    itemView.priceKwdActualPrice.text = paymentPackage.package_price_kwd.toString()
                    itemView.priceKwdOfferPrice.text = paymentPackage.package_price_kwd_offer.toString()
                    itemView.kwdDummyPrice.visibility = View.GONE
                }
            }

            //AED
            when (paymentPackage.package_price_aed_offer) {
                0 -> {
                    itemView.aedDummyPrice.visibility = View.VISIBLE
                    itemView.priceAedOfferPrice.text = paymentPackage.package_price_kwd.toString()
                }
                else -> {
                    itemView.priceAedActualPrice.text = paymentPackage.package_price_aed.toString()
                    itemView.priceAedOfferPrice.text = paymentPackage.package_price_aed_offer.toString()
                    itemView.aedDummyPrice.visibility = View.GONE
                }
            }
            //INR
            when (paymentPackage.package_price_offer) {
                0 -> {
                    itemView.aedDummyPrice.visibility = View.VISIBLE
                    itemView.priceInrOfferPrice.text = paymentPackage.package_price.toString()
                }
                else -> {
                    itemView.priceInrActualPrice.text = paymentPackage.package_price.toString()
                    itemView.priceInrOfferPrice.text = paymentPackage.package_price_offer.toString()
                    itemView.inrDummyPrice.visibility = View.GONE
                }
            }
        }

    }
}