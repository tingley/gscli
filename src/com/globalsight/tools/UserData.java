package com.globalsight.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.Properties;

public class UserData {

    private Properties properties = new Properties();
    
    public UserData() {
    }
    protected UserData(Properties properties) {
        this.properties = properties;
    }
    
    public void store(File file) throws IOException {
        // XXX This is wrong, it needs to be in ISO-8859-1
        // Also, does this do output escaping for me?
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
    }
    
    private String key(String prefix, String rest) {
        return prefix + '.' + rest;
    }
    
    public void setAuthData(AuthData data) {
        store("auth", data);
    }
    
    public AuthData getAuthData() {
        // XXX What is the behavior here if the data is missing?
        // What if it's only partially missing?
    }
}
