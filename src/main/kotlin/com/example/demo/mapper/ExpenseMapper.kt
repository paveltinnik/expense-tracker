package com.example.demo.mapper

import com.example.demo.controller.dto.expense.CreateExpenseRequest
import com.example.demo.controller.dto.expense.ExpenseResponse
import java.util.UUID

fun ExpenseEntity.toResponse() = ExpenseResponse(
    id = id,
    categoryId = categoryId,
    amount = amount,
    currency = currency,
    description = description,
    spentAt = spentAt
)

fun CreateExpenseRequest.toEntity(userId: UUID) = ExpenseEntity(
    userId = userId,
    categoryId = categoryId,
    amount = amount,
    currency = currency,
    description = description,
    spentAt = spentAt
)