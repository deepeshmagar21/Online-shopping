package com.app.android.june.allinone.helpers;


import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;

@IgnoreExtraProperties
public class Food {

    private String ShopName;
    private String ShopAddress;
    private String ProductName;
    private String ProductCity;
    private String ProductDesc;
    private String ProductAvail;
    private String ProductBook;
    private String ProductPic;
    private String ProductPrice;
    private String ProductState;
    private String User_id;
    private String ShopId;
    private String ProductTime;
    private String FoodTags;
    private Double ProductLikes;
    private String Created_date;
    private String Id;



    public Food() {

    }
    public Food(String shopName, String shopAddress, String productName, String productCity, String productDesc, String productAvail, String productBook, String productPic, String productPrice, String productState, String user_id, String shopId, String productTime, String foodTags, Double productLikes, String created_date, String id) {
        ShopName = shopName;
        ShopAddress = shopAddress;
        ProductName = productName;
        ProductCity = productCity;
        ProductDesc = productDesc;
        ProductAvail = productAvail;
        ProductBook = productBook;
        ProductPic = productPic;
        ProductPrice = productPrice;
        ProductState = productState;
        User_id = user_id;
        ShopId = shopId;
        ProductTime = productTime;
        FoodTags = foodTags;
        ProductLikes = productLikes;
        Created_date = created_date;
        Id = id;
    }

    public static List<Food> getSampleProducts() {
        List<Food> list = new ArrayList<>();
        // 8 Food items (tag: "food")
        list.add(new Food("Burger House", "123 Main St", "Cheeseburger & Fries", "Seoul", "Delicious grilled cheeseburger with crispy french fries.", "Yes", "Yes", "https://images.unsplash.com/photo-1568901346375-23c9450c58cd", "15000", "Seoul", "user1", "shop1", "10:00 AM", "food, lunch", 4.5, "18/10/2026", "p1"));
        list.add(new Food("Pizza Palace", "456 Oak Rd", "Margherita Pizza", "Seoul", "Fresh mozzarella, tomatoes, and basil on thin crust.", "Yes", "Yes", "https://images.unsplash.com/photo-1604382354936-07c5d9983bd3", "18000", "Seoul", "user1", "shop2", "11:00 AM", "food, dinner", 4.8, "18/10/2026", "p2"));
        list.add(new Food("Morning Glory Cafe", "789 Pine Ave", "Classic Pancakes", "Seoul", "Fluffy pancakes served with maple syrup and fresh berries.", "Yes", "Yes", "https://images.unsplash.com/photo-1567620905732-2d1ec7ab7445", "12000", "Seoul", "user1", "shop3", "08:00 AM", "food, breakfast", 4.2, "18/10/2026", "p3"));
        list.add(new Food("Brew & Bean", "321 Elm St", "Iced Caramel Macchiato", "Seoul", "Espresso with vanilla syrup, milk, and caramel drizzle over ice.", "Yes", "Yes", "https://images.unsplash.com/photo-1461023058943-07fcbe16d735", "6500", "Seoul", "user1", "shop4", "09:00 AM", "food, drinks, breakfast", 4.7, "18/10/2026", "p4"));
        list.add(new Food("Green Bowl", "654 Market St", "Caesar Salad", "Seoul", "Crisp romaine lettuce, parmesan cheese, croutons, and Caesar dressing.", "Yes", "Yes", "https://images.unsplash.com/photo-1550304943-4f24f54ddde9", "14000", "Seoul", "user1", "shop5", "12:00 PM", "food, lunch", 4.3, "18/10/2026", "p5"));
        list.add(new Food("Pizza Palace", "456 Oak Rd", "Pepperoni Pizza", "Seoul", "Loaded with spicy pepperoni and extra mozzarella cheese.", "Yes", "Yes", "https://images.unsplash.com/photo-1628840042765-356cda07504e", "22000", "Seoul", "user1", "shop2", "01:00 PM", "food, dinner", 4.9, "18/10/2026", "p6"));
        list.add(new Food("Burger House", "123 Main St", "BBQ Chicken Wings", "Seoul", "Tender chicken wings glazed in smoky barbecue sauce.", "Yes", "Yes", "https://images.unsplash.com/photo-1527477396000-e27163b481c2", "16000", "Seoul", "user1", "shop1", "02:00 PM", "food, dinner", 4.4, "18/10/2026", "p7"));
        list.add(new Food("Sweet Treats", "777 Sugar St", "Chocolate Lava Cake", "Seoul", "Warm chocolate cake with a gooey molten center.", "Yes", "Yes", "https://images.unsplash.com/photo-1606313564200-e75d5e30476c", "9000", "Seoul", "user1", "shop11", "03:00 PM", "food, breakfast", 4.9, "18/10/2026", "p8"));

        // 8 Fashion items (tag: "fashion")
        list.add(new Food("Fashion Hub", "333 Broadway", "Designer Leather Handbag", "Seoul", "Genuine leather handbag with spacious compartments.", "Yes", "Yes", "https://images.unsplash.com/photo-1584917865442-de89df76afd3", "120000", "Seoul", "user1", "shop8", "01:00 PM", "fashion", 4.5, "18/10/2026", "p9"));
        list.add(new Food("Vogue Boutique", "700 Fashion St", "Designer Silk Scarf", "Seoul", "Luxurious 100% pure silk scarf with elegant floral print.", "Yes", "Yes", "https://images.unsplash.com/photo-1606760227091-3dd870d97f1d", "45000", "Seoul", "user1", "shop107", "11:00 AM", "fashion", 4.8, "18/10/2026", "p10"));
        list.add(new Food("Trendy Style", "101 Gangnam-ro", "Wool Blend Trench Coat", "Seoul", "Classic long wool blend trench coat for autumn and winter.", "Yes", "Yes", "https://images.unsplash.com/photo-1539109136881-3be0616acf4b", "150000", "Seoul", "user1", "shop12", "10:00 AM", "fashion", 4.7, "18/10/2026", "p11"));
        list.add(new Food("Chic Avenue", "202 Itaewon-ro", "Minimalist Gold Watch", "Seoul", "Elegant stainless steel gold watch with leather strap.", "Yes", "Yes", "https://images.unsplash.com/photo-1522335789203-aabd1fc54bc9", "89000", "Seoul", "user1", "shop13", "11:30 AM", "fashion", 4.6, "18/10/2026", "p12"));
        list.add(new Food("Urban Wear", "303 Hongdae-st", "Classic Sunglasses", "Seoul", "UV400 protection stylish sunglasses with metal frame.", "Yes", "Yes", "https://images.unsplash.com/photo-1511499767150-a48a237f0083", "35000", "Seoul", "user1", "shop14", "12:00 PM", "fashion", 4.4, "18/10/2026", "p13"));
        list.add(new Food("Style Studio", "404 Mapo-df", "Canvas Tote Bag", "Seoul", "Durable everyday canvas tote bag with inner pocket.", "Yes", "Yes", "https://images.unsplash.com/photo-1544816155-12df9643f363", "25000", "Seoul", "user1", "shop15", "01:30 PM", "fashion", 4.3, "18/10/2026", "p14"));
        list.add(new Food("Luxe Living", "505 Jamsil-to", "Cashmere Winter Scarf", "Seoul", "Soft and warm pure cashmere winter scarf.", "Yes", "Yes", "https://images.unsplash.com/photo-1608256246200-53e635b5b65f", "55000", "Seoul", "user1", "shop16", "02:00 PM", "fashion", 4.9, "18/10/2026", "p15"));
        list.add(new Food("Elite Boutique", "606 Apgujeong", "Statement Pearl Necklace", "Seoul", "Elegant freshwater pearl necklace with silver clasp.", "Yes", "Yes", "https://images.unsplash.com/photo-1599643478518-a784e5dc4c8f", "79000", "Seoul", "user1", "shop17", "03:00 PM", "fashion", 4.8, "18/10/2026", "p16"));

        // 8 Shoes items (tag: "shoes")
        list.add(new Food("Sports Gear", "222 Stadium Rd", "Running Shoes", "Seoul", "Lightweight and comfortable running shoes for daily workouts.", "Yes", "Yes", "https://images.unsplash.com/photo-1542291026-7eec264c27ff", "89000", "Seoul", "user1", "shop7", "09:00 AM", "shoes", 4.8, "18/10/2026", "p17"));
        list.add(new Food("Sports Gear", "222 Stadium Rd", "Leather Oxford Shoes", "Seoul", "Formal genuine leather shoes for professional wear.", "Yes", "Yes", "https://images.unsplash.com/photo-1614252235316-8c857d38b5f4", "110000", "Seoul", "user1", "shop7", "04:00 PM", "shoes", 4.5, "18/10/2026", "p18"));
        list.add(new Food("Footwear Plus", "777 Foot St", "Casual White Sneakers", "Seoul", "Classic low-top white sneakers suitable for any casual outfit.", "Yes", "Yes", "https://images.unsplash.com/photo-1595950653106-6c9ebd614d3a", "75000", "Seoul", "user1", "shop18", "10:00 AM", "shoes", 4.7, "18/10/2026", "p19"));
        list.add(new Food("Footwear Plus", "777 Foot St", "Suede Ankle Boots", "Seoul", "Stylish suede ankle boots with block heel and side zipper.", "Yes", "Yes", "https://images.unsplash.com/photo-1560769629-975ec94e6a86", "135000", "Seoul", "user1", "shop18", "11:00 AM", "shoes", 4.9, "18/10/2026", "p20"));
        list.add(new Food("Active Walk", "888 Track St", "Trail Hiking Boots", "Seoul", "Waterproof hiking boots with rugged grip sole for outdoor adventures.", "Yes", "Yes", "https://images.unsplash.com/photo-1525966222134-fcfa99b8ae77", "145000", "Seoul", "user1", "shop19", "12:00 PM", "shoes", 4.6, "18/10/2026", "p21"));
        list.add(new Food("Active Walk", "888 Track St", "Slip-on Loafers", "Seoul", "Comfortable breathable slip-on casual loafers.", "Yes", "Yes", "https://images.unsplash.com/photo-1543163521-1bf539c55dd2", "65000", "Seoul", "user1", "shop19", "01:00 PM", "shoes", 4.4, "18/10/2026", "p22"));
        list.add(new Food("Step Up", "999 Heel Ave", "Stiletto High Heels", "Seoul", "Elegant stiletto heels for evening parties and special events.", "Yes", "Yes", "https://images.unsplash.com/photo-1539185441755-769473a23570", "99000", "Seoul", "user1", "shop20", "02:00 PM", "shoes", 4.3, "18/10/2026", "p23"));
        list.add(new Food("Step Up", "999 Heel Ave", "Cushioned Running Sandals", "Seoul", "Ergonomic sport sandals with adjustable straps.", "Yes", "Yes", "https://images.unsplash.com/photo-1575537302964-96cd47c06b1b", "45000", "Seoul", "user1", "shop20", "03:00 PM", "shoes", 4.2, "18/10/2026", "p24"));

        // 8 Cloths items (tag: "cloths")
        list.add(new Food("Fashion Hub", "333 Broadway", "Casual Cotton T-Shirt", "Seoul", "100% organic cotton breathable casual t-shirt.", "Yes", "Yes", "https://images.unsplash.com/photo-1521572267360-ee0c2909d518", "25000", "Seoul", "user1", "shop8", "10:00 AM", "cloths", 4.2, "18/10/2026", "p25"));
        list.add(new Food("Fashion Hub", "333 Broadway", "Classic Denim Jacket", "Seoul", "Vintage wash denim jacket with button closure.", "Yes", "Yes", "https://images.unsplash.com/photo-1576995853123-5a10305d93c0", "75000", "Seoul", "user1", "shop8", "11:30 AM", "cloths", 4.6, "18/10/2026", "p26"));
        list.add(new Food("Apparel Store", "121 Fashion Blvd", "Oversized Hoodie", "Seoul", "Cozy fleece-lined oversized hoodie with front kangaroo pocket.", "Yes", "Yes", "https://images.unsplash.com/photo-1556905055-8f358a7a47b2", "49000", "Seoul", "user1", "shop21", "09:00 AM", "cloths", 4.8, "18/10/2026", "p27"));
        list.add(new Food("Apparel Store", "121 Fashion Blvd", "Slim Fit Chino Pants", "Seoul", "Stretch cotton slim fit chino trousers for smart casual wear.", "Yes", "Yes", "https://images.unsplash.com/photo-1624378439575-d8705ad7ae80", "59000", "Seoul", "user1", "shop21", "10:30 AM", "cloths", 4.5, "18/10/2026", "p28"));
        list.add(new Food("Textile House", "131 Fabric St", "Flannel Plaid Shirt", "Seoul", "Soft brushed cotton flannel plaid shirt in classic colors.", "Yes", "Yes", "https://images.unsplash.com/photo-1596755094514-f87e34085b2c", "39000", "Seoul", "user1", "shop22", "11:00 AM", "cloths", 4.7, "18/10/2026", "p29"));
        list.add(new Food("Textile House", "131 Fabric St", "Pleated Midi Skirt", "Seoul", "Chic high-waist pleated midi skirt for feminine styling.", "Yes", "Yes", "https://images.unsplash.com/photo-1583496661160-fb5886a0aaaa", "45000", "Seoul", "user1", "shop22", "12:00 PM", "cloths", 4.4, "18/10/2026", "p30"));
        list.add(new Food("Garment Depot", "141 Thread Rd", "Knitted Turtleneck Sweater", "Seoul", "Warm ribbed turtleneck sweater crafted from fine wool blend.", "Yes", "Yes", "https://images.unsplash.com/photo-1576566588028-4147f3842f27", "69000", "Seoul", "user1", "shop23", "01:00 PM", "cloths", 4.9, "18/10/2026", "p31"));
        list.add(new Food("Garment Depot", "141 Thread Rd", "Athletic Jogger Pants", "Seoul", "Comfortable fleece joggers with elastic waistband and cuffs.", "Yes", "Yes", "https://images.unsplash.com/photo-1552902865-b72c031ac5ea", "35000", "Seoul", "user1", "shop23", "02:00 PM", "cloths", 4.3, "18/10/2026", "p32"));

        // 8 Beauty items (tag: "beauty")
        list.add(new Food("Beauty Glow", "444 Fifth Ave", "Matte Lipstick Set", "Seoul", "Long-lasting matte liquid lipstick set with 4 popular shades.", "Yes", "Yes", "https://images.unsplash.com/photo-1586495777744-4413f21062fa", "28000", "Seoul", "user1", "shop9", "11:00 AM", "beauty", 4.7, "18/10/2026", "p33"));
        list.add(new Food("Beauty Glow", "444 Fifth Ave", "Skincare Glow Serum", "Seoul", "Vitamin C facial serum for bright and radiant skin.", "Yes", "Yes", "https://images.unsplash.com/photo-1620916566398-39f1143ab7be", "35000", "Seoul", "user1", "shop9", "01:30 PM", "beauty", 4.8, "18/10/2026", "p34"));
        list.add(new Food("Cosmetics Plus", "555 Makeup St", "Hydrating Sheet Mask Pack", "Seoul", "Set of 10 deeply hydrating aloe and hyaluronic acid sheet masks.", "Yes", "Yes", "https://images.unsplash.com/photo-1522337360788-8b13dee7a37e", "18000", "Seoul", "user1", "shop24", "09:30 AM", "beauty", 4.9, "18/10/2026", "p35"));
        list.add(new Food("Cosmetics Plus", "555 Makeup St", "Professional Makeup Brush Set", "Seoul", "12-piece premium synthetic makeup brush set with travel pouch.", "Yes", "Yes", "https://images.unsplash.com/photo-1512290900672-8acd371b397d", "42000", "Seoul", "user1", "shop24", "10:30 AM", "beauty", 4.6, "18/10/2026", "p36"));
        list.add(new Food("Glow Lab", "666 Skincare Way", "Herbal Facial Cleanser", "Seoul", "Gentle foaming cleanser with green tea extract and amino acids.", "Yes", "Yes", "https://images.unsplash.com/photo-1571781926291-c477ebfd024b", "22000", "Seoul", "user1", "shop25", "11:00 AM", "beauty", 4.5, "18/10/2026", "p37"));
        list.add(new Food("Glow Lab", "666 Skincare Way", "Daily Sun Cream SPF50+", "Seoul", "Lightweight non-greasy facial sunscreen with broad spectrum UV protection.", "Yes", "Yes", "https://images.unsplash.com/photo-1596704017254-9b121068fb31", "25000", "Seoul", "user1", "shop25", "12:00 PM", "beauty", 4.8, "18/10/2026", "p38"));
        list.add(new Food("Pure Essence", "777 Aroma Rd", "Essential Oils Aromatherapy Set", "Seoul", "Pure plant therapeutic grade essential oils set of 6 bottles.", "Yes", "Yes", "https://images.unsplash.com/photo-1608571423902-eed4a5ad8108", "38000", "Seoul", "user1", "shop26", "01:00 PM", "beauty", 4.7, "18/10/2026", "p39"));
        list.add(new Food("Pure Essence", "777 Aroma Rd", "Nourishing Hair Treatment Mask", "Seoul", "Argan oil hair repair mask for dry and damaged hair.", "Yes", "Yes", "https://images.unsplash.com/photo-1526947425960-945c6e72858f", "29000", "Seoul", "user1", "shop26", "02:00 PM", "beauty", 4.4, "18/10/2026", "p40"));

        // 8 Electronics items (tag: "electronics")
        list.add(new Food("Tech World", "555 Silicon Way", "Wireless Bluetooth Earbuds", "Seoul", "High fidelity sound with active noise cancellation and charging case.", "Yes", "Yes", "https://images.unsplash.com/photo-1590658268037-6bf12165a8df", "59000", "Seoul", "user1", "shop10", "12:00 PM", "electronics", 4.9, "18/10/2026", "p41"));
        list.add(new Food("Tech World", "555 Silicon Way", "Smart Fitness Watch", "Seoul", "Tracks heart rate, sleep, steps, and workout sessions.", "Yes", "Yes", "https://images.unsplash.com/photo-1523275335684-37898b6baf30", "99000", "Seoul", "user1", "shop10", "10:00 AM", "electronics", 4.7, "18/10/2026", "p42"));
        list.add(new Food("Sound Lab", "600 Audio Ave", "Premium Wireless Headphones", "Seoul", "Over-ear noise-cancelling headphones with 40-hour battery life.", "Yes", "Yes", "https://images.unsplash.com/photo-1505740420928-5e560c06d30e", "159000", "Seoul", "user1", "shop106", "10:00 AM", "electronics", 4.9, "18/10/2026", "p43"));
        list.add(new Food("Gadget Hub", "700 Circuit Rd", "Fast Wireless Charging Pad", "Seoul", "15W Qi-certified fast wireless charger for smartphones and earbuds.", "Yes", "Yes", "https://images.unsplash.com/photo-1586816879360-004f5b0c51e3", "35000", "Seoul", "user1", "shop27", "11:00 AM", "electronics", 4.6, "18/10/2026", "p44"));
        list.add(new Food("Digital Zone", "800 Byte St", "Portable Mini Bluetooth Speaker", "Seoul", "Waterproof rugged portable speaker with rich bass and 12h playtime.", "Yes", "Yes", "https://images.unsplash.com/photo-1608043152269-423dbba4e7e1", "69000", "Seoul", "user1", "shop28", "12:00 PM", "electronics", 4.8, "18/10/2026", "p45"));
        list.add(new Food("Digital Zone", "800 Byte St", "Compact Power Bank 10000mAh", "Seoul", "Slim dual-port fast charging portable power bank.", "Yes", "Yes", "https://images.unsplash.com/photo-1609592424072-d59616a693c0", "45000", "Seoul", "user1", "shop28", "01:00 PM", "electronics", 4.5, "18/10/2026", "p46"));
        list.add(new Food("Electro Mart", "900 Volt Ave", "Smart LED Desk Lamp", "Seoul", "Dimmable eye-care LED desk lamp with USB charging port and touch control.", "Yes", "Yes", "https://images.unsplash.com/photo-1534447677768-be436bb09401", "55000", "Seoul", "user1", "shop29", "02:00 PM", "electronics", 4.7, "18/10/2026", "p47"));
        list.add(new Food("Electro Mart", "900 Volt Ave", "Mini USB Desk Fan", "Seoul", "Quiet and powerful portable USB powered cooling fan.", "Yes", "Yes", "https://images.unsplash.com/photo-1585776245991-cf89dd7fc73a", "25000", "Seoul", "user1", "shop29", "03:00 PM", "electronics", 4.3, "18/10/2026", "p48"));

        return list;
    }

    public static List<Food> get30Products() {
        List<Food> all = getSampleProducts();
        List<Food> list30 = new ArrayList<>();
        for (int i = 0; i < Math.min(30, all.size()); i++) {
            list30.add(all.get(i));
        }
        return list30;
    }

    public static List<Food> getFoodProducts() {
        List<Food> foods = new ArrayList<>();
        for (Food f : getSampleProducts()) {
            if ("food".equalsIgnoreCase(f.getFoodTags())) {
                foods.add(f);
            }
        }
        return foods;
    }

    public static List<Food> getCategoryProducts(String category) {
        if (category == null || category.trim().isEmpty()) {
            return get30Products();
        }
        if ("food".equalsIgnoreCase(category.trim())) {
            return getFoodProducts();
        }
        List<Food> filtered = new ArrayList<>();
        String tag = category.trim().toLowerCase();
        for (Food f : getSampleProducts()) {
            if (f.getFoodTags() != null && f.getFoodTags().toLowerCase().contains(tag)) {
                filtered.add(f);
            }
        }
        if (filtered.isEmpty()) {
            return get30Products();
        }
        return filtered;
    }

    public static List<Food> getPopularProducts() {
        List<Food> popular = new ArrayList<>();
        List<Food> all = getSampleProducts();
        popular.add(all.get(0));
        popular.add(all.get(1));
        popular.add(all.get(8));
        popular.add(all.get(10));
        popular.add(all.get(16));
        popular.add(all.get(17));
        popular.add(all.get(26));
        popular.add(all.get(33));
        popular.add(all.get(40));
        popular.add(all.get(42));
        return popular;
    }

    public static List<Food> getNearMeProducts() {
        List<Food> all = getSampleProducts();
        List<Food> nearMe = new ArrayList<>();
        for (int i = 24; i < 36; i++) {
            nearMe.add(all.get(i));
        }
        return nearMe;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public String getShopAddress() {
        return ShopAddress;
    }

    public void setShopAddress(String shopAddress) {
        ShopAddress = shopAddress;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductCity() {
        return ProductCity;
    }

    public void setProductCity(String productCity) {
        ProductCity = productCity;
    }

    public String getProductDesc() {
        return ProductDesc;
    }

    public void setProductDesc(String productDesc) {
        ProductDesc = productDesc;
    }

    public String getProductAvail() {
        return ProductAvail;
    }

    public void setProductAvail(String productAvail) {
        ProductAvail = productAvail;
    }

    public String getProductBook() {
        return ProductBook;
    }

    public void setProductBook(String productBook) {
        ProductBook = productBook;
    }

    public String getProductPic() {
        return ProductPic;
    }

    public void setProductPic(String productPic) {
        ProductPic = productPic;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = productPrice;
    }

    public String getProductState() {
        return ProductState;
    }

    public void setProductState(String productState) {
        ProductState = productState;
    }

    public String getUser_id() {
        return User_id;
    }

    public void setUser_id(String user_id) {
        User_id = user_id;
    }

    public String getShopId() {
        return ShopId;
    }

    public void setShopId(String shopId) {
        ShopId = shopId;
    }

    public String getProductTime() {
        return ProductTime;
    }

    public void setProductTime(String productTime) {
        ProductTime = productTime;
    }

    public String getFoodTags() {
        return FoodTags;
    }

    public void setFoodTags(String foodTags) {
        FoodTags = foodTags;
    }

    public Double getProductLikes() {
        return ProductLikes;
    }

    public void setProductLikes(Double productLikes) {
        ProductLikes = productLikes;
    }

    public String getCreated_date() {
        return Created_date;
    }

    public void setCreated_date(String created_date) {
        Created_date = created_date;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }


}
