package com.example.womensafety;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailVerify extends AppCompatActivity {

    Button go_home;
    FirebaseAuth mAuth;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verify);


        go_home = findViewById(R.id.go_home);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();


        go_home.setOnClickListener(v->{


            user.reload();


            if(user.isEmailVerified()){

                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
            else{
                user.sendEmailVerification();
                Toast.makeText(getApplicationContext(), "Email Sent", Toast.LENGTH_SHORT).show();
            }






        });
    }
}