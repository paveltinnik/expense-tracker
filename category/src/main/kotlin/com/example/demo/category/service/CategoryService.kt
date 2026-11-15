package com.example.demo.category.service

import com.example.demo.category.controller.dto.category.CreateCategoryRequest
import com.example.demo.category.entity.CategoryEntity
import com.example.demo.category.repository.CategoryRepository
import com.example.demo.common.exception.ConflictException
import com.example.demo.common.exception.ForbiddenException
import com.example.demo.common.exception.NotFoundException
import com.example.demo.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository,
    private val userRepository: UserRepository,
) {

    fun findAll(userId: UUID): List<CategoryEntity> =
        categoryRepository.findAllAvailable(userId)

    fun getById(id: UUID, userId: UUID): CategoryEntity =
        categoryRepository.findByIdAvailable(id, userId)
            ?: throw (NotFoundException("Category not found: $id"))

    @Transactional
    fun create(req: CreateCategoryRequest, userId: UUID): CategoryEntity {
        if (categoryRepository.existsAvailableByName(userId, req.name)) {
            throw ConflictException("Category '${req.name}' already exists")
        }

        val user = userRepository.findById(userId)
            .orElseThrow { NotFoundException("User not found: $userId") }

        return categoryRepository.save(
            CategoryEntity(user = user, name = req.name.trim(), type = req.type)
        )
    }

    @Transactional
    fun delete(id: UUID, userId: UUID) {
        val category = categoryRepository.findByIdAvailable(id, userId)
            ?: throw (NotFoundException("Category not found: $id"))

        if (category.isSystem) {
            throw ForbiddenException("System categories cannot be deleted")
        }

        categoryRepository.delete(category)
    }
}