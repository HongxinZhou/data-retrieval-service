package com.example.repo

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class SignupRequest(val name: String, val email: String, val password: String)
