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

import com.example.pnw12.storenager.VO.StoreUserVo;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ManagerActivity extends AppCompatActivity {
    TextView chumchrumNum , chammisuclassicNum , chammisufreshNum , cassNum , hiteNum , kloudNum;
    TextView chumchrumDate , chammisuclassicDate , chammisufreshDate , cassDate , hiteDate , kloudDate;

    Button btn1 , btn2 ,btn3 , btn4 , btn5 , btn6;
    EditText et1 , et2 , et3 , et4 , et5 , et6;

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


       FontAwesome fab = (FontAwesome)findViewById(R.id.btn_next);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagerActivity.this , MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });


        setContentView(R.layout.activity_manager);


        chumchrumNum = (TextView)findViewById(R.id.chumchrum_num);
        chumchrumDate = (TextView)findViewById(R.id.chumchrum_Date);

        chammisuclassicNum = (TextView)findViewById(R.id.chammisuclassic_num);
        chammisuclassicDate = (TextView)findViewById(R.id.chammisuclassic_Date);


        chammisufreshNum = (TextView)findViewById(R.id.chammisufresh_num);
        chammisufreshDate = (TextView)findViewById(R.id.chammisufresh_Date);


        cassNum = (TextView)findViewById(R.id.cass_num);
        cassDate = (TextView)findViewById(R.id.cass_Date);


        hiteNum = (TextView)findViewById(R.id.hite_num);
        hiteDate = (TextView)findViewById(R.id.hite_Date);

        kloudNum = (TextView)findViewById(R.id.kloud_num);
        kloudDate = (TextView)findViewById(R.id.kloud_Date);



        ConnectServer();




        et1 = (EditText)findViewById(R.id.et_1);
        btn1 =(Button)findViewById(R.id.chumchrum_up);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a = Integer.parseInt(chumchrumNum.getText().toString()) - Integer.parseInt(et1.getText().toString());
                updateBtn1();
                String setNum = String.valueOf(a);
                et1.setText(null);
                chumchrumNum.setText(setNum);
            }
        });//btn1 end

        et2 = (EditText)findViewById(R.id.et_2);
        btn2 =(Button)findViewById(R.id.btn_2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a = Integer.parseInt(chammisuclassicNum.getText().toString()) - Integer.parseInt(et2.getText().toString());
                updateBtn2();
                String setNum = String.valueOf(a);
                et2.setText(null);
                chammisuclassicNum.setText(setNum);
            }
        });//btn2 end


        et3 = (EditText)findViewById(R.id.et_3);
        btn3 =(Button)findViewById(R.id.btn_3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a = Integer.parseInt(chammisufreshNum.getText().toString()) - Integer.parseInt(et3.getText().toString());
                updateBtn3();
                String setNum = String.valueOf(a);
                et3.setText(null);
                chammisufreshNum.setText(setNum);
            }
        });//btn3 end

        et4 = (EditText)findViewById(R.id.et_4);
        btn4 =(Button)findViewById(R.id.btn_4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a = Integer.parseInt(cassNum.getText().toString()) - Integer.parseInt(et4.getText().toString());
                updateBtn4();
                String setNum = String.valueOf(a);
                et4.setText(null);
                cassNum.setText(setNum);
            }
        });//btn4 end

        et5 = (EditText)findViewById(R.id.et_5);
        btn5 =(Button)findViewById(R.id.btn_5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a = Integer.parseInt(hiteNum.getText().toString()) - Integer.parseInt(et5.getText().toString());
                updateBtn5();
                String setNum = String.valueOf(a);
                et5.setText(null);
                hiteNum.setText(setNum);
            }
        });//btn5 end

        et6 = (EditText)findViewById(R.id.et_6);
        btn6 =(Button)findViewById(R.id.btn_6);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a = Integer.parseInt(kloudNum.getText().toString()) - Integer.parseInt(et6.getText().toString());
                updateBtn6();
                String setNum = String.valueOf(a);
                et6.setText(null);
                kloudNum.setText(setNum);
            }
        });//btn6 end

    }//onCreate end






    private void ConnectServer(){

        final String SIGNIN_URL = "http://{my Ip address}/manage.jsp";
        final String urlSuffix = "?userNO="+ StoreUserVo.userNo;
        Log.d("urlSuffix", urlSuffix);
        Log.d("test : ",SIGNIN_URL+urlSuffix);
        class SignupUser extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                /*     Log.d(TAG,s);*/
                if (s != null) {
                    try{

                        JSONArray jArr = new JSONArray(s);;
                        JSONObject json = new JSONObject();

                        for (int i = 0; i < jArr.length(); i++) {
                            json = jArr.getJSONObject(i);
                            chumchrumNum.setText(json.getString("chumchrum"));
                            chumchrumDate.setText(createDay(json.getString("chumchrumDate")));

                            chammisuclassicNum.setText(json.getString("chammisuclassic"));
                            chammisuclassicDate.setText(createDay(json.getString("chammisuclassicDate")));

                            chammisufreshNum.setText(json.getString("chammisufresh"));
                            chammisufreshDate.setText(createDay(json.getString("chammisufreshDate")));

                            cassNum.setText(json.getString("cass"));
                            cassDate.setText(createDay(json.getString("cassDate")));

                            hiteNum.setText(json.getString("hite"));
                            hiteDate.setText(createDay(json.getString("hiteDate")));

                            kloudNum.setText(json.getString("kloud"));
                            kloudDate.setText(createDay(json.getString("kloudDate")));
                        }

                    }catch(Exception e){
                        e.printStackTrace();
                    }
                } else {
                }
            }

            @Override
            protected String doInBackground(String... params) {
                BufferedReader bufferedReader = null;

                try {

                    HttpClient client = new DefaultHttpClient();  // 보낼 객체 만들기
                    HttpPost post = new HttpPost(SIGNIN_URL + urlSuffix);  // 주소 뒤에 데이터를 넣기

                    HttpResponse response = client.execute(post); // 데이터 보내기

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
        SignupUser su = new SignupUser();
        su.execute(urlSuffix);
    }//ConnectServer end

    public String createDay(String updayDate){
        String date[] = updayDate.split("-");
        for(int i=0 ; i<date.length ; i++)
        {
            System.out.println("date["+i+"] : "+date[i]);
        }

        return date[0]+"년"+date[1]+"월"+date[2]+"일";
    }//createDay end

    private void updateBtn1(){

        final String SIGNIN_URL = "http://{my ip address}/updateChumchrum.jsp";
        final String urlSuffix = "?userNO="+StoreUserVo.userNo+"&chumchrumNum="+ a;

        Log.d("urlSuffix", urlSuffix);
        Log.d("test : ",SIGNIN_URL+urlSuffix);
        class SignupUser extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(String... params) {
                BufferedReader bufferedReader = null;

                try {

                    HttpClient client = new DefaultHttpClient();  // 보낼 객체 만들기
                    HttpPost post = new HttpPost(SIGNIN_URL + urlSuffix);  // 주소 뒤에 데이터를 넣기

                    HttpResponse response = client.execute(post); // 데이터 보내기

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

        SignupUser su = new SignupUser();
        su.execute(urlSuffix);
    }//updateBtn1 end

    private void updateBtn2(){

        final String SIGNIN_URL = "http://182.216.6.192/updateChammisuclassic.jsp";
        final String urlSuffix = "?userNO="+StoreUserVo.userNo+"&chammisuclassicNum="+ a;

        Log.d("urlSuffix", urlSuffix);
        Log.d("test : ",SIGNIN_URL+urlSuffix);
        class SignupUser extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(String... params) {
                BufferedReader bufferedReader = null;

                try {

                    HttpClient client = new DefaultHttpClient();  // 보낼 객체 만들기
                    HttpPost post = new HttpPost(SIGNIN_URL + urlSuffix);  // 주소 뒤에 데이터를 넣기

                    HttpResponse response = client.execute(post); // 데이터 보내기

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

        SignupUser su = new SignupUser();
        su.execute(urlSuffix);
    }//updateBtn2 end

    private void updateBtn3(){

        final String SIGNIN_URL = "http://182.216.6.192/updateChammisufresh.jsp";
        final String urlSuffix = "?userNO="+StoreUserVo.userNo+"&chammisufreshNum="+ a;

        Log.d("urlSuffix", urlSuffix);
        Log.d("test : ",SIGNIN_URL+urlSuffix);
        class SignupUser extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(String... params) {
                BufferedReader bufferedReader = null;

                try {

                    HttpClient client = new DefaultHttpClient();  // 보낼 객체 만들기
                    HttpPost post = new HttpPost(SIGNIN_URL + urlSuffix);  // 주소 뒤에 데이터를 넣기

                    HttpResponse response = client.execute(post); // 데이터 보내기

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

        SignupUser su = new SignupUser();
        su.execute(urlSuffix);
    }//updateBtn3 end

    private void updateBtn4(){

        final String SIGNIN_URL = "http://182.216.6.192/updateCass.jsp";
        final String urlSuffix = "?userNO="+StoreUserVo.userNo+"&cassNum="+ a;

        Log.d("urlSuffix", urlSuffix);
        Log.d("test : ",SIGNIN_URL+urlSuffix);
        class SignupUser extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(String... params) {
                BufferedReader bufferedReader = null;

                try {

                    HttpClient client = new DefaultHttpClient();  // 보낼 객체 만들기
                    HttpPost post = new HttpPost(SIGNIN_URL + urlSuffix);  // 주소 뒤에 데이터를 넣기

                    HttpResponse response = client.execute(post); // 데이터 보내기

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

        SignupUser su = new SignupUser();
        su.execute(urlSuffix);
    }//updateBtn4 end

    private void updateBtn5(){

        final String SIGNIN_URL = "http://182.216.6.192/updateHite.jsp";
        final String urlSuffix = "?userNO="+StoreUserVo.userNo+"&hiteNum="+ a;

        Log.d("urlSuffix", urlSuffix);
        Log.d("test : ",SIGNIN_URL+urlSuffix);
        class SignupUser extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(String... params) {
                BufferedReader bufferedReader = null;

                try {

                    HttpClient client = new DefaultHttpClient();  // 보낼 객체 만들기
                    HttpPost post = new HttpPost(SIGNIN_URL + urlSuffix);  // 주소 뒤에 데이터를 넣기

                    HttpResponse response = client.execute(post); // 데이터 보내기

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

        SignupUser su = new SignupUser();
        su.execute(urlSuffix);
    }//updateBtn5 end

    private void updateBtn6(){

        final String SIGNIN_URL = "http://182.216.6.192/updateKloud.jsp";
        final String urlSuffix = "?userNO="+StoreUserVo.userNo+"&kloudNum="+ a;

        Log.d("urlSuffix", urlSuffix);
        Log.d("test : ",SIGNIN_URL+urlSuffix);
        class SignupUser extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(String... params) {
                BufferedReader bufferedReader = null;

                try {

                    HttpClient client = new DefaultHttpClient();  // 보낼 객체 만들기
                    HttpPost post = new HttpPost(SIGNIN_URL + urlSuffix);  // 주소 뒤에 데이터를 넣기

                    HttpResponse response = client.execute(post); // 데이터 보내기

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

        SignupUser su = new SignupUser();
        su.execute(urlSuffix);
    }//updateBtn6 end


}//FragmentB end
