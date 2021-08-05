package com.code.wars.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CompletedResponse (
    val totalPages: Long,
    val totalItems: Long,
    @SerializedName("data")
    val completed: ArrayList<Completed> = ArrayList()
) : Parcelable

@Parcelize
data class Completed (
    val id: String,
    val name: String,
    val slug: String,
    val completedLanguages: List<String>,
    val completedAt: String
) : Parcelable
