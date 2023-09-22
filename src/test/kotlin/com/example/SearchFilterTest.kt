package com.example

import com.example.repo.SearchRequest
import com.example.repo.SignupRequest
import com.example.services.SearchImageService

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.http.client.HttpClient
import io.micronaut.test.extensions.junit5.annotation.MicronautTest

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.mockito.Mockito.`when`
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

import java.sql.PreparedStatement
import java.sql.ResultSet
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.Mockito.mock

@MicronautTest
class SearchFilterTest {

    @Inject
    lateinit var server: EmbeddedServer

    @Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Mock
    lateinit var searchImage: SearchImageService

    @Inject
    lateinit var mockedDataSource: MockedDataSource

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `test searchImages`() {

        val mockedPreparedStatement = mock(PreparedStatement::class.java)
        val mockedResultSet = mock(ResultSet::class.java)

        `when`(mockedDataSource.mockedConnection.prepareStatement(anyString())).thenReturn(mockedPreparedStatement)
        `when`(mockedPreparedStatement.executeQuery()).thenReturn(mockedResultSet)

//        `when`(searchImage.searchImages("silt", "carrot", "healthy", "2019-07-21", "2019-08-31", "")).thenReturn(null)
        val searchRequest = SearchRequest(soilType = "silt", cropsType = "carrot", healthStatus = "healthy", startDate = "2019-07-21", endDate = "2019-08-31", suggestedLocations = null)
        val request: HttpRequest<SearchRequest> = HttpRequest.POST("/search", searchRequest)
        val response: HttpResponse<String> = client.toBlocking().exchange(request, String::class.java)

        assertEquals(HttpStatus.OK, response.status)
//        Assertions.assertEquals("Signup successful", response.body())
    }
}


