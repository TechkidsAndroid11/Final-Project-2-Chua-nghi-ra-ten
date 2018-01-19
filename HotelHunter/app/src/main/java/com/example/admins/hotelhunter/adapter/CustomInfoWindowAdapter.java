package com.example.admins.hotelhunter.adapter;

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
        TextView tvPrice = (TextView) view.findViewById(R.id.tv_price_hotel);
        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.rb_rating);
        tvPrice.setText(marker.getSnippet().substring(marker.getSnippet().indexOf("/")+1));
        ratingBar.setRating(Float.parseFloat(marker.getSnippet().substring(0, marker.getSnippet().indexOf("/"))));
        return view;
    }
}
