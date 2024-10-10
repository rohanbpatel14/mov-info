package com.example.moviereview.ui.models

import android.os.Parcel
import android.os.Parcelable

class UICast(
    val id: Int,
    val name: String,
    val profilePath: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(profilePath)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UICast> {
        override fun createFromParcel(parcel: Parcel): UICast {
            return UICast(parcel)
        }

        override fun newArray(size: Int): Array<UICast?> {
            return arrayOfNulls(size)
        }
    }
}