package com.example.admins.hotelhunter.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admins.hotelhunter.R;
import com.example.admins.hotelhunter.Utils.ImageUtils;
import com.example.admins.hotelhunter.adapter.CustomImageView;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddHotelActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etTenNhaNghi;
    EditText etDiaChi;
    EditText etSDT1;
    EditText etSDT2;
    EditText etGia;
    ImageView iv_wifi, iv_thangmay, iv_dieuhoa, iv_nonglanh, iv_tivi, iv_tulanh, iv_addphoto;
    TextView tv_wifi, tv_thangmay, tv_dieuhoa, tv_nonglanh, tv_tivi, tv_tulanh, tv_sdt1, tv_vitribando, tv_thongbaoten, tv_thongbaodiachi, tv_thongbaogia;
    LinearLayout ln_wifi, ln_thangmay, ln_dieuhoa, ln_nonglanh, ln_tivi, ln_tulanh, ln_image;
    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    public List<HotelModel> list = new ArrayList<>();
    public static String TAG = AddHotelActivity.class.toString();
    public ImageView img_showhotel;
    public List<String> lst_Image = new ArrayList<>();
    public boolean tiVi = false;
    public boolean tuLanh = false;
    public boolean dieuHoa = false;
    public boolean thangMay = false;
    public boolean wifi = false;
    public boolean nongLanh = false;
    public Button bt_dangBai;
    HorizontalScrollView horizontalScrollView;
    MyAsyncTask myAsyncTask;
    List<HotelModel> lstModels = new ArrayList<>();
    EditText kinhdo, vido,rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hotel);
        setupUI();
        addListtenners();
        setEnableService();
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
        bt_dangBai = findViewById(R.id.bt_danghotel);
        ln_dieuhoa = findViewById(R.id.ln_dieuhoa);
        ln_wifi = findViewById(R.id.ln_wifi);
        ln_nonglanh = findViewById(R.id.ln_nonglanh);
        ln_thangmay = findViewById(R.id.ln_thangmay);
        ln_tulanh = findViewById(R.id.ln_tulanh);
        ln_tivi = findViewById(R.id.ln_tivi);
        ln_image = findViewById(R.id.ln_image);
        tv_thongbaoten = findViewById(R.id.tv_thongbaoten);
        tv_thongbaodiachi = findViewById(R.id.tv_thongbaodiachi);
        tv_thongbaogia = findViewById(R.id.tv_thongbaogia);
        tv_thongbaodiachi.setVisibility(View.GONE);
        tv_thongbaogia.setVisibility(View.GONE);
        tv_thongbaoten.setVisibility(View.GONE);
        horizontalScrollView = findViewById(R.id.sc_view);
        kinhdo = findViewById(R.id.et_kinhdoadd);
        vido = findViewById(R.id.et_vidoadd);
        rate = findViewById(R.id.et_rateadd);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("hotels");

    }

    private void addListtenners() {
        iv_addphoto.setOnClickListener(this);
        iv_wifi.setOnClickListener(this);
        iv_thangmay.setOnClickListener(this);
        iv_dieuhoa.setOnClickListener(this);
        iv_nonglanh.setOnClickListener(this);
        iv_tivi.setOnClickListener(this);
        iv_tulanh.setOnClickListener(this);
        tv_vitribando.setOnClickListener(this);
        bt_dangBai.setOnClickListener(this);
        ln_tivi.setOnClickListener(this);
        ln_tulanh.setOnClickListener(this);
        ln_nonglanh.setOnClickListener(this);
        ln_wifi.setOnClickListener(this);
        ln_thangmay.setOnClickListener(this);
        ln_dieuhoa.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_addphoto: {
                Log.d(TAG, "onClick: " + "addphoto");
                AddPhoto();
                break;
            }
            case R.id.ln_wifi: {
                wifi = wifi ? false : true;
                setEnableService();
                break;
            }
            case R.id.ln_thangmay: {
                thangMay = thangMay ? false : true;
                setEnableService();
                break;
            }
            case R.id.ln_dieuhoa: {
                dieuHoa = dieuHoa ? false : true;
                setEnableService();
                break;
            }
            case R.id.ln_nonglanh: {
                nongLanh = nongLanh ? false : true;
                setEnableService();
                break;
            }
            case R.id.ln_tivi: {
                tiVi = tiVi ? false : true;
                setEnableService();
                break;
            }
            case R.id.ln_tulanh: {
                tuLanh = tuLanh ? false : true;
                setEnableService();
                break;
            }
            case R.id.bt_danghotel: {
                DangBai();
                break;
            }
            case R.id.et_giaadd: {
                tv_thongbaogia.setVisibility(View.INVISIBLE);
                break;
            }
        }
    }

    private void DangBai() {
        HotelModel hotelModel = new HotelModel();
        hotelModel.kinhDo = 105.783303;
        hotelModel.viDo = 20.979135;
        hotelModel.images.addAll(lst_Image);
        hotelModel.tulanh = tuLanh;
        hotelModel.tivi = tiVi;
        hotelModel.thangMay = thangMay;
        hotelModel.nongLanh = nongLanh;
        hotelModel.dieuHoa = dieuHoa;
        hotelModel.wifi = wifi;
        if(etDiaChi.getText().toString()==null||etTenNhaNghi.getText().toString()==null||etGia.getText().toString()==null)
        {
            Toast.makeText(this, "Các thành phần có dấu * không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }
        hotelModel.address= etDiaChi.getText().toString();
        hotelModel.nameHotel = etTenNhaNghi.getText().toString();
        hotelModel.phone = etSDT1.getText().toString();
        hotelModel.phone1 = etSDT2.getText().toString();
        hotelModel.gia = etGia.getText().toString();
        hotelModel.kinhDo = Double.parseDouble(kinhdo.getText().toString());
        hotelModel.viDo = Double.parseDouble(vido.getText().toString());
        hotelModel.danhGiaTB = Float.parseFloat(rate.getText().toString());
        databaseReference.push().setValue(hotelModel);
    }

    private void AddPhoto() {
        final String[] item = {"Chụp ảnh", "Mở Bộ sưu tập", "Huỷ"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thêm Ảnh");
        builder.setItems(item, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (item[i].equals("Chụp ảnh")) {
                    openCamera();
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
        startActivityForResult(Intent.createChooser(intent, "Chọn Ảnh"), 2);
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = ImageUtils.getUriFromImage(this);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 1);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Bitmap bitmap = null;
                Log.e(TAG, "onActivityResult: ");
                bitmap = ImageUtils.getBitmap(this);
                myAsyncTask = new MyAsyncTask();
                myAsyncTask.execute(bitmap);
                Log.d(TAG, "onActivityResult: " + bitmap);
            }

        } else if (requestCode == 2) {
            Bitmap bitmap = null;
            if (resultCode == RESULT_OK) {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                    myAsyncTask = new MyAsyncTask();
                    myAsyncTask.execute(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
    }

    public class MyAsyncTask extends AsyncTask<Bitmap, Void, Bitmap> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(Bitmap... bitmaps) {
            return bitmaps[0];
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            showPhoto(bitmap);
        }
    }

    public void showPhoto(Bitmap bitmap) {
        CustomImageView customImageView = new CustomImageView(this);
        customImageView.setLayoutParams(new ViewGroup.LayoutParams
                (100, 100));
        customImageView.setMaxHeight(100);
        customImageView.setMaxWidth(100);
        customImageView.setCropToPadding(true);
        customImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        customImageView.setImageBitmap(bitmap);


        ImageView abc = new ImageView(this);
        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(iv_addphoto.getWidth(), iv_addphoto.getHeight());
        //abc.setLayoutParams(new LinearLayout.LayoutParams(iv_addphoto.getWidth(),iv_addphoto.getHeight()));
        layout.setMarginStart(25);
        abc.setLayoutParams(layout);
        abc.setCropToPadding(true);
        abc.setScaleType(ImageView.ScaleType.CENTER_CROP);
        abc.setImageBitmap(bitmap);
        ln_image.addView(abc);
        String imamge = ImageUtils.encodeTobase64(bitmap);
        lst_Image.add(imamge);
        Log.e(TAG, "showPhoto: " + lst_Image.size());
        //ln_image.addView(customImageView);
        horizontalScrollView.fullScroll(View.FOCUS_RIGHT);
    }

    private void setEnableService() {
        if (tiVi) {
            iv_tivi.setAlpha(255);
            tv_tivi.setTextColor(Color.argb(255, 0, 0, 0));
        } else {
            iv_tivi.setAlpha(100);
            tv_tivi.setTextColor(Color.argb(100, 0, 0, 0));
        }
        if (tuLanh) {
            tv_tulanh.setTextColor(Color.argb(255, 0, 0, 0));
            iv_tulanh.setAlpha(255);
        } else {
            tv_tulanh.setTextColor(Color.argb(100, 0, 0, 0));
            iv_tulanh.setAlpha(100);
        }
        if (thangMay) {
            tv_thangmay.setTextColor(Color.argb(255, 0, 0, 0));
            iv_thangmay.setAlpha(255);
        } else {
            tv_thangmay.setTextColor(Color.argb(100, 0, 0, 0));
            iv_thangmay.setAlpha(100);
        }
        if (nongLanh) {
            iv_nonglanh.setAlpha(255);
            tv_nonglanh.setTextColor(Color.argb(255, 0, 0, 0));
        } else {
            iv_nonglanh.setAlpha(100);
            tv_nonglanh.setTextColor(Color.argb(100, 0, 0, 0));
        }
        if (dieuHoa) {
            tv_dieuhoa.setTextColor(Color.argb(255, 0, 0, 0));
            iv_dieuhoa.setAlpha(255);
        } else {
            tv_dieuhoa.setTextColor(Color.argb(100, 0, 0, 0));
            iv_dieuhoa.setAlpha(100);
        }
        if (wifi) {
            tv_wifi.setTextColor(Color.argb(255, 0, 0, 0));
            iv_wifi.setAlpha(255);
        } else {
            tv_wifi.setTextColor(Color.argb(100, 0, 0, 0));
            iv_wifi.setAlpha(100);
        }

    }
}