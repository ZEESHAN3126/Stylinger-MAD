# Stylinger Mobile App Architecture & Modular Specification

**Author:** Antigravity Architecture Team  
**Document Version:** 1.0.0 — Flutter Architecture Edition  
**Target Platform:** Flutter 3.x / Dart 3.x (Android & iOS)  
**Date:** September 2026  

---

## 1. Architectural Philosophy: Pragmatic & Feature-Based

To fulfill the project requirements without falling into the trap of premature over-engineering, the Stylinger Flutter application is architected around **three core tenets**:

1. **Practical Feature-Based Modularity:** The project code is structured around functional business features (`features/merchant/`, `features/customer/`, `features/auth/`) rather than complex multi-package monorepos or excessive abstraction layers.
2. **Abstract Interface Decoupling:** Every state controller and UI component depends only on abstract repository interfaces (`IInventoryRepository`, `IWardrobeRepository`, `IStylingEngine`). This enables swapping in-memory mock data stores with live Supabase or REST connectors in the future without modifying UI widgets.
3. **100% Offline-First Execution:** The application executes smoothly on any iOS Simulator or Android Emulator with **zero network connectivity, zero API keys, and zero external software dependencies**.

---

## 2. Four-Tier Architectural Analysis

| Architecture Layer | PRODUCT REQUIREMENT (PRD v2.0) | CURRENT WEBSITE IMPLEMENTATION | MOBILE MVP TARGET (Flutter) | FUTURE INTEGRATION (Phase 2+) |
|---|---|---|---|---|
| **App Shell & Routing** | Single Flutter app, dual-mode routing, modular monorepo packages. | TanStack Router file-based tree (`/merchant/*` vs `/app/*`) sharing `DashShell`. | **Single Flutter App with dynamic `ModeRouter`** switching between Customer Shell and Merchant Shell. | Dynamic feature flagging & white-label brand theme swapping. |
| **State Management** | Modular controller, services, and isolated event pipelines. | TanStack React Query + React Context (`AuthProvider`, `useProducts`). | **Lightweight `ChangeNotifier` / `ValueNotifier` controllers** scoped via simple Provider or InheritedWidget. No BLoC boilerplate. | Global offline event sync queue, Hydrated state caching. |
| **Persistence Engine** | PostgreSQL + Redis cache + S3/Cloudinary media. | Browser `localStorage` (30 seeded products) + Supabase Auth GoTrue. | **Typed In-Memory Data Stores** (`MockInventoryStore`, `MockWardrobeStore`) seeded with 30 fashion pieces. No SQLite/Drift setup required for MVP. | Supabase PostgREST client (`supabase_flutter`) + local SQLite/Drift offline-first sync. |
| **Cross-Module Sync** | S2 Atomic Transaction Engine with strict error rollback. | No cross-module sync (catalog completely isolated from customer data). | **`PurchaseSyncCoordinator`**: Local synchronous transaction coordinator executing stock decrement + wardrobe injection atomically. | Distributed cloud transaction with Redis lock and retry queue. |

---

## 3. Practical Directory Structure

The Flutter project structure avoids multi-repo tooling (`melos`) in favor of a clean, highly readable single-package directory layout:

```
stylinger_mobile/
├── lib/
│   ├── main.dart                          # App initialization & root provider injection
│   │
│   ├── core/                              # Shared application kernel & design system
│   │   ├── constants/
│   │   │   ├── app_colors.dart            # "Editorial Ivory", Deep Cobalt, Charcoal tokens
│   │   │   ├── app_typography.dart        # Instrument Serif & Inter text themes
│   │   │   └── app_dimens.dart            # Radii, padding, elevation constants
│   │   ├── widgets/
│   │   │   ├── liquid_glass_card.dart     # Frosted glass container with BackdropFilter
│   │   │   ├── editorial_header.dart      # Elegant serif title & action block
│   │   │   ├── pill_chip.dart             # Interactive category & status chip
│   │   │   ├── stylinger_button.dart      # Luxury cobalt/charcoal action button
│   │   │   └── empty_state_view.dart      # Reusable empty list placeholder
│   │   └── contracts/                     # Abstract interfaces for decoupling
│   │       ├── i_auth_repository.dart
│   │       ├── i_inventory_repository.dart
│   │       ├── i_wardrobe_repository.dart
│   │       ├── i_styling_engine.dart
│   │       └── i_order_repository.dart
│   │
│   ├── data/                              # Concrete data implementations & seeded mocks
│   │   ├── models/
│   │   │   ├── product_model.dart         # Product entity with SKU, sizes, pricing
│   │   │   ├── wardrobe_item_model.dart   # Customer wardrobe piece with wear count
│   │   │   ├── outfit_model.dart          # Multi-piece look with occasion & score
│   │   │   ├── store_model.dart           # Merchant boutique details & location
│   │   │   └── style_dna_model.dart       # Body silhouette, face shape, Monk skin tone
│   │   ├── mocks/
│   │   │   ├── seeded_products.dart       # The 30 luxury fashion catalog items
│   │   │   ├── seeded_wardrobe.dart       # The 8 customer wardrobe items
│   │   │   ├── seeded_boutiques.dart      # Vadodara partner stores (Alkapuri, Mandvi)
│   │   │   └── seeded_style_dna.dart      # Preset user style profiles
│   │   └── repositories/                  # In-memory mock repository implementations
│   │       ├── mock_auth_repository.dart
│   │       ├── mock_inventory_repository.dart
│   │       ├── mock_wardrobe_repository.dart
│   │       ├── mock_styling_engine.dart
│   │       └── mock_order_repository.dart
│   │
│   ├── routing/                           # Navigation & mode routing
│   │   ├── app_mode.dart                  # Enum: AppMode.customer | AppMode.merchant
│   │   ├── mode_controller.dart           # Mode switching state notifier
│   │   └── app_router.dart                # Route definitions & transitions
│   │
│   └── features/                          # Feature modules
│       ├── auth/                          # S1: Authentication & persona switcher
│       │   ├── controllers/auth_controller.dart
│       │   └── screens/
│       │       ├── welcome_screen.dart
│       │       └── persona_selector_screen.dart
│       │
│       ├── merchant/                      # Merchant Mode Tree (M1–M7)
│       │   ├── controllers/
│       │   │   ├── inventory_controller.dart
│       │   │   └── analytics_controller.dart
│       │   └── screens/
│       │       ├── merchant_shell.dart         # Bottom nav: Dashboard, Inventory, QR, Analytics, Store
│       │       ├── merchant_dashboard_screen.dart
│       │       ├── inventory_screen.dart
│       │       ├── product_edit_screen.dart
│       │       ├── qr_generator_screen.dart
│       │       └── store_settings_screen.dart
│       │
│       ├── customer/                      # Customer Mode Tree (U1–U6)
│       │   ├── controllers/
│       │   │   ├── wardrobe_controller.dart
│       │   │   ├── scanner_controller.dart
│       │   │   └── outfit_controller.dart
│       │   └── screens/
│       │       ├── customer_shell.dart         # Bottom nav: Home, Wardrobe, Scanner, Boutiques, Profile
│       │       ├── customer_home_screen.dart   # Daily outfit & weather hero
│       │       ├── wardrobe_screen.dart        # Digital wardrobe grid
│       │       ├── in_store_scanner_screen.dart# Simulated camera viewfinder
│       │       ├── in_store_stylist_screen.dart# Store inventory & "Pair with what I own"
│       │       ├── boutique_discovery_screen.dart# Vadodara boutique list
│       │       └── customer_profile_screen.dart# Style DNA & Monk skin tone selector
│       │
│       └── shared/                        # S2: Critical connector & orchestrator
│           └── services/
│               └── purchase_sync_coordinator.dart # S2 Atomic purchase coordinator
└── test/                                  # Pure Dart unit tests for core logic
    ├── inventory_logic_test.dart
    ├── styling_engine_test.dart
    └── purchase_atomic_sync_test.dart
```

---

## 4. Mode Routing & Navigation Architecture

### Dual-Mode Shell Architecture
Rather than maintaining two distinct binaries, Stylinger uses a unified root shell with dynamic subtree switching.

```
                                  ┌───────────────────────────┐
                                  │      ModeController       │
                                  │ (AppMode: customer/merch) │
                                  └─────────────┬─────────────┘
                                                │
                                    ┌───────────┴───────────┐
                                    ▼                       ▼
                     ┌───────────────────────┐   ┌───────────────────────┐
                     │     CustomerShell     │   │     MerchantShell     │
                     │ (BottomNavigationBar) │   │ (BottomNavigationBar) │
                     └──────────┬────────────┘   └───────────┬───────────┘
                                │                            │
    ┌──────────┬──────────┬─────┴────┬──────────┐    ┌───────┴───┬───────┬───────┐
    ▼          ▼          ▼          ▼          ▼    ▼           ▼       ▼       ▼
 [Home]   [Wardrobe]  [Scanner]  [Stores]   [Profile] [Dashboard] [Inv]  [QR] [Settings]
```

### Mode Switching Mechanism
Users can toggle between Customer Mode and Merchant Mode at any time via a high-level switch in either profile screen:
```dart
// mode_controller.dart
enum AppMode { customer, merchant }

class ModeController extends ChangeNotifier {
  AppMode _currentMode = AppMode.customer;
  
  AppMode get currentMode => _currentMode;
  bool get isCustomer => _currentMode == AppMode.customer;
  bool get isMerchant => _currentMode == AppMode.merchant;
  
  void setMode(AppMode mode) {
    if (_currentMode != mode) {
      _currentMode = mode;
      notifyListeners();
    }
  }

  void toggleMode() {
    setMode(isCustomer ? AppMode.merchant : AppMode.customer);
  }
}
```

---

## 5. State Management: Pragmatic `ChangeNotifier`

To keep development fast, bug-free, and accessible without external dependencies, state management relies on standard Flutter `ChangeNotifier` classes:

1. **`InventoryController`:** Manages the product list, category filtering, search queries, manual stock adjustments, and low-stock detection.
2. **`WardrobeController`:** Manages customer wardrobe items, category tabs, wear counter increments, and newly auto-synced purchases.
3. **`ScannerController`:** Manages QR viewfinder state, simulated barcode recognition, session creation, and error states.
4. **`OutfitController`:** Drives daily outfit assembly, occasion filters, and weather simulation.

### Controller Implementation Blueprint
```dart
class InventoryController extends ChangeNotifier {
  final IInventoryRepository _repository;
  
  InventoryController(this._repository) {
    loadProducts();
  }

  List<ProductModel> _products = [];
  String _searchQuery = '';
  String _selectedCategory = 'All';
  bool _isLoading = false;

  List<ProductModel> get products {
    return _products.where((p) {
      final matchesCat = _selectedCategory == 'All' || p.category == _selectedCategory;
      final matchesQuery = _searchQuery.isEmpty || 
          p.name.toLowerCase().contains(_searchQuery.toLowerCase()) ||
          p.sku.toLowerCase().contains(_searchQuery.toLowerCase());
      return matchesCat && matchesQuery;
    }).toList();
  }

  Future<void> loadProducts() async {
    _isLoading = true;
    notifyListeners();
    _products = await _repository.getProducts();
    _isLoading = false;
    notifyListeners();
  }

  Future<void> updateStock(String productId, int newQuantity) async {
    await _repository.updateStockQuantity(productId, newQuantity);
    final index = _products.indexWhere((p) => p.id == productId);
    if (index != -1) {
      _products[index] = _products[index].copyWith(stockQuantity: newQuantity);
      notifyListeners();
    }
  }
}
```

---

## 6. Abstract Repository Interfaces

All data operations are defined by pure abstract contracts in `lib/core/contracts/`:

```dart
// i_inventory_repository.dart
abstract class IInventoryRepository {
  Future<List<ProductModel>> getProducts();
  Future<ProductModel?> getProductById(String id);
  Future<void> saveProduct(ProductModel product);
  Future<void> deleteProduct(String id);
  Future<void> updateStockQuantity(String id, int quantity);
  Future<void> decrementStock(String id, int quantity);
}

// i_wardrobe_repository.dart
abstract class IWardrobeRepository {
  Future<List<WardrobeItemModel>> getWardrobeItems();
  Future<void> addWardrobeItem(WardrobeItemModel item);
  Future<void> removeWardrobeItem(String id);
  Future<void> incrementWearCount(String id);
}

// i_styling_engine.dart
abstract class IStylingEngine {
  Future<OutfitModel> generateDailyOutfit({
    required List<WardrobeItemModel> wardrobe,
    required String occasion,
    required StyleDnaModel styleDna,
  });

  Future<List<InStoreMatchResult>> rankStoreInventory({
    required List<ProductModel> storeInventory,
    required List<WardrobeItemModel> customerWardrobe,
    required StyleDnaModel styleDna,
    double? budgetCeiling,
  });
}
```

---

## 7. Deep Linking & QR Route Resolution

The mobile application handles two primary custom URL deep-link schemes:

1. **Store Entrance Scan:** `stylinger://store/{store_id}?session={token}`  
   $\rightarrow$ Resolves to `InStoreStylistScreen(storeId: store_id)`  
   $\rightarrow$ Instantly loads boutique profile and begins AI inventory matching.

2. **Garment Tag Scan:** `stylinger://item/{store_id}/{product_id}`  
   $\rightarrow$ Resolves to `ProductDetailModal(productId: product_id, storeId: store_id)`  
   $\rightarrow$ Displays garment specs and opens "Pair with what I own" bottom sheet.

### Mock QR Parser
In offline mock mode, the `ScannerController` parses these string URLs deterministically:
```dart
class QrParser {
  static ParsedQrResult parse(String rawData) {
    final uri = Uri.tryParse(rawData);
    if (uri != null && uri.scheme == 'stylinger') {
      if (uri.host == 'store') {
        final storeId = uri.pathSegments.isNotEmpty ? uri.pathSegments.first : 'STY-VAD-01';
        return ParsedQrResult(type: QrType.storeEntrance, id: storeId);
      } else if (uri.host == 'item') {
        final storeId = uri.pathSegments.length > 0 ? uri.pathSegments[0] : '';
        final itemId = uri.pathSegments.length > 1 ? uri.pathSegments[1] : '';
        return ParsedQrResult(type: QrType.garmentTag, id: itemId, extra: storeId);
      }
    }
    // Fallback: SKU match
    return ParsedQrResult(type: QrType.skuTag, id: rawData);
  }
}
```

---

## 8. Summary of Architectural Guarantees

1. **Zero External Breakage:** No third-party network failure or auth expiration can block app development or testing.
2. **Zero Monorepo Overhead:** All code lives in a single standard Flutter project with no complex build steps.
3. **Seamless Future Migration:** When cloud integration begins, developers simply write `SupabaseInventoryRepository implements IInventoryRepository` and swap the dependency injection line in `main.dart`.
