package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

public class LoginPage extends AppCompatActivity {

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch toogle_screen;
    EditText mobileNo;
    EditText otp;
    EditText admin_id;
    EditText password;
    ProgressBar progressBar;
    Button generate_otp;
    Button validate_otp;
    Button loginButton;
    String generate_url;
    String validate_url;
    String mobile_number;
    String otp_number;
    RequestQueue queue;
    JsonObjectRequest generate_request;
    JsonObjectRequest validate_request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        toogle_screen = findViewById(R.id.toggle_screen);
        mobileNo = findViewById(R.id.mobileNo);
        otp = findViewById(R.id.otp);
        admin_id = findViewById(R.id.adminId);
        password = findViewById(R.id.password);
        generate_otp = findViewById(R.id.generateOtp);
        validate_otp = findViewById(R.id.validateOtp);
        loginButton = findViewById(R.id.loginButton);
        progressBar = findViewById(R.id.login_loading);
        queue = Volley.newRequestQueue(this);

        toogle_screen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showAdminUI();
                } else {
                    showPensionerUI();
                }
            }
        });

        generate_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobile_number = mobileNo.getText().toString().trim();
                if (isValidMobileNumber(mobile_number)) {
                    try {
                        generateOtp(mobile_number);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    // Invalid mobile number
                    Toast.makeText(LoginPage.this, "Enter Valid Mobile number!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        validate_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp_number = otp.getText().toString().trim();
                try {
                    validateOtp();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    // Function to validate mobile number
    private boolean isValidMobileNumber(String mobileNumber) {
        return mobileNumber.length() == 10 && TextUtils.isDigitsOnly(mobileNumber);
    }

    private void showPensionerUI() {
        mobileNo.setVisibility(View.VISIBLE);
        otp.setVisibility(View.VISIBLE);
        generate_otp.setVisibility(View.VISIBLE);

        admin_id.setVisibility(View.GONE);
        password.setVisibility(View.GONE);
        loginButton.setVisibility(View.GONE);

    }

    private void showAdminUI() {
        admin_id.setVisibility(View.VISIBLE);
        password.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.VISIBLE);

        mobileNo.setVisibility(View.GONE);
        otp.setVisibility(View.GONE);
        generate_otp.setVisibility(View.GONE);
        validate_otp.setVisibility(View.GONE);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String admin_id_text = admin_id.getText().toString().trim();
                String password_text = password.getText().toString().trim();
                if (!admin_id_text.isEmpty() || !password_text.isEmpty()) {
                    if (admin_id_text.equals("admin") && password_text.equals("1234")) {
                        Intent intent = new Intent(LoginPage.this, PpoNumber.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginPage.this, "Incorrect Admin Id or Password!!!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginPage.this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void generateOtp(String mobileNo) throws JSONException {
        generate_url = "https://kycdemo.amshoft.in/api/pensioner/GenerateOTP";
        progressBar.setVisibility(View.VISIBLE);
        generate_otp.setVisibility(View.GONE);
        JSONObject generateOtp_data = new JSONObject();
        generateOtp_data.put("mobileNumber", mobileNo);

        generate_request = new JsonObjectRequest(Request.Method.POST, generate_url, generateOtp_data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    progressBar.setVisibility(View.GONE);
                    String responseMessage = response.getString("responseMessage");
                    Toast.makeText(LoginPage.this, responseMessage, Toast.LENGTH_SHORT).show();
                    generate_otp.setVisibility(View.GONE);
                    validate_otp.setVisibility(View.VISIBLE);
                    otp.setEnabled(true);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                generate_otp.setVisibility(View.VISIBLE);
                Log.e("API_ERROR", "Failed to fetch data from the API: " + error.getMessage());
                Toast.makeText(LoginPage.this, "Failed to generate otp", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(generate_request);
    }

    private void validateOtp() throws JSONException {
        validate_url = "https://kycdemo.amshoft.in/api/pensioner/LoginusingOTP";
        progressBar.setVisibility(View.VISIBLE);
        validate_otp.setVisibility(View.GONE);

        JSONObject validateOtp_data = new JSONObject();
        validateOtp_data.put("mobileNumber", mobile_number);
        validateOtp_data.put("otp", otp_number);

        validate_request = new JsonObjectRequest(Request.Method.POST, validate_url, validateOtp_data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    progressBar.setVisibility(View.GONE);
                    String responseMessage = response.getString("responseMessage");
                    boolean success = response.getBoolean("success");
                    Toast.makeText(LoginPage.this, responseMessage, Toast.LENGTH_SHORT).show();
                    if (success) {
                        Intent intent = new Intent(LoginPage.this, GenerateCertificate.class);
                        intent.putExtra("mobileNo", mobile_number);
                        startActivity(intent);
                        validate_otp.setVisibility(View.GONE);
                        generate_otp.setVisibility(View.VISIBLE);
                        mobileNo.setText("");
                        otp.setText("");
                        otp.setEnabled(false);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                validate_otp.setVisibility(View.VISIBLE);
                Log.e("API_ERROR", "Failed to fetch data from the API: " + error.getMessage());
                Toast.makeText(LoginPage.this, "Failed to validate otp", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(validate_request);
    }
}