package com.example.demo.repository

import com.example.demo.entity.AccountEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface AccountRepository : JpaRepository<AccountEntity, UUID> {

    fun findAllByUserIdOrderByNameAsc(userId: UUID): List<AccountEntity>
    fun findByIdAndUserId(id: UUID, userId: UUID): AccountEntity?
}