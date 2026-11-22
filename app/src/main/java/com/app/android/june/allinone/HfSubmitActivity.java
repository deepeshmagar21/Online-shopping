package com.app.android.june.allinone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class HfSubmitActivity extends AppCompatActivity {
    private EditText etEmail, etWork, etState, full_Name, etPhone, etHome, etCountry, etShopName, etShopAddress;
    private String email, state, name, work, phone, home, userId, country, gender, photo_url = "", ID, formattedDate, shopName, shopAddress, userID;
    private ProgressDialog pDialog;
    private static final String TAG = "HfSubmitActivity";
    FirebaseFirestore db;
    TextView textView;
    Long date;
    private FirebaseUser user;
    DocumentReference users;
    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = "key=" +
            "AAAAndDVwl4:APA91bGzZGz2Le7rcdqDhufmQkQNqtTpXmmbbDaS1ExaUTKkV3l5VL-bAA54NQ_jfqp7lRjD8-ndzJcof8AHhMBLyYI7tE8-L_VDYMqbZhqbPlkQnmB79V6xBunWLxRSKBW56-N7P1qi";
    final private String contentType = "application/json";

    String NOTIFICATION_TITLE;
    String NOTIFICATION_MESSAGE;
    String TOPIC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hf_submit);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        full_Name = findViewById(R.id.name);
        etEmail = findViewById(R.id.email);
        etPhone = findViewById(R.id.phone);
        etHome = findViewById(R.id.home);
        etCountry = findViewById(R.id.country);
        etWork = findViewById(R.id.work);
        etState = findViewById(R.id.state);
        textView = findViewById(R.id.text);
        etShopAddress = findViewById(R.id.shopaddress);
        etShopName = findViewById(R.id.shopname);

        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userId = user.getUid();
        } else {
            userId = "";
        }
        date = new Date().getTime();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dddf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        formattedDate = dddf.format(c.getTime());

        if (getIntent().hasExtra("name")) {
            shopName = getIntent().getExtras().getString("name");
            shopAddress = getIntent().getExtras().getString("location");
            userID = getIntent().getExtras().getString("userId");
            ID = getIntent().getExtras().getString("id");
            etShopName.setText(shopName);
            etShopAddress.setText(shopAddress);
            getUsers();
        }
    }

    private void getUsers() {
        if (userId == null || userId.isEmpty()) return;
        users = db.collection("Customers").document("users").collection("users").document(userId);
        users.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc != null && doc.exists()) {
                        name = doc.getString("Name");
                        email = doc.getString("Email");
                        photo_url = doc.getString("Profile_pic");
                        phone = doc.getString("Phone");
                        gender = doc.getString("Gender");
                        home = doc.getString("HomeAddress");
                        state = doc.getString("State");
                        if (state != null) etState.setText(state);
                        if (name != null) full_Name.setText(name);
                        if (email != null) etEmail.setText(email);
                        if (home != null) etHome.setText(home);
                        if (phone != null) etPhone.setText(phone);
                    } else {
                        Toast.makeText(HfSubmitActivity.this, "User does not exist", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(HfSubmitActivity.this, "error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void submit(View view) {
        name = full_Name != null ? full_Name.getText().toString().trim() : "";
        email = etEmail != null ? etEmail.getText().toString().toLowerCase().trim() : "";
        phone = etPhone != null ? etPhone.getText().toString().trim() : "";
        home = etHome != null ? etHome.getText().toString().toLowerCase().trim() : "";
        work = etWork != null ? etWork.getText().toString().toLowerCase().trim() : "";
        state = etState != null ? etState.getText().toString().toLowerCase().trim() : "";
        country = etCountry != null ? etCountry.getText().toString().toLowerCase().trim() : "";

        if (validateInputs()) {
            writeNewUser();
        }
    }

    public void Select_gender(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        int id = view.getId();
        if (id == R.id.male) {
            if (checked) gender = "Male";
        } else if (id == R.id.female) {
            if (checked) gender = "Female";
        }
    }

    private boolean validateInputs() {
        String KEY_EMPTY = "";
        if (KEY_EMPTY.equals(name)) {
            full_Name.setError("Invalid input");
            full_Name.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(email)) {
            etEmail.setError("Invalid input");
            etEmail.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(work)) {
            etWork.setError("Invalid input");
            etWork.requestFocus();
            return false;
        }
        if (phone.length() < 10) {
            etPhone.setError("Invalid input");
            etPhone.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(state)) {
            etState.setError("Invalid input");
            etState.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(country)) {
            etCountry.setError("Invalid input");
            etCountry.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(phone)) {
            etPhone.setError("Invalid input");
            etPhone.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(home)) {
            etHome.setError("Invalid input");
            etHome.requestFocus();
            return false;
        }
        if (gender == null) {
            Toast.makeText(this, "Please select a gender", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void displayLoader() {
        pDialog = new ProgressDialog(HfSubmitActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private void writeNewUser() {
        displayLoader();
        Map<String, Object> newContact = new HashMap<>();
        newContact.put("Name", name);
        newContact.put("Gender", gender);
        newContact.put("Email", email);
        newContact.put("Profile_pic", photo_url != null ? photo_url : "");
        newContact.put("Occupation", work);
        newContact.put("UserID", userId);
        newContact.put("Phone", phone);
        newContact.put("State", state);
        newContact.put("Country", country);
        newContact.put("Hf_delivery", "false");
        newContact.put("HomeAddress", home);
        newContact.put("Created_date", formattedDate);
        newContact.put("Date", date);
        newContact.put("Status", "Not Approved");

        if (userID == null || userID.isEmpty()) {
            userID = userId;
        }

        db.collection("Vendors").document("users").collection("users").document(userID).collection("Hf").add(newContact)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(HfSubmitActivity.this, "Submitted Successfully", Toast.LENGTH_SHORT).show();
                        if (pDialog != null && pDialog.isShowing()) pDialog.dismiss();
                        onBackPressed();
                        sendNotification();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(HfSubmitActivity.this, "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        if (pDialog != null && pDialog.isShowing()) pDialog.dismiss();
                    }
                });
    }

    private void sendNotification() {
        if (userID == null) return;
        TOPIC = "/topics/" + userID;
        NOTIFICATION_TITLE = "New User Request";
        NOTIFICATION_MESSAGE = "You have received a new request from " + name;

        JSONObject notification = new JSONObject();
        JSONObject notificationBody = new JSONObject();
        try {
            notificationBody.put("title", NOTIFICATION_TITLE);
            notificationBody.put("message", NOTIFICATION_MESSAGE);

            notification.put("to", TOPIC);
            notification.put("data", notificationBody);
        } catch (JSONException e) {
            Log.e(TAG, "onCreate: " + e.getMessage());
        }
        sendNotification(notification);
    }

    private void sendNotification(JSONObject notification) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "onResponse: " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TAG, "onErrorResponse: Didn't work");
                    }
                }) {
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void cancel(View view) {
        onBackPressed();
    }
}