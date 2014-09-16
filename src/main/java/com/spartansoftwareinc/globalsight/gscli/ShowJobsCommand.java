package com.spartansoftwareinc.globalsight.gscli;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import org.apache.commons.cli.CommandLine;

// TODO: add filters
public class ShowJobsCommand extends WebServiceCommand {

    @Override
    protected void execute(CommandLine command, GSUserData userData,
        WebService webService) throws RemoteException {
        String[] args = command.getArgs();
        List<Job> jobs = webService.getJobs();
        if (args.length == 0) {
            showAllJobs(jobs);
        }
        else {
            List<Job> jobsToShow = new ArrayList<Job>();
            for (String arg: args) {
                Job job = Job.find(jobs, arg);
                if (job != null) {
                    jobsToShow.add(job);
                    continue;
                }
                System.out.println("Skipping '" + arg + "': no such job");
            }
            if (jobsToShow.size() == 0) {
                return;
            }
            for (Job j : jobsToShow) {
                System.out.println("Job " + j.getId() + ": " + j.getName());
                for (Workflow f : j.getWorkflows()) {
                    Task t = webService.getCurrentTask(f);
                    System.out.println("\t" + f.getTargetLocale() + 
                                " - Task " + t.getId() + " " + t.getName() 
                                + " " + t.getState());
                }
            }
        }
    }
    
    void showAllJobs(List<Job> jobs) throws RemoteException {
        printHeader();
        for (Job job : jobs) {
            printShort(job);
        }
    }
    void printHeader() {
        Formatter f = new Formatter(System.out);
        f.format("%-8s%-8s%-12s%s\n", "ID", "WFID", "State", "Name");
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
