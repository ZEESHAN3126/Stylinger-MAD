# Stylinger Design System & Flutter UI Specification

**Author:** Antigravity Architecture Team  
**Document Version:** 1.0.0 — Flutter Design System Edition  
**Target Platform:** Flutter 3.x (Android & iOS)  
**Date:** September 2026  

---

## 1. Visual Brand Philosophy: Editorial Luxury Meets Retail Utility

Stylinger's aesthetic identity bridges the high-fashion editorial feel of luxury print journals (*Vogue*, *Harper's Bazaar*) with the high-speed utility required by physical Indian retail shopkeepers.

The visual brand system, established during the web engineering phase and formalized for mobile, is termed **"Editorial Ivory"**. It is built on three pillars:
1. **Warm Canvas Foundation:** A calm, tactile `#F6F4EF` Ivory canvas replacing harsh electronic whites.
2. **High-Contrast Editorial Typography:** A pairing of *Instrument Serif* (expressive, luxury editorial headlines) with *Inter* (hyper-legible, geometric UI text).
3. **Liquid Glass Materiality:** Translucent frosted containers (`BackdropFilter` blur with specular white highlights) layered over warm ivory backgrounds.

---

## 2. Four-Tier Design System Evolution

| Design Token Area | PRODUCT REQUIREMENT (PRD v2.0) | CURRENT WEBSITE IMPLEMENTATION | MOBILE MVP TARGET (Flutter) | FUTURE INTEGRATION (Phase 2+) |
|---|---|---|---|---|
| **Color Palette** | Unspecified (focused on merchant/customer business flows). | **"Editorial Ivory"** (`#F6F4EF`), Deep Cobalt (`#2457D6`), Deep Charcoal (`#17191C`), Emerald green accents. | **Exact Flutter Color Palette** mapped to `AppColors` constants. High-contrast WCAG AAA compliance. | Dark Mode luxury OLED palette ("Midnight Obsidian"). |
| **Typography** | Standard platform sans-serif. | *Instrument Serif* (Display headings) + *Inter* (Body/UI). | **`GoogleFonts.instrumentSerif` & `GoogleFonts.inter`** configured via `ThemeData`. | Dynamic font asset bundling for zero-latency offline font rendering. |
| **Material Layer** | Standard cards and modals. | CSS `backdrop-filter: blur(20px)` + multi-stop white gradient specular highlights. | **`LiquidGlassCard` widget** utilizing Flutter `BackdropFilter` (ImageFilter.blur) and border gradients. | Hardware-accelerated GLSL custom shaders for liquid glass refraction. |
| **Brand Iconography** | Stylinger logo badge on entrance QR codes. | Stylinger "S" glyph inside cobalt/emerald square + `lucide-react` icons. | **Custom SVG Logo widget** + Flutter standard `Icons` (or `lucide_icons` package). | Animated vector splash animation with Rive or Lottie. |

---

## 3. Flutter Color Palette & Tokens

```dart
// lib/core/constants/app_colors.dart
import 'package:flutter/material.dart';

class AppColors {
  AppColors._();

  // Canvas & Surfaces
  static const Color canvas = Color(0xFFF6F4EF);         // Warm Ivory (Primary background)
  static const Color surface = Color(0xFFFFFFFF);        // Clean Solid White (Cards)
  static const Color surfaceElevated = Color(0xFFEFECE4);// Soft Ivory / Cream (Elevated modules)
  static const Color surfaceDark = Color(0xFF111419);    // Rich Charcoal-Black (Luxury anchor)

  // Dividers & Hairline Borders
  static const Color border = Color(0xFFD9DCE1);         // Neutral hairline border (0.8px)
  static const Color borderLight = Color(0x33D9DCE1);    // Translucent border for glass cards

  // Typography Tokens
  static const Color textPrimary = Color(0xFF17191C);    // Deep Charcoal (13.8:1 AAA contrast)
  static const Color textSecondary = Color(0xFF626872);  // Cool Gray (5.2:1 contrast)
  static const Color textMuted = Color(0xFF8B929E);      // Muted Slate (Subtle labels)
  static const Color textInverse = Color(0xFFFFFFFF);    // Clean White (For dark backgrounds)

  // Brand Accent (Deep Cobalt)
  static const Color brandPrimary = Color(0xFF2457D6);   // Deep Cobalt Blue (Primary actions)
  static const Color brandSecondary = Color(0xFF5F82E8); // Soft Cornflower Blue
  static const Color brandTint = Color(0xFFEEF2FC);      // 10% Cobalt Ice-Tint (Pills & highlights)

  // Status & Utility Accents
  static const Color success = Color(0xFF15803D);        // Emerald Green (In stock, confirmed)
  static const Color successLight = Color(0xFFDCFCE7);    // Emerald Tint
  static const Color warning = Color(0xFFD97706);        // Warm Amber (Low stock <= 2 units)
  static const Color warningLight = Color(0xFFFEF3C7);    // Amber Tint
  static const Color danger = Color(0xFFDC2626);         // Crimson Red (Sold out, danger zone)
  static const Color dangerLight = Color(0xFFFEE2E2);     // Red Tint

  // Monk Skin Tone Swatches (10 MST Points)
  static const List<Color> monkSkinTones = [
    Color(0xFFF6EDE4), // MST 1
    Color(0xFFF3E7DB), // MST 2
    Color(0xFFF7DAD0), // MST 3
    Color(0xFFEADABA), // MST 4
    Color(0xFFD7BD96), // MST 5
    Color(0xFFA07E56), // MST 6
    Color(0xFF825C43), // MST 7
    Color(0xFF604134), // MST 8
    Color(0xFF3A312A), // MST 9
    Color(0xFF292420), // MST 10
  ];
}
```

---

## 4. Typography System (Google Fonts)

Stylinger Mobile uses Google Fonts:
- **Display Headings:** *Instrument Serif* (Italic & Regular) for hero statements, outfit titles, and monetary figures.
- **Interface & Body:** *Inter* for buttons, table cells, form labels, and navigation.

```dart
// lib/core/constants/app_typography.dart
import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'app_colors.dart';

class AppTypography {
  AppTypography._();

  // Display Serif Headings
  static TextStyle displayLarge = GoogleFonts.instrumentSerif(
    fontSize: 32,
    fontWeight: FontWeight.normal,
    color: AppColors.textPrimary,
    letterSpacing: -0.5,
    height: 1.15,
  );

  static TextStyle displayMedium = GoogleFonts.instrumentSerif(
    fontSize: 24,
    fontWeight: FontWeight.normal,
    color: AppColors.textPrimary,
    letterSpacing: -0.3,
    height: 1.2,
  );

  static TextStyle displaySmall = GoogleFonts.instrumentSerif(
    fontSize: 20,
    fontWeight: FontWeight.normal,
    fontStyle: FontStyle.italic,
    color: AppColors.brandPrimary,
  );

  // Interface / Body (Inter)
  static TextStyle titleLarge = GoogleFonts.inter(
    fontSize: 18,
    fontWeight: FontWeight.w600,
    color: AppColors.textPrimary,
  );

  static TextStyle titleMedium = GoogleFonts.inter(
    fontSize: 16,
    fontWeight: FontWeight.w600,
    color: AppColors.textPrimary,
  );

  static TextStyle bodyMedium = GoogleFonts.inter(
    fontSize: 14,
    fontWeight: FontWeight.w400,
    color: AppColors.textSecondary,
    height: 1.45,
  );

  static TextStyle bodySmall = GoogleFonts.inter(
    fontSize: 12,
    fontWeight: FontWeight.w400,
    color: AppColors.textMuted,
  );

  static TextStyle buttonLabel = GoogleFonts.inter(
    fontSize: 14,
    fontWeight: FontWeight.w600,
    letterSpacing: 0.2,
  );

  static TextStyle eyebrow = GoogleFonts.inter(
    fontSize: 11,
    fontWeight: FontWeight.w700,
    letterSpacing: 1.2,
    color: AppColors.brandPrimary,
  );
}
```

---

## 5. Core Reusable Flutter Widget Primitives

### 1. `LiquidGlassCard` (Frosted Material Container)
Translates the web app's `.liquid-glass-badge` and glass card layer into Flutter using `BackdropFilter` and linear gradients.

```dart
// lib/core/widgets/liquid_glass_card.dart
import 'dart:ui';
import 'package:flutter/material.dart';
import '../constants/app_colors.dart';

class LiquidGlassCard extends StatelessWidget {
  final Widget child;
  final EdgeInsetsGeometry padding;
  final VoidCallback? onTap;
  final double borderRadius;

  const LiquidGlassCard({
    Key? key,
    required this.child,
    this.padding = const EdgeInsets.all(16),
    this.onTap,
    this.borderRadius = 16,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return ClipRRect(
      borderRadius: BorderRadius.circular(borderRadius),
      child: BackdropFilter(
        filter: ImageFilter.blur(sigmaX: 16, sigmaY: 16),
        child: InkWell(
          onTap: onTap,
          borderRadius: BorderRadius.circular(borderRadius),
          child: Container(
            padding: padding,
            decoration: BoxDecoration(
              gradient: LinearGradient(
                begin: Alignment.topLeft,
                end: Alignment.bottomRight,
                colors: [
                  Colors.white.withOpacity(0.75),
                  Colors.white.withOpacity(0.50),
                ],
              ),
              borderRadius: BorderRadius.circular(borderRadius),
              border: Border.all(
                color: AppColors.border.withOpacity(0.7),
                width: 0.8,
              ),
              boxShadow: [
                BoxShadow(
                  color: Colors.black.withOpacity(0.03),
                  blurRadius: 12,
                  offset: const Offset(0, 4),
                ),
              ],
            ),
            child: child,
          ),
        ),
      ),
    );
  }
}
```

---

### 2. `EditorialHeader` (Page Title Block)
Standardized title block with uppercase category eyebrow, expressive serif title, and optional trailing action button.

```dart
// lib/core/widgets/editorial_header.dart
import 'package:flutter/material.dart';
import '../constants/app_typography.dart';

class EditorialHeader extends StatelessWidget {
  final String? eyebrow;
  final String title;
  final String? description;
  final Widget? trailingAction;

  const EditorialHeader({
    Key? key,
    this.eyebrow,
    required this.title,
    this.description,
    this.trailingAction,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 12),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                if (eyebrow != null) ...[
                  Text(eyebrow!.toUpperCase(), style: AppTypography.eyebrow),
                  const SizedBox(height: 4),
                ],
                Text(title, style: AppTypography.displayLarge),
                if (description != null) ...[
                  const SizedBox(height: 4),
                  Text(description!, style: AppTypography.bodyMedium),
                ],
              ],
            ),
          ),
          if (trailingAction != null) trailingAction!,
        ],
      ),
    );
  }
}
```

---

### 3. `PillChip` (Interactive Filter & Selection Chip)
Used for category filtering, occasion selection, and status tags.

```dart
// lib/core/widgets/pill_chip.dart
import 'package:flutter/material.dart';
import '../constants/app_colors.dart';
import '../constants/app_typography.dart';

class PillChip extends StatelessWidget {
  final String label;
  final bool isSelected;
  final VoidCallback onTap;
  final Widget? leadingIcon;

  const PillChip({
    Key? key,
    required this.label,
    required this.isSelected,
    required this.onTap,
    this.leadingIcon,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: onTap,
      child: AnimatedContainer(
        duration: const Duration(milliseconds: 180),
        padding: const EdgeInsets.symmetric(horizontal: 14, vertical: 8),
        decoration: BoxDecoration(
          color: isSelected ? AppColors.textPrimary : AppColors.surface,
          borderRadius: BorderRadius.circular(20),
          border: Border.all(
            color: isSelected ? AppColors.textPrimary : AppColors.border,
            width: 0.8,
          ),
          boxShadow: isSelected
              ? [
                  BoxShadow(
                    color: Colors.black.withOpacity(0.08),
                    blurRadius: 6,
                    offset: const Offset(0, 2),
                  )
                ]
              : null,
        ),
        child: Row(
          mainAxisSize: MainAxisSize.min,
          children: [
            if (leadingIcon != null) ...[
              leadingIcon!,
              const SizedBox(width: 6),
            ],
            Text(
              label,
              style: AppTypography.bodyMedium.copyWith(
                color: isSelected ? AppColors.textInverse : AppColors.textPrimary,
                fontWeight: isSelected ? FontWeight.w600 : FontWeight.w500,
                fontSize: 13,
              ),
            ),
          ],
        ),
      ),
    );
  }
}
```

---

### 4. `StockStatusBadge` (Inventory Health Indicator)
Visual badge reflecting PRD low-stock rules:
- **In stock:** Quantity > 5 $\rightarrow$ Emerald
- **Low stock:** Quantity between 1 and 5 (Threshold: <= 2) $\rightarrow$ Amber
- **Sold out:** Quantity = 0 $\rightarrow$ Red

```dart
// lib/core/widgets/stock_status_badge.dart
import 'package:flutter/material.dart';
import '../constants/app_colors.dart';
import '../constants/app_typography.dart';

class StockStatusBadge extends StatelessWidget {
  final int quantity;

  const StockStatusBadge({Key? key, required this.quantity}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Color bg;
    Color fg;
    String label;

    if (quantity == 0) {
      bg = AppColors.dangerLight;
      fg = AppColors.danger;
      label = 'Sold out';
    } else if (quantity <= 2) {
      bg = AppColors.warningLight;
      fg = AppColors.warning;
      label = 'Low ($quantity left)';
    } else {
      bg = AppColors.successLight;
      fg = AppColors.success;
      label = '$quantity in stock';
    }

    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 4),
      decoration: BoxDecoration(
        color: bg,
        borderRadius: BorderRadius.circular(8),
      ),
      child: Text(
        label,
        style: AppTypography.bodySmall.copyWith(
          color: fg,
          fontWeight: FontWeight.w600,
          fontSize: 11,
        ),
      ),
    );
  }
}
```

---

## 6. Micro-Interactions & Animation Guidelines

1. **Category Chip Selection:** 180ms smooth color tween with subtle scale feedback on tap.
2. **Scanner Viewfinder Laser:** Continuous vertical sine sweep across the viewfinder bounding box using an `AnimationController` (duration: 2000ms, curve: `Curves.easeInOut`).
3. **Card Expansion & Drawers:** Spring easing for modal bottom sheets (`CupertinoModalPopup` physics) when presenting the "Pair with what I own" closet combination view.
4. **Pull-to-Refresh:** Clean charcoal spinner matching `AppColors.textPrimary` with haptic feedback (`HapticFeedback.lightImpact()`).
5. **Instant Toast SnackBar:** Floating ivory glass toast with cobalt border positioned at the top of the viewport for transaction confirmations.
