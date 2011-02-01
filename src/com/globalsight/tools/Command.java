package com.globalsight.tools;

import java.io.PrintStream;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.HelpFormatter;

public abstract class Command {

    public abstract String getName();
    
    public abstract String getDescription();
    
    public abstract Options getOptions();
    
    protected abstract void execute(CommandLine command) 
            throws Exception;
    
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
}
