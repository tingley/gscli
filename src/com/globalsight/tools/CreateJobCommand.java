package com.globalsight.tools;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.CommandLine;

@SuppressWarnings("static-access")
// TODO: can't I just let it specify the fileprofile and infer the locales?  yes
// XXX Wow, there is problem with webservices, the absolute file path isn't stripping
// the '../..' out?
// TODO: I should assume the fileprofile target locale by default, but allow overrides
// via --target
public class CreateJobCommand extends WebServiceCommand {

    @Override
    protected void execute(CommandLine command, UserData userData,
            WebService webService) throws Exception {
        // XXX Need to convert file profile to ID
        String fileProfileId = command.getOptionValue(FILEPROFILE);
        try { // Temporary code
            long l = Long.valueOf(fileProfileId);
        }
        catch (NumberFormatException e) {
            die("For now, --fileprofile must specify a numeric id (" +
                    fileProfileId + ")");
        }
        
        // TODO source and target locale
        String targetLocale = command.getOptionValue(TARGET);
        
        // Convert all remaining arguments to files
        List<File> files = new ArrayList<File>();
        for (String path : command.getArgs()) {
            File f = new File(path);
            if (!f.exists() || f.isDirectory()) {
                die("Not a file: " + f);
            }
            files.add(f);
        }

        // Get a unique job name 
        String baseJobName = command.getOptionValue(JOBNAME);
        String jobName = webService.getUniqueJobName(baseJobName);
        verbose("Got unique job name: " + jobName);
        List<String> filePaths = new ArrayList<String>();
        for (File f : files) {
            filePaths.add(uploadFile(f, jobName, fileProfileId, webService));
        }
        webService.createJob(jobName, filePaths, fileProfileId, targetLocale);
    }
    
    private static long MAX_SEND_SIZE = 5 * 1000 * 1024; // 5M
    
    // Returns the filepath that was sent to the server
    String uploadFile(File file, String jobName, String fileProfileId,
                    WebService webService) throws Exception {
        String filePath = file.getAbsolutePath();
        // XXX This is so janky - why do we have to do this?
        filePath = filePath.substring(filePath.indexOf(File.separator) + 1);     
        verbose("Uploading " + filePath + " to job " + jobName);
        InputStream is = null;
        try {
            long bytesRemaining = file.length();
            is = new BufferedInputStream(new FileInputStream(file));
            while (bytesRemaining > 0) {
                // Safe cast because it's bounded by MAX_SEND_SIZE
                int size = (int)Math.min(bytesRemaining, MAX_SEND_SIZE);
                byte[] bytes = new byte[size];
                int count = is.read(bytes);
                if (count <= 0) {
                    break;
                }
                bytesRemaining -= count;
                verbose("Uploading chunk 1: " + size + " bytes");
                webService.uploadFile(filePath, jobName, fileProfileId, bytes);
            }
            verbose("Finished uploading " + filePath);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }      
        finally {
            if (is != null) {
                is.close();
            }
        }
        return filePath;
    }
    
    static final String TARGET = "target",
                        FILEPROFILE = "fileprofile",
                        JOBNAME = "name";
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
