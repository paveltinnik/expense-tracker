package com.example.demo.user.controller.dto

import java.time.Instant
import java.util.*

data class UserResponse(
    val id: UUID,
    val login: String,
    val createdAt: Instant
)