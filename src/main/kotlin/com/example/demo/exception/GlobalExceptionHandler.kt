package com.example.demo.exception

import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(
        ex: MethodArgumentNotValidException,
        request: HttpServletRequest,
    ): ResponseEntity<ApiError> {
        val message = ex.bindingResult.fieldErrors.joinToString("; ") { "${it.field}: ${it.defaultMessage}" }
        log.warn("Validation failed at ${request.requestURI}: $message")
        return ResponseEntity.badRequest().body(
            ApiError(
                status = 400,
                error = "Validation failed",
                message = message,
                path = request.requestURI
            )
        )
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleUnreadable(ex: HttpMessageNotReadableException, request: HttpServletRequest): ResponseEntity<ApiError> {
        log.warn("Malformed request at ${request.requestURI}: ${ex.message}")
        return ResponseEntity.badRequest().body(
            ApiError(400, "Malformed Request", "Request body is missing or malformed", request.requestURI)
        )
    }

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

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDb(ex: DataIntegrityViolationException, request: HttpServletRequest): ResponseEntity<ApiError> {
        log.error("DB constraint violation at ${request.requestURI}", ex)
        return ResponseEntity.badRequest().body(
            ApiError(400, "Data Integrity Violation", "Request violates data constraints", request.requestURI)
        )
    }

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

    @ExceptionHandler(HttpMediaTypeNotSupportedException::class)
    fun handleUnsupportedMedia(
        ex: HttpMediaTypeNotSupportedException,
        request: HttpServletRequest
    ): ResponseEntity<ApiError> {
        val supported = ex.supportedMediaTypes.joinToString(", ") { it.toString() }
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(
            ApiError(
                status = 415,
                error = "Unsupported Media Type",
                message = "Expected Content-Type: $supported. Received: ${ex.contentType}",
                path = request.requestURI
            )
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleUnexpected(ex: Exception, request: HttpServletRequest): ResponseEntity<ApiError> {
        // ← ГЛАВНОЕ: логируем полное исключение, а клиенту отдаём общее сообщение
        log.error("Unexpected error at ${request.requestURI}", ex)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiError(500, "Internal Server Error", "Unexpected error", request.requestURI))
    }
}