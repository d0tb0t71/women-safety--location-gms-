package com.example.womensafety;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.womensafety.models.RequestModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button get_help, show_location;
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

    TextView my_profile,emergency_number,all_request,my_contact;

    String n1="",n2="",n3="",n4="",n5="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Women Safety - Home");

        get_help = findViewById(R.id.get_help);
        show_location = findViewById(R.id.show_location);
        print_Location = findViewById(R.id.print_Location);
        my_profile = findViewById(R.id.my_profile);
        emergency_number = findViewById(R.id.emergency_number);
        all_request = findViewById(R.id.all_request);
        my_contact = findViewById(R.id.my_contact);
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



        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("myNo").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {



                n1=value.getString("no1");
                n2 =value.getString("no2");
                n3=value.getString("no3");
                n4=value.getString("no4");
                n5=value.getString("no5");




            }
        })
        ;


        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 1, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {

                print_Location.setText("MY LOCATION : "+String.valueOf(location.getLongitude()) + "\n" + String.valueOf(location.getLatitude()));


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


        my_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(),MyNumbers.class));
            }
        });

        all_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ShowRequests.class));
            }
        });

        emergency_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ShowEmergencyHelplineNumbers.class));
            }
        });

        my_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Profile.class));
            }
        });



        need_help.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AddHelpRequest();


                return false;
            }
        });

        need_help_card.setOnLongClickListener(new View.OnLongClickListener() {
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
        String pID = "requestID" + time;

        String issueDate = timestamp.toString();

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);

        RequestModel requestModel = new RequestModel(pID,lon,lat, FirebaseAuth.getInstance().getCurrentUser().getUid(),strDate);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("requests")
                .document(pID)
                .set(requestModel);



        //send sms
        String messageToSend = "I'm in danger.I Need Help. My current Location is Latitude - "+lat + " Longitude - "+lon+"\n.- Women Safety";

        if(n1.length()==11){
            SmsManager.getDefault().sendTextMessage(n1, null, messageToSend, null,null);
        }

        if(n2.length()==11){
            SmsManager.getDefault().sendTextMessage(n2, null, messageToSend, null,null);
        }
        if(n3.length()==11){
            SmsManager.getDefault().sendTextMessage(n3, null, messageToSend, null,null);
        }
        if(n4.length()==11){
            SmsManager.getDefault().sendTextMessage(n4, null, messageToSend, null,null);
        }
        if(n5.length()==11){
            SmsManager.getDefault().sendTextMessage(n5, null, messageToSend, null,null);
        }


        Toast.makeText(getApplicationContext(), "Request Send to community and emergency message sent to your 5 contact", Toast.LENGTH_LONG).show();


    }

}