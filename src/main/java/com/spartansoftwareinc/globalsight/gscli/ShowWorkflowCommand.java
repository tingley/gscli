package com.spartansoftwareinc.globalsight.gscli;

import java.rmi.RemoteException;

import org.apache.commons.cli.CommandLine;

public class ShowWorkflowCommand extends WebServiceCommand {

    @Override
    protected void execute(CommandLine command, GSUserData userData,
            WebService webService) throws RemoteException {
        String[] args = command.getArgs();
        if (args.length != 1) {
            usage(getName() + " [wfid]");
        }
        Long id = null;
        try {
            id = Long.valueOf(args[0]);
        }
        catch (Exception e) {
            usage(getName() + " [wfid]");
        }
        String xml = webService.getWorkflow(id);
        System.out.println(xml);
    }

    @Override
    public String getDescription() {
        return "DEBUG: dump webservice workflow xml";
    }

}
