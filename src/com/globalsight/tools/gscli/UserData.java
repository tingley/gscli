package com.globalsight.tools.gscli;

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
            if (e.getValue() == null) {
                continue;
            }
            properties.setProperty(key(prefix, e.getKey()), e.getValue());
        }
        dirty = true;
    }
     
    
    public Profiles getProfiles() {
        return new Profiles(select("profiles"));

    }
    
    public void setProfiles(Profiles profiles) {
        store("profiles", profiles);
    }
    
    public void setSessions(Sessions sessions) { 
        store("sessions", sessions);
    }
    
    public Sessions getSessions() {
        return new Sessions(select("sessions"));
    }
    
    private String key(String p, String rest) {
        return prefix(p) + rest;
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
