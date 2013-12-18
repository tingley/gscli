package com.spartansoftwareinc.globalsight.gscli;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLOutputFactory;

import org.junit.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import static org.junit.Assert.*;

public class TestRoleXMLWriter {

    @Test
    public void test() throws Exception {
        List<Role> roles = new ArrayList<Role>();
        roles.add(new Role() {{
            setSourceLocale("en_US");
            setTargetLocale("de_DE");
            addActivity(new ActivityType("1", "Translation1"));
            addActivity(new ActivityType("2", "Translation2"));
        }});
        
        RoleXMLWriter w = new RoleXMLWriter(XMLOutputFactory.newFactory());
        StringWriter sw = new StringWriter();
        w.writeXML(sw, roles);
        System.out.println(sw.toString());
        
        DocumentBuilder builder = 
                DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(
                new InputSource(new StringReader(sw.toString())));
        assertNotNull(doc);
        Node root = doc.getFirstChild();
        assertNotNull(root);
        assertEquals("roles", root.getNodeName());
        Node role = root.getFirstChild();
        assertNotNull(role);
        assertEquals("role", role.getNodeName());
        // TODO... finish, or better yet replace with a real parser
    }
    
    /**
     * Roles XML (from Ambassador.parseRoles()): 
     * <?xml version=\"1.0\"?>
     *   <roles>
     *      <role>
     *          <sourceLocale>en_US</sourceLocale>
     *          <targetLocale>de_DE</targetLocale>
     *          <activities>
     *              <activity>
     *                  <name>Dtp1</name>
     *              </activity>
     *              <activity>
     *                  <name>Dtp2</name>
     *              </activity>
     *          </activities>
     *      </role>
     *   </roles>
     */

}
