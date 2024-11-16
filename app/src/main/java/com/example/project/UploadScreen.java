package com.example.project;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UploadScreen extends AppCompatActivity {

    EditText upload_one;
    EditText upload_two;
    EditText upload_three;
    EditText upload_four;
    Button continue_step4;
    Button upload_profile;
    Button upload_aadhar_front;
    Button upload_aadhar_back;
    ActivityResultLauncher<Intent> ProfileCameraLauncher;
    ActivityResultLauncher<Intent> AadharFrontCameraLauncher;
    ActivityResultLauncher<Intent> AadharBackCameraLauncher;
    Bitmap profileImageBitmap;
    Bitmap aadharFrontImageBitmap;
    Bitmap aadharBackImageBitmap;
    String profileImageString;
    String aadharFrontImageString;
    String aadharBackImageString;
    String profileCurrentDateTime;
    String aadharFrontCurrentDateTime;
    String aadharBackCurrentDateTime;
    ImageView profile_photo;
    ImageView aadhar_front;
    ImageView aadhar_back;
    int pensionerId;
    String profile_url;
    String aadhar_front_url;
    String aadhar_back_url;
    RequestQueue queue;
    JsonObjectRequest profile_request;
    JsonObjectRequest aadhar_front_request;
    JsonObjectRequest aadhar_back_request;
    boolean fromPpoNumberScreen;
    String upload_ppoOne, upload_ppoTwo, upload_ppoThree, upload_ppoFour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_screen);
        upload_one = findViewById(R.id.upload_one);
        upload_two = findViewById(R.id.upload_two);
        upload_three = findViewById(R.id.upload_three);
        upload_four = findViewById(R.id.upload_four);
        profile_photo = findViewById(R.id.upload_profile);
        aadhar_front = findViewById(R.id.aadhar_front);
        aadhar_back = findViewById(R.id.aadhar_back);
        upload_profile = findViewById(R.id.upload_profile_button);
        upload_aadhar_front = findViewById(R.id.upload_aadhar_front_button);
        upload_aadhar_back = findViewById(R.id.upload_aadhar_back_button);
        continue_step4 = findViewById(R.id.continue_step4);
        Dialog dialog = new Dialog(this);
        queue = Volley.newRequestQueue(this);

        upload_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent();
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    ProfileCameraLauncher.launch(intent);
                    profileCurrentDateTime = getCurrentDateTime();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        upload_aadhar_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent();
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    AadharFrontCameraLauncher.launch(intent);
                    aadharFrontCurrentDateTime = getCurrentDateTime();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        upload_aadhar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent();
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    AadharBackCameraLauncher.launch(intent);
                    aadharBackCurrentDateTime = getCurrentDateTime();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ProfileCameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        profileImageBitmap = (Bitmap) extras.get("data");
                        profile_photo.setImageBitmap(profileImageBitmap);
                        profileImageString = bitmapToBase64(profileImageBitmap);
                        Toast.makeText(UploadScreen.this, "Profile Photo Submitted!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        AadharFrontCameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        aadharFrontImageBitmap = (Bitmap) extras.get("data");
                        aadhar_front.setImageBitmap(aadharFrontImageBitmap);
                        aadharFrontImageString = bitmapToBase64(aadharFrontImageBitmap);
                        Toast.makeText(UploadScreen.this, "Aadhar Front Photo Submitted!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        AadharBackCameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        aadharBackImageBitmap = (Bitmap) extras.get("data");
                        aadhar_back.setImageBitmap(aadharBackImageBitmap);
                        aadharBackImageString = bitmapToBase64(aadharBackImageBitmap);
                        Toast.makeText(UploadScreen.this, "Aadhar Back Photo Submitted!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        Intent intent = getIntent();
        fromPpoNumberScreen = intent.getBooleanExtra("fromPpoNumberScreen", false);

        if (fromPpoNumberScreen) {
            fromPPOScreen(intent);
        } else {
            fromShowAllScreen(intent);
        }

        continue_step4.setOnClickListener(new View.OnClickListener() {
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
                                postProfile();
                                postAadharFront();
                                postAadharBack();
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            Intent intent = new Intent(UploadScreen.this, PpoNumber.class);
                            intent.putExtra("One", upload_one.getText().toString());
                            intent.putExtra("Two", upload_two.getText().toString());
                            intent.putExtra("Three", upload_three.getText().toString());
                            intent.putExtra("Four", upload_four.getText().toString());
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            Toast.makeText(UploadScreen.this, "Registered Successfully!!!", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            finish();
                            dialog.dismiss();
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
                    Intent intent1 = new Intent(UploadScreen.this, PpoNumber.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent1);
                }
            }
        });

    }

    private void fromPPOScreen(@NonNull Intent intent) {
        // Get the PPO numbers from the Intent
        upload_ppoOne = intent.getStringExtra("One");
        upload_ppoTwo = intent.getStringExtra("Two");
        upload_ppoThree = intent.getStringExtra("Three");
        upload_ppoFour = intent.getStringExtra("Four");
        pensionerId = intent.getIntExtra("pensionerId", 0);

        // Set the PPO numbers to the EditTexts
        upload_one.setText(upload_ppoOne);
        upload_two.setText(upload_ppoTwo);
        upload_three.setText(upload_ppoThree);
        upload_four.setText(upload_ppoFour);

        // Make EditTexts uneditable
        upload_one.setEnabled(false);
        upload_two.setEnabled(false);
        upload_three.setEnabled(false);
        upload_four.setEnabled(false);
    }

    @SuppressLint("ResourceAsColor")
    private void fromShowAllScreen(@NonNull Intent intent) {
        continue_step4.setText(R.string.done);
        // Get the PPO numbers from the Intent
        upload_ppoOne = intent.getStringExtra("one");
        upload_ppoTwo = intent.getStringExtra("two");
        upload_ppoThree = intent.getStringExtra("three");
        upload_ppoFour = intent.getStringExtra("four");
        pensionerId = intent.getIntExtra("pensionerId", 0);

        // Set the PPO numbers to the EditTexts
        upload_one.setText(upload_ppoOne);
        upload_two.setText(upload_ppoTwo);
        upload_three.setText(upload_ppoThree);
        upload_four.setText(upload_ppoFour);

        // Make EditTexts uneditable
        upload_one.setEnabled(false);
        upload_two.setEnabled(false);
        upload_three.setEnabled(false);
        upload_four.setEnabled(false);

        upload_profile.setBackgroundColor(R.color.border);
        upload_aadhar_back.setBackgroundColor(R.color.border);
        upload_aadhar_front.setBackgroundColor(R.color.border);

        upload_profile.setText(R.string.uploaded);
        upload_aadhar_back.setText(R.string.uploaded);
        upload_aadhar_front.setText(R.string.uploaded);

        upload_profile.setEnabled(false);
        upload_aadhar_back.setEnabled(false);
        upload_aadhar_front.setEnabled(false);
    }

    public static String bitmapToBase64(@NonNull Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    @NonNull
    private String getCurrentDateTime() {
        // Create a SimpleDateFormat object with the desired date format
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());

        // Get the current date and time
        Date currentDate = new Date(System.currentTimeMillis());

        // Format the current date and time using SimpleDateFormat
        return sdf.format(currentDate);
    }

    private void postProfile() throws JSONException {
        profile_url = "https://kycdemo.amshoft.in/api/pensioner/SubmitProfilePhotoData";

        JSONObject profile_data = new JSONObject();
        profile_data.put("pensioner_Id", pensionerId);
        profile_data.put("profile_Pic_Data", profileImageString);
        profile_data.put("profile_Pic_Insertion_Date", profileCurrentDateTime);
        profile_data.put("aadhar_Card_Front_Data", "");
        profile_data.put("aadhar_Card_Front_Insertion_Date", null);
        profile_data.put("aadhar_Card_Back_Data", "");
        profile_data.put("aadhar_Card_Back_Insertion_Date", null);

        profile_request = new JsonObjectRequest(Request.Method.POST, profile_url, profile_data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                upload_profile.setEnabled(false);
                //Toast.makeText(UploadScreen.this, "Profile Photo Submitted!", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("API_ERROR", "Failed to fetch data from the API: " + error.getMessage());
                Toast.makeText(UploadScreen.this, "Profile Photo not submitted", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(profile_request);
    }

    private void postAadharFront() throws JSONException {
        aadhar_front_url = "https://kycdemo.amshoft.in/api/pensioner/SubmitAadharFrontData";

        JSONObject aadhar_front_data = new JSONObject();
        aadhar_front_data.put("pensioner_Id", pensionerId);
        aadhar_front_data.put("profile_Pic_Data", "");
        aadhar_front_data.put("profile_Pic_Insertion_Date", null);
        aadhar_front_data.put("aadhar_Card_Front_Data", aadharFrontImageString);
        aadhar_front_data.put("aadhar_Card_Front_Insertion_Date", aadharFrontCurrentDateTime);
        aadhar_front_data.put("aadhar_Card_Back_Data", "");
        aadhar_front_data.put("aadhar_Card_Back_Insertion_Date", null);

        aadhar_front_request = new JsonObjectRequest(Request.Method.POST, aadhar_front_url, aadhar_front_data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(UploadScreen.this, "Aadhar Front Photo Submitted!", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("API_ERROR", "Failed to fetch data from the API: " + error.getMessage());
                Toast.makeText(UploadScreen.this, "Aadhar Front not submitted", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(aadhar_front_request);
    }

    private void postAadharBack() throws JSONException {
        aadhar_back_url = "https://kycdemo.amshoft.in/api/pensioner/SubmitAadharBackData";

        JSONObject aadhar_back_data = new JSONObject();
        aadhar_back_data.put("pensioner_Id", pensionerId);
        aadhar_back_data.put("profile_Pic_Data", "");
        aadhar_back_data.put("profile_Pic_Insertion_Date", null);
        aadhar_back_data.put("aadhar_Card_Front_Data", aadharFrontImageString);
        aadhar_back_data.put("aadhar_Card_Front_Insertion_Date", aadharFrontCurrentDateTime);
        aadhar_back_data.put("aadhar_Card_Back_Data", "");
        aadhar_back_data.put("aadhar_Card_Back_Insertion_Date", null);

        aadhar_back_request = new JsonObjectRequest(Request.Method.POST, aadhar_back_url, aadhar_back_data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(UploadScreen.this, "Documents Submitted!", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("API_ERROR", "Failed to fetch data from the API: " + error.getMessage());
                Toast.makeText(UploadScreen.this, "Aadhar Back not submitted", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(aadhar_back_request);
    }
}