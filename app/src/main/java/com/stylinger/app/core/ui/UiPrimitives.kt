package com.stylinger.app.core.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stylinger.app.core.theme.StylingerColors

@Composable
fun LiquidGlassCard(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 16.dp,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius))
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier),
        shape = RoundedCornerShape(cornerRadius),
        colors = CardDefaults.cardColors(
            containerColor = StylingerColors.Surface.copy(alpha = 0.88f)
        ),
        border = BorderStroke(0.8.dp, StylingerColors.Border.copy(alpha = 0.7f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            content()
        }
    }
}

@Composable
fun EditorialHeader(
    title: String,
    modifier: Modifier = Modifier,
    eyebrow: String? = null,
    description: String? = null,
    trailingAction: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            if (eyebrow != null) {
                Text(
                    text = eyebrow.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = StylingerColors.BrandPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
            Text(
                text = title,
                style = MaterialTheme.typography.displayMedium,
                color = StylingerColors.TextPrimary
            )
            if (description != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = StylingerColors.TextSecondary
                )
            }
        }
        if (trailingAction != null) {
            trailingAction()
        }
    }
}

@Composable
fun PillChip(
    label: String,
    isSelected: Boolean,
    onSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .clickable { onSelected() },
        shape = RoundedCornerShape(20.dp),
        color = if (isSelected) StylingerColors.TextPrimary else StylingerColors.Surface,
        border = BorderStroke(
            0.8.dp,
            if (isSelected) StylingerColors.TextPrimary else StylingerColors.Border
        ),
        shadowElevation = if (isSelected) 2.dp else 0.dp
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                fontSize = 13.sp
            ),
            color = if (isSelected) StylingerColors.TextInverse else StylingerColors.TextPrimary
        )
    }
}

@Composable
fun StockStatusBadge(
    quantity: Int,
    modifier: Modifier = Modifier
) {
    val (bg, fg, label) = when {
        quantity <= 0 -> Triple(StylingerColors.DangerLight, StylingerColors.Danger, "Sold out")
        quantity <= 2 -> Triple(StylingerColors.WarningLight, StylingerColors.Warning, "Low ($quantity left)")
        else -> Triple(StylingerColors.SuccessLight, StylingerColors.Success, "$quantity in stock")
    }

    Box(
        modifier = modifier
            .background(bg, RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = label,
            color = fg,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}
