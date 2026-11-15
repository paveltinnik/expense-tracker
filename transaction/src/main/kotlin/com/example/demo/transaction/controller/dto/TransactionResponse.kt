package com.example.demo.transaction.controller.dto

import com.example.demo.account.controller.dto.account.AccountResponse
import com.example.demo.category.controller.dto.category.CategoryResponse
import java.math.BigDecimal
import java.time.Instant
import java.util.*

data class TransactionResponse(
    val id: UUID,
    val account: AccountResponse,
    val category: CategoryResponse,
    val amount: BigDecimal,
    val occurredAt: Instant,
    val description: String?,
    val createdAt: Instant,
)