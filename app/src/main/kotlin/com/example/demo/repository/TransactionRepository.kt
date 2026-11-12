package com.example.demo.repository

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.Instant
import java.util.*

interface TransactionRepository : JpaRepository<TransactionEntity, UUID> {

    @EntityGraph(attributePaths = ["account", "category"])
    @Query("""
        SELECT t FROM TransactionEntity t 
        WHERE t.account.user.id = :userId 
        ORDER BY t.occurredAt DESC
    """)
    fun findAllByAccountUserIdOrderByOccurredAtDesc(userId: UUID): List<TransactionEntity>

    @EntityGraph(attributePaths = ["account", "category"])
    @Query("""
        SELECT t FROM TransactionEntity t
        WHERE t.account.user.id = :userId AND t.occurredAt BETWEEN :from AND :to
        ORDER BY t.occurredAt DESC
    """)
    fun findAllByAccountUserIdAndOccurredAtBetweenOrderByOccurredAtDesc(
        userId: UUID, from: Instant, to: Instant
    ): List<TransactionEntity>

    @EntityGraph(attributePaths = ["account", "category"])
    @Query("""
        SELECT t FROM TransactionEntity t
        WHERE t.id = :id AND t.account.user.id = :userId
    """)
    fun findByIdAndAccountUserId(id: UUID, userId: UUID): TransactionEntity?
}