package com.example.pnw12.storenager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

public class JoinActivity extends AppCompatActivity {

    private String TAG = "JoinActivity";

    private Button sueccessBtn;
    private TextView exitBtn;
    private EditText joinName, joinId, joinPwd, joinStoreName;

    @Override
    protected void onCreate(Bundle saveIntentState) {
        super.onCreate(saveIntentState);
        setContentView(R.layout.activity_join);

        init();

        sueccessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinUser(
                        joinName.getText().toString(),
                        joinId.getText().toString(),
                        joinPwd.getText().toString(),
                        joinStoreName.getText().toString()
                );
            }
        });//sueccessBtn click end


        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog();
            }
        });//exitBtn end


    }//onCreate end

    /**
     * @return void
     * @name init
     * @brief
     * @author park
     * @version 1.0
     */
    public void init() {
        joinName = (EditText) findViewById(R.id.join_name);
        joinId = (EditText) findViewById(R.id.join_id);
        joinPwd = (EditText) findViewById(R.id.join_pwd);
        joinStoreName = (EditText) findViewById(R.id.join_storeName);
        exitBtn = (TextView) findViewById(R.id.exitTv);
        sueccessBtn = (Button) findViewById(R.id.btn_finish);
    }//init end

    /**
     * @param name
     * @return void
     * @name joinUser
     * @brief
     * @author park
     * @version 1.0
     */
    private void joinUser(String name, String id, String pwd, String storeName) {

        final String SIGNIN_URL = ServerVO.IP + "/join";
        final String param = "?name=" + name + "&id=" + id + "&pwd=" + pwd + "&storeName=" + storeName;
        final String url = SIGNIN_URL + param;

        class ConnectionServer extends AsyncTask<String, Void, String> {
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

                        if (jo.getString("result").equals("true")) {
                            Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Dialog2();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            protected String doInBackground(String... params) {
                BufferedReader bufferedReader = null;
                try {

                    HttpClient client = new DefaultHttpClient();

                    HttpPost post = new HttpPost(url);
                    HttpResponse response = client.execute(post);

                    BufferedReader bufreader = new BufferedReader(
                            new InputStreamReader(response.getEntity().getContent(), "utf-8"));

                    String line = null;
                    String page = "";

                    while ((line = bufreader.readLine()) != null) {
                        page += line;
                    }
                    return page;

                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }

        ConnectionServer connect = new ConnectionServer();
        connect.execute(param);

    }//joinUser end


    public void Dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
        builder.setMessage("정말 가입을 안하실 건가요??");
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.setNegativeButton("종료",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        JoinActivity.this.finish();
                    }
                });
        builder.show();
    }//Dialog end

    public void Dialog2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
        builder.setMessage("다시입력하세요");
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.show();
    }//Dialog end

}//JoinActivity end
