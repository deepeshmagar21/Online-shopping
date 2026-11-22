package com.app.android.june.allinone.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.android.june.allinone.R;
import com.app.android.june.allinone.adapters.allAdapter;
import com.app.android.june.allinone.helpers.Food;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private EditText searchEditText;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView noProductsText;

    private allAdapter adapter;
    private final List<Food> allProducts = new ArrayList<>();
    private final List<Food> filteredProducts = new ArrayList<>();
    private FirebaseFirestore db;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        searchEditText = rootView.findViewById(R.id.search_edit_text);
        recyclerView = rootView.findViewById(R.id.search_recycler_view);
        progressBar = rootView.findViewById(R.id.search_progress_bar);
        noProductsText = rootView.findViewById(R.id.no_products_text);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new allAdapter(filteredProducts, getContext());
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        loadProducts();

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterProducts(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return rootView;
    }

    private void loadProducts() {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }

        db.collection("Vendors").document("services").collection("services")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null && !task.getResult().isEmpty()) {
                            for (DocumentSnapshot doc : task.getResult()) {
                                Food food = doc.toObject(Food.class);
                                if (food != null) {
                                    food.setId(doc.getId());
                                    allProducts.add(food);
                                }
                            }
                        }
                        loadOtherProducts();
                    }
                });
    }

    private void loadOtherProducts() {
        db.collection("Vendors").document("services").collection("others")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null && !task.getResult().isEmpty()) {
                            for (DocumentSnapshot doc : task.getResult()) {
                                Food food = doc.toObject(Food.class);
                                if (food != null) {
                                    food.setId(doc.getId());
                                    allProducts.add(food);
                                }
                            }
                        }

                        // Add sample products (including recently added ones across all categories)
                        List<Food> samples = Food.getSampleProducts();
                        for (Food sample : samples) {
                            boolean exists = false;
                            for (Food existing : allProducts) {
                                if (existing.getId() != null && existing.getId().equals(sample.getId())) {
                                    exists = true;
                                    break;
                                }
                            }
                            if (!exists) {
                                allProducts.add(sample);
                            }
                        }

                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }

                        String currentQuery = searchEditText != null ? searchEditText.getText().toString() : "";
                        filterProducts(currentQuery);
                    }
                });
    }

    private void filterProducts(String query) {
        filteredProducts.clear();
        String q = query.trim().toLowerCase();

        // Show products ONLY when a search query is typed
        if (q.isEmpty()) {
            if (noProductsText != null) {
                noProductsText.setVisibility(View.GONE);
            }
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
            return;
        }

        for (Food product : allProducts) {
            boolean matches = false;
            if (product.getProductName() != null && product.getProductName().toLowerCase().contains(q)) {
                matches = true;
            } else if (product.getFoodTags() != null && product.getFoodTags().toLowerCase().contains(q)) {
                matches = true;
            } else if (product.getShopName() != null && product.getShopName().toLowerCase().contains(q)) {
                matches = true;
            } else if (product.getProductDesc() != null && product.getProductDesc().toLowerCase().contains(q)) {
                matches = true;
            }

            if (matches) {
                filteredProducts.add(product);
            }
        }

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }

        if (noProductsText != null) {
            if (filteredProducts.isEmpty()) {
                noProductsText.setVisibility(View.VISIBLE);
            } else {
                noProductsText.setVisibility(View.GONE);
            }
        }
    }
}