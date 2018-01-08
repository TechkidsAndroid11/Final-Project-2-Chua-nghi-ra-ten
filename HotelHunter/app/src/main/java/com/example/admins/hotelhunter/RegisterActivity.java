package com.example.admins.hotelhunter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    EditText etEmail;
    EditText etPassword;
    EditText etConfirmPass;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setupUI();
    }

    private void setupUI() {
        etEmail=findViewById(R.id.et_emailDK);
        etPassword=findViewById(R.id.et_passDK);
        etConfirmPass=findViewById(R.id.et_confirmPassDK);
    }
}
