package com.example.admins.hotelhunter.database;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.example.admins.hotelhunter.R;
import com.example.admins.hotelhunter.activities.MainActivity;
import com.example.admins.hotelhunter.activities.TurnOnGPSActivity;
import com.example.admins.hotelhunter.adapter.CustomInfoWindowAdapter;
import com.example.admins.hotelhunter.map_direction.DirectionHandler;
import com.example.admins.hotelhunter.map_direction.DirectionResponse;
import com.example.admins.hotelhunter.map_direction.RetrofitInstance;
import com.example.admins.hotelhunter.map_direction.RetrofitService;
import com.example.admins.hotelhunter.map_direction.RouteModel;
import com.example.admins.hotelhunter.model.HotelModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vanph on 13/01/2018.
 */

public class DataHandle {
    public static final List<Polyline> polylines = new ArrayList<>();
    private static final String TAG = "DataHandle";
    public static List<HotelModel> hotelModels(final GoogleMap mMap, final Context context) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("hotels");
        final List<HotelModel> list = new ArrayList<>();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: " + dataSnapshot);
                for (DataSnapshot hotel : dataSnapshot.getChildren()) {
                    HotelModel hotelModel = hotel.getValue(HotelModel.class);
                    Log.d(TAG, "onDataChange: " + hotelModel.kinhDo);
                    hotelModel.key = hotel.getKey();
                    list.add(hotelModel);
                    CustomInfoWindowAdapter adapter = new CustomInfoWindowAdapter(context);
                    mMap.setInfoWindowAdapter(adapter);
                    LatLng sydney = new LatLng(hotelModel.viDo,hotelModel.kinhDo);
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(sydney).title(hotelModel.nameHotel).snippet(String.valueOf(hotelModel.danhGiaTB)+"/"+hotelModel.gia);
                    Marker marker = mMap.addMarker(markerOptions);
                    marker.setTag(hotel);
                    marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.icon_hotel));
                }

                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        final PolylineOptions polylineOptions = new PolylineOptions().color(Color.RED).width(16);
                        for (int i = 0; i < polylines.size(); i++){
                            polylines.get(i).remove();
                        }
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 20);
                        mMap.animateCamera(cameraUpdate);
                        RetrofitService retrofitService = RetrofitInstance.getInstance().create(RetrofitService.class);
                        Log.d(TAG, "onMarkerClick: " + TurnOnGPSActivity.currentLocation);
                        retrofitService.getDirection(String.valueOf(TurnOnGPSActivity.currentLocation.latitude)
                                +","+String.valueOf(TurnOnGPSActivity.currentLocation.longitude),
                                String.valueOf(marker.getPosition().latitude)
                                        +","+String.valueOf(marker.getPosition().longitude),
                                        "AIzaSyCPHUVwzFXx1bfLxZx9b8QYlZD_HMJza_0").enqueue(new Callback<DirectionResponse>() {
                            @Override
                            public void onResponse(Call<DirectionResponse> call, Response<DirectionResponse> response) {
                                RouteModel routeModel = DirectionHandler.getListRoute(response.body()).get(0);
                                Log.d(TAG, "onResponse: " + routeModel.duration);
                                Log.d(TAG, "onResponse: " + routeModel.distance);
//                                PolylineOptions polylineOptions = new PolylineOptions().color(Color.RED).width(16);
                                for (int i = 0; i < routeModel.points.size(); i++){
                                    polylineOptions.add(routeModel.points.get(i));
                                }
                                Polyline polyline = mMap.addPolyline(polylineOptions);
                                polylines.add(polyline);
                            }

                            @Override
                            public void onFailure(Call<DirectionResponse> call, Throwable t) {
                                Log.d(TAG, "onFailure: ");
                            }
                        });

                        return false;
                    }
                });


//                    LatLng sydney = new LatLng(hotelModel.viDo,hotelModel.kinhDo);
//                    MarkerOptions markerOptions = new MarkerOptions();
//                    markerOptions.position(sydney).title(hotelModel.gia).snippet(String.valueOf(hotelModel.danhGiaTB));
//                    CustomInfoWindowAdapter adapter = new CustomInfoWindowAdapter(context);
//                    mMap.setInfoWindowAdapter(adapter);
//                    mMap.addMarker(markerOptions).showInfoWindow();
                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return list;
    }
}
