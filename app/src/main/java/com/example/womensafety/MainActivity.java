package com.example.womensafety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.womensafety.models.RequestModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
    CardView need_help_card;
    TextView need_help;
    LinearLayout need_help_l;
    Button show_emergency_numbers,show_request;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        get_help = findViewById(R.id.get_help);
        show_location = findViewById(R.id.show_location);
        print_Location = findViewById(R.id.print_Location);
        show_numbers = findViewById(R.id.show_numbers);
        show_emergency_numbers = findViewById(R.id.show_emergency_numbers);
        show_request = findViewById(R.id.show_request);
        need_help_card = findViewById(R.id.need_help_card);
        need_help_l = findViewById(R.id.need_help_l);
        need_help = findViewById(R.id.need_help);

        show_location.setEnabled(false);
        need_help_card.setEnabled(false);
        need_help_l.setEnabled(false);
        need_help.setEnabled(false);


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

                    //enable need
                    need_help_card.setEnabled(true);
                    need_help_l.setEnabled(true);
                    need_help.setEnabled(true);

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

        });


        show_numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(),MyNumbers.class));
            }
        });

        show_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ShowRequests.class));
            }
        });

        show_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ShowRequests.class));
            }
        });



        need_help.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AddHelpRequest();


                return false;
            }
        });

    }

    void AddHelpRequest(){


        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        long time = timestamp.getTime();
        String pID = "XD" + time;

        String issueDate = timestamp.toString();

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);

        RequestModel requestModel = new RequestModel(pID,lon,lat, FirebaseAuth.getInstance().getCurrentUser().getUid(),strDate);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("requests")
                .document(pID)
                .set(requestModel);

        Toast.makeText(getApplicationContext(), "Request Send to community", Toast.LENGTH_SHORT).show();


    }

}