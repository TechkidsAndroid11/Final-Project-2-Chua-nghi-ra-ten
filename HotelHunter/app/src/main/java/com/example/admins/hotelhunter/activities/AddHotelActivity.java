package com.example.admins.hotelhunter.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.admins.hotelhunter.R;

import com.example.admins.hotelhunter.model.HotelModel;
import com.example.admins.hotelhunter.model.ReviewModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddHotelActivity extends AppCompatActivity {
    EditText etTenNhaNghi;
    EditText etDiaChi;
    EditText etSDT;
    EditText etGia;
    CheckBox cbWifi, cbThangMay, cbNongLanh, cbDieuHoa;
    Button btAdd;
   public static FirebaseDatabase firebaseDatabase;
   public  static DatabaseReference databaseReference;
    EditText edt_kinhdo;
    EditText edit_vido;
    EditText edit_rate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hotel);
        setupUI();
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HotelModel hotelModel  = new HotelModel(etTenNhaNghi.getText().toString(), etDiaChi.getText().toString(), etSDT.getText().toString(),
                        Double.parseDouble(edt_kinhdo.getText().toString()), Double.parseDouble(edit_vido.getText().toString()), Integer.parseInt(edit_rate.getText().toString()),
                        etGia.getText().toString(), new ArrayList<String>(), new ArrayList<ReviewModel>(),
                        cbWifi.isChecked(), cbDieuHoa.isChecked(), cbNongLanh.isChecked(),
                        cbThangMay.isChecked());
                databaseReference.push().setValue(hotelModel);
            }
        });
    }
    private void setupUI() {

        etTenNhaNghi = findViewById(R.id.et_ten_nha_nghi);
        etDiaChi = findViewById(R.id.et_dia_chi);
        etSDT = findViewById(R.id.et_sdt);
        etGia = findViewById(R.id.et_gia)   ;
        cbWifi = findViewById(R.id.cb_1);
        cbDieuHoa = findViewById(R.id.cb_dieu_hoa);
        cbThangMay = findViewById(R.id.cb_thang_may);
        cbNongLanh = findViewById(R.id.cb_nong_lanh);
        btAdd = findViewById(R.id.bt_add);
        edt_kinhdo = findViewById(R.id.edit_kinhdo);
        edit_vido = findViewById(R.id.edit_vido);
        edit_rate = findViewById(R.id.editrate);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("hotels");
    }
}
