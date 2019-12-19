package com.iot.smartpump.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import cc.cloudist.acplibrary.ACProgressFlower
import com.trackingsystem.utils.Utils

open class BaseFragment : Fragment() {
    private lateinit var progressBar: ACProgressFlower

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initProgrgessbar()
    }

    fun setupUI(view: View) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (view !is EditText) {
            view.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View, event: MotionEvent): Boolean {
                    hideSoftKeyboard(activity!!)
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

    fun showToast(msg: String) {
        activity!!.runOnUiThread {
            Toast.makeText(activity!!, "" + msg, Toast.LENGTH_LONG).show()
        }

    }

    private fun initProgrgessbar() {
        progressBar = Utils.showProgressDialog(activity!!)
    }

    public fun showProgressDialog() {
        activity!!.runOnUiThread {
            progressBar.show()
        }
    }

    public fun hideProgress() {

        activity!!.runOnUiThread {
            progressBar.hide()
        }

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

    protected fun changeInnerFragment(fragment: Fragment, resourceId: Int) {
        val fm = fragmentManager
        val TAG = fragment.javaClass.name
        val ts = fm!!.beginTransaction()
        ts.add(resourceId, fragment, TAG)
        ts.addToBackStack(TAG)
        ts.commit()
    }


}