package com.gbmainframe.learnersindia.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.activities.SignIn
import com.gbmainframe.learnersindia.utils.sharedPrefManager
import kotlinx.android.synthetic.main.layout_user_profile.*

/**
 * Created by ambareeshb on 27/03/18.
 * Profile screen for the user.
 */
class ProfileFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.layout_user_profile, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logout.setOnClickListener {
            AlertDialog.Builder(context).setTitle("Logout").setMessage(R.string.logoutMessage)
                    .setPositiveButton(R.string.yes, { _, _ ->
                        context?.let {
                            sharedPrefManager.logoutUser(it)
                            startActivity(Intent(activity, SignIn::class.java))
                        }
                    })
                    .setNegativeButton(R.string.no, { _, _ ->
                        //Do noting means to dismiss the dialog.
                    }).show()
        }
        //Show user info in the profile display.
        context?.let {
            val user = sharedPrefManager.getUser(it)
            name.text = user.full_name
            val board = when (user.syl_id.toInt()) {
                1 -> "CBSE"
                3 -> "Kerala"
                4 -> "TN"
                else -> "CBSE"

            }
            className.text = ("$board ${user.cls_id}")
        }

    }
}