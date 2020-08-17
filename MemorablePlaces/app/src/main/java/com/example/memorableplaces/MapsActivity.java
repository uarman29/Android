package com.example.memorableplaces;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                centerMapOnLocation(lastKnownLocation, "Your Location");
            }
        }
    }

    public void centerMapOnLocation(Location location, String title)
    {
        if(location != null) {
            LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(userLocation).title(title));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 13));
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);

        Intent intent = getIntent();
        if(intent.getIntExtra("placeNumberSelected",0) == 0)
        {
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location)
                {
                    centerMapOnLocation(location, "Your Location");
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };

            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
            else
            {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                centerMapOnLocation(lastKnownLocation, "Your Location");
            }
        }
        else
        {
            Location location = new Location(LocationManager.GPS_PROVIDER);
            location.setLatitude(MainActivity.locationCoords.get(intent.getIntExtra("placeNumberSelected",0)).latitude);
            location.setLongitude(MainActivity.locationCoords.get(intent.getIntExtra("placeNumberSelected",0)).longitude);
            centerMapOnLocation(location, MainActivity.locations.get(intent.getIntExtra("placeNumberSelected",0)));

        }
    }

    @Override
    public void onMapLongClick(LatLng latLng)
    {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        String address = "";
        try
        {
            List<Address> addressList = geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);

            if(addressList != null && addressList.size() > 0)
            {
                if(addressList.get(0).getThoroughfare() != null)
                {
                    if(addressList.get(0).getSubThoroughfare() != null)
                        address += addressList.get(0).getSubThoroughfare() + " ";
                    address += addressList.get(0).getThoroughfare();
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        if(address.length() == 0)
        {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm: dd-MM-yyyy");
            address = sdf.format(new Date());
        }

        mMap.addMarker(new MarkerOptions().position(latLng).title(address));
        MainActivity.locations.add(address);
        MainActivity.locationCoords.add(latLng);
        MainActivity.arrayAdapter.notifyDataSetChanged();
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.memorableplaces", Context.MODE_PRIVATE);
        try {
            sharedPreferences.edit().putString("locations", ObjectSerializer.serialize(MainActivity.locations)).apply();

            ArrayList<String> latitudes = new ArrayList<String>();
            ArrayList<String> longitudes = new ArrayList<String>();
            for(LatLng coords: MainActivity.locationCoords)
            {
                latitudes.add(Double.toString(coords.latitude));
                longitudes.add(Double.toString(coords.longitude));
            }
            sharedPreferences.edit().putString("latitudes", ObjectSerializer.serialize(latitudes)).apply();
            sharedPreferences.edit().putString("longitudes", ObjectSerializer.serialize(longitudes)).apply();

        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "Location saved", Toast.LENGTH_SHORT).show();
    }
}