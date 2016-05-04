package com.chaseswanstrom.sentiment;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
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
        final ArrayList<String> tweetArray = (ArrayList<String>) getIntent().getExtras().getSerializable("tweetArray");
        ListView tweetView = (ListView) findViewById(R.id.tweetView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                tweetArray );
        tweetView.setAdapter(arrayAdapter);

    }
}
