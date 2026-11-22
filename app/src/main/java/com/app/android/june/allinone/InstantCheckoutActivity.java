package com.app.android.june.allinone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class InstantCheckoutActivity extends AppCompatActivity {
TextView etUsername, etAddress, etFoodTags, etFoodName, etShopName, etLikes, etReviews, etPrice, etPieces, etTotal;
String username, userAddress, paymentMethod = "pay on delivery", foodName, shopName,  review, foodPrice,foodPhoto, foodId, foodTags, foodDesc,
    shopCity, shopState, locationOrder = "m", shopAddress, shopID, userID, userId, useremail, userPhoto, orderId, orderId2, userPhone, userGender, userHome, newAddress,
    mon, tue, wed, thu, fri, sat, sun, shopOrder, order = "YES", lga = "m", cardPayment = "No";
Integer paymentType = 1, userType = 5, valueTime, card = 0;
Double likes, totalPrice, pieces;
ImageView imageView;
    FirebaseFirestore db;
    DocumentReference users;
    DecimalFormat df = new DecimalFormat("####0.00");
    AlertDialog dialog;
    FirebaseUser user;
    private ProgressDialog pDialog;
    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = "key=" +
            "your api key here";
    final private String contentType = "application/json";
    final String TAG = "NOTIFICATION TAG";
    private static final String SUBSCRIBE_TO = "userABC";

    String NOTIFICATION_TITLE;
    String NOTIFICATION_MESSAGE, today;
    String TOPIC;
    long date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instant_checkout);
        getSupportActionBar().hide();
        df.setGroupingUsed(true);
        df.setGroupingSize(3);
        etUsername = findViewById(R.id.username);
        etAddress = findViewById(R.id.useraddress);
        imageView = findViewById(R.id.image);
        etFoodTags = findViewById(R.id.foodTags);
        etFoodName = findViewById(R.id.foodName);
        etShopName = findViewById(R.id.shopName);
        etLikes = findViewById(R.id.num);
        etReviews = findViewById(R.id.reviews);
        etPrice = findViewById(R.id.price);
        etPieces = findViewById(R.id.pieces);
        etTotal = findViewById(R.id.total);

        date   = new Date().getTime();
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();

        //getConstants();
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
       today= (new SimpleDateFormat("EE", Locale.ENGLISH).format(date.getTime()));
        //Toast.makeText(this, ", " + today, Toast.LENGTH_SHORT).show();
        getTimeOfday();
        if (getIntent().hasExtra("foodName")) {
            foodName = Objects.requireNonNull(getIntent().getExtras()).getString("foodName");
            totalPrice = getIntent().getExtras().getDouble("foodPrice");
            foodPrice = getIntent().getExtras().getString("foodprice");
            foodPhoto = getIntent().getExtras().getString("foodPhoto");
            likes = getIntent().getExtras().getDouble("foodLikes");
            foodId = getIntent().getExtras().getString("foodId");
            foodDesc = getIntent().getExtras().getString("foodDesc");
            pieces = getIntent().getExtras().getDouble("foodPieces");

            foodTags = getIntent().getExtras().getString("foodTags");
            shopName = getIntent().getExtras().getString("shopName");
            shopCity = getIntent().getExtras().getString("shopCity");
            shopState = getIntent().getExtras().getString("shopState");
            shopAddress = getIntent().getExtras().getString("shopAddress");
            userID = getIntent().getExtras().getString("userID");
            shopID = getIntent().getExtras().getString("shopID");
            etTotal.setText("₩" + df.format(totalPrice));
            etPieces.setText(pieces + " Pieces");
            etPrice.setText("₩" + df.format(totalPrice));
            etReviews.setText(df.format(likes) + " reviews");
            //etLikes.setText(df.format(likes));
            etShopName.setText(shopName);
            etFoodName.setText(foodName);
            etFoodTags.setText(foodTags);
            Picasso.get()
                    .load(foodPhoto)
                    .placeholder(R.drawable.food)
                    .into(imageView);

            getDays();



            getUser();
        }
    }

    private void getTimeOfday() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 9){
            valueTime = 0;
        }else if(timeOfDay >= 9 && timeOfDay < 16){
            valueTime = 1;
        }else if(timeOfDay >= 16 && timeOfDay < 17){
            valueTime = 1;
        }else if(timeOfDay >= 17 && timeOfDay < 24){
            valueTime = 0;
        }
    }

    public void cancel(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void editAddress(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(InstantCheckoutActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_layout, null);
        builder.setView(dialogView);

         dialog= builder.create();
        dialog.setCancelable(false);
        dialog.show();

        ImageView btnClose = dialog.findViewById(R.id.close);
        Button btn = dialog.findViewById(R.id.copyAddress);
        final EditText address = dialog.findViewById(R.id.address);
        address.setText(userHome);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String Address = address.getText().toString();
                if (TextUtils.isEmpty(Address)) {
                    Toast.makeText(InstantCheckoutActivity.this, "Please enter your correct address", Toast.LENGTH_SHORT).show();
                } else {
                    newAddress = Address;
                    updateDatabase();

                }
            }
        });
    }

    public void selectMapLocation(View view) {
        Intent intent = new Intent(InstantCheckoutActivity.this, LocationActivity.class);
        startActivityForResult(intent, 201);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 201 && resultCode == RESULT_OK && data != null) {
            if (data.hasExtra("selectedAddress")) {
                String selectedAddr = data.getStringExtra("selectedAddress");
                if (selectedAddr != null && !selectedAddr.trim().isEmpty()) {
                    newAddress = selectedAddr;
                    etAddress.setText(selectedAddr);
                    Toast.makeText(this, "Location selected from map", Toast.LENGTH_SHORT).show();
                    updateDatabase();
                }
            }
        }
    }

    private void updateDatabase() {
        if (userId == null || userId.isEmpty()) return;
        Map<String, Object> newContact = new HashMap<>();
        newContact.put("HomeAddress", newAddress);
        if (userType == 4){
            db.collection("Vendors").document("users").collection("users").document(userId).update(newContact)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            etAddress.setText(newAddress);
                            Toast.makeText(InstantCheckoutActivity.this, "Address updated", Toast.LENGTH_SHORT).show();
                            if (dialog != null && dialog.isShowing()) dialog.cancel();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                                              @Override
                                              public void onFailure(@NonNull Exception e) {
                                                  Toast.makeText(InstantCheckoutActivity.this, "ERROR " + e.getMessage(),
                                                          Toast.LENGTH_SHORT).show();

                                              }
                                          }
                    );

        } else if (userType == 5){
            db.collection("Customers").document("users").collection("users").document(userId).update(newContact)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            etAddress.setText(newAddress);
                            Toast.makeText(InstantCheckoutActivity.this, "Address updated", Toast.LENGTH_SHORT).show();
                            if (dialog != null && dialog.isShowing()) dialog.cancel();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                                              @Override
                                              public void onFailure(@NonNull Exception e) {
                                                  Toast.makeText(InstantCheckoutActivity.this, "ERROR " + e.getMessage(),
                                                          Toast.LENGTH_SHORT).show();

                                              }
                                          }
                    );
        }

    }

    public void checkout(View view) {
        if (validates()){
            if (paymentType == 1 || "pay on delivery".equalsIgnoreCase(paymentMethod)) {
                displayLoader();
                SendToVendor();
            } else if (paymentType == 2) {
                showCardPaymentDialog();
            } else {
                displayLoader();
                SendToVendor();
            }
        }
    }

    private void showCardPaymentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(InstantCheckoutActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.card_payment_dialog, null);
        builder.setView(dialogView);

        AlertDialog cardDialog = builder.create();
        cardDialog.setCancelable(true);
        cardDialog.show();

        EditText cardNumber = cardDialog.findViewById(R.id.card_number);
        EditText cardExpiry = cardDialog.findViewById(R.id.card_expiry);
        EditText cardCvv = cardDialog.findViewById(R.id.card_cvv);
        EditText cardHolder = cardDialog.findViewById(R.id.card_holder);
        Button btnPay = cardDialog.findViewById(R.id.btn_pay_card);

        if (btnPay != null) {
            btnPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String num = cardNumber != null ? cardNumber.getText().toString().trim() : "";
                    String exp = cardExpiry != null ? cardExpiry.getText().toString().trim() : "";
                    String cvv = cardCvv != null ? cardCvv.getText().toString().trim() : "";
                    String holder = cardHolder != null ? cardHolder.getText().toString().trim() : "";

                    if (num.length() < 12) {
                        if (cardNumber != null) cardNumber.setError("Invalid card number");
                        return;
                    }
                    if (exp.length() < 4) {
                        if (cardExpiry != null) cardExpiry.setError("Invalid expiry");
                        return;
                    }
                    if (cvv.length() < 3) {
                        if (cardCvv != null) cardCvv.setError("Invalid CVV");
                        return;
                    }
                    if (holder.isEmpty()) {
                        if (cardHolder != null) cardHolder.setError("Required");
                        return;
                    }

                    cardDialog.dismiss();
                    paymentMethod = "debit/credit card";
                    displayLoader();
                    SendToVendor();
                }
            });
        }
    }

    private boolean validates() {
        if (username == null || username.trim().isEmpty()) {
            if (user != null && user.getEmail() != null) {
                username = user.getEmail();
            } else {
                username = "Customer";
            }
        }
        if (order != null && "NO".equalsIgnoreCase(order)) {
            Toast.makeText(this, "Sorry, you cannot place orders at this time", Toast.LENGTH_LONG).show();
            return false;
        }
        if (shopOrder != null && "NO".equalsIgnoreCase(shopOrder)) {
            Toast.makeText(this, "Sorry, this shop does not accept orders currently", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (today != null) {
            if ("Mon".equalsIgnoreCase(today) && "0".equals(mon)) {
                Toast.makeText(this, "Sorry, this vendor does not deliver today", Toast.LENGTH_LONG).show();
                return false;
            }
            if ("Tue".equalsIgnoreCase(today) && "0".equals(tue)) {
                Toast.makeText(this, "Sorry, this vendor does not deliver today", Toast.LENGTH_LONG).show();
                return false;
            }
            if ("Wed".equalsIgnoreCase(today) && "0".equals(wed)) {
                Toast.makeText(this, "Sorry, this vendor does not deliver today", Toast.LENGTH_LONG).show();
                return false;
            }
            if ("Thu".equalsIgnoreCase(today) && "0".equals(thu)) {
                Toast.makeText(this, "Sorry, this vendor does not deliver today", Toast.LENGTH_LONG).show();
                return false;
            }
            if ("Fri".equalsIgnoreCase(today) && "0".equals(fri)) {
                Toast.makeText(this, "Sorry, this vendor does not deliver today", Toast.LENGTH_LONG).show();
                return false;
            }
            if ("Sat".equalsIgnoreCase(today) && "0".equals(sat)) {
                Toast.makeText(this, "Sorry, this vendor does not deliver today", Toast.LENGTH_LONG).show();
                return false;
            }
            if ("Sun".equalsIgnoreCase(today) && "0".equals(sun)) {
                Toast.makeText(this, "Sorry, this vendor does not deliver today", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }


    private void displayLoader() {
        pDialog = new ProgressDialog(InstantCheckoutActivity.this);
        pDialog.setMessage("Processing.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }
    private void SendToVendor() {
        Map<String, Object> newContact = new HashMap<>();
        newContact.put("UserName", username);
        newContact.put("FoodName", foodName);
        newContact.put("Date", date);
        newContact.put("UserEmail", useremail);
        newContact.put("UserGender", userGender);
        newContact.put("UserPhoto", userPhoto);
        newContact.put("UserPhone", userPhone);
        newContact.put("Likes", likes);
        newContact.put("FoodPhoto", foodPhoto);
        newContact.put("FoodDesc", foodDesc);
        newContact.put("FoodId", foodId);
        newContact.put("TotalPrice", totalPrice);
        newContact.put("FoodPieces", pieces);
        newContact.put("FoodTags", foodTags);
        newContact.put("ShopId", shopID);
        newContact.put("PaymentMethod", paymentMethod);
        newContact.put("Status", 1);
        newContact.put("Time", "time");
        newContact.put("UserId", userId);
        newContact.put("HomeAddress", newAddress);

        db.collection("Vendors").document("users").collection("users").document(userID).collection("orders").add(newContact)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
orderId2 = documentReference.getId();
                       // Toast.makeText(InstantCheckoutActivity.this, "Address updated", Toast.LENGTH_SHORT).show();
                        sendNotification();
                        updateUser();
                        //UpdateHistory();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                                          @Override
                                          public void onFailure(@NonNull Exception e) {
                                              Toast.makeText(InstantCheckoutActivity.this, "ERROR" + e.toString(),
                                                      Toast.LENGTH_SHORT).show();
                                              pDialog.dismiss();

                                          }
                                      }
                );
    }

    private void updateUser() {
        Map<String, Object> newContact = new HashMap<>();
        newContact.put("FoodName", foodName);
        newContact.put("UserName", username);
        newContact.put("Date", date);
        newContact.put("ShopName", shopName);
        newContact.put("Likes", likes);
        newContact.put("FoodPhoto", foodPhoto);
        newContact.put("FoodDesc", foodDesc);
        newContact.put("FoodId", foodId);
        newContact.put("TotalPrice", totalPrice);
        newContact.put("FoodPieces", pieces);
        newContact.put("FoodTags", foodTags);
        newContact.put("ShopId", shopID);
        newContact.put("PaymentMethod", paymentMethod);
        newContact.put("Status", 1);
        newContact.put("OrderId", orderId2);
        newContact.put("Time", "time");
        newContact.put("UserId", userID);
        newContact.put("HomeAddress", newAddress);
        newContact.put("UserEmail", useremail);
        newContact.put("UserGender", userGender);
        newContact.put("UserPhoto", userPhoto);
        newContact.put("UserPhone", userPhone);
        db.collection("Customers").document("users").collection("users").document(userId).collection("orders").add(newContact)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        orderId = documentReference.getId();
                        UpdateHistory();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                                          @Override
                                          public void onFailure(@NonNull Exception e) {
                                              Toast.makeText(InstantCheckoutActivity.this, "ERROR" + e.toString(),
                                                      Toast.LENGTH_SHORT).show();
                                              pDialog.dismiss();

                                          }
                                      }
                );
    }

    private void sendNotification() {
        TOPIC = "/topics/" + userID; //topic has to match what the receiver subscribed to
        NOTIFICATION_TITLE = "New Order Received";
        NOTIFICATION_MESSAGE = "You have received a new order from " + username;

        JSONObject notification = new JSONObject();
        JSONObject notifcationBody = new JSONObject();
        try {
            notifcationBody.put("title", NOTIFICATION_TITLE);
            notifcationBody.put("message", NOTIFICATION_MESSAGE);

            notification.put("to", TOPIC);
            notification.put("data", notifcationBody);
        } catch (JSONException e) {
            Log.e(TAG, "onCreate: " + e.getMessage() );
            //Toast.makeText(ChatActivity.this, "err  " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        sendNotification(notification);
    }

    private void sendNotification(JSONObject notification) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "onResponse: " + response.toString());
                        // Toast.makeText(ChatActivity.this, "res  " + response.toString(), Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Toast.makeText(MainActivity.this, "Request error", Toast.LENGTH_LONG).show();
                        Log.i(TAG, "onErrorResponse: Didn't work");
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", serverKey);
                params.put("Content-Type", contentType);
                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void UpdateHistory() {
        Map<String, Object> newContact = new HashMap<>();
        newContact.put("ProductName", foodName);
        newContact.put("UserName", username);
        newContact.put("Date", date);
        newContact.put("ShopName", shopName);
        newContact.put("ProductLikes", likes);
        newContact.put("ProductPic", foodPhoto);
        newContact.put("ProductPrice", foodPrice);
        newContact.put("ProductDesc", foodDesc);
        newContact.put("FoodId", foodId);
        newContact.put("TotalPrice", totalPrice);
        newContact.put("FoodPieces", pieces);
        newContact.put("FoodTags", foodTags);
        newContact.put("ShopId", shopID);
        newContact.put("PaymentMethod", paymentMethod);
        newContact.put("Status", 1);
        newContact.put("OrderId", orderId2);
        newContact.put("Time", "time");
        newContact.put("UserId", userID);
        newContact.put("HomeAddress", newAddress);
        newContact.put("UserEmail", useremail);
        newContact.put("UserGender", userGender);
        newContact.put("UserPhoto", userPhoto);
        newContact.put("UserPhone", userPhone);
        db.collection("Customers").document("users").collection("users").document(userId).collection("history").add(newContact)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        updateVendor();


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                                          @Override
                                          public void onFailure(@NonNull Exception e) {
                                              Toast.makeText(InstantCheckoutActivity.this, "ERROR" + e.toString(),
                                                      Toast.LENGTH_SHORT).show();
                                              pDialog.dismiss();

                                          }
                                      }
                );
    }

    private void updateVendor() {
        Map<String, Object> newContact = new HashMap<>();

        newContact.put("OrderId", orderId);
        db.collection("Vendors").document("users").collection("users").document(userID).collection("orders").document(orderId2).update(newContact)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        pDialog.dismiss();
                        Toast.makeText(InstantCheckoutActivity.this, "Your order has been sent", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(InstantCheckoutActivity.this, OrdersActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                                          @Override
                                          public void onFailure(@NonNull Exception e) {
                                              Toast.makeText(InstantCheckoutActivity.this, "ERROR" + e.toString(),
                                                      Toast.LENGTH_SHORT).show();
                                              pDialog.dismiss();

                                          }
                                      }
                );
    }


    private void getUser() {
        users =  db.collection("Vendors").document("users").collection("users").document(userId);
        users.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                              @Override
                                              public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                  if (task.isSuccessful()) {
                                                      // pDialog.dismiss();
                                                      DocumentSnapshot doc = task.getResult();
                                                      if (doc.exists()) {
                                                          //pDialog.dismiss();
                                                          userType = 4;
                                                          username = doc.getString("Name");
                                                          useremail = doc.getString("Email");
                                                          userPhoto = doc.getString("Profile_pic");
                                                          userPhone = doc.getString("Phone");
                                                          userGender = doc.getString("Gender");
                                                          userHome = doc.getString("HomeAddress");
                                                          newAddress = doc.getString("HomeAddress");
                                                          lga = doc.getString("HomeAddress");
                                                          etAddress.setText(userHome);
                                                          etUsername.setText(username);

                                                      }else {
                                                          userType = 5;
                                                          users =  db.collection("Customers").document("users").collection("users").document(userId);
                                                          users.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                                                @Override
                                                                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                                                    if (task.isSuccessful()) {
                                                                                                        // pDialog.dismiss();
                                                                                                        DocumentSnapshot doc = task.getResult();
                                                                                                        if (doc.exists()) {
                                                                                                            //pDialog.dismiss();
                                                                                                            username = doc.getString("Name");
                                                                                                            useremail = doc.getString("Email");
                                                                                                            userPhoto = doc.getString("Profile_pic");
                                                                                                            userPhone = doc.getString("Phone");
                                                                                                            userGender = doc.getString("Gender");
                                                                                                            userHome = doc.getString("HomeAddress");
                                                                                                            newAddress = doc.getString("HomeAddress");
                                                                                                            order = doc.getString("CanOrder");
                                                                                                            lga = doc.getString("Lga");
                                                                                                            etAddress.setText(userHome);
                                                                                                            etUsername.setText(username);
                                                                                                        }else {
                                                                                                            Toast.makeText(InstantCheckoutActivity.this, "user does not exit", Toast.LENGTH_SHORT).show();
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                          )
                                                                  .addOnFailureListener(new OnFailureListener() {
                                                                      @Override
                                                                      public void onFailure(@NonNull Exception e) {
                                                                          // pDialog.dismiss();
                                                                          Toast.makeText(InstantCheckoutActivity.this, "error" + e, Toast.LENGTH_LONG).show();
                                                                      }
                                                                  });
                                                      }
                                                  }
                                              }
                                          }
        )
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // pDialog.dismiss();
                        Toast.makeText(InstantCheckoutActivity.this, "error" + e, Toast.LENGTH_LONG).show();
                    }
                });
    }
    public void Select_payment(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        int id = view.getId();
        if (id == R.id.delivery) {
            if (checked) {
                paymentType = 1;
                paymentMethod = "pay on delivery";
            }
        } else if (id == R.id.card) {
            if (checked) {
                paymentType = 2;
                paymentMethod = "pay with card";
            }
        }
    }


    private void getDays() {
        users =  db.collection("Vendors").document("shops").collection("shops").document(shopID);
        users.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                              @Override
                                              public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                  if (task.isSuccessful()) {
                                                      // pDialog.dismiss();
                                                      DocumentSnapshot doc = task.getResult();
                                                      if (doc.exists()) {
                                                          //pDialog.dismiss();
                                                          mon = doc.getString("Monday");
                                                          tue = doc.getString("Tuesday");
                                                          wed = doc.getString("Wednesday");
                                                          thu = doc.getString("Thursday");
                                                          fri = doc.getString("Friday");
                                                          sat = doc.getString("Saturday");
                                                          sun = doc.getString("Sunday");
                                                          shopOrder = doc.getString("ShopOrder");
                                                          locationOrder =  doc.getString("ShopCoverage");

                                                      }
                                                  }
                                              }
                                          }
        )
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // pDialog.dismiss();
                        Toast.makeText(InstantCheckoutActivity.this, "error" + e, Toast.LENGTH_LONG).show();
                    }
                });
    }
    private void getConstants() {
        users =  db.collection("Vendors").document("Constants");
        users.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                              @Override
                                              public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                  if (task.isSuccessful()) {
                                                      // pDialog.dismiss();
                                                      DocumentSnapshot doc = task.getResult();
                                                      if (doc.exists()) {
                                                          //pDialog.dismiss();
                                                          cardPayment = doc.getString("CardPayment");

                                                      }
                                                  }
                                              }
                                          }
        )
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // pDialog.dismiss();
                       // Toast.makeText(PromoteActivity.this, "error" + e, Toast.LENGTH_LONG).show();
                    }
                });
    }

}