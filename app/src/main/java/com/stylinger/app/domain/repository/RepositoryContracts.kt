package com.stylinger.app.domain.repository

import com.stylinger.app.domain.model.AppRole
import com.stylinger.app.domain.model.Order
import com.stylinger.app.domain.model.Product
import com.stylinger.app.domain.model.Store
import com.stylinger.app.domain.model.StyleDna
import com.stylinger.app.domain.model.User
import com.stylinger.app.domain.model.WardrobeItem
import kotlinx.coroutines.flow.StateFlow

interface IAuthRepository {
    val currentUser: StateFlow<User?>
    suspend fun signInAsPersona(role: AppRole): User
    suspend fun switchRole(newRole: AppRole): User
    suspend fun signOut()
}

interface IInventoryRepository {
    suspend fun getProducts(category: String? = null, query: String? = null): List<Product>
    suspend fun getProductById(id: String): Product?
    suspend fun saveProduct(product: Product)
    suspend fun updateStock(productId: String, newQuantity: Int)
    suspend fun decrementStock(productId: String, count: Int = 1)
    suspend fun deleteProduct(productId: String)
}

interface IWardrobeRepository {
    suspend fun getWardrobeItems(category: String? = null): List<WardrobeItem>
    suspend fun addWardrobeItem(item: WardrobeItem)
    suspend fun removeWardrobeItem(id: String)
    suspend fun incrementWearCount(id: String)
}

data class InStoreMatch(
    val product: Product,
    val score: Int,
    val reason: String,
    val compatibleWardrobeItems: List<WardrobeItem> = emptyList()
)

data class DailyOutfit(
    val id: String,
    val title: String,
    val pieces: List<WardrobeItem>,
    val compatibilityScore: Int,
    val occasion: String,
    val weatherNote: String
)

interface IStylingEngine {
    suspend fun generateDailyOutfit(
        wardrobe: List<WardrobeItem>,
        occasion: String,
        styleDna: StyleDna
    ): DailyOutfit

    suspend fun rankStoreInventory(
        storeInventory: List<Product>,
        customerWardrobe: List<WardrobeItem>,
        styleDna: StyleDna,
        budgetCeiling: Double? = null
    ): List<InStoreMatch>
}

interface IOrderRepository {
    suspend fun recordOrder(order: Order)
    suspend fun getOrdersByStore(storeId: String): List<Order>
    suspend fun getOrdersByCustomer(customerId: String): List<Order>
}
