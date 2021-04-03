package com.example.triviahunt;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.android.gms.tasks.Task;

import static com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {


    private SupportMapFragment supportMapFragment;
    Location currLocation;
    private FusedLocationProviderClient client;
    private static final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        client = LocationServices.getFusedLocationProviderClient(this);
        getCurrLocation();
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
                    supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.google_map);

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

    private void generateNearbyMarkers(GoogleMap googleMap){
        double start_latitude = 37.4220186;
        double start_longitude = -122.0839727;
        int num_of_marks;
        num_of_marks = (int)((Math.random() * 5.0) + 4.0);
        System.out.println("num_of_marks: "+ num_of_marks);
        for(int i = 0; i < num_of_marks; i++){
            double new_latitude, new_longitude;
            new_latitude = ((((Math.random() * 2) - 1)) * (Math.random() / 800)) + start_latitude;
            new_longitude = ((((Math.random() * 2) - 1)) * (Math.random() / 800)) + start_longitude;
            System.out.println("new_lat: " + new_latitude + " new_long: " + new_longitude);
            LatLng newLatLng = new LatLng(new_latitude, new_longitude);
            MarkerOptions newLoc = new MarkerOptions().position(newLatLng).title("TrivaQ" + i);
            googleMap.addMarker(newLoc);
        }
    }
}