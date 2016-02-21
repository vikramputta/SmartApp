package com.sap.cloud.sample.persistence;


import java.io.IOException;
import java.util.Arrays;

import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.cloud.sample.persistence.twitter.ConfigurationsTemplate;


/**
 * Servlet implementation class TwitterJava
 */
@WebServlet("/TwitterJava")
public class TwitterJava extends HttpServlet {
	

    @EJB
    ReaderManager readerManager;
    @EJB
    TweetBean tweetBean;
    
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(TwitterJava.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TwitterJava() {
        super();
    }
    

    public void init(ServletConfig config) throws ServletException {
      super.init(config);
      ConfigurationsTemplate template = ConfigurationsTemplate.getInstance();
      
      String consumerKey = config.getInitParameter("OAUTH_CONSUMER_KEY");
      if( consumerKey != null)
    	  template.setOAuthConsumerKey(consumerKey);
      
      String consumerSecret = config.getInitParameter("OAUTH_CONSUMER_SECRET");
      if( consumerSecret != null)
    	  template.setOAuthConsumerSecret(consumerSecret);
      
      String accessToken = config.getInitParameter("OAUTH_ACCESS_TOKEN");
      if( accessToken != null)
    	  template.setOAuthAccessToken(accessToken);
      
      String accessSecret = config.getInitParameter("OAUTH_ACCESS_TOKEN_SECRET");
      if( accessSecret != null)
    	  template.setOAuthAccessSecret(accessSecret);
      
      String hasProxy = config.getInitParameter("HAS_PROXY");
      if( hasProxy != null){
    	  template.setHasProxy(hasProxy.equalsIgnoreCase("true"));
      }
      
      String proxyHost = config.getInitParameter("PROXY_HOST");
      if( proxyHost != null)
    	  template.setProxyHost(proxyHost);
      
      String proxyPort = config.getInitParameter("PROXY_PORT");
      if( proxyPort != null){
    	  try{
    	  	template.setProxyPort(Integer.parseInt(proxyPort));
    	  }
    	  catch(NumberFormatException nfe){} //leave as default
      }
    }
	/** {@inheritDoc} */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
		
	}
	/** {@inheritDoc} */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String sResponse = "<p>Hana Tweet Listener</p>";
		String sStop = request.getParameter("stop");
		if( sStop != null && sStop.equals("y")){
			sResponse = "<p>Stopping the Tweet Listener</p>";
			readerManager.stopMonitoring();
			LOGGER.info("Stopping the Tweet Listener");
		}
		else{
			String[] queries = request.getParameterValues("track");
			if( queries != null){
				queries = cleanArray(queries);
				
				sResponse = "<p>Starting Tweet Listener for : ";
				for( int i=0; i<queries.length; i++)
					sResponse += " " + queries[i];
				
				readerManager.startMonitoring(queries, tweetBean);
				
			}
		}
		
		response.getWriter().println(sResponse);
	}
	
    private String[] cleanArray(final String[] v) {
   	   int r, w;
   	   int n = r = w = v.length;
   	   while (r > 0) {
   	      String s = v[--r];
   	      if (s != null && !s.equals("")) {
   	          v[--w] = s;
   	      }
   	  }
   	  return Arrays.copyOfRange(v, w, n);
   }


}
