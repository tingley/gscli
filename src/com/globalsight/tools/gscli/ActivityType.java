package com.globalsight.tools.gscli;

public class ActivityType implements SimpleListParser.Settable {
    private String id, name, description;

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

    @Override
    public void set(String name, String value) {
        if (name.equals("id")) {
            setId(value);
        }
        else if (name.equals("name")) {
            setName(value);
        }
        else if (name.equals("description")) {
            setDescription(value);
        }
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getName() + " (ID " + getId() + ")");
        return sb.toString();
    }

}
