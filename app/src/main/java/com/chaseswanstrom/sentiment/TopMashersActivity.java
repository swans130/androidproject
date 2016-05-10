package com.chaseswanstrom.sentiment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by cwatsonbaseball on 5/2/16.
 */

public class TopMashersActivity extends AppCompatActivity implements Serializable{

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topmashers);




        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(5);
        gd.setStroke(4, 0xffffffff);

        //TextView tvBorderT1Positive = (TextView) findViewById(R.id.tvBorderT1Positive);
        //tvBorderT1Positive.setBackground(gd);

        Intent intent = getIntent();
        Bundle extra = getIntent().getBundleExtra("extra");


        //Set Query Words to Strings
        String query1 = intent.getStringExtra("query1");
        String query2 = intent.getStringExtra("query2");

//        fetchImageAsync fetchEm = new fetchImageAsync();
//        fetchEm.execute(url1, url2);

        //Positive tweet 1
        final TextView userName1 = (TextView) findViewById(R.id.tvUsernameT1Positive);
        userName1.setText((intent.getExtras().getString("unPos1")));
        TextView displayName1 = (TextView) findViewById(R.id.tvDisplaynameT1Positive);
        displayName1.setText((intent.getExtras().getString("dnPos1")) + ": " +  "\""+query1+"\"");
        TextView tweet1 = (TextView) findViewById(R.id.tvTweetT1Positive);
        tweet1.setText((intent.getExtras().getString("tweetPos1")));

        //Positive tweet 2
        final TextView userName2 = (TextView) findViewById(R.id.tvUsernameT2Positive);
        userName2.setText(intent.getExtras().getString("unPos2"));
        TextView displayName2 = (TextView) findViewById(R.id.tvDisplaynameT2Positive);
        displayName2.setText((intent.getExtras().getString("dnPos2")) + ": " +  "\""+query2+"\"");
        TextView tweet2 = (TextView) findViewById(R.id.tvTweetT2Positive);
        tweet2.setText((intent.getExtras().getString("tweetPos2")));

        //Negative tweet 1
        final TextView userName3 = (TextView) findViewById(R.id.tvUsernameT1Negative);
        userName3.setText((intent.getExtras().getString("unNeg1")));
        TextView displayName3 = (TextView) findViewById(R.id.tvDisplaynameT1Negative);
        displayName3.setText((intent.getExtras().getString("dnNeg1")) + ": " +  "\""+query1+"\"");
        TextView tweet3 = (TextView) findViewById(R.id.tvTweetT1Negative);
        tweet3.setText((intent.getExtras().getString("tweetNeg1")));

        //Negative tweet 2
        final TextView userName4 = (TextView) findViewById(R.id.tvUsernameT2Negative);
        userName4.setText((intent.getExtras().getString("unNeg2")));
        TextView displayName4 = (TextView) findViewById(R.id.tvDisplaynameT2Negative);
        displayName4.setText((intent.getExtras().getString("dnNeg2")) + ": " +  "\""+query2+"\"");
        TextView tweet4 = (TextView) findViewById(R.id.tvTweetT2Negative);
        tweet4.setText((intent.getExtras().getString("tweetNeg2")));

        //Positive Image Button Setups
        ImageButton ib1 = (ImageButton) findViewById(R.id.ibT1Positive);
        Bitmap bitmap1 = intent.getParcelableExtra("imgPos1");
        BitmapDrawable img1 = new BitmapDrawable(getResources(), bitmap1);
        ib1.setBackground(img1);
        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent twitterBrowserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.twitter.com/" + userName1.getText()));
                startActivity(twitterBrowserIntent);
            }
        });
        ImageButton ib2 = (ImageButton) findViewById(R.id.ibT2Positive);
        Bitmap bitmap2 = intent.getParcelableExtra("imgPos2");
        BitmapDrawable img2 = new BitmapDrawable(getResources(), bitmap2);
        ib2.setBackground(img2);
        ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent twitterBrowserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.twitter.com/" + userName2.getText()));
                startActivity(twitterBrowserIntent);
            }
        });

        //Negative Image Button Setups
        ImageButton ib3 = (ImageButton) findViewById(R.id.ibT1Negative);
        Bitmap bitmap3 = intent.getParcelableExtra("imgNeg1");
        BitmapDrawable img3 = new BitmapDrawable(getResources(), bitmap3);
        ib3.setBackground(img3);
        ib3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent twitterBrowserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.twitter.com/" + userName3.getText()));
                startActivity(twitterBrowserIntent);
            }
        });
        ImageButton ib4 = (ImageButton) findViewById(R.id.ibT2Negative);
        Bitmap bitmap4 = intent.getParcelableExtra("imgNeg2");
        BitmapDrawable img4 = new BitmapDrawable(getResources(), bitmap4);
        ib4.setBackground(img4);
        ib4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent twitterBrowserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.twitter.com/" + userName4.getText()));
                startActivity(twitterBrowserIntent);
            }
        });

        //Set the background for each tweet
        View bgT1Positive = findViewById(R.id.bgT1Positive);
        bgT1Positive.setBackground(gd);
        View bgT2Positive = findViewById(R.id.bgT2Positive);
        bgT2Positive.setBackground(gd);

        View bgT1Negative = findViewById(R.id.bgT1Negative);
        bgT1Negative.setBackground(gd);
        View bgT2Negative = findViewById(R.id.bgT2Negative);
        bgT2Negative.setBackground(gd);

//        final ArrayList<String> tweetArray = (ArrayList<String>) getIntent().getExtras().getSerializable("tweetArray");
//        ListView tweetView = (ListView) findViewById(R.id.tweetView);
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
//                this,
//                android.R.layout.simple_list_item_1,
//                tweetArray );
//        tweetView.setAdapter(arrayAdapter);

    }

}
