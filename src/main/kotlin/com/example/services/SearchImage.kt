package com.example.services

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.inject.Singleton
import java.sql.DriverManager
import java.sql.ResultSet
import java.util.*

@Singleton
open class SearchImageService (private val dataSource: DataSource){

//    private val jdbcUrl = "jdbc:postgresql://localhost:5432/postgres"
//    private val dataUserName = "anton"
//    private val dataPassword = "822681"
    @Serdeable.Serializable
    data class ImageData(
        val photoData: String?,
        val soilType: String?,
        val cropType: String?,
        val healthStatus: String?,
        val longitude: Double?,
        val latitude: Double?

    )

    fun searchImages(
        soilType: String?,
        cropsType: String?,
        healthStatus: String?,
        startDate: String?,
        endDate: String?,
        location: String?
    ): List<ImageData> {
        dataSource.getConnection().use { connection ->
            var sql = "SELECT labels.soil_type, labels.plant_type, labels.health_status, labels.longitude, labels.latitude, photos.photo_data " +
                    "FROM public.labels AS labels " +
                    "JOIN public.photos1 AS photos ON labels.image_name = photos.image_name " +
                    "WHERE 1=1"

            val params = mutableListOf<Any>()

            if (soilType != null) {
                sql += " AND labels.soil_type LIKE ?"
                params.add("%$soilType%")
            }
            if (cropsType != null) {
                sql += " AND labels.plant_type LIKE ?"
                params.add("%$cropsType%")
            }
            if (healthStatus != null) {
                sql += " AND labels.health_status LIKE ?"
                params.add("%$healthStatus%")
            }
            if (startDate != "" && endDate != "") {
                sql += " AND labels.timestamp BETWEEN ? AND ?"
                params.add(java.sql.Date.valueOf(startDate))
                params.add(java.sql.Date.valueOf(endDate))
            } else if (startDate != "") {
                sql += " AND labels.timestamp >= ?"
                params.add(java.sql.Date.valueOf(startDate))
            } else if (endDate != "") {
                sql += " AND labels.timestamp <= ?"
                params.add(java.sql.Date.valueOf(endDate))
            }

            connection.prepareStatement(sql).use { preparedStatement ->
                for ((index, param) in params.withIndex()) {
                    preparedStatement.setObject(index + 1, param)
                }

                val resultSet: ResultSet = preparedStatement.executeQuery()

                val imageDataList = mutableListOf<ImageData>()
                while (resultSet.next()) {
//                    val photoData = resultSet.getBytes("photo_data")
//                    val photoData = Base64.getEncoder().encodeToString(photoDataBytes)
                    val photoData = resultSet.getString("photo_data")
                    val soilType = resultSet.getString("soil_type")
                    val cropType = resultSet.getString("plant_type")
                    val healthStatus = resultSet.getString("health_status")
                    val longitude = resultSet.getDouble("longitude")
                    val latitude = resultSet.getDouble("latitude")

                    val imageData = ImageData(photoData, soilType, cropType, healthStatus, longitude, latitude)
                    imageDataList.add(imageData)
                }

                return imageDataList
            }
        }
    }


}
