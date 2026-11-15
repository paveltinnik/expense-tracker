package com.example.demo.category.controller.dto.category

import com.example.demo.category.entity.CategoryType
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class CreateCategoryRequest(
    @field:NotBlank
    @field:Size(max = 100)
    val name: String,

    @field:NotNull
    val type: CategoryType,
)