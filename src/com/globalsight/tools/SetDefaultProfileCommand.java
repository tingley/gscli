package com.globalsight.tools;

import java.util.List;

import org.apache.commons.cli.CommandLine;

public class SetDefaultProfileCommand extends Command {

    @Override
    public String getDescription() {
        return "sets the default profile";
    }

    @SuppressWarnings("unchecked")
    @Override
    public void handle(CommandLine command, UserData userData) throws Exception {
        List<String> args = command.getArgList();
        if (args.size() != 1) {
            usage();
        }
        String name = args.get(0);
        Profiles profiles = userData.getProfiles();
        Profile p = profiles.getProfile(name);
        if (p == null) {
            die("No such profile: " + name);
        }
        profiles.setDefaultProfile(p);
        userData.setProfiles(profiles);
        verbose("Set default profile to '" + p.getProfileName() + "'");
    }

}
