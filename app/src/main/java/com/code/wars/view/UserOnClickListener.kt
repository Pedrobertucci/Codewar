package com.code.wars.view

import com.code.wars.models.UserResponse

interface UserOnClickListener {
    fun onClick(userResponse: UserResponse)
}