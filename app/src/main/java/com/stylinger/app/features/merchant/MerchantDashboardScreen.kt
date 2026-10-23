package com.stylinger.app.features.merchant

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
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.stylinger.app.core.ui.StockStatusBadge
import com.stylinger.app.core.ui.StylingerIcons
import com.stylinger.app.domain.model.Product
import com.stylinger.app.domain.repository.IInventoryRepository

@Composable
fun MerchantDashboardScreen(
    inventoryRepo: IInventoryRepository,
    onNavigateToInventory: () -> Unit,
    onNavigateToAddProduct: () -> Unit,
    onNavigateToQr: () -> Unit,
    onOpenModeSwitcher: () -> Unit,
    onSelectProduct: (Product) -> Unit
) {
    var products by remember { mutableStateOf<List<Product>>(emptyList()) }
    var lowStockProducts by remember { mutableStateOf<List<Product>>(emptyList()) }

    LaunchedEffect(Unit) {
        val all = inventoryRepo.getProducts()
        products = all
        lowStockProducts = all.filter { it.isLowStock || it.isOutOfStock }.take(3)
    }

    val inStockCount = products.count { it.stockQuantity > 2 }
    val lowStockCount = products.count { it.stockQuantity in 1..2 }
    val soldOutCount = products.count { it.stockQuantity <= 0 }
    val totalCount = products.size.coerceAtLeast(1)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(StylingerColors.Canvas)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        // 1. Header with Aura Atelier & Top Action icons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Aura Atelier",
                        style = StylingerTypography.headlineLarge.copy(
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = StylingerColors.TextPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .background(StylingerColors.BrandLight, RoundedCornerShape(6.dp))
                            .padding(horizontal = 7.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "STORE",
                            color = StylingerColors.BrandPrimary,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Text(
                    text = "Alkapuri, Vadodara · Active Pilot",
                    style = StylingerTypography.bodyMedium,
                    color = StylingerColors.TextSecondary
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                // Notification Icon
                IconButton(
                    onClick = { /* Notification indicator */ },
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(StylingerColors.Surface)
                        .border(0.8.dp, StylingerColors.Border, CircleShape)
                ) {
                    Icon(
                        imageVector = StylingerIcons.Notifications,
                        contentDescription = "Notifications",
                        tint = StylingerColors.TextPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Mode Switch Icon
                IconButton(
                    onClick = onOpenModeSwitcher,
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(StylingerColors.BrandPrimary)
                ) {
                    Icon(
                        imageVector = StylingerIcons.Swap,
                        contentDescription = "Switch Mode",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 2. KPI Cards Grid (Today's Scans, Revenue, Products, Low Stock)
        Row(modifier = Modifier.fillMaxWidth()) {
            KpiCard(
                label = "Today's Scans",
                value = "18",
                delta = "+12%",
                deltaPositive = true,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(12.dp))
            KpiCard(
                label = "Today's Revenue",
                value = "₹24,800",
                delta = "4 orders",
                deltaPositive = true,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            KpiCard(
                label = "Total Products",
                value = "${products.size}",
                delta = "10 categories",
                deltaPositive = true,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(12.dp))
            KpiCard(
                label = "Low Stock Alert",
                value = "${lowStockCount + soldOutCount}",
                delta = "Needs reorder",
                deltaPositive = false,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 3. Quick Action Buttons
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = onNavigateToAddProduct,
                modifier = Modifier
                    .weight(1f)
                    .height(46.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = StylingerColors.BrandPrimary)
            ) {
                Icon(
                    imageVector = StylingerIcons.Add,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Add Product",
                    style = StylingerTypography.labelLarge,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            OutlinedButton(
                onClick = onNavigateToQr,
                modifier = Modifier
                    .weight(1f)
                    .height(46.dp),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, StylingerColors.Border),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = StylingerColors.Surface)
            ) {
                Icon(
                    imageVector = StylingerIcons.QrCode,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = StylingerColors.TextPrimary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Generate QR",
                    style = StylingerTypography.labelLarge,
                    color = StylingerColors.TextPrimary
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 4. Catalog Health Visualization
        LiquidGlassCard(modifier = Modifier.fillMaxWidth()) {
            Column {
                Text(
                    text = "CATALOG HEALTH",
                    style = StylingerTypography.labelSmall,
                    color = StylingerColors.TextSecondary
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Progress segments
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .clip(RoundedCornerShape(5.dp))
                ) {
                    val inStockWeight = (inStockCount.toFloat() / totalCount).coerceAtLeast(0.01f)
                    val lowStockWeight = (lowStockCount.toFloat() / totalCount).coerceAtLeast(0.01f)
                    val soldOutWeight = (soldOutCount.toFloat() / totalCount).coerceAtLeast(0.01f)

                    Box(
                        modifier = Modifier
                            .weight(inStockWeight)
                            .fillMaxSize()
                            .background(StylingerColors.Success)
                    )
                    Box(
                        modifier = Modifier
                            .weight(lowStockWeight)
                            .fillMaxSize()
                            .background(StylingerColors.Warning)
                    )
                    Box(
                        modifier = Modifier
                            .weight(soldOutWeight)
                            .fillMaxSize()
                            .background(StylingerColors.Danger)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    LegendItem(color = StylingerColors.Success, label = "In Stock", count = inStockCount)
                    LegendItem(color = StylingerColors.Warning, label = "Low Stock", count = lowStockCount)
                    LegendItem(color = StylingerColors.Danger, label = "Sold Out", count = soldOutCount)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 5. Low Stock Section (3 low stock items)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "LOW STOCK ALERT",
                style = StylingerTypography.labelSmall,
                color = StylingerColors.Danger
            )
            Text(
                text = "View All (${lowStockProducts.size})",
                style = StylingerTypography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                color = StylingerColors.BrandPrimary,
                modifier = Modifier.clickable { onNavigateToInventory() }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (lowStockProducts.isEmpty()) {
            Text(
                text = "All products have healthy inventory levels.",
                style = StylingerTypography.bodyMedium,
                color = StylingerColors.TextSecondary
            )
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                lowStockProducts.forEach { product ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .clickable { onSelectProduct(product) },
                        colors = CardDefaults.cardColors(containerColor = StylingerColors.Surface),
                        border = BorderStroke(0.8.dp, StylingerColors.Border)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Avatar thumbnail
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(
                                        Brush.linearGradient(
                                            listOf(StylingerColors.BrandLight, StylingerColors.Border)
                                        )
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = product.category.take(2).uppercase(),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = StylingerColors.TextPrimary
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = product.name,
                                    style = StylingerTypography.titleSmall,
                                    color = StylingerColors.TextPrimary
                                )
                                Text(
                                    text = "SKU: ${product.sku} · Sizes: ${product.sizes.joinToString()}",
                                    style = StylingerTypography.bodySmall,
                                    color = StylingerColors.TextMuted
                                )
                            }

                            StockStatusBadge(quantity = product.stockQuantity)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 6. AI Insight Card (Clearly labeled DEMO INSIGHT)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = StylingerColors.BrandLight),
            border = BorderStroke(1.dp, StylingerColors.BrandPrimary.copy(alpha = 0.25f))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = StylingerIcons.Star,
                            contentDescription = null,
                            tint = StylingerColors.BrandPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "CATALOG INTELLIGENCE",
                            style = StylingerTypography.labelSmall,
                            color = StylingerColors.BrandPrimary
                        )
                    }

                    Box(
                        modifier = Modifier
                            .background(Color.White, RoundedCornerShape(4.dp))
                            .border(0.5.dp, StylingerColors.BrandPrimary.copy(alpha = 0.4f), RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "DEMO INSIGHT",
                            color = StylingerColors.BrandPrimary,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "“Earth-tone outerwear is performing strongly in your current catalog. Shoppers with Warm undertones frequently pair your Italian Wool Blazer with existing closet pieces.”",
                    style = StylingerTypography.bodyMedium,
                    color = StylingerColors.TextPrimary
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 7. Recent Sales Transactions
        Text(
            text = "RECENT IN-STORE TRANSACTIONS",
            style = StylingerTypography.labelSmall,
            color = StylingerColors.TextSecondary
        )

        Spacer(modifier = Modifier.height(10.dp))

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            SaleItemRow(item = "Italian Wool Blazer", price = "₹8,999", time = "14m ago · Alkapuri", size = "Size M")
            SaleItemRow(item = "Silk Evening Blouse", price = "₹5,499", time = "42m ago · Alkapuri", size = "Size S")
            SaleItemRow(item = "High-Rise Relaxed Denim", price = "₹4,299", time = "2h ago · In-Store QR", size = "Size 28")
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun KpiCard(
    label: String,
    value: String,
    delta: String,
    deltaPositive: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = StylingerColors.Surface),
        border = BorderStroke(0.8.dp, StylingerColors.Border)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = label.uppercase(),
                style = StylingerTypography.labelSmall.copy(fontSize = 10.sp),
                color = StylingerColors.TextSecondary
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = value,
                style = StylingerTypography.headlineMedium.copy(
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold
                ),
                color = StylingerColors.TextPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = delta,
                style = StylingerTypography.bodySmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = if (deltaPositive) StylingerColors.Success else StylingerColors.Warning
                )
            )
        }
    }
}

@Composable
private fun LegendItem(color: Color, label: String, count: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = "$label ($count)",
            style = StylingerTypography.bodySmall,
            color = StylingerColors.TextSecondary
        )
    }
}

@Composable
private fun SaleItemRow(item: String, price: String, time: String, size: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = StylingerColors.Surface),
        border = BorderStroke(0.8.dp, StylingerColors.Border)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = item,
                    style = StylingerTypography.titleSmall,
                    color = StylingerColors.TextPrimary
                )
                Text(
                    text = "$size · $time",
                    style = StylingerTypography.bodySmall,
                    color = StylingerColors.TextMuted
                )
            }
            Text(
                text = price,
                style = StylingerTypography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = StylingerColors.TextPrimary
            )
        }
    }
}
