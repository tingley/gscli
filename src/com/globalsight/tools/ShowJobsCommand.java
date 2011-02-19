package com.globalsight.tools;

import org.apache.commons.cli.CommandLine;

public class ShowJobsCommand extends WebServiceCommand {

    @Override
    protected void execute(CommandLine command, UserData userData,
            WebService webService) throws Exception {
        String x = webService.getJobs();
        System.out.println(x);
    }

    @Override
    public String getDescription() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getName() {
        return "show-jobs";
    }

}
