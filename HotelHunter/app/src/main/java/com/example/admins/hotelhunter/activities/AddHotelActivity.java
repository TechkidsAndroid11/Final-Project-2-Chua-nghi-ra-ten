package com.example.admins.hotelhunter.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admins.hotelhunter.R;
import com.example.admins.hotelhunter.Utils.ImageUtils;
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

public class AddHotelActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etTenNhaNghi;
    EditText etDiaChi;
    EditText etSDT1;
    EditText etSDT2;
    EditText etGia;
    ImageView iv_wifi, iv_thangmay,iv_dieuhoa, iv_nonglanh,iv_tivi, iv_tulanh, iv_addphoto;
    TextView tv_wifi, tv_thangmay, tv_dieuhoa, tv_nonglanh, tv_tivi, tv_tulanh, tv_sdt1, tv_vitribando;
    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    public List<HotelModel> list = new ArrayList<>();
    public static String TAG = AddHotelActivity.class.toString();
    public ImageView img_showhotel;
    public List<String> lst_String = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hotel);
        setupUI();
        addListtenners();
        final String phone1 = getIntent().getStringExtra("KEYPHONE");

    }

    private void setupUI() {
        etTenNhaNghi = findViewById(R.id.et_tenadd);
        etDiaChi = findViewById(R.id.et_diachiadd);
        etSDT1 = findViewById(R.id.et_sdt1add);
        etSDT2 = findViewById(R.id.et_sdt2add);
        etGia = findViewById(R.id.et_giaadd);
        iv_wifi = findViewById(R.id.iv_wifiadd);
        iv_thangmay = findViewById(R.id.iv_thangmayadd);
        iv_dieuhoa = findViewById(R.id.iv_dieuhoaadd);
        iv_nonglanh = findViewById(R.id.iv_nonglanhadd);
        iv_tivi = findViewById(R.id.iv_tiviadd);
        iv_tulanh = findViewById(R.id.iv_tulanhadd);
        iv_addphoto = findViewById(R.id.iv_addphoto);
        tv_wifi = findViewById(R.id.tv_wifiadd);
        tv_thangmay = findViewById(R.id.tv_thangmayadd);
        tv_dieuhoa = findViewById(R.id.tv_dieuhoaadd);
        tv_nonglanh = findViewById(R.id.tv_nonglanhadd);
        tv_tivi = findViewById(R.id.tv_tiviadd);
        tv_tulanh = findViewById(R.id.tv_tulanhadd);
        tv_sdt1 = findViewById(R.id.tv_sdt1add);
        tv_vitribando = findViewById(R.id.tv_vitribando);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("hotels");

    }
    private void addListtenners()
    {
        iv_addphoto.setOnClickListener(this);
        iv_wifi.setOnClickListener(this);
        iv_thangmay.setOnClickListener(this);
        iv_dieuhoa.setOnClickListener(this);
        iv_nonglanh.setOnClickListener(this);
        iv_tivi.setOnClickListener(this);
        iv_tulanh.setOnClickListener(this);
        tv_vitribando.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.iv_addphoto:
            {
                Log.d(TAG, "onClick: "+"addphoto");
                AddPhoto();
                break;
            }
            case R.id.iv_wifiadd:{

                break;
            }
            case R.id.iv_thangmayadd:
            {
                break;
            }
            case R.id.iv_dieuhoaadd:{
                break;
            }
            case R.id.iv_nonglanhadd:
            {
                break;
            }
            case R.id.iv_tiviadd:{
                break;
            }
            case R.id.iv_tulanhadd:{
                break;
            }
        }
    }
    private void AddPhoto()
    {
        final String[] item = {"Chụp ảnh", "Mở Bộ sưu tập", "Huỷ"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thêm Ảnh");
        builder.setItems(item, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (item[i].equals("Chụp ảnh")) {
                    cameraIntent();
                } else if (item[i].equals("Mở Bộ sưu tập")) {
                    galleryIntent();
                } else {
                    dialogInterface.dismiss();
                }
            }
        }).show();
    }
    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*"); // mở tất cả các folder lưa trữ ảnh
        intent.setAction(Intent.ACTION_GET_CONTENT); // đi đến folder mình chọn
        startActivityForResult(Intent.createChooser(intent, "Chọn Ảnh"), 1);
    }

    private void cameraIntent() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        Uri uri = ImageUtils.getUriFromImage(this);
//        if(uri!=null){
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//        }
       // check xem co ton tai intent nao khong

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 2);
        }
    }
}