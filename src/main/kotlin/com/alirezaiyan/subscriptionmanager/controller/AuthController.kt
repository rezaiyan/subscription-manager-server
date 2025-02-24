package com.alirezaiyan.subscriptionmanager.controller

import com.alirezaiyan.subscriptionmanager.security.AuthenticatedUser
import com.alirezaiyan.subscriptionmanager.model.dto.ApiResponse
import com.alirezaiyan.subscriptionmanager.model.dto.ErrorResponse
import com.alirezaiyan.subscriptionmanager.model.dto.SuccessResponse
import com.alirezaiyan.subscriptionmanager.model.entity.User
import com.alirezaiyan.subscriptionmanager.repository.UserRepository
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.gson.GsonFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val userRepository: UserRepository
) {
    private val jsonFactory: JsonFactory = GsonFactory.getDefaultInstance()

    @GetMapping("/user")
    fun getUser(@AuthenticationPrincipal user: AuthenticatedUser): ResponseEntity<ApiResponse<Map<String, Any>>> {
        return ResponseEntity.ok(
            SuccessResponse(
                data = mapOf(
                    "email" to user.username,
                    "name" to user.getName(),
                    "picture" to user.getPicture()
                )
            )
        )
    }

    @PostMapping("/google")
    fun verifyGoogleToken(@RequestBody idTokenString: String): ResponseEntity<ApiResponse<Any>> {
        try {
            val rawToken = idTokenString.trim()
            val cleanedToken = rawToken.removeSurrounding("\"")

            val verifier = GoogleIdTokenVerifier.Builder(NetHttpTransport(), jsonFactory)
                .setAudience(listOf("269921399907-84opfong0qurlhct21mo9ec06k2df2qr.apps.googleusercontent.com"))
                .build()

            val idToken = verifier.verify(cleanedToken)
            return if (idToken != null) {
                val payload = idToken.payload
                val email = payload.email
                val name = payload["name"] as String
                val picture = payload["picture"] as String?

                var user = userRepository.findByEmail(email)
                user = if (user == null) {
                    User(
                        userId = UUID.randomUUID().toString(),
                        email = email,
                        name = name,
                        picture = picture
                    )
                } else {
                    user.copy(name = name, picture = picture)
                }
                userRepository.save(user)

                val response = mapOf(
                    "name" to name,
                    "email" to email,
                    "picture" to picture
                )
                ResponseEntity.ok(SuccessResponse(data = response))
            } else {
                ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ErrorResponse(message = "Invalid Token", errorCode = "UNAUTHORIZED"))
            }
        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponse(message = "Invalid Token", errorCode = "UNAUTHORIZED"))
        }
    }
}
