package com.code.wars.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthoredResponse (
    @SerializedName("data")
    val challenges: List<Challenge> = ArrayList()
) : Parcelable

@Parcelize
data class Challenge (
    val id: String?,
    val name: String?,
    val description: String?,
    val rank: Int?,
    val rankName: String?,
    val tags: List<String>?,
    val languages: List<String>?
) : Parcelable
