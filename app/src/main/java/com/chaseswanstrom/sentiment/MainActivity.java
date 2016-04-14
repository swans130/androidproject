package com.chaseswanstrom.sentiment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {



    //thread.start();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        TwitterManager t = new TwitterManager();
//        try {
//            t.performQuery("trump");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        TextView tv = (TextView) findViewById(R.id.textViewScore);
//        tv.setText(t.sent.toString());
        thread.start();

    }

    Thread thread = new Thread(new Runnable(){
        @Override
        public void run() {
            try {
                TwitterManager t = new TwitterManager();
                try {
                    t.performQuery("trump");
                    TextView tv = (TextView) findViewById(R.id.textViewScore);
                    tv.setText(t.senti.toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });


}
