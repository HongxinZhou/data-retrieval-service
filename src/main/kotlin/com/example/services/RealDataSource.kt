package com.example.services

import jakarta.inject.Singleton
import java.sql.Connection
import java.sql.DriverManager

@Singleton
class RealDataSource : DataSource {

    private val jdbcUrl = "jdbc:postgresql://localhost:5432/postgres"
    private val dataUserName = "isaacykc"
    private val dataPassword = "Isaac010329"

    override fun getConnection(): Connection {
        return DriverManager.getConnection(jdbcUrl, dataUserName, dataPassword)
    }
}
