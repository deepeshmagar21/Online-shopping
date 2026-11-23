# Design & System Architecture
## Project: All in One (Online Shopping & Multi-Vendor Delivery Ecosystem)

---

## 1. Architectural Overview
The **All in One** mobile application is built upon a **Layered Architecture** adhering to Clean Architecture and Unidirectional Data Flow (UDF) principles. It connects native Android UI components directly to a serverless Backend-as-a-Service (BaaS) powered by Firebase.

```
+-------------------------------------------------------------+
|                       Presentation Layer                    |
|      (Activities, Fragments, Custom RecyclerView Adapters)  |
+------------------------------+------------------------------+
                               |
                               v
+-------------------------------------------------------------+
|                        Data / Business Logic Layer          |
|      (Firebase Firestore Repositories, Volley Requests,     |
|       FusedLocationProviderClient, GeocoderHandler)         |
+------------------------------+------------------------------+
                               |
                               v
+-------------------------------------------------------------+
|                        Cloud BaaS Infrastructure            |
|   (Firebase Auth, Firestore NoSQL, Storage, Cloud Messaging)|
+-------------------------------------------------------------+
```

---

## 2. Database Design (Firebase Firestore Schema)
* **Vendors Collection (`/Vendors/services/services` & `others`):**
  - Stores product attributes: `ProductName`, `ProductPrice`, `ProductPic`, `ProductDesc`, `FoodTags`, `ShopName`, `ShopAddress`, `ProductLikes`.
* **Customer Cart (`/Customers/users/{userId}/cart/{foodId}`):**
  - Stores individual cart items allowing multi-product shopping carts.
* **Customer Orders (`/Customers/users/{userId}/orders/{orderId}` & `/Vendors/users/{vendorId}/orders/{orderId}`):**
  - Manages order lifecycles with status codes (`0` = Pending/Not Approved, `1` = Approved, `2` = Canceled).
* **Delivery Agent Applications (`/Vendors/users/{userId}/Hf/{hfId}`):**
  - Stores delivery agent profile submissions for vendor review.

---

## 3. UI/UX Design & User Flows
* **Dashboard & Navigation:** Persistent `BottomNavigationView` switching seamlessly between Home, Favourites, Search, Cart, and Profile.
* **Search Flow:** Empty state by default; queries populate results in real-time.
* **Checkout Flow:** Shipping address management, "Select on Map" Google Maps picker, and dual payment selection (Pay on Delivery or Debit/Credit Card dialog).
