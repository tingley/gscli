package com.globalsight.tools;

import java.util.ArrayList;
import java.util.List;

public class Job {
    private String id, name, state, displayState,
                   priority, wordCount, sourceLang, dueDate;
    private List<SourcePage> sourcePages = new ArrayList<SourcePage>();
    private List<Workflow> workflows = new ArrayList<Workflow>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDisplayState() {
        return displayState;
    }

    public void setDisplayState(String displayState) {
        this.displayState = displayState;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getWordCount() {
        return wordCount;
    }

    public void setWordCount(String wordCount) {
        this.wordCount = wordCount;
    }

    public String getSourceLang() {
        return sourceLang;
    }

    public void setSourceLang(String sourceLang) {
        this.sourceLang = sourceLang;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
    
    public List<SourcePage> getSourcePages() {
        return sourcePages;
    }
    
    public List<Workflow> getWorkflows() {
        return workflows;
    }
    
    public void addSourcePage(String id, String externalPageId) {
        sourcePages.add(new SourcePage(id, externalPageId));
    }
    
    public void addWorkflow(long id, String targetLocale) {
        workflows.add(new Workflow(id, targetLocale));
    }
    
    static class SourcePage {
        SourcePage(String id, String externalPageId) {
            this.id = id;
            this.externalPageId = externalPageId;
        }
        
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getExternalPageId() {
            return externalPageId;
        }

        public void setExternalPageId(String externalPageId) {
            this.externalPageId = externalPageId;
        }

        private String id, externalPageId;
    }
}
