package com.example.controller

import com.example.repo.SearchRequest
import com.example.services.SearchImageService
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.http.server.cors.CrossOrigin
import jakarta.inject.Inject

@Controller("/search")
class SearchController @Inject constructor(private val searchImageService: SearchImageService){

    @CrossOrigin(allowedOrigins = ["http://localhost:3000"])
    @Post("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun searchRequest(@Body searchRequest: SearchRequest): HttpResponse<List<SearchImageService.ImageData>> {

        val imgs = searchImageService.searchImages(searchRequest.soilType,
            searchRequest.cropsType,
            searchRequest.healthStatus,
            searchRequest.startDate,
            searchRequest.endDate,
            searchRequest.suggestedLocations)

        return HttpResponse.ok(imgs)
    }
//
}