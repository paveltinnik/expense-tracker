package com.example.demo.category.controller

import com.example.demo.category.controller.dto.category.CategoryResponse
import com.example.demo.category.controller.dto.category.CreateCategoryRequest
import com.example.demo.category.mapper.toResponse
import com.example.demo.category.service.CategoryService
import com.example.demo.user.security.AppUserDetails
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/categories")
class CategoryController(private val categoryService: CategoryService) {

    @GetMapping
    fun findAll(@AuthenticationPrincipal principal: AppUserDetails): List<CategoryResponse> =
        categoryService.findAll(principal.userId).map { it.toResponse() }

    @GetMapping("/{id}")
    fun findById(
        @PathVariable id: UUID,
        @AuthenticationPrincipal principal: AppUserDetails,
    ): CategoryResponse =
        categoryService.getById(id, principal.userId).toResponse()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @Valid @RequestBody req: CreateCategoryRequest,
        @AuthenticationPrincipal principal: AppUserDetails,
    ): CategoryResponse =
        categoryService.create(req, principal.userId).toResponse()

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(
        @PathVariable id: UUID,
        @AuthenticationPrincipal principal: AppUserDetails,
    ) {
        categoryService.delete(id, principal.userId)
    }
}