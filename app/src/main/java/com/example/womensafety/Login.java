package com.example.womensafety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    EditText email, pass;
    Button login;
    TextView register_now;

    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        login = findViewById(R.id.login);
        register_now = findViewById(R.id.register_now);

        mAuth = FirebaseAuth.getInstance();


        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("no1", "+8801");
        myEdit.putString("no2", "+8801");
        myEdit.putString("no3", "+8801");
        myEdit.putString("no4", "+8801");
        myEdit.putString("no5", "+8801");
        myEdit.commit();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString();
                String Pass = pass.getText().toString();


                mAuth.signInWithEmailAndPassword(Email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        user = mAuth.getCurrentUser();
                        if(task.isSuccessful() ){
                            if(user.isEmailVerified()){
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            }
                            else
                            {
                                startActivity(new Intent(getApplicationContext(),EmailVerify.class));
                            }

                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Login Failed \n"+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        register_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Registration.class));
            }
        });


    }
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null ){

            if(user.isEmailVerified()){
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
            else{
                startActivity(new Intent(getApplicationContext(),EmailVerify.class));
            }

        }


    }

}