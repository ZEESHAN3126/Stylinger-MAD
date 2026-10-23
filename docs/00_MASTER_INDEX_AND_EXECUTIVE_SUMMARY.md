# Stylinger Mobile Architecture Specification
## Master Index & Executive Summary

**Author:** Antigravity Architecture Team  
**Document Version:** 1.0.0 — Mobile Architecture Edition  
**Target Platform:** Flutter (Android & iOS)  
**Date:** September 2026  
**Primary References:**  
1. `pre development v1.pdf` (PRD v2.0 Merchant-First Edition by Zeeshan Vahora, Founder)  
2. `WEBSITE_CURRENT_STATE.md` (Forensic Engineering Audit of tanstack/react full-stack website)  

---

## 1. Executive Summary & Core Mission

**Stylinger** is an AI-powered fashion platform designed to serve as the unified digital and intelligence layer for India's unorganized offline clothing retailers. The platform bridges physical boutique inventory with hyper-personalized consumer styling through a single mobile application operating in **Dual Mode** (**Merchant Mode** and **Customer Mode**).

### The Core Problem & Strategic Pivot
- **The Merchant Reality:** India's offline independent clothing merchants (specifically Tier 2 retail centers like Vadodara and Surat) lack inventory digitisation, customer footfall analytics, and trend forecasting. They operate via paper ledgers, memory, and fragmented WhatsApp communication.
- **The Customer Cold-Start Bottleneck:** Traditional fashion styling apps require users to manually photograph, crop, and tag their entire existing wardrobe before receiving value. This friction leads to high drop-off during onboarding.
- **The Stylinger Solution ("Merchant-First" & "Auto-Wardrobe"):**
  1. **Merchant-First Priority (The UPI Model):** Merchants adopt Stylinger because standalone inventory tracking, low-stock alerts, and basic sales logging provide immediate operational value, even before customers scan.
  2. **Auto-Wardrobe Engine:** When a customer visits an affiliated physical boutique and purchases an item via Stylinger's in-store QR code, the item is **automatically synthesized and injected into their personal digital wardrobe**. Manual wardrobe photography becomes completely optional.
  3. **In-Store Personal AI Stylist:** When a customer scans a merchant's store entrance QR code, Stylinger connects the boutique's live in-stock inventory with the customer's personal body silhouette, skin undertone, and existing wardrobe pieces, generating real-time outfit combinations with an honest recommendations engine.

---

## 2. Four-Tier Classification Framework

To maintain strict truth separation between conceptual product goals, web prototypes, and mobile targets, every capability across this documentation suite is evaluated against the following four-tier schema:

```
┌────────────────────────────────────────────────────────────────────────┐
│                        FOUR-TIER TRUTH SCHEMA                          │
├───────────────────────────────┬────────────────────────────────────────┤
│ 1. PRODUCT REQUIREMENT        │ The intended product specification as  │
│    (pre development v1.pdf)   │ defined in PRD v2.0.                   │
├───────────────────────────────┼────────────────────────────────────────┤
│ 2. CURRENT WEBSITE            │ The forensic reality of the existing   │
│    IMPLEMENTATION             │ TanStack/React full-stack web app.     │
├───────────────────────────────┼────────────────────────────────────────┤
│ 3. MOBILE MVP TARGET          │ The pragmatic, offline-first, mock-    │
│    (Initial Mobile Phase)     │ backed implementation for Flutter.     │
├───────────────────────────────┼────────────────────────────────────────┤
│ 4. FUTURE INTEGRATION         │ Production cloud connectors, real ML   │
│    (Phase 2+ Cloud / SaaS)    │ services, payment gateways, and APIs.  │
└───────────────────────────────┴────────────────────────────────────────┘
```

### Operational Status Legend
Every sub-feature and endpoint is tagged with one of six status markers:
- **`REAL`**: Implemented with active, functional business logic (e.g., Supabase Auth GoTrue pipeline on web).
- **`LOCAL`**: Functional and persistent client-side, but currently isolated from cloud databases (e.g., 30-item product catalog in `localStorage`).
- **`MOCK`**: Visual interface driven by static, hardcoded in-memory demonstration collections.
- **`PARTIAL`**: Partially functioning user interface or logic with incomplete validation or missing workflows.
- **`PLACEHOLDER`**: Visual container or wireframe without operational backend or event wiring.
- **`FUTURE`**: Formally deferred to post-MVP roadmap (e.g., automated Razorpay settlements, Instagram trend crawlers).

---

## 3. High-Level Architecture Overview

Stylinger Mobile is architected as a **Single Flutter Codebase** running a dynamic **Mode Router**. The application avoids premature over-engineering, using a clean, feature-driven structure backed by decoupled abstract repository interfaces and 100% offline-runnable mock data.

```
┌────────────────────────────────────────────────────────────────────────┐
│                   Stylinger Single App Entry Point                     │
│                             (main.dart)                                │
└───────────────────────────────────┬────────────────────────────────────┘
                                    │
                                    ▼
┌────────────────────────────────────────────────────────────────────────┐
│                 App Router & Mode Resolver Engine                      │
│     (Checks Active Session & Role: 'merchant' vs 'customer')           │
└───────────┬────────────────────────────────────────────────┬───────────┘
            │                                                │
            ▼                                                ▼
┌───────────────────────────────┐        ┌───────────────────────────────┐
│     CUSTOMER MODE TREE        │        │      MERCHANT MODE TREE       │
│  (Tailored Shopper & Stylist) │        │   (Store Owner & Inventory)   │
├───────────────────────────────┤        ├───────────────────────────────┤
│ • C-01: Customer Home Feed    │        │ • M-01: Merchant Dashboard    │
│ • C-02: Digital Wardrobe      │        │ • M-02: Inventory Manager     │
│ • C-03: In-Store QR Scanner   │        │ • M-03: Product Add / Edit    │
│ • C-04: Item & Outfit Matcher │        │ • M-04: QR Tag Generator      │
│ • C-05: Boutique Discovery    │        │ • M-05: Store Analytics       │
│ • C-06: Profile & Style DNA   │        │ • M-06: Store Settings        │
└───────────┬───────────────────┘        └───────────┬───────────────────┘
            │                                        │
            └───────────────────┬────────────────────┘
                                │
                                ▼
┌────────────────────────────────────────────────────────────────────────┐
│               SHARED CORE & DESIGN SYSTEM (stylinger_core)             │
├────────────────────────────────────────────────────────────────────────┤
│ • Design Tokens: "Editorial Ivory" (#F6F4EF), Cobalt (#2457D6), etc.   │
│ • Design Primitives: Liquid Glass cards, pill chips, editorial headers │
│ • Abstract Service Interfaces: Auth, Catalog, Scanner, Styling, Orders │
│ • Local In-Memory Mock Data Source (30 seeded products, 8 wardrobe pcs)│
└────────────────────────────────────────────────────────────────────────┘
```

---

## 4. Documentation Suite Navigation

This documentation suite provides a complete, unambiguous technical specification across eight interconnected documents:

| Document | Title | Purpose |
|---|---|---|
| **[00_MASTER_INDEX](file:///e:/Stylinger%20MAD/docs/00_MASTER_INDEX_AND_EXECUTIVE_SUMMARY.md)** | Master Index & Executive Summary | Architectural context, status taxonomy, and master navigation. |
| **[01_PRODUCT_MATRIX](file:///e:/Stylinger%20MAD/docs/01_PRODUCT_REQUIREMENTS_AND_FEATURE_AUDIT_MATRIX.md)** | Product Requirements & Feature Audit Matrix | Granular module-by-module mapping across PRD, Web Audit, and Mobile Target. |
| **[02_APP_ARCHITECTURE](file:///e:/Stylinger%20MAD/docs/02_MOBILE_APP_ARCHITECTURE_AND_MODULAR_SPEC.md)** | Mobile App Architecture & Modular Specification | Practical feature-based Flutter design, state management, and mode routing. |
| **[03_DESIGN_SYSTEM](file:///e:/Stylinger%20MAD/docs/03_DESIGN_SYSTEM_AND_FLUTTER_UI_SPECIFICATION.md)** | Design System & Flutter UI Specification | "Editorial Ivory" brand tokens, Liquid Glass shader effects, typography, and widgets. |
| **[04_DATABASE_SCHEMA](file:///e:/Stylinger%20MAD/docs/04_DATABASE_SCHEMA_AND_TRANSACTION_FLOWS.md)** | Database Schema & Transaction Flows | Schema reconciliation, atomic S2 purchase flow, and entity lifecycles. |
| **[05_SCREEN_INVENTORY](file:///e:/Stylinger%20MAD/docs/05_SCREEN_INVENTORY_AND_USER_FLOWS.md)** | Consolidated Screen Inventory & User Flows | Validated 16-screen mobile inventory, wireframes, component hierarchy, and states. |
| **[06_INTEGRATIONS](file:///e:/Stylinger%20MAD/docs/06_INTEGRATIONS_AND_ISOLATION_CONTRACTS.md)** | Integrations & Isolation Contracts | Abstract Dart interfaces and offline mock data providers with zero external dependencies. |
| **[07_ROADMAP_PLAN](file:///e:/Stylinger%20MAD/docs/07_MOBILE_DEVELOPMENT_ROADMAP_AND_SPRINT_PLAN.md)** | Mobile Development Roadmap & Sprint Plan | 6-sprint execution plan for Phase 1 MVP targeting the Vadodara pilot. |

---

## 5. Mobile MVP Constraints & Offline-First Principles

To guarantee rapid, stable mobile development without infrastructure friction, the following engineering principles are strictly enforced:

1. **Zero External API Dependency:** The initial mobile application must build, launch, and execute all core user journeys with **no active internet connection, no API keys, and no live cloud services**.
2. **Abstract Interface Decoupling:** Every data operation (fetching inventory, creating a product, scanning a QR code, simulating a purchase) routes through an abstract repository interface (`IInventoryRepository`, `IWardrobeRepository`, etc.).
3. **High-Fidelity Local Mock Repositories:** Default implementations use typed in-memory Dart data stores pre-seeded with the 30 luxury fashion catalog items, 8 wardrobe pieces, and sample boutiques established during the web audit.
4. **No Premature Complexity:** Avoid complex monorepos, code-generation pipelines, micro-frameworks, or multi-threaded database engines (such as SQLite/Drift) for the initial prototype. Utilize Flutter's built-in `ChangeNotifier` / `ValueNotifier` or lightweight dependency injection.
5. **Preservation of Core Mechanics:** Even in offline mock mode, the core business mechanics (Auto-Wardrobe, In-Store QR matching, Honest Mode recommendations, and Low-Stock alerts) must function deterministically in code.
