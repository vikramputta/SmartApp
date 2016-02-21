package com.sap.cloud.sample.persistence.twitter;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterConnection {

	public static Twitter getInstance(){
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey(ConfigurationsTemplate.OAUTH_CONSUMER_KEY)
		  .setOAuthConsumerSecret(ConfigurationsTemplate.OAUTH_CONSUMER_SECRET)
		  .setOAuthAccessToken(ConfigurationsTemplate.OAUTH_ACCESS_TOKEN)
		  .setOAuthAccessTokenSecret(ConfigurationsTemplate.OAUTH_ACCESS_TOKEN_SECRET);
		
		if(ConfigurationsTemplate.HAS_PROXY){
			cb.setHttpProxyHost(ConfigurationsTemplate.PROXY_HOST).setHttpProxyPort(ConfigurationsTemplate.PROXY_PORT);
		}
		
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		
		return twitter;
	}

	public static TwitterStream getStreamInstance(){
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey(ConfigurationsTemplate.OAUTH_CONSUMER_KEY)
		  .setOAuthConsumerSecret(ConfigurationsTemplate.OAUTH_CONSUMER_SECRET)
		  .setOAuthAccessToken(ConfigurationsTemplate.OAUTH_ACCESS_TOKEN)
		  .setOAuthAccessTokenSecret(ConfigurationsTemplate.OAUTH_ACCESS_TOKEN_SECRET);
		
		if(ConfigurationsTemplate.HAS_PROXY){
			cb.setHttpProxyHost(ConfigurationsTemplate.PROXY_HOST).setHttpProxyPort(ConfigurationsTemplate.PROXY_PORT);
		}
		
		TwitterStream tf = new TwitterStreamFactory(cb.build()).getInstance();
		
		return tf;
	}


}
