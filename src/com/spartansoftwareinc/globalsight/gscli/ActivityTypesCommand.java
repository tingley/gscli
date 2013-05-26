package com.spartansoftwareinc.globalsight.gscli;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.cli.CommandLine;

public class ActivityTypesCommand extends WebServiceCommand {

    @Override
    protected void execute(CommandLine command, UserData userData,
            WebService webService) throws Exception {
        List<ActivityType> types = webService.getAllActivityTypes();
        Collections.sort(types, new ActivityTypeNameComparator());
        for (ActivityType type : types) {
            System.out.println(type);
        }
    }

    @Override
    public String getDescription() {
        return "Get a list of all available activity types";
    }

    static class ActivityTypeNameComparator 
                        implements Comparator<ActivityType> {
        @Override
        public int compare(ActivityType p1, ActivityType p2) {
            return p1.getName().compareToIgnoreCase(p2.getName());
        }
    }
}
