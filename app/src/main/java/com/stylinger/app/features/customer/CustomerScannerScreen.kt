package com.stylinger.app.features.customer

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
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

@Composable
fun CustomerScannerScreen(
    onSimulatedStoreScan: () -> Unit
) {
    var isFlashlightOn by remember { mutableStateOf(false) }

    // Animated scanning beam translation
    val infiniteTransition = rememberInfiniteTransition(label = "scannerBeam")
    val scanLineY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scanLineY"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F1113))
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "IN-STORE SCANNER",
                        style = StylingerTypography.labelSmall.copy(
                            letterSpacing = 1.6.sp,
                            color = StylingerColors.BrandPrimary
                        )
                    )
                    Text(
                        text = "Aura Atelier Viewfinder",
                        style = StylingerTypography.titleMedium.copy(
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = Color.White
                    )
                }

                // Flashlight toggle
                IconButton(
                    onClick = { isFlashlightOn = !isFlashlightOn },
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(if (isFlashlightOn) StylingerColors.BrandPrimary else Color.White.copy(alpha = 0.15f))
                ) {
                    Icon(
                        imageVector = StylingerIcons.Star,
                        contentDescription = "Flashlight",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // Viewfinder Zone
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.82f)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color.Black.copy(alpha = 0.65f))
                        .border(1.dp, Color.White.copy(alpha = 0.15f), RoundedCornerShape(24.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    // Corner bracket markers & animated line
                    Canvas(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                        val w = size.width
                        val h = size.height
                        val cornerLen = 32.dp.toPx()
                        val strokeW = 3.dp.toPx()
                        val cornerColor = Color(0xFF2457D6)

                        // Top-Left
                        drawLine(cornerColor, Offset(0f, 0f), Offset(cornerLen, 0f), strokeW)
                        drawLine(cornerColor, Offset(0f, 0f), Offset(0f, cornerLen), strokeW)

                        // Top-Right
                        drawLine(cornerColor, Offset(w, 0f), Offset(w - cornerLen, 0f), strokeW)
                        drawLine(cornerColor, Offset(w, 0f), Offset(w, cornerLen), strokeW)

                        // Bottom-Left
                        drawLine(cornerColor, Offset(0f, h), Offset(cornerLen, h), strokeW)
                        drawLine(cornerColor, Offset(0f, h), Offset(0f, h - cornerLen), strokeW)

                        // Bottom-Right
                        drawLine(cornerColor, Offset(w, h), Offset(w - cornerLen, h), strokeW)
                        drawLine(cornerColor, Offset(w, h), Offset(w, h - cornerLen), strokeW)

                        // Moving scanning line
                        val currentY = h * scanLineY
                        drawLine(
                            color = Color(0xFF2457D6),
                            start = Offset(10f, currentY),
                            end = Offset(w - 10f, currentY),
                            strokeWidth = 2.5.dp.toPx()
                        )
                    }

                    // QR Watermark hint
                    Icon(
                        imageVector = StylingerIcons.QrCode,
                        contentDescription = null,
                        tint = Color.White.copy(alpha = 0.12f),
                        modifier = Modifier.size(110.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Align camera with garment tag or entrance stand",
                    style = StylingerTypography.bodyMedium,
                    color = Color.White.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "stylinger://store/store_aura_vadodara",
                    style = StylingerTypography.bodySmall.copy(fontSize = 11.sp),
                    color = Color.White.copy(alpha = 0.45f)
                )
            }

            // Bottom Actions: Manual Demo Trigger
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color.White.copy(alpha = 0.1f),
                    modifier = Modifier.padding(bottom = 12.dp)
                ) {
                    Text(
                        text = "LOCAL DEMO MODE · NO CAMERA HARDWARE REQUIRED",
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        style = StylingerTypography.labelSmall.copy(
                            fontSize = 9.sp,
                            color = Color.White.copy(alpha = 0.7f),
                            letterSpacing = 1.sp
                        )
                    )
                }

                Button(
                    onClick = onSimulatedStoreScan,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = StylingerColors.BrandPrimary)
                ) {
                    Icon(
                        imageVector = StylingerIcons.Scanner,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Simulate Store QR Scan",
                        style = StylingerTypography.labelLarge.copy(fontSize = 15.sp),
                        color = Color.White
                    )
                }
            }
        }
    }
}
