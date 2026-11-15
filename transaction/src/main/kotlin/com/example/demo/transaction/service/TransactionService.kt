package com.example.demo.transaction.service

import com.example.demo.transaction.controller.dto.CreateTransactionRequest
import com.example.demo.transaction.controller.dto.UpdateTransactionRequest
import com.example.demo.transaction.entity.TransactionEntity
import com.example.demo.common.exception.NotFoundException
import com.example.demo.transaction.mapper.toEntity
import com.example.demo.account.repository.AccountRepository
import com.example.demo.category.repository.CategoryRepository
import com.example.demo.transaction.repository.TransactionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Service
class TransactionService(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository,
    private val categoryRepository: CategoryRepository,
) {

    fun findAll(userId: UUID): List<TransactionEntity> =
        transactionRepository.findAllByUserId(userId)

    fun findAllByPeriod(userId: UUID, from: Instant, to: Instant): List<TransactionEntity> =
        transactionRepository.findAllByUserIdAndPeriod(userId, from, to)

    fun getById(id: UUID, userId: UUID): TransactionEntity =
        transactionRepository.findByIdAndUserId(id, userId) ?: throw (NotFoundException("Transaction not found: $id"))

    @Transactional
    fun create(req: CreateTransactionRequest, userId: UUID): TransactionEntity {
        val account = accountRepository.findByIdAndUserId(req.accountId, userId)
            ?: throw (NotFoundException("Account not found: ${req.accountId}"))

        val category = categoryRepository.findByIdAvailable(req.categoryId, userId)
            ?: throw (NotFoundException("Category not found: ${req.categoryId}"))

        val transaction = transactionRepository.save(req.toEntity(account, category))
        account.balance = account.balance.add(req.amount)
        return transaction
    }

    @Transactional
    fun update(id: UUID, req: UpdateTransactionRequest, userId: UUID): TransactionEntity {
        val transaction = getById(id, userId)

        // Смена счёта — пересчитать балансы старого и нового счёта
        val newAccount = req.accountId?.takeIf { it != transaction.account.id }?.let { newAccountId ->
            val oldAccount = transaction.account
            val targetAccount = accountRepository.findByIdAndUserId(newAccountId, userId)
                ?: throw (NotFoundException("Account not found: $newAccountId"))

            // Откатить влияние на старый счёт
            oldAccount.balance = oldAccount.balance.subtract(transaction.amount)
            // Применить на новый
            targetAccount.balance = targetAccount.balance.add(req.amount ?: transaction.amount)

            targetAccount.also { transaction.account = it }
        }

        // Смена категории
        req.categoryId?.let { newCategoryId ->
            if (newCategoryId != transaction.category.id) {
                val newCategory = categoryRepository.findByIdAvailable(req.categoryId, userId)
                    ?: throw (NotFoundException("Category not found: $newCategoryId"))
                transaction.category = newCategory
            }
        }

        // Смена суммы — скорректировать баланс (если счёт не менялся)
        req.amount?.let { newAmount ->
            if (newAccount == null && newAmount != transaction.amount) {
                val diff = newAmount.subtract(transaction.amount)
                transaction.account.balance = transaction.account.balance.add(diff)
            }
            transaction.amount = newAmount
        }

        req.occurredAt?.let { transaction.occurredAt = it }
        req.description?.let { transaction.description = it }

        return transactionRepository.save(transaction)
    }

    @Transactional
    fun delete(id: UUID, userId: UUID) {
        val transaction = getById(id, userId)

        // Откатить влияние на баланс счёта
        transaction.account.balance = transaction.account.balance.subtract(transaction.amount)

        transactionRepository.delete(transaction)
    }
}