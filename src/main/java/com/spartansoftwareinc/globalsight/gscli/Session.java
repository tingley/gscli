package com.spartansoftwareinc.globalsight.gscli;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Session implements UserDataComponent {

    private String authToken;
    private Date timestamp;
    
    public Session(String authToken) {
        this.authToken = authToken;
        this.timestamp = new Date();
    }
    
    public Session(Map<String, String> props) {
        authToken = props.get("token");
        try {
            String ts = props.get("timestamp");
            timestamp = (ts == null) ? null : 
                getDateFormat().parse(ts);
        } catch (ParseException e) {
            System.out.println("Failed to parse date: " + e.getMessage());
            timestamp = null;
        }
    }
    
    @Override
    public Map<String, String> getValues() {
        Map<String, String> v = new HashMap<String, String>();
        v.put("token", authToken);
        v.put("timestamp", getDateFormat().format(timestamp));
        return v;
    }
    
    private DateFormat getDateFormat() {
        return new SimpleDateFormat("yyMMddHHmmssZ");
    }

    public String getToken() {
        return authToken;
    }
    
    public Date getDate() {
        return timestamp;
    }
}
