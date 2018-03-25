package com.gbmainframe.learnersindia.fragments

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gbmainframe.learnersindia.R
import kotlinx.android.synthetic.main.layout_sign_up.*

/**
 * Created by ambareeshb on 25/03/18.
 */
class SignUpFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.layout_sign_up, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isEmpty(fullName.text, emailText.text, passwordText.text, passwordRetype.text, classText.text, boardText.text, textPhoneNumber.text)) {
            Snackbar.make(view, R.string.error_fields_empty, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun isEmpty(vararg text: Editable) = text.none {
        it.isEmpty()
    }


}