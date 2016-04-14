package com.chaseswanstrom.sentiment;

import android.util.Log;

import java.io.IOException;
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

    public String sent;
    public String senti;

    //SentimentClassifier sentClassifier;
    int LIMIT= 500; //the number of retrieved tweets
    ConfigurationBuilder cb;
    Twitter twitter;
    public TwitterManager() {
        cb = new ConfigurationBuilder();
        cb.setOAuthConsumerKey("zlSSHxbLCBBwOEIJsLLqvLfgP");
        cb.setOAuthConsumerSecret("nEZMkRC2Dr6Dsw73iKHshgIDinBzmoii79W41Wut8nct5z9vv4");
        cb.setOAuthAccessToken("471717414-tvC5d7rB3qCALtOsabXIT1YExrZxlSGViZ8alAMk");
        cb.setOAuthAccessTokenSecret("YmU9tz0ZhOBJMxZXsvaABxJ3BflIPgEGBXFZGDwoRmQHP");
        twitter = new TwitterFactory(cb.build()).getInstance();
       // sentClassifier = new SentimentClassifier();
    }
    public void performQuery(String inQuery) throws InterruptedException, IOException {
        Query query = new Query(inQuery);
        query.setCount(10);
        try {
            int count = 0;
            QueryResult r;
                r = twitter.search(query);
                ArrayList ts = (ArrayList) r.getTweets();

                for (int i = 0; i < 10; ++i) {
                    count++;
                    Status t = (Status) ts.get(i);
                    //Status t = ts.get(i);
                    senti = t.getText();
                    Log.v("tweet: ", senti);
//                    System.out.println("Text: " + senti);
//                    String name = t.getUser().getScreenName();
//                    System.out.println("User: " + name);

                    //sent = sentClassifier.classify(t.getText());
                    //System.out.println("Sentiment: " + sent);
                }
        }
        catch (TwitterException te) {
            System.out.println("Couldn't connect: " + te);
        }
    }

}
