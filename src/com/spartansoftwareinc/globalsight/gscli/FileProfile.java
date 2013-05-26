package com.spartansoftwareinc.globalsight.gscli;

import java.util.SortedSet;
import java.util.TreeSet;

public class FileProfile {

    private String id;
    private String name;
    private String description;
    private String l10nProfile;
    private SortedSet<String> fileExtensions = new TreeSet<String>();
    private String sourceLocale;
    private SortedSet<String> targetLocales = new TreeSet<String>();
 
    FileProfile() {
        
    }
    
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getL10nProfile() {
        return l10nProfile;
    }
    public SortedSet<String> getFileExtensions() {
        return fileExtensions;
    }
    public String getSourceLocale() {
        return sourceLocale;
    }
    public SortedSet<String> getTargetLocales() {
        return targetLocales;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setL10nProfile(String l10nProfile) {
        this.l10nProfile = l10nProfile;
    }
    public void setFileExtensions(SortedSet<String> fileExtensions) {
        this.fileExtensions = fileExtensions;
    }
    public void addFileExtension(String fileExtension) {
        fileExtensions.add(fileExtension);
    }
    public void setSourceLocale(String sourceLocale) {
        this.sourceLocale = sourceLocale;
    }
    public void setTargetLocales(SortedSet<String> targetLocales) {
        this.targetLocales = targetLocales;
    }
    public void addTargetLocale(String locale) {
        targetLocales.add(locale);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getName() + "(ID " + getId() + "); ");
        sb.append(getFileExtensions()).append("; ")
          .append(getSourceLocale())
          .append("-->")
          .append(getTargetLocales());
        return sb.toString();
    }
}
