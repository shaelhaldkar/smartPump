package com.iot.smartpump.database

import android.content.Context
import android.content.SharedPreferences
import com.iot.smartpump.BuildConfig
import com.iot.smartpump.MainApplication
import com.iot.smartpump.model.ProfileModel

/**
 * Created by pradeep on 16/05/18.
Mail id : pradeep.kumar@inventum.net
 */
class AppPrefs {


    private val editor: SharedPreferences.Editor
    private val preference: SharedPreferences
    private val KEY_TOKEN = "token";
    private val KEY_USER_FIRST_NAME = "first_name";
    private val KEY_USER_LAST_NAME = "last_name";
    private val KEY_USER_MOBILE = "mobile";
    private val KEY_USER_EMAIL = "email";
    private val KEY_USER_ADDRESS = "email";
    private val KEY_USER_ID= "id";

//    state_name:String,city_name:String,postcode:String




    private val NAME = BuildConfig.APPLICATION_ID

    init {
        preference = MainApplication.instance!!.getSharedPreferences(NAME, Context.MODE_PRIVATE)
        editor = preference.edit()
    }

    fun clearLocalData() {
        preference.edit().clear().commit()
    }

    fun setToken(token: String?) {
        editor.putString(KEY_TOKEN, token).apply()
    }





    fun saveUserData(profileModel: ProfileModel){
        editor.putString(KEY_USER_ID, profileModel.ID).apply()
        editor.putString(KEY_USER_FIRST_NAME, profileModel.FirstName).apply()
        editor.putString(KEY_USER_LAST_NAME, profileModel.LastName).apply()
        editor.putString(KEY_USER_EMAIL, profileModel.Email).apply()
        editor.putString(KEY_USER_ADDRESS, profileModel.Address).apply()
        editor.putString(KEY_USER_MOBILE, profileModel.Mobile).apply()
        editor.putString(KEY_TOKEN, profileModel.passToken).apply()


    }
//    fun setUserName(token: String?) {
//        editor.putString(KEY_TOKEN, token).apply()
//    }
    fun getUserId(): String {
        return preference.getString(KEY_USER_ID,"").toString()
    }



//    fun setProfilePic(token: String?) {
//        editor.putString(KEY_PROFILE_PIC, token).apply()
//    }
//    fun getProfilePic(): String {
//        return preference.getString(KEY_PROFILE_PIC, "")
//    }
//
//    fun getMobile(): String {
//        return preference.getString(KEY_USER_MOBILE, "")
//    }
//
    fun getToken(): String? {
        return preference.getString(KEY_TOKEN, "")
    }
    fun getUserFName(): String? {
        return preference.getString(KEY_USER_FIRST_NAME, "")
    }
    fun getUserLName(): String? {
        return preference.getString(KEY_USER_LAST_NAME, "")
    }
    fun getUserAddress(): String? {
        return preference.getString(KEY_USER_ADDRESS, "")
    }
    fun getUserEmail(): String? {
        return preference.getString(KEY_USER_EMAIL, "")
    }
    fun getUserPhone(): String? {
        return preference.getString(KEY_USER_MOBILE, "")
    }
    fun getUserName(): String? {
        return preference.getString(KEY_USER_FIRST_NAME, "")+" "+preference.getString(KEY_USER_LAST_NAME, "")
    }


//
////    fun saveLoginData(profileModel: ProfileModel) {
//////        val jsonObject = JSONObject(response).optJSONObject("response").optJSONObject("result");
////        setToken(profileModel.token)
////        setUserName(profileModel.))
//////saveBarCategory(jsonObject)
////    }
//
//    fun saveBarCategory(response:JSONObject) {
//        val jsonObject = response.optJSONArray("bareact_categories")
//        editor.putString(KEY_BAR_ACT_CATEGORY, jsonObject.toString()).apply()
//    }
//


}