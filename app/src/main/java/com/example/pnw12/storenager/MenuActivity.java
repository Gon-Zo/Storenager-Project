package com.example.pnw12.storenager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

public class MenuActivity extends AppCompatActivity {

    private FrameLayout faBtn1, faBtn2, faBtn3, faBtn4, faBtn5, faBtn6;

    @Override
    protected void onCreate(Bundle saveIntentState) {
        super.onCreate(saveIntentState);
        setContentView(R.layout.activity_menu);

        init();

        faBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(ProfileActivity.class);
            }
        });

        faBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(CamActivity.class);
            }
        });

        faBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(ManagerActivity.class);
            }
        });

        faBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(ScheduleActivity.class);
            }
        });

        faBtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(PhonebookActivity.class);
            }
        });

        faBtn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(CalculatorActivity.class);
            }
        });

    }//onCreate end

    public void init() {
        faBtn1 = (FrameLayout) findViewById(R.id.fBtn_1);
        faBtn2 = (FrameLayout) findViewById(R.id.fBtn_2);
        faBtn3 = (FrameLayout) findViewById(R.id.fBtn_3);
        faBtn4 = (FrameLayout) findViewById(R.id.fBtn_4);
        faBtn5 = (FrameLayout) findViewById(R.id.fBtn_5);
        faBtn6 = (FrameLayout) findViewById(R.id.fBtn_6);
    }

    public void goActivity(Class className) {
        Intent intent = new Intent(MenuActivity.this, className);
        startActivity(intent);
        finish();
    }


}//menuActivity_ end
