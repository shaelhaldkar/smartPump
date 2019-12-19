package com.iot.smartpump.fragment

import android.os.Bundle
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
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.iot.smartpump.webtask.WebMethods
import com.trackingsystem.webServices.WebResponseListener


class DeviceDetailsFragment : BaseFragment(), BottomNavigationView.OnNavigationItemSelectedListener, WebResponseListener {

    private var isAuto = 0
    override fun onResponseReceived(error: String?, response: String, tag: String?) {


    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.getItemId()) {
            R.id.navigation_auto -> if (isAuto == 0) {

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
        val dialog = BottomSheetDialog(activity!!)
        val dialogLayout = layoutInflater.inflate(R.layout.dia_confirmation, null)
        var tv_confirmation_msg=dialogLayout.findViewById<TextView>(R.id.tv_confirmation_msg);
        var btn_cancel=dialogLayout.findViewById<Button>(R.id.btn_cancel);
        var btn_yes=dialogLayout.findViewById<Button>(R.id.btn_yes);
        tv_confirmation_msg.setText("Are you sure to want off auto mode?")
        btn_cancel.setOnClickListener {
            dialog.dismiss()
        }
        btn_yes.setOnClickListener {
            dialog.dismiss()
            doAutoOff(1)
        }

        dialog.setContentView(dialogLayout)
        dialog.show()
    }

    private fun processAutoOn() {
        val dialog = BottomSheetDialog(activity!!)
        val dialogLayout = layoutInflater.inflate(R.layout.dia_devices_auto, null)
        var img_cancel = dialogLayout.findViewById<ImageView>(R.id.img_cancel)
        var ed_ulimit = dialogLayout.findViewById<EditText>(R.id.ed_ulimit)
        var ed_llimit = dialogLayout.findViewById<EditText>(R.id.ed_llimit)
        var btn_save = dialogLayout.findViewById<Button>(R.id.btn_save)
        btn_save.setOnClickListener {
            if (ed_llimit.text.isEmpty()) {

            } else if (ed_ulimit.text.toString().isEmpty()) {

            } else if (!isValidLimit(ed_llimit.text.toString(), ed_ulimit.text.toString())) {
                ed_ulimit.error = "Upper Limit should be greater than Lower Limit"
            } else {
                dialog.dismiss()

                doAutoLimitSet(ed_llimit.text.toString(), ed_ulimit.text.toString())
            }
        }
        dialog.setContentView(dialogLayout)
        img_cancel.setOnClickListener {
            dialog.dismiss()
        }


        dialog.show()
    }

    private fun doAutoLimitSet(lllimt: String, ulimit: String) {
        var data = JSONObject()
        data.put("limitOn", lllimt.toInt())
        data.put("limitOff", ulimit.toInt())
        MainApplication.instance!!.sendMQTTMessgae(data.toString(), deviceData.DeviceNo)
        doAutoOff(1)
    }

    private fun doAutoOff(i: Int) {
        var data1 = JSONObject()
        data1.put("CkLimit", i)
        MainApplication.instance!!.sendMQTTMessgae(data1.toString(), deviceData.DeviceNo)
        isAuto = if(isAuto ==1 )0 else 1

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
        tv_device_name.setText(MainApplication.instance!!.getPrefs().getUserFName() + " " + MainApplication.instance!!.getPrefs().getUserLName())
        img_edit.setOnClickListener {
            showAddDeviceDialog(deviceData, Constants.WEB_ACTION_UPDATE_DEVICE_NAME)
        }
        val navigation = activity!!.findViewById(R.id.nav_view) as BottomNavigationView
        navigation.setOnNavigationItemSelectedListener(this)
    }

    private fun showAddDeviceDialog(model: DeviceData?, action: String) {
        val builder = AlertDialog.Builder(activity!!)
        val dialogLayout = layoutInflater.inflate(R.layout.dia_devices_registration, null)
        var ed_DeviceNo = dialogLayout.findViewById<EditText>(R.id.ed_DeviceNo)
        var ed_device_name = dialogLayout.findViewById<EditText>(R.id.ed_device_name)
        var ed_llimit = dialogLayout.findViewById<EditText>(R.id.ed_llimit)
        var ed_ulimit = dialogLayout.findViewById<EditText>(R.id.ed_ulimit)
        var ed_ck_limit = dialogLayout.findViewById<CheckBox>(R.id.chbx_ck)
        var alertTitle = dialogLayout.findViewById<TextView>(R.id.alertTitle)
        var btn_save = dialogLayout.findViewById<Button>(R.id.btn_save)
        var img_cancel = dialogLayout.findViewById<ImageView>(R.id.img_cancel)
        alertTitle.setText("Add Device")
        if (action.equals(Constants.WEB_ACTION_UPDATE_DEVICE_NAME)) {
            alertTitle.setText("Update Device")
            ed_DeviceNo.isEnabled = false
            ed_DeviceNo.setText("" + model!!.DeviceNo)
            ed_device_name.setText("" + model!!.DeviceName)
            ed_llimit.setText("" + model.LLimit)


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
            var cklimit = 0
            if (ed_ck_limit.isChecked) {
                cklimit = 1
            }
            WebMethods().insertDevice(activity!!, Constants.WEB_ACTION_INSERT_DEVICE, ed_DeviceNo.text.toString(), ed_device_name.text.toString(), ed_ulimit.text.toString(), ed_llimit.text.toString(), cklimit, this)

        }
        img_cancel.setOnClickListener {
            alertDialog.dismiss()

        }
    }

    private fun doOnOff() {
        if (imgbtn_on_off.tag.toString().equals(getString(R.string.tag_on), true)) {
            imgbtn_on_off.setImageResource(R.mipmap.off)
            imgbtn_on_off.setTag(getString(R.string.tag_off))
            deviceData.isOn = 0
        } else {
            imgbtn_on_off.setImageResource(R.mipmap.on)
            imgbtn_on_off.setTag(getString(R.string.tag_on))
            deviceData.isOn = 1
        }
        var data = JSONObject()
        data.put(Constants.MQTT_METHOD_STATUS, deviceData.isOn)
        sendDataOnOff(deviceData, data)
    }

    fun sendDataOnOff(deviceData: DeviceData, jsonData: JSONObject) {
        MainApplication.instance!!.sendMQTTMessgae(jsonData.toString(), deviceData.DeviceNo)
    }


}
