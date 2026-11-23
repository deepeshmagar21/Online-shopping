# Implementation & Development Guide
## Project: All in One (Online Shopping & Multi-Vendor Delivery Ecosystem)

---

## 1. Tech Stack & Dependencies
* **Language:** Java / Kotlin
* **UI Framework:** Android Views, Material Design 3, CardView, RecyclerView
* **Backend BaaS:** Google Firebase (Auth, Firestore, Storage, Cloud Messaging)
* **Networking & Image Loading:** Volley, Picasso, OkHttp3
* **Location & Maps:** Google Play Services Location (`FusedLocationProviderClient`) & Maps API (`GoogleMap`)

---

## 2. Core Code Modules
* **Authentication:** [`LoginActivity.java`](file:///C:/Users/user/Downloads/Allinone/app/src/main/java/com/app/android/june/allinone/LoginActivity.java), [`RegisterActivity.java`](file:///C:/Users/user/Downloads/Allinone/app/src/main/java/com/app/android/june/allinone/RegisterActivity.java)
* **Catalog & Home:** [`MainActivity.java`](file:///C:/Users/user/Downloads/Allinone/app/src/main/java/com/app/android/june/allinone/MainActivity.java), [`HomeFragment.java`](file:///C:/Users/user/Downloads/Allinone/app/src/main/java/com/app/android/june/allinone/fragments/HomeFragment.java)
* **Search Engine:** [`SearchFragment.java`](file:///C:/Users/user/Downloads/Allinone/app/src/main/java/com/app/android/june/allinone/fragments/SearchFragment.java)
* **Cart & Checkout:** [`CartFragment.java`](file:///C:/Users/user/Downloads/Allinone/app/src/main/java/com/app/android/june/allinone/fragments/CartFragment.java), [`InstantCheckoutActivity.java`](file:///C:/Users/user/Downloads/Allinone/app/src/main/java/com/app/android/june/allinone/InstantCheckoutActivity.java)
* **Mapping & Tracking:** [`LocationActivity.java`](file:///C:/Users/user/Downloads/Allinone/app/src/main/java/com/app/android/june/allinone/LocationActivity.java), [`CusLocationActivity.java`](file:///C:/Users/user/Downloads/Allinone/app/src/main/java/com/app/android/june/allinone/CusLocationActivity.java)
* **Delivery Agent Profile:** [`HfSubmitActivity.java`](file:///C:/Users/user/Downloads/Allinone/app/src/main/java/com/app/android/june/allinone/HfSubmitActivity.java)
