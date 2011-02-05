package com.globalsight.tools;

import java.util.List;

import org.apache.commons.cli.CommandLine;

public class SetServerCommand extends Command {

    @SuppressWarnings("unchecked")
    @Override
    public void handle(CommandLine command, UserData userData) 
                            throws Exception {
        List<String> args = command.getArgList();
        if (args.size() != 1) {
            usage();
        }
        userData.setServer(new Server(args.get(0)));
    }

    @Override
    public String getDescription() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getName() {
        return "set-server";
    }

}
