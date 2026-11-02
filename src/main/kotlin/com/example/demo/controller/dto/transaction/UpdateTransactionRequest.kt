package com.example.demo.controller.dto.transaction

import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Size
import java.math.BigDecimal
import java.time.Instant

data class UpdateTransactionRequest(
    @field:DecimalMin("0.01") val amount: BigDecimal? = null,
    @field:Size(max = 500) val note: String? = null,
    val occurredAt: Instant? = null
)