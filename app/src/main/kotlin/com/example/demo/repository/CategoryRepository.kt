package com.example.demo.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface CategoryRepository : JpaRepository<CategoryEntity, UUID> {

    @Query("""
        SELECT c FROM CategoryEntity c
        ORDER BY c.name ASC
    """)
    fun findAllByOrderByNameAsc(): List<CategoryEntity>

    @Query("""
        SELECT (COUNT(c) > 0) FROM CategoryEntity c
        WHERE UPPER(c.name) = UPPER(:name)
    """)
    fun existsByNameIgnoreCase(name: String): Boolean
}