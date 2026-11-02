package com.example.demo.repository

import com.example.demo.entity.CategoryEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface CategoryRepository : JpaRepository<CategoryEntity, UUID> {

    fun findAllByOrderByNameAsc(): List<CategoryEntity>
    fun existsByNameIgnoreCase(name: String): Boolean
}