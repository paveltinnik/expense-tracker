package com.example.demo.account.mapper

import com.example.demo.account.controller.dto.account.AccountResponse
import com.example.demo.account.controller.dto.account.CreateAccountRequest
import com.example.demo.account.entity.AccountEntity
import com.example.demo.user.entity.UserEntity

fun AccountEntity.toResponse() = AccountResponse(
    id = id,
    name = name,
    currency = currency,
    balance = balance,
    createdAt = createdAt,
)

fun CreateAccountRequest.toEntity(owner: UserEntity) = AccountEntity(
    user = owner,
    name = name.trim(),
    currency = currency,
)