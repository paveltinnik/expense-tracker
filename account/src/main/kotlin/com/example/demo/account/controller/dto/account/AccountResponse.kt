package com.example.demo.account.controller.dto.account

import java.math.BigDecimal
import java.time.Instant
import java.util.*

data class AccountResponse(
    val id: UUID,
    val name: String,
    val currency: String,
    val balance: BigDecimal,
    val createdAt: Instant,
)