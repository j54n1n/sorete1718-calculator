package acc240.utils;

import java.util.EmptyStackException;

/**
 * A simple stack to hold double values.
 *
 * @author Aaron Councilman
 */
public class ValueStack implements Stack<Double> {

    private Node head = new Node(Double.NaN, null);
    private int length = 0;

    /**
     * Returns the number of items in the stack.
     * <p>
     * If the pop() method is called this number of times, the stack
     * should be left empty.
     *
     * @return The number of elements in the stack
     */
    @Override
    public int length() {
        return length;
    }

    /**
     * Remove and return the top item in the stack.
     * <p>
     * The item is removed and returned. If there is no item, an exception
     * should be thrown.
     *
     * @return The value of the top item that was removed
     */
    @Override
    public Double pop() {
        if(length > 0) {
            Node top = head.getNext();
            Double res = top.getValue();
            head.setNext(top.getNext());
            length--;
            return res;
        } else
            throw new EmptyStackException();
    }

    /**
     * Returns the top item in the stack without removing it.
     * <p>
     * The item is returned but remains on top of the stack. If there is no item
     * an exception should be thrown.
     *
     * @return The top item on the stack
     */
    @Override
    public Double peek() {
        if(length > 0)
            return head.getNext().getValue();
        else
            throw new EmptyStackException();
    }

    /**
     * Add an item to the top of the stack.
     *
     * @param item The item to be placed on the top of the stack
     */
    @Override
    public void push(Double item) {
        head.setNext(new Node(item, head.getNext()));
        length++;
    }

    private class Node {
        private double value;
        private Node next;

        public Node(double value, Node next) {
            this.value = value;
            this.next = next;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }
}
