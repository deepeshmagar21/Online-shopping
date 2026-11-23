package com.app.android.june.allinone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Random;

public class CusLocationActivity extends AppCompatActivity implements OnMapReadyCallback {
    FirebaseFirestore db;
    DocumentReference users;
    String UserID;
    Double sellerLat = 37.5665, sellerLng = 126.9780; // Default seller (Seoul)
    Double buyerLat = 35.0160, buyerLng = 126.7100;   // Default buyer (Naju-si / Jeollanam-do)
    MapFragment mapFragment;
    EditText editText;

    CountDownTimer countDownTimer;
    private int counter = 40;
    AppLocationService appLocationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cus_location);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapView);
        editText = findViewById(R.id.myLocation);
        db = FirebaseFirestore.getInstance();

        appLocationService = new AppLocationService(CusLocationActivity.this);

        // Generate random area in South Korea if no specific location exists
        generateKoreaRandomLocations();

        if (getIntent().hasExtra("UserID")) {
            UserID = getIntent().getStringExtra("UserID");
            getUserLocation();
        } else {
            if (mapFragment != null) {
                mapFragment.getMapAsync(this);
            }
            showAddress();
        }
    }

    private void generateKoreaRandomLocations() {
        Random rand = new Random();
        // Korea latitude roughly 34.0 to 38.0, longitude 126.0 to 129.0
        double baseLat = 35.0 + (37.5 - 35.0) * rand.nextDouble();
        double baseLng = 126.5 + (129.0 - 126.5) * rand.nextDouble();

        sellerLat = baseLat + (rand.nextDouble() - 0.5) * 0.05;
        sellerLng = baseLng + (rand.nextDouble() - 0.5) * 0.05;

        buyerLat = baseLat + (rand.nextDouble() - 0.5) * 0.08;
        buyerLng = baseLng + (rand.nextDouble() - 0.5) * 0.08;
    }

    private void getUserLocation() {
        if (UserID == null || UserID.isEmpty()) {
            if (mapFragment != null) mapFragment.getMapAsync(this);
            showAddress();
            return;
        }

        users = db.collection("Vendors").document("users").collection("users").document(UserID).collection("location").document("location");
        users.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc != null && doc.exists()) {
                        String latitude = doc.getString("Latitude");
                        String longitude = doc.getString("Longitude");
                        if (latitude != null && longitude != null) {
                            try {
                                buyerLat = Double.parseDouble(latitude);
                                buyerLng = Double.parseDouble(longitude);
                            } catch (Exception ignored) {}
                        }
                    }
                }
                if (mapFragment != null) {
                    mapFragment.getMapAsync(CusLocationActivity.this);
                }
                showAddress();
                startTimer();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (mapFragment != null) {
                    mapFragment.getMapAsync(CusLocationActivity.this);
                }
                showAddress();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng sellerLoc = new LatLng(sellerLat, sellerLng);
        LatLng buyerLoc = new LatLng(buyerLat, buyerLng);

        // Add Seller / Store Marker
        googleMap.addMarker(new MarkerOptions()
                .position(sellerLoc)
                .title("Seller / Store Location")
                .snippet("Dispatching order"));

        // Add Buyer / Customer Marker
        googleMap.addMarker(new MarkerOptions()
                .position(buyerLoc)
                .title("Buyer / Delivery Location")
                .snippet("Destination address"));

        // Draw Route Polyline from Seller to Buyer
        googleMap.addPolyline(new PolylineOptions()
                .add(sellerLoc, buyerLoc)
                .width(8f)
                .color(Color.RED));

        // Focus camera on Buyer location
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(buyerLoc, 14));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(buyerLoc)
                .zoom(15)
                .bearing(30)
                .tilt(45)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    public void startTimer() {
        countDownTimer = new CountDownTimer(40000, 1000) {
            public void onTick(long millisUntilFinished) {
                counter--;
            }
            public void onFinish() {
                counter = 40;
                getUserLocation();
            }
        }.start();
    }

    private void showAddress() {
        LocationAddress.getAddressFromLocation(buyerLat, buyerLng,
                getApplicationContext(), new GeocoderHandler());
    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            if (editText != null && locationAddress != null) {
                editText.setText("Delivery Route: " + locationAddress);
            }
        }
    }
}