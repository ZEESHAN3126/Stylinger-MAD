package com.stylinger.app.features.customer

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.stylinger.app.core.theme.MonkSkinToneScale
import com.stylinger.app.core.theme.StylingerColors
import com.stylinger.app.core.theme.StylingerTypography
import com.stylinger.app.core.ui.LiquidGlassCard
import com.stylinger.app.core.ui.StylingerIcons
import com.stylinger.app.data.mock.MockPersonas
import com.stylinger.app.domain.model.Order
import com.stylinger.app.domain.repository.IOrderRepository

@Composable
fun CustomerProfileScreen(
    orderRepo: IOrderRepository,
    onOpenModeSwitcher: () -> Unit,
    onSignOut: () -> Unit
) {
    val customer = MockPersonas.CustomerAnanya
    val dna = customer.styleDna!!
    var orders by remember { mutableStateOf<List<Order>>(emptyList()) }

    LaunchedEffect(Unit) {
        orders = orderRepo.getOrdersByCustomer(customer.id)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(StylingerColors.Canvas)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        // Header
        Text(
            text = "Profile & Style DNA",
            style = StylingerTypography.headlineLarge.copy(
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.SemiBold
            ),
            color = StylingerColors.TextPrimary
        )
        Text(
            text = "Ananya Sharma · Vadodara, Gujarat",
            style = StylingerTypography.bodyMedium,
            color = StylingerColors.TextSecondary
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Profile Avatar Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = StylingerColors.Surface),
            border = BorderStroke(1.dp, StylingerColors.Border)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                listOf(StylingerColors.BrandPrimary, Color(0xFF17191C))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "AS",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = customer.name,
                        style = StylingerTypography.titleLarge.copy(fontFamily = FontFamily.Serif),
                        color = StylingerColors.TextPrimary
                    )
                    Text(
                        text = "ananya.sharma@stylinger.app",
                        style = StylingerTypography.bodySmall,
                        color = StylingerColors.TextMuted
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .background(StylingerColors.BrandLight, RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "VADODARA FASHION PILOT",
                            color = StylingerColors.BrandPrimary,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Style DNA Card
        LiquidGlassCard(modifier = Modifier.fillMaxWidth()) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "STYLE DNA SPECIFICATIONS",
                        style = StylingerTypography.labelSmall,
                        color = StylingerColors.BrandPrimary
                    )
                    Text(
                        text = "Verified",
                        style = StylingerTypography.labelSmall.copy(color = StylingerColors.Success, fontWeight = FontWeight.Bold)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Dna items grid
                Row(modifier = Modifier.fillMaxWidth()) {
                    DnaSpecItem(label = "BODY SHAPE", value = dna.bodyShape, modifier = Modifier.weight(1f))
                    DnaSpecItem(label = "FACE SHAPE", value = dna.faceShape, modifier = Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "SKIN TONE", style = StylingerTypography.labelSmall, color = StylingerColors.TextSecondary)
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .clip(CircleShape)
                                    .background(MonkSkinToneScale.MST_05)
                                    .border(0.5.dp, Color.Black.copy(alpha = 0.2f), CircleShape)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = "MST ${dna.monkSkinToneScale}", style = StylingerTypography.bodyMedium.copy(fontWeight = FontWeight.SemiBold))
                        }
                    }

                    DnaSpecItem(label = "UNDERTONE", value = dna.undertone, modifier = Modifier.weight(1f))
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Purchase History
        Text(
            text = "IN-STORE PURCHASE HISTORY",
            style = StylingerTypography.labelSmall,
            color = StylingerColors.TextSecondary
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (orders.isEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = StylingerColors.Surface),
                border = BorderStroke(0.8.dp, StylingerColors.Border)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No in-store purchases yet. Simulate purchase from In-Store Stylist.",
                        style = StylingerTypography.bodySmall,
                        color = StylingerColors.TextMuted
                    )
                }
            }
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                orders.forEach { order ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = StylingerColors.Surface),
                        border = BorderStroke(0.8.dp, StylingerColors.Border)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(text = order.productName, style = StylingerTypography.titleSmall, color = StylingerColors.TextPrimary)
                                Text(text = "Size: ${order.size} · Aura Atelier, Alkapuri", style = StylingerTypography.bodySmall, color = StylingerColors.TextMuted)
                            }
                            Text(text = "₹${order.amount.toInt()}", style = StylingerTypography.titleSmall.copy(fontWeight = FontWeight.Bold), color = StylingerColors.TextPrimary)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Action Buttons: Switch Mode & Sign Out
        Button(
            onClick = onOpenModeSwitcher,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = StylingerColors.BrandPrimary)
        ) {
            Icon(
                imageVector = StylingerIcons.Swap,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Switch to Boutique Owner", style = StylingerTypography.labelLarge, color = Color.White)
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(
            onClick = onSignOut,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, StylingerColors.Border),
            colors = ButtonDefaults.outlinedButtonColors(containerColor = StylingerColors.Surface)
        ) {
            Text(text = "Sign Out to Welcome Screen", style = StylingerTypography.labelLarge, color = StylingerColors.TextPrimary)
        }
    }
}

@Composable
private fun DnaSpecItem(label: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(text = label, style = StylingerTypography.labelSmall, color = StylingerColors.TextSecondary)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = value, style = StylingerTypography.bodyMedium.copy(fontWeight = FontWeight.SemiBold), color = StylingerColors.TextPrimary)
    }
}
