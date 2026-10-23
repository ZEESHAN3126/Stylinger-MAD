package com.stylinger.app.features.shared

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stylinger.app.core.theme.StylingerColors
import com.stylinger.app.core.theme.StylingerTypography
import com.stylinger.app.core.ui.StylingerIcons
import com.stylinger.app.domain.model.AppRole

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModeSwitchSheet(
    currentRole: AppRole,
    onRoleSelected: (AppRole) -> Unit,
    onDismiss: () -> Unit,
    sheetState: SheetState
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = StylingerColors.Surface,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 36.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "ROLE SWITCHER",
                        style = StylingerTypography.labelSmall,
                        color = StylingerColors.BrandPrimary
                    )
                    Text(
                        text = "Dual-Mode Platform",
                        style = StylingerTypography.titleLarge,
                        color = StylingerColors.TextPrimary
                    )
                }
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(StylingerColors.Canvas)
                        .clickable { onDismiss() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = StylingerIcons.Close,
                        contentDescription = "Close",
                        tint = StylingerColors.TextSecondary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Stylinger serves both independent boutique merchants and discerning shoppers. Select your workspace below:",
                style = StylingerTypography.bodyMedium,
                color = StylingerColors.TextSecondary
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Merchant Card Option
            val isMerchant = currentRole == AppRole.MERCHANT
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .clickable {
                        onRoleSelected(AppRole.MERCHANT)
                        onDismiss()
                    },
                colors = CardDefaults.cardColors(
                    containerColor = if (isMerchant) StylingerColors.BrandLight else StylingerColors.Canvas
                ),
                border = BorderStroke(
                    width = if (isMerchant) 1.5.dp else 1.dp,
                    color = if (isMerchant) StylingerColors.BrandPrimary else StylingerColors.Border
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isMerchant) StylingerColors.BrandPrimary else StylingerColors.Surface),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = StylingerIcons.Store,
                            contentDescription = "Boutique Owner",
                            tint = if (isMerchant) Color.White else StylingerColors.TextPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Boutique Owner",
                                style = StylingerTypography.titleMedium,
                                color = StylingerColors.TextPrimary
                            )
                            if (isMerchant) {
                                Spacer(modifier = Modifier.width(8.dp))
                                Box(
                                    modifier = Modifier
                                        .background(StylingerColors.BrandPrimary, RoundedCornerShape(6.dp))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "ACTIVE",
                                        color = Color.White,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                        Text(
                            text = "Aura Atelier · Alkapuri, Vadodara",
                            style = StylingerTypography.bodySmall,
                            color = StylingerColors.TextSecondary
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Manage inventory, QR code generation, real-time sales & low stock alerts.",
                            style = StylingerTypography.bodySmall.copy(fontSize = 11.sp),
                            color = StylingerColors.TextMuted
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Customer Card Option
            val isCustomer = currentRole == AppRole.CUSTOMER
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .clickable {
                        onRoleSelected(AppRole.CUSTOMER)
                        onDismiss()
                    },
                colors = CardDefaults.cardColors(
                    containerColor = if (isCustomer) StylingerColors.BrandLight else StylingerColors.Canvas
                ),
                border = BorderStroke(
                    width = if (isCustomer) 1.5.dp else 1.dp,
                    color = if (isCustomer) StylingerColors.BrandPrimary else StylingerColors.Border
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isCustomer) StylingerColors.BrandPrimary else StylingerColors.Surface),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = StylingerIcons.Person,
                            contentDescription = "Shopper Persona",
                            tint = if (isCustomer) Color.White else StylingerColors.TextPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Fashion Shopper",
                                style = StylingerTypography.titleMedium,
                                color = StylingerColors.TextPrimary
                            )
                            if (isCustomer) {
                                Spacer(modifier = Modifier.width(8.dp))
                                Box(
                                    modifier = Modifier
                                        .background(StylingerColors.BrandPrimary, RoundedCornerShape(6.dp))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "ACTIVE",
                                        color = Color.White,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                        Text(
                            text = "Ananya Sharma · Vadodara",
                            style = StylingerTypography.bodySmall,
                            color = StylingerColors.TextSecondary
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Daily outfit curator, digital closet, in-store QR styling & Style DNA matcher.",
                            style = StylingerTypography.bodySmall.copy(fontSize = 11.sp),
                            color = StylingerColors.TextMuted
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val targetRole = if (isMerchant) AppRole.CUSTOMER else AppRole.MERCHANT
                    onRoleSelected(targetRole)
                    onDismiss()
                },
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
                    text = if (isMerchant) "Switch to Fashion Shopper" else "Switch to Boutique Owner",
                    style = StylingerTypography.labelLarge,
                    color = StylingerColors.TextInverse
                )
            }
        }
    }
}
