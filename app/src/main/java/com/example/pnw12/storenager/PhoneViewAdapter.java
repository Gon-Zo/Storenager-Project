package com.example.pnw12.storenager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.pnw12.storenager.VO.PhoneVO;
import com.example.pnw12.storenager.VO.StoreUserVo;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PhoneViewAdapter extends BaseAdapter {

    private ArrayList<PhoneVO> phoneVO = new ArrayList<PhoneVO>();


    @Override
    public int getCount() {
        return phoneVO.size();
    }

    // ** 이 부분에서 리스트뷰에 데이터를 넣어줌 **
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //postion = ListView의 위치      /   첫번째면 position = 0
        final int pos = position;
        final Context context = parent.getContext();


        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.phone_listview, parent, false);
        }

        final TextView name = (TextView) convertView.findViewById(R.id.name);
        final TextView number = (TextView) convertView.findViewById(R.id.number);
        final TextView no = (TextView) convertView.findViewById(R.id.no_phone);
        Button DeletBtn = (Button) convertView.findViewById(R.id.btn_delete);


        final PhoneVO listViewItem = phoneVO.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        name.setText(listViewItem.getName());
        number.setText(listViewItem.getNumber());
        no.setText(listViewItem.getNo());


        DeletBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String test = listViewItem.getNo();
                deletePhon(Integer.parseInt(test));
                Intent intent = new Intent(context, PhonebookActivity.class);
                context.startActivity(intent);


            }
        });


        //리스트뷰 클릭 이벤트
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tel = "tel:"+listViewItem.getNumber();
                context.startActivity(new Intent("android.intent.action.DIAL", Uri.parse(tel)));
            }
        });
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public Object getItem(int position) {
        return phoneVO.get(position);
    }

    // 데이터값 넣어줌
    public void addVO(String title, String desc, String no) {
        PhoneVO item = new PhoneVO();

        item.setNo(no);
        item.setName(title);
        item.setNumber(desc);

        phoneVO.add(item);
    }



    private void deletePhon(int num){
        final String SIGNIN_URL = "http://{my Ip address}/deletePhone.jsp";
        final String urlSuffix = "?userNO="+ StoreUserVo.userNo+"&num="+num;


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

    }//ConnectServer end





}

