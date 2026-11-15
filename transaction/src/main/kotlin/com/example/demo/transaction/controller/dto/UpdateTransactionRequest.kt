package com.example.demo.transaction.controller.dto

import jakarta.validation.constraints.Size
import java.math.BigDecimal
import java.time.Instant
import java.util.*

data class UpdateTransactionRequest(
    val accountId: UUID? = null,
    val categoryId: UUID? = null,
    val amount: BigDecimal? = null,
    val occurredAt: Instant? = null,
    @field:Size(max = 500)
    val description: String? = null,
)