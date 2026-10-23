package com.stylinger.app.features.customer

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stylinger.app.core.theme.StylingerColors
import com.stylinger.app.core.theme.StylingerTypography
import com.stylinger.app.core.ui.LiquidGlassCard
import com.stylinger.app.core.ui.StylingerIcons
import com.stylinger.app.data.mock.MockPersonas
import com.stylinger.app.domain.model.Product
import com.stylinger.app.domain.repository.IInventoryRepository
import com.stylinger.app.domain.repository.IStylingEngine
import com.stylinger.app.domain.repository.IWardrobeRepository
import com.stylinger.app.domain.repository.InStoreMatch
import com.stylinger.app.features.shared.PurchaseResult
import com.stylinger.app.features.shared.PurchaseSyncCoordinator
import kotlinx.coroutines.launch

@Composable
fun InStoreStylistScreen(
    inventoryRepo: IInventoryRepository,
    wardrobeRepo: IWardrobeRepository,
    stylingEngine: IStylingEngine,
    purchaseCoordinator: PurchaseSyncCoordinator,
    onBackToScanner: () -> Unit,
    onGoToWardrobe: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var matches by remember { mutableStateOf<List<InStoreMatch>>(emptyList()) }
    var purchaseFeedback by remember { mutableStateOf<String?>(null) }
    var purchasedProductId by remember { mutableStateOf<String?>(null) }

    val customer = MockPersonas.CustomerAnanya

    LaunchedEffect(Unit) {
        val inventory = inventoryRepo.getProducts()
        val wardrobe = wardrobeRepo.getWardrobeItems()
        matches = stylingEngine.rankStoreInventory(
            storeInventory = inventory,
            customerWardrobe = wardrobe,
            styleDna = customer.styleDna!!
        ).take(4)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(StylingerColors.Canvas)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        // Back Bar & Store Badge
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = StylingerColors.Surface,
                border = BorderStroke(0.8.dp, StylingerColors.Border),
                modifier = Modifier.clickable { onBackToScanner() }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = StylingerIcons.ArrowBack,
                        contentDescription = "Back to scanner",
                        tint = StylingerColors.TextPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Scanner",
                        style = StylingerTypography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = StylingerColors.TextPrimary
                    )
                }
            }

            Box(
                modifier = Modifier
                    .background(StylingerColors.BrandLight, RoundedCornerShape(6.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "LOCAL DEMO STYLING ENGINE",
                    color = StylingerColors.BrandPrimary,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Store Header
        Text(
            text = "Aura Atelier",
            style = StylingerTypography.displayMedium.copy(
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.SemiBold
            ),
            color = StylingerColors.TextPrimary
        )
        Text(
            text = "Your personalized store edit · Alkapuri, Vadodara",
            style = StylingerTypography.bodyMedium,
            color = StylingerColors.TextSecondary
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Honest Mode Banner
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = StylingerColors.Surface),
            border = BorderStroke(1.dp, StylingerColors.Border)
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(StylingerColors.WarningLight),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = StylingerIcons.Star,
                        contentDescription = null,
                        tint = StylingerColors.Warning,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "HONEST STYLING ADVISORY",
                        style = StylingerTypography.labelSmall,
                        color = StylingerColors.Warning
                    )
                    Text(
                        text = "Curated exclusively for your Rectangle silhouette & Warm undertone.",
                        style = StylingerTypography.bodySmall,
                        color = StylingerColors.TextPrimary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Purchase Feedback Alert
        if (purchaseFeedback != null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = StylingerColors.SuccessLight),
                border = BorderStroke(1.dp, StylingerColors.Success)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "PURCHASE & WARDROBE SYNCED!",
                            style = StylingerTypography.labelSmall,
                            color = StylingerColors.Success
                        )
                        Text(
                            text = purchaseFeedback!!,
                            style = StylingerTypography.bodySmall,
                            color = StylingerColors.TextPrimary
                        )
                    }
                    Button(
                        onClick = onGoToWardrobe,
                        colors = ButtonDefaults.buttonColors(containerColor = StylingerColors.Success),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(text = "View Closet", fontSize = 11.sp, color = Color.White)
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Ranked In-Store Matches
        Text(
            text = "RECOMMENDED IN-STORE PIECES",
            style = StylingerTypography.labelSmall,
            color = StylingerColors.BrandPrimary
        )

        Spacer(modifier = Modifier.height(10.dp))

        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            matches.forEachIndexed { index, match ->
                val isBest = index == 0
                val isBought = purchasedProductId == match.product.id

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = StylingerColors.Surface),
                    border = BorderStroke(
                        width = if (isBest) 1.5.dp else 0.8.dp,
                        color = if (isBest) StylingerColors.BrandPrimary else StylingerColors.Border
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = if (isBest) 3.dp else 1.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if (isBest) {
                                    Box(
                                        modifier = Modifier
                                            .background(StylingerColors.BrandPrimary, RoundedCornerShape(6.dp))
                                            .padding(horizontal = 8.dp, vertical = 3.dp)
                                    ) {
                                        Text(
                                            text = "BEST MATCH",
                                            color = Color.White,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                }
                                Text(
                                    text = match.product.category.uppercase(),
                                    style = StylingerTypography.labelSmall,
                                    color = StylingerColors.TextSecondary
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .background(StylingerColors.BrandLight, RoundedCornerShape(8.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "${match.score}% Match",
                                    color = StylingerColors.BrandPrimary,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Product Title & Price
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = match.product.name,
                                    style = StylingerTypography.titleMedium.copy(fontFamily = FontFamily.Serif),
                                    color = StylingerColors.TextPrimary
                                )
                                Text(
                                    text = "SKU: ${match.product.sku} · ${match.product.color}",
                                    style = StylingerTypography.bodySmall,
                                    color = StylingerColors.TextMuted
                                )
                            }
                            Text(
                                text = "₹${match.product.price.toInt()}",
                                style = StylingerTypography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                color = StylingerColors.TextPrimary
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Reason for match
                        Text(
                            text = "“${match.reason}”",
                            style = StylingerTypography.bodySmall,
                            color = StylingerColors.TextSecondary
                        )

                        // Pair with what I own section
                        if (match.compatibleWardrobeItems.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "PAIR WITH WHAT I OWN",
                                style = StylingerTypography.labelSmall.copy(fontSize = 10.sp),
                                color = StylingerColors.TextSecondary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                match.compatibleWardrobeItems.forEach { owned ->
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(StylingerColors.Canvas)
                                            .border(0.6.dp, StylingerColors.Border, RoundedCornerShape(6.dp))
                                            .padding(horizontal = 8.dp, vertical = 4.dp)
                                    ) {
                                        Text(
                                            text = "✓ ${owned.name}",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = StylingerColors.TextPrimary
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Buy in store (S2 flow)
                        Button(
                            onClick = {
                                scope.launch {
                                    val result = purchaseCoordinator.executeInStorePurchase(
                                        product = match.product,
                                        selectedSize = match.product.sizes.firstOrNull() ?: "M",
                                        customerId = customer.id
                                    )
                                    when (result) {
                                        is PurchaseResult.Success -> {
                                            purchasedProductId = match.product.id
                                            purchaseFeedback = "Decremented Aura stock and auto-added ${result.order.productName} into Ananya's digital closet."
                                        }
                                        is PurchaseResult.Failure -> {
                                            purchaseFeedback = "Transaction failed: ${result.reason}"
                                        }
                                    }
                                }
                            },
                            enabled = !isBought,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(44.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isBought) StylingerColors.Success else StylingerColors.TextPrimary
                            )
                        ) {
                            Icon(
                                imageVector = if (isBought) StylingerIcons.Check else StylingerIcons.Add,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isBought) "Added to Digital Wardrobe!" else "Buy In-Store (Simulate S2 Flow)",
                                style = StylingerTypography.labelLarge,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}
