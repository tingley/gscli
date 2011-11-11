package com.globalsight.tools.gscli;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.cli.CommandLine;

public class ProjectsCommand extends WebServiceCommand {

    @Override
    protected void execute(CommandLine command, UserData userData,
            WebService webService) throws Exception {
        List<Project> projects = webService.getAllProjects();
        Collections.sort(projects, new ProjectNameComparator());
        for (Project p : projects) {
            System.out.println(p);
        }
    }

    @Override
    public String getDescription() {
        return "Get a list of all available projects in the company";
    }

    static class ProjectNameComparator implements Comparator<Project> {
        @Override
        public int compare(Project p1, Project p2) {
            return p1.getName().compareToIgnoreCase(p2.getName());
        }
    }
}
