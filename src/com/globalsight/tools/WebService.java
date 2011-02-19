package com.globalsight.tools;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

import net.sundell.snax.SNAXUserException;

import com.globalsight.www.webservices.Ambassador;
import com.globalsight.www.webservices.AmbassadorServiceLocator;

public class WebService {

    private String url;
    private Ambassador service;
    private String authToken;
    private XMLInputFactory factory;
    
    public WebService(String baseUrl, XMLInputFactory factory) {
        StringBuilder sb = new StringBuilder();
        sb.append(baseUrl);
        if (!baseUrl.endsWith("/")) {
            sb.append("/");
        }
        sb.append("/services/AmbassadorWebService");
        url = sb.toString();
        this.factory = factory;
    }
    
    public String login(String username, String password) throws RemoteException {
        return getService().login(username, password);
    }
    
    public List<FileProfile> getFileProfiles() throws RemoteException {
        try {
            String fileProfileInfoEx = getService().getFileProfileInfoEx(getToken());
            return new FileProfilesParser(factory).parse(fileProfileInfoEx);
        }
        catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
        catch (SNAXUserException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Job> getJobs() throws RemoteException {
        try {
            String jobsXml = getService().fetchJobsPerCompany(getToken());
            return new JobsParser(factory).parse(jobsXml);
        }
        catch (XMLStreamException e) {
 
            throw new RuntimeException(e);
        }
        catch (SNAXUserException e) {
            throw new RuntimeException(e);
        }
    }
    
    public String getUniqueJobName(String jobName) throws RemoteException {
        return getService().getUniqueJobName(getToken(), jobName);
    }
    
    // webService.uploadFile(filePath, jobName, fileProfileId, bytes);
    public void uploadFile(String path, String jobName, String fpId, 
                byte[] data) throws RemoteException {
        HashMap<String, Object> args = new HashMap<String, Object>();
        args.put("accessToken", getToken());
        args.put("filePath", path);
        args.put("jobName", jobName);
        args.put("fileProfileId", fpId);
        args.put("bytes", data);
        getService().uploadFile(args);
    }
    
    /**
     * Create a job, using the default target locales for the 
     * file profile.
     */
    public void createJob(String jobName, List<String> filePaths,
                FileProfile fileProfile) throws RemoteException {
         getService().createJob(getToken(), jobName, "", 
                 serializeStrings(filePaths), fileProfile.getId(),
                 serializeStrings(fileProfile.getTargetLocales()));
         /* For reference, this is the minimal attr xml
         <?xml version="1.0" encoding="UTF-8" standalone="yes"?>\n<attributes/>
         */
    }

    private String serializeStrings(Collection<String> strings) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String s : strings) {
            if (!first) {
                sb.append('|');
            }
            else {
                first = false;
            }
            sb.append(s);
        }
        return sb.toString();
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
