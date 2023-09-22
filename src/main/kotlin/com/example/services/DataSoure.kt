package com.example.services

import java.sql.Connection

interface DataSource {
    fun getConnection(): Connection
}
