package com.chaseswanstrom.sentiment;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    public Boolean isDone = false;
    TwitterManager t = new TwitterManager();
    static String queryWord = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sentimentButton = (Button) findViewById(R.id.buttonSentiment);

        if (sentimentButton != null) {
            sentimentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText qw = (EditText) findViewById(R.id.editTextQueryWord);
                    queryWord = qw.getText().toString();
                    tweetAsync ta = new tweetAsync();
                    ta.execute(queryWord);
                }

            });
        }

        if(isDone) {
            TextView tv = (TextView) findViewById(R.id.textViewScore);
                    tv.setText(t.totalScoreFinal.toString());
        }

    }

    public class tweetAsync extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                try {
                    t.performQuery(queryWord);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            isDone = true;
            return "Executed";
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }

        @Override
        protected void onPostExecute(String result) {

        }


    }
}
    /*Thread tweetThread = new Thread(new Runnable(){
        @Override
        public void run() {
            try {
                try {
                    t.performQuery(queryWord);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    });*/



