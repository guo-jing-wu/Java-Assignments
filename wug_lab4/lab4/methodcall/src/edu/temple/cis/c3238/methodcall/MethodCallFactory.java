package edu.temple.cis.c3238.methodcall;

import java.io.InputStream;

/**
 * Interface to define the MethodCallFactory
 * 
 * @author Paul Wolfgang
 */
public interface MethodCallFactory {
    
    /**
     * Factory method to construct a MethodCall from XML input.
     *
     * @param in The input stream contains the serialized MethodCall
     * @return a MethodCall object based on the XML
     * @throws Exception If the XML is not well formed or other parsing issues.
     */
    MethodCall readMethodCall(InputStream in) throws Exception;
    
    /**
     * Factory method to construct a MethodResponse from XML input.
     *
     * @param in The input stream contains the serialized MethodCall
     * @return a MethodCall object based on the XML
     * @throws Exception If the XML is not well formed or other parsing issues.
     */
    MethodResponse readMethodResponse(InputStream in) throws Exception;
    
}
