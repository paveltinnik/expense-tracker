package com.example.demo.controller

import com.example.demo.controller.dto.transaction.CreateTransactionRequest
import com.example.demo.controller.dto.transaction.TransactionResponse
import com.example.demo.controller.dto.transaction.UpdateTransactionRequest
import com.example.com.example.demo.mapper.toResponse
import com.example.com.example.demo.security.AppUserDetails
import jakarta.validation.Valid
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.util.*

@RestController
@RequestMapping("/api/v1/transactions")
class TransactionController(private val service: TransactionService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @AuthenticationPrincipal user: AppUserDetails,
        @Valid @RequestBody req: CreateTransactionRequest
    ): TransactionResponse = service.create(user.userId, req).toResponse()

    @GetMapping("/{id}")
    fun get(
        @AuthenticationPrincipal user: AppUserDetails,
        @PathVariable id: UUID
    ): TransactionResponse = service.get(user.userId, id).toResponse()

    @GetMapping
    fun list(
        @AuthenticationPrincipal user: AppUserDetails,
        @RequestParam(required = false)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) from: Instant?,
        @RequestParam(required = false)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) to: Instant?
    ): List<TransactionResponse> =
        service.list(user.userId, from, to).map { it.toResponse() }

    @PatchMapping("/{id}")
    fun update(
        @AuthenticationPrincipal user: AppUserDetails,
        @PathVariable id: UUID,
        @Valid @RequestBody req: UpdateTransactionRequest
    ): TransactionResponse = service.update(user.userId, id, req).toResponse()

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(
        @AuthenticationPrincipal user: AppUserDetails,
        @PathVariable id: UUID
    ) = service.delete(user.userId, id)
}