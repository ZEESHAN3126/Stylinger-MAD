package com.stylinger.app.data.mock

import com.stylinger.app.domain.model.AppRole
import com.stylinger.app.domain.model.Product
import com.stylinger.app.domain.model.Store
import com.stylinger.app.domain.model.StyleDna
import com.stylinger.app.domain.model.User
import com.stylinger.app.domain.model.WardrobeItem

object MockPersonas {
    val CustomerAnanya = User(
        id = "usr_ananya_01",
        name = "Ananya Vahora",
        role = AppRole.CUSTOMER,
        city = "Vadodara",
        styleDna = StyleDna(
            bodyShape = "Hourglass",
            faceShape = "Oval",
            monkSkinToneScale = 5,
            undertone = "Warm"
        )
    )

    val MerchantAura = User(
        id = "usr_zeeshan_01",
        name = "Zeeshan Vahora",
        role = AppRole.MERCHANT,
        city = "Vadodara",
        storeId = "STY-VAD-01"
    )
}

object MockStores {
    val AuraAtelier = Store(
        id = "STY-VAD-01",
        name = "Aura Atelier",
        ownerName = "Zeeshan Vahora",
        city = "Vadodara",
        area = "Alkapuri",
        address = "14 Galleria Arcade, RC Dutt Road, Alkapuri, Vadodara",
        categories = listOf("Outerwear", "Shirts", "Ethnic", "Trousers"),
        acceptedPayments = listOf("UPI", "Card", "Cash"),
        rating = 4.9
    )

    val MandviHeritage = Store(
        id = "STY-VAD-02",
        name = "Mandvi Heritage Weaves",
        ownerName = "Kavita Patel",
        city = "Vadodara",
        area = "Mandvi",
        address = "Old City Gate, Mandvi, Vadodara",
        categories = listOf("Ethnic", "Dresses", "Accessories"),
        acceptedPayments = listOf("UPI", "Cash"),
        rating = 4.7
    )
}

object MockWardrobeData {
    val initialItems = listOf(
        WardrobeItem(
            id = "w_001",
            name = "Structured Camel Wool Overcoat",
            category = "Outerwear",
            color = "Camel",
            imageUrl = "https://images.unsplash.com/photo-1539533018447-63fcce2678e3?w=800&auto=format&fit=crop&q=80",
            wearCount = 14,
            source = "auto_qr"
        ),
        WardrobeItem(
            id = "w_002",
            name = "Relaxed Poplin Shirt",
            category = "Shirts",
            color = "Optic White",
            imageUrl = "https://images.unsplash.com/photo-1598033129183-c4f50c736f10?w=800&auto=format&fit=crop&q=80",
            wearCount = 22,
            source = "manual"
        ),
        WardrobeItem(
            id = "w_003",
            name = "Tailored Wide-Leg Trousers",
            category = "Trousers",
            color = "Charcoal",
            imageUrl = "https://images.unsplash.com/photo-1509551388413-e18d0ac5d495?w=800&auto=format&fit=crop&q=80",
            wearCount = 18,
            source = "auto_qr"
        ),
        WardrobeItem(
            id = "w_004",
            name = "Chunky Ribbed Cashmere Sweater",
            category = "Knitwear",
            color = "Oatmeal",
            imageUrl = "https://images.unsplash.com/photo-1576566588028-4147f3842f27?w=800&auto=format&fit=crop&q=80",
            wearCount = 9,
            source = "manual"
        ),
        WardrobeItem(
            id = "w_005",
            name = "Japanese Selvedge Denim Jeans",
            category = "Jeans",
            color = "Indigo",
            imageUrl = "https://images.unsplash.com/photo-1542272604-780c96856592?w=800&auto=format&fit=crop&q=80",
            wearCount = 31,
            source = "auto_qr"
        ),
        WardrobeItem(
            id = "w_006",
            name = "Silk Slip Evening Dress",
            category = "Dresses",
            color = "Midnight Black",
            imageUrl = "https://images.unsplash.com/photo-1595777457583-95e059d581b8?w=800&auto=format&fit=crop&q=80",
            wearCount = 4,
            source = "manual"
        ),
        WardrobeItem(
            id = "w_007",
            name = "Minimalist Leather Penny Loafers",
            category = "Footwear",
            color = "Espresso",
            imageUrl = "https://images.unsplash.com/photo-1614252369475-531eba835eb1?w=800&auto=format&fit=crop&q=80",
            wearCount = 27,
            source = "auto_qr"
        ),
        WardrobeItem(
            id = "w_008",
            name = "Italian Calfskin Belt",
            category = "Accessories",
            color = "Cognac",
            imageUrl = "https://images.unsplash.com/photo-1624222247344-550fb60583dc?w=800&auto=format&fit=crop&q=80",
            wearCount = 40,
            source = "auto_qr"
        )
    )
}

object MockProductsData {
    val initialProducts = listOf(
        Product(
            id = "STY-001",
            storeId = "STY-VAD-01",
            name = "Oversized Double-Breasted Trench Coat",
            sku = "OTR-001",
            category = "Outerwear",
            color = "Classic Khaki",
            price = 8490.0,
            stockQuantity = 4,
            sizes = listOf("S", "M", "L"),
            imageUrl = "https://images.unsplash.com/photo-1591047139829-d91aecb6caea?w=800&auto=format&fit=crop&q=80",
            description = "Tailored water-repellent trench coat with storm flap and tortoiseshell buttons.",
            suitableBodyShapes = listOf("Rectangle", "Hourglass", "Pear"),
            suitableUndertones = listOf("Warm", "Neutral")
        ),
        Product(
            id = "STY-002",
            storeId = "STY-VAD-01",
            name = "Italian Wool Tailored Blazer",
            sku = "BLZ-002",
            category = "Outerwear",
            color = "Midnight Navy",
            price = 7290.0,
            stockQuantity = 2, // Low stock <= 2
            sizes = listOf("38", "40", "42"),
            imageUrl = "https://images.unsplash.com/photo-1507679799987-c73779587ccf?w=800&auto=format&fit=crop&q=80",
            description = "Structured shoulder blazer crafted from premium Italian super 120s wool.",
            suitableBodyShapes = listOf("Hourglass", "Inverted Triangle"),
            suitableUndertones = listOf("Cool", "Neutral")
        ),
        Product(
            id = "STY-003",
            storeId = "STY-VAD-01",
            name = "Raw Silk Bandhgala Jacket",
            sku = "ETH-003",
            category = "Ethnic",
            color = "Ivory Gold",
            price = 9990.0,
            stockQuantity = 3,
            sizes = listOf("38", "40", "42", "44"),
            imageUrl = "https://images.unsplash.com/photo-1610030469983-98e550d6193c?w=800&auto=format&fit=crop&q=80",
            description = "Hand-loomed raw silk festive jacket with antique metal buttons for wedding edits.",
            suitableBodyShapes = listOf("Rectangle", "Hourglass", "Apple"),
            suitableUndertones = listOf("Warm", "Neutral")
        ),
        Product(
            id = "STY-004",
            storeId = "STY-VAD-01",
            name = "Crisp Poplin Oxford Shirt",
            sku = "SHT-004",
            category = "Shirts",
            color = "Sky Blue",
            price = 2490.0,
            stockQuantity = 8,
            sizes = listOf("S", "M", "L", "XL"),
            imageUrl = "https://images.unsplash.com/photo-1602810318383-e386cc2a3ccf?w=800&auto=format&fit=crop&q=80",
            description = "Everyday 100% Giza cotton button-down shirt with mother-of-pearl buttons.",
            suitableBodyShapes = listOf("Rectangle", "Hourglass", "Pear", "Apple", "Inverted Triangle"),
            suitableUndertones = listOf("Cool", "Neutral", "Warm")
        ),
        Product(
            id = "STY-005",
            storeId = "STY-VAD-01",
            name = "Pleated Linen Formal Trousers",
            sku = "TRS-005",
            category = "Trousers",
            color = "Olive Stone",
            price = 3290.0,
            stockQuantity = 1, // Low stock <= 2
            sizes = listOf("30", "32", "34"),
            imageUrl = "https://images.unsplash.com/photo-1473966968600-fa801b869a1a?w=800&auto=format&fit=crop&q=80",
            description = "High-rise tailored trousers with double forward pleats and side adjusters.",
            suitableBodyShapes = listOf("Hourglass", "Pear", "Rectangle"),
            suitableUndertones = listOf("Warm", "Neutral")
        ),
        Product(
            id = "STY-006",
            storeId = "STY-VAD-01",
            name = "Merino Wool Knit Polo",
            sku = "KNT-006",
            category = "Knitwear",
            color = "Forest Green",
            price = 3890.0,
            stockQuantity = 6,
            sizes = listOf("S", "M", "L"),
            imageUrl = "https://images.unsplash.com/photo-1620799140408-edc6dcb6d633?w=800&auto=format&fit=crop&q=80",
            description = "Fine gauge 16-micron merino wool knit with three-button placket.",
            suitableBodyShapes = listOf("Hourglass", "Inverted Triangle"),
            suitableUndertones = listOf("Warm", "Cool")
        ),
        Product(
            id = "STY-007",
            storeId = "STY-VAD-01",
            name = "Selvedge Tapered Denim Jeans",
            sku = "JNS-007",
            category = "Jeans",
            color = "Deep Indigo",
            price = 4590.0,
            stockQuantity = 5,
            sizes = listOf("30", "32", "34", "36"),
            imageUrl = "https://images.unsplash.com/photo-1541099649105-f69ad21f3246?w=800&auto=format&fit=crop&q=80",
            description = "13.5oz shuttle-loomed Japanese denim with red selvedge ID.",
            suitableBodyShapes = listOf("Rectangle", "Hourglass", "Inverted Triangle"),
            suitableUndertones = listOf("Cool", "Neutral")
        ),
        Product(
            id = "STY-008",
            storeId = "STY-VAD-01",
            name = "Heavyweight Supima Cotton T-Shirt",
            sku = "TSH-008",
            category = "T-Shirts",
            color = "Bone White",
            price = 1490.0,
            stockQuantity = 12,
            sizes = listOf("S", "M", "L", "XL"),
            imageUrl = "https://images.unsplash.com/photo-1521572267360-ee0c2909d518?w=800&auto=format&fit=crop&q=80",
            description = "240 GSM combed Supima cotton crewneck with reinforced collar ribbing.",
            suitableBodyShapes = listOf("Rectangle", "Hourglass", "Pear", "Apple", "Inverted Triangle"),
            suitableUndertones = listOf("Warm", "Neutral", "Cool")
        ),
        Product(
            id = "STY-009",
            storeId = "STY-VAD-01",
            name = "Silk Chiffon Maxi Dress",
            sku = "DRS-009",
            category = "Dresses",
            color = "Earthy Terracotta",
            price = 6890.0,
            stockQuantity = 0, // Sold out = 0
            sizes = listOf("XS", "S", "M"),
            imageUrl = "https://images.unsplash.com/photo-1572804013309-59a88b7e92f1?w=800&auto=format&fit=crop&q=80",
            description = "Tiered flowing silhouette in hand-dyed natural terracotta silk.",
            suitableBodyShapes = listOf("Hourglass", "Pear"),
            suitableUndertones = listOf("Warm")
        ),
        Product(
            id = "STY-010",
            storeId = "STY-VAD-01",
            name = "Handcrafted Leather Chelsea Boots",
            sku = "FTW-010",
            category = "Footwear",
            color = "Dark Walnut",
            price = 7990.0,
            stockQuantity = 4,
            sizes = listOf("40", "41", "42", "43"),
            imageUrl = "https://images.unsplash.com/photo-1638247025967-b4e38f787b76?w=800&auto=format&fit=crop&q=80",
            description = "Goodyear welted Chelsea boots crafted from full-grain calf leather.",
            suitableBodyShapes = listOf("Rectangle", "Hourglass", "Pear", "Apple", "Inverted Triangle"),
            suitableUndertones = listOf("Warm", "Neutral")
        ),
        // Additional 20 products for full 30-item catalog
        Product("STY-011", "STY-VAD-01", "Cashmere Blend Cardigan", "KNT-011", "Knitwear", "Heather Gray", 4990.0, 3, listOf("S", "M", "L"), "https://images.unsplash.com/photo-1434389677669-e08b4cac3105?w=800&auto=format&fit=crop&q=80"),
        Product("STY-012", "STY-VAD-01", "Linen Spread Collar Shirt", "SHT-012", "Shirts", "Sage Green", 2690.0, 5, listOf("M", "L"), "https://images.unsplash.com/photo-1596755094514-f87e34085b2c?w=800&auto=format&fit=crop&q=80"),
        Product("STY-013", "STY-VAD-01", "Chino Slim Trousers", "TRS-013", "Trousers", "Desert Sand", 2790.0, 7, listOf("30", "32", "34"), "https://images.unsplash.com/photo-1624378439575-d8705ad7ae80?w=800&auto=format&fit=crop&q=80"),
        Product("STY-014", "STY-VAD-01", "Festive Silk Kurta", "ETH-014", "Ethnic", "Royal Crimson", 3490.0, 2, listOf("38", "40", "42"), "https://images.unsplash.com/photo-1583391733956-3750e0ff4e8b?w=800&auto=format&fit=crop&q=80"),
        Product("STY-015", "STY-VAD-01", "Washed Cotton Field Jacket", "OTR-015", "Outerwear", "Army Green", 5990.0, 4, listOf("M", "L", "XL"), "https://images.unsplash.com/photo-1544441893-675973e31985?w=800&auto=format&fit=crop&q=80"),
        Product("STY-016", "STY-VAD-01", "Waffle Knit Crewneck", "TSH-016", "T-Shirts", "Charcoal Melange", 1690.0, 9, listOf("S", "M", "L"), "https://images.unsplash.com/photo-1581655353564-df123a1eb820?w=800&auto=format&fit=crop&q=80"),
        Product("STY-017", "STY-VAD-01", "White Denim Straight Leg", "JNS-017", "Jeans", "Ecru White", 4190.0, 3, listOf("30", "32"), "https://images.unsplash.com/photo-1565084888279-aca607ecce0c?w=800&auto=format&fit=crop&q=80"),
        Product("STY-018", "STY-VAD-01", "Satin Midi Wrap Dress", "DRS-018", "Dresses", "Emerald Green", 5490.0, 1, listOf("S", "M"), "https://images.unsplash.com/photo-1539008835657-9e8e9680c956?w=800&auto=format&fit=crop&q=80"),
        Product("STY-019", "STY-VAD-01", "Monk Strap Dress Shoes", "FTW-019", "Footwear", "Oxblood", 6990.0, 2, listOf("41", "42"), "https://images.unsplash.com/photo-1533867617858-e7b97e060509?w=800&auto=format&fit=crop&q=80"),
        Product("STY-020", "STY-VAD-01", "Structured Wool Fedora", "ACC-020", "Accessories", "Smoky Charcoal", 2190.0, 6, listOf("One Size"), "https://images.unsplash.com/photo-1514327605112-b887c0e61c0a?w=800&auto=format&fit=crop&q=80"),
        Product("STY-021", "STY-VAD-01", "Band Collar Striped Shirt", "SHT-021", "Shirts", "Navy / White", 2590.0, 5, listOf("M", "L"), "https://images.unsplash.com/photo-1603252109303-2751441dd157?w=800&auto=format&fit=crop&q=80"),
        Product("STY-022", "STY-VAD-01", "Relaxed Fit Chino Shorts", "TRS-022", "Trousers", "Mocha Brown", 1990.0, 8, listOf("30", "32", "34"), "https://images.unsplash.com/photo-1591195853828-11db59a44f6b?w=800&auto=format&fit=crop&q=80"),
        Product("STY-023", "STY-VAD-01", "Jacquard Nehru Jacket", "ETH-023", "Ethnic", "Dusty Gold", 4290.0, 3, listOf("38", "40", "42"), "https://images.unsplash.com/photo-1610030469983-98e550d6193c?w=800&auto=format&fit=crop&q=80"),
        Product("STY-024", "STY-VAD-01", "Suede Bomber Jacket", "OTR-024", "Outerwear", "Tobacco Brown", 8990.0, 2, listOf("M", "L"), "https://images.unsplash.com/photo-1487222477894-8943e31ef7b2?w=800&auto=format&fit=crop&q=80"),
        Product("STY-025", "STY-VAD-01", "Mercerized Pima Cotton Tee", "TSH-025", "T-Shirts", "Deep Black", 1590.0, 10, listOf("S", "M", "L"), "https://images.unsplash.com/photo-1503342217505-b0a15ec3261c?w=800&auto=format&fit=crop&q=80"),
        Product("STY-026", "STY-VAD-01", "Vintage Washed Relaxed Jeans", "JNS-026", "Jeans", "Light Stonewash", 3990.0, 4, listOf("30", "32", "34"), "https://images.unsplash.com/photo-1542272604-780c96856592?w=800&auto=format&fit=crop&q=80"),
        Product("STY-027", "STY-VAD-01", "Pleated Georgette Anarkali", "DRS-027", "Dresses", "Blush Rose", 7490.0, 2, listOf("M", "L"), "https://images.unsplash.com/photo-1566174053879-31528523f8ae?w=800&auto=format&fit=crop&q=80"),
        Product("STY-028", "STY-VAD-01", "Suede Slipper Loafers", "FTW-028", "Footwear", "Sand Beige", 5490.0, 3, listOf("41", "42", "43"), "https://images.unsplash.com/photo-1560343090-f0409e92791a?w=800&auto=format&fit=crop&q=80"),
        Product("STY-029", "STY-VAD-01", "Silk Jacquard Pocket Square Set", "ACC-029", "Accessories", "Paisley Cobalt", 1290.0, 15, listOf("One Size"), "https://images.unsplash.com/photo-1598532163257-ae3c6b2524b6?w=800&auto=format&fit=crop&q=80"),
        Product("STY-030", "STY-VAD-01", "Heavy Denim Chore Overshirt", "SHT-030", "Shirts", "Rinse Wash Indigo", 3790.0, 4, listOf("S", "M", "L"), "https://images.unsplash.com/photo-1578932750294-f5075e85f44a?w=800&auto=format&fit=crop&q=80")
    )
}
