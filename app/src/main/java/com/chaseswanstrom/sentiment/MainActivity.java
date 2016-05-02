package com.chaseswanstrom.sentiment;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import twitter4j.HttpClient;
import twitter4j.HttpResponse;

public class MainActivity extends AppCompatActivity {
    private static final float ROTATE_FROM = 0.0f;
    private static final float ROTATE_TO = 10.0f * 360.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(5);
        gd.setStroke(4, 0xffffffff);

        TextView title = (TextView) findViewById(R.id.TextViewTitleMain);
        title.setBackground(gd);

        final ImageView imgSpinner = (ImageView) findViewById(R.id.imgSpinner);
        final RotateAnimation rotation = new RotateAnimation(ROTATE_FROM, ROTATE_TO, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotation.setInterpolator(new LinearInterpolator());
        rotation.setRepeatCount(Animation.INFINITE);
        rotation.setDuration(20000);
        imgSpinner.setVisibility(View.INVISIBLE);
        imgSpinner.setVisibility(View.VISIBLE);
        imgSpinner.startAnimation(rotation);

        int secondsDelayed = 3;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(MainActivity.this, SentimentActivity.class));
                finish();
            }
        }, secondsDelayed * 1000);
    }
}



