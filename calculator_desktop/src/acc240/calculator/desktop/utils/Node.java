package acc240.calculator.desktop.utils;

/**
 * A simple node class to create equations.
 */
public final class Node {
    private Data data;
    private Node next;
    private Node previous;

    public Node(Data data, Node next, Node previous) {
        this.data = data;
        this.next = next;
        this.previous = previous;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Node getPrevious() {
        return previous;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }
}
