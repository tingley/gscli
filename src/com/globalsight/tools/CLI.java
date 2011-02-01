package com.globalsight.tools;

import java.net.URL;
import java.util.Arrays;
import java.util.Formatter;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.MissingOptionException;

public class CLI {

    private SortedMap<String, Class<? extends Command>> commands =
        new TreeMap<String, Class<? extends Command>>();
    
    public void run(String[] args) {
        registerDefaultCommands(commands);
        if (args.length == 0) {
            help();
        }
        String cmd = args[0].toLowerCase();
        if (cmd.equals("help") && args.length == 2) {
            help(args[1]);
        }
        Command command = getCommand(commands.get(cmd));
        if (command == null) {
            help();
        }
        if (args.length > 1) {
            args = Arrays.asList(args).subList(1, args.length)
                                .toArray(new String[args.length - 1]);
        }
        else {
            args = new String[0];
        }
        CommandLineParser parser = new GnuParser();
        try {
            CommandLine cl = parser.parse(command.getOptions(), args);
            command.execute(cl);
        }
        catch (MissingOptionException e) {
            System.err.println(e.getMessage());
            command.usage();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    void registerDefaultCommands(Map<String, Class<? extends Command>> commands) {
        commands.put("fileprofiles", FileProfilesCommand.class);
    }
    
    private static Command getCommand(Class<? extends Command> clazz) {
        if (clazz == null) {
            return null;
        }
        try {
            return clazz.newInstance();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    void help() {
        Formatter f = new Formatter(System.err);
        f.format("Available commands:\n");
        for (Map.Entry<String, Class<? extends Command>> e : 
                                            commands.entrySet()) {
            Command c = getCommand(e.getValue());
            f.format("%-20s%s\n", e.getKey(), c.getDescription());
        }
        f.flush();
        f.close();
    }
    
    private void help(String cmd) {
        Command command = getCommand(commands.get(cmd));
        if (command == null) {
            help();
        }
        command.usage();
    }
    
    public static void main(String[] args) {
        new CLI().run(args);
    }
}
