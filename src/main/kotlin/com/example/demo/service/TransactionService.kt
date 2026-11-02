package com.example.demo.service

import com.example.demo.controller.dto.expense.CreateExpenseRequest
import com.example.demo.controller.dto.expense.UpdateExpenseRequest
import com.example.demo.exception.NotFoundException
import com.example.demo.mapper.toEntity
import com.example.demo.repository.CategoryRepository
import com.example.demo.repository.TransactionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.UUID

@Service
class TransactionService(
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: CategoryRepository
) {
    @Transactional
    fun create(userId: UUID, req: CreateExpenseRequest): ExpenseEntity {
        val category = categoryRepository.findById(req.categoryId)
            .orElseThrow { NotFoundException("Category ${req.categoryId} not found") }
        if (category.userId != userId) throw NotFoundException("Category ${req.categoryId} not found")
        if (category.archived) throw IllegalArgumentException("Category is archived")

        return transactionRepository.save(req.toEntity(userId))
    }

    @Transactional(readOnly = true)
    fun get(userId: UUID, id: UUID): ExpenseEntity {
        val expense = transactionRepository.findById(id)
            .orElseThrow { NotFoundException("Expense $id not found") }
        if (expense.userId != userId) throw NotFoundException("Expense $id not found")
        return expense
    }

    @Transactional(readOnly = true)
    fun list(userId: UUID, from: Instant?, to: Instant?): List<ExpenseEntity> =
        if (from != null && to != null)
            transactionRepository.findAllByUserIdAndSpentAtBetweenOrderBySpentAtDesc(userId, from, to)
        else transactionRepository.findAllByUserIdOrderBySpentAtDesc(userId)

    @Transactional
    fun update(userId: UUID, id: UUID, req: UpdateExpenseRequest): ExpenseEntity {
        val expense = get(userId, id)
        req.amount?.let { expense.amount = it }
        req.currency?.let { expense.currency = it }
        req.description?.let { expense.description = it }
        req.spentAt?.let { expense.spentAt = it }
        return transactionRepository.save(expense)
    }

    @Transactional
    fun delete(userId: UUID, id: UUID) {
        get(userId, id)
        transactionRepository.deleteById(id)
    }
}