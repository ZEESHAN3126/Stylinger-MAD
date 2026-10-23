package com.stylinger.app.features.merchant

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stylinger.app.core.theme.StylingerColors
import com.stylinger.app.core.theme.StylingerTypography
import com.stylinger.app.core.ui.LiquidGlassCard
import com.stylinger.app.core.ui.StylingerIcons

@Composable
fun MerchantAnalyticsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(StylingerColors.Canvas)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        // Header
        Text(
            text = "Analytics & Insights",
            style = StylingerTypography.headlineLarge.copy(
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.SemiBold
            ),
            color = StylingerColors.TextPrimary
        )
        Text(
            text = "In-store scan traffic & boutique conversion",
            style = StylingerTypography.bodyMedium,
            color = StylingerColors.TextSecondary
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Traffic Stats
        Row(modifier = Modifier.fillMaxWidth()) {
            StatCard(
                title = "Total Scans (Week)",
                value = "142",
                subtitle = "+24% vs last week",
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(12.dp))
            StatCard(
                title = "Scan to Closet Rate",
                value = "68%",
                subtitle = "High intent shoppers",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Top Scanned Garments
        LiquidGlassCard(modifier = Modifier.fillMaxWidth()) {
            Column {
                Text(
                    text = "TOP SCANNED PIECES",
                    style = StylingerTypography.labelSmall,
                    color = StylingerColors.BrandPrimary
                )
                Spacer(modifier = Modifier.height(12.dp))

                TopScannedRow(rank = "1", name = "Italian Wool Blazer", scans = "48 scans", conversion = "8 sold")
                Spacer(modifier = Modifier.height(8.dp))
                TopScannedRow(rank = "2", name = "Silk Evening Blouse", scans = "34 scans", conversion = "6 sold")
                Spacer(modifier = Modifier.height(8.dp))
                TopScannedRow(rank = "3", name = "Linen Blend Trousers", scans = "29 scans", conversion = "5 sold")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Hourly Scan Distribution
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = StylingerColors.Surface),
            border = BorderStroke(0.8.dp, StylingerColors.Border)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "PEAK IN-STORE FOOTFALL (ALKAPURI)",
                    style = StylingerTypography.labelSmall,
                    color = StylingerColors.TextSecondary
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Peak traffic occurs between 5:30 PM and 8:30 PM. 72% of customer scans happen during evening boutique hours.",
                    style = StylingerTypography.bodyMedium,
                    color = StylingerColors.TextPrimary
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Honest Mode Feedback
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = StylingerColors.BrandLight),
            border = BorderStroke(1.dp, StylingerColors.BrandPrimary.copy(alpha = 0.2f))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = StylingerIcons.Info,
                        contentDescription = null,
                        tint = StylingerColors.BrandPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "HONEST STYLING TRANSPARENCY",
                        style = StylingerTypography.labelSmall,
                        color = StylingerColors.BrandPrimary
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "When an in-store shopper’s Style DNA matches fewer than 3 items, the app recommends curated partner boutiques nearby in Vadodara (Kala Niketan, Studio Ritu), building high trust.",
                    style = StylingerTypography.bodySmall,
                    color = StylingerColors.TextPrimary
                )
            }
        }
    }
}

@Composable
private fun StatCard(title: String, value: String, subtitle: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = StylingerColors.Surface),
        border = BorderStroke(0.8.dp, StylingerColors.Border)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(text = title.uppercase(), style = StylingerTypography.labelSmall.copy(fontSize = 10.sp), color = StylingerColors.TextSecondary)
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = value, style = StylingerTypography.headlineMedium.copy(fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold), color = StylingerColors.TextPrimary)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = subtitle, style = StylingerTypography.bodySmall.copy(color = StylingerColors.Success, fontWeight = FontWeight.SemiBold))
        }
    }
}

@Composable
private fun TopScannedRow(rank: String, name: String, scans: String, conversion: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(StylingerColors.Canvas),
                contentAlignment = Alignment.Center
            ) {
                Text(text = rank, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = StylingerColors.TextPrimary)
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = name, style = StylingerTypography.titleSmall, color = StylingerColors.TextPrimary)
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(text = scans, style = StylingerTypography.bodySmall.copy(fontWeight = FontWeight.Bold), color = StylingerColors.TextPrimary)
            Text(text = conversion, style = StylingerTypography.bodySmall.copy(fontSize = 10.sp), color = StylingerColors.TextMuted)
        }
    }
}
