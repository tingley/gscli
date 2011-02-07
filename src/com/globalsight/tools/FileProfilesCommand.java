package com.globalsight.tools;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

import org.apache.commons.cli.Option;

@SuppressWarnings("static-access")
public class FileProfilesCommand extends WebServiceCommand {

    @Override
    public String getName() {
        return "fileprofiles";
    }

    @Override
    public void usage() {
        // TODO Auto-generated method stub

    }

    @Override
    public String getDescription() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void execute(CommandLine command, UserData userData,
                        WebService webService) throws Exception {
        List<Filter> filters = getFilters(command);
        List<FileProfile> profiles = webService.getFileProfiles();
PROFILE:
        for (FileProfile fp : profiles) {
            for (Filter f : filters) {
                if (!f.accept(fp)) {
                    continue PROFILE;
                }
            }
            System.out.println(fp);
        }
    }

    interface Filter {
        boolean accept(FileProfile fp);
    }
    
    static final String SOURCE = "source",
                        TARGET = "target",
                        EXTENSION = "extension";
    static final Option SOURCE_OPT = OptionBuilder
            .withArgName("sourceLocale")
            .hasArg()
            .withDescription("source locale code")
            .create(SOURCE);
    static final Option TARGET_OPT = OptionBuilder
            .withArgName("targetLocale")
            .hasArg()
            .withDescription("target locale code")
            .create(TARGET);
    static final Option EXTENSION_OPT = OptionBuilder
            .withArgName("file_extension")
            .hasArg()
            .withDescription("file extension")
            .create(EXTENSION);
    
    @Override
    public Options getOptions() {
        Options opts = getDefaultOptions();
        opts.addOption(SOURCE_OPT);
        opts.addOption(TARGET_OPT);
        opts.addOption(EXTENSION_OPT);
        return opts;
    }
    
    private List<Filter> getFilters(CommandLine command) {
        List<Filter> filters = new ArrayList<Filter>();
        if (command.hasOption(SOURCE)) {
            final String val = command.getOptionValue(SOURCE);
            if (val != null) {
                filters.add(new Filter() {
                    @Override
                    public boolean accept(FileProfile fp) {
                        return fp.getSourceLocale().equals(val);
                    }
                });
            }
        }
        if (command.hasOption(TARGET)) {
            final String val = command.getOptionValue(TARGET);
            if (val != null) {
                filters.add(new Filter() {
                    @Override
                    public boolean accept(FileProfile fp) {
                        return fp.getTargetLocales().contains(val);
                    }
                });
            }
        }
        if (command.hasOption(EXTENSION)) {
            final String val = command.getOptionValue(EXTENSION);
            if (val != null) {
                filters.add(new Filter() {
                    @Override
                    public boolean accept(FileProfile fp) {
                        return fp.getFileExtensions().contains(val);
                    }
                });
            }
        }
        return filters;
    }
}
