package com.code.wars.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CompletedResponse (
    val totalPages: Int = -1,
    val totalItems: Int = -1,
    @SerializedName("data")
    val completed: ArrayList<Completed> = ArrayList()
) : Parcelable

@Parcelize
data class Completed (
    val id: String = "",
    val name: String = "",
    val slug: String = "",
    val completedLanguages: List<String> = listOf(),
    val completedAt: String = ""
) : Parcelable
