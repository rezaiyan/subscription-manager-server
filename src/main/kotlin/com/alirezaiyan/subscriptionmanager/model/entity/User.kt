package com.alirezaiyan.subscriptionmanager.model.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.springframework.data.jpa.domain.AbstractPersistable_.id


@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val pid: Long = 0,
    @Column(nullable = false, unique = true)
    private val userId: String,
    @Column(nullable = false, unique = true)
    private val email: String,
    @Column(nullable = false, unique = true)
    private val name: String,
    @Column(nullable = true, unique = true)
    private val picture: String?,
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonIgnore
    val subscriptions: List<Subscription> = mutableListOf()
) {
    override fun toString(): String {
        return "User{id=$id, name='$name'}"
    }
}
