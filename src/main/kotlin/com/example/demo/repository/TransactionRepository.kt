package com.example.demo.repository

import com.example.demo.entity.TransactionEntity
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import java.time.Instant
import java.util.*

interface TransactionRepository : JpaRepository<TransactionEntity, UUID> {

    @EntityGraph(attributePaths = ["account", "category"])
    fun findAllByAccountUserIdOrderByOccurredAtDesc(userId: UUID): List<TransactionEntity>

    @EntityGraph(attributePaths = ["account", "category"])
    fun findAllByAccountUserIdAndOccurredAtBetweenOrderByOccurredAtDesc(
        userId: UUID, from: Instant, to: Instant
    ): List<TransactionEntity>

    @EntityGraph(attributePaths = ["account", "category"])
    fun findByIdAndAccountUserId(id: UUID, userId: UUID): TransactionEntity?
}