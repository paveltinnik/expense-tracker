package com.example.demo.user.mapper

import com.example.demo.user.controller.dto.UserResponse
import com.example.demo.user.entity.UserEntity

fun UserEntity.toResponse() = UserResponse(
    id = id,
    login = login,
    createdAt = createdAt
)