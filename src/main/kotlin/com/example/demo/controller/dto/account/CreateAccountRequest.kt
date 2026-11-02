package com.example.demo.controller.dto.account

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateAccountRequest(
    @field:NotBlank @field:Size(max = 100) val name: String,
    @field:Size(min = 3, max = 3) val currency: String = "RUB"
)