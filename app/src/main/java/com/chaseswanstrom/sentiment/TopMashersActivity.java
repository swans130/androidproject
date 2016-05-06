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

//        fetchImageAsync fetchEm = new fetchImageAsync();
//        fetchEm.execute(url1, url2);

        final TextView userName1 = (TextView) findViewById(R.id.tvUsernameT1Positive);
        userName1.setText((getIntent().getExtras().getString("unPos1")));
        TextView displayName1 = (TextView) findViewById(R.id.tvDisplaynameT1Positive);
        displayName1.setText((getIntent().getExtras().getString("dnPos1")));
        TextView tweet1 = (TextView) findViewById(R.id.tvTweetT1Positive);
        tweet1.setText((getIntent().getExtras().getString("tweetPos1")));


        final TextView userName2 = (TextView) findViewById(R.id.tvUsernameT2Positive);
        userName2.setText(getIntent().getExtras().getString("unPos2"));
        TextView displayName2 = (TextView) findViewById(R.id.tvDisplaynameT2Positive);
        displayName2.setText((getIntent().getExtras().getString("dnPos2")));
        TextView tweet2 = (TextView) findViewById(R.id.tvTweetT2Positive);
        tweet2.setText((getIntent().getExtras().getString("tweetPos2")));


        ImageButton ib1 = (ImageButton) findViewById(R.id.ibT1Positive);
        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent twitterBrowserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.twitter.com/" + userName1.getText()));
                startActivity(twitterBrowserIntent);
            }
        });
        ImageButton ib2 = (ImageButton) findViewById(R.id.ibT2Positive);
        ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent twitterBrowserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.twitter.com/" + userName2.getText()));
                startActivity(twitterBrowserIntent);
            }
        });

//        final ArrayList<String> tweetArray = (ArrayList<String>) getIntent().getExtras().getSerializable("tweetArray");
//        ListView tweetView = (ListView) findViewById(R.id.tweetView);
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
//                this,
//                android.R.layout.simple_list_item_1,
//                tweetArray );
//        tweetView.setAdapter(arrayAdapter);

    }

    public class fetchImageAsync extends AsyncTask<String, Void, String>{
        BitmapDrawable img1;
        BitmapDrawable img2;
        @Override
        protected void onPreExecute(){

        }

        @Override
        protected String doInBackground(String... params){

            img1 = new BitmapDrawable(getResources(), getBitmapFromURL(params[0]));
            img2 = new BitmapDrawable(getResources(), getBitmapFromURL(params[1]));

            return "Completed";

        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }

        @Override
        protected void onPostExecute(String result) {
//            ib1.setBackground(img1);
//            ib2.setBackground(img2);

        }


    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }
}
