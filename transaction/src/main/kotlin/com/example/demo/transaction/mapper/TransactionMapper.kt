package com.example.demo.transaction.mapper

import com.example.demo.account.entity.AccountEntity
import com.example.demo.account.mapper.toResponse
import com.example.demo.category.entity.CategoryEntity
import com.example.demo.category.mapper.toResponse
import com.example.demo.transaction.controller.dto.CreateTransactionRequest
import com.example.demo.transaction.controller.dto.TransactionResponse
import com.example.demo.transaction.entity.TransactionEntity

fun TransactionEntity.toResponse() = TransactionResponse(
    id = id,
    account = account.toResponse(),
    category = category.toResponse(),
    amount = amount,
    occurredAt = occurredAt,
    description = description,
    createdAt = createdAt,
)

fun CreateTransactionRequest.toEntity(
    account: AccountEntity,
    category: CategoryEntity
) = TransactionEntity(
    account = account,
    category = category,
    amount = amount,
    description = description,
    occurredAt = occurredAt
)