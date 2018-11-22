package com.quickcheck;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.quickcheck.preference.preferance;
import com.skyfishjy.library.RippleBackground;
import com.squareup.picasso.Picasso;
import com.quickcheck.preference.preferance;
import com.quickcheck.webservice.Serverfuction;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    ImageView imageView;
    ProgressBar progressBar;
    private Handler handler = new Handler();
    private int total = 0;
    private CountDownTimer cdt;
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        progressBar = findViewById(R.id.progressBar);
        imageView = findViewById(R.id.centerImage);
        // setProgressData();

        //  progressBar = (ProgressBar) findViewById(R.id.progress);

        progressBar.setProgress(i);
        cdt = new CountDownTimer(2800, 26) {

            @Override
            public void onTick(long millisUntilFinished) {
                i++;
            //   progressBar.setProgress((int) i * 100 / (3000 / 18));
                Log.d("i ",i+"");
                progressBar.setProgress((int) (i) );

            }

            @Override
            public void onFinish() {
                //Do what you want

                progressBar.setProgress(100);
                String user_id = null;
                user_id = preferance.getInstance(getApplicationContext()).getUserId();
                if (user_id == "") {
                    Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                    i.putExtra("strtem", "1");
                    startActivity(i);
                    overridePendingTransition(0, 0);
                } else {
                    Intent i = new Intent(SplashScreen.this, HomeActivity.class);
                    i.putExtra("strtem", "1");
                    startActivity(i);
                    overridePendingTransition(0, 0);
                }
                // close this activity
                finish();
            }
        };
        cdt.start();


    }


    private void setProgressData() {
        progressBar.setMax(100);
        progressBar.setProgress(total);
        final int oneMin = 3 * 1000; // 1 minute in milli seconds

        /** CountDownTimer starts with 1 minutes and every onTick is 1 second */
        cdt = new CountDownTimer(oneMin, 40) {

            public void onTick(long millisUntilFinished) {
                int progress = (int) (3000 - millisUntilFinished) / 30;
                Log.d("progress", progress + "");
                progressBar.setProgress(progress);
                if (progress == 98 || progress == 99) {
                    Log.d("finished", progressBar.getProgress() + "");
                    String user_id = null;
                    user_id = preferance.getInstance(getApplicationContext()).getUserId();
                    if (user_id == "") {
                        Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                        i.putExtra("strtem", "1");
                        startActivity(i);
                        overridePendingTransition(0, 0);
                    } else {
                        Intent i = new Intent(SplashScreen.this, HomeActivity.class);
                        i.putExtra("strtem", "1");
                        startActivity(i);
                        overridePendingTransition(0, 0);
                    }
                    // close this activity
                    finish();
                }
            }

            @Override
            public void onFinish() {
            }


        }.start();

    }


}


