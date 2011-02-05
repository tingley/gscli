package com.globalsight.tools;

import java.util.HashMap;
import java.util.Map;

// TODO: authToken should maybe move elsewhere?
public class AuthData implements UserDataComponent {
    private String username;
    private String password;
    
    public AuthData(String username, String password, String authToken) {
        this.username = username;
        this.password = password;
    }
    
    public AuthData(Map<String, String> props) {
        this.username = props.get("user");
        this.password = props.get("password");
    }
    
    @Override
    public Map<String, String> getValues() {
        Map<String, String> v = new HashMap<String, String>();
        v.put("user", username);
        v.put("password", password);
        return v;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String name) {
        this.username = name;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
