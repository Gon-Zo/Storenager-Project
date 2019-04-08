package com.example.pnw12.storenager;


import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CamUpdate {

    String name;
    String numString;


    public void createName(String qrCodeDate) {
        int check = qrCodeDate.indexOf(",");

        if (check != -1) {
            String date[] = qrCodeDate.split(",");
            name = date[0];
            numString = date[1];
        }

    }//createDay end

}