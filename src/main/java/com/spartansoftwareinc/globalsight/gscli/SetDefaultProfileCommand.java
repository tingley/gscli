package com.spartansoftwareinc.globalsight.gscli;

import java.util.List;

import org.apache.commons.cli.CommandLine;

public class SetDefaultProfileCommand extends GSCommand {

    @Override
    public String getDescription() {
        return "sets the default profile";
    }

    @SuppressWarnings("unchecked")
    @Override
    public void handle(CommandLine command) {
        List<String> args = command.getArgList();
        if (args.size() != 1) {
            usage();
        }
        String name = args.get(0);
        Profiles profiles = getUserData().getProfiles();
        Profile p = profiles.getProfile(name);
        if (p == null) {
            die("No such profile: " + name);
        }
        profiles.setDefaultProfile(p);
        getUserData().setProfiles(profiles);
        verbose("Set default profile to '" + p.getProfileName() + "'");
    }

}
