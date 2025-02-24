package com.alirezaiyan.subscriptionmanager.service

import com.alirezaiyan.subscriptionmanager.model.dto.SubscriptionTotalDto
import com.alirezaiyan.subscriptionmanager.model.entity.Period
import com.alirezaiyan.subscriptionmanager.model.entity.Subscription
import com.alirezaiyan.subscriptionmanager.model.entity.User
import com.alirezaiyan.subscriptionmanager.repository.SubscriptionRepository
import com.alirezaiyan.subscriptionmanager.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class SubscriptionManagementService(
    private val subscriptionRepository: SubscriptionRepository,
    private val userRepository: UserRepository
) {

    fun getUserByEmail(email: String): User? = userRepository.findByEmail(email)

    fun getAllSubscriptions(userEmail: String): List<Subscription> {
        val user = getUserByEmail(userEmail) ?: throw UserNotFoundException()
        return subscriptionRepository.findByUserPid(user.pid).reversed()
    }

    fun getSubscriptionByIdAndUserEmail(subscriptionId: Long, userEmail: String): Subscription? {
        val user = getUserByEmail(userEmail) ?: throw UserNotFoundException()
        return subscriptionRepository.findByIdAndUserPid(subscriptionId, user.pid)
    }

    fun createSubscription(subscription: Subscription, userEmail: String): Subscription {
        val user = getUserByEmail(userEmail) ?: throw UserNotFoundException()
        return subscriptionRepository.save(subscription.copy(user = user))
    }

    fun deleteSubscriptionByUserEmail(subscriptionId: Long, userEmail: String): Boolean {
        val user = getUserByEmail(userEmail) ?: throw UserNotFoundException()
        return subscriptionRepository.deleteByIdAndUserPid(subscriptionId, user.pid)
    }

    fun getTotalAmountByUserEmail(userEmail: String): SubscriptionTotalDto {
        val user = getUserByEmail(userEmail) ?: throw UserNotFoundException()
        val totalMonthlyAmount = subscriptionRepository.getTotalMonthlyAmountByUser(user.pid)
        val totalYearlyAmount = subscriptionRepository.getTotalYearlyAmountByUser(user.pid)

        return SubscriptionTotalDto(
            totalAmount = totalYearlyAmount,
            monthlyAmount = totalMonthlyAmount
        )

    }
}

class UserNotFoundException(override val message: String = "User not found") : RuntimeException(message)
