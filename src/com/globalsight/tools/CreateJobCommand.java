package com.globalsight.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.CommandLine;

@SuppressWarnings("static-access")
public class CreateJobCommand extends WebServiceCommand {

    @Override
    protected void execute(CommandLine command, UserData userData,
            WebService webService) throws Exception {
        // Get a unique job name 
        String baseJobName = command.getOptionValue(JOBNAME);
        String jobName = webService.getUniqueJobName(baseJobName);
        verbose("Got unique job name: " + jobName);
        // XXX Need to convert file profile to ID
        String fileProfileId = command.getOptionValue(FILEPROFILE);
        try { // Temporary code
            long l = Long.valueOf(fileProfileId);
        }
        catch (NumberFormatException e) {
            die("For now, --fileprofile must specify a numeric id (" +
                    fileProfileId + ")");
        }
        
        // Convert all remaining arguments to files
        List<File> files = new ArrayList<File>();
        for (String path : command.getArgs()) {
            File f = new File(path);
            if (!f.exists() || f.isDirectory()) {
                die("Not a file: " + f);
            }
            files.add(f);
        }
        // TODO: Each file needs to be uploaded in 5M chunks
        // TODO: is there some sort of initial job creation thing to do?
        // Followed by a 'ok, we're done' call?
        // --> yes, it's the 'createJob' call
        // TODO: attribute files and supplemental files and comments
    }

    static final String SOURCE = "source", 
                        TARGET = "target",
                        FILEPROFILE = "fileprofile",
                        JOBNAME = "name";
    static final Option SOURCE_OPT = OptionBuilder
        .withArgName("sourceLocale")
        .hasArg()
        .withDescription("source locale code")
        .isRequired()
        .create(SOURCE);
    static final Option TARGET_OPT = OptionBuilder
        .withArgName("targetLocale")
        .hasArg()
        .withDescription("target locale code")
        .isRequired()
        .create(TARGET);
    static final Option FILEPROFILE_OPT = OptionBuilder
        .withArgName("fileProfile")
        .hasArg()
        .withDescription("file profile to use")
        .isRequired() // Would be nice to get rid of this
        .create(FILEPROFILE);
    static final Option JOBNAME_OPT = OptionBuilder
        .withArgName("jobName")
        .hasArg()
        .withDescription("job name")
        .isRequired()
        .create(JOBNAME);

    @Override
    public Options getOptions() {
        Options opts = getDefaultOptions();
        opts.addOption(SOURCE_OPT);
        opts.addOption(TARGET_OPT);
        opts.addOption(FILEPROFILE_OPT);
        opts.addOption(JOBNAME_OPT);
        return opts;
    }
    
    @Override
    public String getDescription() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getName() {
        return "create-job";
    }

}
