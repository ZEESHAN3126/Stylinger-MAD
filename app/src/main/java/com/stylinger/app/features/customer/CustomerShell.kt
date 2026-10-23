package com.stylinger.app.features.customer

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stylinger.app.core.theme.StylingerColors
import com.stylinger.app.core.theme.StylingerTypography
import com.stylinger.app.core.ui.StylingerIcons
import com.stylinger.app.domain.model.AppRole
import com.stylinger.app.domain.repository.IInventoryRepository
import com.stylinger.app.domain.repository.IOrderRepository
import com.stylinger.app.domain.repository.IStylingEngine
import com.stylinger.app.domain.repository.IWardrobeRepository
import com.stylinger.app.features.shared.ModeSwitchSheet
import com.stylinger.app.features.shared.PurchaseSyncCoordinator

enum class CustomerTab(val label: String) {
    HOME("Home"),
    WARDROBE("Wardrobe"),
    SCANNER("Scanner"),
    BOUTIQUES("Boutiques"),
    PROFILE("Profile")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerShell(
    inventoryRepo: IInventoryRepository,
    wardrobeRepo: IWardrobeRepository,
    orderRepo: IOrderRepository,
    stylingEngine: IStylingEngine,
    purchaseCoordinator: PurchaseSyncCoordinator,
    onRoleSelected: (AppRole) -> Unit,
    onSignOut: () -> Unit
) {
    var currentTab by remember { mutableStateOf(CustomerTab.HOME) }
    var inStoreStylistActive by remember { mutableStateOf(false) }
    var showModeSwitcher by remember { mutableStateOf(false) }
    val modeSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Scaffold(
        containerColor = StylingerColors.Canvas,
        bottomBar = {
            if (!inStoreStylistActive) {
                NavigationBar(
                    containerColor = StylingerColors.Surface,
                    tonalElevation = 6.dp
                ) {
                    // 1. Home
                    NavigationBarItem(
                        selected = currentTab == CustomerTab.HOME,
                        onClick = { currentTab = CustomerTab.HOME },
                        icon = {
                            Icon(
                                imageVector = StylingerIcons.Home,
                                contentDescription = "Home",
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        label = {
                            Text(
                                text = "Home",
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

                    // 2. Wardrobe
                    NavigationBarItem(
                        selected = currentTab == CustomerTab.WARDROBE,
                        onClick = { currentTab = CustomerTab.WARDROBE },
                        icon = {
                            Icon(
                                imageVector = StylingerIcons.Wardrobe,
                                contentDescription = "Wardrobe",
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        label = {
                            Text(
                                text = "Wardrobe",
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

                    // 3. Scanner
                    NavigationBarItem(
                        selected = currentTab == CustomerTab.SCANNER,
                        onClick = { currentTab = CustomerTab.SCANNER },
                        icon = {
                            Icon(
                                imageVector = StylingerIcons.Scanner,
                                contentDescription = "Scanner",
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        label = {
                            Text(
                                text = "Scanner",
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

                    // 4. Boutiques
                    NavigationBarItem(
                        selected = currentTab == CustomerTab.BOUTIQUES,
                        onClick = { currentTab = CustomerTab.BOUTIQUES },
                        icon = {
                            Icon(
                                imageVector = StylingerIcons.Store,
                                contentDescription = "Boutiques",
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        label = {
                            Text(
                                text = "Boutiques",
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

                    // 5. Profile
                    NavigationBarItem(
                        selected = currentTab == CustomerTab.PROFILE,
                        onClick = { currentTab = CustomerTab.PROFILE },
                        icon = {
                            Icon(
                                imageVector = StylingerIcons.Person,
                                contentDescription = "Profile",
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        label = {
                            Text(
                                text = "Profile",
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
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (inStoreStylistActive) {
                InStoreStylistScreen(
                    inventoryRepo = inventoryRepo,
                    wardrobeRepo = wardrobeRepo,
                    stylingEngine = stylingEngine,
                    purchaseCoordinator = purchaseCoordinator,
                    onBackToScanner = { inStoreStylistActive = false },
                    onGoToWardrobe = {
                        inStoreStylistActive = false
                        currentTab = CustomerTab.WARDROBE
                    }
                )
            } else {
                when (currentTab) {
                    CustomerTab.HOME -> CustomerHomeScreen(
                        wardrobeRepo = wardrobeRepo,
                        inventoryRepo = inventoryRepo,
                        stylingEngine = stylingEngine,
                        onNavigateToScanner = { currentTab = CustomerTab.SCANNER },
                        onNavigateToWardrobe = { currentTab = CustomerTab.WARDROBE },
                        onOpenModeSwitcher = { showModeSwitcher = true }
                    )
                    CustomerTab.WARDROBE -> CustomerWardrobeScreen(
                        wardrobeRepo = wardrobeRepo,
                        onOpenModeSwitcher = { showModeSwitcher = true }
                    )
                    CustomerTab.SCANNER -> CustomerScannerScreen(
                        onSimulatedStoreScan = { inStoreStylistActive = true }
                    )
                    CustomerTab.BOUTIQUES -> CustomerBoutiquesScreen(
                        onSelectBoutique = {
                            currentTab = CustomerTab.SCANNER
                        }
                    )
                    CustomerTab.PROFILE -> CustomerProfileScreen(
                        orderRepo = orderRepo,
                        onOpenModeSwitcher = { showModeSwitcher = true },
                        onSignOut = onSignOut
                    )
                }
            }
        }

        // Mode Switcher Sheet
        if (showModeSwitcher) {
            ModeSwitchSheet(
                currentRole = AppRole.CUSTOMER,
                onRoleSelected = onRoleSelected,
                onDismiss = { showModeSwitcher = false },
                sheetState = modeSheetState
            )
        }
    }
}
