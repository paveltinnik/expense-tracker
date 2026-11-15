package com.example.demo.user.controller

import com.example.demo.user.controller.dto.RegisterUserRequest
import com.example.demo.user.controller.dto.UserResponse
import com.example.demo.user.mapper.toResponse
import com.example.demo.user.security.AppUserDetails
import com.example.demo.user.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

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