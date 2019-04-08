package com.example.pnw12.storenager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import com.example.pnw12.storenager.adpter.PhoneViewAdapter;
import com.example.pnw12.storenager.font.FontAwesome;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class PhonebookActivity extends AppCompatActivity {

    EditText nameI , numI;

    private ListView listview;
    private PhoneViewAdapter adapter;

    Button inputBtn;


    String name;
    String phoneO;


    @Override
    public void onCreate(Bundle saveIntentState){
        super.onCreate(saveIntentState);

        try {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.custom_actionbar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        setContentView(R.layout.activity_phone);


        FontAwesome fab = (FontAwesome)findViewById(R.id.btn_next);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhonebookActivity.this , MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });



        nameI = (EditText)findViewById(R.id.et_name);
        numI = (EditText)findViewById(R.id.et_phone);


        inputBtn = (Button)findViewById(R.id.btn_inPohon);
        inputBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = nameI.getText().toString();
                phoneO = numI.getText().toString();

                 int checkNum = phoneO.length();

                 if(checkNum == 13){
                     Intent intent = new Intent(PhonebookActivity.this,PhonebookActivity.class);
                     startActivity(intent);
                     finish();
                 }else{
                    Dialog();
                 }


            }
        });//btn end


        adapter = new PhoneViewAdapter();
        listview = (ListView) findViewById(R.id.list_view2);
        //어뎁터 할당
        listview.setAdapter(adapter);


    }//onCreaten end


    public void Dialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(PhonebookActivity.this);
        builder.setMessage("번호에 - 를 넣어주세요");
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.show();
    }//Dialog end



}//phoneActivity end
