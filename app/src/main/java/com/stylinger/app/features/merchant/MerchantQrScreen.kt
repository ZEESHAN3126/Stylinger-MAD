package com.stylinger.app.features.merchant

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stylinger.app.core.theme.StylingerColors
import com.stylinger.app.core.theme.StylingerTypography
import com.stylinger.app.core.ui.StylingerIcons
import com.stylinger.app.features.shared.StylingerQrCode

@Composable
fun MerchantQrScreen(
    onOpenModeSwitcher: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) } // 0 = Store QR, 1 = Product QR

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(StylingerColors.Canvas)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "QR Management",
                    style = StylingerTypography.headlineLarge.copy(
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = StylingerColors.TextPrimary
                )
                Text(
                    text = "Aura Atelier · Alkapuri, Vadodara",
                    style = StylingerTypography.bodyMedium,
                    color = StylingerColors.TextSecondary
                )
            }

            Surface(
                shape = RoundedCornerShape(20.dp),
                color = StylingerColors.Surface,
                border = BorderStroke(0.8.dp, StylingerColors.Border)
            ) {
                Text(
                    text = "PILOT ACTIVE",
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                    style = StylingerTypography.labelSmall.copy(
                        color = StylingerColors.Success,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Tab Row: Store Entrance QR vs Garment Tag QR
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = StylingerColors.Surface,
            contentColor = StylingerColors.BrandPrimary,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = StylingerColors.BrandPrimary
                )
            },
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .border(0.8.dp, StylingerColors.Border, RoundedCornerShape(12.dp))
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = {
                    Text(
                        text = "Store Entrance QR",
                        style = StylingerTypography.labelLarge.copy(
                            fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal
                        )
                    )
                }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = {
                    Text(
                        text = "Garment Tag QR",
                        style = StylingerTypography.labelLarge.copy(
                            fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal
                        )
                    )
                }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // QR Card Display
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = StylingerColors.Surface),
            border = BorderStroke(1.dp, StylingerColors.Border),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (selectedTab == 0) "AURA ATELIER" else "GARMENT TAG #PROD-001",
                    style = StylingerTypography.labelSmall.copy(letterSpacing = 1.5.sp),
                    color = StylingerColors.BrandPrimary
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = if (selectedTab == 0) "Scan for AI Style Advice" else "Italian Wool Blazer · Size M",
                    style = StylingerTypography.titleLarge.copy(
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = StylingerColors.TextPrimary,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = if (selectedTab == 0) "Alkapuri, Vadodara" else "SKU: AURA-BLZ-001 · ₹8,999",
                    style = StylingerTypography.bodyMedium,
                    color = StylingerColors.TextSecondary
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Rendered QR Code
                val payload = if (selectedTab == 0) {
                    "stylinger://store/store_aura_vadodara"
                } else {
                    "stylinger://item/store_aura_vadodara/PROD-001"
                }

                StylingerQrCode(
                    payload = payload,
                    modifier = Modifier.size(210.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Payload URI: $payload",
                    style = StylingerTypography.bodySmall.copy(fontSize = 11.sp),
                    color = StylingerColors.TextMuted,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Action Buttons: Print Tag & Save
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { /* Demo print */ },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = StylingerColors.BrandPrimary)
            ) {
                Icon(
                    imageVector = StylingerIcons.Share,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (selectedTab == 0) "Print Store Stand" else "Print Hangtag",
                    style = StylingerTypography.labelLarge,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            OutlinedButton(
                onClick = onOpenModeSwitcher,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, StylingerColors.Border),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = StylingerColors.Surface)
            ) {
                Icon(
                    imageVector = StylingerIcons.Swap,
                    contentDescription = null,
                    tint = StylingerColors.TextPrimary,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Test as Shopper",
                    style = StylingerTypography.labelLarge,
                    color = StylingerColors.TextPrimary
                )
            }
        }
    }
}
