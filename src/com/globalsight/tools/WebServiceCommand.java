package com.globalsight.tools;

import java.util.Date;

import javax.xml.stream.XMLInputFactory;

import org.apache.commons.cli.CommandLine;

public abstract class WebServiceCommand extends Command {

    // Default implementation that actually wants a webservice.
    public void handle(CommandLine command, UserData userData) 
                                throws Exception {
        String url = null;
        if (command.hasOption(URL)) {
            url = command.getOptionValue(URL);
        }
        else {
            url = userData.getServer().getUrl(); 
        }
        if (url == null) {
            die("No URL specified.  Specify with --url=<url>");
        }
        WebService ws = new WebService(url, XMLInputFactory.newInstance());
        // XXX Ugly
        String token = getAuthToken(userData);
        if (token == null) {
            token = authorize(ws, userData);
            userData.setSession(new Session(token));
        }
        verbose("Using auth token " + token);
        ws.setToken(token);
        // TODO: handle auth failure?
        execute(command, userData, ws);
    }

    protected String authorize(WebService ws, UserData userData) 
                        throws Exception {
        AuthData auth = userData.getAuthData();
        if (auth.getUsername() == null) {
            die("No username specified.  Specify with --user=<username>");
        }
        if (auth.getPassword() == null) {
            die("No password specified.  Specify with --password=<password>");
        }
        return ws.login(auth.getUsername(), auth.getPassword());
    }
    
    protected String getAuthToken(UserData userData) throws Exception {
        Session session = userData.getSession();
        // XXX Is this the right place to validate the timestamp?
        String token = session.getToken();
        if (token == null) {
            return null;
        }
        Date timestamp = session.getDate(), now = new Date();
        if (!timestamp.before(now)) {
            // Strange date corruption
            return null; 
        }
        long delta = (now.getTime() - timestamp.getTime());
        // Expire the token
        if (delta > ONE_HOUR) {
            return null;
        }
        return token;
    }
    
    public static final long ONE_HOUR = 1000 * 60 * 60;
    
    protected abstract void execute(CommandLine command, UserData userData, 
                WebService webService) throws Exception;
    
}
