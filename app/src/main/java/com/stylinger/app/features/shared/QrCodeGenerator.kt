package com.stylinger.app.features.shared

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.stylinger.app.core.theme.StylingerColors
import kotlin.math.abs

/**
 * Deterministic, offline QR Code Matrix visualizer.
 * Renders standard QR finder patterns (top-left, top-right, bottom-left 7x7)
 * and generates deterministic data module cells based on payload hash.
 */
@Composable
fun StylingerQrCode(
    payload: String,
    modifier: Modifier = Modifier,
    size: Dp = 200.dp,
    darkColor: Color = StylingerColors.TextPrimary,
    lightColor: Color = Color.White
) {
    val matrixSize = 25
    val matrix = remember(payload) {
        generateQrMatrix(payload, matrixSize)
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(lightColor)
            .border(1.dp, StylingerColors.Border, RoundedCornerShape(16.dp))
            .padding(16.dp)
            .aspectRatio(1f)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val cellSize = this.size.width / matrixSize

            for (row in 0 until matrixSize) {
                for (col in 0 until matrixSize) {
                    if (matrix[row][col]) {
                        drawRect(
                            color = darkColor,
                            topLeft = Offset(col * cellSize, row * cellSize),
                            size = Size(cellSize * 1.02f, cellSize * 1.02f)
                        )
                    }
                }
            }
        }
    }
}

private fun generateQrMatrix(payload: String, size: Int): Array<BooleanArray> {
    val matrix = Array(size) { BooleanArray(size) }

    // Finder pattern painter
    fun drawFinder(startRow: Int, startCol: Int) {
        for (r in 0 until 7) {
            for (c in 0 until 7) {
                val isOuter = r == 0 || r == 6 || c == 0 || c == 6
                val isInner = r in 2..4 && c in 2..4
                matrix[startRow + r][startCol + c] = isOuter || isInner
            }
        }
    }

    // Top-Left, Top-Right, Bottom-Left finders
    drawFinder(0, 0)
    drawFinder(0, size - 7)
    drawFinder(size - 7, 0)

    // Timing patterns
    for (i in 7 until size - 7) {
        matrix[6][i] = i % 2 == 0
        matrix[i][6] = i % 2 == 0
    }

    // Deterministic payload hash distribution
    val seed = abs(payload.hashCode())
    var currentSeed = seed
    for (r in 0 until size) {
        for (c in 0 until size) {
            // Skip finder zones
            val inTopLeft = r < 8 && c < 8
            val inTopRight = r < 8 && c >= size - 8
            val inBottomLeft = r >= size - 8 && c < 8
            val inTiming = r == 6 || c == 6

            if (!inTopLeft && !inTopRight && !inBottomLeft && !inTiming) {
                currentSeed = (currentSeed * 1103515245 + 12345) and 0x7FFFFFFF
                val charVal = if (payload.isNotEmpty()) payload[(r * size + c) % payload.length].code else 0
                val bit = (currentSeed xor (r * 31 + c * 17) xor charVal) % 3 == 0
                matrix[r][c] = bit
            }
        }
    }

    return matrix
}
