package com.example.baecosmoclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.douglas.bean.DoctorSchedule;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostScheduleActivity extends AppCompatActivity {


    Button postSchedule;
    Spinner spinner1, spinner2, spinner3, spinner4, spinner5, spinner6, spinner7, spinner8, spinner9, spinner10;
    int mon1, mon2, tue1, tue2, wed1, wed2, thur1, thur2, fri1, fri2;
    FirebaseDatabase mDatabase;
    DatabaseReference mDatabaseReference;
    DoctorSchedule ds;

    Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_schedule);


        getIds();
        setListeners();
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference();





    }

    public void getIds()
    {
        spinner1 = (Spinner) findViewById(R.id.spinner_monday1);
        spinner2 = (Spinner) findViewById(R.id.spinner_monday2);
        spinner3 = (Spinner) findViewById(R.id.spinner_tue1);
        spinner4 = (Spinner) findViewById(R.id.spinner_tue2);
        spinner5 = (Spinner) findViewById(R.id.spinner_wed1);
        spinner6 = (Spinner) findViewById(R.id.spinner_wed2);
        spinner7 = (Spinner) findViewById(R.id.spinner_thur1);
        spinner8 = (Spinner) findViewById(R.id.spinner_thur2);
        spinner9 = (Spinner) findViewById(R.id.spinner_fri1);
        spinner10 = (Spinner) findViewById(R.id.spinner_fri2);
        postSchedule = findViewById(R.id.postSchedule);
    }

    public void getSpinnerData()
    {
        // get spinner data
        mon1 = Integer.parseInt(spinner1.getSelectedItem().toString());
        mon2 = Integer.parseInt(spinner2.getSelectedItem().toString());
        tue1 = Integer.parseInt(spinner3.getSelectedItem().toString());
        tue2 = Integer.parseInt(spinner4.getSelectedItem().toString());
        wed1 = Integer.parseInt(spinner5.getSelectedItem().toString());
        wed2 = Integer.parseInt(spinner6.getSelectedItem().toString());
        thur1 = Integer.parseInt(spinner7.getSelectedItem().toString());
        thur2 = Integer.parseInt(spinner8.getSelectedItem().toString());
        fri1 = Integer.parseInt(spinner9.getSelectedItem().toString());
        fri2 = Integer.parseInt(spinner10.getSelectedItem().toString());

    }

    public void setListeners()
    {
        postSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSpinnerData();

                mDatabaseReference = mDatabase.getReference().child("schedule");
                ds = new DoctorSchedule(mon1 + "-" + mon2, tue1 + "-" + tue2
                                        , wed1 + "-" + wed2, thur1 + "-" + thur2
                                        , fri1 + "-" + fri2);
                mDatabaseReference.setValue(ds);



                Snackbar snackbar = Snackbar
                        .make(findViewById(R.id.coordinatorLayout), "Schedule Posted", Snackbar.LENGTH_LONG);
                snackbar.show();

                snackbar.addCallback(new Snackbar.Callback() {

                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        //see Snackbar.Callback docs for event details

                        PostScheduleActivity.this.finish();
                    }

                    @Override
                    public void onShown(Snackbar snackbar) {

                    }
                });





            }
        });
    }
}
