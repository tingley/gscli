package com.globalsight.tools;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

public abstract class Command {

    public abstract String getDescription();
    
    public abstract Options getOptions();
    
    protected abstract void execute(CommandLine command) 
            throws Exception;
    
    public abstract void usage();
}
