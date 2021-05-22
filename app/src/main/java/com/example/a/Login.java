package com.example.a;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.base.Verify;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Login extends AppCompatActivity {

    EditText mEmail,mPassword;
    Button mLoginBtn,mphone;
    ProgressBar progressBar;
    TextView mCreatebtn;
    FirebaseAuth fAuth;
    int code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail=findViewById(R.id.Email);
        fAuth = FirebaseAuth.getInstance();
        mPassword=findViewById(R.id.password);
        progressBar=findViewById(R.id.progressBar2);
        mLoginBtn=findViewById(R.id.Login);
        mCreatebtn=findViewById(R.id.gotocreate);
        mphone=findViewById(R.id.withphone);



    mLoginBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String email = mEmail.getText().toString().trim();
            String password = mPassword.getText().toString().trim();


            if (TextUtils.isEmpty(email)) {
                mEmail.setError("Email is required.");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                mPassword.setError("Password is Required.");
                return;
            }
            if (password.length() < 6) {
                mPassword.setError("Password must be greater than 6 character");
                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        if(fAuth.getCurrentUser().isEmailVerified()){
                            Random random = new Random();
                            code=random.nextInt(8999)+1000;
                            String url = "http://192.168.43.235/emailotp/otp.php";
                            RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                            StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(Login.this, response, Toast.LENGTH_SHORT).show();

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(Login.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();

                                }
                            }){
                                @Nullable
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String,String> params = new HashMap<>();
                                    params.put("email",mEmail.getText().toString());
                                    params.put("code",String.valueOf(code));
                                    return params;
                                }
                            };
                            requestQueue.add(stringRequest);

                            Intent intent = new Intent(getApplicationContext(), verifyotp.class);
                            intent.putExtra("mobile",String.valueOf(code));
//                            intent.putExtra("userid",mEmail.getText().toString());
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(Login.this, "Please Verify Your Email id", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    } else{
                        Toast.makeText(Login.this, "Error !"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });


        }
    });

        mCreatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Register.class);
                startActivity(intent);
            }
        });

        mphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                startActivity(new Intent(getApplicationContext(),sendotp.class));


            }
        });

    }
}