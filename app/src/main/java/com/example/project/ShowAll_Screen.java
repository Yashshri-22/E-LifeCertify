package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

import java.util.ArrayList;
import java.util.List;

public class ShowAll_Screen extends AppCompatActivity implements ShowAll_Interface {

    EditText search;
    Button back;
    RecyclerView recyclerView;
    ShowAll_Adapter showAll_adapter;
    private List<ShowAllModel> originalData;
    String url;
    RequestQueue queue;
    JsonObjectRequest request;
    String firstName, middleName, lastName, fullName, pensioncode, mobileNumber, designation, dateOfBirth, dateOfJoining, dateOfRetirement, typeOfRetirement, nomineeName, height, birthMark, bankName, bankAccountNumber, address, adharNumber, gender, nomineeRelation;
    ProgressBar loader;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_screen);

        recyclerView = findViewById(R.id.show_all_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loader = findViewById(R.id.show_all_loader);

        showAll_adapter = new ShowAll_Adapter(this);
        recyclerView.setAdapter(showAll_adapter);
        List<ShowAllModel> showAllModels = new ArrayList<>();
        originalData = new ArrayList<>();
        //back = findViewById(R.id.back);

        search = findViewById(R.id.search);

        loader.setVisibility(View.VISIBLE);
        getPensionerData();

        search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Check if the touch event is within the drawableEnd region
                if (event.getAction() == MotionEvent.ACTION_UP &&
                        event.getRawX() >= (search.getRight() - search.getCompoundDrawables()[2].getBounds().width())) {
                    // Handle the click on the drawableEnd
                    handleDrawableEndClick();
                    return true;
                }
                return false;
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                handleSearch(editable.toString());
            }
        });

    }

    private void handleSearch(String query) {
        List<ShowAllModel> filteredData = filterData(query);
        showAll_adapter.setShowAllModels(filteredData);
    }

    private void handleDrawableEndClick() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(search.getWindowToken(), 0);
        search.requestFocus();
    }

    private List<ShowAllModel> filterData(String query) {
        List<ShowAllModel> filteredList = new ArrayList<>();
        for (ShowAllModel model : originalData) {
            // Assuming getPpo_number returns a String
            if (model.getPpo_number().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(model);
            }
        }
        return filteredList;
    }

    private void getPensionerData() {
        url = "https://kycdemo.amshoft.in/api/pensioner/GetPensionerPersonalDetails";
        queue = Volley.newRequestQueue(ShowAll_Screen.this);
        request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray dataArray = response.getJSONArray("data");
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject dataObject = dataArray.getJSONObject(i);
                        pensioncode = String.valueOf(dataObject.getInt("pensionCode"));
                        firstName = dataObject.getString("firstName");
                        middleName = dataObject.getString("middleName");
                        lastName = dataObject.getString("lastName");
                        fullName = String.format("%s %s %s", firstName, middleName, lastName).trim();
                        mobileNumber = dataObject.getString("mobileNumber");
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

                        ShowAllModel model = new ShowAllModel(R.drawable.profile, fullName, pensioncode, mobileNumber, designation, dateOfBirth, dateOfJoining, dateOfRetirement, typeOfRetirement, nomineeName, height, birthMark, bankName, bankAccountNumber, address, adharNumber, gender, nomineeRelation);
                        originalData.add(0, model);
                    }
                    showAll_adapter.setShowAllModels(originalData);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                loader.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ShowAll_Screen.this, "No data found", Toast.LENGTH_SHORT).show();
                loader.setVisibility(View.GONE);
            }
        });
        queue.add(request);

    }

    public void onItemClicked(ShowAllModel showAllModel) {
        Intent intent = new Intent(ShowAll_Screen.this, CheckScreen.class);
        intent.putExtra("fromPpoNumberScreen", false);
        String pensionCode = showAllModel.getPpo_number();
        intent.putExtra("Pension Code", pensionCode);

        // Find the corresponding data for the clicked PPO number
        for (ShowAllModel model : originalData) {
            if (model.getPpo_number().equals(pensionCode)) {
                // Retrieve data for the clicked PPO number from the 'model' object
                intent.putExtra("Full Name", model.getName());
                intent.putExtra("Mobile Number", model.getMobileNumber());
                intent.putExtra("Designation", model.getDesignation());
                intent.putExtra("Date of Birth", model.getDateOfBirth());
                intent.putExtra("Date of Joining", model.getDateOfJoining());
                intent.putExtra("Date of Retirement", model.getDateOfRetirement());
                intent.putExtra("Type of Retirement", model.getTypeOfRetirement());
                intent.putExtra("Address", model.getAddress());
                intent.putExtra("Height", model.getHeight());
                intent.putExtra("Birth Mark", model.getBirthMark());
                intent.putExtra("Bank Name", model.getBankName());
                intent.putExtra("Bank Account Number", model.getBankAccountNumber());
                intent.putExtra("Nominee Name", model.getNomineeName());
                intent.putExtra("Aadhar Number", model.getAdharNumber());
                intent.putExtra("Gender", model.getGender());
                intent.putExtra("Nominee Type", model.getNomineeType());
                break; // Exit loop once data for the clicked PPO number is found
            }
        }

        startActivity(intent);
    }

}



