package com.stylinger.app.data.repository

import com.stylinger.app.data.mock.MockPersonas
import com.stylinger.app.data.mock.MockProductsData
import com.stylinger.app.data.mock.MockWardrobeData
import com.stylinger.app.domain.model.AppRole
import com.stylinger.app.domain.model.Order
import com.stylinger.app.domain.model.Product
import com.stylinger.app.domain.model.StyleDna
import com.stylinger.app.domain.model.User
import com.stylinger.app.domain.model.WardrobeItem
import com.stylinger.app.domain.repository.DailyOutfit
import com.stylinger.app.domain.repository.IAuthRepository
import com.stylinger.app.domain.repository.IInventoryRepository
import com.stylinger.app.domain.repository.IOrderRepository
import com.stylinger.app.domain.repository.IStylingEngine
import com.stylinger.app.domain.repository.IWardrobeRepository
import com.stylinger.app.domain.repository.InStoreMatch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MockAuthRepository : IAuthRepository {
    private val _currentUser = MutableStateFlow<User?>(MockPersonas.CustomerAnanya)
    override val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    override suspend fun signInAsPersona(role: AppRole): User {
        val user = if (role == AppRole.MERCHANT) MockPersonas.MerchantAura else MockPersonas.CustomerAnanya
        _currentUser.value = user
        return user
    }

    override suspend fun switchRole(newRole: AppRole): User {
        return signInAsPersona(newRole)
    }

    override suspend fun signOut() {
        _currentUser.value = null
    }
}

class MockInventoryRepository : IInventoryRepository {
    private val items = mutableListOf<Product>().apply {
        addAll(MockProductsData.initialProducts)
    }

    override suspend fun getProducts(category: String?, query: String?): List<Product> {
        return items.filter { product ->
            val matchesCat = category == null || category == "All" || product.category.equals(category, ignoreCase = true)
            val matchesQuery = query.isNullOrBlank() ||
                    product.name.contains(query, ignoreCase = true) ||
                    product.sku.contains(query, ignoreCase = true)
            matchesCat && matchesQuery
        }
    }

    override suspend fun getProductById(id: String): Product? {
        return items.find { it.id == id }
    }

    override suspend fun saveProduct(product: Product) {
        val index = items.indexOfFirst { it.id == product.id }
        if (index != -1) {
            items[index] = product
        } else {
            items.add(0, product)
        }
    }

    override suspend fun updateStock(productId: String, newQuantity: Int) {
        val index = items.indexOfFirst { it.id == productId }
        if (index != -1) {
            items[index] = items[index].copy(stockQuantity = newQuantity)
        }
    }

    override suspend fun decrementStock(productId: String, count: Int) {
        val index = items.indexOfFirst { it.id == productId }
        if (index != -1) {
            val current = items[index].stockQuantity
            items[index] = items[index].copy(stockQuantity = (current - count).coerceAtLeast(0))
        }
    }

    override suspend fun deleteProduct(productId: String) {
        items.removeAll { it.id == productId }
    }
}

class MockWardrobeRepository : IWardrobeRepository {
    private val wardrobe = mutableListOf<WardrobeItem>().apply {
        addAll(MockWardrobeData.initialItems)
    }

    override suspend fun getWardrobeItems(category: String?): List<WardrobeItem> {
        return if (category == null || category == "All") {
            wardrobe.toList()
        } else {
            wardrobe.filter { it.category.equals(category, ignoreCase = true) }
        }
    }

    override suspend fun addWardrobeItem(item: WardrobeItem) {
        wardrobe.add(0, item)
    }

    override suspend fun removeWardrobeItem(id: String) {
        wardrobe.removeAll { it.id == id }
    }

    override suspend fun incrementWearCount(id: String) {
        val index = wardrobe.indexOfFirst { it.id == id }
        if (index != -1) {
            wardrobe[index] = wardrobe[index].copy(wearCount = wardrobe[index].wearCount + 1)
        }
    }
}

class MockStylingEngine : IStylingEngine {
    override suspend fun generateDailyOutfit(
        wardrobe: List<WardrobeItem>,
        occasion: String,
        styleDna: StyleDna
    ): DailyOutfit {
        val tops = wardrobe.filter { it.category == "Shirts" || it.category == "T-Shirts" }
        val bottoms = wardrobe.filter { it.category == "Jeans" || it.category == "Trousers" }
        val outerwear = wardrobe.filter { it.category == "Outerwear" }

        val selectedPieces = mutableListOf<WardrobeItem>()
        if (tops.isNotEmpty()) selectedPieces.add(tops.first())
        if (bottoms.isNotEmpty()) selectedPieces.add(bottoms.first())
        if (outerwear.isNotEmpty()) selectedPieces.add(outerwear.first())

        return DailyOutfit(
            id = "outfit_${System.currentTimeMillis()}",
            title = "$occasion Curated Edit",
            pieces = selectedPieces,
            compatibilityScore = 96,
            occasion = occasion,
            weatherNote = "28° · Sunny, Vadodara"
        )
    }

    override suspend fun rankStoreInventory(
        storeInventory: List<Product>,
        customerWardrobe: List<WardrobeItem>,
        styleDna: StyleDna,
        budgetCeiling: Double?
    ): List<InStoreMatch> {
        return storeInventory
            .filter { budgetCeiling == null || it.price <= budgetCeiling }
            .map { product ->
                var score = 70
                if (product.suitableUndertones.contains(styleDna.undertone)) score += 18
                if (product.suitableBodyShapes.contains(styleDna.bodyShape)) score += 12

                val matchingOwned = customerWardrobe.filter { w ->
                    (product.category == "Outerwear" && (w.category == "Shirts" || w.category == "Jeans")) ||
                    (product.category == "Shirts" && w.category == "Trousers")
                }.take(2)

                InStoreMatch(
                    product = product,
                    score = score.coerceIn(0, 99),
                    reason = "Flattering for ${styleDna.bodyShape} silhouette with ${styleDna.undertone} undertone",
                    compatibleWardrobeItems = matchingOwned
                )
            }
            .sortedByDescending { it.score }
    }
}

class MockOrderRepository : IOrderRepository {
    private val orders = mutableListOf<Order>()

    override suspend fun recordOrder(order: Order) {
        orders.add(0, order)
    }

    override suspend fun getOrdersByStore(storeId: String): List<Order> {
        return orders.filter { it.storeId == storeId }
    }

    override suspend fun getOrdersByCustomer(customerId: String): List<Order> {
        return orders.toList()
    }
}
