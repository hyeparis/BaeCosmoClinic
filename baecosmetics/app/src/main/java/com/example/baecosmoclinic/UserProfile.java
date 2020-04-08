package com.example.baecosmoclinic;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bean.TimeSlots;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;

public class UserProfile extends AppCompatActivity {

    private ImageView logout;

    private FirebaseAuth mAuth;
    //String userEmail;
    TextView txtAccount;
    Button btnManageProfile;

    Button btnSeeAppoingment;

    FirebaseDatabase mDatabase;
    DatabaseReference mDatabaseReference;

    Boolean empty = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Intent i = getIntent();
        //userEmail = i.getStringExtra("userEmail");

        txtAccount = findViewById(R.id.txtAccount);
        btnManageProfile = findViewById(R.id.btnEdit);

        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference();

        btnSeeAppoingment = findViewById(R.id.btnSeeAppoingment);


        String name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        System.out.println("======================================" + name);
        txtAccount.setText(name);

        //txtAccount.setText(userEmail);

        mAuth = FirebaseAuth.getInstance();
        logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();//whenever user logs in, this will be called
                if (user != null){
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    mAuth.signOut();
                                    Intent intent = new Intent(UserProfile.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(UserProfile.this);
                    builder.setMessage("Are you sure you want to logout?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();

                    //return;
                }
            }
        });

        btnManageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfile.this,ManageProfile.class);
                startActivity(intent);
            }
        });

        btnSeeAppoingment.setOnClickListener(new View.OnClickListener() {
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
                            if(booking!=null) {
                                startActivity(new Intent(UserProfile.this, ViewClientAppointmentsActivity.class));
                            }

                            }



                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        //   Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
            }
        });




    }
}
