package com.example.admins.hotelhunter.activities;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.admins.hotelhunter.R;
import com.example.admins.hotelhunter.database.OnClickWindowinfo;
import com.example.admins.hotelhunter.model.HotelModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class InformationOfHotelActivity extends AppCompatActivity {
    private static final String TAG = "InformationOfHotelActivity";
    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_of_hotel);
        Log.d(TAG, "onCreate: ");
        EventBus.getDefault().register(this);
    }

    @SuppressLint("LongLogTag")
    @Subscribe(sticky = true)
    public void onRecievedHotelModel(final OnClickWindowinfo onClickWindowinfo){
        final HotelModel hotelModel = onClickWindowinfo.hotelModel;
        Log.d(TAG, "onRecievedHotelModel: " + hotelModel);
    }
}

