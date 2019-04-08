package com.example.pnw12.storenager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.pnw12.storenager.font.FontAwesome;
import com.example.pnw12.storenager.vo.ServerVO;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ManagerActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = "ManagerActivity";
    private TextView chumchrumNum, chammisuclassicNum, chammisufreshNum, cassNum, hiteNum, kloudNum;
    private TextView chumchrumDate, chammisuclassicDate, chammisufreshDate, cassDate, hiteDate, kloudDate;
    private Button btn1, btn2, btn3, btn4, btn5, btn6;
    private EditText et1, et2, et3, et4, et5, et6;
    private FontAwesome fab;
    int a;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.custom_actionbar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        setContentView(R.layout.activity_manager);

        init();
        ConnectServer();

        fab.setOnClickListener(this);
        btn1.setOnClickListener(this);//btn1 end
        btn2.setOnClickListener(this);//btn2 end
        btn3.setOnClickListener(this);//btn3 end
        btn4.setOnClickListener(this);//btn4 end
        btn5.setOnClickListener(this);//btn5 end
        btn6.setOnClickListener(this);//btn6 end

    }//onCreate end

    public void init() {
        fab = (FontAwesome) findViewById(R.id.btn_next);

        et1 = (EditText) findViewById(R.id.et_1);
        btn1 = (Button) findViewById(R.id.btn_1);

        et2 = (EditText) findViewById(R.id.et_2);
        btn2 = (Button) findViewById(R.id.btn_2);

        et3 = (EditText) findViewById(R.id.et_3);
        btn3 = (Button) findViewById(R.id.btn_3);

        et4 = (EditText) findViewById(R.id.et_4);
        btn4 = (Button) findViewById(R.id.btn_4);

        et5 = (EditText) findViewById(R.id.et_5);
        btn5 = (Button) findViewById(R.id.btn_5);

        et6 = (EditText) findViewById(R.id.et_6);
        btn6 = (Button) findViewById(R.id.btn_6);

        chumchrumNum = (TextView) findViewById(R.id.chumchrum_num);
        chumchrumDate = (TextView) findViewById(R.id.chumchrum_Date);
        chammisuclassicNum = (TextView) findViewById(R.id.chammisuclassic_num);
        chammisuclassicDate = (TextView) findViewById(R.id.chammisuclassic_Date);
        chammisufreshNum = (TextView) findViewById(R.id.chammisufresh_num);
        chammisufreshDate = (TextView) findViewById(R.id.chammisufresh_Date);
        cassNum = (TextView) findViewById(R.id.cass_num);
        cassDate = (TextView) findViewById(R.id.cass_Date);
        hiteNum = (TextView) findViewById(R.id.hite_num);
        hiteDate = (TextView) findViewById(R.id.hite_Date);
        kloudNum = (TextView) findViewById(R.id.kloud_num);
        kloudDate = (TextView) findViewById(R.id.kloud_Date);
    }

    public String createDay(String updayDate) {
        String date[] = updayDate.split("-");
        for (int i = 0; i < date.length; i++) {
            System.out.println("date[" + i + "] : " + date[i]);
        }

        return date[0] + "년" + date[1] + "월" + date[2] + "일";
    }//createDay end


    @Override
    public void onClick(View v) {
        Log.i(TAG, "onClick: true" + v.getId());
        switch (v.getId()) {
            case R.id.btn_next:
                break;
            case R.id.btn_1:
                break;
            case R.id.btn_2:
                break;
            case R.id.btn_3:
                break;
            case R.id.btn_4:
                break;
            case R.id.btn_5:
                break;
            case R.id.btn_6:
                break;
        }
    }


    private void ConnectServer() {
        final String SIGNIN_URL = ServerVO.IP + "/management/list";
        class ManagerList extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s != null) {
                    try {
                        JSONArray ja = new JSONArray(s);
                        for(int i = 0 ; i < ja.length() ; i++){
                            JSONObject jo = ja.getJSONObject(i);
                            Log.i(TAG, "onPostExecute: "+jo.getString("type"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(ManagerActivity.this, "서버와의 통신에 문제가 발생했습니다", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            protected String doInBackground(String... params) {
                BufferedReader bufferedReader = null;

                try {
                    HttpClient client = new DefaultHttpClient();

                    HttpGet get = new HttpGet(SIGNIN_URL);
                    HttpResponse response = client.execute(get);

                    BufferedReader bufreader = new BufferedReader(
                            new InputStreamReader(
                                    response.getEntity().getContent(), "utf-8"));

                    String line = null;
                    String page = "";

                    while ((line = bufreader.readLine()) != null) {
                        page += line;
                    }
                    return page;
                } catch (Exception e) {
                    return null;
                }
            }
        }

        ManagerList ml = new ManagerList();
        ml.execute(SIGNIN_URL);

    }//ConnectServer end

}//FragmentB end
