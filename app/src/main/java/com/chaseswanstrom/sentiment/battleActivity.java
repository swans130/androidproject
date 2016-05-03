package com.chaseswanstrom.sentiment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class battleActivity extends AppCompatActivity {


    //Animation points
    private static final float ROTATE_FROM = 0.0f;
    private static final float ROTATE_TO = 10.0f * 360.0f;// 3.141592654f * 32.0f;

    public Boolean isDone = false;
    TwitterManager t = new TwitterManager();
    public static String queryWord1 = "";
    public static String queryWord2 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(5);
        gd.setStroke(4, 0xffffffff);

        final Button singleModeButton = (Button) findViewById(R.id.sentimentButtonMode);
        final Button results = (Button) findViewById(R.id.buttonResults);
        results.setVisibility(View.INVISIBLE);
        final Button topmashers = (Button) findViewById(R.id.buttontm);
        topmashers.setVisibility(View.INVISIBLE);
        final Button sentimentButton = (Button) findViewById(R.id.buttonSentiment);
        sentimentButton.setBackground(gd);
        final ImageView imgSpinner = (ImageView) findViewById(R.id.imgSpinner);
        final RotateAnimation rotation = new RotateAnimation(ROTATE_FROM, ROTATE_TO, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotation.setInterpolator(new LinearInterpolator());
        rotation.setRepeatCount(Animation.INFINITE);
        rotation.setDuration(20000);
        imgSpinner.setVisibility(View.INVISIBLE);

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
                    sentimentButton.setVisibility(View.INVISIBLE);
                    imgSpinner.setVisibility(View.VISIBLE);
                    imgSpinner.startAnimation(rotation);
                }

            });
        }
        singleModeButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent myIntent = new Intent(battleActivity.this, SentimentActivity.class);
                battleActivity.this.startActivity(myIntent);
            }
        });

    }

    public void sendMessage (View view){
        Intent intent = new Intent (this, battleActivity.class);
        startActivity(intent);
    }

    public class tweetAsync extends AsyncTask<String, Void, String> {

        ResultsActivity r = new ResultsActivity();

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

        @SuppressLint("NewApi")
        @Override
        protected void onPostExecute(String result) {

            //r.test = t.firstNeutralCount;

            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(5);
            gd.setStroke(4, 0xffffffff);

            ImageView imgSpinner = (ImageView) findViewById(R.id.imgSpinner);
            imgSpinner.setAnimation(null);

            Button results = (Button) findViewById(R.id.buttonResults);
            results.setVisibility(View.VISIBLE);
            results.setBackground(gd);
            results.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), ResultsActivity.class);
                    intent.putExtra("neutral", t.firstNeutralCount + "");
                    startActivity(intent);
                }
            });
            Button topmashers = (Button) findViewById(R.id.buttontm);
            topmashers.setVisibility(View.VISIBLE);
            topmashers.setBackground(gd);
            topmashers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), ResultsActivity.class);
                    intent.putExtra("neutral", t.firstNeutralCount + "");
                    startActivity(intent);
                }
            });

            final Button singleModeButton = (Button) findViewById(R.id.sentimentButtonMode);
            singleModeButton.setVisibility(View.INVISIBLE);

            TextView tv = (TextView) findViewById(R.id.textViewScore);
            tv.setText(t.totalScoreFinal.toString());
            if(t.totalScoreFinal > t.totalScoreFinal2) {
                tv.setText(queryWord1.toString().toUpperCase() + " WINS!");
                tv.setBackground(gd);
            }
            else {
                tv.setText(queryWord2.toString().toUpperCase() + " WINS!");
                tv.setBackground(gd);
            }
        }

    }
}


