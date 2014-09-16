package com.spartansoftwareinc.globalsight.gscli;

import net.sundell.cauliflower.Command;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

@SuppressWarnings("static-access")
public abstract class GSCommand extends Command {
    @Override
    public Options getOptions() {
        return getDefaultOptions();
    }

    @Override
    protected GSUserData getUserData() {
        return (GSUserData)super.getUserData();
    }

    static final String URL = "url";
    static final Option URL_OPT = OptionBuilder.withArgName("url")
                        .hasArg()
                        .withDescription("GlobalSight URL")
                        .isRequired(false)
                        .create(URL);
    static final String USER = "user";
    static final Option USER_OPT = OptionBuilder.withArgName("user")
                        .hasArg()
                        .withDescription("user name")
                        .isRequired(false)
                        .create(USER);
    static final String PASSWORD = "password";
    static final Option PASSWORD_OPT = OptionBuilder.withArgName("password")
                        .hasArg()
                        .withDescription("password")
                        .isRequired(false)
                        .create(PASSWORD);
    protected Options getDefaultOptions() {
        Options opts = new Options();
        opts.addOption(URL_OPT);
        opts.addOption(USER_OPT);
        opts.addOption(PASSWORD_OPT);
        return opts;
    }
}
