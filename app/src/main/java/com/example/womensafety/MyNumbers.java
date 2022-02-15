package com.example.womensafety;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MyNumbers extends AppCompatActivity {

    TextView no1,no2,no3,no4,no5;
    ImageView edit1,edit2,edit3,edit4,edit5;
    TextView n1,n2,n3,n4,n5;


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



        @SuppressLint("WrongConstant") SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_APPEND);

        no1.setText(sh.getString("no1","+8801"));
        no2.setText(sh.getString("no2","+8801"));
        no3.setText(sh.getString("no3","+8801"));
        no4.setText(sh.getString("no4","+8801"));
        no5.setText(sh.getString("no5","+8801"));


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