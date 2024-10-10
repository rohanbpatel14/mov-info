package com.example.moviereview.ui.models

import android.os.Parcel
import android.os.Parcelable

class UIPosterMedia(
    val id: Int,
    val voteAverage: Double,
    val title: String,
    val backdropPath: String?,
    val posterPath: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readString() ?: "",
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeDouble(voteAverage)
        parcel.writeString(title)
        parcel.writeString(backdropPath)
        parcel.writeString(posterPath)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UIPosterMedia> {
        override fun createFromParcel(parcel: Parcel): UIPosterMedia {
            return UIPosterMedia(parcel)
        }

        override fun newArray(size: Int): Array<UIPosterMedia?> {
            return arrayOfNulls(size)
        }
    }
}