package com.example.demo.mapper

import com.example.demo.controller.dto.category.CategoryResponse
import com.example.demo.controller.dto.category.CreateCategoryRequest
import com.example.demo.entity.CategoryEntity

fun CategoryEntity.toResponse() = CategoryResponse(
    id = id,
    name = name
)

fun CreateCategoryRequest.toEntity() = CategoryEntity(
    name = name.trim()
)