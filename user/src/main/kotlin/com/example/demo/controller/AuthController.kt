package com.example.demo.controller

import com.example.demo.controller.dto.user.RegisterUserRequest
import com.example.demo.controller.dto.user.UserResponse
import com.example.demo.mapper.toResponse
import com.example.demo.security.AppUserDetails
import com.example.demo.service.UserService
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