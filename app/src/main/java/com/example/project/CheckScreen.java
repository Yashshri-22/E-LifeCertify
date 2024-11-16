package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CheckScreen extends AppCompatActivity {

    TextView ppo_check_screen;
    EditText check_one;
    EditText check_two;
    EditText check_three;
    EditText check_four;
    EditText fullName, mobileNo, aadharNo, gender, designation, typeOfRetirement, nomineeType, nomineeName, height, birthMark, bankName, bankAcc, address;
    EditText date_of_birth;
    EditText date_of_joining;
    EditText date_of_retirement;
    Calendar calendar;
    Button continue_step1;
    Dialog dialog;
    ScrollView scrollView;
    StringBuilder ppo = new StringBuilder();
    String ppo_string;
    int ppo_number, pensionerId;
    Intent intent;
    String url;
    String url_submit;
    RequestQueue queue;
    JsonObjectRequest request;
    JsonObjectRequest jsonObjectRequest;
    int toSubmit = 0;
    String pensioner_name, pensioner_mobileNo, pensioner_designation, pensioner_dateOfBirth, pensioner_height, pensioner_birthMark, pensioner_dateOfJoining, pensioner_dateOfRetirement, pensioner_typeOfRetirement, pensioner_nomineeType, pensioner_nomineeName, pensioner_bankName, pensioner_bankAcc, pensioner_pensionType, pensioner_nomineeBirthDate, pensioner_address, pensioner_aadharId, pensioner_gender;
    String ppoOne, ppoTwo, ppoThree, ppoFour;
    ProgressBar check_loader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_screen);

        check_one = findViewById(R.id.check_one);
        check_two = findViewById(R.id.check_two);
        check_three = findViewById(R.id.check_three);
        check_four = findViewById(R.id.check_four);
        fullName = findViewById(R.id.full_name);
        mobileNo = findViewById(R.id.mobileNo);
        aadharNo = findViewById(R.id.aadharNo);
        gender = findViewById(R.id.gender);
        designation = findViewById(R.id.designation);
        date_of_birth = findViewById(R.id.date_of_birth);
        date_of_joining = findViewById(R.id.date_of_joining);
        date_of_retirement = findViewById(R.id.date_of_retirement);
        typeOfRetirement = findViewById(R.id.type_of_retirement);
        nomineeType = findViewById(R.id.nominee_type);
        nomineeName = findViewById(R.id.nominee_name);
        height = findViewById(R.id.height);
        birthMark = findViewById(R.id.birth_mark);
        bankName = findViewById(R.id.bank_name);
        bankAcc = findViewById(R.id.bank_accNo);
        address = findViewById(R.id.address);
        continue_step1 = findViewById(R.id.continue_step1);
        dialog = new Dialog(this);
        scrollView = findViewById(R.id.check_scrollview);
        calendar = Calendar.getInstance();
        ppo_check_screen = findViewById(R.id.ppo_check_screen);
        ppo.setLength(0); // Clear the StringBuilder
        check_loader = findViewById(R.id.check_loader);

        // Use getIntent() to get the Intent that started this activity
        intent = getIntent();
        boolean fromPpoNumberScreen = intent.getBooleanExtra("fromPpoNumberScreen", false);

        if (fromPpoNumberScreen) {
            fromPPOScreen(intent);
        } else {
            fromShowAllScreen(intent);
        }

        continue_step1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onClick(View v) {
                if (fromPpoNumberScreen) {
                    dialog.setContentView(R.layout.confirmation_dialog);
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.setCancelable(false);
                    Button submit = dialog.findViewById(R.id.submit);
                    Button cancel = dialog.findViewById(R.id.cancel);

                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                submitFormData();
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                } else {
                    Intent intent1 = new Intent(CheckScreen.this, FaceRecognition.class);
                    intent1.putExtra("fromPpoNumberScreen", false);
                    intent1.putExtra("one", ppoOne);
                    intent1.putExtra("two", ppoTwo);
                    intent1.putExtra("three", ppoThree);
                    intent1.putExtra("four", ppoFour);
                    startActivity(intent1);
                }
            }
        });

        date_of_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(date_of_birth);
            }
        });

        date_of_joining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(date_of_joining);
            }
        });

        date_of_retirement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(date_of_retirement);
            }
        });

    }

    private void showDatePicker(final EditText dateEditText) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Update the EditText with the selected date
                dateEditText.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            }
        }, year, month, dayOfMonth);

        datePickerDialog.show();
    }

    private void getFormData() {
        url = "http://103.224.247.134:8085/Personnel/rest/webservice/getByPPONO?PPONo=" + ppo_number;
        check_loader.setVisibility(View.VISIBLE);
        queue = Volley.newRequestQueue(CheckScreen.this);
        request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    check_loader.setVisibility(View.GONE);
                    JSONArray dataArray = response.getJSONArray("allData");
                    if (dataArray.length() > 0) {
                        JSONObject dataObject = dataArray.getJSONObject(0);
                        pensioner_name = dataObject.optString("Pensioner Name");
                        pensioner_mobileNo = dataObject.optString("Mobile No");
                        pensioner_designation = dataObject.optString("Designation");
                        pensioner_dateOfBirth = convertDateFormat(dataObject.optString("date Of Birth"));
                        pensioner_height = dataObject.optString("Height");
                        pensioner_birthMark = dataObject.optString("Birth Mark");
                        pensioner_dateOfJoining = convertDateFormat(dataObject.optString("Date Of Joining"));
                        pensioner_dateOfRetirement = convertDateFormat(dataObject.optString("Date Of Retirement "));
                        pensioner_typeOfRetirement = dataObject.optString("Type Of Retirement");
                        pensioner_nomineeType = dataObject.optString("Nominee Type");
                        pensioner_nomineeName = dataObject.optString("Name Of Nominee");
                        pensioner_bankName = dataObject.optString("Bank Name");
                        pensioner_bankAcc = dataObject.optString("bank Account no.");
                        pensioner_pensionType = dataObject.optString("Pension Type");
                        pensioner_nomineeBirthDate = dataObject.optString("Nominee Birth Date");

                        // Set the data to the edittext of check screen
                        fullName.setText(pensioner_name);
                        mobileNo.setText(pensioner_mobileNo);
                        designation.setText(pensioner_designation);
                        birthMark.setText(pensioner_birthMark);
                        date_of_birth.setText(pensioner_dateOfBirth);
                        date_of_joining.setText(pensioner_dateOfJoining);
                        date_of_retirement.setText(pensioner_dateOfRetirement);
                        typeOfRetirement.setText(pensioner_typeOfRetirement);
                        nomineeType.setText(pensioner_nomineeType);
                        nomineeName.setText(pensioner_nomineeName);
                        height.setText(pensioner_height);
                        bankName.setText(pensioner_bankName);
                        bankAcc.setText(pensioner_bankAcc);
                    } else {
                        Toast.makeText(CheckScreen.this, "No data found for this PPO Number", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                check_loader.setVisibility(View.GONE);
                Log.e("API_ERROR", "Failed to fetch data from the API: " + error.getMessage());
                Toast.makeText(CheckScreen.this, "Failed to fetch the data for this PPO Number!!!", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);

    }

    private void submitFormData() throws JSONException {

        String full_name = fullName.getText().toString().trim();
        String[] parts = full_name.split(" ");

        String firstName = "";
        String middleName = "";
        String lastName = "";

        if (parts.length >= 1) {
            firstName = parts[0];
        }
        if (parts.length >= 2) {
            lastName = parts[parts.length - 1];
        }
        if (parts.length > 2) {
            StringBuilder middleNameBuilder = new StringBuilder();
            for (int i = 1; i < parts.length - 1; i++) {
                middleNameBuilder.append(parts[i]);
                if (i < parts.length - 2) {
                    middleNameBuilder.append(" ");
                }
            }
            middleName = middleNameBuilder.toString();
        }

        String mobile_no = mobileNo.getText().toString().trim();
        String aadhar_no = aadharNo.getText().toString().trim();
        String Gender = gender.getText().toString().trim();
        Log.d("Aadhar Number", aadhar_no);
        Log.d("Gender", Gender);
        String designation_var = designation.getText().toString().trim();
        String dob = date_of_birth.getText().toString().trim();
        String doj = date_of_joining.getText().toString().trim();
        String dor = date_of_retirement.getText().toString().trim();
        int type_of_retirement = Integer.parseInt(String.valueOf(typeOfRetirement.getText()));
        String nominee_name = nomineeName.getText().toString().trim();
        String height_var = height.getText().toString().trim();
        String birth_mark = birthMark.getText().toString().trim();
        String bank_name = bankName.getText().toString().trim();
        String bank_account = bankAcc.getText().toString().trim();
        String address_var = address.getText().toString().trim();
        String first_name = firstName;
        String middle_name = middleName;
        String last_name = lastName;


        url_submit = "https://kycdemo.amshoft.in/api/pensioner/RegisterPensioner";
        check_loader.setVisibility(View.VISIBLE);
        JSONObject submitData = new JSONObject();
        submitData.put("firstName", first_name);
        submitData.put("middleName", middle_name);
        submitData.put("lastName", last_name);
        submitData.put("mobileNumber", mobile_no);
        submitData.put("designation", designation_var);
        submitData.put("dateOfBirth", dob);
        submitData.put("dateOfJoining", doj);
        submitData.put("dateOfRetirment", dor);
        submitData.put("typeOfRetirment", type_of_retirement);
        submitData.put("nomineeRelation", "Son");
        submitData.put("nomineeName", nominee_name);
        submitData.put("height", height_var);
        submitData.put("birthMark", birth_mark);
        submitData.put("bankName", bank_name);
        submitData.put("bankAccountNumber", bank_account);
        submitData.put("address", address_var);
        submitData.put("userId", 1);
        submitData.put("pensionCode", ppo_number);
        submitData.put("adharNumber", aadhar_no);
        submitData.put("gender", Gender);
        submitData.put("pensionType", "F");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url_submit, submitData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    check_loader.setVisibility(View.GONE);
                    boolean success = response.getBoolean("success");
                    String responseMessage = response.getString("responseMessage");
                    JSONArray dataArray = response.getJSONArray("data");
                    JSONObject dataObject = dataArray.getJSONObject(0);
                    pensionerId = dataObject.getInt("pensionerId");

                    if (success) {
                        Toast.makeText(CheckScreen.this, "Form Submitted!!!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CheckScreen.this, FaceRecognition.class);
                        intent.putExtra("fromPpoNumberScreen", true);
                        intent.putExtra("One", check_one.getText().toString());
                        intent.putExtra("Two", check_two.getText().toString());
                        intent.putExtra("Three", check_three.getText().toString());
                        intent.putExtra("Four", check_four.getText().toString());
                        intent.putExtra("pensionerId", pensionerId);
                        startActivity(intent);
                        dialog.dismiss();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(CheckScreen.this, "Mobile Number already Exists!!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                check_loader.setVisibility(View.GONE);
                Log.e("API_ERROR", "Failed to fetch data from the API: " + error.getMessage());
                Toast.makeText(CheckScreen.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonObjectRequest);
    }

    public static String convertDateFormat(String inputDateStr) {
        String outputDateStr = "";
        String inputFormat = "dd/MM/yyyy";
        String outputFormat = "yyyy-MM-dd";
        try {
            SimpleDateFormat inputFormatter = new SimpleDateFormat(inputFormat, Locale.getDefault());
            SimpleDateFormat outputFormatter = new SimpleDateFormat(outputFormat, Locale.getDefault());

            Date date = inputFormatter.parse(inputDateStr);
            assert date != null;
            outputDateStr = outputFormatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputDateStr;
    }

    private void fromPPOScreen(Intent intent) {

        // Get the PPO numbers from the Intent
        ppoOne = intent.getStringExtra("One");
        ppoTwo = intent.getStringExtra("Two");
        ppoThree = intent.getStringExtra("Three");
        ppoFour = intent.getStringExtra("Four");

        // Set the PPO numbers to the EditTexts
        check_one.setText(ppoOne);
        check_two.setText(ppoTwo);
        check_three.setText(ppoThree);
        check_four.setText(ppoFour);

        // Make EditTexts uneditable
        check_one.setEnabled(false);
        check_two.setEnabled(false);
        check_three.setEnabled(false);
        check_four.setEnabled(false);


        ppo.append(check_one.getText().toString()).append(check_two.getText().toString()).append(check_three.getText().toString()).append(check_four.getText().toString());

        ppo_string = ppo.toString();
        ppo_number = Integer.parseInt(ppo_string);
        getFormData();

    }

    private void fromShowAllScreen(Intent intent) {
        ppo_string = intent.getStringExtra("Pension Code");

        //Retrieving values from the intent
        ppoOne = ppo_string.substring(0, 1);
        ppoTwo = ppo_string.substring(1, 2);
        ppoThree = ppo_string.substring(2, 3);
        ppoFour = ppo_string.substring(3, 4);

        // Set the PPO numbers to the EditTexts
        check_one.setText(ppoOne);
        check_two.setText(ppoTwo);
        check_three.setText(ppoThree);
        check_four.setText(ppoFour);

        // Make EditTexts uneditable
        check_one.setEnabled(false);
        check_two.setEnabled(false);
        check_three.setEnabled(false);
        check_four.setEnabled(false);

        //Retrieving the data to be displayed in the form
        pensioner_name = intent.getStringExtra("Full Name");
        pensioner_mobileNo = intent.getStringExtra("Mobile Number");
        pensioner_designation = intent.getStringExtra("Designation");
        pensioner_dateOfBirth = intent.getStringExtra("Date of Birth");
        pensioner_dateOfJoining = intent.getStringExtra("Date of Joining");
        pensioner_dateOfRetirement = intent.getStringExtra("Date of Retirement");
        pensioner_typeOfRetirement = intent.getStringExtra("Type of Retirement");
        pensioner_address = intent.getStringExtra("Address");
        pensioner_height = intent.getStringExtra("Height");
        pensioner_birthMark = intent.getStringExtra("Birth Mark");
        pensioner_bankName = intent.getStringExtra("Bank Name");
        pensioner_bankAcc = intent.getStringExtra("Bank Account Number");
        pensioner_nomineeName = intent.getStringExtra("Nominee Name");
        pensioner_aadharId = intent.getStringExtra("Aadhar Number");
        pensioner_gender = intent.getStringExtra("Gender");
        pensioner_nomineeType = intent.getStringExtra("Nominee Type");

        //Setting the values to edittext
        fullName.setText(pensioner_name);
        mobileNo.setText(pensioner_mobileNo);
        designation.setText(pensioner_designation);
        date_of_birth.setText(pensioner_dateOfBirth);
        date_of_joining.setText(pensioner_dateOfJoining);
        date_of_retirement.setText(pensioner_dateOfRetirement);
        typeOfRetirement.setText(pensioner_typeOfRetirement);
        address.setText(pensioner_address);
        height.setText(pensioner_height);
        birthMark.setText(pensioner_birthMark);
        bankName.setText(pensioner_bankName);
        bankAcc.setText(pensioner_bankAcc);
        nomineeName.setText(pensioner_nomineeName);
        nomineeType.setText(pensioner_nomineeType);
        aadharNo.setText(pensioner_aadharId);
        gender.setText(pensioner_gender);

        fullName.setEnabled(false);
        mobileNo.setEnabled(false);
        designation.setEnabled(false);
        date_of_birth.setEnabled(false);
        date_of_joining.setEnabled(false);
        date_of_retirement.setEnabled(false);
        typeOfRetirement.setEnabled(false);
        address.setEnabled(false);
        height.setEnabled(false);
        birthMark.setEnabled(false);
        bankName.setEnabled(false);
        bankAcc.setEnabled(false);
        nomineeName.setEnabled(false);
        nomineeType.setEnabled(false);
        aadharNo.setEnabled(false);
        gender.setEnabled(false);
    }
}
