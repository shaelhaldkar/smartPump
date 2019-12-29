package com.iot.smartpump.model

import android.os.Parcel
import android.os.Parcelable

class DeviceData() :Parcelable{
    var ID=""
    var DeviceNo=""
    var DeviceKey=""
    var DeviceName=""
    var DImage=""
    var LLimit=0
    var CkLimit=0
    var ULimit=0
    var state=0

    constructor(parcel: Parcel) : this() {
        ID = parcel.readString().toString()
        DeviceNo = parcel.readString().toString()
        DeviceKey = parcel.readString().toString()
        DeviceName = parcel.readString().toString()
        DImage = parcel.readString().toString()
        LLimit = parcel.readInt()
        CkLimit = parcel.readInt()
        ULimit = parcel.readInt()
        state = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(ID)
        parcel.writeString(DeviceNo)
        parcel.writeString(DeviceKey)
        parcel.writeString(DeviceName)
        parcel.writeString(DImage)
        parcel.writeInt(LLimit)
        parcel.writeInt(CkLimit)
        parcel.writeInt(ULimit)
        parcel.writeInt(state)
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