package com.example.ebtesam.educationexchange.chatApp;

/**
 * Created by ebtesam on 21/03/2018 AD.
 */

public class Messages {

    private String messageText;
    private String from;
    private String userBid;
    private boolean seen;
    private long time;
    public Messages(String messageText,boolean seen,long date ){
        this.messageText=messageText;
        this.seen=seen;
        this.time=date;
    }
    public Messages(){
    }

    public String getUserBid() {
        return userBid;
    }

    public void setUserBid(String userBid) {
        this.userBid = userBid;
    }

    public String getMessageText() {return messageText;}

    public void setMessageText(String messageText) {this.messageText = messageText;}

    public boolean getSeen() {return seen;}

    public void setSeen(boolean seen) {this.seen = seen;}

    public long getDate() {return time;}

    public void setDate(long date) {this.time = date;}

    public String getFrom() {return from;}

    public void setFrom(String from) {this.from = from;}


}

