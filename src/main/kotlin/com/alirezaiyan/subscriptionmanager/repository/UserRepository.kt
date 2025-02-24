package com.alirezaiyan.subscriptionmanager.repository

import com.alirezaiyan.subscriptionmanager.model.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM users WHERE email = :email")
    fun findByEmail(@Param("email") email: String): User?
}
