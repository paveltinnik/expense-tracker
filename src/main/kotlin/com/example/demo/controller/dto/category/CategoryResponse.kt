package com.example.demo.controller.dto.category

import com.example.demo.entity.CategoryEntity
import java.util.UUID

data class CategoryResponse(
    val id: UUID,
    val name: String,
    val archived: Boolean
)