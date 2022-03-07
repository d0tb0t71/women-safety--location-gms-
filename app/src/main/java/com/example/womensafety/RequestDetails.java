package com.example.womensafety;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class RequestDetails extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseUser user;

    TextView username,time_tv,mobile_tv;
    Button call_now;

    String mobileNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_details);

        getSupportActionBar().setTitle("Request Details");

        String id = getIntent().getStringExtra("id");
        String time = getIntent().getStringExtra("time");


        username = findViewById(R.id.username);
        time_tv = findViewById(R.id.time_tv);
        mobile_tv = findViewById(R.id.mobile_tv);

        call_now = findViewById(R.id.call_now);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        String uid = user.getUid();

        time_tv.setText("Date Time"+time);

        DocumentReference documentReference = db.collection("users").document(id);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                username.setText(""+value.getString("name"));
                mobile_tv.setText("Email: "+value.getString("mobile"));

                mobileNo = ""+value.getString("mobile");

            }
        });


        call_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+mobileNo));
                startActivity(callIntent);


            }
        });



    }
}