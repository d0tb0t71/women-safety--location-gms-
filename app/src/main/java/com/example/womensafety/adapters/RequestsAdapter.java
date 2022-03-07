package com.example.womensafety.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.womensafety.MainActivity;
import com.example.womensafety.R;
import com.example.womensafety.RequestDetails;
import com.example.womensafety.ShowRequests;
import com.example.womensafety.models.RequestModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.ViewHolder> {

    Context context;
    ArrayList<RequestModel> list;

    public RequestsAdapter(Context context, ArrayList<RequestModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.request_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        RequestModel requestModel = list.get(position);


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String rUid = requestModel.getUserID();

        if(uid.equals(rUid)){

            holder.delete_request.setVisibility(View.VISIBLE);

        }

        DocumentReference documentReference = db.collection("users").document(requestModel.getUserID());
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                holder.username.setText(""+value.getString("name"));


            }
        })
        ;

        holder.lat.setText(requestModel.getLat());
        holder.lon.setText(requestModel.getLon());
        holder.time_stamp.setText("Time Stamp : "+requestModel.getDateTime());



        holder.show_in_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String lat = requestModel.getLat();
                String lon = requestModel.getLon();


                //Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194");
                Uri gmmIntentUri = Uri.parse("geo:" +lon+","+lat+"?q="+Uri.parse(lon+","+lat+"("+"address"+")"));
                // Creates an Intent that will load a map of San Francisco
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                context.startActivity(mapIntent);



            }
        });

        holder.show_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, RequestDetails.class);
                intent.putExtra("id", requestModel.getUserID());
                intent.putExtra("time", requestModel.getDateTime());
                context.startActivity(intent);

            }
        });

        holder.delete_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                db.collection("requests").document(requestModel.getRequestID()).delete();
                ((ShowRequests)context).recreate();

                Toast.makeText(v.getContext(), "Request Deleted...", Toast.LENGTH_SHORT).show();

            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView username,lat,lon,time_stamp;
        Button show_in_map,show_details;
        ImageView delete_request;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            lat = itemView.findViewById(R.id.lat);
            lon = itemView.findViewById(R.id.lon);
            time_stamp = itemView.findViewById(R.id.time_stamp);

            show_in_map = itemView.findViewById(R.id.show_in_map);
            show_details = itemView.findViewById(R.id.show_details);
            delete_request = itemView.findViewById(R.id.delete_request);


        }
    }
}

