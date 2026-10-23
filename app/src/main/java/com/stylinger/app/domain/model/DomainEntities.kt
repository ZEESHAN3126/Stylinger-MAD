package com.stylinger.app.domain.model

data class Store(
    val id: String,
    val name: String,
    val ownerName: String,
    val city: String,
    val area: String,
    val address: String,
    val categories: List<String>,
    val acceptedPayments: List<String> = listOf("UPI", "Card", "Cash"),
    val rating: Double = 4.8
)

data class StyleDna(
    val bodyShape: String,     // "Hourglass", "Pear", "Rectangle", "Apple", "Inverted Triangle"
    val faceShape: String,     // "Oval", "Round", "Square", "Heart", "Oblong"
    val monkSkinToneScale: Int,// 1 to 10
    val undertone: String      // "Warm", "Cool", "Neutral"
)

data class Order(
    val id: String,
    val productId: String,
    val productName: String,
    val storeId: String,
    val size: String,
    val amount: Double,
    val timestampEpochMs: Long = System.currentTimeMillis(),
    val status: String = "Delivered"
)

enum class AppRole {
    CUSTOMER,
    MERCHANT
}

data class User(
    val id: String,
    val name: String,
    val role: AppRole,
    val city: String,
    val styleDna: StyleDna? = null,
    val storeId: String? = null
)
