package com.alirezaiyan.subscriptionmanager.model.dto

import java.time.LocalDateTime

sealed interface ApiResponse<T> {
    val status: String
    val message: String?
}

data class SuccessResponse<T>(
    override val status: String = "success",
    override val message: String? = null,
    val data: T
) : ApiResponse<T>

data class ErrorResponse<T>(
    override val status: String = "error",
    override val message: String,
    val errorCode: String? = null,
    val timestamp: LocalDateTime = LocalDateTime.now()
) : ApiResponse<T>


