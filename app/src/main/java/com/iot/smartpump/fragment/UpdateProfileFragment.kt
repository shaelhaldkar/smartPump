package com.iot.smartpump.fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iot.smartpump.MainApplication
import com.iot.smartpump.R
import com.iot.smartpump.model.ProfileModel
import com.iot.smartpump.utils.Constants
import com.iot.smartpump.webtask.WebMethods
import com.trackingsystem.webServices.WebResponseListener
import com.trackingsystem.webtask.ParseJson
import kotlinx.android.synthetic.main.fragment_register.*

class UpdateProfileFragment : BaseFragment(), WebResponseListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_register, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnRegister!!.setOnClickListener {
            validateData()
        }
        getUserDetails()
    }

    private fun getUserDetails() {
        showProgressDialog()
        WebMethods().getUserDetail(activity!!, MainApplication.instance!!.getPrefs().getToken(), Constants.WEB_ACTION_GET_USER_DETAILS, this)
    }

    private fun validateData() {
        val firstName = etFirstname!!.text.toString()
        val lastName = etLastName!!.text.toString()
        val mobileNumber = etMobileNumber!!.text.toString()
        val emailAddress = etEmail!!.text.toString()
        if (isValidField(firstName, lastName, mobileNumber, emailAddress)) {
            showProgressDialog()
            var profileModel = ProfileModel()
            profileModel.FirstName = etFirstname.text.toString()
            profileModel.LastName = etLastName.text.toString()
            profileModel.Mobile = etMobileNumber.text.toString()
            profileModel.Email = etEmail.text.toString()
            profileModel.Address = etAddress.text.toString()
            WebMethods().updateUser(activity, Constants.WEB_ACTION_UPDATE_USER, profileModel, this)
        } else {

        }


    }


    private fun isValidField(firstName: String, lastName: String, mobileNumber: String, emailAddress: String): Boolean {

        if (TextUtils.isEmpty(firstName)) {
            etFirstname!!.error = "Your First Name"
            return false
        } else if (TextUtils.isEmpty(lastName)) {
            etLastName!!.error = "Your Last Name"
            return false
        } else if (TextUtils.isEmpty(mobileNumber) || mobileNumber.length < 10) {

            return false
        } else if (TextUtils.isEmpty(emailAddress) || !Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            if (TextUtils.isEmpty(emailAddress)) {
                etEmail!!.error = "Your Email"
            } else {
                etEmail!!.error = "Please Enter Valid Email"
            }
            return false
        }

        return true
    }

    override fun onResponseReceived(error: String?, response: String, tag: String?) {
        Log.i("tag : ", tag)
        Log.i("tag : ", response)
        Log.i("error : ", "" + error)
        activity!!.runOnUiThread {
            hideProgress()
        }

        if (error == null) {

            if (tag.equals(Constants.WEB_ACTION_UPDATE_USER)) {
                if (ParseJson.isSuccess(response)) {
                    activity!!.runOnUiThread {
                        showToast("Profile Updated Successfully ")
                        changeInnerFragment(HomeFragment(), R.id.nav_host_fragment)
                    }

                }

            } else if (tag.equals(Constants.WEB_ACTION_GET_USER_DETAILS)) {
                if (ParseJson.isSuccess(response)) {
                    var profleModel = ParseJson.parseProfile(response)
//
                    if (profleModel != null && !profleModel.passToken.isNullOrEmpty()) {
                        MainApplication.instance!!.getPrefs().saveUserData(profleModel)
                        activity!!.runOnUiThread {
                            setDataOnView(profleModel)
                        }
                    }
                }
            }
        } else {
            showToast(error)
        }

    }

    private fun setDataOnView(profileModel: ProfileModel) {
        etFirstname.setText(profileModel.FirstName)
        etLastName.setText(profileModel.LastName)
        etMobileNumber.setText(profileModel.Mobile)
        etEmail.setText(profileModel.Email)
        etAddress.setText(profileModel.Address)
    }

    //    @Override
    //    public void onResponseReceived(final String error, final String response, final String tag) {
    //        progressDialog.dismiss();
    //        getActivity().runOnUiThread(new Runnable() {
    //            @Override
    //            public void run() {
    //                progressDialog.dismiss();
    //                if (error == null) {
    //                    if (tag.equalsIgnoreCase(Constant.WEB_ACTION_INSET_USER)) {
    //                        login(etEmail.getText().toString(), etPassword.getText().toString());
    //                    } else if (tag.equalsIgnoreCase(Constant.WEB_ACTION_LOGIN)) {
    //                        try {
    //                            LocalDataBase.getInstance().saveUserDetails(new Parser().getUserDetails(response));
    //                            startActivity(new Intent(getActivity(), HomeActivity.class));
    //                            getActivity().finish();
    //                        } catch (JSONException e) {
    //                            e.printStackTrace();
    //                        }
    //                    } else {
    //                        Utility.getInstance().showAlertDialog(getActivity(), error, getResources().getString(R.string.type_error_warning));
    //                    }
    //                } else {
    //                    Utility.getInstance().showAlertDialog(getActivity(), response, getResources().getString(R.string.type_error_warning));
    //                }
    //            }
    //        });
    //    }

    //    private void login(String email, String pass) {
    //        showProgressDialog();
    //        webMethods.getLoginUser(getContext(), email, pass, okHttpRequestCallHandler, this);
    //    }
}