package com.chaseswanstrom.sentiment;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by cwatsonbaseball on 5/2/16.
 */

public class TopMashersActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topmashers);

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(5);
        gd.setStroke(4, 0xffffffff);

        TextView tvBorderT1Positive = (TextView) findViewById(R.id.tvBorderT1Positive);
        tvBorderT1Positive.setBackground(gd);




    }
}
