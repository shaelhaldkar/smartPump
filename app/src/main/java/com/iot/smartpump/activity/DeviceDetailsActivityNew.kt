package com.iot.smartpump.activity

import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.iot.smartpump.MainApplication
import com.iot.smartpump.R
import com.iot.smartpump.fragment.DeviceDetailsFragment
import com.iot.smartpump.model.DeviceData
import com.iot.smartpump.utils.MQTTRequestListnerListner
import com.iot.smartpump.utils.MQTTSubscribeListner
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.json.JSONObject

class DeviceDetailsActivityNew : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener, MQTTSubscribeListner, MQTTRequestListnerListner {
   var  TAG=javaClass.name

    override fun onMessageArrived(s: String?, mqttMessage: MqttMessage?) {
        Log.d(TAG, "message Arived")
        Log.d(TAG, "String s$s")
        Log.d(TAG, "MqttMessage mqttMessage$mqttMessage")


    }

    override fun onSubscribe(isScuess: Boolean, iMqttToken: IMqttToken?) {
        if(isScuess){
            var jsonData=JSONObject()
            jsonData.put("sync","/SNS/pump_"+MainApplication.instance!!.getPrefs().getUserId())
               checkDeviceState(deviceData!!,jsonData)
        }else{

        }
    }

    private var deviceData: DeviceData? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_devices)
        val navView = findViewById<BottomNavigationView>(R.id.nav_view)
        deviceData = intent.getParcelableExtra<DeviceData>("data")
        navView.setOnNavigationItemSelectedListener(this)
        val fragment = DeviceDetailsFragment()
        val bundle = Bundle()
        bundle.putParcelable("data", deviceData)
        fragment.arguments = bundle

        changeFragment(fragment)
    }

    private fun checkDeiceStatus(deviceData: DeviceData?) {
        subcribeToChannel()




    }

    private fun subcribeToChannel() {
        MainApplication.instance!!.subscribeToMQTT("/SNS/pump_"+MainApplication.instance!!.getPrefs().getUserId(),this)
    }

    fun checkDeviceState(deviceData: DeviceData, jsonData: JSONObject) {
        MainApplication.instance!!.sendMQTTMessgae(jsonData.toString(), deviceData.DeviceNo,this@DeviceDetailsActivityNew)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        var fragment: Fragment? = null
        when (menuItem.itemId) {
            R.id.nav_home -> fragment = DeviceDetailsFragment()


        }//            case R.id.navigation_dashboard:
        //                fragment= new DashboardFragment();
        //                break;
        //            case R.id.navigation_notifications:
        //                fragment= new NotificationsFragment();
        //                break;
        if (fragment != null) {
            val bundle = Bundle()
            bundle.putParcelable("data", deviceData)
            fragment.arguments = bundle
            changeFragment(fragment)
        }
        return false
    }




    fun changeFragment(fragment: Fragment) {
        val TAG = fragment.javaClass.simpleName
        val fm = supportFragmentManager
        val ts = fm.beginTransaction()
        ts.replace(R.id.nav_device_fragment, fragment, TAG)
        ts.commitAllowingStateLoss()
        //        .getMenu().getItem(0).setChecked(true);

    }
}
