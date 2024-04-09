package com.example.project;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

public class home extends BaseActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitleHome(R.id.toolbar, R.id.iv_title, R.drawable.ic_burger, R.drawable.logo2);

        Button btn_request = findViewById(R.id.bt_request);
        btn_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home.this, SelectDriver.class));
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        GoogleMap mMap = googleMap;

        boolean success = googleMap.setMapStyle(new MapStyleOptions(getResources()
                .getString(R.string.style_json)));

        if (!success) {
            Log.e("Style", "Style parsing failed.");
        }

        LatLng jakarta = new LatLng(-6.232812, 106.820933);
        LatLng southjakarta = new LatLng(-6.22865,106.8151753);

        mMap.addMarker(new MarkerOptions().position(jakarta).icon(BitmapDescriptorFactory.fromBitmap(getBitmapFromView("Set Pickup Location", R.drawable.dot_pickup))));
        mMap.addMarker(new MarkerOptions().position(southjakarta).icon(BitmapDescriptorFactory.fromBitmap(getBitmapFromView("Set Dropoff Location", R.drawable.dot_dropoff))));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(jakarta, 15f));
    }

    public Bitmap getBitmapFromView(String title, int dotBg) {
        // Implement this method as needed
        return null;
    }

}
