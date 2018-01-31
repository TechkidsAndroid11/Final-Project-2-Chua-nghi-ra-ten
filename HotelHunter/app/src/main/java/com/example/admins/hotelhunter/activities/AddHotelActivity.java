package com.example.admins.hotelhunter.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.admins.hotelhunter.R;
import com.example.admins.hotelhunter.database.DataHandle;
import com.example.admins.hotelhunter.model.HotelModel;
import com.example.admins.hotelhunter.model.ReviewModel;
import com.example.admins.hotelhunter.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddHotelActivity extends AppCompatActivity {
    EditText etTenNhaNghi;
    EditText etDiaChi;
    EditText etSDT;
    EditText etGia;
    CheckBox cbWifi, cbThangMay, cbNongLanh, cbDieuHoa;
    Button btAdd;
    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    EditText edt_kinhdo;
    EditText edit_vido;
    EditText edit_rate;
    Button bt_addImage;
    public List<HotelModel> list = new ArrayList<>();
    public static String TAG = AddHotelActivity.class.toString();
    public ImageView img_showhotel;
    public TextInputLayout textInputLayout;
    public List<String> lst_String = new ArrayList<>();
    public Button bt_clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hotel);
        setupUI();
        final String phone1 = getIntent().getStringExtra("KEYPHONE");
//        list = DataHandle.hotelModels(null, AddHotelActivity.this);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final HotelModel hotelModel = new HotelModel(phone1, etTenNhaNghi.getText().toString(), etDiaChi.getText().toString(), etSDT.getText().toString(),
                        Double.parseDouble(edt_kinhdo.getText().toString()), Double.parseDouble(edit_vido.getText().toString()),
                        Float.parseFloat(edit_rate.getText().toString()),
                        etGia.getText().toString(), lst_String, new ArrayList<ReviewModel>(),
                        cbWifi.isChecked(), cbDieuHoa.isChecked(), cbNongLanh.isChecked(),
                        cbThangMay.isChecked());

                databaseReference.child(hotelModel.phone).setValue(hotelModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "onComplete: push hotel");
                        databaseReference = firebaseDatabase.getReference("users");
                        databaseReference.child(firebaseAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                UserModel userModel = new UserModel();
                                userModel.Huid = hotelModel.phone;
                                databaseReference.child(firebaseAuth.getCurrentUser().getUid()).setValue(userModel);
                                Log.d(TAG, "onDataChange: push hotel");
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                });
            }
        });
        img_showhotel.setVisibility(View.GONE);
        img_showhotel.setVisibility(View.INVISIBLE);
        bt_addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final HotelModel hotelModel = list.get(1);
                hotelModel.images.addAll(lst_String);
                //                String encodedImage = list.get(list.size()-1).images.get(0);
//                byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
//                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//                if(decodedByte!=null)
//                {
//                    img_showhotel.setVisibility(View.GONE);
//                    img_showhotel.setVisibility(View.VISIBLE);
//                    img_showhotel.setImageBitmap(decodedByte);
//                }
//                Log.d(TAG, "onClick: "+decodedByte.getHeight());



            }
        });
        bt_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                list = DataHandle.hotelModels(null, AddHotelActivity.this);

            }
        });
    }

    private void setupUI() {

        etTenNhaNghi = findViewById(R.id.et_ten_nha_nghi);
        etDiaChi = findViewById(R.id.et_dia_chi);
        etSDT = findViewById(R.id.et_sdt);
        etGia = findViewById(R.id.et_gia);
        cbDieuHoa = findViewById(R.id.cb_dieu_hoa);
        cbThangMay = findViewById(R.id.cb_thang_may);
        cbNongLanh = findViewById(R.id.cb_nong_lanh);
        btAdd = findViewById(R.id.bt_add);
        bt_addImage = findViewById(R.id.btn_addimage);
        edt_kinhdo = findViewById(R.id.edit_kinhdo);
        edit_vido = findViewById(R.id.edit_vido);
        edit_rate = findViewById(R.id.editrate);
        img_showhotel = findViewById(R.id.img_showhotel);
        textInputLayout = findViewById(R.id.txt_image);
        bt_clear = findViewById(R.id.bt_clear);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("hotels");


    }
}