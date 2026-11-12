package com.example.demo.controller.dto.transaction

import com.example.demo.entity.TransactionType
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.math.BigDecimal
import java.time.Instant
import java.util.*

data class CreateTransactionRequest(
    @field:NotNull val accountId: UUID,
    val categoryId: UUID? = null,
    @field:NotNull @field:DecimalMin("0.01") val amount: BigDecimal,
    @field:Size(min = 3, max = 3) val currency: String = "RUB",
    @field:NotNull val type: TransactionType,
    @field:Size(max = 500) val note: String? = null,
    @field:NotNull val occurredAt: Instant
)