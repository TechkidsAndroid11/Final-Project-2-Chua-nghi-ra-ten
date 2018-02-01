package com.example.admins.hotelhunter.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.admins.hotelhunter.R;
import com.example.admins.hotelhunter.Utils.ImageUtils;
import com.example.admins.hotelhunter.database.DataHandle;
import com.example.admins.hotelhunter.database.OnClickWindowinfo;
import com.example.admins.hotelhunter.fragment.DetailFragment;
import com.example.admins.hotelhunter.fragment.MyHotelFragment;
import com.example.admins.hotelhunter.model.HotelModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.auth.FirebaseAuth;

import org.greenrobot.eventbus.EventBus;

import java.util.List;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, View.OnClickListener {
    private static final String TAG = MainActivity.class.toString();
    FirebaseAuth firebaseAuth;
    private GoogleMap mMap;
    TextView tvName, tvNavText;
    ImageView ivAvata;
    RelativeLayout relativeLayout;
    public LatLng currentLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tvFilter=findViewById(R.id.tv_filter);
        tvFilter.setOnClickListener(this);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View view = navigationView.getHeaderView(0);
        tvNavText = view.findViewById(R.id.tv_nav_login);
        tvName = view.findViewById(R.id.tv_name);
        firebaseAuth = FirebaseAuth.getInstance();
        ivAvata = view.findViewById(R.id.iv_avatar);
//        Log.d(TAG, "onCreate: "+firebaseAuth.getCurrentUser().getDisplayName());
        if (firebaseAuth.getCurrentUser() == null) {

        } else {
            Log.d(TAG, "onResume: " + firebaseAuth.getCurrentUser() + firebaseAuth.getCurrentUser().getDisplayName());
            tvNavText.setVisibility(View.GONE);
            tvName.setVisibility(View.VISIBLE);
            ivAvata.setVisibility(View.VISIBLE);
            tvName.setText(firebaseAuth.getCurrentUser().getDisplayName());
//            Picasso.with(this).load(R.drawable.ic_close_black_24dp).into(ivAvata);
            ivAvata.setImageResource(R.drawable.ic_close_black_24dp);
        }
        tvNavText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_favourite) {

        } else if (id == R.id.nav_Logout) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater layoutInflater = this.getLayoutInflater();
            View dialogView = layoutInflater.inflate(R.layout.sign_out, null);
            dialogBuilder.setView(dialogView);
            final AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.show();
            Button btYes = dialogView.findViewById(R.id.btn_yes);
            btYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    firebaseAuth.signOut();
                    tvNavText.setVisibility(View.VISIBLE);
                    ivAvata.setImageResource(R.mipmap.ic_launcher_round);
                    tvName.setVisibility(View.GONE);
                    alertDialog.dismiss();

                }
            });
            Button btNo = dialogView.findViewById(R.id.btn_no);
            btNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });


        } else if (id == R.id.nav_myhotel) {
            if (firebaseAuth.getCurrentUser() == null) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                LayoutInflater layoutInflater = this.getLayoutInflater();
                View dialogView = layoutInflater.inflate(R.layout.require, null);
                dialogBuilder.setView(dialogView);
                final AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();
                Intent i2 = new Intent(this, LoginActivity.class);
                startActivity(i2);

            } else {
                Intent i3 = new Intent(this, CodeActivity.class);
                startActivity(i3);
            }

        } else if (id == R.id.nav_myPost)
            ImageUtils.openFragment(getSupportFragmentManager(), R.id.rl_main, new  MyHotelFragment());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: ");
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(TurnOnGPSActivity.currentLocation, 18);
        mMap.animateCamera(cameraUpdate);
        DataHandle.hotelModels(mMap, this);
        final List<HotelModel> list = DataHandle.hotelModels(mMap, this);
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                for (int i = 0; i < list.size(); i++) {
                    if (marker.getPosition().latitude == list.get(i).viDo && marker.getPosition().longitude == list.get(i).kinhDo) {
                        EventBus.getDefault().postSticky(new OnClickWindowinfo(list.get(i)));
                        Log.d(TAG, "onInfoWindowClick: ");
                    }
                }
                Log.d(TAG, "onInfoWindowClick: ");
                Intent intent = new Intent(MainActivity.this, InformationOfHotelActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
        for (int i = 0; i < DataHandle.polylines.size(); i++) {
            DataHandle.polylines.get(i).remove();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_filter:
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                LayoutInflater layoutInflater = this.getLayoutInflater();
                View dialogView = layoutInflater.inflate(R.layout.filter, null);
                dialogBuilder.setView(dialogView);
                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();
                RadioGroup rgPrice = dialogView.findViewById(R.id.gr_price);
                switch (rgPrice.getCheckedRadioButtonId()) {
                    case R.id.rb_11: {
                        break;
                    }
                    case R.id.rb_12: {
                        break;
                    }
                    case R.id.rb_13: {
                        break;
                    }



                }
                RadioGroup rgDistance= dialogView.findViewById(R.id.gr_distance);
                switch (rgDistance.getCheckedRadioButtonId()){
                    case R.id.rb_1:{

                    }
                    case R.id.rb_2:{

                    }
                    case R.id.rb_3:{

                    }
                }
                Button btOk=dialogView.findViewById(R.id.bt_ok);
                Button btCancer= dialogView.findViewById(R.id.bt_cancer);


        }


    }
}