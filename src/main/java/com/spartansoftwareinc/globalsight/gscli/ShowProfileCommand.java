package com.spartansoftwareinc.globalsight.gscli;

import java.util.Formatter;

import org.apache.commons.cli.CommandLine;

public class ShowProfileCommand extends Command {

    @Override
    public String getDescription() {
        return "show all stored profiles";
    }

    @Override
    public void handle(CommandLine command, UserData userData) throws Exception {
        Profiles profiles = userData.getProfiles();
        StringBuilder sb = new StringBuilder();
        Formatter f = new Formatter(sb);
        f.format("Available Profiles:\n");
        for (Profile profile : profiles.getAllProfiles()) {
            f.format("%-20s%s @ %s\n", profile.getProfileName(),
                     profile.getUsername(), profile.getUrl());
        }
        f.flush();
        f.close();
        out(sb.toString());
    }

}
