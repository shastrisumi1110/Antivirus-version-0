package com.example.a;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class verifyotp extends AppCompatActivity {
    EditText inputnumber1,inputnumber2,inputnumber3,inputnumber4,inputnumber5,inputnumber6;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String otp;
//    String userID;
//
//    String getotpbackend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifyotp);

        final Button Verifybtn = findViewById(R.id.submitotp);

        inputnumber1 =findViewById(R.id.inputotp1);
        inputnumber2 =findViewById(R.id.inputotp2);
        inputnumber3 =findViewById(R.id.inputotp3);
        inputnumber4 =findViewById(R.id.inputotp4);

        inputnumber1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0){
                    inputnumber2.requestFocus();
                }
            }
        });

        inputnumber2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0){
                    inputnumber3.requestFocus();
                }
            }
        });

        inputnumber3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0){
                    inputnumber4.requestFocus();
                }
            }
        });
//        inputnumber5 =findViewById(R.id.inputotp5);
//        inputnumber6 =findViewById(R.id.inputotp6);

        fAuth = FirebaseAuth.getInstance();
        fStore =FirebaseFirestore.getInstance();

        otp=getIntent().getStringExtra("mobile");


//        TextView textView =findViewById(R.id.textmobileshownumber);
//        textView.setText(String.format("+91-%s",getIntent().getStringExtra("mobile")));
//
//        getotpbackend=getIntent().getStringExtra("backendotp");

        final ProgressBar progressBar =findViewById(R.id.progressbar_verify);

        Verifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputcode;
                inputcode=inputnumber1.getText().toString()+inputnumber2.getText().toString()+
                        inputnumber3.getText().toString()+inputnumber4.getText().toString();

                if(inputcode.equals(String.valueOf(otp))){
                    Toast.makeText(verifyotp.this, "Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(verifyotp.this, "Failed", Toast.LENGTH_SHORT).show();
                }
//        findViewById(R.id.textsendotp).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + getIntent().getStringExtra("mobile"), 60, TimeUnit.SECONDS, verifyotp.this,
//                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                            @Override
//                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//
//
//                            }
//
//                            @Override
//                            public void onVerificationFailed(@NonNull FirebaseException e) {
//
//                                Toast.makeText(verifyotp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//
//                            }
//
//                            @Override
//                            public void onCodeSent(@NonNull String newotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                                super.onCodeSent(newotp, forceResendingToken);
//                                getotpbackend = newotp;
//                                Toast.makeText(verifyotp.this, "OTP sended Successfully", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//            }
//        });


    }

});
    }
}