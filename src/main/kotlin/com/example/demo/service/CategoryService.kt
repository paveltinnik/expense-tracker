package com.example.demo.service

import com.example.demo.controller.dto.category.CreateCategoryRequest
import com.example.demo.entity.CategoryEntity
import com.example.demo.exception.NotFoundException
import com.example.demo.repository.CategoryRepository
import com.example.demo.repository.TransactionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository,
    private val transactionRepository: TransactionRepository
) {

    @Transactional
    fun create(userId: UUID, req: CreateCategoryRequest): CategoryEntity {
        val entity = CategoryEntity(userId = userId, name = req.name.trim())
        return categoryRepository.save(entity)
    }

    @Transactional(readOnly = true)
    fun list(userId: UUID): List<CategoryEntity> =
        categoryRepository.findAllByUserIdOrderByNameAsc(userId)

    @Transactional(readOnly = true)
    fun get(userId: UUID, id: UUID): CategoryEntity {
        val category = categoryRepository.findById(id)
            .orElseThrow { NotFoundException("Category $id not found") }
        if (category.userId != userId) throw NotFoundException("Category $id not found")
        return category
    }

    @Transactional
    fun archive(userId: UUID, id: UUID): CategoryEntity {
        val category = get(userId, id)
        category.archived = true
        return categoryRepository.save(category)
    }

    @Transactional
    fun delete(userId: UUID, id: UUID) {
        get(userId, id)
        if (transactionRepository.existsByCategoryId(id)) {
            throw IllegalArgumentException("Cannot delete category with expenses")
        }
        categoryRepository.deleteById(id)
    }
}