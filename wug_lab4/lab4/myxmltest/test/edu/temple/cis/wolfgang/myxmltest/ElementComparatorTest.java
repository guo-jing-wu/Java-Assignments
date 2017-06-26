/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.temple.cis.wolfgang.myxmltest;

import org.junit.Test;
import static org.junit.Assert.*;
import org.w3c.dom.Element;

/**
 *
 * @author Paul Wolfgang
 */
public class ElementComparatorTest {
    
    public ElementComparatorTest() {
    }

    @Test
    public void testEqual() throws Exception {
        TestElementFactory factory = new TestElementFactory();
        Element e1 = factory.newElement("<value><int>10</int></value>");
        Element e2 = factory.newElement("<value>\n     <int>10</int>\n</value>\n");
        assertTrue(ElementComparator.isEqual(e1, e2));
    }
}
