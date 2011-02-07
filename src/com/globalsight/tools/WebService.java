package com.globalsight.tools;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.globalsight.www.webservices.Ambassador;
import com.globalsight.www.webservices.AmbassadorServiceLocator;

public class WebService {

    private String url;
    private Ambassador service;
    private String authToken;
    
    public WebService(String baseUrl) {
        StringBuilder sb = new StringBuilder();
        sb.append(baseUrl);
        if (!baseUrl.endsWith("/")) {
            sb.append("/");
        }
        sb.append("/services/AmbassadorWebService");
        url = sb.toString();
    }
    
    public String login(String username, String password) {
        try {
            return getService().login(username, password);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public String getFileProfileData() {
        try {
            return getService().getFileProfileInfoEx(getToken());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public String getUniqueJobName(String jobName) {
        try {
            return getService().getUniqueJobName(getToken(), jobName);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    // webService.uploadFile(filePath, jobName, fileProfileId, bytes);
    public void uploadFile(String path, String jobName, String fpId, byte[] data) {
        try {
            HashMap<String, Object> args = new HashMap<String, Object>();
            args.put("accessToken", getToken());
            args.put("filePath", path);
            args.put("jobName", jobName);
            args.put("fileProfileId", fpId);
            args.put("bytes", data);
            getService().uploadFile(args);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public void createJob(String jobName, List<String> filePaths,
                String fpId, String targetLocale) {
         try {
             getService().createJob(getToken(), jobName, "", 
                     filePaths.get(0), fpId, targetLocale);
         }
         catch (Exception e) {
             throw new RuntimeException(e);
         }
         /* For reference, this is the minimal attr xml
         <?xml version="1.0" encoding="UTF-8" standalone="yes"?>\n<attributes/>
         */
    }

    protected Ambassador getService() {
        if (service != null) {
            return service;
        }
        try {
            AmbassadorServiceLocator locator = new AmbassadorServiceLocator();
            this.service = locator.getAmbassadorWebService(new URL(url));
            return service;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private String getToken() {
        if (authToken == null) {
            throw new IllegalStateException("No auth token");
        }
        return authToken;
    }
    
    public void setToken(String token) {
        this.authToken = token;
    }
}
