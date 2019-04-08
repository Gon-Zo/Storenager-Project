package com.example.pnw12.storenager;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pnw12.storenager.font.FontAwesome;
import com.example.pnw12.storenager.vo.ServerVO;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CamActivity extends Activity {

    private BarcodeDetector barcodeDetector;
    private FontAwesome faBack;
    private TextView tv;
    private CameraSource cameraSource;
    private SurfaceView cameraSurface;
    private String barcodeContents;
    private ImageView lodingView;

    String chc, chf, chm, hite, cass, kloud;

    int cnt = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam);

        init();

        cameraSurface = (SurfaceView) findViewById(R.id.cam); // SurfaceView 선언 :: Boilerplate


        lodingView = (ImageView) findViewById(R.id.gif_image);
        Glide.with(this).load(R.drawable.load).into(lodingView);


        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE) // QR_CODE로 설정하면 좀더 빠르게 인식할 수 있습니다.
                .build();

        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedFps(29.8f) // 프레임 높을 수록 리소스를 많이 먹겠죠
                .setRequestedPreviewSize(1080, 1920)    // 확실한 용도를 잘 모르겠음. 필자는 핸드폰 크기로 설정
                .setAutoFocusEnabled(true)  // AutoFocus를 안하면 초점을 못 잡아서 화질이 많이 흐립니다.
                .build();
        Log.d("NowStatus", "CameraSource Build Complete");

        // Callback을 이용해서 SurfaceView를 실시간으로 Mobile Vision API와 연결
        cameraSurface.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {   // try-catch 문은 Camera 권한획득을 위한 권장사항
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                            == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(cameraSurface.getHolder());  // Mobile Vision API 시작
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();    // SurfaceView가 종료되었을 때, Mobile Vision API 종료
                Log.d("NowStatus", "SurfaceView Destroyed and CameraSource Stopped");
            }
        });//cameraSurface end


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {

            @Override
            public void release() {
                Log.d("NowStatus", "BarcodeDetector SetProcessor Released");
            }//release end

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                // 바코드가 인식되었을 때 무슨 일을 할까?
                final SparseArray<Barcode> qrCode = detections.getDetectedItems();

                if (qrCode.size() != 0) {
                    tv.post(new Runnable() {
                        @Override
                        public void run() {
                            cameraSource.stop();
                            lodingView.setVisibility(View.VISIBLE);

                            barcodeContents = qrCode.valueAt(0).displayValue;
                            qrCode.clear();


                            cnt++;
                            if (cnt == 1) {
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        lodingView.setVisibility(View.GONE);
                                        // return;
                                        Dialog();

                                    }
                                }, 5000);
                            }


                            Log.d("Detection", barcodeContents);

                        }
                    }); // tv.post end
                }//if end*/

            }//receiveDetections end
        });//barcodeDetector end


        faBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CamActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }//onCreate end

    public void init() {
        tv = (TextView) findViewById(R.id.tv);
        faBack = (FontAwesome) findViewById(R.id.btn_back);
        cameraSurface = (SurfaceView) findViewById(R.id.cam);
    }

    private void ConnectServer() {
        // http://localhost/management/update?num=150&type=%EC%B2%98%EC%9D%8C%EC%B2%98%EB%9F%BC&state=P
        final String SIGNIN_URL = ServerVO.IP + "/management/update";
        final String param = "?num=" + 15 + "&type=" + "참이슬" + "&state=" + "P";

        class UpdateManagement extends AsyncTask<String, Void, String> {

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

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(CamActivity.this, "서버와의 통신에 문제가 발생했습니다", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                BufferedReader bufferedReader = null;

                try {

                    HttpClient client = new DefaultHttpClient();
                    HttpPost post = new HttpPost(SIGNIN_URL + param);

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
        UpdateManagement um = new UpdateManagement();
        um.execute(param);
    }//ConnectServer end

    public void Dialog() {
        ConnectServer();
        final CamUpdate update = new CamUpdate();
        update.createName(barcodeContents);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        lodingView.setVisibility(View.GONE);

        builder.setTitle("목록 : " + update.name + " 수량 : " + update.numString);
        builder.setMessage("업데이트 하시겠습니까? ");
        builder.setPositiveButton("업데이트",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        barcodeContents = null;
                        cnt = 0;
                        if (ActivityCompat.checkSelfPermission(CamActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ConnectServer();

                            return;
                        }//ckeck cam end
                        try {
                            cameraSource.start(cameraSurface.getHolder());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }//trt catch end
                    }
                });
        builder.setNegativeButton("아니요",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        barcodeContents = null;
                        cnt = 0;
                        if (ActivityCompat.checkSelfPermission(CamActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }//ckeck cam end
                        try {
                            cameraSource.start(cameraSurface.getHolder());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }//trt catch end
                    }
                });
        builder.show();

    }//Dialog end

}//CamActivity end