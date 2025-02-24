package com.alirezaiyan.subscriptionmanager.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class AuthenticatedUser(
    private val userId: String,
    private val email: String,
    private val name: String,
    private val picture: String
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableListOf()
    override fun getPassword(): String? = null
    override fun getUsername(): String = email
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true

    fun getId(): String = userId
    fun getName(): String = name
    fun getPicture(): String = picture
}
