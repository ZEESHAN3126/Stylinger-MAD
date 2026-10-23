package com.stylinger.app.features.welcome

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stylinger.app.core.theme.StylingerColors
import com.stylinger.app.core.theme.StylingerTypography
import com.stylinger.app.core.ui.StylingerIcons
import com.stylinger.app.domain.model.AppRole

@Composable
fun WelcomeScreen(
    onSelectRole: (AppRole) -> Unit
) {
    var isVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        isVisible = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(StylingerColors.Canvas)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top editorial brand section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 28.dp)
            ) {
                // Editorial tag
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = StylingerColors.BrandLight,
                    border = BorderStroke(0.8.dp, StylingerColors.BrandPrimary.copy(alpha = 0.3f))
                ) {
                    Text(
                        text = "VADODARA FASHION PILOT",
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                        style = StylingerTypography.labelSmall.copy(
                            color = StylingerColors.BrandPrimary,
                            letterSpacing = 1.8.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Hero Logo / Title
                Text(
                    text = "STYLINGER",
                    style = StylingerTypography.displayLarge.copy(
                        fontFamily = FontFamily.Serif,
                        fontSize = 44.sp,
                        letterSpacing = 2.sp
                    ),
                    color = StylingerColors.TextPrimary
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "AI styling for the modern wardrobe.",
                    style = StylingerTypography.bodyLarge.copy(fontSize = 17.sp),
                    color = StylingerColors.TextSecondary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Editorial accent card (Fashion aesthetic banner)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(170.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = StylingerColors.Surface),
                    border = BorderStroke(0.8.dp, StylingerColors.Border),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.linearGradient(
                                    listOf(
                                        Color(0xFF2C3E50),
                                        Color(0xFF17191C),
                                        Color(0xFF2457D6).copy(alpha = 0.85f)
                                    )
                                )
                            )
                            .padding(20.dp),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Column {
                            Box(
                                modifier = Modifier
                                    .background(Color.White.copy(alpha = 0.2f), RoundedCornerShape(6.dp))
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = "AUTUMN / WINTER 2026",
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.2.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Aura Atelier Curated Collection",
                                color = Color.White,
                                style = StylingerTypography.titleLarge.copy(
                                    fontFamily = FontFamily.Serif,
                                    fontSize = 20.sp
                                )
                            )
                            Text(
                                text = "Bridging boutique racks with digital wardrobe intelligence",
                                color = Color.White.copy(alpha = 0.8f),
                                style = StylingerTypography.bodySmall
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Persona Entry Cards
            AnimatedVisibility(
                visible = isVisible,
                enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 })
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "CHOOSE YOUR WORKSPACE",
                        style = StylingerTypography.labelSmall.copy(
                            letterSpacing = 1.5.sp,
                            color = StylingerColors.TextSecondary
                        ),
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    // 1. Merchant Persona Entry Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(18.dp))
                            .clickable { onSelectRole(AppRole.MERCHANT) },
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = StylingerColors.Surface),
                        border = BorderStroke(1.dp, StylingerColors.Border),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(52.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(StylingerColors.BrandPrimary),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = StylingerIcons.Store,
                                    contentDescription = "Boutique Owner",
                                    tint = Color.White,
                                    modifier = Modifier.size(26.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "Boutique Owner",
                                        style = StylingerTypography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                        color = StylingerColors.TextPrimary
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Box(
                                        modifier = Modifier
                                            .background(StylingerColors.BrandLight, RoundedCornerShape(4.dp))
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = "MERCHANT",
                                            color = StylingerColors.BrandPrimary,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Aura Atelier · Vadodara",
                                    style = StylingerTypography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                                    color = StylingerColors.TextSecondary
                                )
                                Text(
                                    text = "Live stock management, QR tags & sales dashboard",
                                    style = StylingerTypography.bodySmall,
                                    color = StylingerColors.TextMuted
                                )
                            }

                            Icon(
                                imageVector = StylingerIcons.ArrowBack,
                                contentDescription = "Enter",
                                tint = StylingerColors.TextSecondary,
                                modifier = Modifier
                                    .size(20.dp)
                                    .padding(start = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // 2. Customer Persona Entry Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(18.dp))
                            .clickable { onSelectRole(AppRole.CUSTOMER) },
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = StylingerColors.Surface),
                        border = BorderStroke(1.dp, StylingerColors.Border),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(52.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(StylingerColors.TextPrimary),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = StylingerIcons.Person,
                                    contentDescription = "Fashion Shopper",
                                    tint = Color.White,
                                    modifier = Modifier.size(26.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "Fashion Shopper",
                                        style = StylingerTypography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                        color = StylingerColors.TextPrimary
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Box(
                                        modifier = Modifier
                                            .background(Color(0xFFEFECE4), RoundedCornerShape(4.dp))
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = "CUSTOMER",
                                            color = StylingerColors.TextPrimary,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Ananya · Vadodara",
                                    style = StylingerTypography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                                    color = StylingerColors.TextSecondary
                                )
                                Text(
                                    text = "Daily look curation, closet digitizer & store QR scanner",
                                    style = StylingerTypography.bodySmall,
                                    color = StylingerColors.TextMuted
                                )
                            }

                            Icon(
                                imageVector = StylingerIcons.ArrowBack,
                                contentDescription = "Enter",
                                tint = StylingerColors.TextSecondary,
                                modifier = Modifier
                                    .size(20.dp)
                                    .padding(start = 4.dp)
                            )
                        }
                    }
                }
            }

            // Footer note
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 24.dp)
            ) {
                Text(
                    text = "STANDALONE NATIVE ANDROID · ZERO CLOUD RUNTIME",
                    style = StylingerTypography.labelSmall.copy(
                        fontSize = 10.sp,
                        color = StylingerColors.TextMuted,
                        letterSpacing = 1.sp
                    )
                )
            }
        }
    }
}
