package com.xing.imhere.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;

import com.xing.imhere.R;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
//        Display display = getWindowManager().getDefaultDisplay();
//        if (display.getWidth() > display.getHeight()) {
//            Fragment1 fragment1 = new Fragment1();
//            getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment1).commit();
//        } else {
//            Fragment2 fragment2 = new Fragment2();
//            getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment2).commit();
//        }
    }

}
