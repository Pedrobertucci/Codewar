package com.code.wars.view.search

import com.code.wars.models.UserResponse

interface UserOnClickListener {
    fun onClick(userResponse: UserResponse)
}