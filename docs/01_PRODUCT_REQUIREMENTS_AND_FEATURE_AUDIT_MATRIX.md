# Stylinger Product Requirements & Feature Audit Matrix

**Author:** Antigravity Architecture Team  
**Document Version:** 1.0.0 — Matrix Edition  
**Target Platform:** Flutter Mobile Application  
**Date:** September 2026  
**Authoritative Sources:**  
- **Intended Product Truth:** `pre development v1.pdf` (PRD v2.0 Merchant-First Edition)  
- **Existing Implementation Truth:** `WEBSITE_CURRENT_STATE.md` (Web Forensic Audit)  

---

## 1. Architectural Reconciliation & Foundational Decisions

Before analyzing individual feature modules, the following foundational discrepancies between the PRD specification and the existing website implementation are resolved for the mobile application:

### Discrepancy 1: Authentication Engine (Phone OTP vs. Supabase Email/Password)
- **PRD Intended Truth:** Passwordless phone number authentication via OTP. Single phone number can own both a Customer profile and a Merchant store profile, toggled via in-app menu.
- **Website Implementation Truth:** Supabase Auth (`@supabase/supabase-js`) using email and password with GoTrue session management, email verification notices, and password reset tokens.
- **Mobile Resolution:**
  - `PRODUCT REQUIREMENT`: Passwordless phone OTP with single-number dual-mode switching.
  - `CURRENT WEBSITE IMPLEMENTATION`: Email & password authentication with Supabase GoTrue. Status: **`REAL`**.
  - `MOBILE MVP TARGET`: In-memory mock authentication layer supporting both guest preview and instant mock login for both `'merchant'` and `'customer'` personas without network calls or OTP SMS gateways. Abstract `IAuthRepository` provides the interface contract.
  - `FUTURE INTEGRATION`: Plug in Supabase Phone OTP or Twilio/Msg91 SMS gateway to fulfill the PRD vision in Phase 2.

### Discrepancy 2: Geographic Pilot Market (Vadodara/Surat vs. Mumbai)
- **PRD Intended Truth:** Vadodara as the Phase 1 pilot city (10 merchants, 1,000 users), expanding to Surat, Ahmedabad, and Pan-Gujarat in Phase 2. Specific retail dynamics: independent boutiques, festive shopping (Navratri/Diwali), local fitting habits.
- **Website Implementation Truth:** Static mock boutique data localized to upscale Mumbai neighborhoods (Bandra West, Colaba, Khar).
- **Mobile Resolution:**
  - `PRODUCT REQUIREMENT`: Vadodara Tier-2 fashion merchant ecosystem.
  - `CURRENT WEBSITE IMPLEMENTATION`: Mock Mumbai locations in static arrays. Status: **`MOCK`**.
  - `MOBILE MVP TARGET`: Configure local mock data around Vadodara retail hubs (Alkapuri, Mandvi, Sayajiganj) to preserve the PRD's strategic pilot truth, while retaining city-selectable architecture.
  - `FUTURE INTEGRATION`: Cloud GPS location services filtering stores within dynamic radius.

### Discrepancy 3: QR Code Architecture (Store Entrance vs. Garment SKU Tag)
- **PRD Intended Truth:** Primary QR code is placed at the **store entrance** (`stylinger://store/{store_id}?session={token}`). Scanning opens the store's entire live inventory filtered by the customer's personal AI stylist. The session token rotates every 24 hours for security while the visual QR remains constant.
- **Website Implementation Truth:** Product-level QR generator creating visual QR tiles for individual catalog items/SKUs.
- **Mobile Resolution:**
  - `PRODUCT REQUIREMENT`: Store entrance QR connecting merchant inventory to personal AI stylist.
  - `CURRENT WEBSITE IMPLEMENTATION`: Mathematical procedural SVG grid for individual product SKUs. Status: **`PLACEHOLDER`**.
  - `MOBILE MVP TARGET`: Support **both** QR tiers:
    1. *Store Entrance QR:* Encodes `stylinger://store/{store_id}` to trigger the in-store styling experience.
    2. *Garment Tag QR:* Encodes `stylinger://item/{store_id}/{item_id}` for instant garment detail and "Pair with what I own" view.
  - `FUTURE INTEGRATION`: Backend 24-hour rotating token cryptography and print-ready PDF vector export sheets.

---

## 2. Merchant Modules (M1 – M7) Audit Matrix

### M1: Merchant Onboarding & Store Setup

| Dimension | Details | Status |
|---|---|---|
| **PRODUCT REQUIREMENT** | Auto-generate unique Store ID (`STY-XXXX`). Capture store category (Men's, Women's, Both, Kids, All), physical GPS coordinates + manual address, optional operating hours, and declared payment methods (Cash, UPI, Card). | PRD §3 (M1), §4 |
| **CURRENT WEBSITE IMPLEMENTATION** | `/onboarding/role` allows choosing "merchant"; `/onboarding/waitlist` captures store name, business email, business phone, city, and stores in `profiles.preferences.waitlist`; `/merchant/settings` allows editing bio, address, founding year, and discovery toggle. | **`PARTIAL`** |
| **MOBILE MVP TARGET** | Single consolidated Store Setup screen (`M-06`). Pre-populated with default Vadodara boutique profile ("Aura Atelier, Alkapuri"). Allows editing store name, category selector chips, address, and accepted payment toggles using local state. | **`LOCAL`** |
| **FUTURE INTEGRATION** | Supabase database upsert to `public.stores`, GPS autofill via native `geolocator`, and GST document verification. | **`FUTURE`** |

---

### M2: Inventory Management System

| Dimension | Details | Status |
|---|---|---|
| **PRODUCT REQUIREMENT** | Digital inventory replacing paper registers and memory. Real-time stock decrement on purchase. Manual stock adjustment (for non-Stylinger sales, returns, damaged items). Recalculation of analytics. Bulk CSV upload. Low stock alert rules (default threshold: 2 pieces). Proof of sale log with PDF/CSV export. | PRD §3 (M2), §4 |
| **CURRENT WEBSITE IMPLEMENTATION** | Fully functional client-side inventory catalog at `/merchant/inventory`, `/merchant/products/new`, and `/merchant/products/$id/edit`. Backed by 30 seeded luxury fashion pieces in `localStorage` with TanStack Query CRUD mutations, real-time search, category filters, and dynamic stock status pill badges ("In stock" >5, "Low stock" 1–5, "Sold out" 0). | **`REAL`** *(Local)* |
| **MOBILE MVP TARGET** | Mobile Inventory Screen (`M-02`) and Add/Edit Product Wizard (`M-03`). Uses typed in-memory Dart catalog pre-loaded with the 30 seeded products. Supports search by title/SKU, category filtering, stock decrement, manual quantity edits, low-stock threshold highlight (<= 2 pieces), and camera/gallery image simulation. | **`LOCAL`** |
| **FUTURE INTEGRATION** | Sync with PostgreSQL `public.products` and `public.inventory` tables via Supabase PostgREST; background CSV import parser; physical Bluetooth barcode scanner support. | **`FUTURE`** |

---

### M3: QR Code Generation & Management

| Dimension | Details | Status |
|---|---|---|
| **PRODUCT REQUIREMENT** | Unique store QR code acting as physical-digital bridge. QR Code Version 3, Error Correction Level H (30% damage tolerance). Encodes `stylinger://store/{store_id}?session={token}`. 24h rotating session token. Downloadable print formats: A4 full page, A5 half page, 10x10cm sticker. Displayed at entrance and billing counter. Multi-store support with location switcher. | PRD §3 (M3), §5 |
| **CURRENT WEBSITE IMPLEMENTATION** | `/merchant/qr` displays deterministic mathematical SVG grid tiles for each catalog product. PDF export and print buttons are disabled placeholders with roadmap notice. | **`PLACEHOLDER`** |
| **MOBILE MVP TARGET** | QR Generator Screen (`M-04`). Generates clean scannable vector QR codes using local Dart QR encoding for: (1) The Store Entrance deep link (`stylinger://store/STY-VAD-01`), and (2) Individual product SKU tags. Displays simulated print preview with Stylinger branding and "Scan for AI Style Advice" badge. | **`LOCAL`** |
| **FUTURE INTEGRATION** | Native PDF sheet generation (`pdf` / `printing` packages), dynamic 24-hour cryptographic rotating salt from backend, multi-branch store switcher. | **`FUTURE`** |

---

### M4: Sales Analytics Dashboard

| Dimension | Details | Status |
|---|---|---|
| **PRODUCT REQUIREMENT** | 4A Sales Overview (Revenue, units sold, ATV, period comparisons). 4B Best Seller Intelligence (units sold, revenue, scan-to-purchase conversion rate, size velocity, slow movers 30+ days). 4C Customer Behaviour (browsing duration after scan, anonymized demographics, peak footfall hours, repeat scan rate). 4D Automated Reports (9 PM nightly summary, Monday weekly report, monthly/quarterly BI export). Revenue metrics: Gross Margin, Basket Size, AOV, LTV, Sell-Through Rate, Dead Stock %. | PRD §3 (M4), §6–7 |
| **CURRENT WEBSITE IMPLEMENTATION** | `/merchant/analytics`, `/merchant/reports`, and `/merchant` dashboard cards. Real-time catalog breakdown computed from stored catalog (category distribution, price bands, stock health). Weekly scans and sales line chart are driven by static mock data. | **`PARTIAL`** |
| **MOBILE MVP TARGET** | Merchant Dashboard (`M-01`) & Analytics Tab (`M-05`). Displays summary KPI cards (Today's Revenue, Units Sold, Active Scans, Low Stock Alerts) and a category distribution chart computed dynamically from the active local inventory. Includes mock best-seller rankings and dead-stock warning chips (items unsold 30+ days). | **`LOCAL`** *(Catalog stats)* + **`MOCK`** *(Footfall/Sales)* |
| **FUTURE INTEGRATION** | Automated daily 9 PM push notifications, aggregation pipeline across PostgreSQL sales orders, PDF export for GST and tax accountants. | **`FUTURE`** |

---

### M5: AI Trend Intelligence Engine

| Dimension | Details | Status |
|---|---|---|
| **PRODUCT REQUIREMENT** | Predictive trend forecasting for Tier 2 shopkeepers (Vadodara & Surat). Data sources: City-wide scan/purchase data, Instagram India trending hashtags, seasonal festival patterns (Navratri floral prints 3 weeks prior). Alerts: "Current Trending", "Upcoming Trend", "What to Restock", "What to Discount" (20% off slow polo shirts), and "Competitor Insight" (40% higher QR scans for kurtas nearby). Live dashboard widget, weekly Monday push, seasonal festival alerts 4–6 weeks prior. | PRD §3 (M5), §7–8 |
| **CURRENT WEBSITE IMPLEMENTATION** | Only static alert text chips inside `/merchant/notifications` ("AI inventory insight: Outerwear is top category"). No data engine or API. | **`PLACEHOLDER`** |
| **MOBILE MVP TARGET** | Embedded "AI Trend Insights" card inside Merchant Dashboard (`M-01`). Renders realistic curated seasonal insights tailored to Gujarat retail (e.g., "Navratri ethnic wear demand surging in Vadodara; 2 kurta lines low on stock") derived from deterministic local mock rule cards. | **`MOCK`** |
| **FUTURE INTEGRATION** | Python FastAPI trend analysis microservice, Instagram Graph API hashtag ingestion, multi-tenant merchant demand pooling. | **`FUTURE`** |

---

### M6: Payments & Razorpay Integration

| Dimension | Details | Status |
|---|---|---|
| **PRODUCT REQUIREMENT** | In-app payment collection on QR scan purchase. Razorpay gateway (UPI, cards, net banking, wallets). Settlement on T+2 basis. Stylinger commission (2–5% post-launch Phase 2). Payout history and pending settlements. GST-compliant digital invoicing. Webhook API for third-party billing software (Marg ERP, Vyapar, Zoho Books). Future MIMP (Merchant Integrated Management Platform). | PRD §3 (M6), §8 |
| **CURRENT WEBSITE IMPLEMENTATION** | `/merchant/orders` renders a static 4-row HTML table with mock rupee amounts and order statuses. No payment gateway integration. | **`MOCK`** |
| **MOBILE MVP TARGET** | Merchant Orders Screen (`M-06b`, consolidated in Dashboard). Displays order history log. Includes a "Simulate Customer In-Store Checkout" button that triggers the atomic stock decrement and wardrobe sync locally without calling Razorpay or processing real money. | **`MOCK`** |
| **FUTURE INTEGRATION** | Native Razorpay Flutter SDK (`razorpay_flutter`), webhook listener microservice on backend, automatic Marg ERP/Vyapar inventory sync. | **`FUTURE`** |

---

### M7: Merchant Notifications & Alerts

| Dimension | Details | Status |
|---|---|---|
| **PRODUCT REQUIREMENT** | Push notification and in-app badge engine. Notification types: New Sale Alert (instant push with item, size, price), Low Stock Alert (item <= 2 pieces), Out of Stock Alert (item = 0, auto-hidden from scan), Daily Sales Summary (9 PM nightly), Trend Alert, New QR Scan Alert (optional for boutique owner assistance), Report Ready Alert, Restock Reminder (48 hours after low stock alert). | PRD §3 (M7), §8–9 |
| **CURRENT WEBSITE IMPLEMENTATION** | `/merchant/notifications` renders 5 static mock notification cards categorized by tags (Inventory, AI, System, Reports). | **`MOCK`** |
| **MOBILE MVP TARGET** | In-app Notification Sheet accessible via bell icon in Merchant Dashboard. Dynamically generates real notification badges when local stock is edited below threshold (<= 2 units) or when a simulated purchase is triggered. | **`LOCAL`** *(State-driven)* |
| **FUTURE INTEGRATION** | Firebase Cloud Messaging (FCM) background push notifications with device token registration. | **`FUTURE`** |

---

## 3. Customer Modules (U1 – U6) Audit Matrix

### U1: User Onboarding & Profile Setup

| Dimension | Details | Status |
|---|---|---|
| **PRODUCT REQUIREMENT** | High-speed onboarding. Captures style preference (Casual, Smart Casual, Formal, Mix), budget range per item, gender preference, and occasion relevance (Daily, College, Office, Special Events). Photo upload for body and skin analysis. Single phone number authentication. | PRD §3 (U1), §9 |
| **CURRENT WEBSITE IMPLEMENTATION** | `/register` creates account in Supabase Auth; `/onboarding/role` sets role; `/onboarding/waitlist` captures first/last name, city; `/app/profile` displays member avatar, style DNA tag, and contact details. | **`REAL`** *(Supabase Auth)* + **`PARTIAL`** *(Profile)* |
| **MOBILE MVP TARGET** | Consolidated Customer Profile & Style DNA Screen (`C-06`). Allows instant switching between demo style personas (e.g., "Casual Smart / ₹1,500–₹3,500") and editing body/skin parameters via interactive sliders and chips with local state persistence. | **`LOCAL`** |
| **FUTURE INTEGRATION** | Persistent sync to Supabase `public.profiles`, `public.body_profiles`, and `public.color_profiles` tables. | **`FUTURE`** |

---

### U2: AI Body, Face & Skin Analysis

| Dimension | Details | Status |
|---|---|---|
| **PRODUCT REQUIREMENT** | Converts customer photos into structured data driving recommendations.  <br>• **Body Shape:** Google MediaPipe Pose (33 body landmarks $\rightarrow$ Rectangle, Inverted Triangle, Pear, Hourglass, Apple).  <br>• **Face Shape:** MediaPipe Face Mesh (468 facial landmarks $\rightarrow$ Oval, Round, Square, Heart, Oblong, Triangle; drives collar and neckline recommendations).  <br>• **Skin Tone & Undertone:** Google Monk Skin Tone Scale (MST 10 points) sampled in LAB colour space with hue bias (Warm, Cool, Neutral; drives flattering color palette). | PRD §3 (U2), §9–10 |
| **CURRENT WEBSITE IMPLEMENTATION** | No visual or camera implementation on web. Database schema tables `body_profiles`, `face_profiles`, and `color_profiles` are fully architected in PostgreSQL migration scripts. | **`FUTURE`** *(Web: Schema only)* |
| **MOBILE MVP TARGET** | Interactive "Style DNA & Fit Profile" component inside Customer Profile (`C-06`). Provides manual selector wheels and visual silhouette chips for Body Shape (5 types), Face Shape (6 types), and Monk Skin Tone swatch picker (10 MST shades with Warm/Cool/Neutral tags). Local rules engine uses these selections directly to score and filter in-store recommendations. | **`LOCAL`** *(Deterministic rules engine)* |
| **FUTURE INTEGRATION** | On-device Google MediaPipe pose and face mesh inference, camera snapshot skin color extractor in CIELAB color space. | **`FUTURE`** |

---

### U3: Digital Wardrobe (Auto + Optional Manual)

| Dimension | Details | Status |
|---|---|---|
| **PRODUCT REQUIREMENT** | Customer's personal digital clothing closet. Auto-builds via in-store Stylinger QR purchases. Manual photo upload with AI auto-tagging (category, colour, pattern, fit, formality) is optional. Quick-add text entry without photos. Organization: Tops, Bottoms, Footwear, Accessories, Outerwear, Ethnic. Filters: colour, formality, occasion, season. 'Unworn' tag for items unselected for 30+ days. 'Recently bought' section for last 5 purchases. | PRD §2.2, §3 (U3), §10 |
| **CURRENT WEBSITE IMPLEMENTATION** | `/app/wardrobe` displays an 8-item responsive grid with category tabs, wear counters ("Worn 12×"), and "+ Add piece" button. Backed by static mock array `wardrobeItems` in `src/lib/mock-data.ts`. | **`MOCK`** |
| **MOBILE MVP TARGET** | Digital Wardrobe Screen (`C-02`). Backed by an in-memory mutable wardrobe repository pre-seeded with 8 luxury garments. Supports category filtering (All, Tops, Bottoms, Outerwear, Footwear), wear count badges, "Recently Purchased" carousel, and an instant "+ Add Item" modal (manual text/category input). Items purchased in the scanner flow immediately appear here via local auto-sync. | **`LOCAL`** |
| **FUTURE INTEGRATION** | Vision AI auto-tagger microservice (GPT-4V / Gemini Vision), Cloudinary garment image hosting, background unused wardrobe donation alerts. | **`FUTURE`** |

---

### U4: Daily Outfit Planner

| Dimension | Details | Status |
|---|---|---|
| **PRODUCT REQUIREMENT** | Core daily retention habit driver. Generates daily ready-to-wear outfit suggestion from customer's personal wardrobe every morning. Occasion picker (College, Office, Casual, Wedding, Date, Gym, Other). Weather API integration. Save outfit as favorite. Outfit calendar history view. "Buy what's missing" prompt surfacing nearby boutique items that complete an outfit. | PRD §3 (U4), §10–11 |
| **CURRENT WEBSITE IMPLEMENTATION** | `/app/daily` renders static outfit cards ("Morning Edit", "Evening Look", "Weekend Casual") paired with hardcoded weather chip ("14° · Overcast") and action buttons ("Wear it", "Swap a piece"). Backed by static array `outfits` in `mock-data.ts`. | **`MOCK`** |
| **MOBILE MVP TARGET** | Customer Home Feed Hero Section (`C-01`). Renders "Today's Curated Look" assembled from the active wardrobe items. Features an occasion pill selector (Office, Casual, Evening), a local weather simulation chip ("28° · Sunny, Vadodara"), and action buttons ("Log as Worn" which increments item wear counters, and "Shuffle Pieces"). Includes a "Missing Accent" card linking to a nearby boutique piece. | **`LOCAL`** |
| **FUTURE INTEGRATION** | Real-time OpenWeatherMap API sync, Polyvore-trained neural compatibility scoring model hosted on Hugging Face Inference Endpoints. | **`FUTURE`** |

---

### U5: In-Store AI Styling (QR Scan Experience)

| Dimension | Details | Status |
|---|---|---|
| **PRODUCT REQUIREMENT** | Stylinger's core offline-first USP. Customer scans store QR $\rightarrow$ Boutique profile shown (name, photo, category, accepted payments) $\rightarrow$ AI filters live store inventory against customer's Style DNA $\rightarrow$ Ranked recommendations shown on personal avatar wearing the exact item $\rightarrow$ "Pair with what I own" view $\rightarrow$ Budget ceiling filter $\rightarrow$ **"Honest Mode"** (if <3 items suit customer, app explicitly informs user and recommends nearby partner stores). | PRD §1, §3 (U5), §11 |
| **CURRENT WEBSITE IMPLEMENTATION** | `/app/scanner` shows a CSS animated viewfinder overlay, recent scan chips, and a manual SKU text input box. No camera stream, no store inventory linking, no Honest Mode logic. | **`PLACEHOLDER`** |
| **MOBILE MVP TARGET** | In-Store QR Scanner Screen (`C-03`) & In-Store AI Stylist View (`C-04`).  <br>• Scanner provides a simulated camera viewfinder with quick-tap test buttons ("Scan Aura Atelier QR", "Scan Garment Tag", "Simulate Camera Barcode").  <br>• Scanned boutique session displays the store profile, live inventory ranked by customer's Style DNA score, a budget slider, "Pair with what I own" drawer, and deterministic **"Honest Mode"** notice if matching inventory is constrained. Includes an instant "Purchase & Add to Wardrobe" action. | **`LOCAL`** *(Simulated scanner & ranker)* |
| **FUTURE INTEGRATION** | Hardware camera stream via `mobile_scanner`, 3D generative avatar fitting engine, live Redis store inventory cache. | **`FUTURE`** |

---

### U6: Purchase History & Spend Tracker

| Dimension | Details | Status |
|---|---|---|
| **PRODUCT REQUIREMENT** | Complete digital ledger of Stylinger in-store purchases: item, boutique, date, price, size. Monthly and annual spend tracker. Breakdown of spend by merchant. Re-stock alerts for scanned but unbought items. Return and exchange tracking linked to original sale record. | PRD §3 (U6), §12 |
| **CURRENT WEBSITE IMPLEMENTATION** | `/app/orders` renders a static 4-row HTML table with mock order numbers, boutique names, sample dates, rupee amounts, and "Delivered" status badges. | **`MOCK`** |
| **MOBILE MVP TARGET** | Order History Sheet / Sub-view within Customer Profile (`C-06`). Displays all simulated in-store purchases with boutique name, date, price, and item thumbnail. Automatically prepends newly purchased items when the user completes an in-store scan checkout. | **`LOCAL`** |
| **FUTURE INTEGRATION** | GST invoice PDF download, merchant return/exchange webhook sync. | **`FUTURE`** |

---

## 4. Shared Modules (S1 – S6) Audit Matrix

### S1: Authentication & Account Management

| Dimension | Details | Status |
|---|---|---|
| **PRODUCT REQUIREMENT** | Phone OTP login (no passwords). Single phone account can hold both Customer and Merchant roles with instant profile switching. JWT session refreshed every 24 hours. Logout invalidates all active sessions. | PRD §3 (S1), §12 |
| **CURRENT WEBSITE IMPLEMENTATION** | Supabase Auth (Email & Password) with `AuthGuard`, `GuestGuard`, and `OnboardedGuard`. Role selection updates `profiles.role`. Session saved in `localStorage`. | **`REAL`** |
| **MOBILE MVP TARGET** | Shared Auth & Mode Router (`S-01`, `S-02`). In-memory session manager with pre-configured one-tap persona switcher: "Enter as Merchant (Aura Atelier)" or "Enter as Customer (Ananya, Vadodara)". Manages instant mode toggling without network latency. | **`LOCAL`** |
| **FUTURE INTEGRATION** | Supabase Phone Auth or SMS OTP integration with biometric device unlock (FaceID / Fingerprint). | **`FUTURE`** |

---

### S2: Purchase Flow & Auto-Sync Engine (The Critical Connector)

| Dimension | Details | Status |
|---|---|---|
| **PRODUCT REQUIREMENT** | **The most architecturally critical module in Stylinger.** Executes an atomic multi-step transaction upon in-store QR checkout:  <br>1. Decrement merchant item stock in real time.  <br>2. Synthesize item record and inject into customer's digital wardrobe.  <br>3. Log sale in merchant analytics and customer purchase history.  <br>4. Dispatch instant push notification to merchant terminal.  <br>5. Generate digital GST receipt.  <br>If any step fails, entire transaction rolls back. | PRD §3 (S2), §12, §14–15 |
| **CURRENT WEBSITE IMPLEMENTATION** | Not implemented. Product catalog updates and orders are completely isolated from customer wardrobe and analytics. | **`FUTURE`** *(Web: No sync)* |
| **MOBILE MVP TARGET** | **Local In-Memory Atomic S2 Transaction Coordinator.** A dedicated Dart service class (`PurchaseSyncCoordinator`) that executes the atomic sequence across local repositories: decrements stock in `InMemoryInventoryRepository`, adds the item to `InMemoryWardrobeRepository`, appends the order to `InMemoryOrderRepository`, and dispatches an in-app toast notification. If any entity validation fails, changes are discarded. | **`LOCAL`** *(Simulated atomic bus)* |
| **FUTURE INTEGRATION** | PostgreSQL ACID database transaction (or Supabase Edge Function / Node.js API) with distributed Redis lock and rollback recovery queue. | **`FUTURE`** |

---

### S3: AI Recommendation & Styling Engine

| Dimension | Details | Status |
|---|---|---|
| **PRODUCT REQUIREMENT** | Multi-tier AI pipeline:  <br>• Vision AI (GPT-4V / Gemini Vision) tags uploaded clothing photos at ingestion (cached permanently).  <br>• Body-Colour Rules Engine: Python rules scoring color & cut against body shape and skin tone.  <br>• Outfit Compatibility Engine: Polyvore model scoring garment combinations.  <br>• Daily Outfit Generator combining rules + repeat-memory.  <br>• In-Store Ranker ordering boutique items by fit score + owned wardrobe compatibility + budget ceiling. | PRD §3 (S3), §12–13 |
| **CURRENT WEBSITE IMPLEMENTATION** | `/app/suggestions` renders 4 hardcoded outfit suggestion cards from `mock-data.ts`. | **`MOCK`** |
| **MOBILE MVP TARGET** | Pure Dart Deterministic Rules Engine (`LocalStylingEngine`). Evaluates clothing category, silhouette cut, and color palette against the user's selected Monk skin tone (Warm/Cool/Neutral) and body silhouette (Hourglass, Pear, Rectangle, Apple, Inverted Triangle) using pre-calculated harmony matrix scoring. Runs 100% on-device with zero API latency. | **`LOCAL`** *(Deterministic rules)* |
| **FUTURE INTEGRATION** | Remote Python FastAPI inference microservices with fine-tuned fashion embeddings and multimodal vision analysis. | **`FUTURE`** |

---

### S4: Push Notification System

| Dimension | Details | Status |
|---|---|---|
| **PRODUCT REQUIREMENT** | Push notification channels: Transactional (Sales, receipts, OTPs — always on); Operational (Low stock, restock reminders — default on); Marketing (Trend updates, new stores — opt-out); Digest (9 PM daily summary, Monday report). | PRD §3 (S4), §13 |
| **CURRENT WEBSITE IMPLEMENTATION** | Static bell icon and static notification list in `/merchant/notifications` and `/app/notifications`. | **`MOCK`** |
| **MOBILE MVP TARGET** | In-App Toast & Floating Banner Manager. Dispatches real-time styled in-app banners when stock drops to low threshold or when a sale is completed. | **`LOCAL`** |
| **FUTURE INTEGRATION** | Firebase Cloud Messaging (FCM) on Android & Apple Push Notification Service (APNs) on iOS. | **`FUTURE`** |

---

### S5: Search & Discovery

| Dimension | Details | Status |
|---|---|---|
| **PRODUCT REQUIREMENT** | Phase 1 (Basic): Search by store name or area in city; interactive map view displaying nearby partner boutiques with distance and category filters. Phase 2 (Advanced): Search by garment type across all nearby inventories ("Navy blazer near me"); filter by budget; personalized trending discovery feed. | PRD §3 (S5), §13 |
| **CURRENT WEBSITE IMPLEMENTATION** | Real-time product search inside inventory catalog; mock store map screen at `/app/stores` with static graphic and 4 hardcoded Mumbai boutiques. | **`PARTIAL`** *(Catalog search real, map mock)* |
| **MOBILE MVP TARGET** | Boutique Discovery Screen (`C-05`). Displays a curated list of partner boutiques in Vadodara (e.g., Alkapuri, Mandvi, Sayajiganj) with distance badges, category tags, and "Open In-Store Stylist" CTA. Includes instant keyword search across store names and catalog categories. | **`LOCAL`** |
| **FUTURE INTEGRATION** | Native Google Maps / Mapbox SDK with dynamic GPS geofencing and multi-merchant inventory federated search. | **`FUTURE`** |

---

### S6: App Settings & Preferences

| Dimension | Details | Status |
|---|---|---|
| **PRODUCT REQUIREMENT** | Manage notification channels, privacy settings, body profile visibility consent, store discoverability, and account deletion. | PRD §3 (S6), §13 |
| **CURRENT WEBSITE IMPLEMENTATION** | `/app/settings` and `/merchant/settings` with switch toggles for alerts and discoverability. | **`PARTIAL`** |
| **MOBILE MVP TARGET** | Settings Sheet embedded in Profile views (`C-06` and `M-06`). Provides functional local toggles for "Low Stock Push Alerts", "In-Store Scan Notifications", and "Honest Recommendation Mode", plus a prominent "Switch to Merchant/Customer Mode" action. | **`LOCAL`** |
| **FUTURE INTEGRATION** | Synchronized remote profile settings stored in Supabase `profiles.preferences`. | **`FUTURE`** |

---

## 5. Master Status Summary Table

| Module Code | Module Name | PRD Scope | Current Web Status | Mobile MVP Target | Future Integration |
|---|---|---|---|---|---|
| **M1** | Merchant Onboarding & Store Setup | Phase 1 Priority | **`PARTIAL`** | **`LOCAL`** | Supabase `stores` + GPS |
| **M2** | Inventory Management System | Phase 1 Priority | **`REAL`** *(Local)* | **`LOCAL`** *(Seeded 30 items)* | PostgreSQL cloud sync |
| **M3** | QR Code Generation & Management | Phase 1 Priority | **`PLACEHOLDER`** | **`LOCAL`** *(Vector QR)* | Dynamic 24h token salt |
| **M4** | Sales Analytics Dashboard | Phase 1 Basic / Phase 2 Full | **`PARTIAL`** | **`LOCAL`** + **`MOCK`** | Daily 9 PM push pipeline |
| **M5** | AI Trend Intelligence Engine | Phase 2 Growth | **`PLACEHOLDER`** | **`MOCK`** *(Curated cards)* | Python FastAPI + IG API |
| **M6** | Payments & Razorpay Integration | Phase 1 Priority | **`MOCK`** | **`MOCK`** *(Local checkout)* | Razorpay Flutter SDK |
| **M7** | Merchant Notifications & Alerts | Phase 1 Priority | **`MOCK`** | **`LOCAL`** *(In-app alerts)* | FCM / APNs background |
| **U1** | User Onboarding & Profile Setup | Phase 1 Priority | **`REAL`** *(Auth)* / **`PARTIAL`** | **`LOCAL`** *(Style DNA)* | Supabase sync |
| **U2** | AI Body, Face & Skin Analysis | Phase 1 Priority | **`FUTURE`** *(Schema only)* | **`LOCAL`** *(Slider rules)* | MediaPipe ML on-device |
| **U3** | Digital Wardrobe (Auto + Manual) | Phase 1 Priority | **`MOCK`** | **`LOCAL`** *(Mutable closet)* | Vision AI auto-tagging |
| **U4** | Daily Outfit Planner | Phase 1 Basic / Phase 2 Full | **`MOCK`** | **`LOCAL`** *(Daily look + weather)*| Polyvore ML engine |
| **U5** | In-Store AI Styling (QR Scan) | Phase 1 Priority | **`PLACEHOLDER`** | **`LOCAL`** *(Simulated scanner)*| Camera stream + 3D avatar |
| **U6** | Purchase History & Spend Tracker | Phase 1 Priority | **`MOCK`** | **`LOCAL`** *(Dynamic ledger)*| GST invoices & returns |
| **S1** | Auth & Account Management | Phase 1 Priority | **`REAL`** *(Email/Pass)* | **`LOCAL`** *(Mock personas)* | Phone OTP gateway |
| **S2** | Purchase Flow & Auto-Sync Engine| Phase 1 Priority | **`FUTURE`** | **`LOCAL`** *(Atomic coordinator)*| Cloud ACID transaction |
| **S3** | AI Recommendation & Styling | Phase 1 Rules / Phase 2 ML | **`MOCK`** | **`LOCAL`** *(Dart rules engine)* | Hugging Face inference |
| **S4** | Push Notification System | Phase 1 Priority | **`MOCK`** | **`LOCAL`** *(In-app banners)*| FCM / APNs background |
| **S5** | Search & Discovery | Phase 1 Stores / Phase 2 Items | **`PARTIAL`** | **`LOCAL`** *(Vadodara list)* | Google Maps SDK |
| **S6** | App Settings & Preferences | Phase 1 Priority | **`PARTIAL`** | **`LOCAL`** *(Toggles)* | Supabase sync |
