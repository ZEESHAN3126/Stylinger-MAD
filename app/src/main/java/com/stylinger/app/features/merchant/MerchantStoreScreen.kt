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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.stylinger.app.core.ui.StylingerIcons

@Composable
fun MerchantStoreScreen(
    onOpenModeSwitcher: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(StylingerColors.Canvas)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text = "Store Profile",
            style = StylingerTypography.headlineLarge.copy(
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.SemiBold
            ),
            color = StylingerColors.TextPrimary
        )
        Text(
            text = "Boutique credentials & pilot settings",
            style = StylingerTypography.bodyMedium,
            color = StylingerColors.TextSecondary
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Store Banner Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = StylingerColors.Surface),
            border = BorderStroke(1.dp, StylingerColors.Border)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .background(
                        Brush.horizontalGradient(
                            listOf(Color(0xFF2C3E50), Color(0xFF17191C))
                        )
                    )
                    .padding(20.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Column {
                    Text(
                        text = "AURA ATELIER",
                        fontFamily = FontFamily.Serif,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Independent Luxury Contemporary Wear",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }

            Column(modifier = Modifier.padding(20.dp)) {
                DetailRow(label = "Owner", value = "Zeeshan Khan")
                DetailRow(label = "Retail Hub", value = "Alkapuri, Vadodara, Gujarat")
                DetailRow(label = "Address", value = "Shop 14, Prestige Complex, RC Dutt Rd")
                DetailRow(label = "Pilot Status", value = "Active On-Premise Partner")
                DetailRow(label = "Accepted Payments", value = "UPI, Cards, Offline Cash")
                DetailRow(label = "Hours", value = "10:30 AM – 9:00 PM (Mon–Sun)")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Mode Switcher trigger
        Button(
            onClick = onOpenModeSwitcher,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = StylingerColors.TextPrimary)
        ) {
            Icon(
                imageVector = StylingerIcons.Swap,
                contentDescription = null,
                tint = StylingerColors.TextInverse,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Switch to Customer Mode",
                style = StylingerTypography.labelLarge,
                color = StylingerColors.TextInverse
            )
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = StylingerTypography.bodyMedium, color = StylingerColors.TextSecondary)
        Text(text = value, style = StylingerTypography.bodyMedium.copy(fontWeight = FontWeight.Medium), color = StylingerColors.TextPrimary)
    }
}
