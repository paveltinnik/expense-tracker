package com.example.demo.controller.dto.expense

import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Size
import org.jetbrains.annotations.NotNull
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

data class CreateExpenseRequest(
    @field:NotNull val categoryId: UUID,
    @field:NotNull @field:DecimalMin("0.01") val amount: BigDecimal,
    @field:Size(min = 3, max = 3) val currency: String = "RUB",
    @field:Size(max = 500) val description: String? = null,
    @field:NotNull val spentAt: Instant
)