package com.example.baecosmoclinic;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.douglas.baecosmoclinic.adapter.RecyclerViewAdapter;
import com.douglas.bean.ServiceCategory;
import com.douglas.bean.ServiceDetail;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeActivity  extends AppCompatActivity {
    List<ServiceDetail> serviceList;
    List<ServiceCategory> serviceCategory;
    BottomNavigationView bottomNavigation;

    ImageView notification;
    RecyclerView recyclerView;

    ImageView homeImage;
    String userEmail;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_main);

        Intent i = getIntent();
        userEmail = i.getStringExtra("userEmail");


        getIds();
        addListeners();
        getHomeImage();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        serviceList = new ArrayList<>();
        serviceCategory = new ArrayList<>();


        db = FirebaseDatabase.getInstance().getReference().child("Service");

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    final String category= snapshot.getKey();


                    db.child(category).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot2 : dataSnapshot.getChildren()) {
                                String thumbnail = snapshot2.child("url").getValue(String.class);
                                if (thumbnail != null) {
                                    serviceCategory.add(new ServiceCategory(category, thumbnail));
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }

                RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(HomeActivity.this, serviceCategory);
                recyclerView.setLayoutManager(new GridLayoutManager(HomeActivity.this,3));
                recyclerView.setAdapter(myAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.navigation_home:
                        break;
                    case R.id.navigation_dashboard:
                        startActivity(new Intent(HomeActivity.this, ScheduleActivity.class));
                        break;
                    case R.id.navigation_map:
                        startActivity(new Intent(HomeActivity.this, MapsActivity.class));
                        break;
                    case R.id.navigation_about:
                        startActivity(new Intent(HomeActivity.this, BaeProfile.class));
                        break;
                    case R.id.navigation_account:
                        if(userEmail.equals("admin@admin.com")) {
                            Intent intent = new Intent(HomeActivity.this,AdminHomeActivity.class);
                            intent.putExtra("userEmail", userEmail);
                            startActivity(intent);
                            break;
                        }
                        else {
                            Intent intent = new Intent(HomeActivity.this,UserProfile.class);
                            intent.putExtra("userEmail", userEmail);
                            startActivity(intent);
                            break;
                        }

                }



                return false;
            }
        });

    }

    private void getHomeImage() {
        homeImage = (ImageView) findViewById(R.id.displayProduct);
        homeImage.setImageResource(R.drawable.baeproduct);

        homeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.benebiomecosmetics.com/"));
                startActivity(intent);
            }
        });
    }

    public void getIds()
    {
        bottomNavigation = findViewById(R.id.bottomNavBar);
        notification = findViewById(R.id.notification);
    }

    // notification action
    public void addListeners()
    {
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, NotificationActivity.class));
            }
        });
    }

}
