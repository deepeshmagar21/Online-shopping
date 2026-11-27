# Requirements Traceability Matrix (RTM)

## Project Name
**All in One – Online Shopping & Multi-Vendor Delivery Ecosystem**

## 1. Introduction

The Requirements Traceability Matrix (RTM) is used to connect the project requirements with their related development activities and testing activities.

It helps the development team ensure that every identified requirement is considered during development and checked during testing.

## 2. Purpose of the RTM

The main purposes of this matrix are:

- To track all functional and non-functional requirements.
- To connect each requirement with its related application module.
- To identify test cases for checking each requirement.
- To make sure important requirements are not missed.
- To monitor the progress of requirement implementation and testing.

## 3. Requirements Traceability Matrix

| Requirement ID | Requirement Description | Related Module / Component | Test Case ID | Testing Description | Status |
|---|---|---|---|---|---|
| FR-01 | Users should be able to register, log in, and manage authentication. | Login and Registration, Firebase Authentication | TC-01 | Check registration, login, and invalid login details. | To be verified |
| FR-02 | Users should be able to browse products from different categories, such as clothing, food, groceries, electronics, shoes, and beauty products. | HomeFragment, Product Catalog | TC-02 | Check whether products from different categories are displayed correctly. | To be verified |
| FR-03 | Users should be able to search for products. | SearchFragment | TC-03 | Search for products using different names and keywords. | To be verified |
| FR-04 | Users should be able to add products to their favourites. | FavouriteFragment | TC-04 | Add and remove products from the favourites list. | To be verified |
| FR-05 | Users should be able to add products to the shopping cart. | CartFragment, CartAdapter | TC-05 | Add products, view cart items, and remove products. | To be verified |
| FR-06 | The system should calculate the total price of products in the cart. | Cart Management | TC-06 | Check whether the total price updates correctly when cart items change. | To be verified |
| FR-07 | Users should be able to place orders using available payment options. | InstantCheckoutActivity, Payment Module | TC-07 | Check the checkout process and available payment options. | To be verified |
| FR-08 | Users should be able to select a delivery location using a map. | LocationActivity, Google Maps | TC-08 | Check whether users can select and confirm a delivery location. | To be verified |
| FR-09 | Delivery personnel should be able to submit their profile information. | HfSubmitActivity, Delivery Profile Module | TC-09 | Submit delivery personnel information and check the submission process. | To be verified |
| FR-10 | The system should support order approval and order management. | Order Management Module | TC-10 | Check whether order information and approval processes work correctly. | To be verified |
| FR-11 | The system should support customer order history. | Order History Module | TC-11 | Check whether previous orders are displayed correctly. | To be verified |
| NFR-01 | The application should provide a user-friendly interface. | Android UI, Material Design | TC-12 | Check screen layout, navigation, and usability. | To be verified |
| NFR-02 | The application should respond to user actions within an acceptable time. | Android Application, Firebase | TC-13 | Check loading time and response to common user actions. | To be verified |
| NFR-03 | The system should handle user data reliably and avoid application crashes. | Firebase, Application Logic | TC-14 | Check invalid inputs, missing data, and unexpected situations. | To be verified |
| NFR-04 | The system should support future growth in products, categories, and users. | Firebase Firestore, Database Structure | TC-15 | Check whether the database structure can accommodate additional records and categories. | To be verified |

## 4. Traceability Process

The project team will use the following process:

1. Identify and assign an ID to each requirement.
2. Connect each requirement to its related application module.
3. Prepare test cases for checking the requirements.
4. Perform testing and record the results.
5. Update the status when development and testing are completed.

## 5. Conclusion

The Requirements Traceability Matrix provides a clear connection between project requirements, application components, and testing activities.

It helps the team track project progress, identify missing requirements, and ensure that the planned functionality of the All in One application is properly considered during development and testing.
