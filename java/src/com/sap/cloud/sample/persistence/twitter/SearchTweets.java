package com.sap.cloud.sample.persistence.twitter;


import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.cloud.sample.persistence.Tweet;
import com.sap.cloud.sample.persistence.TweetBean;

import twitter4j.FilterQuery;
import twitter4j.GeoLocation;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
 
public class SearchTweets {
	TweetBean tweetBean;

	private static final Logger LOGGER = LoggerFactory.getLogger(SearchTweets.class);

	private TwitterStream twitterStream;
	
	public SearchTweets(){
		
	}

	StatusListener listener = new StatusListener(){
        public void onStatus(Status status) {
        //	LOGGER.info(status.getUser().getName() + " : " + status.getText());
            handleNewTweet(status);
        }
        public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
        public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}
        public void onException(Exception ex) {
            ex.printStackTrace();
        }
		@Override
		public void onScrubGeo(long arg0, long arg1) {
			
		}
		@Override
		public void onStallWarning(StallWarning arg0) {
			
		}
    };
    
    private void handleNewTweet(Status status){
    	//first get the base details of the tweet
        long lId = status.getId();
        String sId = "";
        if( Long.valueOf(lId) == null){
        	return; //abort
        }
        else{
        	sId = String.valueOf(lId);
        }
        Tweet tweet = new Tweet();
        tweet.setId(sId);

        Date createDt =  status.getCreatedAt();
        if( createDt != null)
        	tweet.setTimestamp(createDt);
        
        String sText = status.getText();
        if( sText == null)
        	sText = "";
        tweet.setText(sText);
        
        String sLang = status.getLang();
        if( sLang == null)
        	sLang = "";
        tweet.setLang(sLang);
        
        String sUser = status.getUser().getScreenName();// .getName();
        if( sUser == null)
        	sUser = "";
        tweet.setUser(sUser);
        
        String sReplyUser =  status.getInReplyToScreenName();
        if( sReplyUser == null || sReplyUser.equals("null"))
        	sReplyUser = "";
     //   LOGGER.error("REPLY USER: " + sReplyUser);
        tweet.setReplyUser(sReplyUser);
        
        String sRetweetedUser = "";
        try{
        	sRetweetedUser = status.getRetweetedStatus().getUser().getScreenName();// .getName();
        }
        catch(Exception ex){
        	sRetweetedUser = "";
			//LOGGER.error("Error getting retweet user: ", ex);
        }
        tweet.setRetweetedUser(sRetweetedUser);
        

        double dLat;
        double dLong;
        GeoLocation location = status.getGeoLocation();
        if( location != null){
    		LOGGER.error("Location not null");
        	dLat = location.getLatitude();
        	dLong = location.getLongitude();
            tweet.setLat(dLat);
            tweet.setLon(dLong);
        }
	//	LOGGER.info("sUser: " + sUser);
        tweetBean.addTweet(tweet);     
    	
    }

	public void listenForTweets(String[] queries){
		twitterStream = TwitterConnection.getStreamInstance();
		twitterStream.addListener(listener);
		
		FilterQuery filterQuery = new FilterQuery();
		filterQuery.track( queries);
		twitterStream.filter(filterQuery);
	}
	
	public void stopListener(){
		if( twitterStream != null ){
			twitterStream.shutdown();
			twitterStream = null;
		}
	}
	
	public void setTweetBean(TweetBean bean){
		this.tweetBean = bean;
	}
	

}
