package com.example.demo.controller

import com.example.user.controller.dto.user.RegisterUserRequest
import com.example.com.example.demo.controller.dto.UserResponse
import com.example.com.example.demo.mapper.toResponse
import com.example.com.example.demo.security.AppUserDetails
import com.example.com.example.demo.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(private val userService: UserService) {

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun register(@Valid @RequestBody req: RegisterUserRequest): UserResponse =
        userService.register(req).toResponse()

    @GetMapping("/me")
    fun me(@AuthenticationPrincipal principal: AppUserDetails): UserResponse =
        userService.getById(principal.userId).toResponse()
}