package com.stylinger.app.features.merchant

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stylinger.app.core.theme.StylingerColors
import com.stylinger.app.core.theme.StylingerTypography
import com.stylinger.app.core.ui.StylingerIcons
import com.stylinger.app.domain.model.AppRole
import com.stylinger.app.domain.model.Product
import com.stylinger.app.domain.repository.IInventoryRepository
import com.stylinger.app.features.shared.ModeSwitchSheet
import kotlinx.coroutines.launch

enum class MerchantTab(val label: String) {
    DASHBOARD("Dashboard"),
    INVENTORY("Inventory"),
    QR("QR Codes"),
    ANALYTICS("Analytics"),
    STORE("Store")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MerchantShell(
    inventoryRepo: IInventoryRepository,
    onRoleSelected: (AppRole) -> Unit
) {
    var currentTab by remember { mutableStateOf(MerchantTab.DASHBOARD) }
    var selectedProductForDetail by remember { mutableStateOf<Product?>(null) }
    var productToEdit by remember { mutableStateOf<Product?>(null) }
    var showAddDialog by remember { mutableStateOf(false) }
    var showModeSwitcher by remember { mutableStateOf(false) }

    val detailSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val editSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val modeSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Scaffold(
        containerColor = StylingerColors.Canvas,
        bottomBar = {
            NavigationBar(
                containerColor = StylingerColors.Surface,
                tonalElevation = 6.dp
            ) {
                // 1. Dashboard
                NavigationBarItem(
                    selected = currentTab == MerchantTab.DASHBOARD,
                    onClick = { currentTab = MerchantTab.DASHBOARD },
                    icon = {
                        Icon(
                            imageVector = StylingerIcons.Dashboard,
                            contentDescription = "Dashboard",
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    label = {
                        Text(
                            text = "Dashboard",
                            style = StylingerTypography.labelSmall.copy(fontSize = 11.sp)
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = StylingerColors.BrandPrimary,
                        selectedTextColor = StylingerColors.BrandPrimary,
                        indicatorColor = StylingerColors.BrandLight,
                        unselectedIconColor = StylingerColors.TextMuted,
                        unselectedTextColor = StylingerColors.TextMuted
                    )
                )

                // 2. Inventory
                NavigationBarItem(
                    selected = currentTab == MerchantTab.INVENTORY,
                    onClick = { currentTab = MerchantTab.INVENTORY },
                    icon = {
                        Icon(
                            imageVector = StylingerIcons.Inventory,
                            contentDescription = "Inventory",
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    label = {
                        Text(
                            text = "Inventory",
                            style = StylingerTypography.labelSmall.copy(fontSize = 11.sp)
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = StylingerColors.BrandPrimary,
                        selectedTextColor = StylingerColors.BrandPrimary,
                        indicatorColor = StylingerColors.BrandLight,
                        unselectedIconColor = StylingerColors.TextMuted,
                        unselectedTextColor = StylingerColors.TextMuted
                    )
                )

                // 3. QR
                NavigationBarItem(
                    selected = currentTab == MerchantTab.QR,
                    onClick = { currentTab = MerchantTab.QR },
                    icon = {
                        Icon(
                            imageVector = StylingerIcons.QrCode,
                            contentDescription = "QR",
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    label = {
                        Text(
                            text = "QR Codes",
                            style = StylingerTypography.labelSmall.copy(fontSize = 11.sp)
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = StylingerColors.BrandPrimary,
                        selectedTextColor = StylingerColors.BrandPrimary,
                        indicatorColor = StylingerColors.BrandLight,
                        unselectedIconColor = StylingerColors.TextMuted,
                        unselectedTextColor = StylingerColors.TextMuted
                    )
                )

                // 4. Analytics
                NavigationBarItem(
                    selected = currentTab == MerchantTab.ANALYTICS,
                    onClick = { currentTab = MerchantTab.ANALYTICS },
                    icon = {
                        Icon(
                            imageVector = StylingerIcons.Analytics,
                            contentDescription = "Analytics",
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    label = {
                        Text(
                            text = "Analytics",
                            style = StylingerTypography.labelSmall.copy(fontSize = 11.sp)
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = StylingerColors.BrandPrimary,
                        selectedTextColor = StylingerColors.BrandPrimary,
                        indicatorColor = StylingerColors.BrandLight,
                        unselectedIconColor = StylingerColors.TextMuted,
                        unselectedTextColor = StylingerColors.TextMuted
                    )
                )

                // 5. Store
                NavigationBarItem(
                    selected = currentTab == MerchantTab.STORE,
                    onClick = { currentTab = MerchantTab.STORE },
                    icon = {
                        Icon(
                            imageVector = StylingerIcons.Store,
                            contentDescription = "Store",
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    label = {
                        Text(
                            text = "Store",
                            style = StylingerTypography.labelSmall.copy(fontSize = 11.sp)
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = StylingerColors.BrandPrimary,
                        selectedTextColor = StylingerColors.BrandPrimary,
                        indicatorColor = StylingerColors.BrandLight,
                        unselectedIconColor = StylingerColors.TextMuted,
                        unselectedTextColor = StylingerColors.TextMuted
                    )
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentTab) {
                MerchantTab.DASHBOARD -> MerchantDashboardScreen(
                    inventoryRepo = inventoryRepo,
                    onNavigateToInventory = { currentTab = MerchantTab.INVENTORY },
                    onNavigateToAddProduct = { showAddDialog = true },
                    onNavigateToQr = { currentTab = MerchantTab.QR },
                    onOpenModeSwitcher = { showModeSwitcher = true },
                    onSelectProduct = { selectedProductForDetail = it }
                )
                MerchantTab.INVENTORY -> MerchantInventoryScreen(
                    inventoryRepo = inventoryRepo,
                    onSelectProduct = { selectedProductForDetail = it },
                    onNavigateToAddProduct = { showAddDialog = true },
                    onOpenModeSwitcher = { showModeSwitcher = true }
                )
                MerchantTab.QR -> MerchantQrScreen(
                    onOpenModeSwitcher = { showModeSwitcher = true }
                )
                MerchantTab.ANALYTICS -> MerchantAnalyticsScreen()
                MerchantTab.STORE -> MerchantStoreScreen(
                    onOpenModeSwitcher = { showModeSwitcher = true }
                )
            }
        }

        // Product Detail Bottom Sheet
        selectedProductForDetail?.let { product ->
            ProductDetailSheet(
                product = product,
                inventoryRepo = inventoryRepo,
                onDismiss = { selectedProductForDetail = null },
                onEdit = {
                    productToEdit = it
                    selectedProductForDetail = null
                    showAddDialog = true
                },
                onProductUpdated = {
                    // Refreshes upon save
                },
                sheetState = detailSheetState
            )
        }

        // Add or Edit Product Bottom Sheet
        if (showAddDialog) {
            AddEditProductDialog(
                initialProduct = productToEdit,
                inventoryRepo = inventoryRepo,
                onDismiss = {
                    showAddDialog = false
                    productToEdit = null
                },
                onSaved = {
                    showAddDialog = false
                    productToEdit = null
                },
                sheetState = editSheetState
            )
        }

        // Mode Switcher Modal
        if (showModeSwitcher) {
            ModeSwitchSheet(
                currentRole = AppRole.MERCHANT,
                onRoleSelected = onRoleSelected,
                onDismiss = { showModeSwitcher = false },
                sheetState = modeSheetState
            )
        }
    }
}
