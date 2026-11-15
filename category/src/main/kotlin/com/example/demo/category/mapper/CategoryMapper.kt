package com.example.demo.category.mapper

import com.example.demo.category.controller.dto.category.CategoryResponse
import com.example.demo.category.entity.CategoryEntity

fun CategoryEntity.toResponse() = CategoryResponse(
    id = id,
    name = name,
    type = type,
    isSystem = isSystem,        // ← клиент видит, какие нельзя удалять
)