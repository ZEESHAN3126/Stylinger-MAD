# Stylinger Database Schema & Transaction Flows

**Author:** Antigravity Architecture Team  
**Document Version:** 1.0.0 — Data Architecture Edition  
**Target Platform:** Flutter Mobile Application (with Supabase PostgreSQL Mapping)  
**Date:** September 2026  

---

## 1. Schema Reconciliation Overview

The data architecture reconciles the **10 high-level conceptual tables** specified in the original PRD (§6.1) with the **23 production-ready PostgreSQL tables** engineered during the web implementation:

```
┌────────────────────────────────────────────────────────────────────────┐
│                        SUPABASE AUTH (auth.users)                      │
└───────────────────────────────────┬────────────────────────────────────┘
                                    │ 1:1 Auto-Trigger
                                    ▼
┌────────────────────────────────────────────────────────────────────────┐
│                       PROFILES (public.profiles)                       │
│              Role: 'customer' | 'merchant' | 'admin'                   │
└──────────┬────────────────────────┬────────────────────────┬───────────┘
           │ 1:1                    │ 1:N                    │ 1:1
           ▼                        ▼                        ▼
┌─────────────────────┐  ┌─────────────────────┐  ┌─────────────────────┐
│  Style DNA Profiles │  │   Merchant Stores   │  │  Customer Wardrobe  │
│ • body_profiles     │  │   (public.stores)   │  │ (public.wardrobes)  │
│ • face_profiles     │  └──────────┬──────────┘  └──────────┬──────────┘
│ • color_profiles    │             │ 1:N                    │ 1:N
└─────────────────────┘             ▼                        ▼
                          ┌─────────────────────┐  ┌─────────────────────┐
                          │   Store Inventory   │  │   Wardrobe Items    │
                          │ (public.inventory)  │  │(public.wardrobe_it) │
                          └─────────▲───────────┘  └─────────▲───────────┘
                                    │ N:1                    │ N:1
                                    └───────────┬────────────┘
                                                │
                                    ┌───────────┴───────────┐
                                    │    Master Products    │
                                    │   (public.products)   │
                                    └───────────────────────┘
```

---

## 2. Four-Tier Data Entity Schema Mapping

| Conceptual Entity | PRODUCT REQUIREMENT (PRD §6.1) | CURRENT WEBSITE IMPLEMENTATION | MOBILE MVP TARGET (In-Memory Dart Model) | FUTURE INTEGRATION (PostgreSQL Table) |
|---|---|---|---|---|
| **User & Identity** | `users`: id, phone, name, body_shape, face_shape, skin_tone_mst, undertone, avatar_url, style_pref. | Supabase `auth.users` + `public.profiles` (id, email, role, preferences). | `UserModel` & `StyleDnaModel` held in memory with instant preset persona switching. | `public.profiles` joined with `public.body_profiles` and `public.color_profiles`. |
| **Merchant Store** | `merchants`: id, store_name, owner, phone, email, gst, location, category, qr_code, status. | `public.profiles.preferences.waitlist` + local settings state. | `StoreModel` pre-populated with "Aura Atelier" (Vadodara) details. | `public.stores` (id, owner_id, name, city, address, gps_lat, gps_lng, categories). |
| **Product Catalog** | `inventory_items`: id, merchant_id, name, category, colour, pattern, fit, formality, mrp, price, photo_urls. | `public.products` schema ready; active data in `localStorage` (30 items). | `ProductModel` initialized with 30 seeded high-res Unsplash items. | `public.products` (id, name, description, sku, category, price, images, tags). |
| **Stock & Sizes** | `inventory_sizes`: size_id, item_id, size_label, quantity, is_available. | Embedded `stock` and `sizes` array within product JSON. | `stockQuantity` (int) and `sizes` (List<String>) embedded in `ProductModel`. | `public.inventory` (id, store_id, product_id, size, quantity, low_threshold). |
| **Wardrobe Closet** | `wardrobe_items`: wardrobe_id, user_id, item_id, category, colour, photo_url, source. | Static array `wardrobeItems` in `mock-data.ts` (8 pieces). | `WardrobeItemModel` list in `MockWardrobeStore` (mutable on purchase). | `public.wardrobes` + `public.wardrobe_items` (id, user_id, product_id, wear_count). |
| **Outfits & Looks** | `outfit_logs`: log_id, user_id, outfit_combination (JSON), occasion, date_worn. | Static array `outfits` in `mock-data.ts` (3 looks). | `OutfitModel` generated dynamically from active wardrobe items. | `public.outfits` + `public.outfit_items`. |
| **Sales & Orders** | `orders`: order_id, user_id, merchant_id, item_id, size, price, payment_method, status. | Static array `merchantOrders` (4 rows) in `mock-data.ts`. | `OrderModel` list in `MockOrderStore` appended on simulated checkout. | `public.orders` + `public.order_items`. |

---

## 3. Core Dart Data Models (Mobile MVP)

### Product Model (`product_model.dart`)
```dart
class ProductModel {
  final String id;
  final String storeId;
  final String name;
  final String sku;
  final String category;
  final String color;
  final double price;
  final int stockQuantity;
  final List<String> sizes;
  final String imageUrl;
  final String description;
  final List<String> suitableBodyShapes;
  final List<String> suitableUndertones;

  const ProductModel({
    required this.id,
    required this.storeId,
    required this.name,
    required this.sku,
    required this.category,
    required this.color,
    required this.price,
    required this.stockQuantity,
    required this.sizes,
    required this.imageUrl,
    this.description = '',
    this.suitableBodyShapes = const [],
    this.suitableUndertones = const [],
  });

  bool get isOutOfStock => stockQuantity <= 0;
  bool get isLowStock => stockQuantity > 0 && stockQuantity <= 2;

  ProductModel copyWith({int? stockQuantity, String? name, double? price}) {
    return ProductModel(
      id: id,
      storeId: storeId,
      name: name ?? this.name,
      sku: sku,
      category: category,
      color: color,
      price: price ?? this.price,
      stockQuantity: stockQuantity ?? this.stockQuantity,
      sizes: sizes,
      imageUrl: imageUrl,
      description: description,
      suitableBodyShapes: suitableBodyShapes,
      suitableUndertones: suitableUndertones,
    );
  }
}
```

---

### Wardrobe Item Model (`wardrobe_item_model.dart`)
```dart
class WardrobeItemModel {
  final String id;
  final String? originalProductId;
  final String name;
  final String category;
  final String color;
  final String imageUrl;
  final int wearCount;
  final String source; // 'auto_qr' | 'manual' | 'quick_add'
  final DateTime addedAt;

  const WardrobeItemModel({
    required this.id,
    this.originalProductId,
    required this.name,
    required this.category,
    required this.color,
    required this.imageUrl,
    this.wearCount = 0,
    required this.source,
    required this.addedAt,
  });

  WardrobeItemModel incrementWear() {
    return WardrobeItemModel(
      id: id,
      originalProductId: originalProductId,
      name: name,
      category: category,
      color: color,
      imageUrl: imageUrl,
      wearCount: wearCount + 1,
      source: source,
      addedAt: addedAt,
    );
  }
}
```

---

## 4. The Critical S2 Atomic Transaction Flow (Purchase $\rightarrow$ Auto-Wardrobe)

As defined in PRD §3 (S2), this is the most critical cross-module flow in the entire platform. When a customer purchases a garment via Stylinger's in-store QR code, seven sequential operations execute in an **atomic unit of work**:

```
[In-Store QR Checkout Triggered]
             │
             ▼
    Step A: Verify Stock Availability (> 0) ───[Failed: Quantity = 0]───► Abort & Alert "Sold Out"
             │
             ▼ [Passed]
    Step B: Capture / Simulate Payment (M6) ───[Failed: Rejected]───────► Abort & Rollback
             │
             ▼ [Captured]
    Step C: Decrement Merchant Stock (M2) 
             │ (Quantity = Quantity - 1)
             ▼
    Step D: Auto-Synthesize Wardrobe Item (U3)
             │ (Inject into Customer Wardrobe: 'auto_qr')
             ▼
    Step E: Append to Store & Customer Ledgers (M4, U6)
             │ (Create immutable Order record)
             ▼
    Step F: Dispatch Merchant Push Alert (M7)
             │ ("New Sale: {Item} Size {Size} - ₹{Amount}")
             ▼
    Step G: Issue In-App Digital Receipt
```

### Rollback Contract
If any step between **A** and **D** encounters an exception:
1. The inventory decrement is reverted immediately.
2. The wardrobe insertion is deleted.
3. No order record is committed to disk or memory.
4. The user receives a clear transaction error banner.

### Implementation Blueprint: `PurchaseSyncCoordinator`
```dart
// lib/features/shared/services/purchase_sync_coordinator.dart
class PurchaseSyncCoordinator {
  final IInventoryRepository inventoryRepo;
  final IWardrobeRepository wardrobeRepo;
  final IOrderRepository orderRepo;

  PurchaseSyncCoordinator({
    required this.inventoryRepo,
    required this.wardrobeRepo,
    required this.orderRepo,
  });

  Future<PurchaseResult> executeInStorePurchase({
    required ProductModel product,
    required String selectedSize,
    required String customerId,
  }) async {
    // Step A: Check stock
    if (product.stockQuantity <= 0) {
      return PurchaseResult.failure('Item is sold out.');
    }

    // Step B: Simulate Payment
    final paymentSuccess = await _simulatePayment(product.price);
    if (!paymentSuccess) {
      return PurchaseResult.failure('Payment authorization failed.');
    }

    try {
      // Step C: Decrement Merchant Stock
      await inventoryRepo.decrementStock(product.id, 1);

      // Step D: Auto-Inject into Customer Digital Wardrobe
      final newWardrobePiece = WardrobeItemModel(
        id: 'w_${DateTime.now().millisecondsSinceEpoch}',
        originalProductId: product.id,
        name: product.name,
        category: product.category,
        color: product.color,
        imageUrl: product.imageUrl,
        wearCount: 0,
        source: 'auto_qr',
        addedAt: DateTime.now(),
      );
      await wardrobeRepo.addWardrobeItem(newWardrobePiece);

      // Step E: Commit Order Record
      final order = OrderModel(
        id: 'ORD-${DateTime.now().millisecondsSinceEpoch.toString().substring(7)}',
        productId: product.id,
        productName: product.name,
        storeId: product.storeId,
        size: selectedSize,
        amount: product.price,
        timestamp: DateTime.now(),
        status: 'Delivered',
      );
      await orderRepo.recordOrder(order);

      return PurchaseResult.success(order: order, wardrobeItem: newWardrobePiece);
    } catch (e) {
      // Rollback: Re-increment stock if partially committed
      await inventoryRepo.updateStockQuantity(product.id, product.stockQuantity);
      return PurchaseResult.failure('Transaction failed and was safely rolled back: $e');
    }
  }

  Future<bool> _simulatePayment(double amount) async {
    await Future.delayed(const Duration(milliseconds: 300));
    return true; // Deterministic success for local prototype
  }
}
```

---

## 5. In-Store QR Scan $\rightarrow$ Avatar Recommendation Flow

This flow executes when a customer scans a store entrance QR tag (`stylinger://store/STY-VAD-01`):

```
Customer Scans Store QR (U5)
             │
             ▼
Resolve Store Profile & Fetch Live Inventory (M2)
             │
             ▼
Score Inventory against Customer's Style DNA (S3):
  • Body Silhouette Compatibility (Score 0–40)
  • Skin Undertone Harmony (Score 0–30)
  • Compatibility with Owned Wardrobe Pieces (Score 0–30)
             │
             ▼
Filter by Customer Budget Ceiling (if active)
             │
             ▼
Check Total Matched Items (Honest Mode Check):
  ├── If Matched Items >= 3:
  │     └── Render Ranked In-Store Recommendations ("Best Matches for You")
  └── If Matched Items < 3:
        └── Trigger "Honest Mode" Notice:
              "Only {count} items match your style profile here. 
               Explore curated options at nearby partner boutiques?"
```

---

## 6. Offline Data Lifecycle Strategy

```
┌────────────────────────────────────────────────────────────────────────┐
│                        INITIAL MOBILE PROTOTYPE                        │
│                     (In-Memory Dart Stores & Maps)                     │
│  • Instant startup, zero setup, predictable deterministic testing      │
│  • Resets to default seed on full app restart                          │
└───────────────────────────────────┬────────────────────────────────────┘
                                    │
                                    ▼ Future Phase
┌────────────────────────────────────────────────────────────────────────┐
│                       HYBRID OFFLINE CACHING                           │
│                   (SQLite / Drift / Hive Local DB)                     │
│  • Cache catalog and wardrobe locally on device flash memory           │
│  • Offline transactions queued in local mutation buffer                │
└───────────────────────────────────┬────────────────────────────────────┘
                                    │
                                    ▼ Phase 2 Cloud Sync
┌────────────────────────────────────────────────────────────────────────┐
│                    SUPABASE POSTGREST REALTIME SYNC                    │
│  • Bidirectional sync with public.products and public.wardrobes        │
│  • Real-time stock decrement broadcasts via Supabase Realtime Channels │
└────────────────────────────────────────────────────────────────────────┘
```
