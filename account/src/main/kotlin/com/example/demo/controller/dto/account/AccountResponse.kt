package com.example.demo.controller.dto.account

import java.time.Instant
import java.util.UUID

data class AccountResponse(
    val id: UUID,
    val name: String,
    val currency: String,
    val createdAt: Instant
)