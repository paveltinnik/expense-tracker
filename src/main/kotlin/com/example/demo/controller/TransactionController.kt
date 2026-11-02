package com.example.demo.controller

import com.example.demo.controller.dto.expense.CreateExpenseRequest
import com.example.demo.controller.dto.expense.ExpenseResponse
import com.example.demo.controller.dto.expense.UpdateExpenseRequest
import com.example.demo.mapper.toResponse
import com.example.demo.service.TransactionService
import jakarta.validation.Valid
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.util.UUID

@RestController
@RequestMapping("/api/v1/expenses")
class TransactionController(private val service: TransactionService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody req: CreateExpenseRequest): ExpenseResponse =
        service.create(currentUserId(), req).toResponse()

    @GetMapping("/{id}")
    fun get(@PathVariable id: UUID): ExpenseResponse =
        service.get(currentUserId(), id).toResponse()

    @GetMapping
    fun list(
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) from: Instant?,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) to: Instant?
    ): List<ExpenseResponse> =
        service.list(currentUserId(), from, to).map { it.toResponse() }

    @PatchMapping("/{id}")
    fun update(@PathVariable id: UUID, @Valid @RequestBody req: UpdateExpenseRequest): ExpenseResponse =
        service.update(currentUserId(), id, req).toResponse()

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: UUID) = service.delete(currentUserId(), id)

    private fun currentUserId(): UUID =
        UUID.fromString("00000000-0000-0000-0000-000000000001")
}