package com.spartansoftwareinc.globalsight.gscli;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileProfileResolver {

    private List<FileProfile> profiles;
    
    public FileProfileResolver(List<FileProfile> profiles) {
        this.profiles = profiles;
    }
    
    /**
     * Return a list of all possible file profiles for a given
     * file.
     */
    public List<FileProfile> findByFileExtension(File file) {        
        List<FileProfile> fps = new ArrayList<FileProfile>();
        String fileExtension = getFileExtension(file).toLowerCase();
        for (FileProfile fp : profiles) {
            if (fp.getFileExtensions().contains(fileExtension)) {
                fps.add(fp);
            }
        }
        return fps;
    }
    
    private String getFileExtension(File file) {
        String basename = file.getName();
        int i = basename.lastIndexOf('.');
        if (i == -1 || i + 1 >= basename.length()) {
            return "";
        }
        return basename.substring(i + 1, basename.length());
    }

    public FileProfile findByName(String name) {
        for (FileProfile fp : profiles) {
            if (fp.getName().equalsIgnoreCase(name)) {
                return fp;
            }
        }
        return null;
    }
    
    public FileProfile findById(String id) {
        for (FileProfile fp : profiles) {
            if (fp.getId().equals(id)) {
                return fp;
            }
        }
        return null;
    }
}
