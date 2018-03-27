package com.gbmainframe.learnersindia.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gbmainframe.learnersindia.R
import com.gbmainframe.learnersindia.activities.Home
import com.gbmainframe.learnersindia.utils.ApiInterface
import com.gbmainframe.learnersindia.utils.RetrofitUtils
import com.gbmainframe.learnersindia.utils.sharedPrefManager
import kotlinx.android.synthetic.main.ask_question_toolbar.*
import kotlinx.android.synthetic.main.layout_ask_question.*
import pub.devrel.easypermissions.EasyPermissions
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by ambareeshb on 27/03/18.
 */

class AskQuestionFragment : Fragment(), EasyPermissions.PermissionCallbacks {
    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Snackbar.make(view!!, R.string.please_allow_permission, Snackbar.LENGTH_SHORT).show()
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        setListeners()
    }

    companion object {
        const val TAG = "AskFragment"
        const val RC_GALLERY = 10003
        const val RC_CAMERA = 10008
        const val RC_STORAGE = 10009
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.layout_ask_question, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        EasyPermissions.requestPermissions(this,
                getString(R.string.storage_permission_rationale),
                RC_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        toolbarCloseButton.setOnClickListener {
            (activity as Home).popBackStack()
        }
        toolBarDoneButton.setOnClickListener {
            if (askQuestionText.text.isBlank()) {
                Snackbar.make(view, R.string.ask_question_empty, Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            //Call Ask question API.
            context?.let {
                val user = sharedPrefManager.getUser(it)
                toolBarDoneButton.isEnabled = false
                progress.visibility = View.VISIBLE

                RetrofitUtils.initRetrofit(ApiInterface::class.java).askQuestion(
                        token = user.tocken,
                        sylId = user.syl_id.toInt(),
                        classId = user.cls_id.toInt(),
                        details = askQuestionText.text.toString()).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ response ->
                            progress.visibility = View.GONE

                            Snackbar.make(view, response.response_text, Snackbar.LENGTH_SHORT).show()
                            if (response.response_type == "error") {
                                toolBarDoneButton.isEnabled = true
                                return@subscribe
                            }
                            (activity as Home).loadHomeFeedFragment()
                        }
                                ,
                                {
                                    toolBarDoneButton.isEnabled = true
                                    progress.visibility = View.GONE
                                    it.printStackTrace()
                                    Snackbar.make(view, R.string.something_went_wrong, Snackbar.LENGTH_SHORT).show()
                                }

                        )
            }


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) {
            Snackbar.make(view!!, R.string.something_went_wrong, Snackbar.LENGTH_SHORT).show()
            return
        }
        data?.let {
            val dataImage = data.extras["data"]
            when(dataImage){
                is Bitmap -> Log.d("DATA_IMAGE","Bitmap")
                is Uri -> Log.d("DATA_IMAGE","URI")
                else -> Log.d("DATA_IMAGE","SOME thing else")
            }
            Log.d("Test","Test")
            when (requestCode) {
                RC_CAMERA -> {
                    buttonAskQuestionAttachment.setImageBitmap(dataImage as Bitmap)
                }
                RC_GALLERY -> {
                    buttonAskQuestionAttachment.setImageURI(null)
                }
            }
        }
    }

    /**
     * Listener for gallery and camera.
     */
    private fun setListeners() {
        //On pressing gallery button.
        buttonGallery.setOnClickListener {
            startActivityForResult(Intent().apply {
                action = Intent.ACTION_PICK
                type = "image/*"
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }, RC_GALLERY
            )
        }
        //On pressing camera button.
        buttonCamera.setOnClickListener {
            startActivityForResult(
                    Intent().apply {
                        action = MediaStore.ACTION_IMAGE_CAPTURE
                    }, RC_CAMERA
            )
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, activity as Home)
        setListeners()
    }
}