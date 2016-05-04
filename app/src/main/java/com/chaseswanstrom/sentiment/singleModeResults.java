package com.chaseswanstrom.sentiment;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;

import static com.chaseswanstrom.sentiment.R.id.TextViewTitle1;

public class singleModeResults extends AppCompatActivity implements Serializable{


    //    SentimentActivity s = new SentimentActivity();
//    TwitterManager t = new TwitterManager();
    battleActivity b = new battleActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_mode_results);

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(5);
        gd.setStroke(4, 0xffffffff);

        Intent intent = getIntent();
        Bundle extra = getIntent().getBundleExtra("extra");

        final ArrayList<String> tweetArray = (ArrayList<String>) getIntent().getExtras().getSerializable("tweetArray");

        TextView query1 = (TextView) findViewById(R.id.textViewQuery1term);
        query1.setBackground(gd);
        query1.setText(b.queryWord1.toUpperCase());
        TextView results1 = (TextView) findViewById(R.id.textView1results);
        results1.setText("Positive Tweets: " + intent.getStringExtra("positive").toString() + "\n"
                + "Neutral Tweets: " + intent.getStringExtra("neutral").toString() + "\n" + "Negative Tweets: " + intent.getStringExtra("negative").toString());



        Button topMashers = (Button) findViewById(R.id.buttonTM);
        topMashers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(singleModeResults.this, TopMashersActivity.class);
                Bundle extra = new Bundle();
                extra.putSerializable("objects", tweetArray);
                myIntent.putExtra("tweetArray", tweetArray);
                singleModeResults.this.startActivity(myIntent);
            }
        });

    }
}
