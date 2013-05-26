package com.spartansoftwareinc.globalsight.gscli;

public class Workflow {
    private long id;
    private String targetLocale;
    
    Workflow(long id, String targetLocale) {
        this.id = id;
        this.targetLocale = targetLocale;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTargetLocale() {
        return targetLocale;
    }

    public void setTargetLocale(String targetLocale) {
        this.targetLocale = targetLocale;
    }
}
