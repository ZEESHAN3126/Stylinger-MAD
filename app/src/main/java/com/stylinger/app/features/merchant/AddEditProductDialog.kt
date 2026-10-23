package com.stylinger.app.features.merchant

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stylinger.app.core.theme.StylingerColors
import com.stylinger.app.core.theme.StylingerTypography
import com.stylinger.app.core.ui.PillChip
import com.stylinger.app.core.ui.StylingerIcons
import com.stylinger.app.domain.model.Product
import com.stylinger.app.domain.repository.IInventoryRepository
import kotlinx.coroutines.launch
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditProductDialog(
    initialProduct: Product? = null,
    inventoryRepo: IInventoryRepository,
    onDismiss: () -> Unit,
    onSaved: () -> Unit,
    sheetState: SheetState
) {
    val isEdit = initialProduct != null
    val scope = rememberCoroutineScope()

    var name by remember { mutableStateOf(initialProduct?.name ?: "") }
    var sku by remember {
        mutableStateOf(
            initialProduct?.sku ?: "AURA-${(100..999).random()}"
        )
    }
    var category by remember { mutableStateOf(initialProduct?.category ?: "Outerwear") }
    var price by remember { mutableStateOf(initialProduct?.price?.toInt()?.toString() ?: "4999") }
    var color by remember { mutableStateOf(initialProduct?.color ?: "Camel Beige") }
    var description by remember {
        mutableStateOf(
            initialProduct?.description ?: "Handcrafted luxury piece with bespoke finishing."
        )
    }
    var stockQuantity by remember { mutableIntStateOf(initialProduct?.stockQuantity ?: 5) }
    var selectedSizes by remember {
        mutableStateOf(
            initialProduct?.sizes?.toSet() ?: setOf("S", "M", "L")
        )
    }

    val availableCategories = listOf(
        "Shirts", "T-Shirts", "Jeans", "Trousers",
        "Knitwear", "Outerwear", "Dresses", "Footwear", "Accessories"
    )

    val sizeOptions = listOf("XS", "S", "M", "L", "XL", "XXL", "28", "30", "32", "34")

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
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = if (isEdit) "EDIT PRODUCT" else "NEW CATALOG ITEM",
                        style = StylingerTypography.labelSmall,
                        color = StylingerColors.BrandPrimary
                    )
                    Text(
                        text = if (isEdit) "Update Garment Specs" else "Add New Garment",
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

            // Product Name Field
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Product Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = StylingerColors.Surface,
                    unfocusedContainerColor = StylingerColors.Surface,
                    focusedBorderColor = StylingerColors.BrandPrimary,
                    unfocusedBorderColor = StylingerColors.Border
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // SKU & Price Row
            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = sku,
                    onValueChange = { sku = it },
                    label = { Text("SKU") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = StylingerColors.Surface,
                        unfocusedContainerColor = StylingerColors.Surface,
                        focusedBorderColor = StylingerColors.BrandPrimary,
                        unfocusedBorderColor = StylingerColors.Border
                    )
                )

                Spacer(modifier = Modifier.width(10.dp))

                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Price (₹)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = StylingerColors.Surface,
                        unfocusedContainerColor = StylingerColors.Surface,
                        focusedBorderColor = StylingerColors.BrandPrimary,
                        unfocusedBorderColor = StylingerColors.Border
                    )
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Category Chips Selector
            Text(
                text = "CATEGORY",
                style = StylingerTypography.labelSmall,
                color = StylingerColors.TextSecondary
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                availableCategories.forEach { cat ->
                    PillChip(
                        label = cat,
                        isSelected = category == cat,
                        onSelected = { category = cat }
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Color Field
            OutlinedTextField(
                value = color,
                onValueChange = { color = it },
                label = { Text("Color") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = StylingerColors.Surface,
                    unfocusedContainerColor = StylingerColors.Surface,
                    focusedBorderColor = StylingerColors.BrandPrimary,
                    unfocusedBorderColor = StylingerColors.Border
                )
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Sizes Selection (Multi-select)
            Text(
                text = "AVAILABLE SIZES",
                style = StylingerTypography.labelSmall,
                color = StylingerColors.TextSecondary
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                sizeOptions.forEach { size ->
                    val isSelected = selectedSizes.contains(size)
                    PillChip(
                        label = size,
                        isSelected = isSelected,
                        onSelected = {
                            selectedSizes = if (isSelected) {
                                selectedSizes - size
                            } else {
                                selectedSizes + size
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Stock Quantity Stepper Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = StylingerColors.Canvas),
                border = BorderStroke(0.8.dp, StylingerColors.Border)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "INITIAL STOCK QUANTITY",
                            style = StylingerTypography.labelSmall,
                            color = StylingerColors.TextSecondary
                        )
                        Text(
                            text = "$stockQuantity units available",
                            style = StylingerTypography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                            color = StylingerColors.TextPrimary
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        OutlinedButton(
                            onClick = { if (stockQuantity > 0) stockQuantity-- },
                            modifier = Modifier.size(36.dp),
                            shape = CircleShape,
                            contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
                        ) {
                            Text("-", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }

                        Text(
                            text = "$stockQuantity",
                            modifier = Modifier.padding(horizontal = 12.dp),
                            style = StylingerTypography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )

                        Button(
                            onClick = { stockQuantity++ },
                            modifier = Modifier.size(36.dp),
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(containerColor = StylingerColors.BrandPrimary),
                            contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
                        ) {
                            Text("+", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Description Field
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description & Fabric Details") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = StylingerColors.Surface,
                    unfocusedContainerColor = StylingerColors.Surface,
                    focusedBorderColor = StylingerColors.BrandPrimary,
                    unfocusedBorderColor = StylingerColors.Border
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Save Button
            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        val product = Product(
                            id = initialProduct?.id ?: "prod_${UUID.randomUUID()}",
                            storeId = "store_aura_vadodara",
                            name = name,
                            sku = sku,
                            category = category,
                            color = color,
                            price = price.toDoubleOrNull() ?: 4999.0,
                            stockQuantity = stockQuantity,
                            sizes = if (selectedSizes.isEmpty()) listOf("M") else selectedSizes.toList(),
                            imageUrl = initialProduct?.imageUrl ?: "local://products/new.jpg",
                            description = description,
                            suitableBodyShapes = listOf("Hourglass", "Rectangle"),
                            suitableUndertones = listOf("Warm", "Neutral")
                        )

                        scope.launch {
                            inventoryRepo.saveProduct(product)
                            onSaved()
                            onDismiss()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = StylingerColors.BrandPrimary)
            ) {
                Icon(
                    imageVector = StylingerIcons.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isEdit) "Update Product" else "Add to Aura Inventory",
                    style = StylingerTypography.labelLarge,
                    color = Color.White
                )
            }
        }
    }
}
