package com.chaseswanstrom.sentiment;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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



    //thread.start();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tweetThread.start();
    }

    Thread tweetThread = new Thread(new Runnable(){
        @Override
        public void run() {
            try {
                TwitterManager t = new TwitterManager();
                try {
                    t.performQuery("trump");
                    TextView tv = (TextView) findViewById(R.id.textViewScore);
                    //tv.setText(t.senti.toString());
                    //sentimentThread.start();

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
