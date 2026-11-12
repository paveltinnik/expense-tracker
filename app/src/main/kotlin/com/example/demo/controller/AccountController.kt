package com.example.demo.controller

import com.example.demo.controller.dto.account.AccountResponse
import com.example.demo.controller.dto.account.CreateAccountRequest
import com.example.com.example.demo.mapper.toResponse
import com.example.com.example.demo.security.AppUserDetails
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/accounts")
class AccountController(private val service: AccountService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @AuthenticationPrincipal user: AppUserDetails,
        @Valid @RequestBody req: CreateAccountRequest
    ): AccountResponse = service.create(user.userId, req).toResponse()

    @GetMapping
    fun list(@AuthenticationPrincipal user: AppUserDetails): List<AccountResponse> =
        service.list(user.userId).map { it.toResponse() }

    @GetMapping("/{id}")
    fun get(
        @AuthenticationPrincipal user: AppUserDetails,
        @PathVariable id: UUID
    ): AccountResponse = service.get(user.userId, id).toResponse()
}