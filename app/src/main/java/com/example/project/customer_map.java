package com.example.project;

import static android.app.PendingIntent.getActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;

import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryDataEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class customer_map extends FragmentActivity implements OnMapReadyCallback ,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    FirebaseAuth auth;
    Button button;
    FirebaseUser user;
    private Boolean  currentLogout=false;
    private GoogleApiClient mGoogleApiClient;
    private Button call;
    private String userid;
    private LatLng pick;
    private DatabaseReference driverloc;
    private DatabaseReference userref;
    private int radius=1;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;

    private Boolean driverFound=false;
    private String driverfoundid;
    private Location lastLocation;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_map);
        call=findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (locationCallback != null) {
                    GeoFire geoFire = new GeoFire(userref);
                    geoFire.setLocation(userid, new GeoLocation(lastLocation.getLatitude(), lastLocation.getLongitude()));
                    pick = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(pick).title("Pick customer from here"));
                    call.setText("Getting your driver");
                    Getclosedrive();
                } else {
                    // Handle the case where lastLocation is null
                    mMap.addMarker(new MarkerOptions().position(pick).title("Pick customer from here"));
                    call.setText("Getting your driver");
                }
            }
        });



        Button set=findViewById(R.id.menuset);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        drawerLayout = findViewById(R.id.drawer);
        navigationView= findViewById(R.id.nav_view);
        drawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                }
                return false;
            }
        });

        auth =FirebaseAuth.getInstance();
        button=findViewById(R.id.logoutt);
        user=auth.getCurrentUser();
        userid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        userref=FirebaseDatabase.getInstance().getReference().child("users");
        driverloc=FirebaseDatabase.getInstance().getReference().child("Drivers Available");
        button.setOnClickListener(new View.OnClickListener(){
                                      @Override
                                      public void onClick(View view){
                                          currentLogout=true;
                                          auth.signOut();

                                          Intent intent=new Intent(customer_map.this, customer_login.class);
                                          startActivity(intent);
                                          finish();
                                      }
                                  }
        );

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    lastLocation = location;
                    updateLocationUI(location);
                }
            }
        };
    }

    private void Getclosedrive() {
        GeoFire geoFire=new GeoFire(driverloc);
        GeoQuery geoQuery=geoFire.queryAtLocation(new GeoLocation(pick.latitude,pick.longitude),radius);

        geoQuery.addGeoQueryDataEventListener(new GeoQueryDataEventListener() {
            @Override
            public void onDataEntered(DataSnapshot dataSnapshot, GeoLocation location) {

            }

            @Override
            public void onDataExited(DataSnapshot dataSnapshot) {
                if(!driverFound){
                    driverFound=true;
                    String key = dataSnapshot.getKey(); // Initialize key variable here
                    driverfoundid = key;
                }

            }

            @Override
            public void onDataMoved(DataSnapshot dataSnapshot, GeoLocation location) {

            }

            @Override
            public void onDataChanged(DataSnapshot dataSnapshot, GeoLocation location) {

            }

            @Override
            public void onGeoQueryReady() {
                if(!driverFound){
                    radius=radius+1;
                    Getclosedrive();
                }
            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
            return;
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        startLocationUpdates();
        buildGoogleApiClient();
        mMap.setMyLocationEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    private void startLocationUpdates() {
        LocationRequest locationRequest = LocationRequest
                .create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(1000);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                null /* Looper */);
    }

    protected synchronized void buildGoogleApiClient(){
        mGoogleApiClient=new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    private void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
        if(!currentLogout){
            String userID= FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference DriverAvailibilityRef=FirebaseDatabase.getInstance().getReference().child("Drivers Available");
            GeoFire geofire=new GeoFire(DriverAvailibilityRef);
            geofire.removeLocation(userID);
        }
    }

    private void updateLocationUI(Location location) {
        if (mMap == null) {
            return;
        }
        try {
            if (location != null) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
                if (auth.getCurrentUser() != null) { // Check if FirebaseUser object is not null
                    String userId = auth.getCurrentUser().getUid();
                    // Your code here that depends on the FirebaseUser object
                }
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }


    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 1001;

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);

        }
        else {
            super.onBackPressed();
        }
    }
}