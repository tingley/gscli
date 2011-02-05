package com.globalsight.tools;

import java.util.HashMap;
import java.util.Map;

public class AuthData implements UserDataComponent {
    private String username;
    private String password;
    private String authToken;
    
    public AuthData(String username, String password, String authToken) {
        this.username = username;
        this.password = password;
        this.authToken = authToken;
    }
    
    public AuthData(Map<String, String> props) {
        this.username = props.get("user");
        this.password = props.get("password");
        this.authToken = props.get("token");
    }
    
    @Override
    public Map<String, String> getValues() {
        Map<String, String> v = new HashMap<String, String>();
        v.put("user", username);
        v.put("password", password);
        v.put("token", authToken);
        return v;
    }

    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }


    public String getAuthToken() {
        return authToken;
    }
    
    public void setUsername(String name) {
        this.username = name;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setAuthToken(String token) {
        this.authToken = token;
    }
}
