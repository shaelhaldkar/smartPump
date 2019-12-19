package com.trackingsystem.webtask


import com.google.gson.reflect.TypeToken
import com.iot.smartpump.MainApplication
import com.iot.smartpump.model.ProfileModel
import com.iot.smartpump.model.DeviceData
import org.json.JSONObject

class ParseJson {
    companion object {
        fun isSuccess(jsonData: String?): Boolean {
            val jsonObject = JSONObject(jsonData!!)
            if (jsonObject.optInt("status") == 200 || jsonObject.optBoolean("Transation")) {

                return true
            } else {
                var msg = jsonObject.optString("Messase")
//                if (msg == null || msg.isEmpty()) {
//                    msg = jsonObject.optString("message")
//                }

                MainApplication.application!!.showToast(msg)
            }
            return false
        }

//        fun parseState(response: String?): ArrayList<StateModel> {
//            val data = JSONObject(response).optJSONArray("state").toString();
//            val gson = MainApplication.instance?.gsonLib()
//            val type = object : TypeToken<List<StateModel>>() {}.type
//            return gson!!.fromJson<ArrayList<StateModel>>(data, type)
//        }
//
        fun parseProfile(response: String?): ProfileModel {
            val jsonData = JSONObject(response!!);
            val gson = MainApplication.application?.gsonLib()
            return gson!!.fromJson(jsonData.toString(), ProfileModel::class.java)
        }
//        fun parseProfileFromLogin(response: String?): ProfileModel {
//            val jsonData = JSONObject(response).optJSONObject("data");
//            val gson = MainApplication.instance?.gsonLib()
//            return gson!!.fromJson(jsonData.toString(), ProfileModel::class.java)
//        }
//
//
//        fun parseUpdateProfileFromJSONObject(response: String?): ProfileModel {
//            val jsonData = JSONObject(response).optJSONObject("userdata")
//            val gson = MainApplication.instance?.gsonLib()
//            return gson!!.fromJson(jsonData.toString(), ProfileModel::class.java)
//        }
//
//
//        fun parseCustomerDtails(response: String?): CustomerDetailsModel {
//            val jsonData = JSONObject(response)
//            val gson = MainApplication.instance?.gsonLib()
//            return gson!!.fromJson(jsonData.toString(), CustomerDetailsModel::class.java)
//        }
//
        fun parseDevice(response: String): ArrayList<DeviceData> {
            val data = JSONObject(response).optJSONArray("data")!!.toString();
            val gson = MainApplication.application?.gsonLib()
            val type = object : TypeToken<List<DeviceData>>() {}.type
            return gson!!.fromJson<ArrayList<DeviceData>>(data, type)
        }
//        fun parseDisrtibutor(response: String?): ArrayList<DistributorModel> {
//            val data = JSONObject(response).optJSONArray("data").toString();
//            val gson = MainApplication.instance?.gsonLib()
//            val type = object : TypeToken<List<DistributorModel>>() {}.type
//            return gson!!.fromJson<ArrayList<DistributorModel>>(data, type)
//        }
//        fun parseCustomerList(response: String?): ArrayList<CustomerModel> {
//            val data = JSONObject(response).optJSONArray("data").toString();
//            val gson = MainApplication.instance?.gsonLib()
//            val type = object : TypeToken<List<CustomerModel>>() {}.type
//            return gson!!.fromJson<ArrayList<CustomerModel>>(data, type)
//        }
//        fun getPurchasedCouponList(response: String?): ArrayList<CouponModel> {
//            val data = JSONObject(response).optJSONArray("data").toString();
//            val gson = MainApplication.instance?.gsonLib()
//            val type = object : TypeToken<List<CouponModel>>() {}.type
//            return gson!!.fromJson<ArrayList<CouponModel>>(data, type)
//        }
//        fun getNearByDistributor(response: String?): ArrayList<DistributorModel> {
//            val data = JSONObject(response).optJSONArray("data").toString();
//            val gson = MainApplication.instance?.gsonLib()
//            val type = object : TypeToken<List<DistributorModel>>() {}.type
//            return gson!!.fromJson<ArrayList<DistributorModel>>(data, type)
//        }
//        fun getWinnerList(response: String?): ArrayList<WinnerModel> {
//            val data = JSONObject(response).optJSONArray("data").toString();
//            val gson = MainApplication.instance?.gsonLib()
//            val type = object : TypeToken<List<WinnerModel>>() {}.type
//            return gson!!.fromJson<ArrayList<WinnerModel>>(data, type)
//        }
//
////        fun parseTrainingList(response: String?): ArrayList<TrainingTypeModel> {
////            val data = JSONObject(response).optJSONArray("data").toString();
////            val gson = MainApplication.instance?.gsonLib()
////            val type = object : TypeToken<List<TrainingTypeModel>>() {}.type
////            return gson!!.fromJson<ArrayList<TrainingTypeModel>>(data, type)
////        }
////
////        fun parseTrainingDetails(jsonData: JSONObject?): TrainingTypeModel {
//////            val data = JSONObject(response);
////            val gson = MainApplication.instance?.gsonLib()
////            return gson!!.fromJson(jsonData.toString(), TrainingTypeModel::class.java)
////        }
////        fun parseBankDetails(jsonData: String?): BankDetails {
////            val data = JSONObject(jsonData).optString("data");
////            val gson = MainApplication.instance?.gsonLib()
////            return gson!!.fromJson(data.toString(), BankDetails::class.java)
////        }
////        fun getFSRDetails(response: String?):FSRDetailsModel{
////            val gson = MainApplication.instance?.gsonLib()
////
////            return gson!!.fromJson(JSONObject(response).optJSONObject("response").optJSONArray("result").get(0).toString(), FSRDetailsModel::class.java)
////        }
////
//        //
////
//
//fun getDescriptionArray(arrayList: ArrayList<DescriptionModel>):JSONArray{
//    val gson = MainApplication.instance?.gsonLib()
//    return  JSONArray(gson?.toJson(arrayList))
//}


    }
}