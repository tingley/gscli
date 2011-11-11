package com.globalsight.tools.gscli;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;

import net.sundell.snax.DefaultElementHandler;
import net.sundell.snax.NodeModel;
import net.sundell.snax.NodeModelBuilder;
import net.sundell.snax.SNAXParser;
import net.sundell.snax.SNAXUserException;

public class ProjectsParser {
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
    
    static final NodeModel<P> MODEL = new NodeModelBuilder<P>() {{
        elements("ProjectInformation", "Project").attach(new ProjectHandler());
        elements("ProjectInformation", "Project").child()
                                             .attach(new ProjectDataHandler());
    }}.build();

    private XMLInputFactory factory;
    
    ProjectsParser(XMLInputFactory factory) {
        this.factory = factory;
    }
    
    // TODO: refactor some of this stuff
    public List<Project> parse(String projectsXml) 
                        throws SNAXUserException, XMLStreamException {
        SNAXParser<P> parser = SNAXParser.createParser(factory, MODEL);
        P p = new P();
        parser.parse(new StringReader(projectsXml), p);
        return p.projects;
    }
    
    static class P {
        List<Project> projects = new ArrayList<Project>();
        Project current = new Project();
    }
    
    static class ProjectDataHandler extends DefaultElementHandler<P> {
        @Override
        public void characters(StartElement parent, Characters contents, 
                                P data) throws SNAXUserException {
            String el = parent.getName().getLocalPart();
            if (el.equals("id")) {
                data.current.setId(contents.getData());
            }
            else if (el.equals("name")) {
                data.current.setName(contents.getData());
            }
            else if (el.equals("description")) {
                data.current.setDescription(contents.getData());
            }
            else if (el.equals("projectmanager")) {
                data.current.setPmUser(contents.getData());
            }
        }
    }
    static class ProjectHandler extends DefaultElementHandler<P> {
        @Override
        public void endElement(EndElement el, P data)
                throws SNAXUserException {
            data.projects.add(data.current);
            data.current = new Project();
        }
    }
}
