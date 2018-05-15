package com.example.aquibkhan.ramazan;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.aquibkhan.ramazan.Fragment.ExploreFragment;
import com.example.aquibkhan.ramazan.Fragment.HomeFragment;
import com.example.aquibkhan.ramazan.Fragment.ProfileFragment;
import com.example.aquibkhan.ramazan.Fragment.SearchFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONObject;


public class Home extends AppCompatActivity implements LocationListener {
    private static final String TAG = "Home";

    private TextView mTextMessage;

    private String url;

    private RequestQueue queue;

    private LocationManager locationManager;

    private FusedLocationProviderClient mFusedLocationClient;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    Fragment hfragment = new HomeFragment();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame, hfragment)
                            .commit();

                    return true;
                case R.id.navigation_Explore:

                    Fragment Efragment = new ExploreFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, Efragment).commit();

                    return true;
                case R.id.navigation_profile:

                    Fragment Pfragment = new ProfileFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, Pfragment).commit();

                    return true;

                case R.id.navigation_search:
                    Fragment sfragment = new SearchFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, sfragment).commit();
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Fragment hfragment = new HomeFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame, hfragment)
                .commit();

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);


        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

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
        Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
        onLocationChanged(location);

        //        Here we get the longitude and the latitude
//        Saved in a String[] variable and use it

       queue = Volley.newRequestQueue(this);

    }

    @Override
    public void onLocationChanged(Location location) {

        Double longitude = null, latitude = null;

        if(location != null){
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        }

        Toast.makeText(this, ""+longitude+" "+latitude+"", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
