package com.example.demo.category.repository

import com.example.demo.category.entity.CategoryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface CategoryRepository : JpaRepository<CategoryEntity, UUID> {

    @Query("""
    SELECT c FROM CategoryEntity c
    WHERE c.user.id = :userId OR c.user IS NULL
    ORDER BY CASE WHEN c.user IS NULL THEN 1 ELSE 0 END ASC, c.name ASC
""")
    fun findAllAvailable(@Param("userId") userId: UUID): List<CategoryEntity>

    /**
     * По id — только если системная или своя.
     * Для чужой пользовательской вернёт empty (не раскрываем существование).
     */
    @Query("""
        SELECT c FROM CategoryEntity c
        WHERE c.id = :id AND (c.user.id = :userId OR c.user IS NULL)
    """)
    fun findByIdAvailable(
        @Param("id") id: UUID,
        @Param("userId") userId: UUID,
    ): CategoryEntity?

    /** Проверка «такая категория уже доступна пользователю» (системная или своя). */
    @Query("""
        SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END
        FROM CategoryEntity c
        WHERE (c.user.id = :userId OR c.user IS NULL)
          AND LOWER(c.name) = LOWER(:name)
    """)
    fun existsAvailableByName(
        @Param("userId") userId: UUID,
        @Param("name") name: String,
    ): Boolean
}