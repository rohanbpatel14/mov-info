package com.example.moviereview.ui.models

import android.os.Parcel
import android.os.Parcelable

class UISeason(
    val id: Int,
    val episodeCount: Int,
    val name: String,
    val overview: String,
    val posterPath: String?,
    val seasonNumber: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: " ",
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(episodeCount)
        parcel.writeString(name)
        parcel.writeString(overview)
        parcel.writeString(posterPath)
        parcel.writeInt(seasonNumber)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UISeason> {
        override fun createFromParcel(parcel: Parcel): UISeason {
            return UISeason(parcel)
        }

        override fun newArray(size: Int): Array<UISeason?> {
            return arrayOfNulls(size)
        }
    }
}