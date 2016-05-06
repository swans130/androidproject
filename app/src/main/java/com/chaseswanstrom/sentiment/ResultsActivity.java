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

import java.util.ArrayList;

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
        Bundle extra = getIntent().getBundleExtra("extra");

        final ArrayList<String> tweetArray = (ArrayList<String>) getIntent().getExtras().getSerializable("tweetArray");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                tweetArray );

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


        Button topMashers = (Button) findViewById(R.id.buttonTM);
        assert topMashers != null;
        topMashers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ResultsActivity.this, TopMashersActivity.class);
                Bundle extra = new Bundle();
                extra.putSerializable("objects", tweetArray);
                myIntent.putExtra("tweetArray", tweetArray);
                myIntent.putExtra("unPos1", getIntent().getExtras().getSerializable("unPos1"));
                myIntent.putExtra("dnPos1", getIntent().getExtras().getSerializable("dnPos1"));
                myIntent.putExtra("imgPos1", getIntent().getExtras().getSerializable("imgPos1"));
                myIntent.putExtra("unPos2", getIntent().getExtras().getSerializable("unPos2"));
                myIntent.putExtra("dnPos2", getIntent().getExtras().getSerializable("dnPos2"));
                myIntent.putExtra("imgPos2", getIntent().getExtras().getSerializable("imgPos2"));
                myIntent.putExtra("tweetPos1", getIntent().getExtras().getSerializable("tweetPos1"));
                myIntent.putExtra("tweetPos2", getIntent().getExtras().getSerializable("tweetPos2"));

                ResultsActivity.this.startActivity(myIntent);
            }
        });

    }
}
