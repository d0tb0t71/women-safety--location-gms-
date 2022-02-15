package com.example.womensafety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button get_help, show_location,show_numbers;
    TextView print_Location;

    LocationManager locationManager;
    String lon = "";
    String lat = "";
    List<Address> addresses ;
    String address="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        get_help = findViewById(R.id.get_help);
        show_location = findViewById(R.id.show_location);
        print_Location = findViewById(R.id.print_Location);
        show_numbers = findViewById(R.id.show_numbers);

        show_location.setEnabled(false);


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(this, new String[] {
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION },
                        1);
        }


        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 1, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {

                print_Location.setText(String.valueOf(location.getLongitude()) + "\n" + String.valueOf(location.getLatitude()));


                lon = String.valueOf(location.getLongitude());
                lat = String.valueOf(location.getLatitude());


                Geocoder geocoder;
                geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                try {
                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    address = addresses.get(0).getAddressLine(0);
                    show_location.setEnabled(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

        });

        show_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194");
                Uri gmmIntentUri = Uri.parse("geo:" +lat+","+lon+"?q="+Uri.parse(lat+","+lon+"("+address+")"));
                // Creates an Intent that will load a map of San Francisco
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        show_numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(),MyNumbers.class));
            }
        });


    }
}