package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class PpoNumber extends AppCompatActivity {

    EditText one;
    EditText two;
    EditText three;
    EditText four;
    Button check;
    Button show_all;
    private StringBuilder ppo = new StringBuilder();
    String url;
    RequestQueue queue;
    JsonObjectRequest request;
    ProgressBar check_loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ppo_number);

        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        check = findViewById(R.id.check);
        show_all = findViewById(R.id.show_all);
        check_loading = findViewById(R.id.check_loading);

        setEditTextListeners();

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (one.getText().toString().isEmpty() ||
                        two.getText().toString().isEmpty() ||
                        three.getText().toString().isEmpty() ||
                        four.getText().toString().isEmpty()) {

                    Toast.makeText(PpoNumber.this, "Enter a valid PPO number", Toast.LENGTH_SHORT).show();
                } else {
                    ppo.setLength(0); // Clear the StringBuilder
                    ppo.append(one.getText().toString()).append(two.getText().toString()).append(three.getText().toString()).append(four.getText().toString());

                    url = "https://kycdemo.amshoft.in/api/pensioner/CheckPensionCode";

                    check_loading.setVisibility(View.VISIBLE);
                    check.setVisibility(View.GONE);

                    JSONObject jsonParams = new JSONObject();
                    try {
                        jsonParams.put("pensionCode", ppo.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    queue = Volley.newRequestQueue(PpoNumber.this);
                    request = new JsonObjectRequest(Request.Method.POST, url, jsonParams, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                check_loading.setVisibility(View.GONE);
                                check.setVisibility(View.VISIBLE);
                                String responseMessage = response.getString("responseMessage");
                                if (responseMessage.equals("Pension code does not exists")) {
                                    Intent intent = new Intent(PpoNumber.this, CheckScreen.class);
                                    intent.putExtra("fromPpoNumberScreen", true);
                                    intent.putExtra("One", one.getText().toString());
                                    intent.putExtra("Two", two.getText().toString());
                                    intent.putExtra("Three", three.getText().toString());
                                    intent.putExtra("Four", four.getText().toString());
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(PpoNumber.this, responseMessage, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            check_loading.setVisibility(View.GONE);
                            check.setVisibility(View.VISIBLE);
                            Log.e("API_ERROR", "Failed to fetch data from the API: " + error.getMessage());
                            Toast.makeText(PpoNumber.this, "Server Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                    queue.add(request);
                }
            }
        });

        show_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PpoNumber.this, ShowAll_Screen.class);
                startActivity(intent);
            }
        });
    }

    private void setEditTextListeners() {
        one.addTextChangedListener(new MyTextWatcher(one, two));
        two.addTextChangedListener(new MyTextWatcher(two, three));
        three.addTextChangedListener(new MyTextWatcher(three, four));
        four.addTextChangedListener(new MyTextWatcher(four, null));

        four.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (four.getText().length() == 0) {
                        // Backspace pressed in the last EditText, move cursor to the previous EditText
                        three.requestFocus();
                        three.setSelection(three.length());
                        return true;
                    }
                }
                return false;
            }
        });
        three.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (three.getText().length() == 0) {
                        // Backspace pressed in the last EditText, move cursor to the previous EditText
                        two.requestFocus();
                        two.setSelection(two.length());
                        return true;
                    }
                }
                return false;
            }
        });
        two.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (two.getText().length() == 0) {
                        // Backspace pressed in the last EditText, move cursor to the previous EditText
                        one.requestFocus();
                        one.setSelection(one.length());
                        return true;
                    }
                }
                return false;
            }
        });
        four.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return actionId == EditorInfo.IME_ACTION_DONE;
            }
        });

    }

    private class MyTextWatcher implements TextWatcher {

        private final EditText currentEditText;
        private final EditText nextEditText;

        public MyTextWatcher(EditText currentEditText, EditText nextEditText) {
            this.currentEditText = currentEditText;
            this.nextEditText = nextEditText;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Not needed for this example
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Not needed for this example
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.length() > 0 && nextEditText != null) {
                // Move cursor to the next EditText
                nextEditText.requestFocus();
                nextEditText.setSelection(0);
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Go back to LoginActivity
        super.onBackPressed();
        Intent intent = new Intent(PpoNumber.this, LoginPage.class);

        // Set flags to clear the entire task stack and create a new task
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
        finish(); // Optional: Finish the current activity to remove it from the stack
    }
}