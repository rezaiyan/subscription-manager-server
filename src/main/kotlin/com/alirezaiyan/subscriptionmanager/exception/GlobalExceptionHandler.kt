package com.alirezaiyan.subscriptionmanager.exception

import com.alirezaiyan.subscriptionmanager.model.dto.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFoundException(ex: NoSuchElementException): ResponseEntity<ErrorResponse<Unit>> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(
                ErrorResponse(
                    message = ex.message ?: "Resource not found",
                    errorCode = "NOT_FOUND"
                )
            )
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequestException(ex: IllegalArgumentException): ResponseEntity<ErrorResponse<Unit>> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                ErrorResponse(
                    message = ex.message ?: "Invalid request data",
                    errorCode = "BAD_REQUEST"
                )
            )
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<ErrorResponse<Unit>> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ErrorResponse(
                    message = "An unexpected error occurred",
                    errorCode = "INTERNAL_ERROR"
                )
            )
    }
}
