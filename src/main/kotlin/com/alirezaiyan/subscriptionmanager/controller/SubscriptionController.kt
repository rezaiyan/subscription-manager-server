package com.alirezaiyan.subscriptionmanager.controller

import com.alirezaiyan.subscriptionmanager.security.AuthenticatedUser
import com.alirezaiyan.subscriptionmanager.model.dto.ApiResponse
import com.alirezaiyan.subscriptionmanager.model.dto.ErrorResponse
import com.alirezaiyan.subscriptionmanager.model.dto.SuccessResponse
import com.alirezaiyan.subscriptionmanager.model.dto.SubscriptionTotalDto
import com.alirezaiyan.subscriptionmanager.model.entity.Subscription
import com.alirezaiyan.subscriptionmanager.service.SubscriptionManagementService
import com.alirezaiyan.subscriptionmanager.service.UserNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/subscriptions")
class SubscriptionController(
    private val subscriptionManagementService: SubscriptionManagementService
) {

    @GetMapping
    fun getSubscriptionsByUser(@AuthenticationPrincipal user: AuthenticatedUser): ResponseEntity<ApiResponse<List<Subscription>>> {
        return try {
            val subscriptions = subscriptionManagementService.getAllSubscriptions(user.username)
            ResponseEntity.ok(SuccessResponse(data = subscriptions))
        } catch (e: UserNotFoundException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse(message = e.message, errorCode = "USER_NOT_FOUND"))
        }
    }

    @GetMapping("/total")
    fun getTotalSubscriptionsAmount(@AuthenticationPrincipal user: AuthenticatedUser): ResponseEntity<ApiResponse<SubscriptionTotalDto>> {
        return try {
            val totalMonthlyAmount = subscriptionManagementService.getTotalAmountByUserEmail(user.username)
            ResponseEntity.ok(SuccessResponse(data = totalMonthlyAmount))
        } catch (e: UserNotFoundException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse(message = e.message, errorCode = "USER_NOT_FOUND"))
        }
    }

    @GetMapping("/{id}")
    fun getSubscriptionById(
        @AuthenticationPrincipal user: AuthenticatedUser,
        @PathVariable id: Long
    ): ResponseEntity<ApiResponse<Subscription>> {
        return try {
            val subscription = subscriptionManagementService.getSubscriptionByIdAndUserEmail(id, user.username)
                ?: return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(
                        ErrorResponse(
                            message = "Subscription not found or does not belong to the user",
                            errorCode = "NOT_FOUND"
                        )
                    )
            ResponseEntity.ok(SuccessResponse(data = subscription))
        } catch (e: UserNotFoundException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse(message = e.message, errorCode = "USER_NOT_FOUND"))
        }
    }

    @PostMapping
    fun createSubscription(
        @AuthenticationPrincipal user: AuthenticatedUser,
        @RequestBody subscription: Subscription
    ): ResponseEntity<ApiResponse<Subscription>> {
        return try {
            val createdSubscription = subscriptionManagementService.createSubscription(subscription, user.username)
            ResponseEntity.status(HttpStatus.CREATED)
                .body(SuccessResponse(data = createdSubscription, message = "Subscription created successfully"))
        } catch (e: UserNotFoundException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse(message = e.message, errorCode = "USER_NOT_FOUND"))
        }
    }

    @DeleteMapping("/{id}")
    fun deleteSubscription(
        @AuthenticationPrincipal user: AuthenticatedUser,
        @PathVariable id: Long
    ): ResponseEntity<ApiResponse<Unit>> {
        return try {
            val deleted = subscriptionManagementService.deleteSubscriptionByUserEmail(id, user.username)
            if (deleted) {
                ResponseEntity.ok(SuccessResponse(message = "Subscription deleted successfully", data = Unit))
            } else {
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(
                        ErrorResponse(
                            message = "Subscription not found or does not belong to the user",
                            errorCode = "NOT_FOUND"
                        )
                    )
            }
        } catch (e: UserNotFoundException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse(message = e.message, errorCode = "USER_NOT_FOUND"))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse(message = "Failed to delete subscription", errorCode = "INTERNAL_ERROR"))
        }
    }
}
