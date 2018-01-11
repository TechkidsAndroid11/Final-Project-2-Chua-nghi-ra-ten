package com.example.admins.hotelhunter.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.admins.hotelhunter.R;
import com.example.admins.hotelhunter.model.HotelModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddHotelActivity extends AppCompatActivity {
    EditText etTenNhaNghi;
    EditText etDiaChi;
    EditText etSDT;
    EditText etGia;
    CheckBox cbWifi, cbThangMay, cbNongLanh, cbDieuHoa;
    Button btAdd;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hotel);
        setupUI();
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HotelModel hotelModel  = new HotelModel(etTenNhaNghi.getText().toString(), etDiaChi.getText().toString(), etSDT.getText().toString(),
                        105.816179, 21.022494, 0, etGia.getText().toString(), null, null,
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
        cbWifi = findViewById(R.id.cb_wifi);
        cbDieuHoa = findViewById(R.id.cb_dieu_hoa);
        cbThangMay = findViewById(R.id.cb_thang_may);
        cbNongLanh = findViewById(R.id.cb_nong_lanh);
        btAdd = findViewById(R.id.bt_add);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("hotels");
    }
}
