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
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.stylinger.app.domain.model.WardrobeItem
import com.stylinger.app.domain.repository.DailyOutfit
import com.stylinger.app.domain.repository.IInventoryRepository
import com.stylinger.app.domain.repository.IStylingEngine
import com.stylinger.app.domain.repository.IWardrobeRepository
import kotlinx.coroutines.launch

@Composable
fun CustomerHomeScreen(
    wardrobeRepo: IWardrobeRepository,
    inventoryRepo: IInventoryRepository,
    stylingEngine: IStylingEngine,
    onNavigateToScanner: () -> Unit,
    onNavigateToWardrobe: () -> Unit,
    onOpenModeSwitcher: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var dailyOutfit by remember { mutableStateOf<DailyOutfit?>(null) }
    var boutiqueFeatured by remember { mutableStateOf<List<Product>>(emptyList()) }
    var outfitVariationIndex by remember { mutableIntStateOf(0) }
    var wearLoggedMessage by remember { mutableStateOf<String?>(null) }

    val styleDna = MockPersonas.CustomerAnanya.styleDna!!

    LaunchedEffect(outfitVariationIndex) {
        val items = wardrobeRepo.getWardrobeItems()
        if (items.isNotEmpty()) {
            val occasion = if (outfitVariationIndex % 2 == 0) "Morning Edit · College" else "Evening Casual · Alkapuri"
            dailyOutfit = stylingEngine.generateDailyOutfit(items, occasion, styleDna)
        }
        boutiqueFeatured = inventoryRepo.getProducts().take(3)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(StylingerColors.Canvas)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        // 1. Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Good morning, Ananya.",
                    style = StylingerTypography.headlineLarge.copy(
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = StylingerColors.TextPrimary
                )
                Text(
                    text = "Your daily style edit · Vadodara",
                    style = StylingerTypography.bodyMedium,
                    color = StylingerColors.TextSecondary
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Mode Switcher icon
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

        Spacer(modifier = Modifier.height(20.dp))

        // 2. Hero Card: TODAY'S LOOK
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = StylingerColors.Surface),
            border = BorderStroke(1.dp, StylingerColors.Border),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f).padding(end = 12.dp)) {
                        Text(
                            text = "TODAY'S LOOK",
                            style = StylingerTypography.labelSmall.copy(letterSpacing = 1.5.sp),
                            color = StylingerColors.BrandPrimary
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = dailyOutfit?.title ?: "Morning Edit · College",
                            style = StylingerTypography.titleLarge.copy(fontFamily = FontFamily.Serif),
                            color = StylingerColors.TextPrimary
                        )
                    }

                    Box(
                        modifier = Modifier
                            .background(StylingerColors.SuccessLight, RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "85% Style Match",
                            color = StylingerColors.Success,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Pieces in Outfit
                dailyOutfit?.pieces?.let { pieces ->
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        pieces.forEach { piece ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(StylingerColors.Canvas)
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(
                                            Brush.linearGradient(
                                                listOf(Color(0xFFE8E2D5), Color(0xFFD8D0C2))
                                            )
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = piece.category.take(1),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = StylingerColors.TextPrimary
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = piece.name,
                                        style = StylingerTypography.titleSmall,
                                        color = StylingerColors.TextPrimary
                                    )
                                    Text(
                                        text = "${piece.color} · ${piece.category}",
                                        style = StylingerTypography.bodySmall,
                                        color = StylingerColors.TextMuted
                                    )
                                }
                                Text(
                                    text = "Worn ${piece.wearCount}x",
                                    style = StylingerTypography.bodySmall.copy(fontSize = 11.sp),
                                    color = StylingerColors.TextSecondary
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Hero Actions: Wear it & Swap a piece
                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {
                            dailyOutfit?.pieces?.forEach { piece ->
                                scope.launch { wardrobeRepo.incrementWearCount(piece.id) }
                            }
                            wearLoggedMessage = "Outfit logged! Wear counts updated."
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = StylingerColors.TextPrimary)
                    ) {
                        Icon(
                            imageVector = StylingerIcons.Check,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Wear it", style = StylingerTypography.labelLarge, color = Color.White)
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    OutlinedButton(
                        onClick = {
                            outfitVariationIndex++
                            wearLoggedMessage = null
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, StylingerColors.Border),
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = StylingerColors.Surface)
                    ) {
                        Icon(
                            imageVector = StylingerIcons.Refresh,
                            contentDescription = null,
                            tint = StylingerColors.TextPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Swap a piece", style = StylingerTypography.labelLarge, color = StylingerColors.TextPrimary)
                    }
                }

                if (wearLoggedMessage != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = wearLoggedMessage!!,
                        style = StylingerTypography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                        color = StylingerColors.Success
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 3. Quick Action Banner: In-Store Stylist Callout
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .clickable { onNavigateToScanner() },
            colors = CardDefaults.cardColors(containerColor = StylingerColors.BrandPrimary)
        ) {
            Row(
                modifier = Modifier.padding(18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = StylingerIcons.Scanner,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Visiting Aura Atelier?",
                        style = StylingerTypography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                    Text(
                        text = "Scan garment tags in-store for real-time Style DNA pairing.",
                        style = StylingerTypography.bodySmall,
                        color = Color.White.copy(alpha = 0.85f)
                    )
                }

                Button(
                    onClick = onNavigateToScanner,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "Scan",
                        color = StylingerColors.BrandPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 4. Curated for Ananya's Style DNA
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "MATCHED TO YOUR STYLE DNA",
                    style = StylingerTypography.labelSmall,
                    color = StylingerColors.BrandPrimary
                )
                Text(
                    text = "Rectangle · Warm Undertone",
                    style = StylingerTypography.bodySmall,
                    color = StylingerColors.TextSecondary
                )
            }

            Text(
                text = "My Closet",
                style = StylingerTypography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                color = StylingerColors.BrandPrimary,
                modifier = Modifier.clickable { onNavigateToWardrobe() }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            boutiqueFeatured.forEach { product ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = StylingerColors.Surface),
                    border = BorderStroke(0.8.dp, StylingerColors.Border)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(
                                    Brush.linearGradient(
                                        listOf(Color(0xFFEDE8DF), Color(0xFFDDD5C8))
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = product.category.take(2).uppercase(),
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
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
                                text = "Aura Atelier · ₹${product.price.toInt()}",
                                style = StylingerTypography.bodySmall,
                                color = StylingerColors.TextSecondary
                            )
                        }

                        Box(
                            modifier = Modifier
                                .background(StylingerColors.BrandLight, RoundedCornerShape(6.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "87% Match",
                                color = StylingerColors.BrandPrimary,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
