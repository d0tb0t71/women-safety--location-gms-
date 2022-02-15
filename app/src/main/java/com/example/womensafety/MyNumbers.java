package com.example.womensafety;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MyNumbers extends AppCompatActivity {

    TextView no1,no2,no3,no4,no5;
    ImageView edit1,edit2,edit3,edit4,edit5;
    TextView n1,n2,n3,n4,n5;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_numbers);


        no1 = findViewById(R.id.no1);
        no2 = findViewById(R.id.no2);
        no3 = findViewById(R.id.no3);
        no4 = findViewById(R.id.no4);
        no5 = findViewById(R.id.no5);

        edit1 = findViewById(R.id.edit1);
        edit2 = findViewById(R.id.edit2);
        edit3 = findViewById(R.id.edit3);
        edit4 = findViewById(R.id.edit4);
        edit5 = findViewById(R.id.edit5);

        n1 = findViewById(R.id.n1);
        n2 = findViewById(R.id.n2);
        n3 = findViewById(R.id.n3);
        n4 = findViewById(R.id.n4);
        n5 = findViewById(R.id.n5);



        db = FirebaseFirestore.getInstance();


        DocumentReference documentReference = db.collection("myNo").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                n1.setText(value.getString("n1"));
                n2.setText(value.getString("n2"));
                n3.setText(value.getString("n3"));
                n4.setText(value.getString("n4"));
                n5.setText(value.getString("n5"));

                no1.setText(value.getString("no1"));
                no2.setText(value.getString("no2"));
                no3.setText(value.getString("no3"));
                no4.setText(value.getString("no4"));
                no5.setText(value.getString("no5"));

                //documentSnapshot.toObject(Vehicle.class);



            }
        })
        ;
        Intent intent = new Intent(getBaseContext(), EditNumber.class);


        edit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("id", "1");
                startActivity(intent);
            }
        });

        edit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("id", "2");
                startActivity(intent);
            }
        });


        edit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("id", "3");
                startActivity(intent);
            }
        });


        edit4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("id", "4");
                startActivity(intent);
            }
        });


        edit5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("id", "5");
                startActivity(intent);
            }
        });




    }
}