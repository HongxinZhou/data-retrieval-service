package com.example.controller

import com.example.repo.SignupRequest
import com.example.services.SignupService
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Consumes
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import jakarta.inject.Inject
import io.micronaut.http.server.cors.CrossOrigin

/**
 *  CREATE TABLE "user" (
 * 	id SERIAL PRIMARY KEY,
 * 	name 		VARCHAR(128),
 * 	email 		VARCHAR(128),
 * 	password 	VARCHAR(128)
 * );
 */
@Controller("/signup")
class SignupController @Inject constructor(private val signupService: SignupService) {

    @CrossOrigin(allowedOrigins = ["http://localhost:3000"])
    @Post("/")
    @Consumes(MediaType.APPLICATION_JSON)
    fun signup(@Body signupRequest: SignupRequest): String {
        // Check if user exists
        val userExists = signupService.checkIfUserExists(signupRequest.name)
        if (userExists) {
            return "User already exists"
        }

        // Create user
        val result = signupService.createUser(signupRequest.name, signupRequest.email, signupRequest.password)
        return result
    }
}
