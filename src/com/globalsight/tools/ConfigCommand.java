package com.globalsight.tools;

import java.util.List;

import org.apache.commons.cli.CommandLine;

// Usage: config [name] [value]
// Possible [name]: 'url', 'user', 'password'
public class ConfigCommand extends Command {

    @Override
    public String getDescription() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getName() {
        return "config";
    }

    @SuppressWarnings("unchecked")
    @Override
    public void handle(CommandLine command, UserData userData) throws Exception {
        List<String> args = command.getArgList();
        if (args.size() != 2) {
            usage();
        }
        String name = args.get(0);
        if (name.equals("url")) {
            userData.setServer(new Server(args.get(1)));
        }
        else if (name.equals("user")) {
            // XXX This is really hacky
            AuthData a = userData.getAuthData();
            a.setUsername(args.get(1));
            userData.setAuthData(a);
        }
        else if (name.equals("password")) {
            AuthData a = userData.getAuthData();
            a.setPassword(args.get(1));
            userData.setAuthData(a);
        }
        else {
            usage("Unrecognized parameter '" + name + "'");
        }
    }

}
