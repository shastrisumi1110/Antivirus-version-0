package com.example.a;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class sendotp extends AppCompatActivity {

    EditText enternumber;
    Button getotpbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendotp);

        enternumber =findViewById(R.id.inpmobileno);
        getotpbtn = findViewById(R.id.btngetotp);

        ProgressBar progressBar =findViewById(R.id.progressbar_send);

        getotpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!enternumber.getText().toString().trim().isEmpty()){
                    if((enternumber.getText().toString().trim()).length() ==10){

                        progressBar.setVisibility(View.VISIBLE);
                        getotpbtn.setVisibility(View.INVISIBLE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + enternumber.getText().toString(), 60, TimeUnit.SECONDS, sendotp.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        progressBar.setVisibility(View.GONE);
                                        getotpbtn.setVisibility(View.VISIBLE);

                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        progressBar.setVisibility(View.GONE);
                                        getotpbtn.setVisibility(View.VISIBLE);
                                        Toast.makeText(sendotp.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        progressBar.setVisibility(View.GONE);
                                        getotpbtn.setVisibility(View.VISIBLE);
                                        Intent intent = new Intent(getApplicationContext(),verifyotp.class);
                                        intent.putExtra("mobile",enternumber.getText().toString());
                                        intent.putExtra("backendotp",backendotp);
                                        startActivity(intent);
                                    }
                                });
                         Intent intent = new Intent(getApplicationContext(),verifyotp.class);
                         intent.putExtra("mobile",enternumber.getText().toString());
                         startActivity(intent);
                    }else{
                        Toast.makeText(sendotp.this, "Please enter correct number", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(sendotp.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}