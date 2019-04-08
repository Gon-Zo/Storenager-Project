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

import com.example.pnw12.storenager.VO.StoreUserVo;

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
                    //서버연결
                     insetPhone();
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


        selectList();




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


    private void insetPhone(){
        final String SIGNIN_URL = "http://{my Ip address}/insertPhone.jsp";
        final String urlSuffix = "?userNO="+ StoreUserVo.userNo+"&cname="+name+"&phoneNum="+phoneO;

        Log.d("urlSuffix", urlSuffix);
        Log.d("test : ",SIGNIN_URL+urlSuffix);

        class SignupUser extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {

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
        }//doInBackground end
        SignupUser su = new SignupUser();
        su.execute(urlSuffix);
    }//insetPhone end


    private void selectList() {
        final String SIGNIN_URL = "http://{my ip address }/selectPhone.jsp";
        final String urlSuffix = "?userNO=" + StoreUserVo.userNo;

        Log.d("urlSuffix", urlSuffix);
        Log.d("test : ", SIGNIN_URL + urlSuffix);
        class SignupUser extends AsyncTask<String, Void, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                /*Log.d(TAG,s);*/
                if (s != null)

                {
                    try {
                        JSONArray jArr = new JSONArray(s);

                        JSONObject json = new JSONObject();

                        for (int i = 0; i < jArr.length(); i++) {
                            json = jArr.getJSONObject(i);

                            String num = json.getString("num");
                            String cname = json.getString("cname");
                            String phoneNum = json.getString("phoneNum");

                            adapter.addVO( cname , phoneNum , num);
                            adapter.notifyDataSetChanged();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else

                {
//                    Toast.makeText(FragmentA.this, "서버와의 통신에 문제가 발생했습니다", Toast.LENGTH_SHORT).show();
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
    }//ConnectServer end*/



}//phoneActivity end
