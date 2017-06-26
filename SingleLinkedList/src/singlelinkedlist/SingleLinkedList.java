/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package singlelinkedlist;

public class SingleLinkedList {

    SLLNode start;	// be careful when handling this field!

    public SingleLinkedList() {
        start = null;
        System.out.println("A List Object was created");
    }

    /**
     * addLast
     *
     * @param iValue
     */
    public void addLast(int iValue) {
        SLLNode newNode = new SLLNode(iValue);

        if (start == null) {
            start = newNode;
            return;
        }

        // search for last element
        // hop from one element to its next neighbour
        // until the last one is reached
        SLLNode current = start;
        while (current.next != null) {
            current = current.next;
        }

        // Link in new element at the end
        current.next = newNode;
    }

    /**
     * addFirst
     *
     * @param iValue
     */
    public void addFirst(int iValue) {
        SLLNode newNode = new SLLNode(iValue);
        newNode.next = start;
        start = newNode;
    }

    /**
     * getFirst
     *
     * @return
     */
    public int getFirst() {
        if (start == null) {
            return (Integer.MIN_VALUE);
        }
        return (start.data);
    }

    /**
     * getLast
     *
     * @return
     */
    public int getLast() {
        if (start == null) {
            return (Integer.MIN_VALUE);
        }
        SLLNode current;
        current = start;
        while (current.next != null) {
            current = current.next;
        }
        return (current.data);
    }

    /**
     * getElementAt
     * @param index
     * @return 
     */
    public int getElementAt(int index) {
        if (start == null) {
            return (Integer.MIN_VALUE);
        }
        SLLNode current;
        current = start;
        while (true) {
            if (index == 0) {
                return (current.data);
            }
            if (current.next == null) {
                return (Integer.MIN_VALUE);
            }
            index--;
            current = current.next;
        }
    }

    /**
     * printList
     */
    public void printList() {
        if (start == null) {
            return;
        }
        SLLNode current = start;  // IMPORTANT!!! do NOT use start during iteration!
        while (current != null) {
            System.out.println(current.toString());
            current = current.next;
        }
    }

    /**
     * remove First
     */
    public void removeFirst() {
        if (start == null) {
            return;
        }
        start = start.next;
    }

    /**
     * remove last
     */
    public void removeLast() {
    }

    public static void main(String[] args) {
        SingleLinkedList sll = new SingleLinkedList();
        for (int i = 0; i < 10; i++) {
            sll.addLast(i);
        }
        //sll.traverse();
        System.out.println("------------");
        System.out.println(sll.getFirst());
        System.out.println(sll.getLast());
        System.out.println(sll.getElementAt(4));
    }
}
