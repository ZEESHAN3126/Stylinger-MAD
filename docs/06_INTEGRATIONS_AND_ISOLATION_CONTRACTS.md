# Stylinger Integrations & Isolation Contracts

**Author:** Antigravity Architecture Team  
**Document Version:** 1.0.0 — Integration Specification  
**Target Platform:** Flutter Mobile Application  
**Date:** September 2026  

---

## 1. The Zero-Dependency Isolation Guarantee

To enable rapid, unblocked mobile development and deterministic testing across any developer workstation or continuous integration environment, the Stylinger Flutter application adheres to the **Zero-Dependency Isolation Guarantee**:

```
┌────────────────────────────────────────────────────────────────────────┐
│                   THE ZERO-DEPENDENCY GUARANTEE                        │
├────────────────────────────────────────────────────────────────────────┤
│ 1. ZERO Live Network Calls during initial prototype execution.         │
│ 2. ZERO External API Keys (Supabase, Firebase, Razorpay, Google Maps). │
│ 3. ZERO Cloud Database Initialization or Schema Configuration needed.  │
│ 4. 100% Deterministic Offline Testing across iOS and Android simulators│
└────────────────────────────────────────────────────────────────────────┘
```

All interactions between user interface controllers and data layers must route strictly through typed Dart **abstract contracts** (`abstract class`). Concrete implementations in the prototype layer are backed by in-memory stores initialized with the seeded 30-item fashion catalog, 8 wardrobe pieces, and Vadodara boutique records.

---

## 2. Four-Tier Integration Mapping

| Integration Domain | PRODUCT REQUIREMENT (PRD v2.0) | CURRENT WEBSITE IMPLEMENTATION | MOBILE MVP TARGET (In-Memory Mock) | FUTURE INTEGRATION (Production Cloud) |
|---|---|---|---|---|
| **Authentication** | Passwordless Phone OTP with 24h JWT refresh. | Supabase GoTrue Auth (Email & Password). Status: **`REAL`**. | `MockAuthRepository`: Instant one-tap persona switching without network calls. | `SupabaseAuthRepository`: `supabase_flutter` with Phone OTP SMS gateway. |
| **Catalog & Stock** | PostgreSQL + Redis live inventory cache. | Browser `localStorage` (30 products) + TanStack Query. Status: **`REAL`** *(Local)*. | `MockInventoryRepository`: In-memory product list seeded with 30 Unsplash fashion items. | `SupabaseInventoryRepository`: Real-time PostgREST sync with `public.products`. |
| **Wardrobe Closet** | Auto-synthesized wardrobe + vision tagging. | Static mock array `wardrobeItems` in `mock-data.ts`. Status: **`MOCK`**. | `MockWardrobeRepository`: In-memory mutable list dynamically updated on in-store checkout. | `SupabaseWardrobeRepository`: Remote user wardrobe cloud storage. |
| **Barcode / QR Scanning**| Native camera barcode decoder (ECL H, 30% tolerance).| CSS animated laser line + manual text input. Status: **`PLACEHOLDER`**. | `MockQrService`: Deterministic string parser + UI simulation buttons. | `MobileScannerService`: Real camera feed decoding via `mobile_scanner`. |
| **QR Code Generation** | Vector QR with 24h rotating salt and PDF export. | Mathematical SVG preview grid on web. Status: **`PLACEHOLDER`**. | `QrGeneratorService`: Pure Dart vector QR generation using `qr_flutter`. | Cloud dynamic rotating salt + PDF print spooler. |
| **AI Styling Engine** | MediaPipe Pose/Face + Monk Skin Tone + Polyvore model. | Static mock arrays in `mock-data.ts`. Status: **`MOCK`**. | `LocalStylingEngine`: Deterministic Dart rules engine scoring body & skin tone. | Remote Python FastAPI microservices on Hugging Face / Cloud Run. |
| **Payments & Checkout**| Razorpay UPI/Card gateway with T+2 settlement. | Static orders table in `/merchant/orders`. Status: **`MOCK`**. | `MockPaymentCoordinator`: Simulates instant 300ms payment authorization. | `RazorpayPaymentService`: Native `razorpay_flutter` SDK checkout sheet. |
| **Push Notifications** | FCM transactional, operational, and digest alerts. | Static in-app notification cards. Status: **`MOCK`**. | `MockNotificationService`: In-app styled banner alerts for low stock and sales. | `FcmNotificationService`: Firebase Cloud Messaging background notifications. |
| **Geolocation & Maps** | Vadodara boutique map with distance in km. | Static graphic map with 4 Mumbai boutiques. Status: **`MOCK`**. | `MockLocationService`: Static distance calculator for Vadodara retail hubs. | `GoogleMapsLocationService`: Real GPS geocoding via `geolocator`. |

---

## 3. Core Abstract Repository Contracts

### 1. `IAuthRepository`
```dart
// lib/core/contracts/i_auth_repository.dart
import '../models/user_model.dart';
import '../../routing/app_mode.dart';

abstract class IAuthRepository {
  Future<UserModel?> getCurrentUser();
  Future<UserModel> signInWithPersona({required AppMode targetMode});
  Future<void> signOut();
  Stream<UserModel?> get authStateChanges;
}
```

---

### 2. `IInventoryRepository`
```dart
// lib/core/contracts/i_inventory_repository.dart
import '../models/product_model.dart';

abstract class IInventoryRepository {
  Future<List<ProductModel>> getProducts({String? category, String? searchQuery});
  Future<ProductModel?> getProductById(String id);
  Future<void> saveProduct(ProductModel product);
  Future<void> deleteProduct(String id);
  Future<void> updateStockQuantity(String id, int quantity);
  Future<void> decrementStock(String id, int quantity);
}
```

---

### 3. `IWardrobeRepository`
```dart
// lib/core/contracts/i_wardrobe_repository.dart
import '../models/wardrobe_item_model.dart';

abstract class IWardrobeRepository {
  Future<List<WardrobeItemModel>> getWardrobeItems({String? category});
  Future<void> addWardrobeItem(WardrobeItemModel item);
  Future<void> removeWardrobeItem(String id);
  Future<void> incrementWearCount(String id);
}
```

---

### 4. `IStylingEngine`
```dart
// lib/core/contracts/i_styling_engine.dart
import '../models/product_model.dart';
import '../models/wardrobe_item_model.dart';
import '../models/outfit_model.dart';
import '../models/style_dna_model.dart';

class InStoreMatchResult {
  final ProductModel product;
  final int suitabilityScore; // 0 to 100
  final String matchReason;
  final List<WardrobeItemModel> compatibleWardrobePieces;

  InStoreMatchResult({
    required this.product,
    required this.suitabilityScore,
    required this.matchReason,
    required this.compatibleWardrobePieces,
  });
}

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

### 5. `IOrderRepository`
```dart
// lib/core/contracts/i_order_repository.dart
import '../models/order_model.dart';

abstract class IOrderRepository {
  Future<List<OrderModel>> getOrdersByStore(String storeId);
  Future<List<OrderModel>> getOrdersByCustomer(String customerId);
  Future<void> recordOrder(OrderModel order);
}
```

---

## 4. Concrete In-Memory Mock Implementations

### `MockInventoryRepository` (Seeded with 30 Luxury Fashion Items)
```dart
// lib/data/repositories/mock_inventory_repository.dart
import '../../core/contracts/i_inventory_repository.dart';
import '../../core/models/product_model.dart';
import '../mocks/seeded_products.dart';

class MockInventoryRepository implements IInventoryRepository {
  final List<ProductModel> _items = List.from(kSeededProducts);

  @override
  Future<List<ProductModel>> getProducts({String? category, String? searchQuery}) async {
    await Future.delayed(const Duration(milliseconds: 100)); // Simulate micro-delay
    return _items.where((item) {
      final matchesCategory = category == null || category == 'All' || item.category == category;
      final matchesQuery = searchQuery == null || searchQuery.isEmpty ||
          item.name.toLowerCase().contains(searchQuery.toLowerCase()) ||
          item.sku.toLowerCase().contains(searchQuery.toLowerCase());
      return matchesCategory && matchesQuery;
    }).toList();
  }

  @override
  Future<ProductModel?> getProductById(String id) async {
    try {
      return _items.firstWhere((p) => p.id == id);
    } catch (_) {
      return null;
    }
  }

  @override
  Future<void> saveProduct(ProductModel product) async {
    final index = _items.indexWhere((p) => p.id == product.id);
    if (index != -1) {
      _items[index] = product;
    } else {
      _items.insert(0, product);
    }
  }

  @override
  Future<void> deleteProduct(String id) async {
    _items.removeWhere((p) => p.id == id);
  }

  @override
  Future<void> updateStockQuantity(String id, int quantity) async {
    final index = _items.indexWhere((p) => p.id == id);
    if (index != -1) {
      _items[index] = _items[index].copyWith(stockQuantity: quantity);
    }
  }

  @override
  Future<void> decrementStock(String id, int quantity) async {
    final index = _items.indexWhere((p) => p.id == id);
    if (index != -1) {
      final current = _items[index].stockQuantity;
      final updated = (current - quantity).clamp(0, 9999);
      _items[index] = _items[index].copyWith(stockQuantity: updated);
    }
  }
}
```

---

### `LocalStylingEngine` (Deterministic Rules Engine)
Implements PRD §3 (S3) rules without requiring external Python or AI microservices:
```dart
// lib/data/repositories/mock_styling_engine.dart
import '../../core/contracts/i_styling_engine.dart';
import '../../core/models/product_model.dart';
import '../../core/models/wardrobe_item_model.dart';
import '../../core/models/outfit_model.dart';
import '../../core/models/style_dna_model.dart';

class LocalStylingEngine implements IStylingEngine {
  @override
  Future<OutfitModel> generateDailyOutfit({
    required List<WardrobeItemModel> wardrobe,
    required String occasion,
    required StyleDnaModel styleDna,
  }) async {
    await Future.delayed(const Duration(milliseconds: 150));

    // Fallback: Pick first matching pieces from wardrobe
    final tops = wardrobe.where((w) => w.category == 'Shirts' || w.category == 'T-Shirts').toList();
    final bottoms = wardrobe.where((w) => w.category == 'Jeans' || w.category == 'Trousers').toList();
    final outerwear = wardrobe.where((w) => w.category == 'Outerwear').toList();

    final selectedPieces = <WardrobeItemModel>[];
    if (tops.isNotEmpty) selectedPieces.add(tops.first);
    if (bottoms.isNotEmpty) selectedPieces.add(bottoms.first);
    if (outerwear.isNotEmpty) selectedPieces.add(outerwear.first);

    return OutfitModel(
      id: 'outfit_today',
      title: '$occasion Curated Edit',
      pieces: selectedPieces,
      compatibilityScore: 94,
      weatherSuitability: '28° · Ideal for Light Fabrics',
      occasion: occasion,
    );
  }

  @override
  Future<List<InStoreMatchResult>> rankStoreInventory({
    required List<ProductModel> storeInventory,
    required List<WardrobeItemModel> customerWardrobe,
    required StyleDnaModel styleDna,
    double? budgetCeiling,
  }) async {
    await Future.delayed(const Duration(milliseconds: 200));

    final results = <InStoreMatchResult>[];

    for (final product in storeInventory) {
      if (budgetCeiling != null && product.price > budgetCeiling) continue;

      int score = 70; // Base score

      // Check skin undertone harmony
      if (product.suitableUndertones.contains(styleDna.undertone)) {
        score += 18;
      }

      // Check body silhouette compatibility
      if (product.suitableBodyShapes.contains(styleDna.bodyShape)) {
        score += 12;
      }

      // Check pairing with owned wardrobe
      final matchingClosetPieces = customerWardrobe.where((w) {
        return (product.category == 'Outerwear' && (w.category == 'Shirts' || w.category == 'Jeans')) ||
               (product.category == 'Shirts' && w.category == 'Trousers');
      }).take(2).toList();

      results.add(InStoreMatchResult(
        product: product,
        suitabilityScore: score.clamp(0, 99),
        matchReason: 'Flattering cut for ${styleDna.bodyShape} silhouette with ${styleDna.undertone} undertone',
        compatibleWardrobePieces: matchingClosetPieces,
      ));
    }

    // Sort descending by score
    results.sort((a, b) => b.suitabilityScore.compareTo(a.suitabilityScore));
    return results;
  }
}
```

---

## 5. Future Cloud Integration Injection Blueprint

When the project transitions from local mock development to cloud integration (Phase 2), **not a single UI widget will be modified**. Only the dependency injection initialization in `main.dart` will be updated:

```dart
// lib/main.dart
void main() {
  WidgetsFlutterBinding.ensureInitialized();

  // PROTOTYPE LAYER: 100% Offline Mocks
  final inventoryRepo = MockInventoryRepository();
  final wardrobeRepo = MockWardrobeRepository();
  final stylingEngine = LocalStylingEngine();
  final orderRepo = MockOrderRepository();
  final authRepo = MockAuthRepository();

  /* 
  // PHASE 2 CLOUD INTEGRATION: Simply swap repositories here:
  final supabase = Supabase.instance.client;
  final inventoryRepo = SupabaseInventoryRepository(supabase);
  final wardrobeRepo = SupabaseWardrobeRepository(supabase);
  final stylingEngine = RemoteFastApiStylingEngine('https://api.stylinger.in');
  final authRepo = SupabaseAuthRepository(supabase);
  */

  final purchaseCoordinator = PurchaseSyncCoordinator(
    inventoryRepo: inventoryRepo,
    wardrobeRepo: wardrobeRepo,
    orderRepo: orderRepo,
  );

  runApp(StylingerApp(
    inventoryRepo: inventoryRepo,
    wardrobeRepo: wardrobeRepo,
    stylingEngine: stylingEngine,
    authRepo: authRepo,
    purchaseCoordinator: purchaseCoordinator,
  ));
}
```
