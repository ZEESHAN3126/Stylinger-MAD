package com.stylinger.app.features.shared

import com.stylinger.app.domain.model.Order
import com.stylinger.app.domain.model.Product
import com.stylinger.app.domain.model.WardrobeItem
import com.stylinger.app.domain.repository.IInventoryRepository
import com.stylinger.app.domain.repository.IOrderRepository
import com.stylinger.app.domain.repository.IWardrobeRepository

sealed class PurchaseResult {
    data class Success(val order: Order, val wardrobeItem: WardrobeItem) : PurchaseResult()
    data class Failure(val reason: String) : PurchaseResult()
}

class PurchaseSyncCoordinator(
    private val inventoryRepo: IInventoryRepository,
    private val wardrobeRepo: IWardrobeRepository,
    private val orderRepo: IOrderRepository
) {
    suspend fun executeInStorePurchase(
        product: Product,
        selectedSize: String,
        customerId: String
    ): PurchaseResult {
        // Step A: Stock validation
        if (product.stockQuantity <= 0) {
            return PurchaseResult.Failure("Garment is sold out.")
        }

        val originalQuantity = product.stockQuantity

        try {
            // Step B & C: Decrement stock
            inventoryRepo.decrementStock(product.id, 1)

            // Step D: Auto-Inject into Customer Wardrobe
            val newWardrobePiece = WardrobeItem(
                id = "w_${System.currentTimeMillis()}",
                originalProductId = product.id,
                name = product.name,
                category = product.category,
                color = product.color,
                imageUrl = product.imageUrl,
                wearCount = 0,
                source = "auto_qr"
            )
            wardrobeRepo.addWardrobeItem(newWardrobePiece)

            // Step E: Commit Order Record
            val order = Order(
                id = "ORD-${System.currentTimeMillis().toString().takeLast(6)}",
                productId = product.id,
                productName = product.name,
                storeId = product.storeId,
                size = selectedSize,
                amount = product.price,
                status = "Delivered"
            )
            orderRepo.recordOrder(order)

            return PurchaseResult.Success(order, newWardrobePiece)
        } catch (e: Exception) {
            // Rollback inventory on failure
            inventoryRepo.updateStock(product.id, originalQuantity)
            return PurchaseResult.Failure("Transaction failed and rolled back safely: ${e.localizedMessage}")
        }
    }
}
