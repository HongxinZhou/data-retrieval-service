package com.example.repo

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class SearchRequest(val soilType: String?, val cropsType: String?, val healthStatus: String?, val startDate: String?, val endDate: String?, val suggestedLocations: String?)
