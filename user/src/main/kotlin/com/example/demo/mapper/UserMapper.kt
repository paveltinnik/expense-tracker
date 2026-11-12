package com.example.demo.mapper

import com.example.demo.controller.dto.user.UserResponse
import com.example.demo.entity.UserEntity

fun UserEntity.toResponse() = UserResponse(
    id = id,
    login = login,
    createdAt = createdAt
)