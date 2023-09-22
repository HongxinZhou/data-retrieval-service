package com.example

import com.example.repo.SignupRequest
import com.example.services.SignupService

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

@MicronautTest
class SignupControllerTest {

    @Inject
    lateinit var server: EmbeddedServer

    @Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Mock
    lateinit var signupService: SignupService

    @Inject
    lateinit var mockedDataSource: MockedDataSource

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testSignupUserAlreadyExists() {
        // Mock database operations
        val mockedPreparedStatement = Mockito.mock(PreparedStatement::class.java)
        val mockedResultSet = Mockito.mock(ResultSet::class.java)

        `when`(mockedDataSource.mockedConnection.prepareStatement(anyString())).thenReturn(mockedPreparedStatement)
        `when`(mockedPreparedStatement.executeQuery()).thenReturn(mockedResultSet)
        `when`(mockedResultSet.next()).thenReturn(true) // Indicate a user was found in the database

        `when`(signupService.checkIfUserExists("JIMA@email.com")).thenReturn(true)

        val signupRequest = SignupRequest(name = "JIMA", email = "JIMA@email.com", password = "password123")

        val request: HttpRequest<SignupRequest> = HttpRequest.POST("/signup", signupRequest)
        val response: HttpResponse<String> = client.toBlocking().exchange(request, String::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.status) // Assuming you return 200 OK when user exists
        Assertions.assertEquals("User already exists", response.body())
    }

    @Test
    fun testSignupSuccess() {
        // Mock database operations
        val mockedPreparedStatement = Mockito.mock(PreparedStatement::class.java)
        val mockedResultSet = Mockito.mock(ResultSet::class.java)

        `when`(mockedDataSource.mockedConnection.prepareStatement(anyString())).thenReturn(mockedPreparedStatement)
        `when`(mockedPreparedStatement.executeQuery()).thenReturn(mockedResultSet)
        `when`(mockedResultSet.next()).thenReturn(false) // No user found in the database

        // Mock the service to return success
        `when`(signupService.checkIfUserExists("test@email.com")).thenReturn(false)
        `when`(signupService.createUser("JIMA", "password123", "test@email.com")).thenReturn("Signup successful")

        val signupRequest = SignupRequest(name = "JIMA", email = "test@email.com", password = "password123")

        val request: HttpRequest<SignupRequest> = HttpRequest.POST("/signup", signupRequest)
        val response: HttpResponse<String> = client.toBlocking().exchange(request, String::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.status)
        Assertions.assertEquals("Signup successful", response.body())
    }



}
