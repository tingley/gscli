package com.globalsight.tools;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Profiles implements UserDataComponent {

    private Map<String, Profile> profiles = 
                new HashMap<String, Profile>();
    private Profile defaultProfile;
    
    static final String PROFILE = "profile";
    static final String DEFAULT = "default";
    
    public Profiles(Map<String, String> props) {
        Map<String, Map<String, String>> m = 
            new HashMap<String, Map<String, String>>();
        for (Map.Entry<String, String> e : props.entrySet()) {
            String[] parts = e.getKey().split("\\.");
            if (parts.length != 3 || !parts[0].equals(PROFILE)) {
                continue; // TODO: warn for non-DEFAULT case
            }
            Map<String, String> profileValues = m.get(parts[1]);
            if (profileValues == null) {
                profileValues = new HashMap<String, String>();
                m.put(parts[1], profileValues);
            }
            profileValues.put(parts[2], e.getValue());
        }
        for (Map.Entry<String, Map<String, String>> e : m.entrySet()) {
            profiles.put(e.getKey(), new Profile(e.getKey(), e.getValue()));
        }
        String defaultProfileName = props.get(DEFAULT);
        if (defaultProfileName != null) {
            defaultProfile = profiles.get(defaultProfileName);
        }
    }
    
    @Override
    public Map<String, String> getValues() {
        Map<String, String> props = new HashMap<String, String>();
        for (Profile p : profiles.values()) {
            Map<String, String> pv = p.getValues();
            for (Map.Entry<String, String> e : pv.entrySet()) {
                props.put(PROFILE + "." + e.getKey(), e.getValue());
            }
        }
        if (defaultProfile != null) {
            props.put(DEFAULT, defaultProfile.getProfileName());
        }
        return props;
    }

    public void addProfile(Profile profile) {
        profiles.put(profile.getProfileName(), profile);
        // First profile automatically becomes the default
        if (profiles.size() == 1) {
            defaultProfile = profile;
        }
    }
    
    public Profile getProfile(String profileName) {
        return profiles.get(profileName);
    }
    
    public Profile getDefaultProfile() {
        return defaultProfile;
    }
    
    public Collection<Profile> getAllProfiles() {
        return profiles.values();
    }
}
