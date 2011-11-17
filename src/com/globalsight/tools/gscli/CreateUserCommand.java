package com.globalsight.tools.gscli;

import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

@SuppressWarnings("static-access")
public class CreateUserCommand extends WebServiceCommand {

    /*
     *     public void createUser(String username, String password, 
            String firstName, String lastName, String emailAddress,
            List<String> permissionGroups, List<Role> roles) 
            throws RemoteException {
            
            gscli create-user -name "Test Testerson" -userid ttester \
                    -userpassword password -email "ttester@sample.com"
                    -permission LocalizationParticipan -permission Administrator
                    -role "en_US,de_DE,Translate1,Translate2,Translate3"  
            
            Options we need:
            - Username # 
            - password # 
            - firstName # combine into "fullname" or "displayName"
            - lastName  # "" 
            - emailAddress
            - permissionGroups => -permission / -p
            - roles # how to do this?
     */
    
    static final String USERID = "userid",
            USERPASS = "userpassword",
            NAME = "name",
            EMAIL = "email",
            PERMISSION = "permission",
            ROLE = "role";
            
    static final Option USERID_OPT = OptionBuilder
        .withArgName("user id")
        .hasArg()
        .isRequired()
        .withDescription("new user login id")
        .create(USERID);
    static final Option USERPASS_OPT = OptionBuilder
        .withArgName("password")
        .hasArg()
        .isRequired()
        .withDescription("new user password")
        .create(USERPASS);
    static final Option NAME_OPT = OptionBuilder
        .withArgName("name")
        .hasArg()
        .isRequired()
        .withDescription("full name (first and last) of user")
        .create(NAME);
    static final Option EMAIL_OPT = OptionBuilder
        .withArgName("address")
        .hasArg()
        .isRequired()
        .withDescription("new user email address")
        .create(EMAIL);
    static final Option PERMISSION_OPT = OptionBuilder
        .withArgName("permission-group")
        .hasArg()
        .withDescription("new user belongs to this permission group")
        .create(PERMISSION);
    static final Option ROLE_OPT = OptionBuilder
        .withArgName("srcLocale,tgtLocale,activity[,activity,...]")
        .hasArg()
        .withDescription("new user role data")
        .create(ROLE);
    
    @Override
    public Options getOptions() {
        Options opts = super.getOptions();
        opts.addOption(USERID_OPT);
        opts.addOption(USERPASS_OPT);
        opts.addOption(NAME_OPT);
        opts.addOption(EMAIL_OPT);
        opts.addOption(PERMISSION_OPT);
        opts.addOption(ROLE_OPT);
        return opts;
    }
    
    @Override
    protected void execute(CommandLine command, UserData userData,
            WebService webService) throws Exception {
        if (command.getArgs().length != 0) {
            usage();
        }
        
        String firstName = null, 
               lastName = null, 
               email = null, 
               userId = null, 
               password = null;
        List<String> permissionGroups = new ArrayList<String>();
        List<Role> roles = new ArrayList<Role>();

        for (Option opt : command.getOptions()) {
            verbose(opt.getOpt() + " --> " + opt.getValue());
            if (opt.getOpt().equals(USERID)) {
                userId = opt.getValue();
            }
            else if (opt.getOpt().equals(USERPASS)) {
                password = opt.getValue();
            }
            else if (opt.getOpt().equals(EMAIL)) {
                email = opt.getValue();
            }
            else if (opt.getOpt().equals(NAME)) {
                FullName name = new FullName(opt.getValue());
                firstName = name.getFirst();
                lastName = name.getLast();
            }
            else if (opt.getOpt().equals(PERMISSION)) {
                permissionGroups.add(opt.getValue());
            }
            else if (opt.getOpt().equals(ROLE)) {
                // TODO: parse the role
            }
        }

        verbose("User: " + userId);
        verbose("Password: " + password);
        verbose("Email: " + email);
        verbose("First Name: " + firstName);
        verbose("Last Name: " + lastName);
        verbose("Permission Groups: " + permissionGroups);
        verbose("Roles: " + roles);
        
        // Sanity check some arguments
        // - fullname must not be empty
        
        // TODO
        // - parse roles, generate Role
        // - validate permission groups (possible?)

        webService.createUser(userId, password, firstName, lastName, email, 
                              permissionGroups, roles);
    }
    
    class FullName {
        private String name, first, last;
        FullName(String name) {
            this.name = name;
        }
        private void parse() {
            if (first != null) {
                return;
            }
            String[] words = name.trim().split("\\s+");
            if (words.length == 1) {
                first = last = words[0];
                return;
            }
            first = words[0];
            last = Util.join(' ', Arrays.asList(words) 
                                        .subList(1, words.length));
        }
        String getFirst() {
            if (first == null) {
                parse();
            }
            return first;
        }
        String getLast() {
            if (first == null) {
                parse();
            }
            return last;
        }
    }
    
    @Override
    public String getDescription() {
        return "Create a new GlobalSight user.";
    }

}
