package com.example.services

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Consumes
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post

import jakarta.inject.Singleton
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Connection
import java.sql.PreparedStatement

@Singleton
open class SignupService (private val dataSource: DataSource){

    private val jdbcUrl = "jdbc:postgresql://localhost:5432/postgres"
    private val dataUserName = "isaacykc"
    private val dataPassword = "Isaac010329"

    fun checkIfUserExists(userEmail: String): Boolean {
        dataSource.getConnection().use { connection ->
            val sql = "SELECT * FROM public.user WHERE email = ?"

            connection.prepareStatement(sql).use { preparedStatement ->
                preparedStatement.setString(1, userEmail)
                val resultSet: ResultSet = preparedStatement.executeQuery()
                return resultSet.next()
            }
        }
    }

    fun createUser(username: String, userEmail: String,  userPassword: String): String {
        dataSource.getConnection().use { connection ->
            val insertSql = "INSERT INTO public.user (name, email, password) VALUES (?, ?, ?)"
            connection.prepareStatement(insertSql).use { preparedStatement ->
                preparedStatement.setString(1, username)
                preparedStatement.setString(2, userEmail)
                preparedStatement.setString(3, userPassword)

                preparedStatement.executeUpdate()
            }
        }
        return "Signup successful"
    }
}
