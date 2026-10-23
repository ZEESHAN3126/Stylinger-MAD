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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.stylinger.app.core.ui.StockStatusBadge
import com.stylinger.app.core.ui.StylingerIcons
import com.stylinger.app.domain.model.Product
import com.stylinger.app.domain.repository.IInventoryRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailSheet(
    product: Product,
    inventoryRepo: IInventoryRepository,
    onDismiss: () -> Unit,
    onEdit: (Product) -> Unit,
    onProductUpdated: () -> Unit,
    sheetState: SheetState
) {
    val scope = rememberCoroutineScope()
    var currentStock by remember { mutableIntStateOf(product.stockQuantity) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = StylingerColors.Surface,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
                .padding(bottom = 36.dp)
        ) {
            // Header bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = product.category.uppercase(),
                        style = StylingerTypography.labelSmall,
                        color = StylingerColors.BrandPrimary
                    )
                    Text(
                        text = product.name,
                        style = StylingerTypography.titleLarge.copy(fontFamily = FontFamily.Serif),
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
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Large Visual Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0xFFEFE8DC), Color(0xFFDFD6C7))
                        )
                    )
                    .padding(16.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    text = product.name.take(2).uppercase(),
                    modifier = Modifier.align(Alignment.Center),
                    fontFamily = FontFamily.Serif,
                    fontSize = 64.sp,
                    fontWeight = FontWeight.Bold,
                    color = StylingerColors.TextPrimary.copy(alpha = 0.25f)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Box(
                        modifier = Modifier
                            .background(StylingerColors.TextPrimary, RoundedCornerShape(8.dp))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "SKU: ${product.sku}",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    StockStatusBadge(quantity = currentStock)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Price & Stock Stepper Row
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = StylingerColors.Canvas),
                border = BorderStroke(0.8.dp, StylingerColors.Border)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "PRICE",
                            style = StylingerTypography.labelSmall,
                            color = StylingerColors.TextSecondary
                        )
                        Text(
                            text = "₹${product.price.toInt()}",
                            style = StylingerTypography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                            color = StylingerColors.TextPrimary
                        )
                    }

                    // Stock increment / decrement controls
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "ON-HAND STOCK",
                            style = StylingerTypography.labelSmall,
                            color = StylingerColors.TextSecondary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = {
                                    if (currentStock > 0) {
                                        currentStock--
                                        scope.launch {
                                            inventoryRepo.updateStock(product.id, currentStock)
                                            onProductUpdated()
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(StylingerColors.Surface)
                                    .border(0.8.dp, StylingerColors.Border, CircleShape)
                            ) {
                                Text(text = "-", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            }

                            Text(
                                text = "$currentStock",
                                modifier = Modifier.padding(horizontal = 14.dp),
                                style = StylingerTypography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = StylingerColors.TextPrimary
                            )

                            IconButton(
                                onClick = {
                                    currentStock++
                                    scope.launch {
                                        inventoryRepo.updateStock(product.id, currentStock)
                                        onProductUpdated()
                                    }
                                },
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(StylingerColors.BrandPrimary)
                            ) {
                                Text(
                                    text = "+",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Attributes: Color & Sizes
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "COLOR",
                        style = StylingerTypography.labelSmall,
                        color = StylingerColors.TextSecondary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = product.color,
                        style = StylingerTypography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                        color = StylingerColors.TextPrimary
                    )
                }

                Column(modifier = Modifier.weight(1.5f)) {
                    Text(
                        text = "AVAILABLE SIZES",
                        style = StylingerTypography.labelSmall,
                        color = StylingerColors.TextSecondary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        product.sizes.forEach { size ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(StylingerColors.Canvas)
                                    .border(0.8.dp, StylingerColors.Border, RoundedCornerShape(6.dp))
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = size,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = StylingerColors.TextPrimary
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Description
            Text(
                text = "DESCRIPTION",
                style = StylingerTypography.labelSmall,
                color = StylingerColors.TextSecondary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = product.description.ifEmpty { "High-craft luxury garment tailored for comfort and modern silhouettes." },
                style = StylingerTypography.bodyMedium,
                color = StylingerColors.TextSecondary
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Styling Specs (Body shapes & Undertones)
            if (product.suitableBodyShapes.isNotEmpty() || product.suitableUndertones.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = StylingerColors.BrandLight)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "STYLING ENGINE SPECS",
                            style = StylingerTypography.labelSmall,
                            color = StylingerColors.BrandPrimary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Suitable Silhouettes: ${product.suitableBodyShapes.joinToString()}",
                            style = StylingerTypography.bodySmall,
                            color = StylingerColors.TextPrimary
                        )
                        Text(
                            text = "Optimal Undertones: ${product.suitableUndertones.joinToString()}",
                            style = StylingerTypography.bodySmall,
                            color = StylingerColors.TextPrimary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Bottom Actions: Edit & Delete
            Row(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = {
                        onEdit(product.copy(stockQuantity = currentStock))
                        onDismiss()
                    },
                    modifier = Modifier
                        .weight(1.5f)
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = StylingerColors.BrandPrimary)
                ) {
                    Icon(
                        imageVector = StylingerIcons.Edit,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Edit Product", style = StylingerTypography.labelLarge, color = Color.White)
                }

                Spacer(modifier = Modifier.width(10.dp))

                OutlinedButton(
                    onClick = {
                        scope.launch {
                            inventoryRepo.deleteProduct(product.id)
                            onProductUpdated()
                            onDismiss()
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, StylingerColors.Danger),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = StylingerColors.Surface)
                ) {
                    Icon(
                        imageVector = StylingerIcons.Delete,
                        contentDescription = null,
                        tint = StylingerColors.Danger,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Delete", style = StylingerTypography.labelLarge, color = StylingerColors.Danger)
                }
            }
        }
    }
}
