package com.example.project;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;

import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;


public class FaceRecognition extends AppCompatActivity {

    EditText face_one;
    EditText face_two;
    EditText face_three;
    EditText face_four;
    Button capture;
    ActivityResultLauncher<Intent> cameraLauncher;
    Bitmap imageBitmap;
    ImageView captured_image;
    Button continue_step2;
    int pensionerId;
    boolean fromPpoNumberScreen;
    String face_ppoOne, face_ppoTwo, face_ppoThree, face_ppoFour;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_recognition);

        face_one = findViewById(R.id.face_one);
        face_two = findViewById(R.id.face_two);
        face_three = findViewById(R.id.face_three);
        face_four = findViewById(R.id.face_four);
        capture = findViewById(R.id.capture_image);
        captured_image = findViewById(R.id.captured_image);
        continue_step2 = findViewById(R.id.continue_step2);

        Dialog dialog = new Dialog(this);
        // Use getIntent() to get the Intent that started this activity
        Intent intent = getIntent();
        fromPpoNumberScreen = intent.getBooleanExtra("fromPpoNumberScreen", false);

        if (fromPpoNumberScreen) {
            fromPPOScreen(intent);
        } else {
            fromShowAllScreen(intent);
        }

        continue_step2.setOnClickListener(new View.OnClickListener() {
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
                            Intent intent = new Intent(FaceRecognition.this, FingerScan.class);
                            intent.putExtra("fromPpoNumberScreen", true);
                            intent.putExtra("One", face_one.getText().toString());
                            intent.putExtra("Two", face_two.getText().toString());
                            intent.putExtra("Three", face_three.getText().toString());
                            intent.putExtra("Four", face_four.getText().toString());
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
                    Intent intent1 = new Intent(FaceRecognition.this, FingerScan.class);
                    intent1.putExtra("fromPpoNumberScreen", false);
                    intent1.putExtra("one", face_one.getText().toString());
                    intent1.putExtra("two", face_two.getText().toString());
                    intent1.putExtra("three", face_three.getText().toString());
                    intent1.putExtra("four", face_four.getText().toString());
                    intent1.putExtra("pensionerId", pensionerId);
                    startActivity(intent1);
                    dialog.dismiss();
                }
            }
        });

        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent();
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraLauncher.launch(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        imageBitmap = (Bitmap) extras.get("data");
                        captured_image.setImageBitmap(imageBitmap);
                    }
                }
            }
        });
    }

    private void fromPPOScreen(@NonNull Intent intent) {
        // Get the PPO numbers from the Intent
        face_ppoOne = intent.getStringExtra("One");
        face_ppoTwo = intent.getStringExtra("Two");
        face_ppoThree = intent.getStringExtra("Three");
        face_ppoFour = intent.getStringExtra("Four");
        pensionerId = intent.getIntExtra("pensionerId", 0);

        // Set the PPO numbers to the EditTexts
        face_one.setText(face_ppoOne);
        face_two.setText(face_ppoTwo);
        face_three.setText(face_ppoThree);
        face_four.setText(face_ppoFour);

        // Make EditTexts uneditable
        face_one.setEnabled(false);
        face_two.setEnabled(false);
        face_three.setEnabled(false);
        face_four.setEnabled(false);
    }

    private void fromShowAllScreen(@NonNull Intent intent) {
        // Get the PPO numbers from the Intent
        face_ppoOne = intent.getStringExtra("one");
        face_ppoTwo = intent.getStringExtra("two");
        face_ppoThree = intent.getStringExtra("three");
        face_ppoFour = intent.getStringExtra("four");
        pensionerId = intent.getIntExtra("pensionerId", 0);

        // Set the PPO numbers to the EditTexts
        face_one.setText(face_ppoOne);
        face_two.setText(face_ppoTwo);
        face_three.setText(face_ppoThree);
        face_four.setText(face_ppoFour);

        // Make EditTexts uneditable
        face_one.setEnabled(false);
        face_two.setEnabled(false);
        face_three.setEnabled(false);
        face_four.setEnabled(false);
    }
}