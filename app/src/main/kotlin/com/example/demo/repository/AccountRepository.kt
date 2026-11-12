package com.example.demo.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID

interface AccountRepository : JpaRepository<AccountEntity, UUID> {

    @Query("""
        SELECT a FROM AccountEntity a
        WHERE a.user.id = :userId
        ORDER BY a.name ASC
    """)
    fun findAllByUserIdOrderByNameAsc(@Param("userId") userId: UUID): List<AccountEntity>

    @Query("""
        SELECT a FROM AccountEntity a
        WHERE a.id = :id AND a.user.id = :userId
    """)
    fun findByIdAndUserId(
        @Param("id") id: UUID,
        @Param("userId") userId: UUID
    ): AccountEntity?
}