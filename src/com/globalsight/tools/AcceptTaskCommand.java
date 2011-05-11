package com.globalsight.tools;

import org.apache.commons.cli.CommandLine;

public class AcceptTaskCommand extends WebServiceCommand {

    @Override
    protected void execute(CommandLine command, UserData userData,
            WebService webService) throws Exception {
        
        String[] args = command.getArgs();
        if (args.length != 1) {
            usage(getName() + " [taskId]");
        }
        Long id = null;
        try {
            id = Long.valueOf(args[0]);
        }
        catch (Exception e) {
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

}
