package com.example.baecosmoclinic;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;

public class AdminHomeActivity extends AppCompatActivity {

    private ImageView logout;

    private FirebaseAuth mAuth;

    Button postSchedule, viewAppointments, sendNotitification, seeNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        //getActionBar().hide();


        getIds();
        setListeners();

        logout = findViewById(R.id.logout);

        mAuth = FirebaseAuth.getInstance();

        sendNotitification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHomeActivity.this, PushNotificationActivity.class));
            }
        });
        seeNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHomeActivity.this, NotificationActivity.class));
            }
        });
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
                                    Intent intent = new Intent(AdminHomeActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                    return;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(AdminHomeActivity.this);
                    builder.setMessage("Are you sure you want to logout?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();

                    //return;
                }

//                if (user != null){
//                    mAuth.signOut();
//                    Intent intent = new Intent(AdminHomeActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                    return;
//                }
//                //mAuth.signOut();
            }
        });

        postSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHomeActivity.this, PostScheduleActivity.class));
            }
        });

    }


  public void getIds()
    {
        viewAppointments = findViewById(R.id.viewAppointments);
        postSchedule = findViewById(R.id.postSchedule);
        sendNotitification = findViewById(R.id.sendNotifications);
        seeNotification = findViewById(R.id.seeNotification);
    }

    public void setListeners(){

        viewAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(AdminHomeActivity.this, ViewAppointmentsActivity.class));

            }
        });
    }
}
