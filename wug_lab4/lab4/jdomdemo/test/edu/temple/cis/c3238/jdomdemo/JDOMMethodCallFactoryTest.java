/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.temple.cis.c3238.jdomdemo;

import edu.temple.cis.c3238.methodcall.MethodCall;
import edu.temple.cis.c3238.methodcall.MethodCallFactory;
import edu.temple.cis.c3238.methodcall.MethodResponse;
import edu.temple.cis.wolfgang.myxmltest.TestElementFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.jdom2.input.DOMBuilder;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Paul Wolfgang
 */
public class JDOMMethodCallFactoryTest {

    public JDOMMethodCallFactoryTest() {
    }

    @Test
    public void testReadMethodCallPrimitiveParameters() throws Exception {
        String methodCallText = "<methodCall>"
                + "<className>Foo</className>"
                + "<methodName>bar</methodName>"
                + "<paramTypes>"
                + "<type>int</type>"
                + "<type>double</type>"
                + "</paramTypes>"
                + "<params>"
                + "<param><value><int>10</int></value></param>"
                + "<param><value><double>123.0</double></value></param>"
                + "</params>"
                + "</methodCall>";
        InputStream testIn = new ByteArrayInputStream(methodCallText.getBytes());
        Class[] paramTypes = new Class[]{int.class, double.class};
        Object[] params = {10, 123.0};
        MethodCall expected = new MethodCall("Foo", "bar", paramTypes, params);
        MethodCallFactory methodCallFactory = new JDOMMethodCallFactory();
        MethodCall actual = methodCallFactory.readMethodCall(testIn);
        assertEquals(expected, actual);
    }

    @Test
    public void testReadMethodCallArrayParameters() throws Exception {
        String methodCallText = "<methodCall>\n"
                + "<className>edu.temple.cis.c3238.matrix.MatrixOps</className>\n"
                + "<methodName>mult</methodName>\n"
                + "<paramTypes>\n"
                + "<type>[[D</type>\n"
                + "<type>[[D</type>\n"
                + "</paramTypes>\n"
                + "<params>\n"
                + "<param><value><array componentType=\"[D\">\n"
                + "<value><array componentType=\"double\">\n"
                + "<value><double>1.0</double></value>\n"
                + "<value><double>2.0</double></value>\n"
                + "<value><double>3.0</double></value>\n"
                + "</array>\n"
                + "</value>\n"
                + "<value><array componentType=\"double\">\n"
                + "<value><double>4.0</double></value>\n"
                + "<value><double>5.0</double></value>\n"
                + "<value><double>6.0</double></value>\n"
                + "</array>\n"
                + "</value>\n"
                + "</array>\n"
                + "</value></param>\n"
                + "<param><value><array componentType=\"[D\">\n"
                + "<value><array componentType=\"double\">\n"
                + "<value><double>7.0</double></value>\n"
                + "<value><double>8.0</double></value>\n"
                + "</array>\n"
                + "</value>\n"
                + "<value><array componentType=\"double\">\n"
                + "<value><double>9.0</double></value>\n"
                + "<value><double>10.0</double></value>\n"
                + "</array>\n"
                + "</value>\n"
                + "<value><array componentType=\"double\">\n"
                + "<value><double>11.0</double></value>\n"
                + "<value><double>12.0</double></value>\n"
                + "</array>\n"
                + "</value>\n"
                + "</array>\n"
                + "</value></param>\n"
                + "</params>\n"
                + "</methodCall>\n";
        InputStream testIn = new ByteArrayInputStream(methodCallText.getBytes());
        double[][] a = {{1, 2, 3},
        {4, 5, 6}};
        double[][] b = {{7, 8},
        {9, 10},
        {11, 12}};
        MethodCall expected = new MethodCall(
                "edu.temple.cis.c3238.matrix.MatrixOps",
                "mult",
                new Class[]{a.getClass(), b.getClass()},
                new Object[]{a, b}
        );
        MethodCallFactory methodCallFactory = new JDOMMethodCallFactory();
        MethodCall actual = methodCallFactory.readMethodCall(testIn);
        assertEquals(expected, actual);
    }

    @Test
    public void testAllPrimitiveTypes() throws Exception {
        String methodCallText = "<methodCall>\n"
                + "<className>edu.temple.cis.Foo</className>\n"
                + "<methodName>bar</methodName>\n"
                + "<paramTypes>\n"
                + "<type>boolean</type>\n"
                + "<type>byte</type>\n"
                + "<type>char</type>\n"
                + "<type>double</type>\n"
                + "<type>float</type>\n"
                + "<type>int</type>\n"
                + "<type>long</type>\n"
                + "<type>short</type>\n"
                + "</paramTypes>\n"
                + "<params>\n"
                + "<param><value><boolean>true</boolean></value></param>\n"
                + "<param><value><byte>1</byte></value></param>\n"
                + "<param><value><char>x</char></value></param>\n"
                + "<param><value><double>123.45</double></value></param>\n"
                + "<param><value><float>543.12</float></value></param>\n"
                + "<param><value><int>99</int></value></param>\n"
                + "<param><value><long>1234567890</long></value></param>\n"
                + "<param><value><short>987</short></value></param>\n"
                + "</params>\n"
                + "</methodCall>\n";
        Class[] paramTypes = new Class[]{
            boolean.class,
            byte.class,
            char.class,
            double.class,
            float.class,
            int.class,
            long.class,
            short.class
        };
        Object[] params = new Object[]{
            Boolean.TRUE,
            (byte) 1,
            'x',
            123.45,
            543.12F,
            99,
            1234567890L,
            (short) 987
        };
        MethodCall expected = new MethodCall("edu.temple.cis.Foo", "bar",
                paramTypes, params);
        InputStream testIn = new ByteArrayInputStream(methodCallText.getBytes());
        MethodCallFactory methodCallFactory = new JDOMMethodCallFactory();
        MethodCall actual = methodCallFactory.readMethodCall(testIn);
        assertEquals(expected, actual);
    }

    @Test
    public void testMethodResponsePrimitive() throws Exception {
        String methodResponseText = "<methodResponse>\n"
                + "<type>int</type>\n"
                + "<value><int>12</int></value>\n"
                + "</methodResponse>\n";
        MethodResponse expected = new MethodResponse(int.class, new Integer(12));
        InputStream testIn = new ByteArrayInputStream(methodResponseText.getBytes());
        MethodCallFactory methodCallFactory = new JDOMMethodCallFactory();
        MethodResponse actual = methodCallFactory.readMethodResponse(testIn);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testReadException() throws Exception {
        String exceptionText = "<exception class=\"java.lang.ArithmeticException\">"
                + "<message>Divide by zero</message>"
                + "<stackTrace>"
                + "<stackTraceElement>"
                + "<declaringClass>edu.temple.cis.wolfgang.computeImpl.ComputeImpl</declaringClass>"
                + "<methodName>div</methodName>"
                + "<fileName>ComputeImpl.java</fileName>"
                + "<lineNumber>21</lineNumber>"
                + "</stackTraceElement>"
                + "</stackTrace>"
                + "</exception>\n";
        Exception expectedExcepton = new ArithmeticException("Divide by zero");
        StackTraceElement ste = new StackTraceElement("edu.temple.cis.wolfgang.computeImpl.ComputeImpl",
                "div", "ComputeImpl.java", 21);
        expectedExcepton.setStackTrace(new StackTraceElement[]{ste});
        InputStream testIn = new ByteArrayInputStream(exceptionText.getBytes());
        TestElementFactory testElementFactory = new TestElementFactory();
        org.w3c.dom.Element exceptionElement = testElementFactory.newElement(exceptionText);
        DOMBuilder domBuilder = new DOMBuilder();
        org.jdom2.Element jdomExceptionElement = domBuilder.build(exceptionElement);
        Throwable actual = JDOMMethodCallFactory.readException(jdomExceptionElement);
        assertEquals(expectedExcepton.getMessage(), actual.getMessage());
        assertArrayEquals(expectedExcepton.getStackTrace(), actual.getStackTrace());
    }

    @Test
    public void testMethodResponseException() throws Exception {
        String methodResponseText = "<methodResponse>\n"
                + "<type>java.lang.ArithmeticException</type>\n"
                + "<value><exception class=\"java.lang.ArithmeticException\">"
                + "<message>Divide by zero</message>"
                + "<stackTrace>"
                + "<stackTraceElement>"
                + "<declaringClass>edu.temple.cis.wolfgang.computeImpl.ComputeImpl</declaringClass>"
                + "<methodName>div</methodName>"
                + "<fileName>ComputeImpl.java</fileName>"
                + "<lineNumber>21</lineNumber>"
                + "</stackTraceElement>"
                + "</stackTrace>"
                + "</exception></value>\n"
                + "</methodResponse>";
        Exception expectedExcepton = new ArithmeticException("Divide by zero");
        StackTraceElement ste = new StackTraceElement("edu.temple.cis.wolfgang.computeImpl.ComputeImpl",
                "div", "ComputeImpl.java", 21);
        expectedExcepton.setStackTrace(new StackTraceElement[]{ste});
        MethodResponse expected = new MethodResponse(ArithmeticException.class, expectedExcepton);
        InputStream testIn = new ByteArrayInputStream(methodResponseText.getBytes());
        MethodCallFactory methodCallFactory = new JDOMMethodCallFactory();
        MethodResponse actual = methodCallFactory.readMethodResponse(testIn);
        assertEquals(expected, actual);
    }
}
