package com.spartansoftwareinc.globalsight.gscli;

import javax.xml.stream.XMLInputFactory;

import org.junit.*;
import static org.junit.Assert.*;

public class TestTaskParser {
    XMLInputFactory factory = XMLInputFactory.newFactory();

    @Test
    public void testWorkflowTaskXml() throws Exception {
        final String XML = 
            "<tasksInWorkflow>" +
            "<workflowId>125</workflowId>" +
            "<task><id>718</id><name>Translation1_1000</name><state>ACCEPTED</state>" +
            "</task></tasksInWorkflow>";

        Task task = new TaskParser(factory).parse(XML);
        assertEquals(125, task.getWorkflowId());
        assertEquals(718, task.getId());
        assertEquals("Translation1_1000", task.getName());
        assertEquals("ACCEPTED", task.getState());
    }
}
