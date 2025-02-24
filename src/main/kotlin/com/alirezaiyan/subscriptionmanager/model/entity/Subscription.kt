package com.alirezaiyan.subscriptionmanager.model.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "subscriptions")
data class Subscription(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val serviceName: String,

    @Column(nullable = false)
    val price: Double,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val category: Category = Category.NA,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val period: Period = Period.Monthly,

    @Column(nullable = true)
    val startDate: LocalDate? = null,

    @Column(nullable = true)
    val endDate: LocalDate? = null,


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    val user: User

) {
    override fun toString(): String {
        return "Subscription(id=$id, serviceName=$serviceName, price=$price, category=$category, period=$period, startDate=$startDate, endDate=$endDate, userId=${user.pid})"
    }
}
