package com.example.demo.category.controller.dto.category

import com.example.demo.category.entity.CategoryType
import java.util.UUID

data class CategoryResponse(
    val id: UUID,
    val name: String,
    val type: CategoryType,
    val isSystem: Boolean,
)