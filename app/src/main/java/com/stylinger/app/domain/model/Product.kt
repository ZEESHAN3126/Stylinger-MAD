package com.stylinger.app.domain.model

data class Product(
    val id: String,
    val storeId: String,
    val name: String,
    val sku: String,
    val category: String,
    val color: String,
    val price: Double,
    val stockQuantity: Int,
    val sizes: List<String>,
    val imageUrl: String,
    val description: String = "",
    val suitableBodyShapes: List<String> = emptyList(),
    val suitableUndertones: List<String> = emptyList()
) {
    val isOutOfStock: Boolean get() = stockQuantity <= 0
    val isLowStock: Boolean get() = stockQuantity in 1..2
}
