package com.spartansoftwareinc.globalsight.gscli;

import org.apache.commons.cli.CommandLine;

public class VersionCommand extends GSCommand {

    @Override
    public String getDescription() {
        return "prints version information";
    }

    @Override
    public void handle(CommandLine command) {
        getCLI().getOutputWriter().println(Version.PROJECT_NAME + 
                " version " + Version.PROJECT_VERSION + " [" + Version.SOURCE_VERSION + "]");
    }

}
