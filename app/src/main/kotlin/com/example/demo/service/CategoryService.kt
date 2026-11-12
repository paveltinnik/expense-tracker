package com.example.demo.service

import com.example.demo.controller.dto.category.CreateCategoryRequest
import com.example.demo.exception.NotFoundException
import com.example.demo.mapper.toEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class CategoryService(private val categoryRepository: CategoryRepository) {

    @Transactional
    fun create(req: CreateCategoryRequest): CategoryEntity {
        if (categoryRepository.existsByNameIgnoreCase(req.name.trim())) {
            throw IllegalArgumentException("Category already exists")
        }
        return categoryRepository.save(req.toEntity())
    }

    @Transactional(readOnly = true)
    fun list(): List<CategoryEntity> = categoryRepository.findAllByOrderByNameAsc()

    @Transactional(readOnly = true)
    fun get(id: UUID): CategoryEntity =
        categoryRepository.findById(id).orElseThrow { NotFoundException("Category $id not found") }
}