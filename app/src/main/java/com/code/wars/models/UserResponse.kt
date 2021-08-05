package com.code.wars.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponse (
    val username: String,
    val name: String,
    val honor: Long,
    val clan: String,
    val leaderboardPosition: Long,
    val skills: List<String>,
    val ranks: Ranks,
    val codeChallenges: CodeChallenges
) : Parcelable

@Parcelize
data class CodeChallenges (
    val totalAuthored: Long,
    val totalCompleted: Long
) : Parcelable

@Parcelize
data class Ranks (
    val overall: Overall,
    val languages: Map<String, Overall>
) : Parcelable

@Parcelize
data class Overall (
    val rank: Long,
    val name: String,
    val color: String,
    val score: Long
) : Parcelable
