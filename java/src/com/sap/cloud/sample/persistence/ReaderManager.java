package com.sap.cloud.sample.persistence;

import javax.ejb.Singleton;
import com.sap.cloud.sample.persistence.twitter.SearchTweets;

@Singleton

public class ReaderManager {
	private SearchTweets searchTweets;
	
    public void startMonitoring(String[] queries, TweetBean tweetBean) {
     		if( searchTweets == null)
     			searchTweets = new SearchTweets();
     		searchTweets.setTweetBean(tweetBean);

     		searchTweets.listenForTweets(queries);
     }
     public void stopMonitoring(){
     	if( searchTweets == null)
     		return; //do nothing
     	searchTweets.stopListener();
     }

}
