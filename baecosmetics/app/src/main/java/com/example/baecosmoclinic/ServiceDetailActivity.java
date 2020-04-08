package com.example.baecosmoclinic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.bean.ServiceDetailList;
import com.douglas.baecosmoclinic.adapter.RecyclerViewAdapter;
import com.douglas.baecosmoclinic.adapter.ServiceDetailAdapter;
import com.douglas.bean.ServiceCategory;
import com.douglas.bean.ServiceDetail;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ServiceDetailActivity extends AppCompatActivity {

    List<ServiceDetail> serviceList;
    DatabaseReference db;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);

        Intent i = getIntent();
        final String category = i.getStringExtra("Category");

        db = FirebaseDatabase.getInstance().getReference();

        db.keepSynced(true);

        serviceList = new ArrayList<>();
        rv = (RecyclerView) findViewById(R.id.recyclerViewForDetail);

        db =  FirebaseDatabase.getInstance().getReference().child("Service").child(category);
        db.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(int i = 0; i < dataSnapshot.getChildrenCount()-1; i ++ ){
                    String count = String.valueOf(i);

                    String title = dataSnapshot.child(count).child("title").getValue(String.class);

                    String description = dataSnapshot.child(count).child("description").getValue(String.class);

                    String thumbnail = dataSnapshot.child("Thumbnail").child("url").getValue(String.class);
                    serviceList.add(new ServiceDetail(title, category, description, thumbnail));
                }
                ServiceDetailAdapter mAdapter = new ServiceDetailAdapter(ServiceDetailActivity.this, serviceList);
                rv.setLayoutManager(new LinearLayoutManager(ServiceDetailActivity.this));
                rv.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }
}
