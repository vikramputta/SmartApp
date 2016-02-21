package com.sap.cloud.sample.persistence;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Session Bean implementation class TweetBean
 */
@Stateless
@LocalBean
public class TweetBean {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<Tweet> getAllTweets() {
		return em.createNamedQuery("AllTweets").getResultList();

	}

	public void addTweet(Tweet tweet) {
		em.persist(tweet);
		em.flush();
	}

}
