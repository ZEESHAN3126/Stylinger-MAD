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
import com.stylinger.app.core.ui.StylingerIcons

data class BoutiqueInfo(
    val name: String,
    val hub: String,
    val address: String,
    val rating: String,
    val distance: String,
    val tags: List<String>,
    val isPrimaryPilot: Boolean = false
)

@Composable
fun CustomerBoutiquesScreen(
    onSelectBoutique: (String) -> Unit
) {
    val boutiques = listOf(
        BoutiqueInfo(
            name = "Aura Atelier",
            hub = "Alkapuri, Vadodara",
            address = "Shop 14, Prestige Complex, RC Dutt Rd",
            rating = "4.9 ★",
            distance = "0.8 km away",
            tags = listOf("Contemporary", "Minimalist Tailoring", "Luxury Outerwear"),
            isPrimaryPilot = true
        ),
        BoutiqueInfo(
            name = "Kala Niketan",
            hub = "Mandvi, Vadodara",
            address = "Near Mandvi Gate, Heritage District",
            rating = "4.7 ★",
            distance = "2.4 km away",
            tags = listOf("Artisanal Handlooms", "Bandhani", "Ethnic Contemporary")
        ),
        BoutiqueInfo(
            name = "Studio Ritu",
            hub = "Sayajiganj, Vadodara",
            address = "Beside Kala Ghoda Circle",
            rating = "4.8 ★",
            distance = "1.5 km away",
            tags = listOf("Bespoke Evening", "Indo-Western", "Silks")
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(StylingerColors.Canvas)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text = "Discover Boutiques",
            style = StylingerTypography.headlineLarge.copy(
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.SemiBold
            ),
            color = StylingerColors.TextPrimary
        )
        Text(
            text = "Independent fashion retail hubs across Vadodara",
            style = StylingerTypography.bodyMedium,
            color = StylingerColors.TextSecondary
        )

        Spacer(modifier = Modifier.height(20.dp))

        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            boutiques.forEach { boutique ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = StylingerColors.Surface),
                    border = BorderStroke(
                        width = if (boutique.isPrimaryPilot) 1.5.dp else 0.8.dp,
                        color = if (boutique.isPrimaryPilot) StylingerColors.BrandPrimary else StylingerColors.Border
                    )
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = boutique.name,
                                    style = StylingerTypography.titleLarge.copy(fontFamily = FontFamily.Serif),
                                    color = StylingerColors.TextPrimary
                                )
                                if (boutique.isPrimaryPilot) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Box(
                                        modifier = Modifier
                                            .background(StylingerColors.BrandLight, RoundedCornerShape(6.dp))
                                            .padding(horizontal = 7.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = "PILOT PARTNER",
                                            color = StylingerColors.BrandPrimary,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .background(StylingerColors.Canvas, RoundedCornerShape(6.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = boutique.rating,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = StylingerColors.TextPrimary
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "${boutique.hub} · ${boutique.distance}",
                            style = StylingerTypography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                            color = StylingerColors.TextSecondary
                        )

                        Text(
                            text = boutique.address,
                            style = StylingerTypography.bodySmall,
                            color = StylingerColors.TextMuted
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Tags
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            boutique.tags.forEach { tag ->
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(StylingerColors.Canvas)
                                        .border(0.6.dp, StylingerColors.Border, RoundedCornerShape(6.dp))
                                        .padding(horizontal = 8.dp, vertical = 3.dp)
                                ) {
                                    Text(
                                        text = tag,
                                        fontSize = 11.sp,
                                        color = StylingerColors.TextPrimary
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Button(
                            onClick = { onSelectBoutique(boutique.name) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(42.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (boutique.isPrimaryPilot) StylingerColors.BrandPrimary else StylingerColors.TextPrimary
                            )
                        ) {
                            Icon(
                                imageVector = StylingerIcons.Scanner,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Scan Tags at ${boutique.name}",
                                style = StylingerTypography.labelMedium,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}
