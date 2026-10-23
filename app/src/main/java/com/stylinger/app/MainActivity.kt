package com.stylinger.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.stylinger.app.core.theme.StylingerColors
import com.stylinger.app.core.theme.StylingerTheme
import com.stylinger.app.data.repository.MockAuthRepository
import com.stylinger.app.data.repository.MockInventoryRepository
import com.stylinger.app.data.repository.MockOrderRepository
import com.stylinger.app.data.repository.MockStylingEngine
import com.stylinger.app.data.repository.MockWardrobeRepository
import com.stylinger.app.domain.model.AppRole
import com.stylinger.app.features.customer.CustomerShell
import com.stylinger.app.features.merchant.MerchantShell
import com.stylinger.app.features.shared.PurchaseSyncCoordinator
import com.stylinger.app.features.welcome.WelcomeScreen
import kotlinx.coroutines.launch

enum class AppDestination {
    WELCOME,
    MERCHANT,
    CUSTOMER
}

class MainActivity : ComponentActivity() {
    private val authRepo = MockAuthRepository()
    private val inventoryRepo = MockInventoryRepository()
    private val wardrobeRepo = MockWardrobeRepository()
    private val orderRepo = MockOrderRepository()
    private val stylingEngine = MockStylingEngine()
    private val purchaseCoordinator = PurchaseSyncCoordinator(inventoryRepo, wardrobeRepo, orderRepo)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StylingerTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .safeDrawingPadding(),
                    color = StylingerColors.Canvas
                ) {
                    StylingerRoot(
                        authRepo = authRepo,
                        inventoryRepo = inventoryRepo,
                        wardrobeRepo = wardrobeRepo,
                        orderRepo = orderRepo,
                        stylingEngine = stylingEngine,
                        purchaseCoordinator = purchaseCoordinator
                    )
                }
            }
        }
    }
}

@Composable
fun StylingerRoot(
    authRepo: MockAuthRepository,
    inventoryRepo: MockInventoryRepository,
    wardrobeRepo: MockWardrobeRepository,
    orderRepo: MockOrderRepository,
    stylingEngine: MockStylingEngine,
    purchaseCoordinator: PurchaseSyncCoordinator
) {
    var destination by remember { mutableStateOf(AppDestination.WELCOME) }
    val scope = rememberCoroutineScope()

    Crossfade(targetState = destination, label = "AppNavigationCrossfade") { currentDest ->
        when (currentDest) {
            AppDestination.WELCOME -> {
                WelcomeScreen(
                    onSelectRole = { role ->
                        scope.launch {
                            authRepo.signInAsPersona(role)
                            destination = if (role == AppRole.MERCHANT) {
                                AppDestination.MERCHANT
                            } else {
                                AppDestination.CUSTOMER
                            }
                        }
                    }
                )
            }

            AppDestination.MERCHANT -> {
                MerchantShell(
                    inventoryRepo = inventoryRepo,
                    onRoleSelected = { newRole ->
                        scope.launch {
                            authRepo.switchRole(newRole)
                            destination = if (newRole == AppRole.MERCHANT) {
                                AppDestination.MERCHANT
                            } else {
                                AppDestination.CUSTOMER
                            }
                        }
                    }
                )
            }

            AppDestination.CUSTOMER -> {
                CustomerShell(
                    inventoryRepo = inventoryRepo,
                    wardrobeRepo = wardrobeRepo,
                    orderRepo = orderRepo,
                    stylingEngine = stylingEngine,
                    purchaseCoordinator = purchaseCoordinator,
                    onRoleSelected = { newRole ->
                        scope.launch {
                            authRepo.switchRole(newRole)
                            destination = if (newRole == AppRole.MERCHANT) {
                                AppDestination.MERCHANT
                            } else {
                                AppDestination.CUSTOMER
                            }
                        }
                    },
                    onSignOut = {
                        scope.launch {
                            authRepo.signOut()
                            destination = AppDestination.WELCOME
                        }
                    }
                )
            }
        }
    }
}
