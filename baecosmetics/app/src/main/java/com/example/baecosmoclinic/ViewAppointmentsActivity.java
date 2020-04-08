package com.example.baecosmoclinic;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bean.Appointment;
import com.bean.TimeSlots;
import com.douglas.baecosmoclinic.adapter.ScheduleRecyclerAdapter;
import com.douglas.bean.DoctorSchedule;
import com.douglas.bean.Service;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ViewAppointmentsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Service> serviceList ;
    String day = "";





    FirebaseDatabase mDatabase;
    DatabaseReference mDatabaseReference;
    Appointment appointment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_appointments);
        getIds();

          mDatabase = FirebaseDatabase.getInstance();
         mDatabaseReference = mDatabase.getReference();


        initTab1();



        getData();


    }




    private void initTab1() {

        recyclerView.setVisibility(View.VISIBLE);




    }

    public void getIds()
    {
        recyclerView = findViewById(R.id.recyclerView);




    }



    public void getData()
    {
        mDatabaseReference = mDatabase.getReference().child("appointment").child("days");

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            TimeSlots friday =    dataSnapshot.child("friday").child("timeslots").getValue(TimeSlots.class);
                TimeSlots monday =    dataSnapshot.child("monday").child("timeslots").getValue(TimeSlots.class);
                TimeSlots tuesday =    dataSnapshot.child("tuesday").child("timeslots").getValue(TimeSlots.class);
                TimeSlots wednesday =    dataSnapshot.child("wednesday").child("timeslots").getValue(TimeSlots.class);
                TimeSlots thursday =    dataSnapshot.child("thursday").child("timeslots").getValue(TimeSlots.class);


              //  appointment = dataSnapshot.getValue(Appointment.class);

             //   Log.d(TAG, "User name: " + ds.getName() + ", email " + ds.getEmail());

               // System.out.println(">>>>>>>>>>>>>>>>>>>>>>" + friday.getTime());

                serviceList = new ArrayList<>();
                serviceList.clear();
                if(!monday.getUsername().equals("null"))
                {
                    serviceList.add(new Service("Monday",monday.getDate() + " "  + monday.getTime() + "\n\n" +"username: " + monday.getUsername()  ,"",R.drawable.thevigitarian));

                }
                if(!tuesday.getUsername().equals("null"))
                {
                    serviceList.add(new Service("Tuesday",tuesday.getDate() + " "  + tuesday.getTime() + "\n\n" +"username: "+ tuesday.getUsername() ,"Description book",R.drawable.thewildrobot));

                }
                if(!wednesday.getUsername().equals("null"))
                {
                    serviceList.add(new Service("Wednesday",wednesday.getDate() + " "  + wednesday.getTime() + "\n\n" +"username: "+ wednesday.getUsername() ,"Description book",R.drawable.mariasemples));

                }
                if(!thursday.getUsername().equals("null"))
                {
                    serviceList.add(new Service("Thursday",thursday.getDate() + " "  + thursday.getTime() + "\n\n" +"username: "+ thursday.getUsername() ,"Description book",R.drawable.themartian));

                }
                if(!friday.getUsername().equals("null"))
                {
                    serviceList.add(new Service("Friday",friday.getDate() + " "  + friday.getTime() + "\n\n" +"username: "+ friday.getUsername() ,"Description book",R.drawable.hediedwith));

                }


                ScheduleRecyclerAdapter myAdapter = new ScheduleRecyclerAdapter(ViewAppointmentsActivity.this,serviceList);
                recyclerView.setLayoutManager(new LinearLayoutManager(ViewAppointmentsActivity.this));
                recyclerView.setAdapter(myAdapter);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
             //   Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

}
