package com.example.admins.hotelhunter.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admins.hotelhunter.R;
import com.example.admins.hotelhunter.adapter.HotelAdapter;
import com.example.admins.hotelhunter.model.HotelModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyHotelFragment extends Fragment {
    private static final String TAG = MyHotelFragment.class.toString();
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    List<HotelModel> hotelModelList = new ArrayList<>();
    HotelAdapter hotelAdapter;
    RecyclerView rvHotel;


    public MyHotelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_hotel, container, false);
        rvHotel = view.findViewById(R.id.rv_myHotel);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");
        databaseReference.child(firebaseAuth.getCurrentUser().getUid()).child("Huid").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        String huid = d.getValue().toString();

                        databaseReference = firebaseDatabase.getReference("hotels");
                        databaseReference.orderByChild("key").equalTo(huid).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Log.d(TAG, "onDataChange: ");
                                if (dataSnapshot.getChildrenCount() > 0) {
                                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                                        HotelModel hotelModel = d.getValue(HotelModel.class);

                                        hotelModelList.add(hotelModel);
                                        rvHotel.setAdapter(new HotelAdapter(getContext(), hotelModelList));
                                        Log.d(TAG, "onDataChange: "+hotelModelList);

                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;
    }

}
