package com.example.baecosmoclinic;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bean.TimeSlots;
import com.douglas.baecosmoclinic.adapter.ScheduleRecyclerAdapter;
import com.douglas.bean.DoctorSchedule;
import com.douglas.bean.Service;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
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

public class ScheduleActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Service> serviceList ;
    String day = "";

    DatePicker picker;

    Button btnGet;
    TextView tvw;

    TimePicker timePicker;
    Button btnGetTime, btnBookIt;
    TextView tvwTime;
    FirebaseDatabase mDatabase;
    DatabaseReference mDatabaseReference;
    DoctorSchedule ds;

    //FloatingActionButton floatingActionButton;

    Boolean empty = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        getIds();

          mDatabase = FirebaseDatabase.getInstance();
         mDatabaseReference = mDatabase.getReference();

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);






        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==0){

                    /*recyclerView.setVisibility(View.VISIBLE);
                    picker.setVisibility(View.GONE);

                    btnGet.setVisibility(View.GONE);
                    tvw.setVisibility(View.GONE);*/


                    initTab1();
                    btnBookIt.setVisibility(View.GONE);

                }
                else if(tab.getPosition()==1){


                    recyclerView.setVisibility(View.GONE);

                    btnGet.setVisibility(View.VISIBLE);
                    tvw.setVisibility(View.VISIBLE);

                    btnGetTime.setVisibility(View.VISIBLE);
                    tvwTime.setVisibility(View.VISIBLE);

                    btnBookIt.setVisibility(View.VISIBLE);


                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                picker.setVisibility(View.VISIBLE);

                hideTime();
                hideDate();

                  }
        });


        picker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                picker.setVisibility(View.GONE);
                showDate();
                showTime();
                tvw.setText("Selected Date: "+ picker.getDayOfMonth()+"/"+ (picker.getMonth() + 1)+"/"+picker.getYear());
                getDay();
            }
        });


        initTab1();

        setListeners();

        getData();
    }

    private void setListeners() {

        btnGetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour, minute;
                String am_pm;

                hideDate();
                hideTime();
                timePicker.setVisibility(View.VISIBLE);


                if (Build.VERSION.SDK_INT >= 23 ){
                    hour = timePicker.getHour();
                    minute = timePicker.getMinute();
                }
                else{
                    hour = timePicker.getCurrentHour();
                    minute = timePicker.getCurrentMinute();
                }
                if(hour > 12) {
                    am_pm = "PM";
                    hour = hour - 12;
                }
                else
                {
                    am_pm="AM";
                }

            }
        });

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                showTime();
                showDate();
                timePicker.setVisibility(View.GONE);
              //  tvwTime.setText("Selected Time: "+ timePicker.getHour() +":"+ timePicker.getMinute()+" ");


                int hour, minute;
                String am_pm;



                if (Build.VERSION.SDK_INT >= 23 ){
                    hour = timePicker.getHour();
                    minute = timePicker.getMinute();
                }
                else{
                    hour = timePicker.getCurrentHour();
                    minute = timePicker.getCurrentMinute();
                }
                if(hour > 12) {
                    am_pm = "PM";
                    hour = hour - 12;
                }
                else
                {
                    am_pm="AM";
                }

                tvwTime.setText("Selected Time: "+ hour +":"+ minute + " " + am_pm);

            }
        });

        btnBookIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mDatabaseReference = mDatabase.getReference().child("appointment").child("days")
                .child(day).child("timeslots");
              //  mDatabaseReference.setValue("Donald Duck");

                int hour, minute;
                String am_pm;



                if (Build.VERSION.SDK_INT >= 23 ){
                    hour = timePicker.getHour();
                    minute = timePicker.getMinute();
                }
                else{
                    hour = timePicker.getCurrentHour();
                    minute = timePicker.getCurrentMinute();
                }
                if(hour > 12) {
                    am_pm = "PM";
                    hour = hour - 12;
                }
                else
                {
                    am_pm="AM";
                }


                String time = hour +":"+ minute+" " + am_pm;

String date = picker.getDayOfMonth()+"/"+ (picker.getMonth() + 1)+"/"+picker.getYear();

                SharedPreferences sp = getSharedPreferences("userinfo" , Context.MODE_PRIVATE);
                String email  = sp.getString("email","null");
                TimeSlots user = new TimeSlots(time.trim(), date.trim(), email.trim().split("@")[0]);
               // mDatabaseReference = mDatabase.getReference().child("user");
                mDatabaseReference.setValue(user);

                Toast.makeText(ScheduleActivity.this, "Booking completed", Toast.LENGTH_SHORT).show();
            }
        });

/*
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                  empty = true;
                //empty[0] = true;

                SharedPreferences sp = getSharedPreferences("userinfo" , Context.MODE_PRIVATE);
                String email  = sp.getString("email","null");
                final String usn=  email.trim().split("@")[0];


              //  DatabaseReference mDatabaseReferenceN;
                mDatabaseReference = mDatabase.getReference().child("appointment").child("days");

                mDatabaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        TimeSlots friday =    dataSnapshot.child("friday").child("timeslots").getValue(TimeSlots.class);
                        TimeSlots monday =    dataSnapshot.child("monday").child("timeslots").getValue(TimeSlots.class);
                        TimeSlots tuesday =    dataSnapshot.child("tuesday").child("timeslots").getValue(TimeSlots.class);
                        TimeSlots wednesday =    dataSnapshot.child("wednesday").child("timeslots").getValue(TimeSlots.class);
                        TimeSlots thursday =    dataSnapshot.child("thursday").child("timeslots").getValue(TimeSlots.class);

                        TimeSlots booking=null;

                        if(friday.getUsername().equals(usn))
                        {
                            empty = false;
                            booking = friday;}
                        else if(monday.getUsername().equals(usn))
                        {
                            empty = false;
                            booking = monday;}
                        else if(tuesday.getUsername().equals(usn))
                        {
                            empty = false;
                            booking = tuesday;}
                        else if(wednesday.getUsername().equals(usn))
                        {
                            empty = false;
                            booking = wednesday;}
                        else if(thursday.getUsername().equals(usn))
                        {
                            empty = false;
                            booking = thursday;}

                        if(empty == true)
                        {
                            Snackbar snackbar = Snackbar
                                    .make(findViewById(R.id.coordinatorLayout), "No Appointments Found", Snackbar.LENGTH_LONG);
                            snackbar.show();

                            //   startActivity(new Intent(ScheduleActivity.this, ViewClientAppointmentsActivity.class));


                        }else {

                            startActivity(new Intent(ScheduleActivity.this, ViewClientAppointmentsActivity.class));
                        }



                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        //   Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });






            }
        });*/

    }

    private void initTab1() {

        recyclerView.setVisibility(View.VISIBLE);
        picker.setVisibility(View.GONE);

        btnGet.setVisibility(View.GONE);
        tvw.setVisibility(View.GONE);

        timePicker.setIs24HourView(true);

        timePicker.setVisibility(View.GONE);
        tvwTime.setVisibility(View.GONE);
        btnGetTime.setVisibility(View.GONE);



    }

    public void getIds()
    {
        recyclerView = findViewById(R.id.recyclerView);

        tvw=(TextView)findViewById(R.id.textView1);
        picker=(DatePicker)findViewById(R.id.datePicker1);
        btnGet=(Button)findViewById(R.id.button1);

        tvwTime =(TextView)findViewById(R.id.textViewTime);
        timePicker=(TimePicker)findViewById(R.id.timePicker1);

        btnGetTime=(Button)findViewById(R.id.buttonTime);

        btnBookIt= (Button)findViewById(R.id.btnBook);

        //floatingActionButton = findViewById(R.id.floatingActionButton);
    }

    public void hideDate()
    {
        btnGet.setVisibility(View.GONE);
        tvw.setVisibility(View.GONE);
    }
    public void showDate()
    {
        btnGet.setVisibility(View.VISIBLE);
        tvw.setVisibility(View.VISIBLE);
    }

    public void hideTime()
    {
        btnGetTime.setVisibility(View.GONE);
        tvwTime.setVisibility(View.GONE);
    }

    public void showTime()
    {
        btnGetTime.setVisibility(View.VISIBLE);
        tvwTime.setVisibility(View.VISIBLE);
    }


    public void getDay()
    {
        Calendar calendar = new GregorianCalendar(2020, picker.getMonth(), picker.getDayOfMonth()); // Note that Month value is 0-based. e.g., 0 for January.
        int result = calendar.get(Calendar.DAY_OF_WEEK);
        switch (result) {
            case Calendar.MONDAY:
                System.out.println("It's Monday !>>>>>>>>>>>>>>>>>>>>");
                day = "monday";
                break;
            case Calendar.TUESDAY:
                System.out.println("It's TUESDAY !>>>>>>>>>>>>>>>>>>>>");
                day = "tuesday";
                break;
            case Calendar.WEDNESDAY:
                System.out.println("It's WEDNESDAY !>>>>>>>>>>>>>>>>>>>>");
                day = "wednesday";
                break;
            case Calendar.THURSDAY:
                System.out.println("It's THURSDAY !>>>>>>>>>>>>>>>>>>>>");
                day = "thursday";
                break;
            case Calendar.FRIDAY:
                System.out.println("It's FRIDAY !>>>>>>>>>>>>>>>>>>>>");
                day = "friday";
                break;
        }
    }


    public void getData()
    {
        mDatabaseReference = mDatabase.getReference().child("schedule");

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                 ds = dataSnapshot.getValue(DoctorSchedule.class);

             //   Log.d(TAG, "User name: " + ds.getName() + ", email " + ds.getEmail());

                System.out.println(">>>>>>>>>>>>>>>>>>>>>>" + ds.getFriday());

                serviceList = new ArrayList<>();
                serviceList.add(new Service("Monday",ds.getMonday(),"",R.drawable.thevigitarian));
                serviceList.add(new Service("Tuesday",ds.getTuesday(),"Description book",R.drawable.thewildrobot));
                serviceList.add(new Service("Wednesday",ds.getWednesday(),"Description book",R.drawable.mariasemples));
                serviceList.add(new Service("Thursday",ds.getThursday(),"Description book",R.drawable.themartian));
                serviceList.add(new Service("Friday",ds.getFriday(),"Description book",R.drawable.hediedwith));


                ScheduleRecyclerAdapter myAdapter = new ScheduleRecyclerAdapter(ScheduleActivity.this,serviceList);
                recyclerView.setLayoutManager(new LinearLayoutManager(ScheduleActivity.this));
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
