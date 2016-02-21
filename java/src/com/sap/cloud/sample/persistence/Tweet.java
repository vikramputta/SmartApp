package com.sap.cloud.sample.persistence;


import java.util.Date;

//import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class holding information on a tweet.
 */
@Entity
@Table(name = "\"Tweets\"")
@NamedQuery(name = "AllTweets", query = "select t from Tweet t")
public class Tweet {
    @Id
    @Column(name="\"id\"")
    private String id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="\"created\"", nullable=true)
    private Date created;
    
    @Column(name="\"text\"", nullable=true)
    private String text;
    @Column(name="\"lang\"", nullable=true)
    private String lang;
    @Column(name="\"user\"", nullable=true)
    private String user;
    @Column(name="\"replyUser\"", nullable=true)
    private String replyUser;
    @Column(name="\"retweetedUser\"", nullable=true)
    private String retweetedUser;

    @Column(name="\"lat\"", nullable=true)
    private Double lat = null;
    @Column(name="\"lon\"", nullable=true)
    private Double lon = null;

    public String getId() {
        return id;
    }

    public void setId(String newId) {
        this.id = newId;
    }

    public Date getTimestamp() {
        return created;
    }

    public void setTimestamp(Date newTimestamp) {
        this.created = newTimestamp;
    }
    
    public String getText() {
        return text;
    }

    public void setText(String newText) {
        this.text = newText;
    }
    
    public String getLang() {
        return lang;
    }

    public void setLang(String newLang) {
        this.lang = newLang;
    }
    
    public String getUser() {
        return user;
    }

    public void setUser(String newUser) {
        this.user = newUser;
    }
    
    public String getReplyUser() {
        return replyUser;
    }

    public void setReplyUser(String newReplyUser) {
        this.replyUser = newReplyUser;
    }
    
    public String getRetweetedUser() {
        return retweetedUser;
    }

    public void setRetweetedUser(String newRetweetUser) {
        this.retweetedUser = newRetweetUser;
    }
    
    public double getLat() {
        return lat;
    }

    public void setLat(Double newLat) {
        this.lat = newLat;
    }
    
    public double getLon() {
        return lon;
    }

    public void setLon(Double newLon) {
        this.lon = newLon;
    }

}
