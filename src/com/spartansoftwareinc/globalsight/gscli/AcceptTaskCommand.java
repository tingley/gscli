package com.spartansoftwareinc.globalsight.gscli;

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
        Job job = Job.find(webService.getJobs(), arg);
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
        if (args.length == 0) {
            usage();
        }
        List<Long> ids = new ArrayList<Long>();
        for (String arg : args) {
            Long id = parseLong(arg);
            if (id == null) {
                usage("Not a task id: " + arg);
            }
            ids.add(id);
        }
        for (Long id : ids) {
            if (webService.acceptTask(id)) {
                System.out.println("Accepted task " + id);
            }
            else {
                // TODO: get an error msg somehow
                System.out.println("Failed to accept task " + id);
            }
        }
    }

    @Override
    protected String getUsageLine() {
        return super.getUsageLine() + " [task ids], or use -" + JOB; 
    }
        
    @Override
    public String getDescription() {
        return "Accept an available task by id";
    }

    static final String JOB = "job";
    @SuppressWarnings("static-access")
    static final Option JOB_OPT = OptionBuilder
        .withArgName("jobId/jobName")
        .hasArg()
        .withDescription("job id - accept tasks from this job (by name or id)")
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
