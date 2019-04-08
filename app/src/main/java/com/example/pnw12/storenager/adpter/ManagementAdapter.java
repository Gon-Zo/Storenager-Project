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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pnw12.storenager.ManagerActivity;
import com.example.pnw12.storenager.R;
import com.example.pnw12.storenager.ScheduleActivity;
import com.example.pnw12.storenager.vo.ListVO;
import com.example.pnw12.storenager.vo.ManagementVO;
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
import java.util.ArrayList;

public class ManagementAdapter extends BaseAdapter {

    private ArrayList<ManagementVO> managementVO = new ArrayList<ManagementVO>();
    private TextView name, num, day;
    private Button deleteBtn;
    private EditText et;
    private View convertView;
    private String TAG = "ManagementAdapter";
    @Override
    public int getCount() {
        return managementVO.size();
    }

    // ** 이 부분에서 리스트뷰에 데이터를 넣어줌 **
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //postion = ListView의 위치      /   첫번째면 position = 0
        final int pos = position;
        final Context context = parent.getContext();


        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.management_layout, parent, false);
            this.convertView = convertView;
        }
        init();

        final ManagementVO manItem = managementVO.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        name.setText(manItem.getName());
        num.setText(manItem.getNum());
        day.setText(manItem.getDay());

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String test = manItem.getNo();
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
        return managementVO.get(position);
    }

    // 데이터값 넣어줌
    public void addVO(String name, String day, String num) {
        managementVO.add(new ManagementVO(name, day, num));
    }

    public void updateReceiptsList(ArrayList<ListVO> newlist) {
        managementVO.clear();
        this.notifyDataSetChanged();
    }

    public void init() {
        name = (TextView) convertView.findViewById(R.id.name);
        num = (TextView) convertView.findViewById(R.id.num);
        day = (TextView) convertView.findViewById(R.id.day);
        et = (EditText) convertView.findViewById(R.id.et);
        deleteBtn = (Button) convertView.findViewById(R.id.btn);
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
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e(TAG, "onPostExecute: server is error");
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
}
