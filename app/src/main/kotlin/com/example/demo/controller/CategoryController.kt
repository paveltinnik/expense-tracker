package com.example.demo.controller

import com.example.demo.controller.dto.category.CategoryResponse
import com.example.demo.controller.dto.category.CreateCategoryRequest
import com.example.com.example.demo.mapper.toResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/categories")
class CategoryController(private val service: CategoryService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody req: CreateCategoryRequest): CategoryResponse =
        service.create(req).toResponse()

    @GetMapping
    fun list(): List<CategoryResponse> = service.list().map { it.toResponse() }
}