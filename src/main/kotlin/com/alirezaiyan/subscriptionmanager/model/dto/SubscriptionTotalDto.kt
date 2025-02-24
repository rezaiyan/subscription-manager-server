package com.alirezaiyan.subscriptionmanager.model.dto

data class SubscriptionTotalDto(
    val totalAmount: Double,
    val monthlyAmount: Double,
    val currency: String = "USD"
)
