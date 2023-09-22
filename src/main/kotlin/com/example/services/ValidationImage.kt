package com.example.services

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.inject.Singleton
import java.sql.DriverManager
import java.sql.ResultSet
import java.util.*

open class ValidationImage(private val dataSource: DataSource){
    @Serdeable.Serializable
    data class ImageData(
        val photoData: String?,
        val soilType: String?,
        val cropType: String?,
        val healthStatus: String?,
        val longitude: Double?,
        val latitude: Double?

    )
    fun getRandomImages(): List<ImageData> {
        dataSource.getConnection().use { connection ->
            val sql = """
            SELECT * 
            FROM public.labels AS labels 
            JOIN public.photos1 AS photos ON labels.image_name = photos.image_name 
            ORDER BY RANDOM() 
            LIMIT 10
        """

            connection.prepareStatement(sql).use { preparedStatement ->
                val resultSet: ResultSet = preparedStatement.executeQuery()

                val imageDataList = mutableListOf<ImageData>()
                while (resultSet.next()) {
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