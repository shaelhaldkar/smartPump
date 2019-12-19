package com.trackingsystem.utils

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AlertDialog
import cc.cloudist.acplibrary.ACProgressConstant
import cc.cloudist.acplibrary.ACProgressFlower
import com.iot.smartpump.MainApplication
import com.iot.smartpump.R
import com.iot.smartpump.activity.LoginActivity
import java.util.*


class Utils {

    companion object {


        fun showProgressDialog(context: Context): ACProgressFlower {
            val dialog = ACProgressFlower.Builder(context)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)                .fadeColor(Color.DKGRAY).build()
//            dialog.show()
            dialog.setCancelable(false)
            return dialog
        }

        fun setLocale(lang: String) {
            val myLocale = Locale(lang)
            val res = MainApplication.instance!!.getResources()
            val dm = res.getDisplayMetrics()
            val conf = res.getConfiguration()
            conf.locale = myLocale
            res.updateConfiguration(conf, dm)
        }

        fun isValidEmail(target: String): Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()

        fun isOnline(): Boolean {
            return try {
                val command = "ping -c 1 google.com"
                Runtime.getRuntime().exec(command).waitFor() == 0
            } catch (e: Exception) {
                false
            }
        }

        fun showAlertDialog(mContext: Context, message: String, title: String) {
            val builder = AlertDialog.Builder(mContext)
            builder.setTitle(title).setMessage(message).setCancelable(false).setIcon(android.R.drawable.ic_dialog_info).setCancelable(false).setPositiveButton("OK") { dialog, id ->
                dialog.cancel()
                //finish();
            }

            val alert = builder.create()
//            alert.show()
        }

        fun showExpireTokenLogOutDialog(activity: Activity) {

            val message = "Token has been expired, Please login again."
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("Warning!!")
            builder.setMessage(message)
            builder.setCancelable(false)
            builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
            builder.setPositiveButton("Ok") { dialog, which ->
                dialog.cancel()
                MainApplication.instance!!.getPrefs().clearLocalData()
                var intent = Intent(activity.baseContext, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                activity.startActivity(intent)
                activity.finish()

//            showProgress()
//            VolleyMethodsKotlin.getlogout(Constants.WEB_ACTION_LOGOUT, this)

                //                MainActivity.super.onBackPressed();
            }

            val alertDialog = builder.create()
            alertDialog.setOnShowListener {
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(activity.resources.getColor(R.color.black))
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(activity.resources.getColor(R.color.black))
            }
            alertDialog.show()
        }
    }
}