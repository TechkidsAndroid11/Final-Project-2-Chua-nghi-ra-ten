package com.example.admins.hotelhunter.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.admins.hotelhunter.R;
import com.example.admins.hotelhunter.database.OnClickWindowinfo;
import com.example.admins.hotelhunter.model.HotelModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {
    CheckBox cbWifi, cbDieuHoa, cbNongLanh, cbThangMay;
    TextView tvDienThoai, tvDiaChi;
    HotelModel hotelModel;
    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        EventBus.getDefault().register(this);
        setupUI(view);
        loadData();
        return view;
    }

    private void loadData() {
        tvDiaChi.setText(hotelModel.address);
        tvDienThoai.setText(hotelModel.phone);
        cbWifi.setChecked(hotelModel.wifi);
        cbDieuHoa.setChecked(hotelModel.dieuHoa);
        cbNongLanh.setChecked(hotelModel.nongLanh);
        cbThangMay.setChecked(hotelModel.thangMay);
    }

    private void setupUI(View view) {
        cbWifi = view.findViewById(R.id.cb_wifi);
        cbDieuHoa = view.findViewById(R.id.cb_dieu_hoa);
        cbNongLanh = view.findViewById(R.id.cb_nong_lanh);
        cbThangMay = view.findViewById(R.id.cb_thang_may);
        tvDienThoai = view.findViewById(R.id.tv_so_dien_thoai);
        tvDiaChi = view.findViewById(R.id.tv_dia_chi_2);

    }
    @Subscribe(sticky = true)
    public void getHotelModel(final OnClickWindowinfo onClickWindowinfo){
        hotelModel = onClickWindowinfo.hotelModel;
    }

}
