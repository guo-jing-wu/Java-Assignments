/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package singlelinkedlist;

// The Node class contains only the fields
// value and next. It is a very small class, that usually
// would be defined as a local class inside the SingleLinkedList
// class. To be less confusing, I made it its own class here.
public class SLLNode {
    int data;
    SLLNode next = null;

    public SLLNode(int data){
        data = this.data;
    }
    
    @Override
    public String toString(){
        return("SLLNode, data: /t"+data);
    }
}
