package com.example.demo.controller.dto.expense

import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

data class ExpenseResponse(
    val id: UUID,
    val categoryId: UUID,
    val amount: BigDecimal,
    val currency: String,
    val description: String?,
    val spentAt: Instant
) {
    companion object {
        fun from(expense: ExpenseEntity) = ExpenseResponse(
            expense.id, expense.categoryId, expense.amount, expense.currency, expense.description, expense.spentAt
        )
    }
}