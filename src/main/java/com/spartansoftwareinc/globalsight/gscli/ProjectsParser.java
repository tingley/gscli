package com.spartansoftwareinc.globalsight.gscli;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;

public class ProjectsParser extends SimpleListParser<Project> {

    ProjectsParser(XMLInputFactory factory) {
        super(factory);
    }
    /*
       <?xml version="1.0" encoding="UTF-8" ?>
        <ProjectInformation>
        <Project>
            <id>1000</id>
            <name>Template</name>
            <description>N/A</description>
            <projectmanager>gstestpm</projectmanager>
        </Project>
        </ProjectInformation>
     */

    @Override
    String getListElement() {
        return "ProjectInformation";
    }

    @Override
    String getListEntryElement() {
        return "Project";
    }

    @Override
    SimpleListParser.Data<Project> getData() {
        return new SimpleListParser.SimpleData() {
            @Override
            Project newInstance() {
                return new Project();
            }
        };
    }
}
