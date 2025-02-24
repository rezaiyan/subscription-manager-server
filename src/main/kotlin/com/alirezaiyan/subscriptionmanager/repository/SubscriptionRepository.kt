package com.alirezaiyan.subscriptionmanager.repository

import com.alirezaiyan.subscriptionmanager.model.entity.Subscription
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface SubscriptionRepository : JpaRepository<Subscription, Long> {

    @Query("""
        SELECT COALESCE(SUM(
            CASE 
                WHEN s.period = 'Monthly' THEN s.price 
                WHEN s.period = 'Yearly' THEN s.price / 12 
                ELSE 0
            END
        ), 0) 
        FROM Subscription s
        WHERE s.user.pid = :pid
    """)
    fun getTotalMonthlyAmountByUser(@Param("pid") pid: Long): Double

    @Query("""
        SELECT COALESCE(SUM(
            CASE 
                WHEN s.period = 'Yearly' THEN s.price
                WHEN s.period = 'Monthly' THEN s.price * 12 
                ELSE 0
            END
        ), 0) 
        FROM Subscription s
        WHERE s.user.pid = :pid
    """)
    fun getTotalYearlyAmountByUser(@Param("pid") pid: Long): Double

    @Query("SELECT s FROM Subscription s WHERE s.user.pid = :pid")
    fun findByUserPid(@Param("pid") pid: Long): List<Subscription>

    @Query("SELECT s FROM Subscription s WHERE s.id = :subscriptionId AND s.user.pid = :pid")
    fun findByIdAndUserPid(@Param("subscriptionId") subscriptionId: Long, @Param("pid") pid: Long): Subscription?

    @Modifying
    @Transactional
    @Query("DELETE FROM Subscription s WHERE s.id = :subscriptionId AND s.user.pid = :pid")
    fun deleteByIdAndUserPid(@Param("subscriptionId") subscriptionId: Long, @Param("pid") pid: Long): Boolean

}
