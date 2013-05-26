package com.spartansoftwareinc.globalsight.gscli;

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

public abstract class SimpleListParser<T extends SimpleListParser.Settable> {

    abstract String getListElement();
    
    abstract String getListEntryElement();
    
    NodeModel<Data<T>> getNodeModel() {
        return new NodeModelBuilder<Data<T>>() {{
            elements(getListElement(), getListEntryElement())
                .attach(new ListHandler());
            elements(getListElement(), getListEntryElement()).child()
                .attach(new ListElementHandler());
        }}.build();
    }
    
    static abstract class Data<T> {
        abstract List<T> getList();
        
        abstract T getCurrent();
        
        abstract void complete();
    }
    
    abstract class SimpleData extends Data {
        private List<T> list = new ArrayList<T>();
        private T current = newInstance();
        
        abstract T newInstance();
        
        @Override
        List<T> getList() {
            return list;
        }

        @Override
        T getCurrent() {
            return current;
        }

        @Override
        void complete() {
            list.add(current);
            current = newInstance();
        }
    }
    
    interface Settable {
        void set(String name, String value);
    }
    
    private XMLInputFactory factory;
     
    SimpleListParser(XMLInputFactory factory) {
        this.factory = factory;
    }
    
    abstract Data<T> getData();
    
    public List<T> parse(String xml) 
                        throws SNAXUserException, XMLStreamException {
        SNAXParser<Data<T>> parser = SNAXParser.createParser(factory, getNodeModel());
        Data<T> data = getData();
        parser.parse(new StringReader(xml), data);
        return data.getList();
    }
    
    class ListElementHandler extends DefaultElementHandler<Data<T>> {
        @Override
        public void characters(StartElement parent, Characters contents, 
                                Data<T> data) throws SNAXUserException {
            String el = parent.getName().getLocalPart();
            data.getCurrent().set(el, contents.getData());
        }
    }
    class ListHandler extends DefaultElementHandler<Data<T>> {
        @Override
        public void endElement(EndElement el, Data<T> data)
                throws SNAXUserException {
            data.complete();
        }
    }
}