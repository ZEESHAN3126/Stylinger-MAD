package com.stylinger.app.features.customer

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import com.stylinger.app.core.ui.StylingerIcons
import com.stylinger.app.domain.model.WardrobeItem
import com.stylinger.app.domain.repository.IWardrobeRepository
import kotlinx.coroutines.launch
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerWardrobeScreen(
    wardrobeRepo: IWardrobeRepository,
    onOpenModeSwitcher: () -> Unit
) {
    var selectedCategory by remember { mutableStateOf("All") }
    var wardrobeItems by remember { mutableStateOf<List<WardrobeItem>>(emptyList()) }
    var showAddSheet by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val addSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    fun refresh() {
        scope.launch {
            wardrobeItems = wardrobeRepo.getWardrobeItems(
                category = if (selectedCategory == "All") null else selectedCategory
            )
        }
    }

    LaunchedEffect(selectedCategory) {
        refresh()
    }

    val categories = listOf("All", "Shirts", "T-Shirts", "Jeans", "Trousers", "Outerwear", "Footwear", "Dresses")

    Scaffold(
        containerColor = StylingerColors.Canvas,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddSheet = true },
                containerColor = StylingerColors.TextPrimary,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = StylingerIcons.Add,
                    contentDescription = "Add Piece",
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
                        text = "Digital Wardrobe",
                        style = StylingerTypography.headlineLarge.copy(
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = StylingerColors.TextPrimary
                    )
                    Text(
                        text = "${wardrobeItems.size} pieces in your digital closet",
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
                            text = "Customer",
                            style = StylingerTypography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = StylingerColors.TextPrimary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Category Chips
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

            // Wardrobe Grid (2-columns)
            if (wardrobeItems.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 60.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No pieces in this category.",
                        style = StylingerTypography.bodyMedium,
                        color = StylingerColors.TextSecondary
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 80.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(wardrobeItems, key = { it.id }) { item ->
                        WardrobeGridCard(
                            item = item,
                            onIncrementWear = {
                                scope.launch {
                                    wardrobeRepo.incrementWearCount(item.id)
                                    refresh()
                                }
                            }
                        )
                    }
                }
            }
        }

        // Add Piece Bottom Sheet
        if (showAddSheet) {
            AddWardrobePieceSheet(
                onDismiss = { showAddSheet = false },
                onAdd = { newItem ->
                    scope.launch {
                        wardrobeRepo.addWardrobeItem(newItem)
                        refresh()
                        showAddSheet = false
                    }
                },
                sheetState = addSheetState
            )
        }
    }
}

@Composable
fun WardrobeGridCard(
    item: WardrobeItem,
    onIncrementWear: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = StylingerColors.Surface),
        border = BorderStroke(0.8.dp, StylingerColors.Border),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column {
            // Visual Preview
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.1f)
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0xFFEDE9DF), Color(0xFFDED8CD))
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
                        text = item.category.uppercase(),
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = StylingerColors.TextPrimary
                    )
                }

                // Center initial
                Text(
                    text = item.name.take(2).uppercase(),
                    modifier = Modifier.align(Alignment.Center),
                    fontFamily = FontFamily.Serif,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = StylingerColors.TextPrimary.copy(alpha = 0.35f)
                )

                // Wear count badge
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .background(StylingerColors.TextPrimary, RoundedCornerShape(6.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "Worn ${item.wearCount}x",
                        color = Color.White,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            // Info
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = item.name,
                    style = StylingerTypography.titleSmall,
                    color = StylingerColors.TextPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${item.color} · ${item.category}",
                    style = StylingerTypography.bodySmall,
                    color = StylingerColors.TextMuted,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Quick increment wear button
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { onIncrementWear() },
                    shape = RoundedCornerShape(8.dp),
                    color = StylingerColors.Canvas,
                    border = BorderStroke(0.6.dp, StylingerColors.Border)
                ) {
                    Text(
                        text = "+ Log Wear",
                        modifier = Modifier.padding(vertical = 5.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = StylingerColors.TextPrimary
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWardrobePieceSheet(
    onDismiss: () -> Unit,
    onAdd: (WardrobeItem) -> Unit,
    sheetState: androidx.compose.material3.SheetState
) {
    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Shirts") }
    var color by remember { mutableStateOf("Cream White") }

    val categories = listOf("Shirts", "T-Shirts", "Jeans", "Trousers", "Outerwear", "Footwear", "Dresses")

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
            Text(
                text = "DIGITAL CLOSET",
                style = StylingerTypography.labelSmall,
                color = StylingerColors.BrandPrimary
            )
            Text(
                text = "Add Piece to Wardrobe",
                style = StylingerTypography.titleLarge.copy(fontFamily = FontFamily.Serif),
                color = StylingerColors.TextPrimary
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Item Name") },
                placeholder = { Text("e.g. Classic Trench Coat") },
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
                categories.forEach { cat ->
                    PillChip(
                        label = cat,
                        isSelected = category == cat,
                        onSelected = { category = cat }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        val newItem = WardrobeItem(
                            id = "wardrobe_${UUID.randomUUID()}",
                            name = name,
                            category = category,
                            color = color,
                            imageUrl = "local://wardrobe/custom.jpg",
                            wearCount = 1,
                            source = "manual"
                        )
                        onAdd(newItem)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = StylingerColors.TextPrimary)
            ) {
                Icon(
                    imageVector = StylingerIcons.Add,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Add to My Closet", style = StylingerTypography.labelLarge, color = Color.White)
            }
        }
    }
}
