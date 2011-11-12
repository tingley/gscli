package com.globalsight.tools.gscli;

import javax.xml.stream.XMLInputFactory;

public class ActivityTypesParser extends SimpleListParser<ActivityType> {

    /*
      <ActivityInformation>
        <Activity>
            <id>1000</id>
            <name>Translation1_1000</name>
            <description>N/A</description>
        </Activity>
     */
    @Override
    String getListElement() {
        return "ActivityInformation";
    }

    @Override
    String getListEntryElement() {
        return "Activity";
    }

    @Override
    SimpleListParser.Data<ActivityType> getData() {
        return new SimpleListParser.SimpleData() {
            @Override
            ActivityType newInstance() {
                return new ActivityType();
            }
        };
    }

    ActivityTypesParser(XMLInputFactory factory) {
        super(factory);
    }
}
