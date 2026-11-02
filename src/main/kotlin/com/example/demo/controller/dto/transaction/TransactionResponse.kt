package com.example.demo.controller.dto.transaction

import com.example.demo.entity.TransactionType
import java.math.BigDecimal
import java.time.Instant
import java.util.*

data class TransactionResponse(
    val id: UUID,
    val accountId: UUID,
    val accountName: String,
    val categoryId: UUID?,
    val categoryName: String?,
    val amount: BigDecimal,
    val currency: String,
    val type: TransactionType,
    val note: String?,
    val occurredAt: Instant
)