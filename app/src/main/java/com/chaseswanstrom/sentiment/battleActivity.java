package com.chaseswanstrom.sentiment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class battleActivity extends AppCompatActivity implements Serializable{


    //Animation points
    private static final float ROTATE1 = -36.0f;
    private static final float ROTATE2 = 36.0f;
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
        final Button retryButton = (Button) findViewById(R.id.retryButton);
        retryButton.setVisibility(View.INVISIBLE);
        final Button results = (Button) findViewById(R.id.buttonResults);
        results.setVisibility(View.INVISIBLE);
        final Button sentimentButton = (Button) findViewById(R.id.buttonSentiment);
        sentimentButton.setBackground(gd);
        final ImageView imgSpinner = (ImageView) findViewById(R.id.imgSpinner);
        imgSpinner.setVisibility(View.INVISIBLE);

        if (sentimentButton != null) {
            sentimentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText qw = (EditText) findViewById(R.id.editTextQueryWord);
                    final Button singleModeButton = (Button) findViewById(R.id.sentimentButtonMode);
                    singleModeButton.setVisibility(View.INVISIBLE);
                    queryWord1 = qw.getText().toString();
                    if (isNetworkAvailable() == true) {
                        tweetAsync ta = new tweetAsync();
                        ta.execute(queryWord1);
                        EditText qw2 = (EditText) findViewById(R.id.editTextQueryWord2);
                        queryWord2 = qw2.getText().toString();
                        tweetAsync ta2 = new tweetAsync();
                        ta2.execute(queryWord2);
                        sentimentButton.setVisibility(View.INVISIBLE);
                        imgSpinner.setVisibility(View.VISIBLE);
                        RotateAnimation squash = new RotateAnimation(ROTATE1, ROTATE2, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        squash.setDuration(500);
                        squash.setRepeatMode(Animation.REVERSE);
                        squash.setRepeatCount(Animation.INFINITE);
                        imgSpinner.startAnimation(squash);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Could not connect, please check Internet Connectivity",
                                Toast.LENGTH_LONG).show();
                    }
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

        retryButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent myIntent = new Intent(battleActivity.this, battleActivity.class);
                battleActivity.this.startActivity(myIntent);
            }
        });

    }

    public void sendMessage (View view){
        Intent intent = new Intent (this, battleActivity.class);
        startActivity(intent);
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
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

            final ArrayList<String> tweetArray = t.tweetArray;

            ImageView imgSpinner = (ImageView) findViewById(R.id.imgSpinner);
            imgSpinner.setAnimation(null);

            Button results = (Button) findViewById(R.id.buttonResults);
            results.setVisibility(View.VISIBLE);
            results.setBackground(gd);
            results.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), ResultsActivity.class);
                    intent.putExtra("positive", t.firstPosCount / 3 + "");
                    intent.putExtra("neutral", t.firstNeutralCount / 3 + "");
                    intent.putExtra("negative", t.firstNegCount / 3 + "");
                    intent.putExtra("positive2", t.secondPosCount / 3 + "");
                    intent.putExtra("neutral2", t.secondNeutralCount / 3 + "");
                    intent.putExtra("negative2", t.secondNegCount / 3 + "");
                    intent.putExtra("unPos1", t.unPos1);
                    intent.putExtra("dnPos1", t.dnPos1);
                    intent.putExtra("imgPos1", t.imgPos1);
                    intent.putExtra("unPos2", t.unPos2);
                    intent.putExtra("dnPos2", t.dnPos2);
                    intent.putExtra("imgPos2", t.imgPos2);
                    intent.putExtra("tweetPos1", t.tweetPos1);
                    intent.putExtra("tweetPos2", t.tweetPos2);


                    Bundle extra = new Bundle();
                    extra.putSerializable("objects", tweetArray);
                    intent.putExtra("tweetArray", tweetArray);
                    startActivity(intent);
                }
            });

            TextView tv = (TextView) findViewById(R.id.textViewScore);
            final Button retryButton = (Button) findViewById(R.id.retryButton);
            retryButton.setVisibility(View.VISIBLE);
            imgSpinner.setVisibility(View.INVISIBLE);
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


