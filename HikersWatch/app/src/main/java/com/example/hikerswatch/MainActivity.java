package com.example.hikerswatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
{
    TextView latitudeTextView;
    TextView longitudeTextView;
    TextView accuracyTextView;
    TextView altitudeTextView;
    TextView addressTextView;
    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latitudeTextView = (TextView) findViewById(R.id.latitudeTextView);
        longitudeTextView = (TextView) findViewById(R.id.longitudeTextView);
        accuracyTextView = (TextView) findViewById(R.id.accuracyTextView);
        altitudeTextView = (TextView) findViewById(R.id.altitudeTextView);
        addressTextView = (TextView) findViewById(R.id.addressTextView);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location)
            {
                updateLocationInfo(location);
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
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        else
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if(lastKnownLocation != null)
            {
                updateLocationInfo(lastKnownLocation);
            }
        }
    }

    public void updateLocationInfo(Location location)
    {
        latitudeTextView.setText("Latitude: " + location.getLatitude());
        longitudeTextView.setText("Longitude: " + location.getLongitude());
        accuracyTextView.setText("Accuracy: " +  location.getAccuracy());
        altitudeTextView.setText("Altitude: " + location.getAltitude());

        Geocoder geocoder = new Geocoder(this,Locale.getDefault());

        try {
            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String address = "Address:\n";

            if(addressList != null && addressList.size() > 0)
            {
                if(addressList.get(0).getThoroughfare() != null)
                    address += addressList.get(0).getThoroughfare() + "\n";

                if(addressList.get(0).getLocality() != null)
                    address += addressList.get(0).getLocality() + " ";

                if(addressList.get(0).getPostalCode() != null)
                    address += addressList.get(0).getPostalCode() + " ";

                if(addressList.get(0).getAdminArea() != null)
                    address += addressList.get(0).getAdminArea();
            }
            addressTextView.setText(address);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            addressTextView.setText("");
        }
    }
}