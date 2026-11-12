package com.example.demo.mapper

import com.example.demo.controller.dto.account.AccountResponse
import com.example.demo.controller.dto.account.CreateAccountRequest
import com.example.com.example.demo.entity.UserEntity

fun AccountEntity.toResponse() = AccountResponse(
    id = id,
    name = name,
    currency = currency,
    createdAt = createdAt
)

fun CreateAccountRequest.toEntity(owner: UserEntity) = AccountEntity(
    user = owner,
    name = name.trim(),
    currency = currency
)