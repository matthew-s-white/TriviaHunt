package com.example.triviahunt;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {


    private SupportMapFragment supportMapFragment;
    Location currLocation;
    private FusedLocationProviderClient client;
    private static final int REQUEST_CODE = 101;
    private static final double START_LATITUDE = 37.4220186;
    private static final double START_LONGITUDE = -122.0839727;
    private static final double PROXIMITY_RADIUS_IN_FEET = 150.0;
    SharedPreferences prefs = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_map);

        prefs = getSharedPreferences("com.example.triviahunt", MODE_PRIVATE);

        client = LocationServices.getFusedLocationProviderClient(this);
        getCurrLocation();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (prefs.getBoolean("firstrun", true)) {
            Intent intent1 = new Intent(this, AccountActivity.class);
            startActivity(intent1);
            prefs.edit().putBoolean("firstrun", false).commit();
        }
    }

    private void getCurrLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE);
            return;
        }

        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if (location != null) {
                    currLocation = location;

                    supportMapFragment.getMapAsync(MainActivity.this);
                }
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng currLatLng = new LatLng(currLocation.getLatitude(), currLocation.getLongitude());
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(currLatLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currLatLng, 20));
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
        googleMap.setMyLocationEnabled(true);
        generateNearbyMarkers(googleMap);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getCurrLocation();
            }
        }
    }

    public Bitmap resizeBitmap(String drawableName,int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(drawableName, "drawable", getPackageName()));
        return Bitmap.createScaledBitmap(imageBitmap, width, height, false);
    }

    private void generateNearbyMarkers(GoogleMap googleMap){
        int num_of_marks;
        num_of_marks = (int)((Math.random() * 5.0) + 4.0);
        System.out.println("num_of_marks: "+ num_of_marks);
        for(int i = 0; i < num_of_marks; i++){
            double new_latitude, new_longitude;
            new_latitude = ((((Math.random() * 2) - 1)) * (Math.random() / 800)) + START_LATITUDE;
            new_longitude = ((((Math.random() * 2) - 1)) * (Math.random() / 800)) + START_LONGITUDE;
            System.out.println("new_lat: " + new_latitude + " new_long: " + new_longitude);
            LatLng newLatLng = new LatLng(new_latitude, new_longitude);
            MarkerOptions newLoc = new MarkerOptions().position(newLatLng).title("TrivaQ" + i).icon(BitmapDescriptorFactory.fromBitmap(resizeBitmap("trivia_hunt_marker", 120, 120)));
            googleMap.addMarker(newLoc);
        }

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (checkMarkerProximity(marker)){
                    marker.setVisible(false);
                    Intent intent1 = new Intent(MainActivity.this, TriviaCardActivity.class);
                    startActivity(intent1);
                    return true;
                }
                else {
                    return false;
                }
            }
        });
    }

    private boolean checkMarkerProximity(Marker marker){
        if (Math.abs(marker.getPosition().latitude - currLocation.getLatitude()) <= PROXIMITY_RADIUS_IN_FEET/364000.0
        && Math.abs(marker.getPosition().latitude - currLocation.getLatitude()) <= PROXIMITY_RADIUS_IN_FEET/288200.0) {
            return true;
        }
        return false;
    }
}