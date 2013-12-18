/**
 * JobException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.globalsight.www.webservices;

public class JobException extends org.apache.axis.AxisFault {
    public java.lang.Object fault;
    public java.lang.Object getFault() {
        return this.fault;
    }

    public JobException() {
    }

    public JobException(java.lang.Exception target) {
        super(target);
    }

    public JobException(java.lang.String message, java.lang.Throwable t) {
        super(message, t);
    }

      public JobException(java.lang.Object fault) {
        this.fault = fault;
    }

    /**
     * Writes the exception data to the faultDetails
     */
    public void writeDetails(javax.xml.namespace.QName qname, org.apache.axis.encoding.SerializationContext context) throws java.io.IOException {
        context.serialize(qname, null, fault);
    }
}
