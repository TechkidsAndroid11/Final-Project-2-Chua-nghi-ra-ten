package com.example.admins.hotelhunter.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.admins.hotelhunter.R;
import com.example.admins.hotelhunter.model.HotelModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.database.DataSnapshot;

/**
 * Created by Nguyen Duc Anh on 1/15/2018.
 */

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private static final String TAG = "CustomInfoWindowAdapter";
    private Context context;
    public CustomInfoWindowAdapter(Context context){
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = LayoutInflater.from(context).inflate(R.layout.information_marker, null);
        TextView tvPrice = (TextView) view.findViewById(R.id.tv_price_hotel);
        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.rb_rating);
        DataSnapshot dataSnapshot = (DataSnapshot) marker.getTag();
        HotelModel hotelModel = dataSnapshot.getValue(HotelModel.class);
        ImageView ivWifi = view.findViewById(R.id.iv_wifi);
        Log.d(TAG, "getInfoContents: " + marker.getTag());
        ImageView ivThangMay = view.findViewById(R.id.iv_elevator);
        ImageView ivNongLanh = view.findViewById(R.id.iv_heater);
        ImageView ivDieuHoa = view.findViewById(R.id.iv_air_condition);
        tvPrice.setText(hotelModel.gia + "VNĐ");
        ratingBar.setRating(hotelModel.danhGiaTB);
        if (hotelModel.wifi){
            ivWifi.setVisibility(View.VISIBLE);
        }

        if (hotelModel.nongLanh){
            ivNongLanh.setVisibility(View.VISIBLE);
        }

        if (hotelModel.dieuHoa){
            ivDieuHoa.setVisibility(View.VISIBLE);
        }

        if (hotelModel.thangMay){
            ivThangMay.setVisibility(View.VISIBLE);
        }
        Log.d(TAG, "getInfoContents: " + hotelModel);
        return view;
    }
}
