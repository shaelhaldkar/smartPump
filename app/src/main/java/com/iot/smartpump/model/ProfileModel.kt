package com.iot.smartpump.model

import android.os.Parcel
import android.os.Parcelable

class ProfileModel() :Parcelable {
    var passToken=""
    var UserID=""
    var Name=""
    var Mobile=""
    var FirstName=""
    var LastName=""
    var Address=""
    var Email=""

    constructor(parcel: Parcel) : this() {
        passToken = parcel.readString().toString()
        UserID = parcel.readString().toString()
        Name = parcel.readString().toString()
        Mobile = parcel.readString().toString()
        FirstName = parcel.readString().toString()
        LastName = parcel.readString().toString()
        Address = parcel.readString().toString()
        Email = parcel.readString().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(passToken)
        parcel.writeString(UserID)
        parcel.writeString(Name)
        parcel.writeString(Mobile)
        parcel.writeString(FirstName)
        parcel.writeString(LastName)
        parcel.writeString(Address)
        parcel.writeString(Email)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProfileModel> {
        override fun createFromParcel(parcel: Parcel): ProfileModel {
            return ProfileModel(parcel)
        }

        override fun newArray(size: Int): Array<ProfileModel?> {
            return arrayOfNulls(size)
        }
    }
}