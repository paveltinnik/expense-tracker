package com.example.demo.transaction.controller.dto

import jakarta.validation.constraints.NotNull
import java.math.BigDecimal
import java.time.Instant
import java.util.*

data class CreateTransactionRequest(
    @field:NotNull
    val accountId: UUID,

    @field:NotNull
    val categoryId: UUID,

    @field:NotNull
    val amount: BigDecimal,

    @field:NotNull
    val occurredAt: Instant,

    val description: String? = null,
)