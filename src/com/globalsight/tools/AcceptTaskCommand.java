package com.globalsight.tools;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

// TODO: Usage
class AcceptTaskCommand extends WebServiceCommand {
    
    @Override
    protected void execute(CommandLine command, UserData userData,
            WebService webService) throws Exception {
        
        if (command.hasOption(JOB)) {
            acceptByJob(command, webService);
        }
        else {
            acceptByTaskId(command, webService);
        }
    }
    
    private void acceptByJob(CommandLine command, WebService webService)
                            throws Exception {
        // If there are spare arguments, they are interpreted 
        // as target locales
        String arg = command.getOptionValue(JOB);
        // Not currently required, just a sanity check
        if (parseLong(arg) == null) {
            usage("Argument to '-'" + JOB + " must be a job id");
        }
        Job job = Job.byId(webService.getJobs(), arg);
        if (job == null) {
            usage("No such job: " + arg);
        }
        // TODO: handle additional arguments as target locales
        for (Workflow wf : job.getWorkflows()) {
            Task task = webService.getCurrentTask(wf);
            webService.acceptTask(task.getId());
            System.out.println("Accepted task " + task.getId() + ": " +
                    task.getName() + " (" + wf.getTargetLocale() + ")");
        }
    }
    
    private void acceptByTaskId(CommandLine command, WebService webService) 
                            throws Exception {
        String[] args = command.getArgs();
        if (args.length != 1) {
            usage(getName() + " [taskId]");
        }
        Long id = parseLong(args[0]);
        if (id == null) {
            usage(getName() + " [taskId]");
        }
        if (webService.acceptTask(id)) {
            System.out.println("Accepted task " + id);
        }
        else {
            // TODO: get an error msg somehow
            System.out.println("Failed to accept task " + id);
        }
    }

    @Override
    public String getDescription() {
        return "Accept an available task by id";
    }

    static final String JOB = "job";
    @SuppressWarnings("static-access")
    static final Option JOB_OPT = OptionBuilder
        .withArgName("jobId")
        .hasArg()
        .withDescription("job id")
        .create(JOB);

    @Override
    public Options getOptions() {
        Options opts = super.getOptions();
        opts.addOption(JOB_OPT);
        return opts;
    }
    
    private Long parseLong(String s) {
        try {
            return Long.valueOf(s);
        }
        catch (NumberFormatException e) {
            return null;
        }
    }
    
}
