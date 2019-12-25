package com.iot.smartpump.activity


import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import com.iot.smartpump.MainApplication
import com.iot.smartpump.R
import com.iot.smartpump.model.ProfileModel
import com.iot.smartpump.utils.Constants
import com.iot.smartpump.webtask.WebMethods
import com.trackingsystem.webServices.WebResponseListener
import com.trackingsystem.webtask.ParseJson
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterActivity : BaseActivity(), WebResponseListener {
    private lateinit var profileModel: ProfileModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_register)

        btnRegister!!.setOnClickListener {
            validateData()

        }
        getUserDetails()
    }

    private fun getUserDetails() {
        showProgressDialog()
        WebMethods().getUserDetail(this, MainApplication.instance!!.getPrefs().getToken(), Constants.WEB_ACTION_GET_USER_DETAILS, this)
    }

    private fun validateData() {
        val firstName = etFirstname!!.text.toString()
        val lastName = etLastName!!.text.toString()
        val mobileNumber = etMobileNumber!!.text.toString()
        val emailAddress = etEmail!!.text.toString()
        if (isValidField(firstName, lastName, mobileNumber, emailAddress)) {
            showProgressDialog()
            profileModel = ProfileModel()
            profileModel.FirstName = etFirstname.text.toString()
            profileModel.LastName = etLastName.text.toString()
            profileModel.Mobile = etMobileNumber.text.toString()
            profileModel.Email = etEmail.text.toString()
            profileModel.Address = etAddress.text.toString()
            WebMethods().updateUser(this, Constants.WEB_ACTION_UPDATE_USER, profileModel, this)
        } else {

        }


    }

    private fun isValidField(firstName: String, lastName: String, mobileNumber: String, emailAddress: String): Boolean {

        if (TextUtils.isEmpty(firstName)) {
            etFirstname!!.error = "Your First Name"
            return false
        } else if (TextUtils.isEmpty(lastName)) {
            etLastName!!.error = "Your Last Name"
            return false
        } else if (TextUtils.isEmpty(mobileNumber) || mobileNumber.length < 10) {

            return false
        } else if (TextUtils.isEmpty(emailAddress) || !Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            if (TextUtils.isEmpty(emailAddress)) {
                etEmail!!.error = "Your Email"
            } else {
                etEmail!!.error = "Please Enter Valid Email"
            }
            return false
        }

        return true
    }

    override fun onResponseReceived(error: String?, response: String, tag: String?) {
        Log.i("tag : ", tag)
        Log.i("tag : ", response)
        Log.i("error : ", "" + error)
        runOnUiThread {
            hideProgress()
        }
        if (error == null ) {
            if (tag.equals(Constants.WEB_ACTION_UPDATE_USER)) {
                profileModel.passToken = MainApplication.instance!!.getPrefs().getToken().toString()
                if (ParseJson.isSuccess(response)) MainApplication.instance!!.getPrefs().saveUserData(profileModel)
                runOnUiThread {
                    showToast("Profile Updated Successfully ")
                    startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
                    finish()
                }
            } else if (tag.equals(Constants.WEB_ACTION_GET_USER_DETAILS)) {
                if (ParseJson.isSuccess(response)) {
                    profileModel = ParseJson.parseProfile(response)
//
                    if (profileModel != null && !profileModel.passToken.isNullOrEmpty()) {
                        MainApplication.instance!!.getPrefs().saveUserData(profileModel)
                        runOnUiThread {
                            setDataOnView(profileModel)
                        }
                    }
                }
            }
        } else {
            showToast(error)
        }
    }

    private fun setDataOnView(profileModel: ProfileModel) {
        etFirstname.setText(profileModel.FirstName)
        etLastName.setText(profileModel.LastName)
        etMobileNumber.setText(profileModel.Mobile)
        etEmail.setText(profileModel.Email)
        etAddress.setText(profileModel.Address)
    }
}