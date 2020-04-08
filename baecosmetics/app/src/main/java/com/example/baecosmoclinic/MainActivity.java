package com.example.baecosmoclinic;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.fragments.FragmentOne;
import com.fragments.FragmentThree;
import com.fragments.FragmentTwo;

public class MainActivity  extends FragmentActivity implements FragmentOne.OnFragmentInteractionListener, FragmentTwo.OnFragmentInteractionListener, FragmentThree.OnFragmentInteractionListener {

    private Button login,register;
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int NUM_PAGES = 3;
    private ViewPager mPager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.btn_sign_in);
        register = findViewById(R.id.btn_register);

        mPager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new MainActivity.ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                //finish();
                return;
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                //finish();
                return;
            }
        });
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0)
            {
                return new FragmentOne();
            }
            else if (position == 1)
            {
                return new FragmentTwo();
            }
            else
            {
                return new FragmentThree();

            }

        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}