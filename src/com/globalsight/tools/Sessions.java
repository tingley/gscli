package com.globalsight.tools;

import java.util.HashMap;
import java.util.Map;

// sessions.[profilename].token
// sessions.[profilename].timestamp
public class Sessions implements UserDataComponent {
    private Map<String, Session> sessions = 
        new HashMap<String, Session>();

    public Sessions(Map<String, String> props) {
        Map<String, Map<String, String>> m = 
            new HashMap<String, Map<String, String>>();
        for (Map.Entry<String, String> e : props.entrySet()) {
            String[] parts = e.getKey().split("\\.");
            if (parts.length != 2) {
                continue; // TODO: warn 
            }
            Map<String, String> sessionValues = m.get(parts[0]);
            if (sessionValues == null) {
                sessionValues = new HashMap<String, String>();
                m.put(parts[0], sessionValues);
            }
            sessionValues.put(parts[1], e.getValue());
        }
        for (Map.Entry<String, Map<String, String>> e : m.entrySet()) {
            sessions.put(e.getKey(), new Session(e.getValue()));
        }
    }
    
    @Override
    public Map<String, String> getValues() {
        Map<String, String> props = new HashMap<String, String>();
        for (Map.Entry<String, Session> e : sessions.entrySet()) {
            Map<String, String> sv = e.getValue().getValues();
            for (Map.Entry<String, String> prop : sv.entrySet()) {
                props.put(e.getKey() + "." + prop.getKey(), prop.getValue());
            }
        }
        return props;
    }
 
    public void setSession(String profileName, String token) {
        sessions.put(profileName, new Session(token));
    }
    
    public Session getSession(String profileName) {
        return sessions.get(profileName);
    }
}
