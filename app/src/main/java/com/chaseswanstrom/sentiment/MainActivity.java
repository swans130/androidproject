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
    static String queryWord1 = "";
    static String queryWord2 = "";
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
                    queryWord1 = qw.getText().toString();
                    tweetAsync ta = new tweetAsync();
                    ta.execute(queryWord1);
                    EditText qw2 = (EditText) findViewById(R.id.editTextQueryWord2);
                    queryWord2 = qw2.getText().toString();
                    tweetAsync ta2 = new tweetAsync();
                    ta2.execute(queryWord2);
                }

            });
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
                    t.performQuery(queryWord1);
                    t.performQuery2(queryWord2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Executed";
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }

        @Override
        protected void onPostExecute(String result) {

            TextView tv = (TextView) findViewById(R.id.textViewScore);
            tv.setText(t.totalScoreFinal.toString());
            if(t.totalScoreFinal > t.totalScoreFinal2) {
                tv.setText(queryWord1.toString() + "Wins!");
                tv.setBackgroundColor(0xFF00FF00);
            }
            else {
                tv.setText(queryWord2.toString() + "Wins!");
                tv.setBackgroundColor(0xFF00FF00);
            }
        }

    }
}



