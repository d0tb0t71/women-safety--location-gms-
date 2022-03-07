package com.example.womensafety;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.womensafety.adapters.RequestsAdapter;
import com.example.womensafety.models.RequestModel;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ShowRequests extends AppCompatActivity {

    RecyclerView requests_recyclerview;
    RequestsAdapter requestsAdapter;
    ArrayList<RequestModel> list;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_requests);

        getSupportActionBar().setTitle("Show Request");

        requests_recyclerview = findViewById(R.id.requests_recyclerview);


        db = FirebaseFirestore.getInstance();

        LinearLayoutManager layoutManager = new LinearLayoutManager(ShowRequests.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        requests_recyclerview.setLayoutManager(layoutManager);

        list = new ArrayList<>();

        requestsAdapter = new RequestsAdapter(this,list);
        requests_recyclerview.setAdapter(requestsAdapter);



        db.collection("requests")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if(error != null){
                            Toast.makeText(getApplicationContext(), "Error "+error.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        for(DocumentChange dc : value.getDocumentChanges()){

                            if(dc.getType() == DocumentChange.Type.ADDED){
                                list.add(dc.getDocument().toObject(RequestModel.class));
                            }

                            requestsAdapter.notifyDataSetChanged();

                        }

                    }
                });









    }
}