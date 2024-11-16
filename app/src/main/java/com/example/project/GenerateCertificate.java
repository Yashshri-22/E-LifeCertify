package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GenerateCertificate extends AppCompatActivity {

    Button generate_certificate;
    Button view_certificate;
    String viewUrl;
    JsonObjectRequest viewRequest;
    RequestQueue queue;
    ProgressBar loading;
    private WebView webView;
    EditText pensioner_date_of_birth;
    EditText pensioner_date_of_joining;
    EditText pensioner_date_of_retirement;
    EditText pensioner_name, pensioner_mobileNo, pensioner_adharNo, pensioner_gender, pensioner_designation, pensioner_typeOfRetirement, pensioner_nomineeType, pensioner_nomineeName, pensioner_height, pensioner_birthMark, pensioner_bankName, pensioner_bankAccountNumber, pensioner_address;
    Calendar calendar;
    String url;
    boolean changeButtonText;
    JsonObjectRequest request;
    private String pensioncode, firstName, middleName, lastName, fullName, mobileNumber, designation, dateOfBirth, dateOfJoining, dateOfRetirement, typeOfRetirement, nomineeName, height, birthMark, bankName, bankAccountNumber, address, adharNumber, gender, nomineeRelation;
    int pensionerId;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_certificate);
        generate_certificate = findViewById(R.id.generateCertificate);
        view_certificate = findViewById(R.id.viewCertificate);
        pensioner_date_of_birth = findViewById(R.id.pensioner_date_of_birth);
        pensioner_date_of_joining = findViewById(R.id.pensioner_date_of_joining);
        pensioner_date_of_retirement = findViewById(R.id.pensioner_date_of_retirement);
        calendar = Calendar.getInstance();
        pensioner_name = findViewById(R.id.pensioner_full_name);
        pensioner_mobileNo = findViewById(R.id.pensioner_mobileNo);
        pensioner_adharNo = findViewById(R.id.pensioner_aadharNo);
        pensioner_gender = findViewById(R.id.pensioner_gender);
        pensioner_designation = findViewById(R.id.pensioner_designation);
        pensioner_typeOfRetirement = findViewById(R.id.pensioner_type_of_retirement);
        pensioner_nomineeType = findViewById(R.id.pensioner_nominee_type);
        pensioner_nomineeName = findViewById(R.id.pensioner_nominee_name);
        pensioner_height = findViewById(R.id.pensioner_height);
        pensioner_birthMark = findViewById(R.id.pensioner_birth_mark);
        pensioner_bankName = findViewById(R.id.pensioner_bank_name);
        pensioner_bankAccountNumber = findViewById(R.id.pensioner_bank_accNo);
        pensioner_address = findViewById(R.id.pensioner_address);
        loading = findViewById(R.id.viewCertificate_loading);
        webView = findViewById(R.id.webView);

        getPensionerData();

        pensioner_date_of_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(pensioner_date_of_birth);
            }
        });

        pensioner_date_of_joining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(pensioner_date_of_joining);
            }
        });

        pensioner_date_of_retirement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(pensioner_date_of_retirement);
            }
        });

        generate_certificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GenerateCertificate.this, chooseMethod.class);
                intent.putExtra("MobileNo", mobileNumber);
                Log.d("mobile from generate", mobileNumber);
                intent.putExtra("pensionerId", pensionerId);
                startActivity(intent);
            }
        });

        changeButtonText = getIntent().getBooleanExtra("changeButtonText", false);

        if (changeButtonText) {
            generate_certificate.setVisibility(View.GONE);
            view_certificate.setVisibility(View.VISIBLE);
        }

        view_certificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewCertificate();
            }
        });
    }

    private void showDatePicker(EditText dateEditText) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Update the EditText with the selected date
                dateEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
            }
        }, year, month, dayOfMonth);

        datePickerDialog.show();
    }

    private void getPensionerData() {
        url = "https://kycdemo.amshoft.in/api/pensioner/GetPensionerPersonalDetails";
        queue = Volley.newRequestQueue(GenerateCertificate.this);
        request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Intent intent = getIntent();
                    String mobile_number = intent.getStringExtra("mobileNo");
                    Log.d("mobile after clear", mobile_number);
                    JSONArray dataArray = response.getJSONArray("data");
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject dataObject = dataArray.getJSONObject(i);
                        mobileNumber = dataObject.getString("mobileNumber");
                        if (mobile_number.equals(mobileNumber)) {
                            pensionerId = dataObject.getInt("pensionerId");
                            pensioncode = String.valueOf(dataObject.getInt("pensionCode"));
                            firstName = dataObject.getString("firstName");
                            middleName = dataObject.getString("middleName");
                            lastName = dataObject.getString("lastName");
                            fullName = String.format("%s %s %s", firstName, middleName, lastName).trim();
                            designation = dataObject.getString("designation");
                            dateOfBirth = dataObject.getString("dateOfBirth");
                            dateOfJoining = dataObject.getString("dateOfJoining");
                            dateOfRetirement = dataObject.getString("dateOfRetirment");
                            typeOfRetirement = dataObject.getString("typeOfRetirment");
                            nomineeName = dataObject.getString("nomineeName");
                            height = dataObject.getString("height");
                            birthMark = dataObject.getString("birthMark");
                            bankName = dataObject.getString("bankName");
                            bankAccountNumber = dataObject.getString("bankAccountNumber");
                            address = dataObject.getString("address");
                            adharNumber = dataObject.getString("adharNumber");
                            gender = dataObject.getString("gender");
                            nomineeRelation = dataObject.getString("nomineeRelation");


                            pensioner_date_of_birth.setText(dateOfBirth);
                            pensioner_date_of_joining.setText(dateOfJoining);
                            pensioner_date_of_retirement.setText(dateOfRetirement);
                            pensioner_name.setText(fullName);
                            pensioner_mobileNo.setText(mobileNumber);
                            pensioner_adharNo.setText(adharNumber);
                            pensioner_gender.setText(gender);
                            pensioner_designation.setText(designation);
                            pensioner_typeOfRetirement.setText(typeOfRetirement);
                            pensioner_nomineeType.setText(nomineeRelation);
                            pensioner_nomineeName.setText(nomineeName);
                            pensioner_height.setText(height);
                            pensioner_birthMark.setText(birthMark);
                            pensioner_bankName.setText(bankName);
                            pensioner_bankAccountNumber.setText(bankAccountNumber);
                            pensioner_address.setText(address);

                            pensioner_date_of_birth.setEnabled(false);
                            pensioner_date_of_joining.setEnabled(false);
                            pensioner_date_of_retirement.setEnabled(false);
                            pensioner_name.setEnabled(false);
                            pensioner_mobileNo.setEnabled(false);
                            pensioner_adharNo.setEnabled(false);
                            pensioner_gender.setEnabled(false);
                            pensioner_designation.setEnabled(false);
                            pensioner_typeOfRetirement.setEnabled(false);
                            pensioner_nomineeType.setEnabled(false);
                            pensioner_nomineeName.setEnabled(false);
                            pensioner_height.setEnabled(false);
                            pensioner_birthMark.setEnabled(false);
                            pensioner_bankName.setEnabled(false);
                            pensioner_bankAccountNumber.setEnabled(false);
                            pensioner_address.setEnabled(false);
                            return;
                        }
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GenerateCertificate.this, "No data found", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);

    }

    private void ViewCertificate() {
        viewUrl = "http://kyccertificate.amshoft.in/Home/getCertificate/"+pensionerId;

        try {
            // Create a new Intent with the URL
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(viewUrl));
            // Set the package name to Chrome
            intent.setPackage("com.android.chrome");
            // Start the activity with the intent
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // If Chrome is not available, catch the exception and open in the default browser
            Log.e("BrowserError", "Chrome is not installed");
            Toast.makeText(this, "Chrome is not installed", Toast.LENGTH_SHORT).show();
            openInDefaultBrowser(viewUrl);
        }

        String smsUrl = "https://kycdemo.amshoft.in/api/pensioner/SendCertificateLink?pensionerId="+pensionerId;
        JsonObjectRequest smsRequest = new JsonObjectRequest(Request.Method.POST, smsUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String responseMessage = null;
                try {
                    responseMessage = response.getString("responseMessage");
                    Toast.makeText(GenerateCertificate.this, responseMessage, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("API_ERROR", "Failed to fetch data from the API: " + error.getMessage());
                Toast.makeText(GenerateCertificate.this, "Certificate not generated", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(smsRequest);

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void loadHTMLContentIntoWebView(String htmlContent) {
        // Enable JavaScript in the WebView
        webView.getSettings().setJavaScriptEnabled(true);

        // Load the HTML content
        webView.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null);

        // Make the WebView visible
        webView.setVisibility(View.VISIBLE);
    }
    private void openInDefaultBrowser(String url) {
        // If Chrome is not available, fallback to opening in the default browser
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

}