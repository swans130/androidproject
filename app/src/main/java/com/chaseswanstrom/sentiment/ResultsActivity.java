package com.chaseswanstrom.sentiment;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;

import static com.chaseswanstrom.sentiment.R.id.TextViewTitle1;

public class ResultsActivity extends AppCompatActivity {


//    SentimentActivity s = new SentimentActivity();
//    TwitterManager t = new TwitterManager();
    battleActivity b = new battleActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(5);
        gd.setStroke(4, 0xffffffff);

        Intent intent = getIntent();

        TextView query1 = (TextView) findViewById(R.id.textViewQuery1term);
        query1.setBackground(gd);
        query1.setText(b.queryWord1.toUpperCase());
        TextView results1 = (TextView) findViewById(R.id.textView1results);
        results1.setText("Positive Tweets: " + intent.getStringExtra("positive").toString() + "\n"
                + "Neutral Tweets: " + intent.getStringExtra("neutral").toString() + "\n" + "Negative Tweets: " + intent.getStringExtra("negative").toString());

        TextView query2 = (TextView) findViewById(R.id.textViewQuery2term);
        query2.setBackground(gd);
        query2.setText(b.queryWord2.toUpperCase());
        TextView results2 = (TextView) findViewById(R.id.textView2results);
        results2.setText("Positive Tweets: " + intent.getStringExtra("positive2").toString() + "\n"
                + "Neutral Tweets: " + intent.getStringExtra("neutral2").toString() + "\n" + "Negative Tweets: " + intent.getStringExtra("negative2").toString());


    }
}
