package com.example.admins.hotelhunter.database;

import android.content.Context;
import android.util.Log;

import com.example.admins.hotelhunter.adapter.CustomInfoWindowAdapter;
import com.example.admins.hotelhunter.model.HotelModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vanph on 13/01/2018.
 */

public class DataHandle {
    private static final String TAG = "DataHandle";
    public static List<HotelModel> hotelModels(final GoogleMap mMap, final Context context) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("hotels");
        final List<HotelModel> list = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: " + dataSnapshot);
                for (DataSnapshot hotel : dataSnapshot.getChildren()) {
                    HotelModel hotelModel = hotel.getValue(HotelModel.class);
                    Log.d(TAG, "onDataChange: " + hotelModel.kinhDo);
                    list.add(hotelModel);
                    LatLng sydney = new LatLng(hotelModel.viDo,hotelModel.kinhDo);
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(sydney).title(hotelModel.gia).snippet(String.valueOf(hotelModel.danhGiaTB));
                    CustomInfoWindowAdapter adapter = new CustomInfoWindowAdapter(context);
                    mMap.setInfoWindowAdapter(adapter);
                    mMap.addMarker(markerOptions).showInfoWindow();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return list;
    }
}
