package com.example.pnw12.storenager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;

import com.example.pnw12.storenager.font.FontAwesome;

public class CalculatorActivity extends AppCompatActivity {

    EditText et_show,et_result;

    Button add,sub,mul,div,remainder,del;
    Button result;

    //이번 연산의 결과를 저장
    String history = "";
    //피연산자1
    String number1 = "";
    //피연산자2
    String number2 = "";

    //어떤 연산자가 선택되었는지 확인하기 위한 int형 type변수
    int type;

    int ADD = 0;
    int SUB = 1;
    int MUL = 2;
    int DIV = 3;
    int REMAINDER = 4;
    double d1;
    double d2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.custom_actionbar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        setContentView(R.layout.activity_calculator);


        FontAwesome fab = (FontAwesome)findViewById(R.id.btn_next);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalculatorActivity.this , MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });



        et_show = findViewById(R.id.et_show);
        et_result = findViewById(R.id.et_result);
        et_result.setText("");
        add = findViewById(R.id.btn_add);
        sub = findViewById(R.id.btn_sub);
        mul = findViewById(R.id.btn_mul);
        div = findViewById(R.id.btn_div);
        remainder = findViewById(R.id.btn_remainder);
        del = findViewById(R.id.btn_del);

        result = findViewById(R.id.btn_result);

        add.setOnClickListener(mListener);
        sub.setOnClickListener(mListener);
        mul.setOnClickListener(mListener);
        div.setOnClickListener(mListener);
        remainder.setOnClickListener(mListener);
        result.setOnClickListener(mListener);
        del.setOnClickListener(mListener);

        //초기화
        Button clear = findViewById(R.id.btn_clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_show.setText("");
                et_result.setText("");
                d1 = d2 = 0;
                history = number1 = number2 = "";
            }
        });

        Button plus_minus = findViewById(R.id.btn_plus_minus);
        plus_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //실수인지 정수인지 판단해서 부호 바꾸기
                if( ( (Double.parseDouble(et_result.getText().toString())) - ((int)Double.parseDouble(et_result.getText().toString())) ) == 0.0 )
                {
                    et_result.setText( "" + (Integer.parseInt(et_result.getText().toString()) * -1) );
                }
                else {
                    et_result.setText( "" + (Double.parseDouble(et_result.getText().toString()) * -1) );
                }

            }
        });
    }

    Button.OnClickListener mListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(et_result.getText().toString() == null)
            {
                /*Toast.makeText(CalculatorActivity.this,"수를 입력하세요",Toast.LENGTH_SHORT).show();*/
            }
            switch (v.getId()) {
                case R.id.btn_add :
                    number1 = et_result.getText().toString();
                    history = et_result.getText().toString() + " + ";
                    et_show.setText(history);
                    et_result.setText("");

                    type = ADD;
                    break;

                case R.id.btn_sub :
                    number1 = et_result.getText().toString();
                    history = et_result.getText().toString() + " - ";
                    et_show.setText(history);
                    et_result.setText("");

                    type = SUB;
                    break;

                case R.id.btn_mul :
                    number1 = et_result.getText().toString();
                    history = et_result.getText().toString() + " * ";
                    et_show.setText(history);
                    et_result.setText("");

                    type = MUL;
                    break;

                case R.id.btn_div :
                    number1 = et_result.getText().toString();
                    history = et_result.getText().toString() + " / ";
                    et_show.setText(history);
                    et_result.setText("");

                    type = DIV;
                    break;

                case R.id.btn_remainder :
                    number1 = et_result.getText().toString();
                    history = et_result.getText().toString() + " % ";
                    et_show.setText(history);
                    et_result.setText("");

                    type = REMAINDER;
                    break;

                case R.id.btn_del :

                    String del_number = et_result.getText().toString();
                   /* Toast.makeText(CalculatorActivity.this,del_number,Toast.LENGTH_SHORT).show();*/
                    et_result.setText(del_number.substring(0,del_number.length() - 1));
                    break;

                case R.id.btn_result :
                    double result = 0;
                   /* Toast.makeText(CalculatorActivity.this, "결과", Toast.LENGTH_SHORT).show();*/
                    number2 = et_result.getText().toString();
                    history = history + et_result.getText().toString();
                    et_show.setText(history);

                    d1 = Double.parseDouble(number1);
                    d2 = Double.parseDouble(number2);

                    if(type == ADD) {
                        result = d1 + d2;
                        et_result.setText("" + result);
                    } else if (type == SUB) {
                        result = d1 - d2;
                        et_result.setText("" + result);
                    }else if (type == MUL) {
                        result = d1 * d2;
                        et_result.setText("" + result);
                    }else if (type == DIV) {
                        result = d1 / d2;
                        et_result.setText("" + result);
                    }else if (type == REMAINDER) {
                        result = d1 % d2;
                        et_result.setText("" + result);
                    }

                    number1 = et_result.getText().toString();
                    break;
            }
        }
    };

    public void onClick (View v)
    {
        switch(v.getId()){
            case R.id.btn0 : et_result.setText(et_result.getText().toString() + 0); break;
            case R.id.btn1 : et_result.setText(et_result.getText().toString() + 1); break;
            case R.id.btn2 : et_result.setText(et_result.getText().toString() + 2); break;
            case R.id.btn3 : et_result.setText(et_result.getText().toString() + 3); break;
            case R.id.btn4 : et_result.setText(et_result.getText().toString() + 4); break;
            case R.id.btn5 : et_result.setText(et_result.getText().toString() + 5); break;
            case R.id.btn6 : et_result.setText(et_result.getText().toString() + 6); break;
            case R.id.btn7 : et_result.setText(et_result.getText().toString() + 7); break;
            case R.id.btn8 : et_result.setText(et_result.getText().toString() + 8); break;
            case R.id.btn9 : et_result.setText(et_result.getText().toString() + 9); break;
            case R.id.btndot : et_result.setText(et_result.getText().toString() + "."); break;
        }
    }
}
