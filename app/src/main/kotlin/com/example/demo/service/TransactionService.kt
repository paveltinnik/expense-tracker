package com.example.demo.service

import com.example.demo.controller.dto.transaction.CreateTransactionRequest
import com.example.demo.controller.dto.transaction.UpdateTransactionRequest
import com.example.demo.exception.NotFoundException
import com.example.demo.mapper.toEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.UUID

@Service
class TransactionService(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository,
    private val categoryRepository: CategoryRepository
) {

    @Transactional
    fun create(userId: UUID, req: CreateTransactionRequest): TransactionEntity {
        val account = accountRepository.findByIdAndUserId(req.accountId, userId)
            ?: throw NotFoundException("Account ${req.accountId} not found")

        val category = req.categoryId?.let {
            categoryRepository.findById(it)
                .orElseThrow { NotFoundException("Category $it not found") }
        }

        return transactionRepository.save(req.toEntity(account, category))
    }

    @Transactional(readOnly = true)
    fun get(userId: UUID, id: UUID): TransactionEntity =
        transactionRepository.findByIdAndAccountUserId(id, userId)
            ?: throw NotFoundException("Transaction $id not found")

    @Transactional(readOnly = true)
    fun list(userId: UUID, from: Instant?, to: Instant?): List<TransactionEntity> =
        if (from != null && to != null)
            transactionRepository.findAllByAccountUserIdAndOccurredAtBetweenOrderByOccurredAtDesc(userId, from, to)
        else
            transactionRepository.findAllByAccountUserIdOrderByOccurredAtDesc(userId)

    @Transactional
    fun update(userId: UUID, id: UUID, req: UpdateTransactionRequest): TransactionEntity {
        val tx = get(userId, id)
        req.amount?.let { tx.amount = it }
        req.note?.let { tx.note = it }
        req.occurredAt?.let { tx.occurredAt = it }
        return transactionRepository.save(tx)
    }

    @Transactional
    fun delete(userId: UUID, id: UUID) {
        val tx = get(userId, id)
        transactionRepository.delete(tx)
    }
}