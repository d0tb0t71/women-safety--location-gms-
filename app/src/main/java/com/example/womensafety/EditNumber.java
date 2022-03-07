package com.example.womensafety;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.womensafety.models.MyNumbersModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Map;

public class EditNumber extends AppCompatActivity {


    EditText edit_name,edit_no;
    Button save_info;
    FirebaseFirestore db;
    FirebaseUser user;
    MyNumbersModel myNumbersModel = new MyNumbersModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_number);

        getSupportActionBar().setTitle("Edit Numbers");

        edit_name = findViewById(R.id.edit_name);
        edit_no = findViewById(R.id.edit_no);
        save_info = findViewById(R.id.save_info);

        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        String id = getIntent().getStringExtra("id");


        DocumentReference documentReference = db.collection("myNo").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {



               myNumbersModel = value.toObject(MyNumbersModel.class);

               switch (id){
                   case "1":
                       edit_no.setText(myNumbersModel.getNo1());
                       edit_name.setText(myNumbersModel.getN1());
                       break;
                   case "2":
                       edit_no.setText(myNumbersModel.getNo2());
                       edit_name.setText(myNumbersModel.getN2());
                       break;
                   case "3":
                       edit_no.setText(myNumbersModel.getNo3());
                       edit_name.setText(myNumbersModel.getN3());
                       break;
                   case "4":
                       edit_no.setText(myNumbersModel.getNo4());
                       edit_name.setText(myNumbersModel.getN4());
                       break;
                   case "5":
                       edit_no.setText(myNumbersModel.getNo5());
                       edit_name.setText(myNumbersModel.getN5());
                       break;
                   default:
                       System.out.println("No Index");
               }




            }
        })
        ;

        save_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = edit_name.getText().toString();
                String number = edit_no.getText().toString();

                switch (id){
                    case "1":
                        myNumbersModel.setN1(name);
                        myNumbersModel.setNo1(number);
                        break;
                    case "2":
                        myNumbersModel.setN2(name);
                        myNumbersModel.setNo2(number);
                        break;
                    case "3":
                        myNumbersModel.setN3(name);
                        myNumbersModel.setNo3(number);
                        break;
                    case "4":
                        myNumbersModel.setN4(name);
                        myNumbersModel.setNo4(number);
                        break;
                    case "5":
                        myNumbersModel.setN5(name);
                        myNumbersModel.setNo5(number);
                        break;
                }

                db.collection("myNo")
                        .document(user.getUid())
                        .set(myNumbersModel);

                startActivity(new Intent(getApplicationContext(),MyNumbers.class));
                finish();

            }
        });



    }
}