package com.globalsight.tools.gscli;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Generates Role XML of the form expected by the webservices.
 */
public class RoleXMLWriter {

    private XMLOutputFactory factory;
    
    public RoleXMLWriter(XMLOutputFactory factory) {
        this.factory = factory;
    }
    
    public void writeXML(Writer target, List<Role> roles) 
                throws IOException, XMLStreamException {
        
        XMLStreamWriter writer = factory.createXMLStreamWriter(target);
        
        writer.writeStartDocument();
        writer.writeStartElement("roles");
        for (Role role : roles) {
            // Skip roles with no activities
            if (role.getActivities().size() == 0) {
                continue;
            }
            writer.writeStartElement("role");
            writeElement(writer, "sourceLocale", role.getSourceLocale());
            writeElement(writer, "targetLocale", role.getTargetLocale());
            writer.writeStartElement("activities");
            for (ActivityType activity : role.getActivities()) {
                writer.writeStartElement("activity");
                writeElement(writer, "name", activity.getName());
                writer.writeEndElement(); // activity
            }
            writer.writeEndElement(); // activities
            writer.writeEndElement(); // role
        }
        writer.writeEndElement(); // roles
        writer.writeEndDocument();
    }
    
    private static void writeElement(XMLStreamWriter writer, 
            String elementName, String content) throws XMLStreamException {
        writer.writeStartElement(elementName);
        writer.writeCharacters(content);
        writer.writeEndElement();
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
