package com.example.pnw12.storenager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class TitleActivity extends AppCompatActivity {

    private Button btnLoginView, btnJoinView;

    @Override
    protected void onCreate(Bundle saveIntentState) {
        super.onCreate(saveIntentState);
        setContentView(R.layout.activity_title);

        init();

        btnLoginView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(LoginActivity.class);
            }
        });//btnLoginView click end

        btnJoinView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(JoinActivity.class);
            }
        });//btnJoinView click end

    }//onCreate end

    public void init() {
        btnLoginView = (Button) findViewById(R.id.btnStart);
        btnJoinView = (Button) findViewById(R.id.btn_join);
    }

    public void goActivity(Class className) {
        Intent intent = new Intent(TitleActivity.this, className);
        startActivity(intent);
        finish();
    }

}//TitleActivity end
