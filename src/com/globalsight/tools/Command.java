package com.globalsight.tools;

import java.io.PrintStream;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.HelpFormatter;

@SuppressWarnings("static-access")
public abstract class Command {

    public abstract String getName();
    
    public abstract String getDescription();
    
    public Options getOptions() {
        return getDefaultOptions();
    }
    
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
    
    // Print usage for this command to stderr and die
    protected void usage() {
        HelpFormatter f = new HelpFormatter();
        f.printHelp("gstool " + getUsageLine(), getOptions());
        printExtraHelp(System.err);
        System.exit(1);
    }
    
    protected String getUsageLine() {
        return getName() + " [options]";
    }
    
    protected void printExtraHelp(PrintStream out) {
        
    }
    
    // Prints usage info as well as an additional message, then die
    protected void usage(String message) {
        System.err.println(message);
        usage();
    }
    
    protected void die(String message) {
        System.err.println(message);
        System.exit(1);
    }
    
    static final String URL = "url";
    static final Option URL_OPT = OptionBuilder.withArgName("url")
                        .hasArg()
                        .withDescription("GlobalSight URL")
                        .isRequired(false)
                        .create(URL);
    static final String USER = "user";
    static final Option USER_OPT = OptionBuilder.withArgName("user")
                        .hasArg()
                        .withDescription("user name")
                        .isRequired(false)
                        .create(USER);
    static final String PASSWORD = "password";
    static final Option PASSWORD_OPT = OptionBuilder.withArgName("password")
                        .hasArg()
                        .withDescription("password")
                        .isRequired(false)
                        .create(PASSWORD);
    protected Options getDefaultOptions() {
        Options opts = new Options();
        opts.addOption(URL_OPT);
        opts.addOption(USER_OPT);
        opts.addOption(PASSWORD_OPT);
        return opts;
    }
}
