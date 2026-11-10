package com.example.demo.controller.dto.category

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateCategoryRequest(
    @field:NotBlank @field:Size(max = 100) val name: String
)