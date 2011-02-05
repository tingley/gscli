package com.globalsight.tools;

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
        WebService ws = new WebService(url);
        // TODO handle auth
        execute(command, userData, ws);
    }
   
    protected abstract void execute(CommandLine command, UserData userData, 
                WebService webService) throws Exception;
    
}
