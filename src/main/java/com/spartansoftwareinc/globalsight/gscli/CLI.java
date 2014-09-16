package com.spartansoftwareinc.globalsight.gscli;

import java.io.File;
import java.util.Map;
import java.util.Properties;

import net.sundell.cauliflower.*;

public class CLI extends net.sundell.cauliflower.CLI {

    /**
     * Return data file location in the user's home directory.  Note that
     * this file may not exist.
     * @return file location, or null if the file can not be created 
     *     (no home directory set, or directory does not exist)
     */
    @Override
    protected File getUserDataFile() {
        String homeDirPath = System.getProperty("user.home");
        if (homeDirPath == null) {
            return null;
        }
        File homeDir = new File(homeDirPath);
        if (!homeDir.exists()) {
            return null;
        }
        return new File(homeDir, ".globalsight");
    }

    protected GSUserData initializeUserData(Properties properties) {
        return new GSUserData(properties);
    }

    @Override
    protected void registerCommands(Map<String, Class<? extends Command>> commands) {
        commands.put("fileprofiles", FileProfilesCommand.class);
        commands.put("add-profile", AddProfileCommand.class);
        commands.put("create-job", CreateJobCommand.class);
        commands.put("jobs", ShowJobsCommand.class);
        commands.put("workflow", ShowWorkflowCommand.class);
        commands.put("set-default-profile", SetDefaultProfileCommand.class);
        commands.put("accept-task", AcceptTaskCommand.class);
        commands.put("projects", ProjectsCommand.class);
        commands.put("activity-types", ActivityTypesCommand.class);
        commands.put("create-user", CreateUserCommand.class);
        commands.put("show-profiles", ShowProfileCommand.class);
    }

    public static void main(String[] args) {
        new CLI().run(args);
    }

}
