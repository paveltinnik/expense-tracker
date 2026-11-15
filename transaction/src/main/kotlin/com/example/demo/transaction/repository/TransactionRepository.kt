package com.example.demo.transaction.repository

import com.example.demo.transaction.entity.TransactionEntity
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.Instant
import java.util.UUID

interface TransactionRepository : JpaRepository<TransactionEntity, UUID> {

    @EntityGraph(attributePaths = ["account", "category"])
    @Query("""
        SELECT t FROM TransactionEntity t
        WHERE t.account.user.id = :userId
        ORDER BY t.occurredAt DESC, t.createdAt DESC
    """)
    fun findAllByUserId(@Param("userId") userId: UUID): List<TransactionEntity>

    @EntityGraph(attributePaths = ["account", "category"])
    @Query("""
        SELECT t FROM TransactionEntity t
        WHERE t.account.user.id = :userId
          AND t.occurredAt BETWEEN :from AND :to
        ORDER BY t.occurredAt DESC, t.createdAt DESC
    """)
    fun findAllByUserIdAndPeriod(
        @Param("userId") userId: UUID,
        @Param("from") from: Instant,
        @Param("to") to: Instant,
    ): List<TransactionEntity>

    @EntityGraph(attributePaths = ["account", "category"])
    @Query("""
        SELECT t FROM TransactionEntity t
        WHERE t.id = :id AND t.account.user.id = :userId
    """)
    fun findByIdAndUserId(
        @Param("id") id: UUID,
        @Param("userId") userId: UUID,
    ): TransactionEntity?

//    @Query("""
//        SELECT COUNT(t) > 0 FROM TransactionEntity t
//        WHERE t.category.id = :categoryId
//    """)
//    fun existsByCategoryId(@Param("categoryId") categoryId: UUID): Boolean
}