package com.spartansoftwareinc.globalsight.gscli;

import java.util.Properties;

import net.sundell.cauliflower.UserData;

public class GSUserData extends UserData {

    public GSUserData() {
        super();
    }
    
    protected GSUserData(Properties properties) {
        super(properties);
    }

    public Profiles getProfiles() {
        return new Profiles(fetch("profiles"));
    }
    
    public void setProfiles(Profiles profiles) {
        store("profiles", profiles);
    }
    
    public void setSessions(Sessions sessions) { 
        store("sessions", sessions);
    }
    
    public Sessions getSessions() {
        return new Sessions(fetch("sessions"));
    }
}
