package com.globalsight.tools;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import com.globalsight.www.webservices.Ambassador;
import com.globalsight.www.webservices.AmbassadorServiceLocator;
import com.globalsight.www.webservices.AmbassadorWebServiceSoapBindingStub;
import com.globalsight.www.webservices.WebServiceException;

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
