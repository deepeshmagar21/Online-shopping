package com.app.android.june.allinone.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.android.june.allinone.InstantCheckoutActivity;
import com.app.android.june.allinone.R;
import com.app.android.june.allinone.adapters.CartAdapter;
import com.app.android.june.allinone.helpers.Cart;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment implements CartAdapter.OnCartItemRemoveListener {

    private RecyclerView recyclerView;
    private TextView emptyCartText, cartTotalPrice;
    private ProgressBar progressBar;
    private LinearLayout checkoutLayout;
    private Button checkoutButton;

    private CartAdapter adapter;
    private final List<Cart> cartList = new ArrayList<>();
    private FirebaseFirestore db;
    private FirebaseUser user;
    private String userId;
    private final DecimalFormat df = new DecimalFormat("####0.00");
    private double grandTotal = 0.0;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cart, container, false);

        df.setGroupingUsed(true);
        df.setGroupingSize(3);

        recyclerView = rootView.findViewById(R.id.cart_recycler_view);
        emptyCartText = rootView.findViewById(R.id.empty_cart_text);
        cartTotalPrice = rootView.findViewById(R.id.cart_total_price);
        progressBar = rootView.findViewById(R.id.cart_progress_bar);
        checkoutLayout = rootView.findViewById(R.id.checkout_layout);
        checkoutButton = rootView.findViewById(R.id.checkout);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CartAdapter(cartList, getContext(), this);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            userId = user.getUid();
            loadCartItems();
        } else {
            userId = "";
            showEmptyCart();
        }

        checkoutButton.setOnClickListener(v -> {
            if (cartList.isEmpty()) {
                Toast.makeText(getContext(), "Your cart is empty", Toast.LENGTH_SHORT).show();
                return;
            }

            Cart firstItem = cartList.get(0);
            Intent intent = new Intent(getContext(), InstantCheckoutActivity.class);
            intent.putExtra("foodName", firstItem.getFoodName());
            intent.putExtra("foodPrice", grandTotal);
            intent.putExtra("foodPhoto", firstItem.getFoodPhoto());
            
            double likesVal = 0.0;
            try {
                if (firstItem.getFoodLikes() != null) likesVal = Double.parseDouble(firstItem.getFoodLikes());
            } catch (Exception ignored) {}
            intent.putExtra("foodLikes", likesVal);

            intent.putExtra("foodId", firstItem.getFoodId());
            intent.putExtra("foodTags", firstItem.getFoodTags());
            intent.putExtra("foodDesc", firstItem.getFoodDesc());
            intent.putExtra("shopName", firstItem.getShopName());
            intent.putExtra("shopCity", firstItem.getShopCity());
            intent.putExtra("shopState", firstItem.getShopState());
            intent.putExtra("shopAddress", firstItem.getShopAddress());
            intent.putExtra("userID", firstItem.getUserID());
            intent.putExtra("shopID", firstItem.getShopID());

            double pcs = 1.0;
            try {
                if (firstItem.getFoodPieces() != null) pcs = Double.parseDouble(firstItem.getFoodPieces());
            } catch (Exception ignored) {}
            intent.putExtra("foodPieces", pcs);

            startActivity(intent);
        });

        return rootView;
    }

    private void loadCartItems() {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }

        db.collection("Customers").document("users").collection("users").document(userId).collection("cart")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }

                        cartList.clear();
                        if (task.isSuccessful() && task.getResult() != null && !task.getResult().isEmpty()) {
                            for (DocumentSnapshot doc : task.getResult()) {
                                Cart item = parseCartItem(doc);
                                if (item != null) {
                                    cartList.add(item);
                                }
                            }
                        }

                        updateCartUI();
                    }
                });
    }

    private Cart parseCartItem(DocumentSnapshot doc) {
        if (doc == null || !doc.exists()) return null;

        Cart item = new Cart();
        item.setId(doc.getId());

        item.setFoodName(doc.getString("foodName"));
        item.setFoodPhoto(doc.getString("foodPhoto"));
        item.setFoodTags(doc.getString("foodTags"));
        item.setFoodDesc(doc.getString("foodDesc"));
        item.setShopName(doc.getString("shopName"));
        item.setShopCity(doc.getString("shopCity"));
        item.setShopState(doc.getString("shopState"));
        item.setShopAddress(doc.getString("shopAddress"));
        item.setUserID(doc.getString("userID"));
        item.setShopID(doc.getString("shopID"));

        String fId = doc.getString("foodId");
        if (fId == null || fId.isEmpty()) {
            fId = doc.getId();
        }
        item.setFoodId(fId);

        Object priceObj = doc.get("foodPrice");
        if (priceObj != null) {
            item.setFoodPrice(String.valueOf(priceObj));
        } else {
            item.setFoodPrice("0");
        }

        Object piecesObj = doc.get("foodPieces");
        if (piecesObj != null) {
            item.setFoodPieces(String.valueOf(piecesObj));
        } else {
            item.setFoodPieces("1");
        }

        Object likesObj = doc.get("foodLikes");
        if (likesObj != null) {
            item.setFoodLikes(String.valueOf(likesObj));
        } else {
            item.setFoodLikes("0");
        }

        return item;
    }

    private void updateCartUI() {
        grandTotal = 0.0;

        for (Cart item : cartList) {
            try {
                if (item.getFoodPrice() != null) {
                    double p = Double.parseDouble(item.getFoodPrice());
                    double pcs = 1.0;
                    if (item.getFoodPieces() != null) {
                        pcs = Double.parseDouble(item.getFoodPieces());
                    }
                    grandTotal += (p * pcs);
                }
            } catch (Exception ignored) {}
        }

        if (cartList.isEmpty()) {
            showEmptyCart();
        } else {
            if (emptyCartText != null) emptyCartText.setVisibility(View.GONE);
            if (checkoutLayout != null) checkoutLayout.setVisibility(View.VISIBLE);
            if (cartTotalPrice != null) cartTotalPrice.setText("₩" + df.format(grandTotal));
        }

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    private void showEmptyCart() {
        if (emptyCartText != null) emptyCartText.setVisibility(View.VISIBLE);
        if (checkoutLayout != null) checkoutLayout.setVisibility(View.GONE);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemRemove(Cart item, int position) {
        if (item == null || userId == null || userId.isEmpty()) return;

        String docId = item.getId() != null ? item.getId() : item.getFoodId();

        db.collection("Customers").document("users").collection("users").document(userId).collection("cart").document(docId)
                .delete()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Item removed from cart", Toast.LENGTH_SHORT).show();
                    }
                });

        // Also clean legacy "cart" document if it was the same item
        db.collection("Customers").document("users").collection("users").document(userId).collection("cart").document("cart").delete();

        if (position >= 0 && position < cartList.size()) {
            cartList.remove(position);
        } else {
            cartList.remove(item);
        }

        updateCartUI();
    }
}