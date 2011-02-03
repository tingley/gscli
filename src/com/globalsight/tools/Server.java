package com.globalsight.tools;

import java.util.HashMap;
import java.util.Map;

public class Server implements UserDataComponent {
    private String url;
    
    public Server(String url) {
        this.url = url;
    }
    
    public Server(Map<String, String> props) {
        this.url = props.get("url");
    }
    
    public String getUrl() {
        return url;
    }
    
    @Override
    public Map<String, String> getValues() {
        Map<String, String> m = new HashMap<String, String>();
        m.put("url", url);
        return m;
    }

}
