package com.stylinger.app.domain.model

data class WardrobeItem(
    val id: String,
    val originalProductId: String? = null,
    val name: String,
    val category: String,
    val color: String,
    val imageUrl: String,
    val wearCount: Int = 0,
    val source: String, // "auto_qr" | "manual" | "quick_add"
    val addedAtEpochMs: Long = System.currentTimeMillis()
)
