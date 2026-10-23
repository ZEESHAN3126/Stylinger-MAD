# Stylinger Consolidated Screen Inventory & User Flows

**Author:** Antigravity Architecture Team  
**Document Version:** 1.0.0 — Screen & Flow Specification  
**Target Platform:** Flutter Mobile Application  
**Date:** September 2026  

---

## 1. Screen Validation & Mobile Consolidation Rationale

The web application contains **23 distinct client-facing routes** primarily driven by desktop layouts and URL state separation. Blindly translating every desktop web route into a standalone mobile screen would lead to high navigation fragmentation, awkward user journeys, and poor usability.

Following validation against the original PRD (§3, §4, §10, §11) and the web forensic audit, the screen architecture is **consolidated into 12 high-utility, context-rich mobile screens** (2 Shared, 5 Merchant Mode, 5 Customer Mode):

```
┌────────────────────────────────────────────────────────────────────────┐
│               CONSOLIDATED MOBILE SCREEN ARCHITECTURE                  │
├───────────────────────────────────┬────────────────────────────────────┤
│ SHARED & AUTH (2 Screens)         │ • S-01: Welcome & Auth Screen      │
│                                   │ • S-02: Mode Switcher Modal Sheet  │
├───────────────────────────────────┼────────────────────────────────────┤
│ MERCHANT MODE (5 Screens)         │ • M-01: Dashboard & Analytics      │
│ (Store Management & Inventory)    │ • M-02: Inventory Manager          │
│                                   │ • M-03: Product Add/Edit Modal     │
│                                   │ • M-04: QR Generator & Print View  │
│                                   │ • M-05: Store Settings & Profile   │
├───────────────────────────────────┼────────────────────────────────────┤
│ CUSTOMER MODE (5 Screens)         │ • C-01: Home & Daily Outfit Hero   │
│ (Shopper Closet & AI Stylist)     │ • C-02: Digital Wardrobe Grid      │
│                                   │ • C-03: In-Store QR Scanner        │
│                                   │ • C-04: In-Store AI Stylist View   │
│                                   │ • C-05: Boutique Discovery         │
│                                   │ • C-06: Profile, Style DNA & Ledger│
└───────────────────────────────────┴────────────────────────────────────┘
```

---

## 2. Four-Tier Screen Validation Matrix

| Mobile Screen Code | Screen Name & Purpose | PRODUCT REQUIREMENT (PRD v2.0) | CURRENT WEBSITE IMPLEMENTATION | MOBILE MVP TARGET (Consolidated) | FUTURE INTEGRATION |
|---|---|---|---|---|---|
| **S-01** | **Welcome & Auth** | Passwordless phone OTP onboarding. | 5 web routes: `/login`, `/register`, `/forgot-password`, `/reset-password`, `/verify-email`. | **Consolidated Single Screen:** Luxury brand hero with one-tap demo persona login ("Merchant" vs "Customer"). | Phone SMS OTP verification flow. |
| **S-02** | **Mode Switcher** | Dynamic dual-mode account switching via profile menu. | Hardcoded route jumping between `/merchant` and `/app`. | **Bottom Sheet Modal:** Allows instant toggle between Merchant and Customer modes. | Biometric role authentication gate. |
| **M-01** | **Merchant Dashboard** | 4A Sales overview, 4B Best seller intelligence, 4D Automated reports. | 3 web routes: `/merchant`, `/merchant/analytics`, `/merchant/reports`. | **Consolidated Dashboard Screen:** Summary KPI cards, stock status chart, recent sales ledger, and AI trend chips. | Automated 9 PM nightly push digest. |
| **M-02** | **Inventory Manager** | Real-time stock decrement, low stock alert rules, search, filters. | `/merchant/inventory` (table view, search, category filter, stock badges). | **Mobile Product List View:** Search bar, category filter chips, stock status pill badges, and quick quantity steppers. | Bluetooth barcode scanner hardware. |
| **M-03** | **Product Add/Edit** | Real-time item upload with photo, SKU, category, price, and sizes. | 2 web routes: `/merchant/products/new` and `/merchant/products/$id/edit`. | **Consolidated Bottom Sheet Form:** Camera/gallery picker mockup, category selector, price input, size chips. | Vision AI auto-tagging API on photo select. |
| **M-04** | **QR Generator & Print** | Store entrance QR + Garment SKU tags; A4/A5/sticker PDF export. | `/merchant/qr` (mathematical SVG preview; PDF export disabled). | **QR Generator Screen:** Clean vector QR for store entrance and selected product SKUs with print preview. | Vector PDF document exporter & print spooler. |
| **M-05** | **Store Settings** | Store ID `STY-XXXX`, operating hours, payment methods, address. | `/merchant/settings` (bio, address, map toggle, delete account). | **Store Profile & Settings:** Manage boutique info, address, low-stock threshold, and notification preferences. | Native GPS location autofill. |
| **C-01** | **Customer Home Feed** | Daily morning outfit suggestion, occasion input, weather chip. | 2 web routes: `/app` (dashboard) and `/app/daily` (planner). | **Consolidated Home Feed:** "Today's Look" card, occasion selector, weather simulation, and quick QR scan button. | OpenWeatherMap live API sync. |
| **C-02** | **Digital Wardrobe Grid** | Category filters, wear count, 'unworn' tags, auto-built pieces. | `/app/wardrobe` (8-item grid, wear counts, category tabs). | **Wardrobe Screen:** 2-column garment grid, wear counter chips, "Recently Scanned & Bought" rail, and quick-add modal. | Vision AI wardrobe photo analysis. |
| **C-03** | **In-Store QR Scanner** | Scan store entrance QR code to launch in-store stylist. | `/app/scanner` (animated CSS crosshairs + manual SKU input). | **Camera Viewfinder Screen:** Simulated laser sweep, quick simulation buttons, and manual deep-link input drawer. | Camera hardware stream via `mobile_scanner`. |
| **C-04** | **In-Store AI Stylist** | Ranked inventory display, "Pair with what I own", "Honest Mode". | No web equivalent (suggestions route was static hardcoded cards). | **Dedicated In-Store Experience:** Scanned boutique banner, ranked items matching Style DNA, "Pair with what I own" drawer, Honest Mode notice. | Real-time 3D generative avatar fitting. |
| **C-05** | **Boutique Discovery** | Map view & store list in city, distance, category filters. | `/app/stores` (static graphic map with 4 Mumbai boutiques). | **Vadodara Boutique Directory:** Searchable list of partner boutiques (Alkapuri, Mandvi), distance badges, and open stylist CTA. | Interactive Google Maps / Mapbox map. |
| **C-06** | **Profile, Style DNA & Ledger**| Body/face analysis, Monk skin tone, purchase history, settings. | 4 web routes: `/app/profile`, `/app/orders`, `/app/notifications`, `/app/settings`. | **Consolidated Profile Hub:** User style persona, interactive Style DNA sliders (body/skin tone picker), purchase history, and toggles. | MediaPipe ML photo inference pipeline. |

---

## 3. Screen Specifications & Layout Hierarchy

### Screen M-01: Merchant Dashboard & Analytics
- **Layout & Structure:**
  - **Header:** Editorial greeting ("Aura Atelier, Vadodara") + Notification Bell icon.
  - **KPI Summary Row:** 4 luxury metric cards:
    - *Today's Footfall:* "18 Scans" (+12% vs yesterday).
    - *Today's Revenue:* "₹14,500" (4 items sold).
    - *Low Stock Alert:* "2 items need reorder" (Amber pill).
    - *Catalog Health:* "30 active pieces".
  - **AI Trend Intelligence Card:** Curated Vadodara fashion signal ("Navratri ethnic wear demand +40% in Alkapuri; 2 kurta lines low on stock").
  - **Category Stock Breakdown:** Horizontal segmented bar showing category distribution (Shirts, Outerwear, Trousers, Ethnic).
  - **Recent Proof of Sale Log:** List of last 3 simulated transactions with timestamp, item name, and amount.
- **State Variations:**
  - *Normal:* Displays live local statistics computed from active catalog.
  - *Empty:* If catalog has 0 items, prompts to add initial stock.

---

### Screen M-02: Inventory Manager
- **Layout & Structure:**
  - **Top Bar:** Search input field (filters by garment title or SKU) + Category filter chip bar (All, Shirts, Outerwear, Trousers, Dresses, Knitwear, Accessories).
  - **Floating Action Button:** Prominent "+ Add Product" button in Deep Cobalt (`#2457D6`).
  - **Product List Items:** Responsive cards containing:
    - Garment thumbnail image (Unsplash fashion photo).
    - Title, category, and SKU (`#STY-004`).
    - Price in Indian Rupees (`₹3,490`).
    - `StockStatusBadge`: Emerald for >5, Amber for <=2, Red for 0.
    - Quick quantity decrement/increment buttons (`-` and `+`).
- **State Variations:**
  - *Filtered Empty:* Displays `EmptyStateView` ("No garments match 'Blazer' in Outerwear").
  - *Zero Inventory:* Prompts user to tap "+" to seed or create products.

---

### Screen M-04: QR Code Generator & Print View
- **Layout & Structure:**
  - **Segmented Control:** Switch between "Store Entrance QR" and "Product SKU Tag".
  - **QR Display Card:** High-contrast vector QR code rendered on white surface with rounded luxury corners, Stylinger brand logo in center, and "Scan for AI Style Advice" text banner.
  - **Metadata Summary:** Store ID (`STY-VAD-01`), dynamic session indicator ("Valid for 24 hours"), deep link URL.
  - **Print / Share Actions:**
    - "Simulate Customer Scan" (Directly launches Customer In-Store Stylist view).
    - "Share QR Image" / "Export PDF" (UI feedback toast).

---

### Screen C-01: Customer Home Feed & Daily Outfit Hero
- **Layout & Structure:**
  - **Header:** Date in small caps ("SATURDAY, 12 SEPTEMBER") + Greeting ("Good morning, Ananya") + Mode Switcher chip.
  - **Weather & Occasion Bar:** Weather badge ("28° · Sunny, Vadodara") + Occasion pill selector (Casual, Office, Evening, Festive).
  - **Hero Outfit Card ("Today's Curated Look"):**
    - High-res photo assembly of 3 matching wardrobe pieces (e.g., Linen Blazer + Raw Denim Jeans + Leather Loafers).
    - Outfit compatibility score badge ("96% Match for your Warm undertone").
    - Action buttons: "Wear It Today" (increments wear counters) and "Shuffle Pieces".
  - **"Missing Accent" Boutique Upsell:** "Complete this look with an Italian Leather Belt from Aura Atelier (1.2 km away)".
  - **Floating QR Scanner Button:** Deep cobalt floating pill: "Scan Store QR in Boutique".

---

### Screen C-02: Digital Wardrobe Grid
- **Layout & Structure:**
  - **Header:** "My Wardrobe" (Editorial Serif) + Total garment count ("8 pieces in closet") + "+ Add Piece" button.
  - **Category Tabs:** All, Tops, Bottoms, Outerwear, Footwear, Ethnic.
  - **"Recently Scanned & Bought" Carousel:** Horizontal thumbnail scroll of garments purchased in-store via Stylinger QR code.
  - **Garment Grid (2 Columns):**
    - High-res product photography.
    - Category label and garment name.
    - Wear counter chip ("Worn 8×").
    - Origin source tag: `Auto QR` (emerald badge) vs `Manual` (gray badge).
- **State Variations:**
  - *Empty Category:* Displays "No outerwear pieces in your closet yet. Scan garments in boutique partner stores to auto-build your wardrobe."

---

### Screen C-03: In-Store QR Scanner Screen
- **Layout & Structure:**
  - **Fullscreen Viewfinder:** Dark luxury frame (`#111419` backdrop) with transparent central scanning viewport and rounded corner reticles.
  - **Animated Laser Sweep:** Continuous vertical line moving up and down within the viewport.
  - **Flashlight & Camera Flip Toggles:** Circular glass buttons in top bar.
  - **Rapid Simulation Bar (For Testing & Emulators):**
    - Chip 1: "Simulate Scan: Aura Atelier (Store QR)"
    - Chip 2: "Simulate Scan: Linen Blazer (Item Tag)"
    - Chip 3: "Simulate Scan: Unmatched Store (Honest Mode)"
  - **Manual SKU Drawer:** Slide-up bottom sheet allowing manual entry of store code (`STY-VAD-01`) or garment SKU (`#STY-004`).

---

### Screen C-04: In-Store AI Stylist View
- **Layout & Structure:**
  - **Store Banner:** "Aura Atelier — Alkapuri, Vadodara" + Verified boutique badge + Accepted payment chips (UPI, Card, Cash).
  - **Style DNA Matching Banner:** "Scored against your Style DNA: Hourglass Silhouette · Warm Medium Skin Undertone".
  - **Budget Ceiling Slider:** "Show items under ₹5,000".
  - **"Honest Mode" Notice (Conditional):** If less than 3 pieces suit the customer, displays a respectful editorial alert:
    *"We found only 2 items in Aura Atelier that flatter your specific color palette today. We believe in honest recommendations — tap here to explore 3 partner boutiques nearby."*
  - **Ranked In-Store Recommendations List:**
    - Item card with match score ("94% Suitability").
    - "Pair with What I Own" button: Opens bottom drawer showing how this store item pairs with 2 items currently in the customer's digital wardrobe!
    - "Purchase & Add to Wardrobe" button: Triggers the atomic S2 transaction, decrements store stock, and injects the piece into customer's wardrobe.

---

### Screen C-06: Customer Profile, Style DNA & Purchase History
- **Layout & Structure:**
  - **Member Card:** Avatar, member name ("Ananya Vahora"), City ("Vadodara, Gujarat"), and "Switch to Merchant Mode" button.
  - **Interactive Style DNA Editor:**
    - *Body Silhouette:* Horizontal selector (Rectangle, Hourglass, Pear, Apple, Inverted Triangle).
    - *Monk Skin Tone Picker:* 10-swatch circular palette displaying Monk Skin Tone Scale (MST 1–10) with detected undertone label (Warm / Cool / Neutral).
  - **Purchase History Ledger:** Expandable list of all past in-store Stylinger purchases showing date, boutique name, size, price, and status ("Delivered / Wardrobe Synced").
  - **Settings & Toggles:** "In-Store Scan Alerts", "Honest Mode Transparency", "Clear Local Cache".

---

## 4. Primary User Journey Flowcharts

### Journey 1: The Core In-Store Ingestion Loop (Scan $\rightarrow$ Match $\rightarrow$ Buy $\rightarrow$ Auto-Wardrobe)

```
Customer enters Boutique (Aura Atelier)
                   │
                   ▼
Opens Stylinger & Taps "Scan Store QR" (C-03)
                   │
                   ▼
Viewfinder scans entrance QR: "stylinger://store/STY-VAD-01"
                   │
                   ▼
App navigates to In-Store AI Stylist (C-04)
                   │
                   ├─► Store inventory filtered by customer's Style DNA
                   ├─► Ranked recommendations displayed with match scores
                   └─► Customer taps "Pair with what I own" to see outfit synergy
                   │
                   ▼
Customer selects Linen Blazer (Size M) & Taps "Purchase & Sync"
                   │
                   ▼
PurchaseSyncCoordinator executes Atomic S2 Transaction:
  1. Decrements Aura Atelier stock by 1
  2. Auto-injects Linen Blazer into Customer Digital Wardrobe (C-02)
  3. Appends transaction to Customer Ledger (C-06) & Merchant Sales Log (M-01)
  4. Dispatches in-app confirmation toast
                   │
                   ▼
Customer opens Digital Wardrobe (C-02)
  └── Linen Blazer is immediately visible with "Auto QR" emerald badge!
```

---

### Journey 2: Merchant 5-Minute Inventory Onboarding & Stock Audit

```
Merchant opens Stylinger in Merchant Mode (M-01)
                   │
                   ▼
Navigates to Inventory Manager (M-02)
  └── Pre-loaded with 30 luxury fashion items
                   │
                   ▼
Merchant taps "+ Add Product" (M-03)
  ├── Enters title: "Festive Embroidered Kurta"
  ├── Selects Category: "Ethnic"
  ├── Sets Price: "₹2,890" and Stock: "3"
  └── Taps "Save Garment"
                   │
                   ▼
Item appears in inventory list with "Low (3 left)" status
                   │
                   ▼
Merchant navigates to QR Tag Generator (M-04)
  └── Selects "Festive Embroidered Kurta" to generate vector QR tag
```
