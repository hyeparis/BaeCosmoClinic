package com.example.baecosmoclinic;

import android.os.Bundle;

import com.douglas.baecosmoclinic.adapter.NotificationRecyclerAdapter;
import com.douglas.bean.NotificationList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NotificationActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<NotificationList>  listOfNotification;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        recyclerView = findViewById(R.id.recyclerView);

        db = FirebaseDatabase.getInstance().getReference().child("notification");

        listOfNotification = new ArrayList<>();

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    String title = snapshot.child("title").getValue(String.class);
                    String desc = snapshot.child("description").getValue(String.class);

                    listOfNotification.add(new NotificationList(title, desc));

                }
                NotificationRecyclerAdapter myAdapter = new NotificationRecyclerAdapter(NotificationActivity.this, listOfNotification);
                recyclerView.setLayoutManager(new LinearLayoutManager(NotificationActivity.this));
                recyclerView.setAdapter(myAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



}
