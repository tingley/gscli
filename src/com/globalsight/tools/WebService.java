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
         // TODO: comment, priority, CVSModules, attributes
         // XXX WHere does sourcelocale come from?
        
         HashMap<String, Object> args = new HashMap<String, Object>();
         args.put("jobName", jobName);
         args.put("comment", "");    // nullable?
         args.put("filePaths", new Vector<String>(filePaths)); // Vector
         Vector<String> fpIds = new Vector<String>();
         fpIds.add(fpId);
         args.put("fileProfileIds", fpIds);   // Vector
         Vector<String> targetLocales = new Vector<String>();
         targetLocales.add(targetLocale);
         args.put("targetLocales", targetLocales);
         args.put("cvsModules", Collections.emptyMap());
         // This is a stringified int from 1 to 6??
         args.put("priority", "3");
         // XXX This is specially crafted XML.  See UploadFilesDialog.getAttributesXml
         args.put("attributes", "");
         try {
             getService().createJob(args);
         }
         catch (Exception e) {
             throw new RuntimeException(e);
         }        
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
