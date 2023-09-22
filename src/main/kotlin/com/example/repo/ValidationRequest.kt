package com.example.repo

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class ValidationRequest(val ImageId: String, val type: String, val vote: String)