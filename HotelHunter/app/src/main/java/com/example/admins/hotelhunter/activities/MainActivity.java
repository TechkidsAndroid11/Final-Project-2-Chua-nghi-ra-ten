package com.example.admins.hotelhunter.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.admins.hotelhunter.database.DataHandle;
import com.example.admins.hotelhunter.database.OnClickWindowinfo;
import com.example.admins.hotelhunter.map_direction.DirectionHandler;
import com.example.admins.hotelhunter.map_direction.DirectionResponse;
import com.example.admins.hotelhunter.map_direction.RetrofitInstance;
import com.example.admins.hotelhunter.map_direction.RetrofitService;
import com.example.admins.hotelhunter.map_direction.RouteModel;
import com.example.admins.hotelhunter.model.HotelModel;
import com.facebook.login.LoginManager;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import com.example.admins.hotelhunter.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {
    private static final String TAG = MainActivity.class.toString();
    FirebaseAuth firebaseAuth;
    private GoogleMap mMap;
    public static LatLng currentLocation;
    TextView tvName, tvNavText;
    ImageView ivAvata;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            tvNavText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);

                }
            });
        } else {
            tvNavText.setVisibility(View.GONE);
            tvName.setText(firebaseAuth.getCurrentUser().getDisplayName());
//            Picasso.with(this).load(R.drawable.ic_close_black_24dp).into(ivAvata);
            ivAvata.setImageResource(R.drawable.ic_close_black_24dp);
        }

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
            firebaseAuth.signOut();
            tvNavText.setVisibility(View.VISIBLE);
            ivAvata.setImageResource(R.mipmap.ic_launcher_round);
            tvName.setVisibility(View.GONE);


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
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
                for (int i = 0; i < list.size(); i++){
                    if (marker.getPosition().latitude == list.get(i).viDo && marker.getPosition().longitude == list.get(i).kinhDo){
                        EventBus.getDefault().postSticky(new OnClickWindowinfo(list.get(i)));
                        Log.d(TAG, "onInfoWindowClick: ");
                    }
                }
                Log.d(TAG, "onInfoWindowClick: ");
                Intent intent = new Intent(MainActivity.this, InformationOfHotelActivity.class);
                startActivity(intent);
            }
        });
        RetrofitService retrofitService = RetrofitInstance.getInstance().create(RetrofitService.class);
        retrofitService.getDirection("21.0222381,105.8161896","21.007790699999997,105.80114449999999","AIzaSyCPHUVwzFXx1bfLxZx9b8QYlZD_HMJza_0").enqueue(new Callback<DirectionResponse>() {
            @Override
            public void onResponse(Call<DirectionResponse> call, Response<DirectionResponse> response) {
                RouteModel routeModel = DirectionHandler.getListRoute(response.body()).get(0);
                Log.d(TAG, "onResponse: "+routeModel.distance);
                Log.d(TAG, "onResponse: "+routeModel.duration);

            }

            @Override
            public void onFailure(Call<DirectionResponse> call, Throwable t) {

            }
        });
    }
}
