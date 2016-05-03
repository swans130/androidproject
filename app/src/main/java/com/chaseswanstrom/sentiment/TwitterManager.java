package com.chaseswanstrom.sentiment;

import android.net.Uri;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by chaseswanstrom on 4/12/16.
 */
public class TwitterManager {


    public String tweetText;
    public String cleanQuery;
    public Double totalScoreFinal = 0.0;
    public Double totalScoreFinal2 = 0.0;
    public int firstPosCount = 0;
    public int secondPosCount = 0;
    public int firstNegCount = 0;
    public int secondNegCount = 0;
    public int firstNeutralCount = 0;
    public int secondNeutralCount = 0;

    int limit = 5; //the number of retrieved tweets

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
        query.setCount(5);
        try {

            int count = 0;
            QueryResult r;
                r = twitter.search(query);
                ArrayList ts = (ArrayList) r.getTweets();
                for (int i = 0; i < limit - 1; ++i) {
                    count++;
                    Status t = (Status) ts.get(i);
                    tweetText = t.getText();
                    //clean the tweet so it can be added as query in sentiment api string
                    tweetText = tweetText.replace("&", "");
                    cleanQuery = Uri.encode(tweetText);
                    //log the tweet for testing analysis
                    Log.v("tweet: ", tweetText);

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
        query.setCount(5);
        try {

            int count = 0;
            QueryResult r;
            r = twitter.search(query);
            ArrayList ts = (ArrayList) r.getTweets();
            for (int i = 0; i < limit - 1; ++i) {
                count++;
                Status t = (Status) ts.get(i);
                tweetText = t.getText();
                //clean the tweet so it can be added as query in sentiment api string
                tweetText = tweetText.replace("&", "");
                cleanQuery = Uri.encode(tweetText);
                //log the tweet for testing analysis
                Log.v("tweet: ", tweetText);

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
                    for(int j = 0; j < jsonObject.length(); ++j)
                    {
                        JSONObject sentimentJson = jsonObject.getJSONObject("aggregate");
                        String jsonScoreString = sentimentJson.getString("score");
                        Double jsonScore = Double.parseDouble(jsonScoreString);
                        score += jsonScore;
                        score += jsonScore;
                        if(score > 0.0) {
                            ++secondPosCount;
                        }
                        if(score < 0.0) {
                            ++secondNegCount;
                        }
                        if(score == 0.0) {
                            ++secondNeutralCount;
                        }
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
