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
    
    @Override
    public Map<String, String> getValues() {
        Map<String, String> v = new HashMap<String, String>();
        v.put("user", username);
        v.put("password", password);
        v.put("token", authToken);
        return v;
    }

}
