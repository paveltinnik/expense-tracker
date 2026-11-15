package com.example.demo.account.service

import com.example.demo.account.controller.dto.account.CreateAccountRequest
import com.example.demo.account.entity.AccountEntity
import com.example.demo.common.exception.NotFoundException
import com.example.demo.account.mapper.toEntity
import com.example.demo.account.repository.AccountRepository
import com.example.demo.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val userRepository: UserRepository
) {
    @Transactional
    fun create(userId: UUID, req: CreateAccountRequest): AccountEntity {
        val user = userRepository.findById(userId)
            .orElseThrow { NotFoundException("User $userId not found") }
        return accountRepository.save(req.toEntity(user))
    }

    @Transactional(readOnly = true)
    fun list(userId: UUID): List<AccountEntity> =
        accountRepository.findAllByUserIdOrderByNameAsc(userId)

    @Transactional(readOnly = true)
    fun get(userId: UUID, id: UUID): AccountEntity =
        accountRepository.findByIdAndUserId(id, userId)
            ?: throw NotFoundException("Account $id not found")
}