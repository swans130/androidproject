package com.chaseswanstrom.sentiment;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
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

    TwitterManager t = new TwitterManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tweetThread.start();

        Button sentimentButton = (Button) findViewById(R.id.buttonSentiment);
 //       sentimentButton.setOnClickListener();
//        TextView tv = (TextView) findViewById(R.id.textViewScore);
//        tv.setText(t.totalScoreFinal.toString());
    }

    Thread tweetThread = new Thread(new Runnable(){
        @Override
        public void run() {
            try {
                try {
                    t.performQuery("trump");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });



}
