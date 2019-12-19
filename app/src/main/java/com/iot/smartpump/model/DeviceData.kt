package com.iot.smartpump.model

import android.os.Parcel
import android.os.Parcelable

class DeviceData() :Parcelable{
    var ID=""
    var DeviceNo=""
    var DeviceKey=""
    var DeviceName=""
    var DImage=""
    var LLimit=""
    var CkLimit=""
    var ULimit=""
    var isOn=0

    constructor(parcel: Parcel) : this() {
        ID = parcel.readString().toString()
        DeviceNo = parcel.readString().toString()
        DeviceKey = parcel.readString().toString()
        DeviceName = parcel.readString().toString()
        DImage = parcel.readString().toString()
        LLimit = parcel.readString().toString()
        CkLimit = parcel.readString().toString()
        ULimit = parcel.readString().toString()
        isOn = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(ID)
        parcel.writeString(DeviceNo)
        parcel.writeString(DeviceKey)
        parcel.writeString(DeviceName)
        parcel.writeString(DImage)
        parcel.writeString(LLimit)
        parcel.writeString(CkLimit)
        parcel.writeString(ULimit)
        parcel.writeInt(isOn)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DeviceData> {
        override fun createFromParcel(parcel: Parcel): DeviceData {
            return DeviceData(parcel)
        }

        override fun newArray(size: Int): Array<DeviceData?> {
            return arrayOfNulls(size)
        }
    }


}