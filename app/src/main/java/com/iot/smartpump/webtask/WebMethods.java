package com.iot.smartpump.webtask;

import android.content.Context;

import com.google.gson.Gson;
import com.iot.smartpump.MainApplication;
import com.iot.smartpump.model.ProfileModel;
import com.iot.smartpump.utils.Constants;
import com.trackingsystem.webServices.WebResponseListener;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by shailendra on 3/18/2018.
 */

public class WebMethods {

    //    public void getLoginUser(Context mContext, String action,String uid, String password, WebResponseListener  WebResponseListener) {
//        try {
//            JSONObject object = new JSONObject();
//            object.put("UID", uid);
//            object.put("Password", password);
//            MainApplication.Companion.getInstance().okHttpRequestCallHandler.getResponseFromJsonPost(mContext,  Constants.URL_BASE +  action,  Constants.WEB_ACTION_LOGIN, object.toString(),  WebResponseListener);
//        } catch (Exception e) {
//            e.getMessage();
//        }
//    }
    public void deleteDevice(Context mContext, String action, String deviceId, WebResponseListener WebResponseListener) {
        try {
            JSONObject object = new JSONObject();
            object.put("ID", deviceId);
            String url = Constants.URL_BASE + action + MainApplication.Companion.getInstance().getPrefs().getToken();
            MainApplication.Companion.getInstance().okHttpRequestCallHandler.getResponseFromJsonPost(mContext, url, action, object.toString(), WebResponseListener);
        } catch (Exception e) {
            e.getMessage();
        }
    }



    public void updateUser(Context mContext, String action, ProfileModel profileModel, WebResponseListener WebResponseListener) {
        try {
            Gson gson = MainApplication.Companion.getInstance().gsonLib();
            String jsonString = gson.toJson(profileModel);

            JSONObject dataJson = new JSONObject(jsonString);
            dataJson.put("ID",null);
            dataJson.put("passToken",null);
            String url = Constants.URL_BASE + action +MainApplication.Companion.getInstance().getPrefs().getToken();

            MainApplication.Companion.getInstance().okHttpRequestCallHandler.getResponseFromJsonPost(mContext, url, action, dataJson.toString(), WebResponseListener);
        } catch (Exception e) {
            e.getMessage();
        }

    }

    public void getOTP(Context mContext, String mobile, String action, WebResponseListener WebResponseListener) {
        String url = Constants.URL_BASE + action + mobile;
        MainApplication.Companion.getInstance().okHttpRequestCallHandler.getResponseFromJson(mContext, url, action, WebResponseListener);

    }

    public void verifyOTP(Context mContext, String mobile, String otp, String action, WebResponseListener WebResponseListener) {
        String url = Constants.URL_BASE + action + mobile + "/" + otp;
        MainApplication.Companion.getInstance().okHttpRequestCallHandler.getResponseFromJson(mContext, url, action, WebResponseListener);

    }


    public void getDevice(Context mContext, String action, WebResponseListener WebResponseListener) {
        String url = Constants.URL_BASE + Constants.WEB_ACTION_GET_DEVICE_LIST + MainApplication.Companion.getInstance().getPrefs().getToken();
        MainApplication.Companion.getInstance().okHttpRequestCallHandler.getResponseFromJson(mContext, url, action, WebResponseListener);
    }
    public void getUserDetail(Context mContext,String UserToaken, String action, WebResponseListener WebResponseListener) {
        String url = Constants.URL_BASE + Constants.WEB_ACTION_GET_USER_DETAILS + UserToaken;
        MainApplication.Companion.getInstance().okHttpRequestCallHandler.getResponseFromJson(mContext, url, action, WebResponseListener);
    }
    public void insertDevice(Context mContext, String action, String DeviceNo,  String deviceName, WebResponseListener WebResponseListener) {
        String url = Constants.URL_BASE + action + MainApplication.Companion.getInstance().getPrefs().getToken();

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("DeviceNo", DeviceNo);// Math.round(Math.random() * 1025));
            jsonObject.put("DeviceName", deviceName);
            jsonObject.put("TypeID", 1);
//            jsonObject.put("ULimit", ULimit);
//            jsonObject.put("LLimit", LLimit);
//            jsonObject.put("CkLimit", CkLimit);
            jsonObject.put("TypeID", 1);
            MainApplication.Companion.getInstance().okHttpRequestCallHandler.getResponseFromJsonPost(mContext, url, Constants.WEB_ACTION_INSERT_DEVICE, jsonObject.toString(), WebResponseListener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void updateDevice(Context mContext, String action, String DeviceNo,  String deviceName, WebResponseListener WebResponseListener) {
        String url = Constants.URL_BASE + action + MainApplication.Companion.getInstance().getPrefs().getToken();

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("DeviceNo", DeviceNo);// Math.round(Math.random() * 1025));
            jsonObject.put("DeviceName", deviceName);
//            jsonObject.put("ULimit", ULimit);
//            jsonObject.put("LLimit", LLimit);
//            jsonObject.put("CkLimit", CkLimit);
            MainApplication.Companion.getInstance().okHttpRequestCallHandler.getResponseFromJsonPost(mContext, url, action, jsonObject.toString(), WebResponseListener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


//    public void uploadImage(Context mContext, String action, String deviceNo, String image, OkHttpRequestCallHandler okHttpRequestCallHandler, WebResponseListener WebResponseListener) {
//        String url = Constants.URL_BASE + action + MainApplication.Companion.getInstance().getPrefs().getToken();
//
//        try {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("DeviceNo", deviceNo);
//            jsonObject.put("Image", image);
//            okHttpRequestCallHandler.getResponseFromJsonPost(mContext, url, Constants.WEB_ACTION_UPDATE_IMAGE, jsonObject.toString(), WebResponseListener);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }


}