package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.project.global.Config;
import com.example.project.model.DeviceInfo;
import com.example.project.model.PidData;

import org.json.JSONException;
import org.json.JSONObject;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class FingerScan extends AppCompatActivity {

    EditText finger_one;
    EditText finger_two;
    EditText finger_three;
    EditText finger_four;
    Button btnCapture;
    Button btnDeviceInfo;
    Button continue_step3;
    int pensionerId;
    String dataValue = "";
    boolean fromPpoNumberScreen;
    String finger_ppoOne, finger_ppoTwo, finger_ppoThree, finger_ppoFour;
    EditText txtPidOptions;
    EditText txtOutput;
    TextView status_text;
    TextView quality_score;

    private Serializer serializer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_scan);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ArrayList<String> positions = new ArrayList<>();
        serializer = new Persister();

        finger_one = findViewById(R.id.finger_one);
        finger_two = findViewById(R.id.finger_two);
        finger_three = findViewById(R.id.finger_three);
        finger_four = findViewById(R.id.finger_four);
        btnCapture = findViewById(R.id.btnCapture);
        btnDeviceInfo = findViewById(R.id.btnDeviceInfo);
        continue_step3 = findViewById(R.id.continue_step3);
        txtPidOptions = findViewById(R.id.txtPidOptions);
        txtOutput = findViewById(R.id.txtOutput);
        status_text = findViewById(R.id.status_text);
        quality_score = findViewById(R.id.quality_score);

        Dialog dialog = new Dialog(this);


        // Use getIntent() to get the Intent that started this activity
        Intent intent1 = getIntent();
        fromPpoNumberScreen = intent1.getBooleanExtra("fromPpoNumberScreen", false);

        if (fromPpoNumberScreen) {
            fromPPOScreen(intent1);
        } else {
            fromShowAllScreen(intent1);
        }

        btnDeviceInfo.setOnClickListener(v -> {
            try {
                txtPidOptions.setText("");
                Intent intent = new Intent();
                intent.setAction("in.gov.uidai.rdservice.fp.INFO");
                startActivityForResult(intent, 1);
            } catch (Exception e) {
                Log.e("Error", e.toString());
            }
        });

        btnCapture.setOnClickListener(v -> {
            try {

                String pidOption = "<PidOptions ver=\"1.0\">\n" +
                        "   <CustOpts>\n" +
                        "      <Param/>\n" +
                        "   </CustOpts>\n" +
                        "   <Opts env=\"P\" fCount=\"1\" fType=\"0\" format=\"0\" iCount=\"0\" iType=\"0\" pCount=\"0\" pTimeout=\"20000\" pType=\"0\" pgCount=\"2\" pidVer=\"2.0\" posh=\"RIGHT_THUMB\" timeout=\"10000\"/>\n" +
                        "</PidOptions>";

                Log.e("PidOptions", pidOption);
                txtPidOptions.post(() -> txtPidOptions.setText(pidOption));
                Intent intent2 = new Intent();
                intent2.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
                intent2.putExtra("PID_OPTIONS", pidOption);
//                        intent2.setPackage("com.mantra.rdservice");
                startActivityForResult(intent2, 2);
            } catch (Exception e) {
                Log.e("Error", e.toString());
            }
        });

        continue_step3.setOnClickListener(new View.OnClickListener() {
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
                                storeFingerprint();
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            Intent intent = new Intent(FingerScan.this, UploadScreen.class);
                            intent.putExtra("fromPpoNumberScreen", true);
                            intent.putExtra("One", finger_one.getText().toString());
                            intent.putExtra("Two", finger_two.getText().toString());
                            intent.putExtra("Three", finger_three.getText().toString());
                            intent.putExtra("Four", finger_four.getText().toString());
                            intent.putExtra("pensionerId", pensionerId);
                            startActivity(intent);
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
                    Intent intent1 = new Intent(FingerScan.this, UploadScreen.class);
                    intent1.putExtra("fromPpoNumberScreen", false);
                    intent1.putExtra("one", finger_one.getText().toString());
                    intent1.putExtra("two", finger_two.getText().toString());
                    intent1.putExtra("three", finger_three.getText().toString());
                    intent1.putExtra("four", finger_four.getText().toString());
                    intent1.putExtra("pensionerId", pensionerId);
                    startActivity(intent1);
                }
            }
        });

    }

    @SuppressLint("SetTextI18n")

    private void setText(final String message) {
        this.runOnUiThread(() -> {
            txtOutput.setText(message);
            Log.e("MSG", message);
        });
    }

    private void setText(final String message, final String filename) {
        this.runOnUiThread(() -> {
            txtOutput.setText(message);
            com.example.project.global.Config.DataLog(FingerScan.this, message, filename, ".txt");
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        if (data != null) {
                            String result = data.getStringExtra("DEVICE_INFO");
                            String rdService = data.getStringExtra("RD_SERVICE_INFO");
                            String display = "";
                            if (rdService != null) {
                                display = "RD Service Info :\n" + rdService + "\n\n";
                                XmlPullParser parser = Xml.newPullParser();
                                parser.setInput(new StringReader(rdService));
                                int eventType = parser.getEventType();
                                String status = "";
                                while (eventType != XmlPullParser.END_DOCUMENT) {
                                    if (eventType == XmlPullParser.START_TAG && parser.getName().equals("RDService")) {
                                        status = parser.getAttributeValue(null, "status");
                                        break;
                                    }
                                    eventType = parser.next();
                                }
                                // Display status as toast
                                if (!status.isEmpty()) {
                                    status_text.setVisibility(View.VISIBLE);
                                    status_text.setText("Device is " + status);
                                }
                            }
                            if (result != null) {
                                DeviceInfo info = serializer.read(DeviceInfo.class, result);
                                Log.e("Device Info", result);
                                display += "Device Info :\n" + result;
                                //setText(display);
                                setText(display, "DeviceInfo");
                                com.example.project.global.Config.DataLog(FingerScan.this, info.mc, "MC", ".cer");
                            }
                        }
                    } catch (Exception e) {
                        Log.e("Error", "Error while deserialze device info", e);
                    }
                }
                break;
            case 2:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        if (data != null) {
                            String result = data.getStringExtra("PID_DATA");
                            if (result != null) {
                                com.example.project.global.Config.DataLog(FingerScan.this, result, "PID_DATA", ".txt");
                                PidData pidData = serializer.read(PidData.class, result);
                                // setText(result);
                                setText(result, "PidData");
                                Config.DataLog(FingerScan.this, pidData._DeviceInfo.mc, "MC", ".cer");
                                XmlPullParser parser = Xml.newPullParser();
                                parser.setInput(new StringReader(result));
                                int eventType = parser.getEventType();
                                String errInfo = "";
                                String qScore = "";
                                while (eventType != XmlPullParser.END_DOCUMENT) {
                                    if (eventType == XmlPullParser.START_TAG) {
                                        String tagName = parser.getName();
                                        if (tagName.equals("PidData")) {
                                            errInfo = parser.getAttributeValue(null, "errInfo");
                                        } else if (tagName.equals("Data")) {
                                            dataValue = parser.nextText();
                                        } else if (tagName.equals("Resp")) {
                                            qScore = parser.getAttributeValue(null, "qScore");
                                        }
                                    }
                                    eventType = parser.next();
                                }
                                // Display errInfo as toast
                                if (!errInfo.isEmpty()) {
                                    Toast.makeText(this, "Error Info: " + errInfo, Toast.LENGTH_SHORT).show();
                                }

                                // Display quality
                                if (!qScore.isEmpty()) {
                                    quality_score.setVisibility(View.VISIBLE);
                                    quality_score.setText("Quality:-" + qScore + "%");
                                }
                            }
                        }
                    } catch (Exception e) {
                        Log.e("Error", "Error while  deserialze pid data", e);
                    }
                }
                break;
        }
    }

    private void fromPPOScreen(@NonNull Intent intent) {
        // Get the PPO numbers from the Intent
        finger_ppoOne = intent.getStringExtra("One");
        finger_ppoTwo = intent.getStringExtra("Two");
        finger_ppoThree = intent.getStringExtra("Three");
        finger_ppoFour = intent.getStringExtra("Four");
        pensionerId = intent.getIntExtra("pensionerId", 0);

        // Set the PPO numbers to the EditTexts
        finger_one.setText(finger_ppoOne);
        finger_two.setText(finger_ppoTwo);
        finger_three.setText(finger_ppoThree);
        finger_four.setText(finger_ppoFour);

        // Make EditTexts uneditable
        finger_one.setEnabled(false);
        finger_two.setEnabled(false);
        finger_three.setEnabled(false);
        finger_four.setEnabled(false);
    }

    private void fromShowAllScreen(@NonNull Intent intent) {
        // Get the PPO numbers from the Intent
        finger_ppoOne = intent.getStringExtra("one");
        finger_ppoTwo = intent.getStringExtra("two");
        finger_ppoThree = intent.getStringExtra("three");
        finger_ppoFour = intent.getStringExtra("four");
        pensionerId = intent.getIntExtra("pensionerId", 0);

        // Set the PPO numbers to the EditTexts
        finger_one.setText(finger_ppoOne);
        finger_two.setText(finger_ppoTwo);
        finger_three.setText(finger_ppoThree);
        finger_four.setText(finger_ppoFour);

        // Make EditTexts uneditable
        finger_one.setEnabled(false);
        finger_two.setEnabled(false);
        finger_three.setEnabled(false);
        finger_four.setEnabled(false);
    }

    private void storeFingerprint() throws JSONException {
        String url = "https://kycdemo.amshoft.in/api/pensioner/SubmitFingerPrintData";
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject fingerPrintData = new JSONObject();
        fingerPrintData.put("pensionerId", pensionerId);
        fingerPrintData.put("userId", 1);
        fingerPrintData.put("fingerPrintData", dataValue);

        JsonObjectRequest fingerDataRequest = new JsonObjectRequest(Request.Method.POST, url, fingerPrintData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String responseMessage = response.getString("responseMessage");
                    Toast.makeText(FingerScan.this, responseMessage, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("API_ERROR", "Failed to fetch data from the API: " + error.getMessage());
                Toast.makeText(FingerScan.this, "FingerPrint not Stored", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(fingerDataRequest);
    }
}