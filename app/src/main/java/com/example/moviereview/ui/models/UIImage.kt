package com.example.moviereview.ui.models

import android.os.Parcel
import android.os.Parcelable

class UIImage(val path: String?) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(path)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UIImage> {
        override fun createFromParcel(parcel: Parcel): UIImage {
            return UIImage(parcel)
        }

        override fun newArray(size: Int): Array<UIImage?> {
            return arrayOfNulls(size)
        }
    }
}