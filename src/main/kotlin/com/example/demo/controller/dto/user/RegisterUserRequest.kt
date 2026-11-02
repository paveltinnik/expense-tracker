package com.example.demo.controller.dto.user

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class RegisterUserRequest(
    @field:NotBlank @field:Size(min = 3, max = 100) val login: String,
    @field:NotBlank @field:Size(min = 6, max = 100) val password: String
)