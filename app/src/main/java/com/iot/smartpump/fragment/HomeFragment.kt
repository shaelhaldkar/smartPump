package com.iot.smartpump.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iot.smartpump.MainApplication

import com.iot.smartpump.R
import com.iot.smartpump.activity.DeviceDetailsActivityNew
import com.iot.smartpump.adapter.DeviceListRecyclerViewAdapter
import com.iot.smartpump.model.DeviceData
import com.iot.smartpump.utils.Constants
import com.iot.smartpump.utils.RecyclerItemClickListener
import com.iot.smartpump.webtask.WebMethods
import com.trackingsystem.webServices.WebResponseListener
import com.trackingsystem.webtask.ParseJson
import kotlinx.android.synthetic.main.dia_devices_registration.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONObject

class HomeFragment : BaseFragment(), WebResponseListener {
    override fun onResponseReceived(error: String?, response: String, tag: String?) {
        Log.i("tag : ",""+ tag)
        Log.i("response : ", response)
        Log.i("error : ", "" + error)

        hideProgress()
        activity!!.runOnUiThread {
            if (error == null || !error!!.equals("error", true)) {
                if (tag.equals(Constants.WEB_ACTION_GET_DEVICE_LIST)) {
                    if (ParseJson.isSuccess(response)) {
                        var list = ParseJson.parseDevice(response)
                        adapter.list = list
                    }
                } else if (tag.equals(Constants.WEB_ACTION_INSERT_DEVICE)) {
                    if (ParseJson.isSuccess(response)) {
                        getDevicelist()
                    }
                } else if (tag.equals(Constants.WEB_ACTION_DELETE_DEVICE)) {
                    if (ParseJson.isSuccess(response)) {
                        getDevicelist()
                    }
                }
            }
        }
    }

    lateinit var adapter: DeviceListRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = view.context
        val recyclerView = view.findViewById<RecyclerView>(R.id.rec_devices)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = DeviceListRecyclerViewAdapter(ArrayList<DeviceData>(), activity!!)
        recyclerView.adapter = adapter
        tv_owner_name.setText(MainApplication.instance!!.getPrefs().getUserName())
        img_add_device.setOnClickListener { showAddDeviceDialog(null, Constants.WEB_ACTION_INSERT_DEVICE) }
        getDevicelist()
        recyclerView.addOnItemTouchListener(RecyclerItemClickListener(activity!!, object : RecyclerItemClickListener.OnItemClickListener {
            override fun onItemLongPress(childView: View?, position: Int) {


            }

            override fun onItemClick(childView: View?, position: Int) {
//                if (childView!!.id == R.id.img_delete) {
//                    showDeletDeviceConfirmDialog(adapter.list.get(position))
//                } else if (childView!!.id == R.id.img_update) {
//                    showAddDeviceDialog(adapter.list.get(position), Constants.WEB_ACTION_UPDATE_DEVICE_NAME)
//                } else
                    if (childView!!.id == R.id.img_device) {
                    var deviceData = adapter.list.get(position)
                    var data = JSONObject()

                    var deviceStatus = 0
                    if (deviceData.isOn == 0) {
                        deviceStatus = 1
                    }
                    data.put(Constants.MQTT_METHOD_STATUS, deviceStatus)

                    MainApplication.instance!!.sendMQTTMessgae(data.toString(), deviceData.DeviceNo)
                    adapter.list.get(position).isOn = deviceStatus
                    adapter.notifyDataSetChanged()
                } else {
                    var intent = Intent(activity, DeviceDetailsActivityNew::class.java)
                    intent.putExtra("data", adapter.list.get(position))
                    startActivity(intent)
                }
            }

        }))
    }

    private fun getDevicelist() {
        showProgressDialog()
        WebMethods().getDevice(activity!!, Constants.WEB_ACTION_GET_DEVICE_LIST, this)
    }

    private fun showAddDeviceDialog(model: DeviceData?, action: String) {
        val builder = AlertDialog.Builder(activity!!)
        val dialogLayout = layoutInflater.inflate(R.layout.dia_devices_registration, null)
        var ed_DeviceNo = dialogLayout.findViewById<EditText>(R.id.ed_DeviceNo)
        var ed_device_name = dialogLayout.findViewById<EditText>(R.id.ed_device_name)
        var ed_llimit = dialogLayout.findViewById<EditText>(R.id.ed_llimit)
        var ed_ulimit = dialogLayout.findViewById<EditText>(R.id.ed_ulimit)
        var ck_limit = dialogLayout.findViewById<CheckBox>(R.id.chbx_ck)
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
            var ss = 1
            if (ck_limit.isChecked) {
                ss = 1
            } else {
                ss = 0
            }
            showProgressDialog()
            WebMethods().insertDevice(activity!!, Constants.WEB_ACTION_INSERT_DEVICE, ed_DeviceNo.text.toString(),  ed_device_name.text.toString(), ed_ulimit.text.toString(), ed_llimit.text.toString(), ss, this)

        }
        img_cancel.setOnClickListener {
            alertDialog.dismiss()

        }
    }

    private fun showDeletDeviceConfirmDialog(model: DeviceData) {
        val builder = AlertDialog.Builder(activity!!)
        builder.setTitle(getString(R.string.confirmation))
        var msg = getString(R.string.conf_ms) + " : " + model.DeviceName
        builder.setMessage(getString(R.string.conf_ms))
        builder.setCancelable(true)
        builder.setPositiveButton(getString(R.string.yes)) { p0, p1 ->
            showProgressDialog()
            WebMethods().deleteDevice(activity!!, Constants.WEB_ACTION_DELETE_DEVICE, model.ID, this@HomeFragment)
        }
        builder.setNegativeButton(getString(R.string.no)) { p0, p1 -> }
        val alertDialog = builder.show()
//        dialogLayout.setOnClickListener {
//            alertDialog.dismiss()
//            showProgressDialog()
//            WebMethods().insertDevice(activity!!, Constants.WEB_ACTION_INSERT_DEVICE, ed_DeviceNo.text.toString(), ed_device_key.text.toString(), ed_device_name.text.toString(), ed_ulimit.text.toString(), ed_llimit.text.toString(), ed_ck_limit.text.toString(), this)
//        }
    }
}