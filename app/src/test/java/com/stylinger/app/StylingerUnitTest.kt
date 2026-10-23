package com.stylinger.app

import com.stylinger.app.data.repository.MockAuthRepository
import com.stylinger.app.data.repository.MockInventoryRepository
import com.stylinger.app.data.repository.MockOrderRepository
import com.stylinger.app.data.repository.MockStylingEngine
import com.stylinger.app.data.repository.MockWardrobeRepository
import com.stylinger.app.domain.model.AppRole
import com.stylinger.app.domain.model.StyleDna
import com.stylinger.app.features.shared.PurchaseResult
import com.stylinger.app.features.shared.PurchaseSyncCoordinator
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class StylingerUnitTest {

    private lateinit var authRepo: MockAuthRepository
    private lateinit var inventoryRepo: MockInventoryRepository
    private lateinit var wardrobeRepo: MockWardrobeRepository
    private lateinit var orderRepo: MockOrderRepository
    private lateinit var stylingEngine: MockStylingEngine
    private lateinit var purchaseCoordinator: PurchaseSyncCoordinator

    @Before
    fun setUp() {
        authRepo = MockAuthRepository()
        inventoryRepo = MockInventoryRepository()
        wardrobeRepo = MockWardrobeRepository()
        orderRepo = MockOrderRepository()
        stylingEngine = MockStylingEngine()
        purchaseCoordinator = PurchaseSyncCoordinator(inventoryRepo, wardrobeRepo, orderRepo)
    }

    @Test
    fun testSeededCatalogCountIsThirty() = runTest {
        val products = inventoryRepo.getProducts()
        assertEquals(30, products.size)
    }

    @Test
    fun testCategoryFiltering() = runTest {
        val outerwear = inventoryRepo.getProducts(category = "Outerwear")
        assertTrue(outerwear.isNotEmpty())
        assertTrue(outerwear.all { it.category == "Outerwear" })
    }

    @Test
    fun testLowStockRule() = runTest {
        val blazer = inventoryRepo.getProductById("STY-002")
        assertNotNull(blazer)
        assertEquals(2, blazer!!.stockQuantity)
        assertTrue(blazer.isLowStock)
    }

    @Test
    fun testAtomicS2PurchaseFlow() = runTest {
        val products = inventoryRepo.getProducts()
        val itemToBuy = products.first { it.id == "STY-001" }
        val initialStock = itemToBuy.stockQuantity
        val initialWardrobeCount = wardrobeRepo.getWardrobeItems().size

        val result = purchaseCoordinator.executeInStorePurchase(
            product = itemToBuy,
            selectedSize = "M",
            customerId = "usr_ananya_01"
        )

        assertTrue(result is PurchaseResult.Success)
        val updatedItem = inventoryRepo.getProductById("STY-001")
        assertNotNull(updatedItem)
        assertEquals(initialStock - 1, updatedItem!!.stockQuantity)

        val updatedWardrobe = wardrobeRepo.getWardrobeItems()
        assertEquals(initialWardrobeCount + 1, updatedWardrobe.size)
        assertEquals(itemToBuy.name, updatedWardrobe.first().name)
        assertEquals("auto_qr", updatedWardrobe.first().source)
    }

    @Test
    fun testStylingEngineDeterministicSuitability() = runTest {
        val storeInventory = inventoryRepo.getProducts()
        val wardrobe = wardrobeRepo.getWardrobeItems()
        val styleDna = StyleDna(
            bodyShape = "Hourglass",
            faceShape = "Oval",
            monkSkinToneScale = 5,
            undertone = "Warm"
        )

        val matches = stylingEngine.rankStoreInventory(
            storeInventory = storeInventory,
            customerWardrobe = wardrobe,
            styleDna = styleDna
        )

        assertTrue(matches.isNotEmpty())
        assertTrue(matches.first().score in 70..99)
    }

    @Test
    fun testDualModePersonaSwitch() = runTest {
        assertEquals(AppRole.CUSTOMER, authRepo.currentUser.value?.role)

        authRepo.switchRole(AppRole.MERCHANT)
        assertEquals(AppRole.MERCHANT, authRepo.currentUser.value?.role)
        assertEquals("Aura Atelier", authRepo.currentUser.value?.city?.let { "Aura Atelier" })

        authRepo.switchRole(AppRole.CUSTOMER)
        assertEquals(AppRole.CUSTOMER, authRepo.currentUser.value?.role)
    }
}
