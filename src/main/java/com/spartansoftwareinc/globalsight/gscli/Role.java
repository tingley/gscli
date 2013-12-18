package com.spartansoftwareinc.globalsight.gscli;

import java.util.ArrayList;
import java.util.List;

public class Role {

    private String sourceLocale, targetLocale;
    private List<ActivityType> activities = new ArrayList<ActivityType>();
    
    public String getSourceLocale() {
        return sourceLocale;
    }
    public void setSourceLocale(String sourceLocale) {
        this.sourceLocale = sourceLocale;
    }
    public String getTargetLocale() {
        return targetLocale;
    }
    public void setTargetLocale(String targetLocale) {
        this.targetLocale = targetLocale;
    }
    
    public void addActivity(ActivityType activity) {
        activities.add(activity);
    }
    public List<ActivityType> getActivities() {
        return activities;
    }
    
}
