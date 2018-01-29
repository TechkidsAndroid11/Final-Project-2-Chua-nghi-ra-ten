package com.example.admins.hotelhunter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admins.hotelhunter.R;
import com.example.admins.hotelhunter.model.PhoneModel;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class CodeActivity extends AppCompatActivity {
    EditText etPhone;
    Button btVery;
    PhoneModel phoneModel;
    ImageView ivBack;
    private String code;

    public PhoneAuthProvider.ForceResendingToken resendingToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        etPhone = findViewById(R.id.et_phone_Number);
        btVery = findViewById(R.id.bt_phone_veri);
        ivBack = findViewById(R.id.iv_back);
        final String phone = etPhone.getText().toString();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        if (phone!="") {
            btVery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(etPhone.getText().toString(),
                            1, TimeUnit.SECONDS,
                            CodeActivity.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {


                                }

                                @Override
                                public void onVerificationFailed(FirebaseException e) {

                                }

                                @Override
                                public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    code=s;
                                    resendingToken=forceResendingToken;


                                }

                            });
                    Intent i = new Intent(CodeActivity.this,PhoneVerifyActivity.class);
                    i.putExtra("KEYPHONE", etPhone.getText().toString());
                    i.putExtra("KEY_CODE",code);
                    startActivity(i);




                }
            });
        } else {
            Toast.makeText(CodeActivity.this,"Chưa nhập số điện thoai", Toast.LENGTH_SHORT).show();
        }
    }
}
