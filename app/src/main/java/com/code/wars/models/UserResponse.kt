package com.code.wars.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponse (
    val username: String = "",
    val name: String = "",
    val honor: Int = -1,
    val clan: String = "",
    val leaderboardPosition: Int = -1,
    val skills: List<String> = listOf(),
    val ranks: Ranks = Ranks(),
    val codeChallenges: CodeChallenges = CodeChallenges()
) : Parcelable

@Parcelize
data class CodeChallenges (
    val totalAuthored: Int = -1,
    val totalCompleted: Int = -1
) : Parcelable

@Parcelize
data class Ranks (
    val overall: Overall = Overall(),
    val languages: Map<String, Overall> = HashMap()
) : Parcelable

@Parcelize
data class Overall (
    val rank: Int = -1,
    val name: String = "",
    val color: String = "",
    val score: Int = -1
) : Parcelable
