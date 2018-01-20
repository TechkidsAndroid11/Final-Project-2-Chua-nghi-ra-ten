package com.example.admins.hotelhunter.activities;

import android.annotation.SuppressLint;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.admins.hotelhunter.R;
import com.example.admins.hotelhunter.adapter.ViewPagerAdapter;
import com.example.admins.hotelhunter.database.OnClickWindowinfo;
import com.example.admins.hotelhunter.model.HotelModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class InformationOfHotelActivity extends AppCompatActivity {
    private static final String TAG = "InformationOfHotelActivity";
    TabLayout tab;
    ViewPager vpFragment;
    ImageView ivHotel;
    ViewPager viewPager;

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_of_hotel);
        Log.d(TAG, "onCreate: ");
        EventBus.getDefault().register(this);
        setupUI();
    }

    private void setupUI() {
        tab = findViewById(R.id.tab);
        viewPager = findViewById(R.id.vp_fragment);
//        ivHotel = findViewById(R.id.iv_hotel);
        tab.addTab(tab.newTab().setText("Details"));
        tab.addTab(tab.newTab().setText("Comment"));
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
//                tab.getIcon().setAlpha(100);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
    }

    @SuppressLint("LongLogTag")
    @Subscribe(sticky = true)
    public void onRecievedHotelModel(final OnClickWindowinfo onClickWindowinfo) {
        final HotelModel hotelModel = onClickWindowinfo.hotelModel;
        Log.d(TAG, "onRecievedHotelModel: " + hotelModel);
    }
}

