package com.example.baecosmoclinic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class BaeProfile extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bae_profile);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        final LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
        final TextView tv1 = (TextView) findViewById(R.id.profileTextView1);
        final TextView tv2 = (TextView) findViewById(R.id.profileTextView2);
        final TextView tv3 = (TextView) findViewById(R.id.profileTextView3);
        final TextView tv4 = (TextView) findViewById(R.id.profileTextView4);



        tv1.setText(getString(R.string.education));
        tv2.setText(getString(R.string.education_detail));
        tv3.setText(getString(R.string.contact));
        tv4.setText(getString(R.string.location_detail)+"\n"+getString(R.string.contact_detail));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==0){


                    tv1.setText(getString(R.string.education));
                    tv2.setText(getString(R.string.education_detail));
                    tv3.setText(getString(R.string.contact));
                    tv4.setText(getString(R.string.location_detail)+"\n"+getString(R.string.contact_detail));

                }
                else if(tab.getPosition()==1){


                    tv1.setText(getString(R.string.intro));
                    tv2.setText(getString(R.string.intro_detail));
                    tv3.setText("");
                    tv4.setText("");


                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
