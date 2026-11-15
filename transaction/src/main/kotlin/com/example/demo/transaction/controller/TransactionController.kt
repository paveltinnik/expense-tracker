package com.example.demo.transaction.controller

import com.example.demo.transaction.controller.dto.CreateTransactionRequest
import com.example.demo.transaction.controller.dto.TransactionResponse
import com.example.demo.transaction.controller.dto.UpdateTransactionRequest
import com.example.demo.transaction.mapper.toResponse
import com.example.demo.transaction.service.TransactionService
import com.example.demo.user.security.AppUserDetails
import jakarta.validation.Valid
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.util.*

@RestController
@RequestMapping("/api/v1/transactions")
class TransactionController(private val transactionService: TransactionService) {

    @GetMapping
    fun findAll(
        @AuthenticationPrincipal principal: AppUserDetails,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) from: Instant?,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) to: Instant?,
    ): List<TransactionResponse> {
        val transactions = if (from != null && to != null) {
            transactionService.findAllByPeriod(principal.userId, from, to)
        } else {
            transactionService.findAll(principal.userId)
        }
        return transactions.map { it.toResponse() }
    }

    @GetMapping("/{id}")
    fun findById(
        @PathVariable id: UUID,
        @AuthenticationPrincipal principal: AppUserDetails,
    ): TransactionResponse =
        transactionService.getById(id, principal.userId).toResponse()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @Valid @RequestBody req: CreateTransactionRequest,
        @AuthenticationPrincipal principal: AppUserDetails,
    ): TransactionResponse =
        transactionService.create(req, principal.userId).toResponse()

    @PatchMapping("/{id}")
    fun update(
        @PathVariable id: UUID,
        @Valid @RequestBody req: UpdateTransactionRequest,
        @AuthenticationPrincipal principal: AppUserDetails,
    ): TransactionResponse =
        transactionService.update(id, req, principal.userId).toResponse()

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(
        @PathVariable id: UUID,
        @AuthenticationPrincipal principal: AppUserDetails,
    ) {
        transactionService.delete(id, principal.userId)
    }
}