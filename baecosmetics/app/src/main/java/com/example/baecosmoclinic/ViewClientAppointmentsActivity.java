package com.example.baecosmoclinic;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bean.Appointment;
import com.bean.TimeSlots;
import com.douglas.baecosmoclinic.adapter.ScheduleRecyclerAdapter;
import com.douglas.bean.Service;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
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

public class ViewClientAppointmentsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Service> serviceList ;
    String day = "";


Boolean empty = true;
String usn="";

    FirebaseDatabase mDatabase;
    DatabaseReference mDatabaseReference;
    Appointment appointment;

    FloatingActionButton fabCancel;

    TimeSlots booking=new TimeSlots();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_client_appointments);
        getIds();


          mDatabase = FirebaseDatabase.getInstance();
         mDatabaseReference = mDatabase.getReference();


        SharedPreferences sp = getSharedPreferences("userinfo" , Context.MODE_PRIVATE);
        String email  = sp.getString("email","null");
        usn=  email.trim().split("@")[0];

        initTab1();



        getData();

        setListeners();
    }

    private void setListeners() {

        fabCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                booking = new TimeSlots("null", "null", "null");

                mDatabase.getReference().child("appointment").child("days").child(day).child("timeslots").setValue(booking);

                Snackbar snackbar = Snackbar
                        .make(findViewById(R.id.coordinatorLayout), "Appointment Removed", Snackbar.LENGTH_LONG);
                snackbar.show();

                snackbar.addCallback(new Snackbar.Callback() {

                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        //see Snackbar.Callback docs for event details

                        ViewClientAppointmentsActivity.this.finish();
                    }

                    @Override
                    public void onShown(Snackbar snackbar) {

                    }
                });

               // Toast.makeText(ViewClientAppointmentsActivity.this, "Appointment Cancelled", Toast.LENGTH_SHORT).show();

            }
        });






    }


    private void initTab1() {

        recyclerView.setVisibility(View.VISIBLE);




    }

    public void getIds()
    {
        recyclerView = findViewById(R.id.recyclerView);

        fabCancel = findViewById(R.id.fabCancel);

    }



    public void getData()
    {
        mDatabaseReference = mDatabase.getReference().child("appointment").child("days");

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                TimeSlots friday = dataSnapshot.child("friday").child("timeslots").getValue(TimeSlots.class);
                TimeSlots monday = dataSnapshot.child("monday").child("timeslots").getValue(TimeSlots.class);
                TimeSlots tuesday = dataSnapshot.child("tuesday").child("timeslots").getValue(TimeSlots.class);
                TimeSlots wednesday = dataSnapshot.child("wednesday").child("timeslots").getValue(TimeSlots.class);
                TimeSlots thursday = dataSnapshot.child("thursday").child("timeslots").getValue(TimeSlots.class);


                if (friday.getUsername().equals(usn)) {
                    empty = false;
                    booking = friday;
                    day = "friday";
                } else if (monday.getUsername().equals(usn)) {
                    day = "monday";
                    empty = false;
                    booking = monday;
                } else if (tuesday.getUsername().equals(usn)) {
                    day = "tuesday";
                    empty = false;
                    booking = tuesday;
                } else if (wednesday.getUsername().equals(usn)) {
                    empty = false;
                    day = "wednesday";
                    booking = wednesday;
                } else if (thursday.getUsername().equals(usn)) {
                    empty = false;
                    day = "thursday";
                    booking = thursday;
                }


                //  appointment = dataSnapshot.getValue(Appointment.class);

                //   Log.d(TAG, "User name: " + ds.getName() + ", email " + ds.getEmail());

                // System.out.println(">>>>>>>>>>>>>>>>>>>>>>" + friday.getTime());

                serviceList = new ArrayList<>();
                serviceList.add(new Service("Current Appointment", booking.getDate() + " " + booking.getTime() + "\n\n" + "username: " + booking.getUsername(), "", R.drawable.thevigitarian));
                /*serviceList.add(new Service("Tuesday",tuesday.getDate() + " "  + tuesday.getTime() + "\n\n" +"username: "+ tuesday.getUsername() ,"Description book",R.drawable.thewildrobot));
                serviceList.add(new Service("Wednesday",wednesday.getDate() + " "  + wednesday.getTime() + "\n\n" +"username: "+ wednesday.getUsername() ,"Description book",R.drawable.mariasemples));
                serviceList.add(new Service("Thursday",thursday.getDate() + " "  + thursday.getTime() + "\n\n" +"username: "+ thursday.getUsername() ,"Description book",R.drawable.themartian));
                serviceList.add(new Service("Friday",friday.getDate() + " "  + friday.getTime() + "\n\n" +"username: "+ friday.getUsername() ,"Description book",R.drawable.hediedwith));
*/
                if(booking.getUsername() != "null") {
                    ScheduleRecyclerAdapter myAdapter = new ScheduleRecyclerAdapter(ViewClientAppointmentsActivity.this, serviceList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ViewClientAppointmentsActivity.this));
                    recyclerView.setAdapter(myAdapter);
                }
            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
             //   Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

}
