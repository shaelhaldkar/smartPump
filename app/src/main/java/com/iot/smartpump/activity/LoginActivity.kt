package com.iot.smartpump.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.iot.smartpump.MainApplication
import com.iot.smartpump.R
import com.iot.smartpump.utils.Constants
import com.iot.smartpump.utils.PinEntryEditText
import com.iot.smartpump.webtask.WebMethods
import com.trackingsystem.webServices.WebResponseListener
import com.trackingsystem.webtask.ParseJson
import kotlinx.android.synthetic.main.frg_login.*

class LoginActivity : BaseActivity(), WebResponseListener {
    override fun onResponseReceived(error: String?, response: String, tag: String?) {
        Log.i("tag : ", tag)
        Log.i("response : ", response)
        Log.i("error : ", "" + error)
        runOnUiThread {
            hideProgress()
        }
        if (error == null || !error!!.equals("error", true)) {
            runOnUiThread {
                if (tag.equals(Constants.WEB_ACTION_OTP_GENERATE)) {
                    if (ParseJson.isSuccess(response)) {
                        showOtpDialog("", "")
                    }
                } else if (tag.equals(Constants.WEB_ACTION_VERIFY_OTP)) {
                    var profleModel = ParseJson.parseProfile(response)
                    if (profleModel != null && !profleModel.passToken.isNullOrEmpty()) {
                        profleModel.Mobile=ed_mobile.text.toString()
                        MainApplication.instance!!.getPrefs().saveUserData(profleModel)
                        startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
                        finish()
                    }

                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.frg_login)
        if(!MainApplication.instance!!.getPrefs().getToken().toString().isEmpty()){
            startActivity(Intent(this@LoginActivity,MainActivity::class.java))
            finish()
        }
        btn_get_otp.setOnClickListener {
            validateAndLogin()
        }

    }

    private fun showOtpDialog(title: String, msg: String) {
        val builder = android.app.AlertDialog.Builder(this)
        val dialogLayout = layoutInflater.inflate(R.layout.dia_verify_otp, null)
        val tv_opt_mobile = dialogLayout.findViewById(R.id.tv_opt_mobile) as TextView
        val tv_retry = dialogLayout.findViewById(R.id.tv_retry) as TextView
        val btn_verify_otp = dialogLayout.findViewById(R.id.btn_verify_otp) as Button
        val ed_otp = dialogLayout.findViewById(R.id.ed_otp) as PinEntryEditText

        builder.setView(dialogLayout)
        builder.setCancelable(true)
        tv_opt_mobile.append(ed_mobile.text.toString())
        val alertDialog = builder.create()
        tv_retry.setOnClickListener {
            hideProgress()
            validateAndLogin()
        }
        btn_verify_otp.setOnClickListener {
            if (ed_otp.text.toString().isEmpty()) {
                ed_otp.error = "Please Enter OTP"

            } else {
                alertDialog.dismiss()
                showProgressDialog()
                WebMethods().verifyOTP(this, ed_mobile.text.toString(), ed_otp.text.toString(), Constants.WEB_ACTION_VERIFY_OTP, this)
            }
        }
        alertDialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialog.show()
        dialogLayout.setOnClickListener { alertDialog.dismiss() }

    }

    private fun validateAndLogin() {
        if (ed_mobile.text.toString().isEmpty()) {
            ed_mobile.error = "Please Enter 10 Digit Mobile Number"
        } else {
            showProgressDialog()

            WebMethods().getOTP(this, ed_mobile.text.toString(), Constants.WEB_ACTION_OTP_GENERATE, this)
//                VolleyMethodsKotlin.getLoginSignUp(ed_mobile.text.toString(), ed_pwd.text.toString(), Constants.WEB_ACTION_LOGIN,this)
        }


    }


}