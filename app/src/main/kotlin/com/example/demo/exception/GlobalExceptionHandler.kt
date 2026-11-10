package com.example.demo.exception

import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(ex: MethodArgumentNotValidException, request: HttpServletRequest): ResponseEntity<ApiError> {
        val message = ex.bindingResult.fieldErrors.joinToString("; ") { "${it.field}: ${it.defaultMessage}" }
        return ResponseEntity.badRequest().body(
            ApiError(
                status = 400,
                error = "Validation Failed",
                message = message,
                path = request.requestURI
            )
        )
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleUnreadable(ex: HttpMessageNotReadableException, request: HttpServletRequest) =
        ResponseEntity.badRequest().body(
            ApiError(
                status = 400,
                error = "Malformed Request",
                message = "Request body is missing or malformed",
                path = request.requestURI
            )
        )

    @ExceptionHandler(HttpMediaTypeNotSupportedException::class)
    fun handleMedia(ex: HttpMediaTypeNotSupportedException, request: HttpServletRequest) =
        ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(
            ApiError(
                status = 415,
                error = "Unsupported Media Type",
                message = "Expected: ${ex.supportedMediaTypes}",
                path = request.requestURI
            )
        )

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDb(ex: DataIntegrityViolationException, request: HttpServletRequest): ResponseEntity<ApiError> {
        log.error("DB constraint violation", ex)
        return ResponseEntity.badRequest().body(
            ApiError(
                status = 400,
                error = "Data Integrity Violation",
                message = "Request violates data constraints",
                path = request.requestURI
            )
        )
    }

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

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFound(ex: NotFoundException, request: HttpServletRequest) =
        ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            ApiError(
                status = 404,
                error = "Not Found",
                message = ex.message ?: "Not found",
                path = request.requestURI
            )
        )

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(ex: IllegalArgumentException, request: HttpServletRequest) =
        ResponseEntity.badRequest().body(
            ApiError(
                status = 400,
                error = "Bad Request",
                message = ex.message ?: "Bad request",
                path = request.requestURI
            )
        )

    @ExceptionHandler(Exception::class)
    fun handleUnexpected(ex: Exception, request: HttpServletRequest): ResponseEntity<ApiError> {
        log.error("Unexpected error at ${request.requestURI}", ex)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            ApiError(
                status = 500,
                error = "Internal Server Error",
                message = "Unexpected error",
                path = request.requestURI
            )
        )
    }
}