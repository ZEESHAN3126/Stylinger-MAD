# Stylinger Mobile Development Roadmap & Sprint Plan

**Author:** Antigravity Architecture Team  
**Document Version:** 1.0.0 — Sprint Plan Edition  
**Target Platform:** Flutter Mobile Application (iOS & Android)  
**Date:** September 2026  

---

## 1. Roadmap Architecture: Pilot Phasing Strategy

Stylinger's product rollout follows a strictly sequenced, merchant-first trajectory as mandated by PRD §2.1 and §10:

```
┌────────────────────────────────────────────────────────────────────────┐
│                        STYLINGER ROADMAP PHASES                        │
├───────────────────┬────────────────────────────────────────────────────┤
│ PHASE 1: MVP      │ • Vadodara Pilot: 10 independent boutique retailers│
│ (Sprints 0 – 5)   │ • 1,000 initial shoppers (Alkapuri, Mandvi)        │
│                   │ • Modules: M1, M2, M3, M6 (local), U1, U3, U5, S1, │
│                   │   S2 (atomic coordinator), S3 (local rules)        │
├───────────────────┼────────────────────────────────────────────────────┤
│ PHASE 2: GROWTH   │ • Expansion to Surat & Ahmedabad (500 merchants)   │
│                   │ • M4 (Full analytics), M5 (AI trend intelligence)  │
│                   │ • U4 (Full daily planner), M7 (FCM alerts), S5 map │
├───────────────────┼────────────────────────────────────────────────────┤
│ PHASE 3: SCALE    │ • Mall & franchise B2B SaaS across Pan-Gujarat     │
│                   │ • Fine-tuned ML vision models & 3D avatar fitting  │
└───────────────────┴────────────────────────────────────────────────────┘
```

---

## 2. Four-Tier Phase 1 MVP Mapping

| Development Track | PRODUCT REQUIREMENT (PRD v2.0) | CURRENT WEBSITE IMPLEMENTATION | MOBILE MVP TARGET (Sprints 0–5) | FUTURE INTEGRATION (Phase 2+) |
|---|---|---|---|---|
| **Auth & Shell** | Single phone number login with instant dual-mode switching. | Supabase GoTrue Auth (Email & Password). Status: **`REAL`**. | Single Flutter App with `ModeController` and one-tap persona switcher. | Supabase Phone SMS OTP verification. |
| **Merchant Inventory** | Real-time stock decrement, low stock alert rules, search, filters. | `localStorage` (30 products) + TanStack Query. Status: **`REAL`** *(Local)*. | High-performance mobile product grid with 30 seeded items, search, and stock badges. | Supabase PostgreSQL PostgREST synchronization. |
| **QR Code Bridge** | Store entrance deep link (`stylinger://store/...`) with 24h rotating token. | Mathematical SVG preview grid. Status: **`PLACEHOLDER`**. | High-contrast vector QR code generation (`qr_flutter`) + simulated camera scanner. | Dynamic rotating token salt + native PDF print sheets. |
| **Customer Wardrobe** | Auto-builds from QR purchases in-store (manual upload optional). | Static array `wardrobeItems` (8 items). Status: **`MOCK`**. | Dynamic in-memory wardrobe closet updated instantly upon simulated in-store checkout. | Multimodal Vision AI auto-tagging. |
| **AI Stylist Matching**| In-store inventory scored against customer's Style DNA. | Static outfit cards in `mock-data.ts`. Status: **`MOCK`**. | Deterministic local rules engine matching Monk skin tones, silhouettes, and owned items. | Python FastAPI neural compatibility endpoints. |
| **Atomic Sync Flow** | S2 purchase decrements stock, updates wardrobe, and logs receipt. | Not implemented on web. Status: **`FUTURE`**. | `PurchaseSyncCoordinator`: Synchronous atomic coordinator with rollback semantics. | Cloud ACID transaction with Redis lock. |

---

## 3. Granular 6-Sprint Implementation Plan

```
Sprint 0 ────────► Sprint 1 ────────► Sprint 2 ────────► Sprint 3 ────────► Sprint 4 ────────► Sprint 5
Setup & UI         Auth & Dual Shell  Merchant Catalog   QR Engine & Scan   Wardrobe & S2 Sync  Dashboard & Pilot
```

---

### Sprint 0: Foundation, Architecture & "Editorial Ivory" Design System
- **Focus:** Flutter project initialization, core constants, design tokens, and reusable primitives.
- **Tasks:**
  1. Initialize standard Flutter project (`stylinger_mobile`) targeting Android (SDK 34) and iOS (17+).
  2. Implement `AppColors` containing the complete "Editorial Ivory" palette (`#F6F4EF` canvas, `#2457D6` cobalt, `#17191C` charcoal, Monk skin tone swatches).
  3. Configure `AppTypography` pairing *Instrument Serif* and *Inter* via `google_fonts`.
  4. Build core reusable primitives: `LiquidGlassCard` (with `BackdropFilter`), `EditorialHeader`, `PillChip`, `StylingerButton`, and `StockStatusBadge`.
  5. Define all abstract repository contracts (`IInventoryRepository`, `IWardrobeRepository`, `IStylingEngine`, etc.).
- **Deliverables & Acceptance Criteria:**
  - `test/design_system_test.dart` passes.
  - Golden test verifies pixel-perfect contrast and glassmorphic blur rendering on light canvas.

---

### Sprint 1: Dual-Mode Shell & Persona Switcher
- **Focus:** Mode routing architecture (`AppMode.customer` vs `AppMode.merchant`), root scaffold, and navigation bars.
- **Tasks:**
  1. Implement `ModeController` (`ChangeNotifier`) to manage the active application persona.
  2. Build `CustomerShell` containing the 5-tab bottom navigation bar (Home, Wardrobe, Scanner, Boutiques, Profile).
  3. Build `MerchantShell` containing the 5-tab bottom navigation bar (Dashboard, Inventory, QR, Analytics, Settings).
  4. Build `WelcomeScreen` (`S-01`) featuring luxury brand hero visual and instant one-tap persona buttons:
     - *"Enter as Boutique Owner (Aura Atelier, Vadodara)"*
     - *"Enter as Fashion Shopper (Ananya, Vadodara)"*
  5. Embed the `ModeSwitcher` modal sheet (`S-02`) allowing seamless runtime toggling.
- **Deliverables & Acceptance Criteria:**
  - Tapping persona immediately mounts the respective mode shell with zero screen flicker.
  - App state toggles smoothly between Merchant and Customer trees.

---

### Sprint 2: Merchant Inventory Slice (Module M2)
- **Focus:** In-memory catalog store, search, filtering, and product CRUD.
- **Tasks:**
  1. Implement `MockInventoryRepository` initialized with the 30 seeded luxury fashion catalog items.
  2. Build `InventoryScreen` (`M-02`) with real-time text search and category filter chips.
  3. Implement `StockStatusBadge` reflecting stock velocity (Emerald >5, Amber <=2, Red 0).
  4. Build `ProductEditScreen` (`M-03`) modal sheet allowing stock adjustment, price edits, and size selections.
  5. Wire `InventoryController` to update stock levels with instant UI recalculation.
- **Deliverables & Acceptance Criteria:**
  - Searching "Blazer" filters the catalog in <16ms.
  - Decrementing stock to <= 2 units dynamically changes the badge to Amber ("Low: 2 left").
  - Unit tests verify all inventory filtering and stock decrement logic.

---

### Sprint 3: QR Generation & In-Store Viewfinder Bridge (M3 & U5)
- **Focus:** Vector QR generation, camera simulation, and deep-link routing.
- **Tasks:**
  1. Build `QrGeneratorScreen` (`M-04`) generating scannable vector QR codes for the store entrance deep link (`stylinger://store/STY-VAD-01`) and product SKU tags using `qr_flutter`.
  2. Build `InStoreScannerScreen` (`C-03`) featuring the luxury dark frame, animated vertical laser sweep, and camera reticles.
  3. Implement `QrParser` utility resolving deep links into typed action payloads.
  4. Add the rapid simulation test bar:
     - *"Simulate Entrance Scan: Aura Atelier"*
     - *"Simulate Garment Scan: Linen Blazer"*
     - *"Simulate Mismatched Store (Honest Mode)"*
- **Deliverables & Acceptance Criteria:**
  - Tapping "Simulate Entrance Scan" immediately parses the URL and pushes `InStoreStylistScreen` with the boutique context.

---

### Sprint 4: Digital Wardrobe & The Critical S2 Atomic Purchase Sync (U3, U4 & S2)
- **Focus:** Digital closet management, daily outfit hero, and atomic transaction execution.
- **Tasks:**
  1. Implement `MockWardrobeRepository` pre-loaded with the 8 seeded wardrobe items.
  2. Build `WardrobeScreen` (`C-02`) with 2-column grid, wear count chips, and "Recently Scanned & Bought" rail.
  3. Build `CustomerHomeScreen` (`C-01`) featuring "Today's Curated Look" hero card and occasion selector.
  4. Build `InStoreStylistScreen` (`C-04`) showing ranked boutique items, "Pair with what I own" bottom sheet, and "Purchase & Add to Wardrobe" button.
  5. Implement `PurchaseSyncCoordinator`:
     - Decrements merchant stock by 1.
     - Auto-synthesizes wardrobe piece and injects it into `MockWardrobeRepository`.
     - Logs order in customer and merchant ledgers.
     - Reverts all state if stock is sold out.
- **Deliverables & Acceptance Criteria:**
  - Completing a purchase in the In-Store Stylist view immediately adds the garment to the Digital Wardrobe with an "Auto QR" emerald badge.
  - Merchant inventory count for that item decreases by exactly 1.
  - Automated unit test `test/purchase_atomic_sync_test.dart` passes.

---

### Sprint 5: Merchant Analytics, Boutique Directory & Vadodara Pilot Packaging (M4, S5 & Hardening)
- **Focus:** Business metrics, store discovery, profile DNA editing, and release packaging.
- **Tasks:**
  1. Build `MerchantDashboardScreen` (`M-01`) displaying KPI summary cards, category stock health bar, and recent proof-of-sale logs.
  2. Implement `AI Trend Intelligence` card with curated Vadodara retail signals.
  3. Build `BoutiqueDiscoveryScreen` (`C-05`) listing Vadodara boutiques (Alkapuri, Mandvi) with distance badges and category chips.
  4. Build `CustomerProfileScreen` (`C-06`) with interactive Style DNA sliders (body silhouette, Monk skin tone swatch picker) and purchase history ledger.
  5. Complete end-to-end user journey audits across both Customer and Merchant trees.
- **Deliverables & Acceptance Criteria:**
  - 100% of the 12 consolidated screens are fully interactive and navigable.
  - Zero crashes, zero runtime network errors, zero external API keys required.
  - Standalone release APK and iOS build targets generated.

---

## 4. Quality Assurance & Automated Testing Strategy

```
┌────────────────────────────────────────────────────────────────────────┐
│                        TEST AUTOMATION PYRAMID                         │
├────────────────────────────────────────────────────────────────────────┤
│ Unit Tests (Target: 85%+ Core Logic Coverage):                         │
│ • inventory_logic_test.dart: Stock decrements, filters, low-stock rules │
│ • styling_engine_test.dart: Skin undertone & silhouette scoring matrix │
│ • purchase_atomic_sync_test.dart: S2 rollback and auto-wardrobe inject │
├────────────────────────────────────────────────────────────────────────┤
│ Widget & Flow Tests:                                                   │
│ • dual_mode_switch_test.dart: Seamless switching between user trees   │
│ • in_store_scan_flow_test.dart: Scan -> Match -> Buy -> Closet verify │
├────────────────────────────────────────────────────────────────────────┤
│ Golden UI Snapshot Tests:                                              │
│ • Verifies "Editorial Ivory" canvas, Liquid Glass cards, and badges    │
└────────────────────────────────────────────────────────────────────────┘
```

---

## 5. Definition of Done (DoD) for Phase 1 Mobile Release

A sprint task or feature is officially marked **Done** only when:
1. **Zero External Breakage:** The code runs completely offline on fresh simulator clones with no network access.
2. **Interface Adherence:** The UI layer references only abstract repository interfaces (`IInventoryRepository`, etc.).
3. **Design System Fidelity:** All colors, typography, borders, and materials strictly adhere to `AppColors` and `AppTypography` without hardcoded ad-hoc styles.
4. **Deterministic Behavior:** Core mechanics (Auto-Wardrobe, Low-Stock threshold <= 2, Honest Mode < 3 matches) operate reliably without exceptions.
5. **Clean Architecture:** No cross-module coupling or circular imports between feature folders.
