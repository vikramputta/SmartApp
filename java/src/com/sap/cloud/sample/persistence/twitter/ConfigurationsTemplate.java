package com.sap.cloud.sample.persistence.twitter;

public class ConfigurationsTemplate {


	// Network Proxy - replace with your own network proxy or set the HAS_PROXY as false if you don't need to use proxy
	public static boolean HAS_PROXY = false;
	public static String PROXY_HOST = "proxy.yourcorp.com";
	public static int PROXY_PORT = 8080;


	// Twitter Authentication - replace with your own Twitter application consumer key and token
	public static String OAUTH_CONSUMER_KEY = "";
	public static String OAUTH_CONSUMER_SECRET = "";
	public static String OAUTH_ACCESS_TOKEN = "";
	public static String OAUTH_ACCESS_TOKEN_SECRET = "";
	
	private static final ConfigurationsTemplate template = new ConfigurationsTemplate();
     
    //private constructor to avoid client applications to use constructor
    private ConfigurationsTemplate(){}
 
    public static ConfigurationsTemplate getInstance(){
        return template;
    }
	
	public void setOAuthConsumerKey(String s){
		OAUTH_CONSUMER_KEY = s;
	}
	public void setOAuthConsumerSecret(String s){
		OAUTH_CONSUMER_SECRET = s;
	}
	public void setOAuthAccessToken(String s){
		OAUTH_ACCESS_TOKEN = s;
	}
	public void setOAuthAccessSecret(String s){
		OAUTH_ACCESS_TOKEN_SECRET = s;
	}
	public void setHasProxy(boolean b){
		HAS_PROXY = b;
	}
	public void setProxyPort(int i){
		PROXY_PORT = i;
	}
	public void setProxyHost(String s){
		PROXY_HOST = s;
	}
}
 

