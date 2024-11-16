package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

public class chooseMethod extends AppCompatActivity {

    Button faceRecoginition;
    Button fingerPrintScan;
    Dialog dialog;
    Dialog dialog_ques2;
    String generateUrl;
    RequestQueue queue;
    JsonObjectRequest generateRequest;
    boolean remarriageSelected = false;
    String remarriageDate;
    String remarriagePlace;
    boolean rejoinSelected = false;
    String rejoinDate;
    String payment;
    String organisation;
    String mobileno;
    int pensionerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_method);

        faceRecoginition = findViewById(R.id.face_recognition);
        fingerPrintScan = findViewById(R.id.finger_print);
        dialog = new Dialog(this);
        dialog_ques2 = new Dialog(this);
        queue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        mobileno = intent.getStringExtra("MobileNo");
        pensionerId = intent.getIntExtra("pensionerId", 0);

        //Clicking of face recognition button
        faceRecoginition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogCustom(R.layout.question_one_dialog);
            }
        });

        //Clicking of fingerPrint scan button
        fingerPrintScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogCustom(R.layout.question_one_dialog);
            }
        });
    }

    //show first dialog
    private void showDialogCustom(int layoutId) {
        //showing the dialog for first question
        dialog.setContentView(layoutId);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.show();
        Button yes = dialog.findViewById(R.id.yes);
        Button no = dialog.findViewById(R.id.no);

        //if yes is pressed then it will show all the fields and next button
        yes.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onClick(View v) {
                rejoinSelected = true;
                EditText institute_name = dialog.findViewById(R.id.institute_name);
                EditText new_joining_date = dialog.findViewById(R.id.new_joining_date);
                EditText current_salary = dialog.findViewById(R.id.current_salary);
                Button next = dialog.findViewById(R.id.next);

                institute_name.setVisibility(View.VISIBLE);
                new_joining_date.setVisibility(View.VISIBLE);
                current_salary.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);

                new_joining_date.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        // Check if the touch event is within the drawableEnd region
                        if (event.getAction() == MotionEvent.ACTION_UP && event.getRawX() >= (new_joining_date.getRight() - new_joining_date.getCompoundDrawables()[2].getBounds().width())) {
                            // Handle the click on the drawableEnd
                            showDatePicker(new_joining_date);
                            return true;
                        }
                        return false;
                    }
                });

                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rejoinDate = new_joining_date.getText().toString().trim();
                        payment = current_salary.getText().toString().trim();
                        organisation = institute_name.getText().toString().trim();
                        showDialogCustomSecond(R.layout.question_two_dialog);
                    }
                });

            }
        });

        //if no is pressed then it will show next button
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText institute_name = dialog.findViewById(R.id.institute_name);
                EditText new_joining_date = dialog.findViewById(R.id.new_joining_date);
                EditText current_salary = dialog.findViewById(R.id.current_salary);
                Button next = dialog.findViewById(R.id.next);
                institute_name.setVisibility(View.GONE);
                new_joining_date.setVisibility(View.GONE);
                current_salary.setVisibility(View.GONE);
                next.setVisibility(View.VISIBLE);

                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rejoinSelected = false;
                        organisation = "NO";
                        payment = "1234";
                        rejoinDate = "0000-00-00";
                        showDialogCustomSecond(R.layout.question_two_dialog);
                    }
                });
            }
        });
    }

    //show second dialog
    private void showDialogCustomSecond(int layoutId) {
        dialog.dismiss();
        dialog_ques2.setContentView(layoutId);
        dialog_ques2.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog_ques2.setCancelable(false);
        dialog_ques2.show();
        Button yes_ques2 = dialog_ques2.findViewById(R.id.yes_ques2);
        Button no_ques2 = dialog_ques2.findViewById(R.id.no_ques2);

        //if yes is pressed then all fields and submit and cancel button are shown
        yes_ques2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onClick(View v) {
                EditText place = dialog_ques2.findViewById(R.id.place);
                EditText date = dialog_ques2.findViewById(R.id.date);
                Button submit_dialog = dialog_ques2.findViewById(R.id.submit_dialog);
                Button cancel_dialog = dialog_ques2.findViewById(R.id.cancel_dialog);

                place.setVisibility(View.VISIBLE);
                date.setVisibility(View.VISIBLE);
                submit_dialog.setVisibility(View.VISIBLE);
                cancel_dialog.setVisibility(View.VISIBLE);

                //date picker
                date.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        // Check if the touch event is within the drawableEnd region
                        if (event.getAction() == MotionEvent.ACTION_UP && event.getRawX() >= (date.getRight() - date.getCompoundDrawables()[2].getBounds().width())) {
                            // Handle the click on the drawableEnd
                            showDatePicker(date);
                            return true;
                        }
                        return false;
                    }
                });

                //yes and submit button is pressed
                submit_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        remarriageSelected = true;
                        remarriagePlace = place.getText().toString().trim();
                        remarriageDate = date.getText().toString().trim();
                        dialog_ques2.dismiss();
                        GenerateCertificate();
                    }
                });

                //yes and cancel button is pressed
                cancel_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_ques2.dismiss();
                        Intent intent = new Intent(chooseMethod.this, GenerateCertificate.class);

                        // Set flags to clear the entire task stack and create a new task
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("mobileNo", mobileno);
                        startActivity(intent);
                        finish();
                    }
                });


            }
        });

        //if no is pressed then submit and cancel button are shown
        no_ques2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText place = dialog_ques2.findViewById(R.id.place);
                EditText date = dialog_ques2.findViewById(R.id.date);
                Button submit_dialog = dialog_ques2.findViewById(R.id.submit_dialog);
                Button cancel_dialog = dialog_ques2.findViewById(R.id.cancel_dialog);

                place.setVisibility(View.GONE);
                date.setVisibility(View.GONE);
                submit_dialog.setVisibility(View.VISIBLE);
                cancel_dialog.setVisibility(View.VISIBLE);

                //no and submit button is pressed
                submit_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        remarriageSelected = false;
                        remarriagePlace = "No";
                        remarriageDate = "0000-00-00";
                        GenerateCertificate();
                    }
                });

                //no and cancel button is pressed
                cancel_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(chooseMethod.this, GenerateCertificate.class);
                        intent.putExtra("mobileNo", mobileno);
                        // Set flags to clear the entire task stack and create a new task
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

    }

    //date picking function
    private void showDatePicker(EditText dateEditText) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(chooseMethod.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Display the chosen date in the EditText
                @SuppressLint("DefaultLocale") String formattedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
                dateEditText.setText(formattedDate);
            }
        },
                // Set the initial date in the date picker (you can set your own initial date)
                2024, 0, 1);

        datePickerDialog.show();
    }

    private void GenerateCertificate() {
        generateUrl = "https://kycdemo.amshoft.in/api/pensioner/GenerateCert";
        JSONObject postData = new JSONObject();

        try {
            // Add data based on user selections
            postData.put("pensionerId", pensionerId);
            postData.put("remarriage", remarriageSelected);
            postData.put("reJoin", rejoinSelected);

            // Add remarriage details if selected
            if (remarriageSelected) {
                JSONArray marriageDetailsArray = new JSONArray();
                JSONObject marriageDetails = new JSONObject();
                marriageDetails.put("date", remarriageDate);
                marriageDetails.put("place", remarriagePlace);
                marriageDetailsArray.put(marriageDetails);
                postData.put("marriageDetails", marriageDetailsArray);
            }

            // Add job details if selected
            if (rejoinSelected) {
                JSONArray jobDetailsArray = new JSONArray();
                JSONObject jobDetails = new JSONObject();
                jobDetails.put("date", rejoinDate);
                jobDetails.put("payment", payment);
                jobDetails.put("organisation", organisation);
                jobDetailsArray.put(jobDetails);
                postData.put("jobDetails", jobDetailsArray);
            }

            // Add latitude and longitude
            postData.put("lat", "latitude");
            postData.put("long", "longitude");

            generateRequest = new JsonObjectRequest(Request.Method.POST, generateUrl, postData, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String responseMessage = response.getString("responseMessage");
                        Toast.makeText(chooseMethod.this, responseMessage, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(chooseMethod.this, GenerateCertificate.class);

                        // Set flags to clear the entire task stack and create a new task
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("changeButtonText", true);
                        intent.putExtra("mobileNo", mobileno);
                        intent.putExtra("pensionerId",pensionerId);
                        Log.d("mobile from choose", mobileno);
                        startActivity(intent);
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("API_ERROR", "Failed to fetch data from the API: " + error.getMessage());
                    Toast.makeText(chooseMethod.this, "Certificate not generated", Toast.LENGTH_SHORT).show();
                }
            });
            queue.add(generateRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}