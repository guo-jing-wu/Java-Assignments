package edu.temple.cis.c3238.methodcall;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.StringJoiner;

/**
 * Class to encapsulate a method call
 *
 * @author Paul
 */
public class MethodCall {

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
     * Construct a Method Call
     *
     * @param className The class or interface name
     * @param methodName The method name
     * @param paramTypes The array of parameter types
     * @param params The array of parameter values
     */
    public MethodCall(String className,
            String methodName,
            Class[] paramTypes,
            Object[] params) {
        this.className = className;
        this.methodName = methodName;
        this.paramTypes = paramTypes;
        this.params = params;
    }

    /**
     * Construct a Method Call from a method Object and parameter list.
     *
     * @param method The method object passed to the invocation handler
     * @param params The array of parameters passed to the invocation handler
     */
    public MethodCall(Method method, Object[] params) {
        this(method.getDeclaringClass().getName(),
                method.getName(),
                method.getParameterTypes(),
                params);
    }

    /**
     * Generate a string representation of the method call. The output is of the
     * form &lt;className&gt;.&lt;methodName&gt;(&lt;params&gt;)
     *
     * @return String representation of the method call
     */
    @Override
    public String toString() {
        StringBuilder stb = new StringBuilder();
        stb.append(getClassName());
        stb.append('.');
        stb.append(getMethodName());
        StringJoiner sj = new StringJoiner(", ", "(", ")");
        for (int i = 0; i < paramTypes.length; i++) {
            sj.add(paramTypes[i].toString() + " " + params[i].toString());
        }
        return stb.append(sj).toString();
    }

    /**
     * Return an XML representation of the MethodCall
     *
     * @return an XML representation of the MethodCall
     */
    public String toXML() {
        StringBuilder stb = new StringBuilder();
        stb.append("<methodCall>\n");
        stb.append("<className>");
        stb.append(getClassName());
        stb.append("</className>\n");
        stb.append("<methodName>");
        stb.append(getMethodName());
        stb.append("</methodName>\n");
        stb.append("<paramTypes>\n");
        for (Class clazz : getParamTypes()) {
            stb.append("<type>");
            stb.append(clazz.getName());
            stb.append("</type>\n");
        }
        stb.append("</paramTypes>\n");
        stb.append("<params>\n");
        for (int i = 0; i < getParams().length; i++) {
            Class clazz = getParamTypes()[i];
            Object param = getParams()[i];
            stb.append("<param>");
            stb.append(valueToXML(clazz, param));
            stb.append("</param>\n");
        }
        stb.append("</params>\n");
        stb.append("</methodCall>\n");
        return stb.toString();
    }

    /**
     * Construct an XML representation of a value
     * @param clazz The type of the value (may be a primitive type)
     * @param o The value (possibly encapsulated in a wrapper type)
     * @return 
     */
    public static String valueToXML(Class clazz, Object o) {
        StringBuilder stb = new StringBuilder();
        stb.append("<value>");
        if (clazz.isPrimitive()) {
            stb.append("<");
            stb.append(clazz.getName());
            stb.append(">");
            stb.append(o.toString());
            stb.append("</");
            stb.append(clazz.getName());
            stb.append(">");
        } else if (clazz == String.class) {
            stb.append("<string>");
            stb.append(o);
            stb.append("</string>");
        } else if (Throwable.class.isAssignableFrom(clazz)) {
            stb.append(throwableToXML((Throwable) o));
        } else if (clazz.isArray()) {
            Class componentType = clazz.getComponentType();
            stb.append("<array componentType=\"");
            stb.append(componentType.getName());
            stb.append("\">\n");
            int length = Array.getLength(o);
            for (int i = 0; i < length; i++) {
                stb.append(valueToXML(componentType, Array.get(o, i)));
                stb.append("\n");
            }
            stb.append("</array>\n");
       } else {
            stb.append("<object class=\"");
            stb.append(clazz.getName());
            stb.append("\">");
            stb.append(o.toString());
            stb.append("</object>");
        }
        stb.append("</value>");
        return stb.toString();
    }
    
    /**
     * Construct an XML representation of a Throwable
     * @param t The Throwable
     * @return XML representation of the contents of the exception element
     */
    public static StringBuilder throwableToXML(Throwable t) {
        Class clazz = t.getClass();
        String message = t.getMessage();
        Throwable cause = t.getCause();
        StackTraceElement[] stackTrace = t.getStackTrace();
        StringBuilder stb = new StringBuilder();
        stb.append("<exception class=\"").append(clazz.getName()).append("\">\n");        
        if (message != null) {
            stb.append("<message>").append(message).append("</message>\n");
        }
        if (cause != null) {
            stb.append("<cause>\n");
            stb.append(throwableToXML(cause));
            stb.append("</cause>\n");
        }
        stb.append("<stackTrace>\n");
        for (StackTraceElement ste : stackTrace) {
            stb.append("<stackTraceElement>\n");
            stb.append("<declaringClass>").append(ste.getClassName()).append("</declaringClass>\n");
            stb.append("<methodName>").append(ste.getMethodName()).append("</methodName>\n");
            stb.append("<fileName>").append(ste.getFileName()).append("</fileName>\n");
            stb.append("<lineNumber>").append(ste.getLineNumber()).append("</lineNumber>\n");
            stb.append("</stackTraceElement>\n");
        }
        stb.append("</stackTrace>\n");
        stb.append("</exception>\n");
        return stb;
    }

    /**
     * Determine if the other MethodCall object is equal to this object
     *
     * @param o The other MethodCall object
     * @return true if the other MethodCall object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (this.getClass() == o.getClass()) {
            MethodCall other = (MethodCall) o;
            return getClassName().equals(other.getClassName())
                    && getMethodName().equals(other.getMethodName())
                    && Arrays.equals(this.getParamTypes(), other.getParamTypes())
                    && arraysAreEqual(this.getParams(), other.getParams());
        } else {
            return false;
        }
    }
    
    /**
     * @return the className
     */
    public String getClassName() {
        return className;
    }

    /**
     * @return the methodName
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * @return the paramTypes
     */
    public Class[] getParamTypes() {
        return paramTypes;
    }

    /**
     * @return the params
     */
    public Object[] getParams() {
        return params;
    }
    
    /**
     * Method to determine if two arrays are equal.
     * 
     * This performs a deep equality, as opposed to the Arrays.equals
     * method.
     * 
     * @param a1 One array
     * @param a2 The other array
     * @return true if both arrays contain equal content.
     */
    public static boolean arraysAreEqual(Object[] a1, Object[] a2) {
        if (a1 == a2) return true;
        if (a1 == null || a2 == null) return false;
        if (a1.length != a2.length) return false;
        for (int i = 0; i < a1.length; i++) {
            Object o1 = a1[i];
            Object o2 = a2[i];
            if (o1 != null && o2 != null) {
                if (o1.getClass() == o2.getClass()) {
                    Class clazz = o1.getClass();
                    if (clazz.isArray()) {
                        Class componentType = clazz.getComponentType();
                        if (!arraysAreEqual(componentType, o1, o2)) return false;
                    }
                } else {
                    return false;
                }
            } else {
                return o1 == null && o2 == null;
            }
        }
        return true;
    }

    /**
     * Method to determine if two arrays are equal.
     * 
     * This performs a deep equality, as opposed to the Arrays.equals
     * method.
     * 
     * @param componentType the component type of the arrays
     * @param o1 One array
     * @param o2 The other array
     * @return true if both arrays contain equal content.
     */
    public static boolean arraysAreEqual(Class componentType, Object o1, Object o2) {
        if (componentType.isArray()) {
            return arraysAreEqual((Object[])o1,(Object[])o2);
        } else if (componentType.isPrimitive()) {
            if (componentType.equals(boolean.class)) {
                return Arrays.equals((boolean[])o1,(boolean[])o2);
            } else if (componentType.equals(byte.class)) {
                return Arrays.equals((byte[])o1, (byte[])o2);
            } else if (componentType.equals(char.class)) {
                return Arrays.equals((char[])o1, (char[])o2);
            } else if (componentType.equals(double.class)) {
                return Arrays.equals((double[])o1, (double[])o2);
            } else if (componentType.equals(float.class)) {
                return Arrays.equals((float[])o1, (float[])o2);
            } else if (componentType.equals(int.class)) {
                return Arrays.equals((int[])o1, (int[])o2);
            } else if (componentType.equals(long.class)) {
                return Arrays.equals((long[])o1, (long[])o2);
            } else if (componentType.equals(short.class)) {
                return Arrays.equals((short[])o1, (short[])o2);
            } else {
                throw new Error("Unrecognized primitive type " + componentType);
            }
        } else {
            return Arrays.equals((Object[])o1, (Object[])o2);
        }
    }
}
