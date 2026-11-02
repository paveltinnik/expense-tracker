package com.example.demo.controller

import com.example.demo.controller.dto.category.CategoryResponse
import com.example.demo.controller.dto.category.CreateCategoryRequest
import com.example.demo.mapper.toResponse
import com.example.demo.service.CategoryService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/categories")
class CategoryController(
    private val service: CategoryService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody req: CreateCategoryRequest): CategoryResponse =
        service.create(currentUserId(), req).toResponse()

    @GetMapping
    fun list(): List<CategoryResponse> =
        service.list(currentUserId()).map { it.toResponse() }

    @PostMapping("/{id}/archive")
    fun archive(@PathVariable id: UUID): CategoryResponse =
        service.archive(currentUserId(), id).toResponse()

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: UUID) = service.delete(currentUserId(), id)

    private fun currentUserId(): UUID =
        UUID.fromString("00000000-0000-0000-0000-000000000001")
}