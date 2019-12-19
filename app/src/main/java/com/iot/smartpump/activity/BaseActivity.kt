package com.iot.smartpump.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import cc.cloudist.acplibrary.ACProgressFlower
import com.iot.smartpump.MainApplication
import com.iot.smartpump.R
import com.trackingsystem.utils.Utils
import com.trackingsystem.utils.Utils.Companion.showProgressDialog


open class BaseActivity : AppCompatActivity() {
    private lateinit var progressBar: ACProgressFlower

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initProgrgessbar()
    }

    fun setupUI(view: View) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (view !is EditText) {
            view.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View, event: MotionEvent): Boolean {
                    hideSoftKeyboard(this@BaseActivity!!)
                    return false
                }
            })
        }


        //If a layout container, iterate over children and seed recursion.
        if (view is ViewGroup) {
            for (i in 0 until (view as ViewGroup).childCount) {
                val innerView = (view as ViewGroup).getChildAt(i)
                setupUI(innerView)
            }
        }
    }

        private fun initProgrgessbar() {
        progressBar = showProgressDialog(this)
    }
    public fun showProgressDialog(){
        progressBar.show()

    }
    public fun hideProgress(){
        progressBar.hide()
    }
    @SuppressLint("NewApi")
    fun hideSoftKeyboard(activity: Activity) {
        try {

            val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

            inputMethodManager!!.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //    public fun checkLogoutCondition(respose:String):Boolean{
//        if(JSONObject(respose).getJSONObject("response").getString("status").equals("302")){
//            doLogout()
//            return true
//        }else{
//            return false
//        }
//    }
//
//    private fun doLogout() {
//        val intent = Intent(this, LoginActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        MainApplication.instance!!.getPrefs().clearLocalData()
//        startActivity(intent)
//    }
    public fun showToast(msg: String) {
        MainApplication.instance!!.showToast(msg)
    }
//    public fun askPermission() {
//        val permissions = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.ACCESS_FINE_LOCATION)
//
//        askCompactPermissions(permissions, object : PermissionResult {
//
//            override fun permissionGranted() {
////                MainApplication.instance?.getMyLocation()
//            }
//
//            override fun permissionDenied() {
//
//            }
//
//            override fun permissionForeverDenied() {
//            }
//        })
//    }
}