package com.globalsight.tools;

import java.io.StringReader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;

import net.sundell.snax.DefaultElementHandler;
import net.sundell.snax.NodeModel;
import net.sundell.snax.NodeModelBuilder;
import net.sundell.snax.SNAXParser;
import net.sundell.snax.SNAXUserException;

public class TaskParser {
    private XMLInputFactory factory;

    TaskParser(XMLInputFactory factory) {
        this.factory = factory;
    }

    /*** 
     * Can this actually produce multiple tasks?  I've never seen it..
     */
    static final NodeModel<WFT> MODEL = new NodeModelBuilder<WFT>() {{
        elements("tasksInWorkflow", "workflowId")
            .attach(new WFIDHandler());
        elements("tasksInWorkflow", "task").child()
            .attach(new TaskAttrHandler());
    }}.build();

    // TODO: need a way to get the workflow
    public Task parse(String workflowTaskXml) 
        throws SNAXUserException, XMLStreamException {
            SNAXParser<WFT> parser = SNAXParser.createParser(factory, MODEL);
            WFT wft = new WFT();
            parser.parse(new StringReader(workflowTaskXml), wft);
            return wft.toTask();
        }

    static class WFT {
        long id;
        long wfId;
        String name;
        String state;

        Task toTask() {
            return new Task(wfId, id, name, state);
        }
    }

    static class WFIDHandler extends DefaultElementHandler<WFT> {
        @Override
        public void characters(StartElement parent, Characters contents,
                WFT data) throws SNAXUserException {
            data.wfId = parseLong(contents.getData()); 
        }
    }

    static class TaskAttrHandler extends DefaultElementHandler<WFT> {
        @Override
        public void characters(StartElement parent, Characters contents, 
                WFT data) throws SNAXUserException {
            String el = parent.getName().getLocalPart();
            if (el.equals("id")) {
                data.id = parseLong(contents.getData());
            }
            else if (el.equals("name")) {
                data.name = contents.getData();
            }
            else if (el.equals("state")) {
                data.state = contents.getData();
            }
        }
    }

    private static long parseLong(String s) throws SNAXUserException {
        try {
            return Long.valueOf(s); 
        }
        catch (NumberFormatException e) {
            throw new SNAXUserException(e);
        }
    }
}
