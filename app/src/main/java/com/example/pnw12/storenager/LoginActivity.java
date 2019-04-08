package com.example.pnw12.storenager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pnw12.storenager.vo.ServerVO;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.Date;

public class LoginActivity extends AppCompatActivity {

    private String TAG = "LoginActivity";
    private Button loginBtn;
    private TextView signText;
    private EditText loginId, loginPwd;
    private FrameLayout lodingView;

    @Override
    protected void onCreate(Bundle saveIntentState) {
        super.onCreate(saveIntentState);
        setContentView(R.layout.activity_login);
        init();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lodingView.setVisibility(View.VISIBLE);

                ConnectServer(loginId.getText().toString(), loginPwd.getText().toString());

            }
        });//loginBtn click end


        signText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }//onCreate end


    public void init() {
        signText = (TextView) findViewById(R.id.tv_sign);
        loginId = (EditText) findViewById(R.id.login_id);
        loginPwd = (EditText) findViewById(R.id.login_pwd);
        lodingView = (FrameLayout) findViewById(R.id.loding_view);
        loginBtn = (Button) findViewById(R.id.btnLogin);
    }//init end


    private void ConnectServer(String ip, String pwd) {

        final String SIGNIN_URL = ServerVO.IP + "/login";
        final String param = "?id=" + ip + "&pwd=" + pwd;
        final String url = SIGNIN_URL + param;
        Log.i(TAG, "ConnectServer: "+url);
        class LoginUser extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s != null) {
                    try {
                        JSONObject json = new JSONObject(s);

                        if (json.getString("result").equals("true")) {
                            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Dialog();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "서버와의 통신에 문제가 발생했습니다", Toast.LENGTH_SHORT).show();
                }
                lodingView.setVisibility(View.GONE);

            }

            @Override
            protected String doInBackground(String... params) {
                BufferedReader bufferedReader = null;

                try {
                    HttpClient client = new DefaultHttpClient();

                    HttpPost post = new HttpPost(url);
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

        LoginUser lu = new LoginUser();
        lu.execute(param);

    }//ConnectServer end

    public void Dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage("잘못 입력 하셨습니다.");
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.show();
    }//Dialog end


}//LoginActivity end
