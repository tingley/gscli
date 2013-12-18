package com.spartansoftwareinc.globalsight.gscli;

import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

// Usage: add-profile -url=[..] -username=[...] -password=[..]
@SuppressWarnings("static-access")
public class AddProfileCommand extends Command {
    @Override
    public String getDescription() {
        return "store a profile for a GlobalSight server"; 
    }

    static final String URL = "url",
                        USERNAME = "username",
                        PASSWORD = "password",
                        PROFILENAME = "name";
    static final Option URL_OPT = OptionBuilder
                .withArgName(URL)
                .hasArg()
                .isRequired()
                .withDescription("server url")
                .create(URL);
    static final Option USER_OPT = OptionBuilder
                .withArgName(USERNAME)
                .hasArg()
                .isRequired()
                .withDescription("username")
                .create(USERNAME);
    static final Option PASSWORD_OPT = OptionBuilder
                .withArgName(PASSWORD)
                .hasArg()
                .isRequired()
                .withDescription("password")
                .create(PASSWORD);
    static final Option PROFILENAME_OPT = OptionBuilder
                .withArgName(PROFILENAME)
                .hasArg()
                .withDescription("profile name")
                .create(PROFILENAME); 
    
    @Override
    public Options getOptions() {
        Options options = new Options();
        options.addOption(URL_OPT);
        options.addOption(USER_OPT);
        options.addOption(PASSWORD_OPT);
        options.addOption(PROFILENAME_OPT);
        return options;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void handle(CommandLine command, UserData userData) throws Exception {
        List<String> args = command.getArgList();
        if (args.size() != 0) {
            usage();
        }
        String url = command.getOptionValue(URL);
        String username = command.getOptionValue(USERNAME);
        String password = command.getOptionValue(PASSWORD);
        String profileName = command.hasOption(PROFILENAME) ?
                command.getOptionValue(PROFILENAME) : username;
        // TODO: check for collisions
        Profiles profiles = userData.getProfiles();
        profiles.addProfile(
                new Profile(profileName, url, username, password));
        userData.setProfiles(profiles);
    }


}
