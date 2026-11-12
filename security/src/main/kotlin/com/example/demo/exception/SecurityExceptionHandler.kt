package com.example.demo.exception

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class SecurityExceptionHandler {

    @ExceptionHandler(BadCredentialsException::class)
    fun handleBadCredentials(ex: BadCredentialsException, request: HttpServletRequest) =
        ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
            ApiError(
                status = 401,
                error = "Unauthorized",
                message = "Invalid login or password",
                path = request.requestURI
            )
        )
}