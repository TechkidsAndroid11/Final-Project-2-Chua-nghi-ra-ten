package com.example.admins.hotelhunter.database;

import android.util.Log;

import com.example.admins.hotelhunter.activities.AddHotelActivity;
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
import java.util.Collection;
import java.util.List;

/**
 * Created by vanph on 13/01/2018.
 */

public class DataHandle {
    private static final String TAG = "DataHandle";

    public static void hotelModels(final GoogleMap mMap) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("hotels");
        final List<HotelModel> list = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: " + dataSnapshot);
                for (DataSnapshot hotel : dataSnapshot.getChildren()) {
                    HotelModel hotelModel = hotel.getValue(HotelModel.class);
                    Log.d(TAG, "onDataChange: ");
                    list.add(hotelModel);
                    LatLng sydney = new LatLng(hotelModel.kinhDo,hotelModel.viDo );
                    mMap.addMarker(new MarkerOptions().position(sydney).title(hotelModel.nameHotel).snippet("đờ mờ"));

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
