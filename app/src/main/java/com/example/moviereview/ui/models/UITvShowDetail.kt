package com.example.moviereview.ui.models

import android.os.Parcel
import android.os.Parcelable

data class UITvShowDetail(
    val id: Int,
    val name: String,
    val firstAirDate: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val backdropPath: String?,
    val voteAverage: Double,
    val voteCount: Int,
    val genres: String,
    val similar: List<UIPosterMedia>,
    val cast: List<UICast>,
    val images: List<UIImage>,
    val seasons: List<UISeason>,
    var saved: Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.createTypedArrayList(UIPosterMedia.CREATOR) as List<UIPosterMedia>,
        parcel.createTypedArrayList(UICast.CREATOR) as List<UICast>,
        parcel.createTypedArrayList(UIImage.CREATOR) as List<UIImage>,
        parcel.createTypedArrayList(UISeason.CREATOR) as List<UISeason>,
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(firstAirDate)
        parcel.writeString(overview)
        parcel.writeDouble(popularity)
        parcel.writeString(posterPath)
        parcel.writeString(backdropPath)
        parcel.writeDouble(voteAverage)
        parcel.writeInt(voteCount)
        parcel.writeString(genres)
        parcel.writeList(similar)
        parcel.writeList(cast)
        parcel.writeList(images)
        parcel.writeList(seasons)
        parcel.writeByte(if (saved) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UITvShowDetail> {
        override fun createFromParcel(parcel: Parcel): UITvShowDetail {
            return UITvShowDetail(parcel)
        }

        override fun newArray(size: Int): Array<UITvShowDetail?> {
            return arrayOfNulls(size)
        }
    }
}