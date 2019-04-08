package com.example.pnw12.storenager;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.se.omapi.Session;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pnw12.storenager.font.FontAwesome;
import com.example.pnw12.storenager.vo.ServerVO;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ProfileActivity extends AppCompatActivity {

    private String TAG = "ProfileActivity";
    private TextView putStoreName, putId, putName;
    private FontAwesome fab;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        try {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.custom_actionbar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        setContentView(R.layout.activity_profile);

        init();

        ConnectServer();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }//onCreate end

    public void init() {
        fab = (FontAwesome) findViewById(R.id.btn_next);
        putStoreName = (TextView) findViewById(R.id.out_storeName);
        putId = (TextView) findViewById(R.id.out_id);
        putName = (TextView) findViewById(R.id.out_name);
    }

    private void ConnectServer() {

        final String SIGNIN_URL = ServerVO.IP + "/profile";

        class ProfileView extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s != null) {
                    try {
                        JSONObject jo = new JSONObject(s);
                        settingProfile(
                            jo.getString("id"),
                            jo.getString("name"),
                            jo.getString("storeName")
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(ProfileActivity.this, "서버와의 통신에 문제가 발생했습니다", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            protected String doInBackground(String... params) {
                BufferedReader bufferedReader = null;

                try {

                    HttpClient client = new DefaultHttpClient();

                    HttpPost post = new HttpPost(SIGNIN_URL);
                    HttpResponse response = client.execute(post);

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

        ProfileView pf = new ProfileView();
        pf.execute(SIGNIN_URL);

    }//ConnectServer end

    public void settingProfile(String id , String name , String storeName){
        putId.setText(id);
        putName.setText(name);
        putStoreName.setText(storeName);
    }

}//ProfileActivity end