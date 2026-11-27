# Implementation & Development Guide

## Project: All in One – Online Shopping 

## 1. Introduction

The Implementation phase explains how the All in One application is developed using Android technologies and Firebase services. It describes the technologies, dependencies, and major code modules used to build the application.

## 2. Tech Stack & Dependencies

| Technology | Purpose |
|---|---|
| Java / Kotlin | Android application development |
| Android Views | Building application interfaces |
| Material Design 3 | User interface design |
| CardView and RecyclerView | Displaying products and lists |
| Firebase Authentication | User registration and login |
| Firebase Firestore | Storing application data |
| Firebase Storage | Storing images and files |
| Firebase Cloud Messaging | Push notifications, if configured |
| Volley | Network requests, if used |
| Picasso | Loading images, if used |
| OkHttp3 | Network communication, if used |
| Google Play Services Location | Accessing device location |
| Google Maps API | Displaying maps and selecting locations |

## 3. Core Code Modules

### 3.1 Authentication Module
- `LoginActivity.java`
- `RegisterActivity.java`

Handles user login and registration.

### 3.2 Catalog and Home Module
- `MainActivity.java`
- `HomeFragment.java`

Displays the home screen and product catalog across different shopping categories.

### 3.3 Search Module
- `SearchFragment.java`

Allows users to search for products.

### 3.4 Cart and Checkout Module
- `CartFragment.java`
- `InstantCheckoutActivity.java`

Manages cart items and the checkout process.

### 3.5 Location and Tracking Module
- `LocationActivity.java`
- `CusLocationActivity.java`

Supports delivery location selection and customer location-related functionality.

### 3.6 Delivery Agent Profile Module
- `HfSubmitActivity.java`

Handles delivery agent profile submission.

## 4. Development Process

The application development process includes:

1. Setting up the Android Studio project.
2. Configuring required dependencies and Firebase services.
3. Developing authentication screens.
4. Building the home screen and product catalog.
5. Implementing search, favourites, and cart functionality.
6. Developing checkout and payment-related features.
7. Integrating location and map functionality.
8. Developing delivery-related modules.
9. Integrating application components and fixing errors.

## 5. Version Control

Git and GitHub are used to manage source code, track changes, and maintain project documentation.

## 6. Conclusion

The Implementation phase converts the project requirements and system design into a working Android application. The modules and technologies described in this document provide an overview of the development structure of the All in One project.
