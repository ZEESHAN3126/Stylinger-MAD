# Stylinger Website Current State
**Master Product & Engineering Reference Handbook**
*Generated: September 2026 | Target: Mobile App Architecture & Flutter Reference Specification*

---

## 1. Project Overview

**Stylinger** is an AI-powered omnichannel fashion platform designed for offline clothing retail in India. Its core positioning is dual-sided:
1. **For Merchants:** Digitize physical store inventory, manage catalogs digitally, track footfall/demand analytics, and generate scannable in-store tags.
2. **For Customers:** Discover curated local fashion from independent boutiques, maintain a personal digital wardrobe, scan physical garment tags in-store, and receive AI-curated daily styling recommendations.

The current codebase is a modern TypeScript full-stack web application developed using **TanStack Start**, **React 19**, **Tailwind CSS v4**, **Three.js / React Three Fiber**, and **Supabase**. The visual brand system is **"Editorial Ivory"** (`#F6F4EF` canvas, `#17191C` typography, `#2457D6` cobalt brand accent, `#111419` dark footer) paired with a multi-stop optical **Liquid Glass** material layer.

---

## 2. Technology Stack

### Core Framework & Runtime
- **Language:** TypeScript 5.8.3 (`strict: true`, modern ESNext modules)
- **Framework:** TanStack Start 1.168 (Full-stack SSR with Vinxi / Nitro engine)
- **UI Runtime:** React 19.2.0 + React DOM 19.2.0
- **Routing:** TanStack Router 1.170 (Fully type-safe file-based routing with code splitting)
- **Data Fetching / Cache:** TanStack React Query 5.101 (`useQuery`, `useMutation`, `useQueryClient`)
- **Build Tool:** Vite 8.0.16 + Nitro 3.0 (Server engine with Cloudflare Workers / Node support)
- **Package Manager:** npm

### Styling & Design System
- **CSS Engine:** Tailwind CSS v4.2.1 (`@tailwindcss/vite`)
- **Icons:** `lucide-react` 0.575 (60+ active icon symbols)
- **Primitives:** Radix UI primitives (Accordion, Alert Dialog, Aspect Ratio, Avatar, Checkbox, Collapsible, Context Menu, Dialog, Dropdown Menu, Hover Card, Menubar, Navigation Menu, Popover, Progress, Radio Group, Scroll Area, Select, Separator, Slider, Slot, Switch, Tabs, Toggle, Tooltip)
- **Component Styling:** `class-variance-authority` (CVA), `clsx`, `tailwind-merge`
- **Charts:** `recharts` 3.9.2 (ResponsiveContainer, BarChart, PieChart, Tooltip, XAxis, YAxis, Cell)
- **Motion:** `motion` 12.42 (Framer Motion v12 core for spring transitions, fade-ins, and layoutId)
- **Forms & Validation:** `react-hook-form` 7.71 + `zod` 3.24 + `@hookform/resolvers` 5.2
- **Notifications:** `sonner` 2.0.7 (Rich toast notifications)

### 3D & Graphics Engine
- **3D Core:** `three` 0.185.1 (WebGL standard renderer)
- **React 3D Integration:** `@react-three/fiber` 9.7.0 + `@react-three/drei` 10.7.8 (Html overlay, OrbitControls helpers)

### Backend, Auth & Storage
- **Auth Provider:** Supabase Auth (`@supabase/supabase-js` 2.110.7) — GoTrue token-based auth
- **SSR Client:** `@supabase/ssr` 0.12.3
- **Primary Database:** PostgreSQL 15+ managed via Supabase (23 relational tables defined)
- **Local Persistence Layer:** Browser `localStorage` (Product catalog with 30 seeded items, CRUD mutations)
- **Analytics:** Custom analytics client (`src/lib/analytics.ts` dispatching custom browser events and local audit logs)

---

## 3. Architecture

The web application follows a tiered, SSR-safe modular architecture:

```
┌────────────────────────────────────────────────────────────────────────┐
│                        TanStack Start Shell                            │
│                     (src/routes/__root.tsx)                            │
└───────────────────────────────────┬────────────────────────────────────┘
                                    │
          ┌─────────────────────────┴─────────────────────────┐
          ▼                                                   ▼
┌───────────────────────────┐                       ┌────────────────────┐
│      Public / Auth        │                       │  Dashboard Layout  │
│  Landing, Login, Register │                       │ (DashShell Engine) │
└─────────┬─────────────────┘                       └─────────┬──────────┘
          │                                                   │
          ├─────────────────────────┐         ┌───────────────┴──────────────┐
          ▼                         ▼         ▼                              ▼
┌───────────────────┐    ┌────────────────────┐  ┌─────────────────┐  ┌────────────────────┐
│ 3D Visual Layer   │    │  Waitlist Engine   │  │ Merchant Portal │  │  Customer Portal   │
│ Hero & Customizer │    │ Role & EarlyAccess │  │ (/merchant/*)   │  │    (/app/*)        │
└───────────────────┘    └────────────────────┘  └────────┬────────┘  └─────────┬──────────┘
                                                          │                     │
                                                          ▼                     ▼
                                                 ┌─────────────────┐  ┌────────────────────┐
                                                 │ localStorage DB │  │   Mock Data Feed   │
                                                 │  (30 Products)  │  │ (Wardrobe, Outfits)│
                                                 └─────────────────┘  └────────────────────┘
```

1. **Root Layout (`src/routes/__root.tsx`):**
   Injects Google Fonts (*Instrument Serif*, *Inter*), CSS variables, meta tags, and mounts the root `AuthProvider` and `QueryClientProvider`.
2. **Dashboard Shell Engine (`src/components/site/dash-shell.tsx`):**
   A unified layout engine (`DashShell`) used by both Merchant and Customer modes. Provides desktop sidebar navigation (`lg:flex`), mobile sticky top navigation bar with search and quick actions, and reusable page primitives (`PageHeader`, `GlassCard`, `EmptyState`).
3. **Dual Persistence Strategy:**
   - **User Identity & Waitlist:** Backed by Supabase (`auth.users` and `public.profiles`).
   - **Merchant Inventory Catalog:** Backed by `localStorage` (`stylinger_products`) wrapped with TanStack Query.
   - **Customer Features (Wardrobe, Scanner, Outfits):** Backed by static in-memory demo collections (`src/lib/mock-data.ts`).

---

## 4. Complete Page / Route Inventory

Stylinger contains **23 distinct client-facing routes** mapped under file-based TanStack Router conventions:

| Route | Page Name | User Type | Module | Current Status | Backend / Source | Auth Required |
|---|---|---|---|---|---|---|
| `/` | Landing Page | Public | Marketing | **REAL** | Static JSX + 3D WebGL Canvas | No |
| `/login` | Sign In | Guest | S1 (Auth) | **REAL** | Supabase GoTrue Auth | `GuestGuard` |
| `/register` | Sign Up | Guest | S1 (Auth) | **REAL** | Supabase GoTrue Auth | `GuestGuard` |
| `/forgot-password` | Password Recovery | Guest | S1 (Auth) | **REAL** | Supabase GoTrue Auth | `GuestGuard` |
| `/reset-password` | Password Reset | Guest | S1 (Auth) | **REAL** | Supabase GoTrue Auth | `GuestGuard` |
| `/verify-email` | Email Verification Notice | Guest | S1 (Auth) | **REAL** | Static Instructions | No |
| `/auth/callback` | OAuth / PKCE Callback | Shared | S1 (Auth) | **REAL** | Supabase Token Exchange | No |
| `/onboarding/role` | Role Selection | Authenticated | S1 / U1 | **REAL** | Supabase `profiles.role` Update | `AuthGuard` |
| `/onboarding/waitlist` | Waitlist Details Form | Authenticated | S1 / M1 / U1 | **REAL** | Supabase `profiles` Update | `AuthGuard` |
| `/early-access` | Early Access Celebration | Authenticated | S1 (Waitlist) | **REAL** | Supabase `profiles` Read | `OnboardedGuard` |
| `/merchant` | Merchant Dashboard | Merchant | M4 (Analytics) | **PARTIAL** | `localStorage` DB + Recharts + Mock Orders | No (MVP open) |
| `/merchant/inventory` | Product Inventory | Merchant | M2 (Inventory) | **REAL** | `localStorage` DB (CRUD) | No (MVP open) |
| `/merchant/products/new` | Add Product | Merchant | M2 (Inventory) | **REAL** | `localStorage` DB (Create Mutation) | No (MVP open) |
| `/merchant/products/$id/edit` | Edit Product | Merchant | M2 (Inventory) | **REAL** | `localStorage` DB (Update Mutation) | No (MVP open) |
| `/merchant/analytics` | Store Analytics | Merchant | M4 (Analytics) | **PARTIAL** | `localStorage` DB + Recharts | No (MVP open) |
| `/merchant/reports` | Catalog Reports | Merchant | M4 (Analytics) | **PARTIAL** | `localStorage` DB Computed Stats | No (MVP open) |
| `/merchant/qr` | QR Tag Generator | Merchant | M3 (QR Codes) | **PLACEHOLDER** | Mathematical Procedural Grid | No (MVP open) |
| `/merchant/orders` | Store Sales Orders | Merchant | M6 (Payments) | **MOCK** | Static `merchantOrders` Array | No (MVP open) |
| `/merchant/notifications` | Merchant Alerts | Merchant | M7 (Notifs) | **MOCK** | Static In-File Array | No (MVP open) |
| `/merchant/settings` | Store Settings | Merchant | M1 (Setup) | **PARTIAL** | Local State / Default Placeholders | No (MVP open) |
| `/app` | Customer Home | Customer | U1 / U4 | **MOCK** | Static `outfits` + `wardrobeItems` | No (MVP open) |
| `/app/wardrobe` | Digital Wardrobe | Customer | U3 (Wardrobe) | **MOCK** | Static `wardrobeItems` (8 pieces) | No (MVP open) |
| `/app/daily` | Daily Outfit Planner | Customer | U4 (Outfits) | **MOCK** | Static `outfits` + Mock Weather | No (MVP open) |
| `/app/suggestions` | AI Style Suggestions | Customer | S3 (AI Styling) | **MOCK** | Static `outfits` (4 looks) | No (MVP open) |
| `/app/scanner` | QR Tag Scanner | Customer | U5 (In-Store) | **PLACEHOLDER** | Animated CSS Frame + SKU Input | No (MVP open) |
| `/app/stores` | Nearby Store Map | Customer | S5 (Discovery) | **MOCK** | Static `nearbyStores` (4 stores) | No (MVP open) |
| `/app/orders` | Customer Purchases | Customer | U6 (Purchases) | **MOCK** | Static `purchaseHistory` (4 orders) | No (MVP open) |
| `/app/profile` | Customer Profile | Customer | U1 (Profile) | **PARTIAL** | Local Form Inputs | No (MVP open) |
| `/app/settings` | Customer Preferences | Customer | S6 (Settings) | **PARTIAL** | Local Switch Toggles | No (MVP open) |
| `/app/notifications` | Customer Inbox | Customer | S4 (Notifs) | **MOCK** | Static `notifications` (4 alerts) | No (MVP open) |
| `/privacy` | Privacy Policy | Public | Legal | **REAL** | Markdown Text Content | No |
| `/terms` | Terms of Service | Public | Legal | **REAL** | Markdown Text Content | No |

---

## 5. Map the Website into Product Modules

### Merchant Modules

#### M1 — Merchant Onboarding & Store Setup
- **Status:** **PARTIAL**
- **Web Implementation:** Role selection (`/onboarding/role`) sets `role: 'merchant'`. Waitlist form (`/onboarding/waitlist`) captures `store_name`, `business_email`, `business_phone`, and `city`, storing it in `profiles.preferences.waitlist`. Store settings (`/merchant/settings`) allows editing bio, address, founding year, and map toggles.
- **Backend Used:** Supabase `public.profiles` table.
- **Working:** Saving merchant waitlist data and store preferences to Supabase.
- **Mocked:** Multi-branch store hierarchy, GST verification, store operating hours.
- **Mobile Reusability:** High. Screen layout and form fields map directly to a Flutter setup wizard.
- **Mobile Adaptation:** Add native image picker for store banner/logo and GPS autofill for store address.

#### M2 — Inventory Management
- **Status:** **REAL** (Client-side persistent via `localStorage`, database schema ready in PostgreSQL)
- **Web Implementation:** Complete product catalog (`/merchant/inventory`), product creation wizard (`/merchant/products/new`), and product editor (`/merchant/products/$id/edit`).
- **Features:**
  - Real-time search by name, SKU, and category.
  - Category dropdown filter across 9 categories (Outerwear, Shirts, T-Shirts, Jeans, Trousers, Knitwear, Dresses, Footwear, Accessories).
  - Dynamic stock status pill badges: "In stock" (>5), "Low stock" (1–5), "Sold out" (0).
  - Add product form with Zod schema validation (name, SKU, price, category, color, description, stock, comma-separated sizes).
  - Delete product confirmation modal (`AlertDialog`).
  - Auto-seeding 30 realistic fashion pieces with high-res Unsplash imagery.
- **Backend Used:** `src/lib/db.ts` and `src/lib/useProducts.ts` (TanStack Query mutations). PostgreSQL tables `products` and `inventory` fully architected in migration `003_tables.sql`.
- **Mobile Reusability:** 100% of data structures and business logic map directly to Flutter `Product` model.
- **Mobile Adaptation:** Replace table view with a responsive grid/list of product cards with swipe-to-edit and camera photo capture.

#### M3 — QR Code Generation & Management
- **Status:** **PLACEHOLDER**
- **Web Implementation:** Screen at `/merchant/qr` displaying print-ready QR preview cards for each catalog product.
- **Features:** Mathematical deterministic QR cell pattern generator; disabled "Export PDF" and "Print sheet" buttons; explicit roadmap notice.
- **Backend Used:** None (frontend SVG generation). Database table `public.qr_codes` defined in SQL.
- **Mobile Reusability:** Conceptually ready. In Flutter, use `qr_flutter` package to generate real SVG/vector QR codes from `product.id` and `store.id`.

#### M4 — Sales Analytics
- **Status:** **PARTIAL** (Real catalog analytics, mock sales volume)
- **Web Implementation:** `/merchant/analytics`, `/merchant/reports`, and dashboard summary cards (`/merchant`).
- **Features:**
  - Live category distribution bar chart (`recharts`).
  - Stock level distribution (In stock vs Low stock vs Sold out).
  - Price band distribution (Under ₹3k, ₹3k–₹8k, ₹8k–₹15k, ₹15k+).
  - Top 5 most expensive products and top 5 highest stocked products.
  - Average catalog unit price calculation.
  - Weekly scans and sales line chart (mocked).
- **Backend Used:** Computed on the fly from stored catalog data via `getProductStats()`.
- **Mobile Reusability:** High. Re-implement using Flutter `fl_chart`.

#### M5 — AI Trend Intelligence
- **Status:** **NOT CURRENTLY IMPLEMENTED** (UI copy and notification placeholders only).
- **Web Implementation:** Static alert chips in `/merchant/notifications` ("AI inventory insight: Outerwear is top category").
- **Backend Used:** None.
- **Mobile Adaptation Needed:** Requires backend cron/LLM pipeline to analyze sales trends and return natural language restock advice.

#### M6 — Payments
- **Status:** **NOT CURRENTLY IMPLEMENTED** (Mock order table only).
- **Web Implementation:** `/merchant/orders` shows static table of completed/pending sales with rupee amounts.
- **Backend Used:** None. PostgreSQL tables `orders` and `order_items` defined in schema.

#### M7 — Merchant Notifications & Alerts
- **Status:** **MOCK**
- **Web Implementation:** `/merchant/notifications` renders 5 static notification cards categorized by tags (Inventory, AI, System, Reports).
- **Backend Used:** None. PostgreSQL table `public.notifications` defined in schema.

---

### Customer Modules

#### U1 — User Onboarding & Profile
- **Status:** **REAL**
- **Web Implementation:** `/register` creates user in Supabase Auth; `/onboarding/role` assigns role 'customer'; `/onboarding/waitlist` captures first name, last name, and city; `/app/profile` displays member avatar, style DNA tag, and contact details.
- **Backend Used:** Supabase `auth.users` and `public.profiles`.
- **Working:** Complete profile row creation via database trigger `on_auth_user_created` and subsequent updates.

#### U2 — Body / Face / Skin Analysis
- **Status:** **NOT CURRENTLY IMPLEMENTED** (Tables defined in PostgreSQL schema).
- **Web Implementation:** None on web frontend. Database schema includes `body_profile`, `face_profile`, and `color_profile` with detailed measurement and color undertone fields.
- **Mobile Reusability:** Priority mobile feature. Camera-assisted body measurement or interactive measurement sliders should be implemented natively in Flutter.

#### U3 — Digital Wardrobe
- **Status:** **MOCK**
- **Web Implementation:** `/app/wardrobe` displays an 8-item grid with category filtering (Outerwear, Tops, Bottoms, Knitwear, Dresses, Footwear), tags ("Signature", "Everyday", "Workhorse"), wear counters ("Worn 12×"), and "+ Add piece" button.
- **Backend Used:** Static array `wardrobeItems` in `src/lib/mock-data.ts`.
- **Mobile Reusability:** High UI reference. The card design, wear count badges, and category chips provide an exact Flutter widget blueprint.

#### U4 — Daily Outfit Planner
- **Status:** **MOCK**
- **Web Implementation:** `/app/daily` and homepage hero tile. Assembles 3 wardrobe pieces into daily looks ("Morning Edit", "Evening Look", "Weekend Casual") paired with weather metrics ("14° · Overcast") and action buttons ("Wear it", "Swap a piece", "Not today").
- **Backend Used:** Static array `outfits` in `src/lib/mock-data.ts`.
- **Mobile Reusability:** High. Native mobile home screen card with swipe gestures to cycle daily suggestions.

#### U5 — In-Store AI Styling
- **Status:** **PLACEHOLDER**
- **Web Implementation:** `/app/scanner` displays animated scanning crosshairs, recent scans list, and manual SKU input form.
- **Backend Used:** None.
- **Mobile Reusability:** Direct match for mobile. Use Flutter `mobile_scanner` with device camera to read physical QR codes in retail partner stores.

#### U6 — Purchase History & Spend Tracker
- **Status:** **MOCK**
- **Web Implementation:** `/app/orders` displays a 4-row table of item receipts, store names, sample dates, rupee amounts, and "Delivered" status badges.
- **Backend Used:** Static array `purchaseHistory` in `src/lib/mock-data.ts`.

---

### Shared Modules

#### S1 — Authentication & Account Management
- **Status:** **REAL**
- **Web Implementation:** Password signup, login, email verification notification, forgot password reset flow, and session persistence via Supabase GoTrue Auth.
- **Guard Architecture:** `AuthGuard`, `GuestGuard`, and `OnboardedGuard` ensure race-free routing.
- **Mobile Reusability:** 100% reusable backend logic using `supabase_flutter`.

#### S2 — Purchase Flow & Auto-Sync
- **Status:** **NOT CURRENTLY IMPLEMENTED** (Schema ready in PostgreSQL `orders` and `order_items`).

#### S3 — AI Recommendation & Styling Engine
- **Status:** **MOCK**
- **Web Implementation:** `/app/suggestions` renders 4 weekly outfit cards with "Save look" and "Regenerate" buttons.
- **Backend Used:** Static arrays in `mock-data.ts`.

#### S4 — Push Notifications
- **Status:** **MOCK**
- **Web Implementation:** Bell icon in dashboard top bar; feed in `/app/notifications`.
- **Mobile Adaptation Needed:** Implement Firebase Cloud Messaging (FCM) / APNs in Flutter.

#### S5 — Search & Discovery
- **Status:** **PARTIAL**
- **Web Implementation:**
  - Inventory search (real, in `useProducts.ts`).
  - Store discovery (`/app/stores`): Mock map visualization with animated radar pins in Bandra West and list of 4 boutiques.
- **Mobile Adaptation Needed:** Implement Flutter Google Maps or Mapbox with native geolocation (`geolocator`).

#### S6 — Settings & Preferences
- **Status:** **PARTIAL**
- **Web Implementation:** Form in `/app/settings` and `/merchant/settings` with switch toggles for notifications, discovery, and delete account danger zone.

---

## 6. Authentication Audit

### Implemented Methods
- **Email & Password Authentication:** Handled via `supabase.auth.signInWithPassword` and `supabase.auth.signUp`.
- **Password Reset:** Handled via `supabase.auth.resetPasswordForEmail` delivering recovery links targeting `/reset-password`.
- **Session Management:** Persisted in `localStorage` under `sb-<project-ref>-auth-token` with automatic background token refresh (`autoRefreshToken: true`).
- **Callback Verification:** `/auth/callback` handles OAuth, PKCE auth codes, and token hashes.

### User Role Separation
- Profiles have a `role` enum in PostgreSQL: `'customer' | 'merchant' | 'admin'`.
- Initial registration creates a profile with `onboarding_completed: false`.
- User is routed to `/onboarding/role` to select `'customer'` or `'merchant'`.
- Upon completing `/onboarding/waitlist`, `onboarding_completed` is flipped to `true`.
- **Race Condition Safeguard:** The application uses dual-state guarding (`loading` for session check + `profileLoading` for profile query). Guards block rendering until both flags resolve, preventing unwanted redirects.

---

## 7. Database / Supabase Audit

Stylinger has a complete 23-table schema documented in `database_documentation.md` and written in `supabase/migrations/`:

```
┌────────────────────────────────────────────────────────────────────────┐
│                         Supabase Auth (auth.users)                     │
└───────────────────────────────────┬────────────────────────────────────┘
                                    │ (1:1 Cascade Trigger)
                                    ▼
┌────────────────────────────────────────────────────────────────────────┐
│                        Public Schema (public.profiles)                 │
└──────────┬────────────────────────┬────────────────────────┬───────────┘
           │ (1:1)                  │ (1:N)                  │ (1:N)
           ▼                        ▼                        ▼
┌────────────────────┐    ┌────────────────────┐   ┌────────────────────┐
│ Style / Body / Face│    │   Merchant Stores  │   │  Customer Wardrobe │
│    AI Profiles     │    │   (public.stores)  │   │ (public.wardrobes) │
└────────────────────┘    └─────────┬──────────┘   └─────────┬──────────┘
                                    │ (1:N)                  │ (1:N)
                                    ▼                        ▼
                          ┌────────────────────┐   ┌────────────────────┐
                          │   Store Inventory  │   │   Wardrobe Items   │
                          │ (public.inventory) │   │(public.wardrobe_it)│
                          └─────────▲──────────┘   └─────────▲──────────┘
                                    │ (N:1)                  │ (N:1)
                                    └──────────┬─────────────┘
                                               │
                                    ┌──────────┴─────────┐
                                    │  Metadata Catalog  │
                                    │ (public.products)  │
                                    └────────────────────┘
```

### Table Usage in Current Web Codebase:

| Entity | Fields Actively Used in Frontend | Active Operations | Current Code Location | PostgreSQL Table Equivalent |
|---|---|---|---|---|
| **Profiles** | `id`, `email`, `role`, `first_name`, `last_name`, `city`, `onboarding_completed`, `preferences` | `SELECT`, `UPDATE` | `src/lib/auth-context.tsx`, `onboarding.*` | `public.profiles` (Real Supabase) |
| **Products** | `id`, `name`, `description`, `sku`, `price`, `category`, `color`, `sizes`, `stock`, `image`, `createdAt`, `updatedAt` | `SELECT`, `INSERT`, `UPDATE`, `DELETE` | `src/lib/db.ts`, `src/lib/useProducts.ts` | `public.products` (Currently in `localStorage`, schema ready) |
| **Stores** | `name`, `city`, `area`, `rating`, `tags` | `SELECT` (Mock array) | `src/lib/mock-data.ts`, `app.stores.tsx` | `public.stores` (Mock data) |
| **Wardrobe Items** | `id`, `name`, `brand`, `category`, `color`, `worn`, `tag` | `SELECT` (Mock array) | `src/lib/mock-data.ts`, `app.wardrobe.tsx` | `public.wardrobe_items` (Mock data) |
| **Outfits** | `id`, `title`, `pieces`, `weather`, `score` | `SELECT` (Mock array) | `src/lib/mock-data.ts`, `app.daily.tsx` | `public.outfits` + `outfit_items` (Mock data) |
| **Orders** | `id`, `customer`, `item`, `date`, `amount`, `status` | `SELECT` (Mock array) | `src/lib/mock-data.ts`, `merchant.orders.tsx` | `public.orders` (Mock data) |

---

## 8. API / Backend Audit

All backend network calls in the web application route through the Supabase JavaScript client:

| Method | Endpoint / Service | Purpose | Request Body | Response | Used By | Status |
|---|---|---|---|---|---|---|
| `POST` | `/auth/v1/token?grant_type=password` | User Login | `{ email, password }` | `{ access_token, user, session }` | `src/routes/login.tsx` | **REAL** |
| `POST` | `/auth/v1/signup` | User Registration | `{ email, password, options: { data } }` | `{ user, session }` | `src/routes/register.tsx` | **REAL** |
| `POST` | `/auth/v1/recover` | Password Reset Email | `{ email, redirectTo }` | `{}` | `src/routes/forgot-password.tsx` | **REAL** |
| `PUT` | `/auth/v1/user` | Password Update | `{ password }` | `{ user }` | `src/routes/reset-password.tsx` | **REAL** |
| `POST` | `/auth/v1/logout` | Session Invalidation | Header `Bearer <token>` | `{}` | `src/lib/auth-context.tsx` | **REAL** |
| `GET` | `/rest/v1/profiles` | Fetch User Profile | `?id=eq.<userId>&select=*` | Single `Profile` object | `src/lib/auth-context.tsx` | **REAL** |
| `PATCH` | `/rest/v1/profiles` | Update Role / Waitlist | `{ role }` or `{ onboarding_completed, preferences }` | Updated row | `src/routes/onboarding.*` | **REAL** |

*Note:* There are no standalone Express/NestJS REST endpoints; all backend interactions use direct Supabase RPC / PostgREST.

---

## 9. Component Inventory

### Reusable Site Primitives (`src/components/site/`)

| Component | File | Purpose | Props | Mobile Reusability |
|---|---|---|---|---|
| **`DashShell`** | `dash-shell.tsx` | Main portal layout with sidebar, header, and search | `nav: NavItem[]`, `brandLabel: string`, `children` | Adapt to Flutter `Scaffold` + BottomNavigationBar |
| **`PageHeader`** | `dash-shell.tsx` | Page title block with eyebrow, heading, and action CTA | `eyebrow?: string`, `title: string`, `description?: string`, `action?: ReactNode` | Direct match for Flutter App Bar / Header widget |
| **`GlassCard`** | `dash-shell.tsx` | Frosted glass card container | `className?: string`, `children: ReactNode` | Adapt to Flutter `BackdropFilter` card |
| **`EmptyState`** | `dash-shell.tsx` | Standard placeholder for empty lists | `title: string`, `description: string`, `action?: ReactNode` | Direct match for Flutter empty state view |
| **`AuthShell`** | `auth-shell.tsx` | Centered shell for login, register, and reset flows | `title: string`, `subtitle: string`, `footer: ReactNode`, `children` | Direct match for Flutter Auth Scaffold |
| **`Logo`** | `logo.tsx` | Brand badge ("S" initial inside cobalt/emerald square) | None | Direct match for Flutter SVG Logo widget |
| **`Navbar`** | `navbar.tsx` | Public floating pill navbar with glass backdrop | None | Desktop only (Mobile uses app bottom bar) |
| **`Hero`** | `hero.tsx` | Public landing page hero with CTAs and metrics | None | Marketing web only |
| **`Footer`** | `footer.tsx` | Dark luxury anchor footer | None | Web only |

### 3D Components (`src/components/3d/`)

| Component | File | Purpose | Mobile Reusability |
|---|---|---|---|
| **`Stylinger3DHero`** | `Stylinger3DHero.tsx` | Three.js hero scene with camera rig and parallax | Web only |
| **`FashionFigure`** | `FashionFigure.tsx` | Procedural fashion mannequin figure | Web only |
| **`FloatingFashionItems`** | `FloatingFashionItems.tsx` | HTML floating glass chips inside 3D space | Web only |
| **`InteractiveFashionProduct`** | `InteractiveFashionProduct.tsx` | 360° drag customizer with inertia | Future Flutter 3D (`flutter_cube` or Unity/Filament) |
| **`ProductControls`** | `ProductControls.tsx` | Color circles, material finish chips, and reset CTA | Direct match for Flutter Bottom Sheet Customizer |
| **`ProductHotspot`** | `ProductHotspot.tsx` | Pulsing 3D anchor point on tailored garment | Reusable interaction concept |

---

## 10. Design System Audit

### Core Brand Tokens

```
Canvas / Background:   #F6F4EF (Warm Ivory — primary canvas)
Card / Surface:        #FFFFFF (Clean White — solid readable cards)
Elevated Surface:      #EFECE4 (Soft Ivory / Cream)
Borders / Dividers:    #D9DCE1 (Soft Neutral Gray hairline)
Primary Typography:    #17191C (Deep Charcoal — WCAG AAA contrast 13.8:1)
Secondary Text:        #626872 (Cool Gray — 5.2:1 contrast)
Muted Text:            #8B929E (Muted Slate)
Primary Brand Accent:  #2457D6 (Deep Cobalt Blue — primary action buttons)
Secondary Brand Soft:  #5F82E8 (Soft Cornflower Blue)
Accent Brand Tint:     #EEF2FC (Cobalt 10% ice-tint)
Dark Section / Anchor: #111419 (Rich Charcoal-Black — footer & dark accents)
Success / Green:       oklch(0.68 0.17 156) (Emerald accent retained on tags)
```

### Typography System
- **Display / Editorial Headings:** *Instrument Serif* (Fallback: *Cormorant Garamond*, Georgia). Used on `h1`, `h2`, and primary statistics.
- **Interface / Body Text:** *Inter* (`font-sans`, weights 400, 500, 600, 700). Used for all controls, table cells, inputs, and descriptions.

### Liquid Glass Material System
- **Pill Containers (`.liquid-glass-nav`):** `linear-gradient(135deg, rgba(255,255,255,0.72) 0%, rgba(255,255,255,0.48) 100%)`, `backdrop-filter: blur(20px) saturate(1.9)`.
- **Badges & Tags (`.liquid-glass-badge`):** `linear-gradient(135deg, rgba(255,255,255,0.75) 0%, rgba(255,255,255,0.45) 100%)`, `backdrop-filter: blur(16px) saturate(1.9)`.
- **Specular Inset Highlight:** `box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.9)`.
- **Hairline Borders:** `0.8px solid rgba(217, 220, 225, 0.8)`.

---

## 11. Interactions & Animations

1. **Horizontal 3D Drag Rotation:** Implemented with smooth inertial damping. Mobile translation: Touch pan gestures (`GestureDetector` in Flutter).
2. **Glass Chip Overlays:** HTML chips hovering over 3D coordinates. Mobile translation: Floating Action Badges or Tag Overlays.
3. **Animated QR Viewfinder:** Continuous vertical laser sweep (`motion.div` translateY `0%` $\rightarrow$ `100%`). Mobile translation: Standard scanner overlay with `CustomPainter`.
4. **Interactive Filters:** Category pill selection triggers real-time query invalidation. Mobile translation: Horizontal scrolling chip bar (`ChoiceChip` in Flutter).
5. **Toast Notifications:** Sonner toasts on action completion. Mobile translation: Floating `SnackBar` with dark charcoal styling.
6. **Celebration Sparkles:** Spring animation on `/early-access`. Mobile translation: Confetti animation on successful onboarding.

---

## 12. Data & State Flows

### Flow 1: Authentication & Onboarding
```
User registers (/register)
  ├── Supabase Auth creates user in auth.users
  ├── Database trigger automatically inserts row into public.profiles
  └── User redirected to /onboarding/role
        └── User picks "merchant" or "customer"
              └── supabase.from('profiles').update({ role })
                    └── User redirected to /onboarding/waitlist
                          └── Form submission updates profiles.preferences.waitlist
                                └── Redirected to /early-access celebration screen
```

### Flow 2: Merchant Inventory Management
```
Merchant opens /merchant/inventory
  ├── useProducts() queries localStorage DB
  ├── UI renders sorted table with live stock status badges
  └── Merchant clicks "+ Add product" (/merchant/products/new)
        ├── Zod schema validates form inputs
        └── useCreateProduct().mutateAsync(newProduct)
              ├── Saves new product into localStorage
              ├── Invalidates QueryClient ["products"] and ["products", "stats"]
              └── Redirects back to inventory with instant table update
```

---

## 13. Real vs Mock Audit

| Feature Area | Implementation Details | Real / Mock / Partial | Data Source | Notes |
|---|---|---|---|---|
| **User Sign Up / Sign In** | Supabase GoTrue token authentication | **REAL** | Supabase Auth API | Complete end-to-end auth flow |
| **Password Recovery** | Email token link targeting `/reset-password` | **REAL** | Supabase Auth API | Working recovery pipeline |
| **Role Selection** | Updates `profiles.role` column | **REAL** | Supabase REST | Fully persisted in DB |
| **Waitlist Submission** | Updates `profiles.preferences.waitlist` | **REAL** | Supabase REST | Captured in user profile record |
| **Product Inventory CRUD** | Create, read, update, delete products | **REAL (Local)** | Browser `localStorage` | Backed by 30 seeded fashion items |
| **Catalog Analytics** | Category breakdown, stock distribution | **REAL (Local)** | Computed from stored catalog | Driven by live catalog queries |
| **QR Code Generation** | Mathematical mock grid generator | **PLACEHOLDER** | Frontend deterministic math | UI preview only |
| **Sales Orders (Merchant)** | Static orders list (`#SL-0021`, etc.) | **MOCK** | `src/lib/mock-data.ts` | Static demo array |
| **Merchant Alerts** | Static notification feed | **MOCK** | In-file static array | Hardcoded notifications |
| **Customer Wardrobe** | 8 clothing items with wear counters | **MOCK** | `src/lib/mock-data.ts` | Static demo array |
| **Daily Outfit Planner** | "Morning Edit" with weather info | **MOCK** | `src/lib/mock-data.ts` | Hardcoded look recommendations |
| **AI Style Suggestions** | 4 weekly curated outfits | **MOCK** | `src/lib/mock-data.ts` | Hardcoded cards |
| **In-Store QR Scanner** | Visual laser sweep animation | **PLACEHOLDER** | CSS animation + manual SKU input | No real camera feed |
| **Nearby Boutiques** | Map graphic + list of 4 stores | **MOCK** | `src/lib/mock-data.ts` | Static Mumbai boutiques |
| **Purchase History** | 4 receipts with rupee totals | **MOCK** | `src/lib/mock-data.ts` | Static demo array |

---

## 14. Asset Inventory

| Asset Name | File Path | Type | Purpose | Mobile Priority |
|---|---|---|---|---|
| **Brand Logo Icon** | `src/components/site/logo.tsx` | SVG / React Component | Brand symbol (Stylinger "S" in cobalt square) | **P0** (App icon & header logo) |
| **Product Photos (30 items)**| `src/lib/db.ts` (`UNSPLASH_IDS`) | Unsplash CDN URLs | High-resolution studio e-commerce photos | **P0** (Product mock images) |
| **Merchant Showcase 1** | `src/assets/showcase-1.jpg` | JPG Image (154 KB) | Physical clothing store boutique interior | **P1** (Merchant onboarding banner) |
| **Customer Showcase 2** | `src/assets/showcase-2.jpg` | JPG Image (60 KB) | Customer scanning QR tag in store | **P1** (Customer onboarding banner) |
| **Showcase 3** | `src/assets/showcase-3.jpg` | JPG Image (149 KB) | Clean fashion editorial shoot | **P1** (Discovery feed banner) |
| **3D Tailored Blazer Asset** | `src/components/3d/FashionFigure.tsx` | Three.js Mesh Geometries | 3D mannequin rendering | **P2** (Future 3D feature) |

---

## 15. Website → Mobile Relevance

| Website Component / Feature | Mobile Relevance | Strategic Rationale |
|---|---|---|
| **Auth Shell & Forms** | **ADAPT FOR MOBILE** | Convert centered desktop card into native mobile screen with biometric sign-in. |
| **Onboarding Role & Waitlist** | **ADAPT FOR MOBILE** | Convert into full-screen onboarding carousel. |
| **Merchant Inventory Screen** | **ADAPT FOR MOBILE** | Replace wide data table with a mobile product list/grid featuring pull-to-refresh and search. |
| **Product Create / Edit Form** | **ADAPT FOR MOBILE** | Use native camera and gallery image picker (`image_picker` package). |
| **QR Tag Scanner** | **REDESIGN FOR MOBILE** | Replace CSS animation placeholder with native camera barcode scanner (`mobile_scanner`). |
| **Digital Wardrobe** | **ADAPT FOR MOBILE** | 2-column masonry or grid view with floating "+ Add piece" button. |
| **Daily Outfit Card** | **ADAPT FOR MOBILE** | Becomes the hero widget of the mobile customer home screen. |
| **Nearby Store Map** | **REDESIGN FOR MOBILE** | Replace static graphic with real interactive Google Maps widget. |
| **Web 3D Mannequin Hero** | **DESKTOP ONLY** | Omit from mobile MVP to maintain lightweight APK size and fast launch times. |
| **Web Marketing Landing Page** | **NOT NEEDED IN APP** | Mobile apps launch directly into Auth or Home feed. |

---

## 16. Mobile Screen Inventory

### Customer Mobile Screens
1. **Splash & Welcome Screen (`C-01`):** Brand logo, Editorial Ivory splash screen, "Get Started" CTA. (*Priority: P0*)
2. **Customer Home Screen (`C-02`):** Daily outfit card ("Today's look"), weather widget, quick scan button, recently worn items. (*Priority: P0*)
3. **Digital Wardrobe Grid (`C-03`):** Category filter tabs, 2-column product cards, wear count badges, floating add button. (*Priority: P0*)
4. **In-Store Tag Scanner (`C-04`):** Fullscreen camera viewfinder, flashlight toggle, manual SKU input drawer, instant item preview popup. (*Priority: P0*)
5. **Product / Garment Detail Screen (`C-05`):** High-res product images, store name, price, matching items from personal wardrobe, "Add to Wardrobe" CTA. (*Priority: P0*)
6. **Nearby Boutiques Map & List (`C-06`):** Interactive map pins, store distance in km, curated tags, store profile sheet. (*Priority: P1*)
7. **AI Style Recommendations (`C-07`):** Weekly outfit cards, occasion filters (Work, Weekend, Evening), "Save Look" button. (*Priority: P1*)
8. **Customer Profile & Settings (`C-08`):** Personal details, style DNA tag, notification switches, sign out. (*Priority: P0*)

### Merchant Mobile Screens
1. **Merchant Dashboard (`M-01`):** Quick metric tiles (Total Products, Low Stock Alerts, Sold Out Items), quick actions (+ Add Product, Generate QR). (*Priority: P0*)
2. **Inventory Management (`M-02`):** Searchable product list, category filter chips, stock status pills, swipe actions (Edit, Delete). (*Priority: P0*)
3. **Add / Edit Product (`M-03`):** Image upload from camera/gallery, title, SKU, category, price, stock counter, size selector. (*Priority: P0*)
4. **QR Code Generator & Share (`M-04`):** Scannable vector QR code for selected product, print/export sharing sheet. (*Priority: P1*)
5. **Store Analytics (`M-05`):** Visual category breakdown chart, price band distribution, low stock alert list. (*Priority: P1*)
6. **Store Profile & Settings (`M-06`):** Boutique details, address, business hours, discoverability toggle. (*Priority: P0*)

### Shared Mobile Screens
1. **Sign In (`S-01`):** Email & password inputs, forgot password link, validation errors. (*Priority: P0*)
2. **Sign Up (`S-02`):** Email, password, confirmation, terms checkbox. (*Priority: P0*)
3. **Role Selection (`S-03`):** Large visual cards for "Customer" vs "Merchant". (*Priority: P0*)
4. **Waitlist / Profile Completion (`S-04`):** Name, city, store details (if merchant), referral code. (*Priority: P0*)
5. **Password Reset (`S-05`):** Email recovery request screen. (*Priority: P1*)

---

## 17. MVP Gap Analysis

| Feature Area | Existing Website Status | Required Mobile Status | Gap / Required Action |
|---|---|---|---|
| **Authentication** | Supabase Auth working | Identical Supabase Auth | Low gap. Integrate `supabase_flutter`. |
| **Merchant Catalog** | `localStorage` CRUD working | Cloud Database CRUD | High gap. Switch from `localStorage` to live Supabase `products` table. |
| **In-Store QR Scanner** | Visual animation only | Real native camera scanner | Medium gap. Implement `mobile_scanner` in Flutter. |
| **QR Code Generation** | Visual CSS grid only | Real vector QR generator | Low gap. Implement `qr_flutter` package. |
| **Digital Wardrobe** | Static mock data | User-specific wardrobe database | Medium gap. Wire up `wardrobes` and `wardrobe_items` Supabase tables. |
| **Store Geolocation** | Static graphic pins | Real GPS map view | Medium gap. Integrate `google_maps_flutter` + `geolocator`. |
| **3D Mannequin Visual** | WebGL Three.js canvas | Optional future feature | Omit for mobile MVP. Focus on fast 2D photography. |

---

## 18. Recommended Mobile Build Order

```
PHASE 1: Core Foundation & Auth (Weeks 1–2)
  ├── Flutter project setup with Editorial Ivory theme tokens
  ├── Supabase client integration (Auth + Profiles)
  ├── Splash, Sign In, Sign Up, Role Selection, and Onboarding screens
  └── Role-based route branching (Customer App vs Merchant App)

PHASE 2: Merchant Vertical Slice (Weeks 3–4)
  ├── Cloud Product Catalog: Connect to Supabase public.products table
  ├── Merchant Inventory list with search, category filtering, and stock badges
  ├── Add / Edit Product form with native camera image picker
  └── QR Code generation for catalog products using qr_flutter

PHASE 3: Customer Scanner & In-Store Experience (Weeks 5–6)
  ├── Native camera QR scanner using mobile_scanner
  ├── Instant Product Detail sheet on QR scan
  ├── "Add to Wardrobe" mutation saving scanned item to customer's closet
  └── Digital Wardrobe view displaying personal owned garments

PHASE 4: Refinement & Analytics (Weeks 7–8)
  ├── Merchant Dashboard analytics charts using fl_chart
  ├── Daily Outfit card with mock styling engine
  ├── Nearby Boutiques list with distance calculation
  └── End-to-end beta distribution via Google Play Internal & TestFlight
```

---

## 19. Unknowns & Questions for Mobile Architecture

1. **Product Persistence Migration:**
   *Should the mobile app immediately connect to live Supabase `public.products` and `public.inventory` tables, or should it mirror the web app's offline-first local cache during early development?*
   *(Recommendation: Connect directly to Supabase PostgREST for mobile so data syncs seamlessly across devices).*
2. **Camera Barcode Format:**
   *What exact string format should in-store QR codes encode?*
   *(Recommendation: A universal deep link URL e.g. `https://stylinger.in/p/<store_id>/<product_id>` so that scanning with native iPhone/Android camera opens the app directly).*
3. **Customer Wardrobe Sync:**
   *Should scanning an in-store item immediately add it to the customer's "Wardrobe" or a "Saved / Wishlist" bucket?*
   *(Recommendation: Split wardrobe into "Owned" vs "Saved from Store").*

---
*End of Master Reference Handbook — Stylinger Website Current State*
