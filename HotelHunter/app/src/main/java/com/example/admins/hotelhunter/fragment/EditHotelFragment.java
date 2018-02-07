package com.example.admins.hotelhunter.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admins.hotelhunter.R;
import com.example.admins.hotelhunter.Utils.ImageUtils;
import com.example.admins.hotelhunter.activities.AddHotelActivity;
import com.example.admins.hotelhunter.adapter.CustomImageView;
import com.example.admins.hotelhunter.database.OnClickWindowinfo;
import com.example.admins.hotelhunter.database.onClickMyHotel;
import com.example.admins.hotelhunter.model.HotelModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditHotelFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = EditHotelFragment.class.toString();
    EditText etTenNhaNghi;
    EditText etDiaChi;
    TextView tvSDT1;
    EditText etSDT2;
    EditText etGia;
    ImageView iv_wifi, iv_thangmay, iv_dieuhoa, iv_nonglanh, iv_tivi, iv_tulanh, iv_addphoto;
    TextView tv_wifi, tv_thangmay, tv_dieuhoa, tv_nonglanh, tv_tivi, tv_tulanh, tv_sdt1, tv_vitribando, tv_thongbaoten, tv_thongbaodiachi, tv_thongbaogia;
    LinearLayout ln_wifi, ln_thangmay, ln_dieuhoa, ln_nonglanh, ln_tivi, ln_tulanh, ln_image;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    public Button bt_dangBai;
    HorizontalScrollView horizontalScrollView;
    AddHotelActivity.MyAsyncTask myAsyncTask;
    public List<String> lst_Image = new ArrayList<>();
    List<HotelModel> lstModels = new ArrayList<>();
    EditText kinhdo, vido, rate;
    public boolean tiVi = false;
    public boolean tuLanh = false;
    public boolean dieuHoa = false;
    public boolean thangMay = false;
    public boolean wifi = false;
    public boolean nongLanh = false;
    String phone1;
    HotelModel hotelModel;


    public EditHotelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_hotel, container, false);
        EventBus.getDefault().register(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        setupUI(view);
        addListeners();
        return view;


    }

    private void addListeners() {

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

    @Subscribe(sticky = true)
    public void getHotelModel(final onClickMyHotel onMyHotel) {


    }


    private void setupUI(View view) {
        etTenNhaNghi = view.findViewById(R.id.et_tenadd);
        etDiaChi = view.findViewById(R.id.et_diachiadd);
        tvSDT1 = view.findViewById(R.id.tv_sdt1add);
        etSDT2 = view.findViewById(R.id.et_sdt2add);
        etGia = view.findViewById(R.id.et_giaadd);
        iv_wifi = view.findViewById(R.id.iv_wifiadd);
        iv_thangmay = view.findViewById(R.id.iv_thangmayadd);
        iv_dieuhoa = view.findViewById(R.id.iv_dieuhoaadd);
        iv_nonglanh = view.findViewById(R.id.iv_nonglanhadd);
        iv_tivi = view.findViewById(R.id.iv_tiviadd);
        iv_tulanh = view.findViewById(R.id.iv_tulanhadd);
        iv_addphoto = view.findViewById(R.id.iv_addphoto);
        tv_wifi = view.findViewById(R.id.tv_wifiadd);
        tv_thangmay = view.findViewById(R.id.tv_thangmayadd);
        tv_dieuhoa = view.findViewById(R.id.tv_dieuhoaadd);
        tv_nonglanh = view.findViewById(R.id.tv_nonglanhadd);
        tv_tivi = view.findViewById(R.id.tv_tiviadd);
        tv_tulanh = view.findViewById(R.id.tv_tulanhadd);
        tv_sdt1 = view.findViewById(R.id.tv_sdt1add);
        tv_vitribando = view.findViewById(R.id.tv_vitribando);
        bt_dangBai = view.findViewById(R.id.bt_danghotel);
        ln_dieuhoa = view.findViewById(R.id.ln_dieuhoa);
        ln_wifi = view.findViewById(R.id.ln_wifi);
        ln_nonglanh = view.findViewById(R.id.ln_nonglanh);
        ln_thangmay = view.findViewById(R.id.ln_thangmay);
        ln_tulanh = view.findViewById(R.id.ln_tulanh);
        ln_tivi = view.findViewById(R.id.ln_tivi);
        ln_image = view.findViewById(R.id.ln_image);
        tv_thongbaoten = view.findViewById(R.id.tv_thongbaoten);
        tv_thongbaodiachi = view.findViewById(R.id.tv_thongbaodiachi);
        tv_thongbaogia = view.findViewById(R.id.tv_thongbaogia);
        tv_thongbaodiachi.setVisibility(View.GONE);
        tv_thongbaogia.setVisibility(View.GONE);
        tv_thongbaoten.setVisibility(View.GONE);
        horizontalScrollView = view.findViewById(R.id.sc_view);
        kinhdo = view.findViewById(R.id.et_kinhdoadd);
        vido = view.findViewById(R.id.et_vidoadd);
        rate = view.findViewById(R.id.et_rateadd);

        etTenNhaNghi.setText(hotelModel.nameHotel);
        etDiaChi.setText(hotelModel.address);

        etSDT2.setText(hotelModel.phone);
        etGia.setText(hotelModel.gia);
        AlphaAnimation alpha = new AlphaAnimation(0.1F, 0.1F);
        alpha.setDuration(0);
        alpha.setFillAfter(true);
        if (!hotelModel.dieuHoa) {
            ln_dieuhoa.startAnimation(alpha);
        }

        if (!hotelModel.wifi) {
            ln_wifi.startAnimation(alpha);
        }

        if (!hotelModel.nongLanh) {
            ln_nonglanh.startAnimation(alpha);
        }

        if (!hotelModel.thangMay) {
            ln_thangmay.startAnimation(alpha);
        }

        if (!hotelModel.tulanh) {
            ln_tulanh.startAnimation(alpha);
        }

        if (!hotelModel.tivi) {
            ln_tivi.startAnimation(alpha);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_addphoto: {
                Log.d(TAG, "onClick: " + "addphoto");
                ;
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
    }

    private void setEnableService() {
        if (tiVi) {
            iv_tivi.setAlpha(255);
            tv_tivi.setTextColor(Color.argb(255, 252, 119, 3));
        } else {
            iv_tivi.setAlpha(100);
            tv_tivi.setTextColor(Color.argb(100, 252, 119, 3));
        }
        if (tuLanh) {
            tv_tulanh.setTextColor(Color.argb(255, 252, 119, 3));
            iv_tulanh.setAlpha(255);
        } else {
            tv_tulanh.setTextColor(Color.argb(100, 252, 119, 3));
            iv_tulanh.setAlpha(100);
        }
        if (thangMay) {
            tv_thangmay.setTextColor(Color.argb(255, 252, 119, 3));
            iv_thangmay.setAlpha(255);
        } else {
            tv_thangmay.setTextColor(Color.argb(100, 252, 119, 3));
            iv_thangmay.setAlpha(100);
        }
        if (nongLanh) {
            iv_nonglanh.setAlpha(255);
            tv_nonglanh.setTextColor(Color.argb(255, 252, 119, 3));
        } else {
            iv_nonglanh.setAlpha(100);
            tv_nonglanh.setTextColor(Color.argb(100, 252, 119, 3));
        }
        if (dieuHoa) {
            tv_dieuhoa.setTextColor(Color.argb(255, 252, 119, 3));
            iv_dieuhoa.setAlpha(255);
        } else {
            tv_dieuhoa.setTextColor(Color.argb(100, 252, 119, 3));
            iv_dieuhoa.setAlpha(100);
        }
        if (wifi) {
            tv_wifi.setTextColor(Color.argb(255, 252, 119, 3));
            iv_wifi.setAlpha(255);
        } else {
            tv_wifi.setTextColor(Color.argb(100, 252, 119, 3));
            iv_wifi.setAlpha(100);
        }

    }


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1) {
//            if (resultCode == RESULT_OK) {
//                Bitmap bitmap = null;
//                Log.e(TAG, "onActivityResult: ");
//                bitmap = ImageUtils.getBitmap(getActivity());
//                myAsyncTask = new DetailFragment.AsyncTax();
//                myAsyncTask.execute(bitmap);
//                Log.d(TAG, "onActivityResult: " + bitmap);
//            }
//
//        } else if (requestCode == 2) {
//            Bitmap bitmap = null;
//            if (resultCode == RESULT_OK) {
//                try {
//                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
//                    myAsyncTask = new DetailFragment().AsyncTax();
//                    myAsyncTask.execute(bitmap);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//        }
//    }






}

