package com.code.wars.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthoredResponse (
    @SerializedName("data")
    val challenges: ArrayList<Challenge> = ArrayList()
) : Parcelable

@Parcelize
data class Challenge (
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val rank: Int = -1,
    val rankName: String = "",
    val tags: List<String> = listOf(),
    val languages: List<String> = listOf()
) : Parcelable
