package com.example.demo.mapper

import com.example.demo.controller.dto.transaction.CreateTransactionRequest
import com.example.demo.controller.dto.transaction.TransactionResponse

fun TransactionEntity.toResponse() = TransactionResponse(
    id = id,
    accountId = account.id,
    accountName = account.name,
    categoryId = category?.id,
    categoryName = category?.name,
    amount = amount,
    currency = currency,
    type = type,
    note = note,
    occurredAt = occurredAt
)

fun CreateTransactionRequest.toEntity(
    account: AccountEntity,
    category: CategoryEntity?
) = TransactionEntity(
    account = account,
    category = category,
    amount = amount,
    currency = currency,
    type = type,
    note = note,
    occurredAt = occurredAt
)