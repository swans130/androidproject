package com.chaseswanstrom.sentiment;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by chaseswanstrom on 4/12/16.
 */
public class TwitterManager extends Application implements Serializable{


    public String tweetText;
    public String cleanQuery;
    public Double totalScoreFinal = 0.0;
    public Double totalScoreFinal2 = 0.0;
    public Double maxPos1 = 0.0;
    public Double maxPos2 = 0.0;
    public String unPos1;
    public String unPos2;
    public String dnPos1;
    public String dnPos2;
    public String tweetPos1;
    public String tweetPos2;
    public String imgPos1;
    public String imgPos2;

    public Double maxNeg1 = 0.0;
    public Double maxNeg2 = 0.0;
    public String unNeg1;
    public String unNeg2;
    public String dnNeg1;
    public String dnNeg2;
    public String tweetNeg1;
    public String tweetNeg2;
    public String imgNeg1;
    public String imgNeg2;

    public int firstPosCount = 0;
    public int secondPosCount = 0;
    public int firstNegCount = 0;
    public int secondNegCount = 0;
    public int firstNeutralCount = 0;
    public int secondNeutralCount = 0;
    public ArrayList<String> tweetArray = new ArrayList<String>();

    public Bitmap img1BMP;
    public Bitmap img2BMP;
    public Bitmap img3BMP;
    public Bitmap img4BMP;

    int limit = 10; //the number of retrieved tweets

    ConfigurationBuilder cb;
    Twitter twitter;

    public TwitterManager() {
        cb = new ConfigurationBuilder();
        cb.setOAuthConsumerKey("zlSSHxbLCBBwOEIJsLLqvLfgP");
        cb.setOAuthConsumerSecret("nEZMkRC2Dr6Dsw73iKHshgIDinBzmoii79W41Wut8nct5z9vv4");
        cb.setOAuthAccessToken("471717414-tvC5d7rB3qCALtOsabXIT1YExrZxlSGViZ8alAMk");
        cb.setOAuthAccessTokenSecret("YmU9tz0ZhOBJMxZXsvaABxJ3BflIPgEGBXFZGDwoRmQHP");
        twitter = new TwitterFactory(cb.build()).getInstance();
    }
    public void performQuery(String inQuery) throws InterruptedException, IOException {

        Query query = new Query(inQuery);
        //number of retrieved tweets
        query.setCount(10);
        try {

            int count = 0;
            QueryResult r;
                r = twitter.search(query);
                Log.v("Log R>>>", r.toString());
                ArrayList ts = (ArrayList) r.getTweets();
                for (int i = 0; i < limit - 1; ++i) {
                    if (ts.get(i) != null) {
                        count++;
                        Status t = (Status) ts.get(i);
                        tweetText = t.getText();
                        //clean the tweet so it can be added as query in sentiment api string
                        tweetText = tweetText.replace("&", "");
                        cleanQuery = Uri.encode(tweetText);
                        //log the tweet for testing analysis
                        Log.v("tweet: ", tweetText);
                        tweetArray.add(tweetText);
                    }



                //set up the sentiment analysis api
                URL url;

                try {

                //Log.e("tweet value:", cleanQuery); testing to make sure the tweet has been cleaned
                //feed the tweet into the sentiment api
                HttpURLConnection urlConnection = null;
                String api = "https://api.havenondemand.com/1/api/sync/analyzesentiment/v1?text=" + cleanQuery + "&apikey=90b03d84-c5c7-4166-b358-212950d125fc";
                url = new URL(api);
                urlConnection = (HttpURLConnection) url
                        .openConnection();
                InputStream in = urlConnection.getInputStream();
                String sentimentString = convertStreamToString(in);
                // this is the full sentiment payload returned from the api
                Log.e("sentiment string 111111", sentimentString);
                //turn the string into a json object for parsing
                JSONObject jsonObject = new JSONObject(sentimentString);
                // the total score from tweet of sentiment payload, for some reason it is adding it 3 times
                double score = 0;
                //get the aggregate object from json payload, isolate the score string, cast to double, add to running total
                for(int j = 0; j < jsonObject.length(); ++j)
                {
                    JSONObject sentimentJson = jsonObject.getJSONObject("aggregate");
                    String jsonScoreString = sentimentJson.getString("score");
                    Double jsonScore = Double.parseDouble(jsonScoreString);
                    score += jsonScore;
                    if(score > 0.0) {
                        ++firstPosCount;
                    }
                    if(score < 0.0) {
                        ++firstNegCount;
                    }
                    if(score == 0.0) {
                        ++firstNeutralCount;
                    }
                }
                    //Find MaxPos1
                    if (score > maxPos1){
                        maxPos1 = score;
                        Status s = (Status) ts.get(i);
                        User u = (User) s.getUser();
                        imgPos1 = u.getProfileImageURL();
                        unPos1 = u.getScreenName();
                        dnPos1 = u.getName();
                        tweetPos1 = s.getText();
                    }
                    //Find MaxNeg1
                    if (score < maxNeg1){
                        maxNeg1 = score;
                        Status s = (Status) ts.get(i);
                        User u = (User) s.getUser();
                        imgNeg1 = u.getProfileImageURL();
                        unNeg1 = u.getScreenName();
                        dnNeg1 = u.getName();
                        tweetNeg1 = s.getText();
                    }

                //firstPosCount = (firstPosCount);
                //*****BUG BUG BUG BUG BUG BUG********
                //workaround
                //the score is being returned 3 times so must divide by 3
                double totalScore = score / 3.0;
                totalScoreFinal += totalScore;
                //Log.d("total score", Double.toString(totalScore)); for testing purposes, will see it added 3 times and thus incorrect
                Log.d("total score tweet 1", Double.toString(totalScore));

                } catch (Exception e) {
                e.printStackTrace();
                     }
                }
                //this is the final score for the entire query
                Log.d("final score", Double.toString(totalScoreFinal));
            }
        //if twitter api fails
        catch (TwitterException te) {
            System.out.println("Couldn't connect: " + te);

        }
    }

    //SECOND FUNCTION

    public void performQuery2(String inQuery) throws InterruptedException, IOException {

        Query query = new Query(inQuery);

        //number of retrieved tweets
        query.setCount(10);
        try {

            int count = 0;
            QueryResult r;
            r = twitter.search(query);
            ArrayList ts = (ArrayList) r.getTweets();

            for (int i = 0; i < limit - 1; ++i) {
                if (ts.get(i) != null) {
                    count++;
                    Status t = (Status) ts.get(i);
                    tweetText = t.getText();
                    //clean the tweet so it can be added as query in sentiment api string
                    tweetText = tweetText.replace("&", "");
                    cleanQuery = Uri.encode(tweetText);
                    //log the tweet for testing analysis
                    Log.v("tweet: ", tweetText);
                }

                //set up the sentiment analysis api
                URL url;

                try {

                    //Log.e("tweet value:", cleanQuery); testing to make sure the tweet has been cleaned
                    //feed the tweet into the sentiment api
                    HttpURLConnection urlConnection = null;
                    String api = "https://api.havenondemand.com/1/api/sync/analyzesentiment/v1?text=" + cleanQuery + "&apikey=90b03d84-c5c7-4166-b358-212950d125fc";
                    url = new URL(api);
                    urlConnection = (HttpURLConnection) url
                            .openConnection();
                    InputStream in = urlConnection.getInputStream();
                    String sentimentString = convertStreamToString(in);
                    // this is the full sentiment payload returned from the api
                    Log.e("sentiment string222222", sentimentString);
                    //turn the string into a json object for parsing
                    JSONObject jsonObject = new JSONObject(sentimentString);
                    // the total score from tweet of sentiment payload, for some reason it is adding it 3 times
                    double score = 0;
                    //get the aggregate object from json payload, isolate the score string, cast to double, add to running total
                    for (int j = 0; j < jsonObject.length(); ++j) {
                        JSONObject sentimentJson = jsonObject.getJSONObject("aggregate");
                        String jsonScoreString = sentimentJson.getString("score");
                        Double jsonScore = Double.parseDouble(jsonScoreString);
                        score += jsonScore;
                        score += jsonScore;
                        if (score > 0.0) {
                            ++secondPosCount;
                        }
                        if (score < 0.0) {
                            ++secondNegCount;
                        }
                        if (score == 0.0) {
                            ++secondNeutralCount;
                        }
                    }
                    //Find MaxPos2
                    if (score > maxPos2) {
                        maxPos2 = score;
                        Status s = (Status) ts.get(i);
                        User u = (User) s.getUser();
                        imgPos2 = u.getProfileImageURL();
                        unPos2 = u.getScreenName();
                        dnPos2 = u.getName();
                        tweetPos2 = s.getText();
                    }
                    //Find MaxNeg2
                    if (score < maxNeg2) {
                        maxNeg2 = score;
                        Status s = (Status) ts.get(i);
                        User u = (User) s.getUser();
                        imgNeg2 = u.getProfileImageURL();
                        unNeg2 = u.getScreenName();
                        dnNeg2 = u.getName();
                        tweetNeg2 = s.getText();
                    }
                    //*****BUG BUG BUG BUG BUG BUG********
                    //workaround
                    //the score is being returned 3 times so must divide by 3
                    double totalScore = score / 3.0;
                    totalScoreFinal2 += totalScore;
                    //Log.d("total score", Double.toString(totalScore)); for testing purposes, will see it added 3 times and thus incorrect
                    Log.d("total score", Double.toString(totalScore));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //this is the final score for the entire query
            Log.d("final score 2", Double.toString(totalScoreFinal2));
        }
        //if twitter api fails
        catch (TwitterException te) {
            System.out.println("Couldn't connect: " + te);
        }

        img1BMP = getBitmapFromURL(imgPos1);
        img2BMP = getBitmapFromURL(imgPos2);
        img3BMP = getBitmapFromURL(imgNeg1);
        img4BMP = getBitmapFromURL(imgNeg2);

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

    //method to return the input stream from sentiment api to a java string
    String convertStreamToString(java.io.InputStream is) {
        try {
            return new java.util.Scanner(is).useDelimiter("\\A").next();
        } catch (java.util.NoSuchElementException e) {
            return "";
        }
    }

}
