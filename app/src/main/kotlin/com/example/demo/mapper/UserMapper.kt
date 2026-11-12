package com.example.demo.mapper

import com.example.com.example.demo.controller.dto.UserResponse
import com.example.com.example.demo.entity.UserEntity

fun UserEntity.toResponse() = UserResponse(
    id = id,
    login = login,
    createdAt = createdAt
)