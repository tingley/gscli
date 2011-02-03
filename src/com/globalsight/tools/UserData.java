package com.globalsight.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class UserData {

    private Properties properties = new Properties();
    private boolean dirty = false;
    
    public UserData() {
    }
    
    public boolean isDirty() {
        return dirty;
    }
    
    protected UserData(Properties properties) {
        this.properties = properties;
    }
    
    public void store(File file) throws IOException {
        // XXX This is wrong, it needs to be in ISO-8859-1
        // Also, does this do output escaping for me?
        // XXX Also, does this blow away the old file contents?
        properties.store(new OutputStreamWriter(new FileOutputStream(file),
                                                "UTF-8"), null);
    }
    
    public static UserData load(File file) throws IOException {
        if (file == null || !file.exists()) {
            return new UserData();
        }
        Properties p = new Properties();
        // XXX This can fail in the case of malformed unicode escape or
        // other weirdness.  We should warn (somehow) and return an empty
        // data object.
        p.load(new FileInputStream(file));
        return new UserData(p);
    }
    
    private void store(String prefix, UserDataComponent data) {
        for (Map.Entry<String, String> e : data.getValues().entrySet()) {
            properties.setProperty(key(prefix, e.getKey()), e.getValue());
        }
        dirty = true;
    }
    
    private String key(String p, String rest) {
        return prefix(p) + rest;
    }
    
    public void setAuthData(AuthData data) {
        store("auth", data);
    }
    
    public AuthData getAuthData() {
        // XXX What is the behavior here if the data is missing?
        // What if it's only partially missing?
        return new AuthData(select("auth"));
    }
    
    public void setServer(Server data) {
        store("server", data);
    }

    public Server getServer() {
        return new Server(select("server"));
    }
    
    private String prefix(String p) {
        return p + '.';
    }
    
    private Map<String, String> select(String pre) {
        Map<String, String> m = new HashMap<String, String>();
        for (Map.Entry<Object, Object> e : properties.entrySet()) {
            String key = (String)e.getKey();
            String p = prefix(pre);
            if (key.startsWith(p) && key.length() > p.length()) {
                m.put(key.substring(p.length()), 
                      (String)e.getValue());
            }
        }
        return m;
    }
}
