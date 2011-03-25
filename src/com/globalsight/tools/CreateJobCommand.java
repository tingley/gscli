package com.globalsight.tools;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

/**
 * Create a job.  By default, use all the target locales
 * indicated by the file profile (selected by name or id).  
 * Generate a job name based on the name of the first file uploaded,
 * unless one is specified on the command line.
 */
@SuppressWarnings("static-access")
// TODO: I should assume the fileprofile target locale by default, but allow overrides
// via --target
public class CreateJobCommand extends WebServiceCommand {

    @Override
    protected void execute(CommandLine command, UserData userData,
            WebService webService) throws Exception {
        // Make sure we have at least one file to upload
        if (command.getArgs().length == 0) {
            die("Must specify at least one file to import.");
        }
        
        List<File> files = getFileList(command);
        
        int count = 1;
        if (command.hasOption(REPEAT)) {
            String v = command.getOptionValue(REPEAT);
            try {
                count = Integer.valueOf(v);
                if (count <= 0) {
                    die(REPEAT + " option value must be >= 1");
                }
            }
            catch (NumberFormatException e) {
                die(REPEAT + " option requires a numeric argument");
            }
        }
        
        if (command.hasOption(FILEPROFILE) && 
            command.hasOption(FILEPROFILEID)) {
            usage("Can't specify both " + FILEPROFILE + 
                  " and " + FILEPROFILEID + " options.");
        }
        FileProfile fp = null;
        if (command.hasOption(FILEPROFILE)) {
            String fpName = command.getOptionValue(FILEPROFILE);
            fp = findByName(webService.getFileProfiles(), fpName);
            if (fp == null) {
                die("No such file profile: '" + fpName + "'");
            }
        }
        else if (command.hasOption(FILEPROFILEID)) {
            String fpId = command.getOptionValue(FILEPROFILEID);
            fp = findById(webService.getFileProfiles(), fpId);
            if (fp == null) {
                die("No such file profile id: '" + fpId + "'");
            }
        }
        else {
            // Try to infer file profile from file extension
            List<FileProfile> possibleProfiles = new ArrayList<FileProfile>();
            for (File f : files) {
                possibleProfiles.addAll(findByExtension(
                    webService.getFileProfiles(), 
                    getFileExtension(f)));
            }
            if (possibleProfiles.size() > 1) {
                dieWithProfileList(possibleProfiles);
            }
            else if (possibleProfiles.size() == 0) {
                die("No matching file profile for file extension");
            }
            fp = possibleProfiles.get(0);
            verbose("Guessing file profile: " + fp.getName());
        }
        
        // TODO target locale overrides
        
        if (count > 1) {
            verbose("Creating " + count + " jobs:");
        }
        for (int i = 0; i < count; i++) {
            // Get a job name either from command line or first file
            // uploaded, then uniquify
            String baseJobName = files.get(0).getName();
            if (command.hasOption(JOBNAME)) {
                baseJobName = command.getOptionValue(JOBNAME);
            }
            String jobName = webService.getUniqueJobName(baseJobName);
            verbose("Got unique job name: " + jobName);
            List<String> filePaths = new ArrayList<String>();
            for (File f : files) {
                filePaths.add(uploadFile(f, jobName, fp, webService));
            }
            webService.createJob(jobName, filePaths, fp);
        }
    }

    List<File> getFileList(CommandLine command) throws IOException {
        // Convert all remaining arguments to files
        List<File> files = new ArrayList<File>();
        for (String path : command.getArgs()) {
            File f = new File(path);
            if (!f.exists() || f.isDirectory()) {
                die("Not a file: " + f);
            }
            files.add(f.getCanonicalFile());
        }
        return files;
    }

    String getFileExtension(File file) {
        String basename = file.getName();
        int i = basename.lastIndexOf('.');
        if (i == -1 || i + 1 >= basename.length()) {
            return "";
        }
        return basename.substring(i + 1, basename.length());
    }

    FileProfile findByName(List<FileProfile> fileProfiles, String name) {
        for (FileProfile fp : fileProfiles) {
            if (fp.getName().equalsIgnoreCase(name)) {
                return fp;
            }
        }
        return null;
    }
    
    FileProfile findById(List<FileProfile> fileProfiles, String id) {
        for (FileProfile fp : fileProfiles) {
            if (fp.getId().equals(id)) {
                return fp;
            }
        }
        return null;
    }
    
    List<FileProfile> findByExtension(List<FileProfile> fileProfiles, 
                                      String fileExtension) {
        List<FileProfile> fps = new ArrayList<FileProfile>();
        fileExtension = fileExtension.toLowerCase();
        for (FileProfile fp : fileProfiles) {
            if (fp.getFileExtensions().contains(fileExtension)) {
                fps.add(fp);
            }
        }
        return fps;
    }
    
    private static long MAX_SEND_SIZE = 5 * 1000 * 1024; // 5M
    
    // Returns the filepath that was sent to the server
    String uploadFile(File file, String jobName, FileProfile fileProfile,
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
                verbose("Uploading chunk: " + size + " bytes");
                webService.uploadFile(filePath, jobName, fileProfile.getId(), bytes);
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
    
    void dieWithProfileList(List<FileProfile> profiles) {
        StringBuilder sb = new StringBuilder();
        sb.append("Can't guess file profile, multiple matches: [");
        boolean first = true;
        for (FileProfile fp : profiles) {
            if (first) {
                first = false;
            }
            else {
                sb.append(", ");
            }
            sb.append(fp.getName());
        }
        sb.append("]");
        die(sb.toString());
    }
    
    static final String TARGET = "target",
                        FILEPROFILE = "fileprofile",
                        FILEPROFILEID = "fileprofileid",
                        JOBNAME = "name",
                        REPEAT = "repeat";
    static final Option TARGET_OPT = OptionBuilder
        .withArgName("targetLocale")
        .hasArg()
        .withDescription("target locale code")
        .create(TARGET);
    static final Option FILEPROFILE_OPT = OptionBuilder
        .withArgName("fileProfile")
        .hasArg()
        .withDescription("file profile to use")
        .create(FILEPROFILE);
    static final Option FILEPROFILEID_OPT = OptionBuilder
        .withArgName("fileProfileId")
        .hasArg()
        .withDescription("numeric ID file profile to use")
        .create(FILEPROFILEID);
    static final Option JOBNAME_OPT = OptionBuilder
        .withArgName("jobName")
        .hasArg()
        .withDescription("job name")
        .create(JOBNAME);
    static final Option REPEAT_OPT = OptionBuilder
        .withArgName("repeat")
        .hasArg()
        .withDescription("create this job <n> times")
        .create(REPEAT);

    @Override
    public Options getOptions() {
        Options opts = super.getOptions();
        opts.addOption(TARGET_OPT);
        opts.addOption(FILEPROFILE_OPT);
        opts.addOption(FILEPROFILEID_OPT);
        opts.addOption(JOBNAME_OPT);
        opts.addOption(REPEAT_OPT);
        return opts;
    }
    
    @Override
    public String getDescription() {
        return "create a job in GlobalSight";
    }

}
