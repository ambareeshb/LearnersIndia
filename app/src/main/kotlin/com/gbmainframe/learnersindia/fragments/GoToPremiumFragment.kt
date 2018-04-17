package com.gbmainframe.learnersindia.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.utils.FragmentUtils
import kotlinx.android.synthetic.main.go_to_premium_fragment.*

/**
 * Created by ambareeshb on 13/04/18.
 */
class GoToPremiumFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.go_to_premium_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        tryPremium.setOnClickListener {
            fragmentManager?.let {
                FragmentUtils(it).beginTransaction().replace(R.id.fragmentContainer, PaymentPackagesFragment())
                        .commit()
            }
        }

    }
}