package edu.temple.cis.c3238.jdomdemo;

import edu.temple.cis.c3238.methodcall.MethodCall;
import edu.temple.cis.c3238.methodcall.MethodCallFactory;
import edu.temple.cis.c3238.methodcall.MethodResponse;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.ArrayList;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

/**
 * Encapsulation of a MethodCallFactory that includes DOM based XML
 * deserialization using JDOM2
 *
 * @author Paul Wolfgang
 */
public class JDOMMethodCallFactory implements MethodCallFactory {

    private static class MethodCallBuilder {

        /**
         * The interface or class that defines the method
         */
        private String className;
        /**
         * The method name
         */
        private String methodName;
        /**
         * The array of parameter types
         */
        private Class[] paramTypes;
        /**
         * The array of parameters
         */
        private Object[] params;

        /**
         * Private default constructor. Only the factory method can construct an
         * empty DOMMethodCallFactory object.
         */
        private MethodCallBuilder() {
        }

        /**
         * Build a MethodCall
         *
         * @return the MethodCall object build from the assembled field data
         */
        private MethodCall build() {
            return new MethodCall(className, methodName, paramTypes, params);
        }
    }

    /**
     * Factory method to construct a Method Call from XML input.
     *
     * @param in InputStream that contains the serialized MethodCall
     * @return a DOMMethodCallFactory object based on the XML
     * @throws Exception If the XML is not well formed or other parsing issues.
     */
    @Override
    public MethodCall readMethodCall(InputStream in) throws Exception {
        SAXBuilder sax = new SAXBuilder();
        Document doc = sax.build(in);
        return readMethodCallFromElement(doc.getRootElement());
    }

    /**
     * Factory method to construct a Method Call from XML element.
     *
     * @param e The XML Element that contains the serialized
     * DOMMethodCallFactory
     * @return a DOMMethodCallFactory object based on the XML
     * @throws Exception If the XML is not well formed or other parsing issues.
     */
    private static MethodCall readMethodCallFromElement(Element e) throws Exception {
        MethodCallBuilder methodCallBuilder = new MethodCallBuilder();
        for (Element child : e.getChildren()) {
            String nodeName = child.getName();
            switch (nodeName) {
                case "className":
                    methodCallBuilder.className = child.getText().trim();
                    break;
                case "methodName":
                    methodCallBuilder.methodName = child.getText().trim();
                    break;
                case "paramTypes":
                    methodCallBuilder.paramTypes = readParamTypes(child);
                    break;
                case "params":
                    methodCallBuilder.params = readParams(child);
                    break;
                default:
                    throw new Exception("Unrecognized child " + nodeName);
            }
        }
        return methodCallBuilder.build();
    }

    /**
     * Method to read the paramTypes element and return the array of paramTypes
     *
     * @param e the paramTypes element
     * @return The array of parameter types
     */
    private static Class[] readParamTypes(Element e) throws Exception {
        List<Class> paramTypeList = new ArrayList<>();
        for (Element child : e.getChildren()) {
            if ("type".equals(child.getName())) {
                Class clazz;
                String typeName = child.getTextTrim();
                clazz = getClassFromTypeName(typeName);
                paramTypeList.add(clazz);
            }
        }
        return paramTypeList.toArray(new Class[paramTypeList.size()]);
    }

    /**
     * Method to get a Class object from a type name
     *
     * @param typeName the type name
     * @return A class object that corresponds to the type name, either a Java
     * primitive type or a Java class type.
     * @throws ClassNotFoundException if there is no class for the given type
     * name
     */
    private static Class getClassFromTypeName(String typeName) throws ClassNotFoundException {
        Class clazz;
        switch (typeName) {
            case "int":
                clazz = int.class;
                break;
            case "double":
                clazz = double.class;
                break;
            case "boolean":
                clazz = boolean.class;
                break;
            case "string":
                clazz = String.class;
                break;
            case "byte":
                clazz = byte.class;
                break;
            case "short":
                clazz = short.class;
                break;
            case "long":
                clazz = long.class;
                break;
            case "float":
                clazz = float.class;
                break;
            case "char":
                clazz = char.class;
                break;
            default:
                clazz = Class.forName(typeName);
        }
        return clazz;
    }

    /**
     * Method to read the params element return the params array.
     *
     * @param e the params element
     * @return The array of param objects.
     * @throws Exception
     */
    private static Object[] readParams(Element e) throws Exception {
        List<Object> paramsList = new ArrayList<>();
        for (Element child : e.getChildren("param")) {
            Element grandChild = child.getChild("value");
            paramsList.add(readValue(grandChild));
        }
        return paramsList.toArray(new Object[paramsList.size()]);
    }

    /**
     * Method to read a value element
     *
     * @param e the value element
     * @return an Object that contains the value.
     * @throws Exception
     */
    private static Object readValue(Element e) throws Exception {
        Object result = null;
        for (Element child : e.getChildren()) {
            String elementName = child.getName();
            switch (elementName) {
                case "int":
                    result = readInt(child);
                    break;
                case "double":
                    result = readDouble(child);
                    break;
                case "boolean":
                    result = readBoolean(child);
                    break;
                case "string":
                    result = readString(child);
                    break;
                case "byte":
                    result = readByte(child);
                    break;
                case "short":
                    result = readShort(child);
                    break;
                case "long":
                    result = readLong(child);
                    break;
                case "float":
                    result = readFloat(child);
                    break;
                case "char":
                    result = readChar(child);
                    break;
                case "array":
                    result = readArray(child);
                    break;
                case "exception":
                    result = readException(child);
                    break;
                default:
                    throw new Exception("Unrecognized value element " + elementName);
            }
        }
        return result;
    }

    /**
     * Method to read an int element
     *
     * @param e The int element
     * @return an Integer object containing the value
     * @throws Exception
     */
    private static Integer readInt(Element e) throws Exception {
        return new Integer(e.getTextTrim());
    }

    /**
     * Method to read a double element
     *
     * @param e The double element
     * @return a Double object containing the value
     * @throws Exception
     */
    private static Double readDouble(Element e) throws Exception {
        return new Double(e.getTextTrim());
    }

    /**
     * Method to read a boolean element
     *
     * @param e the boolean element
     * @return a Boolean object containing the value
     * @throws Exception
     */
    private static Boolean readBoolean(Element e) throws Exception {
        String textContent = e.getTextTrim();
        if ("0".equals(textContent)) {
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }

    /**
     * Method to read a string element
     *
     * @param e the string element
     * @return A String object containing the value.
     * @throws Exception
     */
    private static String readString(Element e) throws Exception {
        return e.getTextTrim();
    }

    /**
     * Method to read a byte element
     *
     * @param e the byte element
     * @return A Byte object containing the value.
     * @throws Exception
     */
    private static Byte readByte(Element e) throws Exception {
        return new Byte(e.getTextTrim());
    }

    /**
     * Method to read a short element
     *
     * @param e the short element
     * @return A Short object containing the value.
     * @throws Exception
     */
    private static Short readShort(Element e) throws Exception {
        return new Short(e.getTextTrim());
    }

    /**
     * Method to read a long element
     *
     * @param e the long element
     * @return A Long object containing the value.
     * @throws Exception
     */
    private static Long readLong(Element e) throws Exception {
        return new Long(e.getTextTrim());
    }

    /**
     * Method to read a float element
     *
     * @param e the float element
     * @return A Float object containing the value.
     * @throws Exception
     */
    private static Float readFloat(Element e) throws Exception {
        return new Float(e.getTextTrim());
    }

    /**
     * Method to read a char element
     *
     * @param e the char element
     * @return A Char object containing the value.
     * @throws Exception
     */
    private static Character readChar(Element e) throws Exception {
        String s = e.getTextTrim();
        char c = s.charAt(0);
        return c;
    }

    /**
     * Method to read a array element
     *
     * @param e the array element
     * @return A Array object containing the value.
     * @throws Exception
     */
    private static Object readArray(Element e) throws Exception {
        List<Object> list = new ArrayList<Object>();
        Object result = null;
        String component = e.getAttributeValue("componentType");
        Class ct = getClassFromTypeName(component);
        for (Element child : e.getChildren()) {
            String elementName = child.getName();
            switch (elementName) {
                case "value":
                    result = readValue(child);
                    list.add(result);
                    break;
                case "array":
                    result = readArray(child);
                    list.add(result);
                    break;
                default:
                    throw new Exception("Unrecognized value element " + elementName);
            }
        }
        Object arr = Array.newInstance(ct, list.size());
        for (int i = 0;i<list.size();i++){
            Array.set(arr, i, list.get(i));
        }
        return arr;
    }

    static Throwable readException(Element e) throws Exception {
        String exceptionClassName = e.getAttribute("class").getValue();
        String message = null;
        Throwable cause = null;
        StackTraceElement[] stackTrace = null;
        for (Element child : e.getChildren()) {
            String elementName = child.getName();
            switch (elementName) {
                case "message":
                    message = child.getTextTrim();
                    break;
                case "cause":
                    for (Element grandChild : child.getChildren()) {
                        switch (grandChild.getName()) {
                            case "exception":
                                cause = readException(grandChild);
                                break;
                            default:
                            // Ignore any other children
                        }
                    }
                case "stackTrace":
                    stackTrace = readStackTrace(child);
                    break;
            }
        }
        Class exceptionClass = Class.forName(exceptionClassName);
        Constructor<Throwable> constructor;
        Throwable exception;
        if (message == null && cause == null) {
            constructor = exceptionClass.getConstructor();
            exception = constructor.newInstance();

        } else if (message == null && cause != null) {
            constructor = exceptionClass.getConstructor(Throwable.class
            );
            exception = constructor.newInstance(cause);
        } else if (message != null && cause == null) {
            constructor = exceptionClass.getConstructor(String.class
            );
            exception = constructor.newInstance(message);
        } else {
            constructor = exceptionClass.getConstructor(String.class, Throwable.class
            );
            exception = constructor.newInstance(message, cause);
        }

        exception.setStackTrace(stackTrace);
        return exception;
    }

    /**
     * Method to read the stackTrace element
     *
     * @param e the stackTrace element
     * @return An array of StackTraceElements.
     * @throws Exception
     */
    private static StackTraceElement[] readStackTrace(Element e) throws Exception {
        List<StackTraceElement> stackTrace = new ArrayList<>();
        for (Element child : e.getChildren()) {
            if ("stackTraceElement".equals(child.getName())) {
                stackTrace.add(readStackTraceElement(child));
            }
        }
        return stackTrace.toArray(new StackTraceElement[stackTrace.size()]);
    }

    /**
     * Method to read stackTraceElement element
     *
     * @param e the StackTraceElement element
     * @return A StackTraceElement object containing the value.
     * @throws Exception
     */
    static StackTraceElement readStackTraceElement(Element e) throws Exception {
        String declaringClass = null;
        String methodName = null;
        String fileName = null;
        int lineNumber = 0;
        for (Element child : e.getChildren()) {
            String elementName = child.getName();
            switch (elementName) {
                case "declaringClass":
                    declaringClass = child.getTextTrim();
                    break;
                case "methodName":
                    methodName = child.getTextTrim();
                    break;
                case "fileName":
                    fileName = child.getTextTrim();
                    break;
                case "lineNumber":
                    lineNumber = Integer.parseInt(child.getTextTrim());
                    break;
            }
        }
        return new StackTraceElement(declaringClass, methodName, fileName, lineNumber);
    }

    /**
     * Factory method to construct a MethodResponse from XML input.
     *
     * @param in InputStream that contains the serialized MethodCall
     * @return a DOMMethodCallFactory object based on the XML
     * @throws Exception If the XML is not well formed or other parsing issues.
     */
    @Override
    public MethodResponse readMethodResponse(InputStream in) throws Exception {
        SAXBuilder sax = new SAXBuilder();
        Document doc = sax.build(in);
        Element e = doc.getRootElement();
        Class clazz = null;
        Object value = null;
        for (Element child : e.getChildren()) {
            if ("type".equals(child.getName())) {
                String typeName = child.getTextTrim();
                clazz = getClassFromTypeName(typeName);
            } else {
                value = readValue(child);
            }
        }
        return new MethodResponse(clazz, value);
    }

}
