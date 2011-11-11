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

public class ActivityTypesParser {

    /*
      <ActivityInformation>
        <Activity>
            <id>1000</id>
            <name>Translation1_1000</name>
            <description>N/A</description>
        </Activity>
     */

    // TODO: this is almost identical to ProjectsParser and should be 
    // heavily refactored
    static final NodeModel<P> MODEL = new NodeModelBuilder<P>() {{
        elements("ActivityInformation", "Activity")
                        .attach(new ActivityTypeHandler());
        elements("ActivityInformation", "Activity").child()
                        .attach(new ActivityTypeDataHandler());
    }}.build();

    private XMLInputFactory factory;
    
    ActivityTypesParser(XMLInputFactory factory) {
        this.factory = factory;
    }
    
    public List<ActivityType> parse(String projectsXml) 
                        throws SNAXUserException, XMLStreamException {
        SNAXParser<P> parser = SNAXParser.createParser(factory, MODEL);
        P p = new P();
        parser.parse(new StringReader(projectsXml), p);
        return p.projects;
    }
    
    static class P {
        List<ActivityType> projects = new ArrayList<ActivityType>();
        ActivityType current = new ActivityType();
    }
    
    static class ActivityTypeDataHandler extends DefaultElementHandler<P> {
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
        }
    }
    static class ActivityTypeHandler extends DefaultElementHandler<P> {
        @Override
        public void endElement(EndElement el, P data)
                throws SNAXUserException {
            data.projects.add(data.current);
            data.current = new ActivityType();
        }
    }

}
