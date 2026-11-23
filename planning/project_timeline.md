# Project Planning & Timeline (Past 3 Months to Present)
## Project: All in One (Online Shopping & Multi-Vendor Delivery Ecosystem)

---

## Executive Summary
This document outlines the chronological project planning lifecycle for the **All in One** Android application, spanning a 3-month development window from initial conception and requirements elicitation to final deployment.

---

## Phase 1: Month 1 (Weeks 1 - 4) — Conceptualization & Initial Planning
* **Week 1 (Project Initiation & Scope Definition):**
  - Defined project objectives: creating an all-in-one multi-vendor marketplace (food, fashion, electronics, beauty, shoes) on Android.
  - Formulated stakeholder goals: empowering customers, local vendors, and delivery agents within a single ecosystem.
- **Week 2 (Requirement Gathering & Market Analysis):**
  - Analyzed competitor platforms and identified the need for real-time order tracking, multiple payment options (Pay on Delivery + Cards), and multi-vendor product catalogs.
- **Week 3 (System Architecture & Tech Stack Selection):**
  - Selected native Android (Java/Kotlin), Google Play Services Location & Maps API, and Google Firebase (Auth, Firestore, Storage, Cloud Messaging) as the cloud BaaS infrastructure.
- **Week 4 (Initial Wireframing & UX Flow):**
  - Mapped out user journeys for authentication, shopping cart management, checkout, and delivery agent profile submissions.

---

## Phase 2: Month 2 (Weeks 5 - 8) — Design & Core Implementation Planning
* **Week 5 (Database Schema & Data Model Design):**
  - Structured Firebase Firestore collections (`Vendors`, `Customers`, `cart`, `orders`, `history`, `Favourites`, `Hf`).
- **Week 6 (UI/UX Component & Navigation Planning):**
  - Planned BottomNavigationView architecture (`HomeFragment`, `FavouriteFragment`, `SearchFragment`, `CartFragment`, `ProfileFragment`) and Material 3 theming.
- **Week 7 (Core Module Development - Auth & Catalog):**
  - Implemented Splash screen, Login, Register, and Home product feeds with category selectors.
- **Week 8 (Search & Favourites Integration):**
  - Developed real-time search filtering (`SearchFragment`) and bookmark management (`FavouriteFragment`).

---

## Phase 3: Month 3 (Weeks 9 - 12 / Present) — Advanced Features, Testing & Deployment
* **Week 9 (Cart & Checkout Pipeline):**
  - Upgraded My Cart to support multi-product management, item removal (`CartAdapter`), and secure checkout (`InstantCheckoutActivity`).
- **Week 10 (Payment Integration & Geolocation):**
  - Added Pay on Delivery and Debit/Credit Card payment modal options, plus Google Maps location picking (`LocationActivity`).
- **Week 11 (Delivery Agent & Order Tracking):**
  - Finalized delivery agent profile submission (`HfSubmitActivity`), order approval workflows, and live order tracking on Google Maps (`CusLocationActivity`).
- **Week 12 (Quality Assurance, Documentation & Deployment):**
  - Performed comprehensive code audits, resolved null-safety edge cases, generated SRS & project documentation, initialized Git version control, and pushed to GitHub (`https://github.com/deepeshmagar21/Online-shopping.git`).
