package com.spartansoftwareinc.globalsight.gscli;

import java.util.List;

import javax.xml.stream.XMLInputFactory;
import org.junit.*;
import static org.junit.Assert.*;

public class TestProjectsParser {
    XMLInputFactory factory = XMLInputFactory.newFactory();
    
    @Test
    public void test() throws Exception {
        List<Project> projects = new ProjectsParser(factory).parse(SAMPLE_XML);
        assertEquals(1, projects.size());
        assertEquals("1000", projects.get(0).getId());
        assertEquals("Template", projects.get(0).getName());
        assertEquals("N/A", projects.get(0).getDescription());
        assertEquals("gstestpm", projects.get(0).getPmUser());
    }
    
    static final String SAMPLE_XML =  
        "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
        "<ProjectInformation>" +
        "<Project>" +
        "    <id>1000</id>" +
        "    <name>Template</name>" +
        "    <description>N/A</description>" +
        "    <projectmanager>gstestpm</projectmanager>" +
        "</Project>" +
        "</ProjectInformation>";
}
