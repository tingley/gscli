package com.globalsight.tools;

import java.util.Formatter;

import org.apache.commons.cli.CommandLine;

// TODO: add filters
public class ShowJobsCommand extends WebServiceCommand {

    @Override
    protected void execute(CommandLine command, UserData userData,
            WebService webService) throws Exception {
        for (Job job : webService.getJobs()) {
            printShort(job);
        }
    }

    void printShort(Job job) {
        Formatter f = new Formatter(System.out);
        // XXX Hack: just displaying the first WFId
        f.format("%-8s%-4s%-14s%s\n", job.getId(), 
        		job.getWorkflows().get(0).getId(), job.getDisplayState(), 
                 job.getName());
    }
    
    @Override
    public String getDescription() {
        return "List jobs for this company";
    }
}
