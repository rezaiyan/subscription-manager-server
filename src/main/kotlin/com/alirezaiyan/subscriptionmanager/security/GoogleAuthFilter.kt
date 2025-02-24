package com.alirezaiyan.subscriptionmanager.security

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter


@Component
class GoogleAuthFilter(private val verifier: GoogleIdTokenVerifier) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val authHeader = request.getHeader("Authorization")

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header")
                return
            }

            val idTokenString = authHeader.removePrefix("Bearer ").trim().removeSurrounding("\"")
            val idToken: GoogleIdToken? = verifier.verify(idTokenString)

            if (idToken == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token")
                return
            }

            val payload = idToken.payload
            if (!payload.emailVerified) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Email not verified")
                return
            }

            val email = payload.email
            val userId = payload.subject
            val name = payload["name"] as String? ?: "Unknown"
            val picture = payload["picture"] as String? ?: ""

            val userDetails = AuthenticatedUser(userId, email, name, picture)
            val authentication = UsernamePasswordAuthenticationToken(userDetails, null, emptyList())
            SecurityContextHolder.getContext().authentication = authentication
        } catch (e: Exception) {
            logger.error("Token verification failed", e)
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid authentication token")
            return
        }

        filterChain.doFilter(request, response)
    }
}
