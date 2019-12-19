package com.iot.smartpump.utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by abc on 7/23/2018.
 */

public class Parser {
    public void getLoginData(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        AppLog.info(AppLog.getMethodName(), "jobj : " + jsonObject);
    }

//    public UserDetails getUserDetails(String response) throws JSONException {
//        Gson gson = new GsonBuilder().create();
//        String data = response.replace("\\", "");
//        String formatedString = data.substring(1, data.length() - 1);
//        JSONObject jsonObject1 = new JSONObject(formatedString);
//        return gson.fromJson(jsonObject1.toString(), UserDetails.class);
//    }
//
//    public ArrayList<DeviceModel> getDevice(String response) throws JSONException {
//        String data = response.replace("\\", "");
//        String formatedString = data.substring(1, data.length() - 1);
//        JSONObject jsonObject = new JSONObject(formatedString);
//        if (jsonObject.getBoolean("Transation")) {
//            JSONArray jsonObject1 = jsonObject.getJSONArray("data");
//            Gson gson = new GsonBuilder().create();
//            return gson.fromJson(jsonObject1.toString(),
//                    new TypeToken<ArrayList<DeviceModel>>() {
//                    }.getType());
//        } else {
//            return new ArrayList<>();
//        }
//
//    }
//
//    public static FindGameModel getGameList(String response) throws JSONException {
//        Gson gson = new GsonBuilder().create();
//        return gson.fromJson(response,
//                new TypeToken<FindGameModel>() {
//                }.getType());
//
//    }
//
//    public static ArrayList<Games> getReviewList(String response) throws JSONException {
//        String data = response.replace("\\", "");
//        String formatedString = data.substring(1, data.length() - 1);
//        JSONObject jsonObject = new JSONObject(formatedString);
//        if (jsonObject.getBoolean("Transation")) {
//            JSONArray jsonObject1 = jsonObject.getJSONArray("data");
//            Gson gson = new GsonBuilder().create();
//            return gson.fromJson(jsonObject1.toString(),
//                    new TypeToken<ArrayList<Games>>() {
//                    }.getType());
//        } else {
//            return new ArrayList<>();
//        }
//    }

}
