/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package doublelinkedlist;

public class DoubleLinkedList {

    Node start;
    Node end;
    int size;

    public DoubleLinkedList() {
        start = null;
        end = null;
        size = 0;
        System.out.println("A List is created");
    }

    public void insertFirst(Object data) {
        Node newNode = new Node(data);
        if (start == null) {
            start = newNode;
            end = start;
        } else {
            start.prev = newNode;
            newNode.next = start;
            start = newNode;
        }
        size++;
    }

    public void insertLast(Object data) {
        Node newNode = new Node(data);
        if (start == null) {
            start = newNode;
            end = start;
        } else {
            newNode.prev = end;
            end.next = newNode;
            end = newNode;
        }
        size++;
    }

    public void insertAt(int index, Object data) {
        if (index == 0) {
            insertFirst(data);
            return;
        }
        Node newNode = new Node(data);
        Node track = start.next.next;
        for (int i = 1; i <= size; i++) {
            if (i == index) {
                newNode.next = track;
                newNode.prev = track.prev;
                track.prev.next = newNode;
                track.prev = newNode;
            }
        }
        size++;
    }

    public void removeFirst() {
        if (size == 1) {
            start = null;
            end = null;
            size = 0;
            return;
        }
        start = start.next;
        start.prev = null;
        size--;
    }

    public void removeLast() {
        if (size == 1) {
            start = null;
            end = null;
            size = 0;
            return;
        }
        end = end.prev;
        end.next = null;
        size--;
    }

    public void removeAt(int index) {
        if (index == 0) {
            if (size == 1) {
                start = null;
                end = null;
                size = 0;
                return;
            }
            start = start.next;
            start.prev = null;
            size--;
            return;
        }
        if (index == size-1) {
            end = end.prev;
            end.next = null;
            size--;
            return;
        }
        Node track = start.next;
        for (int i = 1; i <= size; i++) {
            if (i == index) {
                Node tempa = track.prev;
                Node tempb = track.next;
                tempa.next = tempb;
                tempb.prev = tempa;
                size--;
            }
            track = track.next;
        }
    }

    public void clearList() {
        if (start == null) {
            return;
        }
        start = null;
        end = null;
    }

    public void printAllElements() {
        if (start == null) {
            return;
        }
        Node current = start;
        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
    }

    public static void main(String[] args) {
        DoubleLinkedList dll = new DoubleLinkedList();
        for (int i = 1; i <= 5; i++) {
            dll.insertFirst(i);
        }
        for (int i = 1; i <= 5; i++) {
            dll.insertLast(i);
        }
        dll.removeFirst();
        dll.removeLast();
        dll.removeAt(1);
        for (int i = 0; i < 5; i++) {
            dll.removeAt(2);
        }
        System.out.println("------------");
        dll.printAllElements(); //42
        dll.clearList();
        for (int i = 0; i < 4; i++) {
            dll.insertFirst(1);
        }
        dll.insertAt(2, 2);
        System.out.println("------------");
        dll.printAllElements(); //11211
    }
}
