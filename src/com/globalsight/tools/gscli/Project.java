package com.globalsight.tools.gscli;

public class Project {   
    private String id, name, description, pmUser;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPmUser() {
        return pmUser;
    }

    public void setPmUser(String pmUser) {
        this.pmUser = pmUser;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getName() + " (ID " + getId() + "); ");
        sb.append("PM: ").append(getPmUser());
        return sb.toString();
    }
}
