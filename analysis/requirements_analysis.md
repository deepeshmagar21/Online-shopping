# Requirement Analysis 

## Project: All in One – Online Shopping 

---

## 1. Problem Statement

Consumers and local merchants face fragmentation in local commerce. Customers must install separate applications for food, fashion, electronics, groceries, and other products. At the same time, small vendors struggle to establish direct digital ordering and delivery services.

The All in One application aims to bring different product categories and multiple vendors together in one platform, making shopping more convenient for customers and providing local vendors with an opportunity to reach more customers.

---

## 2. Functional Requirements

1. **User Authentication:** Secure email/password login, registration, and password recovery using Firebase Authentication.

2. **Multi-Vendor Product Catalog:** Allow customers to browse products from different vendors, categorized into food, fashion, shoes, clothes, electronics, and beauty products.

3. **Real-Time Search:** Allow users to search and filter products across active inventory and sample datasets.

4. **Cart Management:** Allow customers to add multiple products, view cart items, calculate the grand total, and remove products dynamically.

5. **Flexible Checkout:** Support Pay on Delivery and Debit/Credit Card payment options.

6. **Geolocation & Mapping:** Allow customers to select delivery addresses using Google Maps and support live order tracking.

7. **Delivery Agent Registration:** Provide a profile submission form for delivery personnel.

---

## 3. Non-Functional Requirements

- **Performance:** Target a cold app launch time of under 2 seconds and UI response times under 100 milliseconds.

- **Reliability & Null Safety:** Implement null checking on user sessions to reduce application crashes and improve reliability.

- **Scalability:** Use Firebase Firestore serverless architecture to support concurrent database reads and writes.

---

## 4. Conclusion

The requirement analysis identifies the main problems, expected system functions, and quality requirements of the All in One application. These requirements provide a foundation for the feasibility study, system design, development, and testing phases.
