package com.stylinger.app.core.theme

import androidx.compose.ui.graphics.Color

object StylingerColors {
    // Canvas & Surface Foundations
    val Canvas = Color(0xFFF6F4EF)         // Warm Ivory (Primary Canvas)
    val Surface = Color(0xFFFFFFFF)        // Clean Solid White (Cards)
    val SurfaceElevated = Color(0xFFEFECE4)// Soft Ivory / Cream
    val SurfaceDark = Color(0xFF111419)    // Rich Charcoal-Black

    // Dividers & Hairline Borders
    val Border = Color(0xFFD9DCE1)         // Neutral Hairline Border
    val BorderLight = Color(0x33D9DCE1)    // Subtle Translucent Border

    // Typography
    val TextPrimary = Color(0xFF17191C)    // Deep Charcoal (13.8:1 AAA Contrast)
    val TextSecondary = Color(0xFF626872)  // Cool Gray
    val TextMuted = Color(0xFF8B929E)      // Muted Slate
    val TextInverse = Color(0xFFFFFFFF)    // Clean White

    // Brand Accents
    val BrandPrimary = Color(0xFF2457D6)   // Deep Cobalt Blue
    val BrandSecondary = Color(0xFF5F82E8) // Soft Cornflower Blue
    val BrandLight = Color(0xFFEEF2FC)      // 10% Cobalt Ice-Tint
    val BrandTint = BrandLight

    // Utility & Status
    val Success = Color(0xFF15803D)        // Emerald Green (In Stock)
    val SuccessLight = Color(0xFFDCFCE7)   // Emerald Tint
    val Warning = Color(0xFFD97706)        // Warm Amber (Low Stock <= 2)
    val WarningLight = Color(0xFFFEF3C7)   // Amber Tint
    val Danger = Color(0xFFDC2626)         // Crimson Red (Sold Out)
    val DangerLight = Color(0xFFFEE2E2)    // Red Tint

    // Monk Skin Tone Scale (10 MST Points)
    val MonkSkinTones = listOf(
        Color(0xFFF6EDE4), // MST 1
        Color(0xFFF3E7DB), // MST 2
        Color(0xFFF7DAD0), // MST 3
        Color(0xFFEADABA), // MST 4
        Color(0xFFD7BD96), // MST 5
        Color(0xFFA07E56), // MST 6
        Color(0xFF825C43), // MST 7
        Color(0xFF604134), // MST 8
        Color(0xFF3A312A), // MST 9
        Color(0xFF292420)  // MST 10
    )
}

object MonkSkinToneScale {
    val MST_01 = Color(0xFFF6EDE4)
    val MST_02 = Color(0xFFF3E7DB)
    val MST_03 = Color(0xFFF7DAD0)
    val MST_04 = Color(0xFFEADABA)
    val MST_05 = Color(0xFFD7BD96)
    val MST_06 = Color(0xFFA07E56)
    val MST_07 = Color(0xFF825C43)
    val MST_08 = Color(0xFF604134)
    val MST_09 = Color(0xFF3A312A)
    val MST_10 = Color(0xFF292420)
}
