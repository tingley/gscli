package com.spartansoftwareinc.globalsight.gscli;

import java.util.List;

import javax.xml.stream.XMLInputFactory;
import org.junit.*;
import static org.junit.Assert.*;

public class TestActivityTypesParser {
    XMLInputFactory factory = XMLInputFactory.newFactory();
    
    @Test
    public void test() throws Exception {
        List<ActivityType> types = new ActivityTypesParser(factory).parse(SAMPLE_XML);
        assertEquals(1, types.size());
        assertEquals("1000", types.get(0).getId());
        assertEquals("Translation1_1000", types.get(0).getName());
        assertEquals("N/A", types.get(0).getDescription());
    }
    
    static final String SAMPLE_XML =  
        "<ActivityInformation>" +
        "  <Activity>" +
        "      <id>1000</id>" +
        "      <name>Translation1_1000</name>" +
        "      <description>N/A</description>" +
        "  </Activity>" +
        "</ActivityInformation>";
}
