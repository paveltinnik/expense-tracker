package com.example.demo.controller.dto.expense

import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Size
import java.math.BigDecimal
import java.time.Instant

data class UpdateExpenseRequest(
    @field:DecimalMin("0.01") val amount: BigDecimal? = null,
    @field:Size(min = 3, max = 3) val currency: String? = null,
    @field:Size(max = 500) val description: String? = null,
    val spentAt: Instant? = null
)