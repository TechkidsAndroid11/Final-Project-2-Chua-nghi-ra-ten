package com.example.admins.hotelhunter.activities;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.admins.hotelhunter.R;
import com.example.admins.hotelhunter.adapter.ViewPagerAdapter;
import com.example.admins.hotelhunter.database.OnClickWindowinfo;
import com.example.admins.hotelhunter.model.HotelModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class InformationOfHotelActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener{
    private static final String TAG = "InformationOfHotelActivity";
    TabLayout tab;
    ViewPager vpFragment;
    ImageView ivHotel;
    HotelModel hotelModel;
    ViewPager viewPager;
    TextView tvName;
    SliderLayout sliderLayout;
    PagerIndicator pagerIndicator;

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_information_of_hotel);
        Log.d(TAG, "onCreate: ");
        EventBus.getDefault().register(this);
        setupUI();
        ShowImage();
    }

    private void setupUI() {
        tab = findViewById(R.id.tab);
        viewPager = findViewById(R.id.vp_fragment);
        tvName = findViewById(R.id.tv_name);
//        ivHotel = findViewById(R.id.iv_hotel);
        tab.addTab(tab.newTab().setText("Details"));
        tab.addTab(tab.newTab().setText("Comment"));
        sliderLayout = findViewById(R.id.slide_hotel);
        pagerIndicator= findViewById(R.id.custom_indicator);
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
        tvName.setText("Nhà nghỉ " + hotelModel.nameHotel);
    }

    @SuppressLint("LongLogTag")
    @Subscribe(sticky = true)
    public void onRecievedHotelModel(final OnClickWindowinfo onClickWindowinfo) {
        hotelModel = onClickWindowinfo.hotelModel;
        Log.d(TAG, "onRecievedHotelModel: " + hotelModel);
    }
    public void ShowImage()
    {

        for (int i=0; i<hotelModel.images.size(); i++)
        {
            String encodedImage = hotelModel.images.get(i);
            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            try {
                TextSliderView sliderView = new TextSliderView(this);
                File f = File.createTempFile("tmp","png", getCacheDir());
                FileOutputStream fos = new FileOutputStream(f);
                decodedByte.compress(Bitmap.CompressFormat.PNG,100,fos);
                fos.close();
                sliderView.image(f)
                        .setScaleType(BaseSliderView.ScaleType.CenterCrop);
                sliderLayout.addSlider(sliderView);
            }catch (IOException io) {
                io.printStackTrace();
            }
        }
        pagerIndicator.setVisibility(View.VISIBLE);
        sliderLayout.setCustomIndicator(pagerIndicator);

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
}

