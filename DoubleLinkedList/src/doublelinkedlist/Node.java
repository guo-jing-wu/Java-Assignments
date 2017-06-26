package doublelinkedlist;

public class Node {

    Object data;
    Node next;
    Node prev;

    public Node(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }
}