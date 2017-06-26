package edu.temple.cis.c3238.methodcall;

public class MethodResponse {
    
    private final Object result;
    private final Class resultType;
    
    /**
     * Construct a MethodResponse object
     * @param resultType the type of the result
     * @param result The result to be encapsulated.
     */
    public MethodResponse(Class resultType, Object result) {
        this.resultType = resultType;
        this.result = result;
    }
    
    /**
     * Get the result
     * @return The result if it is not an exception
     * @throws Throwable if the result is an exception
     */
    public Object getResult() throws Throwable {
        if (Throwable.class.isAssignableFrom(result.getClass())) {
            Throwable throwable = (Throwable) result;
            throw throwable;
        }
        return result;
    }
    
    /**
     * Return an XML representation of the MethodResponse
     * @return an XML representation of the MethodResponse
     */
    public String toXML() {
        StringBuilder stb = new StringBuilder();
        stb.append("<methodResponse>\n");
        stb.append("<type>");
        stb.append(resultType.getName());
        stb.append("</type>\n");
        stb.append(MethodCall.valueToXML(resultType, result));
        stb.append("\n</methodResponse>\n");
        return stb.toString();
    }
    
    /**
     * Return a string representation of the MethodResponse
     * @return a string representation of the MethodResponse
     */
    public String toString() {
        return "resultType: " + resultType + " result: " + result;
    }
    /**
     * Determine if this MethodResponse is equal to another MethodResponse
     * @param o The other object
     * @return true of the results are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() == o.getClass()) {
            MethodResponse other = (MethodResponse) o;
            if (!resultType.equals(other.resultType)) return false;
            if (resultType.isArray()) {
                Class componentType = resultType.getComponentType();
                return MethodCall.arraysAreEqual(componentType, result, other.result);
            } else if (Throwable.class.isAssignableFrom(resultType)) {
                Throwable thisResult = (Throwable) this.result;
                Throwable otherResult = (Throwable) other.result;
                return thisResult.getClass() == otherResult.getClass() && 
                        thisResult.getMessage().equals(otherResult.getMessage());
            } else {
                return result.equals(other.result);
            }
        } else {
            return false;
        }
    }
    
    /**
     * Generate and return HashCode
     * @return result.hashCode()
     */
    @Override
    public int hashCode() {
        return result.hashCode();
    }
}
