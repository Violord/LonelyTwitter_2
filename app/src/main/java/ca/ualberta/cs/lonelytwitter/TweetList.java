package ca.ualberta.cs.lonelytwitter;

import java.util.ArrayList;

/**
 * Created by makepeac on 9/29/16.
 * This class provides the methods to operate on Tweet.
 * @see LonelyTwitterActivity
 * @see Tweet
 */
public class TweetList {
    /**
     * This is the inserted list that handles methods
     */
    private ArrayList<Tweet> tweets = new ArrayList<Tweet>();

    public TweetList(){

    }

    public Tweet getTweet(int index){
        return tweets.get(index);
    }


    public boolean hasTweet(Tweet tweet){
        return tweets.contains(tweet);
    }

    /**
     * This method add a new tweet that does not exist in the current list to the list.
     * @throws IllegalArgumentException
     * @param tweet
     */
    public void addTweet(Tweet tweet) {
        if(tweets.contains(tweet)){
            throw new IllegalArgumentException();
        }else{
        tweets.add(tweet);
        }
    }

    /**
     * This method removes a existing tweet from the list
     * @param tweet
     */
    public void delete(Tweet tweet) {
        tweets.remove(tweet);
    }


    public int getCount(){return tweets.size();}

    /**
     * This method returns the current Tweet list in chronological order.
     * @return
     */
    public ArrayList getTweets() {
        for(int i=0;i<tweets.size();i++){
            for(int j=0;j<i;j++){
                if(tweets.get(j).getDate().compareTo(tweets.get(j+1).getDate()) == 1){
                    Tweet temp = tweets.get(j);
                    tweets.set(j, tweets.get(j+1));
                    tweets.set(j+1, temp);
                }
            }
        }
        return tweets;
    }

}
