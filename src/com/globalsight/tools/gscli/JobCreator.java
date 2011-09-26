package com.globalsight.tools.gscli;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.stream.XMLInputFactory;

public class JobCreator {

    public final String getUrl() {
        return url;
    }
    public final void setUrl(String url) {
        this.url = url;
    }
    public final String getUsername() {
        return username;
    }
    public final void setUsername(String username) {
        this.username = username;
    }
    public final String getPassword() {
        return password;
    }
    public final void setPassword(String password) {
        this.password = password;
    }
    public final String getFileProfileName() {
        return fileProfileName;
    }
    public final void setFileProfileName(String fileProfileName) {
        this.fileProfileName = fileProfileName;
    }
    public final File getFile() {
        return file;
    }
    public final void setFile(File file) {
        this.file = file;
    }
    
    private String url;
    private String username;
    private String password;
    
    private String fileProfileName;
    private File file;
    
    private void require(boolean condition, String message) {
        if (!condition) {
            throw new IllegalStateException(message);
        }
    }
    
    public void create() throws Exception {
        require(file != null, "No file set");
        require(fileProfileName != null, "No file profile name set");
        require(url != null, "no url set");
        require(username != null, "no username set");
        require(password != null, "no password set");
        
        WebService webService = new WebService(url, XMLInputFactory.newFactory());
        String token = webService.login(username, password);
        webService.setToken(token);
        FileProfile fileProfile = 
            findFileProfile(fileProfileName, webService.getFileProfiles());
        if (fileProfile == null) {
            throw new IllegalStateException("No such file profile: '" + 
                                            fileProfileName + "'");
        }
        // Get a unique job name
        String jobName = webService.getUniqueJobName(file.getName());
        List<String> filePaths = new ArrayList<String>();
        List<FileProfile> fileProfiles = new ArrayList<FileProfile>();
        List<Collection<String>> targetLocales = new ArrayList<Collection<String>>();
        filePaths.add(uploadFile(file, jobName, fileProfile, webService));
        fileProfiles.add(fileProfile);
        targetLocales.add(fileProfile.getTargetLocales());
        webService.createJob(jobName, filePaths, fileProfiles, targetLocales);
    }
    
    private FileProfile findFileProfile(String name, List<FileProfile> fileProfiles) {
        for (FileProfile fp : fileProfiles) {
            if (fp.getName().equalsIgnoreCase(name)) {
                return fp;
            }
        }
        return null;
    }
    
    // Returns the filepath that was sent to the server
    // TODO: refactor with CreateJobCommand
    String uploadFile(File file, String jobName, FileProfile fileProfile,
                    WebService webService) throws Exception {
        String filePath = file.getCanonicalPath();
        // XXX This is so janky - why do we have to do this?
        filePath = filePath.substring(filePath.indexOf(File.separator) + 1);
        System.out.println("filePath: " + filePath);
        InputStream is = null;
        try {
            long bytesRemaining = file.length();
            is = new BufferedInputStream(new FileInputStream(file));
            while (bytesRemaining > 0) {
                // Safe cast because it's bounded by MAX_SEND_SIZE
                int size = (int)Math.min(bytesRemaining, CreateJobCommand.MAX_SEND_SIZE);
                byte[] bytes = new byte[size];
                int count = is.read(bytes);
                if (count <= 0) {
                    break;
                }
                bytesRemaining -= count;
                webService.uploadFile(filePath, jobName, fileProfile.getId(), bytes);
            }
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

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Requires a job name");
            System.exit(1);
        }
        JobCreator creator = new JobCreator();
        creator.setUrl("http://50.19.110.144:8940/globalsight");
        creator.setUsername("testadmin");
        creator.setPassword("password");
        creator.setFileProfileName("TIP");
        creator.setFile(new File(args[0]));
        try {
            creator.create();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
