package com.iot.smartpump.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.iot.smartpump.MainApplication
import com.iot.smartpump.R
import com.iot.smartpump.model.DeviceData
import com.iot.smartpump.utils.Constants
import kotlinx.android.synthetic.main.frg_device_details.*
import kotlinx.android.synthetic.main.header_device.*
import org.json.JSONObject
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.iot.smartpump.utils.MQTTRequestListnerListner
import com.iot.smartpump.utils.MQTTSubscribeListner
import com.iot.smartpump.webtask.WebMethods
import com.trackingsystem.webServices.WebResponseListener
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttMessage


class DeviceDetailsFragment : BaseFragment(), BottomNavigationView.OnNavigationItemSelectedListener, WebResponseListener, MQTTSubscribeListner, MQTTRequestListnerListner {
    private lateinit var menuItem_timer: MenuItem
    private lateinit var menuItem_auto: MenuItem
    var TAG = javaClass.name
    var action = ""
    var CONSTT_SYNC = "sync"
    @ExperimentalStdlibApi
    override fun onMessageArrived(s: String?, mqttMessage: MqttMessage?) {
        Log.d(TAG, "frg message Arived")
        Log.d(TAG, "frg String $s")
//        Log.d(TAG, "frg payload ${mqttMessage!!.payload.decodeToString()}")
//        Log.d(TAG, "frg MqttMessage mqttMessage${mqttMessage.toString()}")
        hideProgress()
        if (!s!!.isNullOrEmpty() && mqttMessage != null ) {

            //{"state":0,"CkLimit":1,"limitOn":10,"limitOff":12}
            if( JSONObject(s).has(CONSTT_SYNC)) {
                var jsonData = JSONObject(mqttMessage.toString())
                deviceData.CkLimit = jsonData.optInt("CkLimit")
                deviceData.LLimit = jsonData.optInt("limitOn")
                deviceData.ULimit = jsonData.optInt("limitOff")
                deviceData.state = jsonData.optInt("state")
                if (deviceData.state == 1) {
                    imgbtn_on_off.setImageResource(R.mipmap.off)
                } else {
                    imgbtn_on_off.setImageResource(R.mipmap.on)
                }
                if(deviceData.CkLimit==1){
                    menuItem_auto.setChecked(true)
                    doEnableDisableOnOffBtn(false)
                }else{
                    doEnableDisableOnOffBtn(true)
                }
            }else{
                showErrorDialog()
            }
        } else {
            if( JSONObject(s).has(CONSTT_SYNC)) {
                showErrorDialog()
            }
        }

    }

    private fun showErrorDialog() {
        val dialog = AlertDialog.Builder(activity!!)
        val dialogLayout = layoutInflater.inflate(R.layout.dia_device_offline, null)
        dialogLayout.setOnClickListener {
            activity!!.onBackPressed()
        }
        dialog.setView(dialogLayout)
        var alert = dialog.show()
    }

    override fun onSubscribe(isScuess: Boolean, iMqttToken: IMqttToken?) {
        if (isScuess) {

            var jsonData = JSONObject()
            jsonData.put("sync", "/SNS/pump_" + MainApplication.instance!!.getPrefs().getUserId())
            checkDeviceState(deviceData!!, jsonData)
        } else {
            hideProgress()
        }
    }

    private var updatedDdevice_name = ""

    override fun onResponseReceived(error: String?, response: String, tag: String?) {
        Log.i("tag : ", tag)
        Log.i("response : ", response)
        Log.i("error : ", "" + error)
        activity?.runOnUiThread {
            hideProgress()
            if (error == null || !error!!.equals("error", true)) {
                if (tag.equals(Constants.WEB_ACTION_UPDATE_DEVICE_NAME, true)) {
                    deviceData.DeviceName = updatedDdevice_name
                    tv_device_name.setText("" + deviceData.DeviceName)
                }

            }
        }


    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.getItemId()) {
            R.id.navigation_auto -> if (deviceData.CkLimit == 0) {
                processAutoOn()
            } else {
                showConfirmationDialog()
            }
//            R.id.navigation_on_off -> {
//                doOnOff()
//                if (p0.title.toString().equals(activity!!.getString(R.string.tag_off))) {
//                    p0.setTitle(activity!!.getString(R.string.tag_on))
//                } else {
//                    p0.setTitle(activity!!.getString(R.string.tag_off))
//                }

//            }
        }
        return true
    }

    private fun showConfirmationDialog() {
        val dialog = AlertDialog.Builder(activity!!)
        val dialogLayout = layoutInflater.inflate(R.layout.dia_confirmation, null)
        var tv_confirmation_msg = dialogLayout.findViewById<TextView>(R.id.tv_confirmation_msg);
        var btn_cancel = dialogLayout.findViewById<Button>(R.id.btn_cancel);
        var btn_yes = dialogLayout.findViewById<Button>(R.id.btn_yes);
        tv_confirmation_msg.setText("Are you sure to want off auto mode?")
        dialog.setView(dialogLayout)
        dialog.setPositiveButton("Yes", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                doAutoOff()
                doEnableDisableOnOffBtn(true)
                p0!!.dismiss()
            }

        })
        dialog.setNegativeButton("Cancel", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                p0!!.dismiss()
            }

        })
        var alert = dialog.show()


    }

    private fun processAutoOn() {
        val dialog = AlertDialog.Builder(activity!!)
        val dialogLayout = layoutInflater.inflate(R.layout.dia_devices_auto, null)
        var img_cancel = dialogLayout.findViewById<ImageView>(R.id.img_cancel)
        var ed_ulimit = dialogLayout.findViewById<EditText>(R.id.ed_ulimit)
        var ed_llimit = dialogLayout.findViewById<EditText>(R.id.ed_llimit)
        var btn_save = dialogLayout.findViewById<Button>(R.id.btn_save)
        ed_ulimit.setText("" + deviceData.ULimit)
        ed_llimit.setText("" + deviceData.LLimit)
        ed_ulimit.setSelection(ed_ulimit.text.length)
        ed_llimit.setSelection(ed_llimit.text.length)
        dialog.setView(dialogLayout)
        var alertDialog = dialog.show()
        btn_save.setOnClickListener {
            if (ed_llimit.text.isEmpty()) {
                ed_llimit.setError("Please Enter Lower Limit")
            } else if (ed_ulimit.text.toString().isEmpty()) {
                ed_llimit.setError("Please Enter Upper Limit")
            } else if (!isValidLimit(ed_llimit.text.toString(), ed_ulimit.text.toString())) {
                ed_ulimit.error = "Upper Limit should be greater than Lower Limit"
            } else {
                alertDialog.dismiss()

                doAutoLimitSet(ed_llimit.text.toString(), ed_ulimit.text.toString())
                doEnableDisableOnOffBtn(false)
            }
        }

        img_cancel.setOnClickListener {
            alertDialog.dismiss()
        }


    }

    private fun doAutoLimitSet(lllimt: String, ulimit: String) {
        var data = JSONObject()
        data.put("limitOn", lllimt.toInt())
        data.put("limitOff", ulimit.toInt())
        MainApplication.instance!!.sendMQTTMessgae(data.toString(), deviceData.DeviceNo, this)
        doAutoOff()
    }

    private fun doAutoOff() {

        var data1 = JSONObject()
       if( deviceData.CkLimit==0){
           deviceData.CkLimit=1
       }else{
           deviceData.CkLimit=0
       }
        data1.put("CkLimit", deviceData.CkLimit)
        MainApplication.instance!!.sendMQTTMessgae(data1.toString(), deviceData.DeviceNo, this)


    }

    private fun isValidLimit(llimit: String, ulimit: String): Boolean {
        var lLmit = llimit.toInt();
        var uLimit = ulimit.toInt();
        if (lLmit < uLimit) {
            return true
        }
        return false
    }

    private lateinit var deviceData: DeviceData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frg_device_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imgbtn_on_off.setOnClickListener {
            doOnOff()
        }
        deviceData = arguments?.getParcelable<DeviceData>("data")!!
        img_back.setOnClickListener {
            activity!!.onBackPressed()
        }
//        ll_timer.setOnClickListener { }
        imgbtn_on_off.setOnClickListener {
            doOnOff()
        }
        tv_device_name.setText("" + deviceData.DeviceName)
        img_edit.setOnClickListener {
            showAddDeviceDialog(deviceData, Constants.WEB_ACTION_UPDATE_DEVICE_NAME)
        }
        val navigation = activity!!.findViewById(R.id.nav_view) as BottomNavigationView
        navigation.setOnNavigationItemSelectedListener(this)
       var menu= navigation.menu
         menuItem_auto=menu.findItem(R.id.navigation_auto)
         menuItem_timer=menu.findItem(R.id.navigation_time)
        menuItem_timer.setChecked(false)

        checkDeiceStatus(deviceData)


    }

    private fun checkDeiceStatus(deviceData: DeviceData?) {
        subcribeToChannel()


    }

    private fun subcribeToChannel() {
        showProgressDialog()
        MainApplication.instance!!.subscribeToMQTT("/SNS/pump_" + MainApplication.instance!!.getPrefs().getUserId(), this@DeviceDetailsFragment)
    }

    fun checkDeviceState(deviceData: DeviceData, jsonData: JSONObject) {
        MainApplication.instance!!.sendMQTTMessgae(jsonData.toString(), deviceData.DeviceNo, this@DeviceDetailsFragment)
    }

    private fun showAddDeviceDialog(model: DeviceData?, action: String) {
        val builder = AlertDialog.Builder(activity!!)
        val dialogLayout = layoutInflater.inflate(R.layout.dia_devices_registration, null)
        var ed_DeviceNo = dialogLayout.findViewById<EditText>(R.id.ed_DeviceNo)
        var ed_device_name = dialogLayout.findViewById<EditText>(R.id.ed_device_name)
//        var ed_llimit = dialogLayout.findViewById<EditText>(R.id.ed_llimit)
        var ed_ulimit = dialogLayout.findViewById<EditText>(R.id.ed_ulimit)
//        var ed_ck_limit = dialogLayout.findViewById<CheckBox>(R.id.chbx_ck)
        var alertTitle = dialogLayout.findViewById<TextView>(R.id.alertTitle)
        var btn_save = dialogLayout.findViewById<Button>(R.id.btn_save)
        var img_cancel = dialogLayout.findViewById<ImageView>(R.id.img_cancel)
        alertTitle.setText("Add Device")
        if (action.equals(Constants.WEB_ACTION_UPDATE_DEVICE_NAME)) {
            alertTitle.setText("Update Device")
            btn_save.setText("Update")
            ed_DeviceNo.isEnabled = false
            ed_DeviceNo.setText("" + model!!.DeviceNo)
            ed_device_name.setText("" + model!!.DeviceName)
//            ed_llimit.setText("" + model.LLimit)


        }
        builder.setView(dialogLayout)
        builder.setCancelable(false)
        val alertDialog = builder.show()
//        dialogLayout.setOnClickListener {
//            alertDialog.dismiss()
//             }
        btn_save.setOnClickListener {
            alertDialog.dismiss()
            showProgressDialog()
//            var cklimit = 0
//            if (ed_ck_limit.isChecked) {
//                cklimit = 1
//            }
            updatedDdevice_name = ed_device_name.text.toString()
            WebMethods().updateDevice(activity!!, action, ed_DeviceNo.text.toString(), updatedDdevice_name, this)

        }
        img_cancel.setOnClickListener {
            alertDialog.dismiss()

        }
    }

    private fun doOnOff() {
        if (deviceData.state == 1) {
            imgbtn_on_off.setImageResource(R.mipmap.off)
            deviceData.state = 0
        } else {
            imgbtn_on_off.setImageResource(R.mipmap.on)
            deviceData.state = 1
        }
        var data = JSONObject()
        data.put(Constants.MQTT_METHOD_STATUS, deviceData.state)
        sendDataOnOff(deviceData, data)
    }

    fun sendDataOnOff(deviceData: DeviceData, jsonData: JSONObject) {
        MainApplication.instance!!.sendMQTTMessgae(jsonData.toString(), deviceData.DeviceNo, this)
    }


    fun doEnableDisableOnOffBtn(boolean: Boolean) {
        imgbtn_on_off.isClickable = boolean
        imgbtn_on_off.isEnabled = boolean
        if (boolean) {
            imgbtn_on_off.alpha = 1.0f
            menuItem_auto.setCheckable(false)
        } else {
            imgbtn_on_off.alpha = 0.5f
            menuItem_auto.setCheckable(true)
        }

    }
}
