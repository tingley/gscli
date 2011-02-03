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
    
    // XXX Clean up exceptions
    protected String getToken() {
        if (authToken != null) {
            return authToken;
        }
        try {
            // TODO: handle auth
            String accessToken = getService().login("tingley", "wsisgreat");
            System.out.println("Got token: " + accessToken);
            // TODO: put token somewhere, return service
            return accessToken;
        }
        catch (Exception e) {
            throw new RuntimeException(e); // haha omg
        }
    }
}
