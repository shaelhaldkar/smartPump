package com.iot.smartpump.webtask

import android.content.Context
import android.util.Log


import com.iot.smartpump.MainApplication
import com.trackingsystem.webServices.WebResponseListener

import java.io.IOException

import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.RequestBody

/**
 * Created by shailendra on 3/18/2018.
 */

class OkHttpRequestCallHandler {
        fun getResponseFromJson(mContext: Context, url: String, tag: String, WebResponseListener: WebResponseListener) {
            run {
                //        client.setConnectTimeout(15, TimeUnit.SECONDS); // connect timeout
                //        client.setReadTimeout(15, TimeUnit.SECONDS);

                val request = okhttp3.Request.Builder().url(url).get().build()



                Log.i("Request", "parm : $url")
                MainApplication.application!!.getOkHttpClient().newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        val mMessage = e.message.toString()
                        Log.w("Failure Response", mMessage)
                        call.cancel()
                        WebResponseListener.onResponseReceived("error", mMessage, tag)
                    }

                    @Throws(IOException::class)
                    override fun onResponse(call: Call, response: okhttp3.Response) {
                        val statusCode = response.code()
                        Log.d("FaucetResponse Code", statusCode.toString())
                        if (statusCode == 200) {
                            val PostRequest = response.body().string() //This is the response URL
                            Log.d("PostResponse", PostRequest)

                            WebResponseListener.onResponseReceived(null, PostRequest.toString(), tag)
                            //USE TRY AND CATCH AND PARSE YOUR JSON REQUEST HERE
                        } else if (statusCode == 400) {
                            Log.d("Status code 400", "Bad Request .. Please Enter Valid value")
                            WebResponseListener.onResponseReceived("error", "Bad Request .. Please Enter Valid value", tag)
                        } else if (statusCode == 401) {
                            //                    Log.d("Status code 401", "Session Expired ... Please Try Again");
                            WebResponseListener.onResponseReceived("error", response.message(), tag)
                        } else {
                            WebResponseListener.onResponseReceived("error", response.message(), tag)
                        }
                    }
                })
            }
        }

        fun getResponseFromJsonPost(mContext: Context, url: String, tag: String, parm: String, WebResponseListener: WebResponseListener) {

            Log.i("TAG", "action : $tag")
            Log.i("TAG", "URL_BASE : $url")
            Log.i("TAG", "parm : $parm")

            val contentType = MediaType.parse("application/json; charset=utf-8")
            val formBody = RequestBody.create(contentType, parm)

            val request = okhttp3.Request.Builder().url(url).post(formBody).build()
            Log.i("Request", "parm : $parm")
            MainApplication.application!!.getOkHttpClient().newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    val mMessage = e.message.toString()
                    Log.w("Failure Response", mMessage)
                    call.cancel()
                    WebResponseListener.onResponseReceived("error", mMessage, tag)
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: okhttp3.Response) {
                    val statusCode = response.code()
                    Log.d("FaucetResponse Code", statusCode.toString())
                    if (statusCode == 200) {
                        val PostRequest = response.body().string() //This is the response URL
                        Log.d("PostResponse", PostRequest)

                        WebResponseListener.onResponseReceived(null, PostRequest.toString(), tag)
                        //USE TRY AND CATCH AND PARSE YOUR JSON REQUEST HERE
                    } else if (statusCode == 400) {
                        Log.d("Status code 400", "Bad Request .. Please Enter Valid value")
                        WebResponseListener.onResponseReceived("error", "Bad Request .. Please Enter Valid value", tag)
                    } else if (statusCode == 401) {
                        //                    Log.d("Status code 401", "Session Expired ... Please Try Again");
                        WebResponseListener.onResponseReceived("error", response.message(), tag)
                    } else {
                        WebResponseListener.onResponseReceived("error", response.message(), tag)
                    }
                }
            })
        }

        fun getResponseFromTextPost(mContext: Context, url: String, tag: String, parm: String, WebResponseListener: WebResponseListener) {

            Log.i("TAG", "action : $tag")
            Log.i("TAG", "URL_BASE : $url")
            Log.i("TAG", "parm : $parm")

            val contentType = MediaType.parse("application/octet-stream")
            val formBody = RequestBody.create(contentType, parm)

            val request = okhttp3.Request.Builder().url(url).post(formBody).build()
            Log.i("Request", "parm : $parm")
            MainApplication.application!!.getOkHttpClient().newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    val mMessage = e.message.toString()
                    Log.w("Failure Response", mMessage)
                    call.cancel()
                    WebResponseListener.onResponseReceived("error", mMessage, tag)
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: okhttp3.Response) {
                    val statusCode = response.code()
                    Log.d("FaucetResponse Code", statusCode.toString())
                    if (statusCode == 200) {
                        val PostRequest = response.body().string() //This is the response URL
                        Log.d("PostResponse", PostRequest)

                        WebResponseListener.onResponseReceived(null, PostRequest.toString(), tag)
                        //USE TRY AND CATCH AND PARSE YOUR JSON REQUEST HERE
                    } else if (statusCode == 400) {
                        Log.d("Status code 400", "Bad Request .. Please Enter Valid value")
                        WebResponseListener.onResponseReceived("error", "Bad Request .. Please Enter Valid value", tag)
                    } else if (statusCode == 401) {
                        //                    Log.d("Status code 401", "Session Expired ... Please Try Again");
                        WebResponseListener.onResponseReceived("error", response.message(), tag)
                    } else {
                        WebResponseListener.onResponseReceived("error", response.message(), tag)
                    }
                }
            })
        }

}
