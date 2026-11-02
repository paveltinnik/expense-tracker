package com.example.demo.mapper

import com.example.demo.controller.dto.category.CategoryResponse
import com.example.demo.controller.dto.category.CreateCategoryRequest
import com.example.demo.entity.CategoryEntity
import java.util.*

fun CategoryEntity.toResponse() = CategoryResponse(
    id = id,
    name = name,
    archived = archived
)

fun CreateCategoryRequest.toEntity(userId: UUID) = CategoryEntity(
    userId = userId,
    name = name.trim()
)