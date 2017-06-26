/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.temple.cis.wolfgang.myxmltest;

import org.junit.Test;
import static org.junit.Assert.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author Paul Wolfgang
 */
public class TestElementFactoryTest {
    
    public TestElementFactoryTest() {
    }

    @Test
    public void testNewElement() throws Exception {
        TestElementFactory factory = new TestElementFactory();
        Element e = factory.newElement("<value><int>10</int></value>");
        assertEquals("value", e.getNodeName());
        Node child = e.getFirstChild();
        assertEquals(Node.ELEMENT_NODE, child.getNodeType());
        assertEquals("int", child.getNodeName());
        assertEquals("10", child.getTextContent().trim());
        assertNull(child.getNextSibling());
    }
}
