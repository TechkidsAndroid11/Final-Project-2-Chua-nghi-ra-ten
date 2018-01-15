package com.example.admins.hotelhunter.database;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.admins.hotelhunter.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by Nguyen Duc Anh on 1/15/2018.
 */

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
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

        TextView tvTitle = (TextView) view.findViewById(R.id.tv_price_hotel);
        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.rb_rating);

        tvTitle.setText(marker.getTitle());
        ratingBar.setRating(marker.getAlpha());

        return view;
    }
}
