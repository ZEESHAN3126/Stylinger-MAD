package com.stylinger.app.features.merchant

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stylinger.app.core.theme.StylingerColors
import com.stylinger.app.core.theme.StylingerTypography
import com.stylinger.app.core.ui.PillChip
import com.stylinger.app.core.ui.StockStatusBadge
import com.stylinger.app.core.ui.StylingerIcons
import com.stylinger.app.domain.model.Product
import com.stylinger.app.domain.repository.IInventoryRepository
import kotlinx.coroutines.launch

@Composable
fun MerchantInventoryScreen(
    inventoryRepo: IInventoryRepository,
    onSelectProduct: (Product) -> Unit,
    onNavigateToAddProduct: () -> Unit,
    onOpenModeSwitcher: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    var products by remember { mutableStateOf<List<Product>>(emptyList()) }
    val scope = rememberCoroutineScope()

    fun refresh() {
        scope.launch {
            products = inventoryRepo.getProducts(
                category = if (selectedCategory == "All") null else selectedCategory,
                query = if (searchQuery.isBlank()) null else searchQuery
            )
        }
    }

    LaunchedEffect(selectedCategory, searchQuery) {
        refresh()
    }

    val categories = listOf(
        "All", "Shirts", "T-Shirts", "Jeans", "Trousers",
        "Knitwear", "Outerwear", "Dresses", "Footwear", "Accessories"
    )

    Scaffold(
        containerColor = StylingerColors.Canvas,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAddProduct,
                containerColor = StylingerColors.BrandPrimary,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = StylingerIcons.Add,
                    contentDescription = "Add Product",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Inventory",
                        style = StylingerTypography.headlineLarge.copy(
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = StylingerColors.TextPrimary
                    )
                    Text(
                        text = "${products.size} items in catalog",
                        style = StylingerTypography.bodyMedium,
                        color = StylingerColors.TextSecondary
                    )
                }

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = StylingerColors.Surface,
                    border = BorderStroke(0.8.dp, StylingerColors.Border),
                    modifier = Modifier.clickable { onOpenModeSwitcher() }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = StylingerIcons.Swap,
                            contentDescription = null,
                            tint = StylingerColors.BrandPrimary,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Merchant",
                            style = StylingerTypography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = StylingerColors.TextPrimary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "Search products or SKU...",
                        style = StylingerTypography.bodyMedium,
                        color = StylingerColors.TextMuted
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = StylingerIcons.Search,
                        contentDescription = "Search",
                        tint = StylingerColors.TextSecondary,
                        modifier = Modifier.size(20.dp)
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        Icon(
                            imageVector = StylingerIcons.Close,
                            contentDescription = "Clear",
                            tint = StylingerColors.TextSecondary,
                            modifier = Modifier
                                .size(18.dp)
                                .clickable { searchQuery = "" }
                        )
                    }
                },
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

            // Category Chips Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { category ->
                    PillChip(
                        label = category,
                        isSelected = selectedCategory == category,
                        onSelected = { selectedCategory = category }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Product Grid (2 columns)
            if (products.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 60.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "No products found",
                            style = StylingerTypography.titleMedium,
                            color = StylingerColors.TextPrimary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Try clearing filters or search terms.",
                            style = StylingerTypography.bodyMedium,
                            color = StylingerColors.TextSecondary
                        )
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 80.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(products, key = { it.id }) { product ->
                        ProductGridCard(
                            product = product,
                            onClick = { onSelectProduct(product) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProductGridCard(
    product: Product,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = StylingerColors.Surface),
        border = BorderStroke(0.8.dp, StylingerColors.Border),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column {
            // Visual Preview Area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.1f)
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color(0xFFF0EBE1),
                                Color(0xFFE5DED0)
                            )
                        )
                    )
                    .padding(10.dp)
            ) {
                // Category badge
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .background(Color.White.copy(alpha = 0.9f), RoundedCornerShape(6.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = product.category.uppercase(),
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = StylingerColors.TextPrimary
                    )
                }

                // Status Badge
                Box(modifier = Modifier.align(Alignment.BottomEnd)) {
                    StockStatusBadge(quantity = product.stockQuantity)
                }

                // Center artistic typography mark
                Text(
                    text = product.name.take(2).uppercase(),
                    modifier = Modifier.align(Alignment.Center),
                    fontFamily = FontFamily.Serif,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = StylingerColors.TextPrimary.copy(alpha = 0.35f)
                )
            }

            // Info Section
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = product.name,
                    style = StylingerTypography.titleSmall,
                    color = StylingerColors.TextPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "SKU: ${product.sku}",
                    style = StylingerTypography.bodySmall,
                    color = StylingerColors.TextMuted,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "₹${product.price.toInt()}",
                        style = StylingerTypography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = StylingerColors.TextPrimary
                    )
                    Text(
                        text = "${product.stockQuantity} pcs",
                        style = StylingerTypography.bodySmall,
                        color = StylingerColors.TextSecondary
                    )
                }
            }
        }
    }
}
