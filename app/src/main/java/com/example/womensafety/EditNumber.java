package com.example.womensafety;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditNumber extends AppCompatActivity {


    EditText edit_name,edit_no;
    Button save_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_number);

        edit_name = findViewById(R.id.edit_name);
        edit_no = findViewById(R.id.edit_no);
        save_info = findViewById(R.id.save_info);

        String id = getIntent().getStringExtra("id");

        @SuppressLint("WrongConstant") SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_APPEND);

        edit_no.setText(sh.getString("no1","+8801"));

        save_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putString("no1", edit_no.getText().toString());
                myEdit.commit();

            }
        });



    }
}