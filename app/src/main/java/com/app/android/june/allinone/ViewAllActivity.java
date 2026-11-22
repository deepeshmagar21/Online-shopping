package com.app.android.june.allinone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.android.june.allinone.adapters.allAdapter;
import com.app.android.june.allinone.helpers.Food;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewAllActivity extends AppCompatActivity {
    private RecyclerView friendList;
    private allAdapter recyclerViewAdapter;
    private List<Food> eventList;
    private FirebaseUser user;
    private FirebaseFirestore db;
    private DocumentReference users;
    private String userId, address, state;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        getSupportActionBar().hide();
        friendList = findViewById(R.id.favouriteRecycler);
        progressBar = findViewById(R.id.progrss);
        TextView textView = findViewById(R.id.text);
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user != null ? user.getUid() : "";
        eventList = new ArrayList<>();
        LinearLayoutManager recyclerLayoutManager =
                new LinearLayoutManager(this);
        friendList.setLayoutManager(recyclerLayoutManager);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(friendList.getContext(),
                        recyclerLayoutManager.getOrientation());
        friendList.addItemDecoration(dividerItemDecoration);
        if (getIntent().hasExtra("category")){
            String category = getIntent().getStringExtra("category");
            loadCategoryProducts(category);
        }else if (getIntent().hasExtra("popular")){
            getFoodItems2();
            textView.setText("Popular Items");
        }else if (getIntent().hasExtra("recent")) {
            getRecent();
            textView.setText("Recent Orders");
        }else if (getIntent().hasExtra("nearme")){
            getUsers2();
            textView.setText("Popular Items Near You");
        }else if (getIntent().hasExtra("sponsord")){
            getSponsordItems();
            textView.setText("Sponsored Items");
        }else if (getIntent().hasExtra("others")){
            String category = getIntent().getStringExtra("others");
            loadCategoryProducts(category != null ? category : "others");
        }else if (getIntent().hasExtra("name")){
            String name = getIntent().getStringExtra("name");
            if (name != null && !name.equalsIgnoreCase("dishes") && !name.equalsIgnoreCase("all")) {
                loadCategoryProducts(name);
            } else {
                getUsers();
                textView.setText("All Items");
            }
        }else {
            getUsers();
            textView.setText("All Items");
        }

    }

    private void getRecent() {
        db.collection("Customers").document("users").collection("users").document(userId).collection("history").orderBy("Date", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            for (DocumentSnapshot doc : task.getResult()) {
                                Food e = doc.toObject(Food.class);
                                e.setId(doc.getId());
                                eventList.add(e);
                            }
                            if (eventList.isEmpty()) {
                                eventList.addAll(Food.getPopularProducts());
                            }
                            recyclerViewAdapter = new
                                    allAdapter(eventList, ViewAllActivity.this);
                            friendList.setAdapter(recyclerViewAdapter);

                        } else {
                            Toast.makeText(ViewAllActivity.this, "error: " + task.getException(), Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                            if (eventList.isEmpty()) {
                                eventList.addAll(Food.getPopularProducts());
                                recyclerViewAdapter = new allAdapter(eventList, ViewAllActivity.this);
                                friendList.setAdapter(recyclerViewAdapter);
                            }
                        }
                    }
                });

        db.collection("Customers").document("users").collection("users").document(userId).collection("history").orderBy("Date", Query.Direction.DESCENDING)
                .addSnapshotListener(ViewAllActivity.this, new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        if (documentSnapshots != null) {
                            for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                doc.getDocument().toObject(Food.class);
                            }
                        }
                    }
                });

    }

    private void getFoodItems2() {
        db.collection("Vendors").document("services").collection("services").orderBy("ProductLikes", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult()) {
                                Food e = doc.toObject(Food.class);
                                e.setId(doc.getId());
                                eventList.add(e);
                            }
                        } else {
                            Toast.makeText(ViewAllActivity.this, "error: " + task.getException(), Toast.LENGTH_LONG).show();
                        }
                        getFoodItems22();
                    }
                });

        db.collection("Vendors").document("services").collection("services").orderBy("ProductLikes", Query.Direction.DESCENDING)
                .addSnapshotListener(ViewAllActivity.this, new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        if (documentSnapshots != null) {
                            for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                doc.getDocument().toObject(Food.class);
                            }
                        }
                    }
                });

    }

    private void getFoodItems22() {
        db.collection("Vendors").document("services").collection("others").orderBy("ProductLikes", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            for (DocumentSnapshot doc : task.getResult()) {
                                Food e = doc.toObject(Food.class);
                                e.setId(doc.getId());
                                eventList.add(e);
                            }
                        } else {
                            Toast.makeText(ViewAllActivity.this, "error: " + task.getException(), Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                        if (eventList.isEmpty()) {
                            eventList.addAll(Food.getPopularProducts());
                        }
                        recyclerViewAdapter = new
                                allAdapter(eventList, ViewAllActivity.this);
                        friendList.setAdapter(recyclerViewAdapter);
                    }
                });

        db.collection("Vendors").document("services").collection("others").orderBy("ProductLikes", Query.Direction.DESCENDING)
                .addSnapshotListener(ViewAllActivity.this, new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        if (documentSnapshots != null) {
                            for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                doc.getDocument().toObject(Food.class);
                            }
                        }
                    }
                });

    }

    private void getFoodItems() {
        db.collection("Vendors").document("services").collection("services").whereArrayContains("ShopAddresses", state != null ? state : "")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            for (DocumentSnapshot doc : task.getResult()) {
                                Food e = doc.toObject(Food.class);
                                e.setId(doc.getId());
                                eventList.add(e);
                            }
                            if (eventList.isEmpty()) {
                                eventList.addAll(Food.get30Products());
                            }
                            recyclerViewAdapter = new
                                    allAdapter(eventList, ViewAllActivity.this);
                            friendList.setAdapter(recyclerViewAdapter);

                        } else {
                            Toast.makeText(ViewAllActivity.this, "error: " + task.getException(), Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                            if (eventList.isEmpty()) {
                                eventList.addAll(Food.get30Products());
                                recyclerViewAdapter = new allAdapter(eventList, ViewAllActivity.this);
                                friendList.setAdapter(recyclerViewAdapter);
                            }
                        }
                    }
                });

        db.collection("Vendors").document("services").collection("services").whereArrayContains("ShopAddresses", state != null ? state : "")
                .addSnapshotListener(ViewAllActivity.this, new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        if (documentSnapshots != null) {
                            for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                doc.getDocument().toObject(Food.class);
                            }
                        }
                    }
                });
    }
    private void getUsers() {
        if (userId == null || userId.isEmpty()) {
            state = "";
            getFoodItems();
            return;
        }
        users =  db.collection("Customers").document("users").collection("users").document(userId);
        users.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                              @Override
                                              public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                  if (task.isSuccessful()) {
                                                      DocumentSnapshot doc = task.getResult();
                                                      if (doc.exists()) {
                                                          address = doc.getString("Lga");
                                                          state = doc.getString("Street name");

                                                          getFoodItems();
                                                      } else {
                                                          state = "";
                                                          getFoodItems();
                                                      }
                                                  } else {
                                                      state = "";
                                                      getFoodItems();
                                                  }
                                              }
                                          }
        )
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        state = "";
                        getFoodItems();
                    }
                });
    }

    private void getUsers2() {
        if (userId == null || userId.isEmpty()) {
            address = "";
            getFoodItems3();
            return;
        }
        users =  db.collection("Customers").document("users").collection("users").document(userId);
        users.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                              @Override
                                              public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                  if (task.isSuccessful()) {
                                                      DocumentSnapshot doc = task.getResult();
                                                      if (doc.exists()) {
                                                          address = doc.getString("Lga");
                                                          state = doc.getString("Street name");

                                                          getFoodItems3();
                                                      } else {
                                                          address = "";
                                                          getFoodItems3();
                                                      }
                                                  } else {
                                                      address = "";
                                                      getFoodItems3();
                                                  }
                                              }
                                          }
        )
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        address = "";
                        getFoodItems3();
                    }
                });
    }
    private void getUsers3() {
        if (userId == null || userId.isEmpty()) {
            state = "";
            getOtherItems();
            return;
        }
        users =  db.collection("Customers").document("users").collection("users").document(userId);
        users.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                              @Override
                                              public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                  if (task.isSuccessful()) {
                                                      DocumentSnapshot doc = task.getResult();
                                                      if (doc.exists()) {
                                                          address = doc.getString("Lga");
                                                          state = doc.getString("Street name");

                                                          getOtherItems();
                                                      } else {
                                                          state = "";
                                                          getOtherItems();
                                                      }
                                                  } else {
                                                      state = "";
                                                      getOtherItems();
                                                  }
                                              }
                                          }
        )
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        state = "";
                        getOtherItems();
                    }
                });
    }
    private void getOtherItems() {
        db.collection("Vendors").document("services").collection("others").whereArrayContains("ShopAddresses", state != null ? state : "")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            for (DocumentSnapshot doc : task.getResult()) {
                                Food e = doc.toObject(Food.class);
                                e.setId(doc.getId());
                                eventList.add(e);
                            }
                            if (eventList.isEmpty()) {
                                eventList.addAll(Food.getPopularProducts());
                            }
                            recyclerViewAdapter = new
                                    allAdapter(eventList, ViewAllActivity.this);
                            friendList.setAdapter(recyclerViewAdapter);

                        } else {
                            Toast.makeText(ViewAllActivity.this, "error: " + task.getException(), Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                            if (eventList.isEmpty()) {
                                eventList.addAll(Food.getPopularProducts());
                                recyclerViewAdapter = new allAdapter(eventList, ViewAllActivity.this);
                                friendList.setAdapter(recyclerViewAdapter);
                            }
                        }
                    }
                });

        db.collection("Vendors").document("services").collection("others").whereArrayContains("ShopAddresses", state != null ? state : "")
                .addSnapshotListener(ViewAllActivity.this, new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        if (documentSnapshots != null) {
                            for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                doc.getDocument().toObject(Food.class);
                            }
                        }
                    }
                });
    }
    private void getFoodItems3() {
        db.collection("Vendors").document("services").collection("services").whereArrayContains("ShopAddresses", address != null ? address : "")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult()) {
                                Food e = doc.toObject(Food.class);
                                e.setId(doc.getId());
                                eventList.add(e);
                            }
                        } else {
                            Toast.makeText(ViewAllActivity.this, "error: " + task.getException(), Toast.LENGTH_LONG).show();
                        }
                        getFoodItems33();
                    }
                });

        db.collection("Vendors").document("services").collection("services").whereArrayContains("ShopAddresses", address != null ? address : "")
                .addSnapshotListener(ViewAllActivity.this, new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        if (documentSnapshots != null) {
                            for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                doc.getDocument().toObject(Food.class);
                            }
                        }
                    }
                });
    }

    private void getFoodItems33() {
        db.collection("Vendors").document("services").collection("others").whereArrayContains("ShopAddresses", address != null ? address : "")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            for (DocumentSnapshot doc : task.getResult()) {
                                Food e = doc.toObject(Food.class);
                                e.setId(doc.getId());
                                eventList.add(e);
                            }
                        } else {
                            Toast.makeText(ViewAllActivity.this, "error: " + task.getException(), Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                        if (eventList.isEmpty()) {
                            eventList.addAll(Food.getNearMeProducts());
                        }
                        recyclerViewAdapter = new
                                allAdapter(eventList, ViewAllActivity.this);
                        friendList.setAdapter(recyclerViewAdapter);
                    }
                });

        db.collection("Vendors").document("services").collection("others").whereArrayContains("ShopAddresses", address != null ? address : "")
                .addSnapshotListener(ViewAllActivity.this, new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        if (documentSnapshots != null) {
                            for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                doc.getDocument().toObject(Food.class);
                            }
                        }
                    }
                });
    }
    private void getSponsordItems() {
        db.collection("Vendors").document("services").collection("services").whereEqualTo("Sponsord", 1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult()) {
                                Food e = doc.toObject(Food.class);
                                e.setId(doc.getId());
                                eventList.add(e);
                            }
                        } else {
                            Toast.makeText(ViewAllActivity.this, "error: " + task.getException(), Toast.LENGTH_LONG).show();
                        }
                        getSponsordItems2();
                    }
                });

        db.collection("Vendors").document("services").collection("services").whereEqualTo("Sponsord", 1)
                .addSnapshotListener(ViewAllActivity.this, new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        if (documentSnapshots != null) {
                            for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                doc.getDocument().toObject(Food.class);
                            }
                        }
                    }
                });
    }

    private void getSponsordItems2() {
        db.collection("Vendors").document("services").collection("others").whereEqualTo("Sponsord", 1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            for (DocumentSnapshot doc : task.getResult()) {
                                Food e = doc.toObject(Food.class);
                                e.setId(doc.getId());
                                eventList.add(e);
                            }
                        } else {
                            Toast.makeText(ViewAllActivity.this, "error: " + task.getException(), Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                        if (eventList.isEmpty()) {
                            eventList.addAll(Food.getPopularProducts());
                        }
                        recyclerViewAdapter = new
                                allAdapter(eventList, ViewAllActivity.this);
                        friendList.setAdapter(recyclerViewAdapter);
                    }
                });

        db.collection("Vendors").document("services").collection("others").whereEqualTo("Sponsord", 1)
                .addSnapshotListener(ViewAllActivity.this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (documentSnapshots != null) {
                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                        doc.getDocument().toObject(Food.class);
                    }
                }
            }
        });
    }

    private void loadCategoryProducts(String category) {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
        eventList.clear();
        List<Food> products = Food.getCategoryProducts(category);
        if (products != null) {
            eventList.addAll(products);
        }
        recyclerViewAdapter = new allAdapter(eventList, ViewAllActivity.this);
        friendList.setAdapter(recyclerViewAdapter);

        TextView textView = findViewById(R.id.text);
        if (textView != null) {
            textView.setText(formatCategoryTitle(category));
        }
    }

    private String formatCategoryTitle(String category) {
        if (category == null || category.trim().isEmpty()) {
            return "Category Items";
        }
        String cat = category.trim();
        if (cat.equalsIgnoreCase("others")) {
            return "Other Items";
        }
        String capitalized = cat.substring(0, 1).toUpperCase() + cat.substring(1).toLowerCase();
        return capitalized + " Items";
    }
}
