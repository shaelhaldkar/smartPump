package com.iot.smartpump


import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.iot.smartpump.database.AppPrefs
import com.iot.smartpump.mqttrequest.MqttMessageService
import com.iot.smartpump.mqttrequest.PahoMqttClient
import com.iot.smartpump.utils.Constants
import com.iot.smartpump.utils.NetworkChangeReceiver
import com.iot.smartpump.webtask.OkHttpRequestCallHandler
import com.trackingsystem.webServices.VolleyRequestKotlin
import okhttp3.OkHttpClient
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.MqttException
import java.io.UnsupportedEncodingException
import java.util.concurrent.TimeUnit


/**
 * Created by Shailendra on 22-April-18.
 */

class MainApplication : Application() {
    private var pahoMqttClient: PahoMqttClient? = null
    private var client: MqttAndroidClient? = null
    private var mRequestQueue: RequestQueue? = null
    private val TAG = MainApplication::class.java.simpleName
    private var volleyRequest: VolleyRequestKotlin? = null
    private var prefs: AppPrefs? = null
    lateinit var mOkHttpClient: OkHttpClient
  lateinit var   okHttpRequestCallHandler:OkHttpRequestCallHandler

    override fun onCreate() {
        super.onCreate()
        instance = this

            application = this


          var   OkHttpClient = OkHttpClient().newBuilder()//
        OkHttpClient.connectTimeout(15, TimeUnit.SECONDS); // connect timeout
        OkHttpClient.readTimeout(15, TimeUnit.SECONDS);
        mOkHttpClient=OkHttpClient.build()

        // connect timeout
            //            OkHttpClient client = new OkHttpClient()
        okHttpRequestCallHandler=OkHttpRequestCallHandler()

        val intent = Intent(this, MqttMessageService::class.java)
        startService(intent)
        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(NetworkChangeReceiver(), intentFilter)
        inintMqtt()
    }

    private fun inintMqtt() {
        if (pahoMqttClient == null) {
            pahoMqttClient = PahoMqttClient()
        }
        if (client == null) {
            client = pahoMqttClient!!.getMqttClient(applicationContext, Constants.MQTT_BROKER_URL, Constants.CLIENT_ID)
        }

    }

    fun subscribeToMQTT(topic: String) {
        try {
            pahoMqttClient!!.subscribe(client!!, topic, 0)
        } catch (e: MqttException) {
            e.printStackTrace()
        }

    }

    fun sendMQTTMessgae(msg: String,deviceId:String) {
        Log.e("Mqtt Message ", "" + msg)
        Log.e("deviceId" , "" + deviceId)
        Log.e("request url" , "" + Constants.PUBLISH_TOPIC+deviceId)
        if (!msg.isEmpty()) {
            try {

                Log.e("Mqtt connect ", "" + client!!.isConnected)
                pahoMqttClient!!.publishMessage(client!!, msg, 0, Constants.PUBLISH_TOPIC+deviceId)
            } catch (e: MqttException) {
                e.printStackTrace()
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }

        }
    }


    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
    }

    companion object {

        var application: MainApplication? = null

        //    public UploadImageListener uploadImageListener;

        var instance: MainApplication? = null
            private set
    }

    private var gson: Gson? = null
    fun gsonLib(): Gson {
        if (gson == null) {
            gson = Gson()
        }
        return gson!!
    }


    fun getPrefs(): AppPrefs {
        if (prefs == null) prefs = AppPrefs()
        return prefs!!
    }


    fun getVolleyRequest(): VolleyRequestKotlin? {
        if (volleyRequest == null) {
            volleyRequest = VolleyRequestKotlin()
        }
        return volleyRequest
    }

    fun showToast(message: String) {

        Toast.makeText(instance, message, Toast.LENGTH_SHORT).show()
    }

    fun isNetworkAvailable(): Boolean {
        val connectivityManager = instance!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    fun getRequestQueue(): RequestQueue {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(applicationContext)
        }
        return mRequestQueue!!
    }

    fun <T> addToRequestQueue(req: Request<T>, tag: String) {
        req.tag = if (TextUtils.isEmpty(tag)) TAG else tag
        getRequestQueue().add(req)
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        req.tag = TAG
        getRequestQueue().add(req)
    }

    fun cancelPendingRequests(tag: Any) {
        if (mRequestQueue != null) {
            mRequestQueue!!.cancelAll(tag)
        }
    }

    fun getOkHttpClient(): OkHttpClient {
        return mOkHttpClient;
    }

}