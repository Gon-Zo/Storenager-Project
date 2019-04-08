package com.example.pnw12.storenager.adpter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;


import com.example.pnw12.storenager.R;
import com.example.pnw12.storenager.ScheduleActivity;
import com.example.pnw12.storenager.vo.ListVO;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    private ArrayList<ListVO> listVO = new ArrayList<ListVO>();


    @Override
    public int getCount() {
        return listVO.size();
    }

    // ** 이 부분에서 리스트뷰에 데이터를 넣어줌 **
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //postion = ListView의 위치      /   첫번째면 position = 0
        final int pos = position;
        final Context context = parent.getContext();


        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_listview, parent, false);
        }

        final TextView title = (TextView) convertView.findViewById(R.id.title);
        final TextView Context = (TextView) convertView.findViewById(R.id.context);
        final TextView no = (TextView) convertView.findViewById(R.id.no);
        Button DeletBtn = (Button) convertView.findViewById(R.id.btn_num);


        final ListVO listViewItem = listVO.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        title.setText(listViewItem.getTitle());
        Context.setText(listViewItem.getContext());
        no.setText(listViewItem.getNo());


        DeletBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String test = listViewItem.getNo();
                ConnectServer(Integer.parseInt(test));

                Intent intent = new Intent(context, ScheduleActivity.class);
                context.startActivity(intent);


            }
        });


        //리스트뷰 클릭 이벤트
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* Toast.makeText(context, (pos+1)+"번째 리스트가 클릭되었습니다.", Toast.LENGTH_SHORT).show();*/

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
        return listVO.get(position);
    }

    // 데이터값 넣어줌
    public void addVO(String title, String desc, String no) {
        ListVO item = new ListVO();

        item.setNo(no);
        item.setTitle(title);
        item.setContext(desc);

        listVO.add(item);
    }

    public void updateReceiptsList(ArrayList<ListVO> newlist) {
        listVO.clear();

        this.notifyDataSetChanged();
    }

    private void ConnectServer(int num) {
        final String SIGNIN_URL = "http://{my Ip address}/deleteSchedule.jsp";
        final String urlSuffix = "" + num;


        Log.d("urlSuffix", urlSuffix);
        Log.d("test : ", SIGNIN_URL + urlSuffix);

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

