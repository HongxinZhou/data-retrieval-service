package com.example.controller


import com.example.repo.ValidationRequest
import com.example.services.ValidationImage
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import jakarta.inject.Inject
import io.micronaut.http.server.cors.CrossOrigin

@Controller("/validation")
open class ValidationController @Inject constructor(private val validationImage: ValidationImage){
    @CrossOrigin(allowedOrigins = ["http://localhost:3000"])
    @Get("/")
    @Produces(MediaType.APPLICATION_JSON)
    fun getImageRequest(): HttpResponse<List<ValidationImage.ImageData>> {

        val imgs = validationImage.getRandomImages()

        return HttpResponse.ok(imgs)
    }

    @CrossOrigin(allowedOrigins = ["http://localhost:3000"])
    @Post("/")
    @Produces(MediaType.APPLICATION_JSON)
    fun UpdateVoteRequest(@Body validationRequest: ValidationRequest): String {
        println("UpdateVoteRequest")
        print(validationRequest.ImageId.toString()+ validationRequest.type.toString()+ validationRequest.vote.toString())
        return "Translation submitted"
    }
}