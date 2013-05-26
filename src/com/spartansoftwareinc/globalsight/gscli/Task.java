package com.spartansoftwareinc.globalsight.gscli;

public class Task {
    private long wfId; // XXX replace with real object
    private long id;
    private String name;
    private String state; // enum?

    Task(long wfId, long id, String name, String state) {
        this.wfId = wfId;
        this.id = id;
        this.name = name;
        this.state = state;
    }

    public long getWorkflowId() {
        return wfId;
    }
    public void setWorkflowId(long wfId) {
        this.wfId = wfId;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
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

}
