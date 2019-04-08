package com.example.pnw12.storenager;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class PopupActivity extends Activity {

    TextView txtText;
    EditText test;
    String inputText;

    String data;
    String year;
    String month;
    String dayy;

    String[] time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_activity);

        //UI 객체생성
        txtText = (TextView)findViewById(R.id.txtText);
        test = (EditText)findViewById(R.id.test);


        //데이터 가져오기
        Intent intent = getIntent();
        data = ((Intent) intent).getStringExtra("data");

        try {
            for (int i = 0; i < 3; i++) {
                time = data.split(",");
                year = time[0];
                month = time[1];
                dayy = time[2];
            }
            data = year+"년 "+month+"월 "+dayy+"일 ";
        }catch (Exception e){
            e.printStackTrace();
            data = "날짜를 클릭하세요";
        }finally {
            txtText.setText(data);
        }

    }//onCreate end

    //확인 버튼 클릭
    public void mOnClose(View v){

        inputText = test.getText().toString();
        if(inputText.length() == 0)
        {
            Intent intent = new Intent(PopupActivity.this , ScheduleActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(PopupActivity.this , ScheduleActivity.class);
            startActivity(intent);
        }
    }//mOnClick end

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {

        //안드로이드 백버튼 막기
        return;
    }




}//PopupActivity end

