package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey val id: String,
    val clientName: String,
    val clientPhone: String,
    val serviceId: String,
    val serviceTitle: String,
    val pricePkr: Int,
    val customNotes: String,
    val urgency: String,
    val photoUri: String?,
    val status: String,
    val timestamp: Long
)

@Entity(tableName = "service_prices")
data class ServicePriceEntity(
    @PrimaryKey val serviceId: String,
    val pricePkr: Int
)
