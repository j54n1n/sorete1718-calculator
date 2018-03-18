package acc240.utils;

import java.util.EmptyStackException;

/**
 * A stack of mathematical operators.
 *
 * @author Aaron Councilman
 */
public class OperatorStack implements Stack<Operator> {

    private Node head = new Node(null, null);
    private int length = 0;

    @Override
    public int length() {
        return length;
    }

    @Override
    public Operator pop() {
        if(length > 0) {
            Node top = head.getNext();
            Operator res = top.getOperator();
            head.setNext(top.getNext());
            length--;
            return res;
        } else
            throw new EmptyStackException();
    }

    @Override
    public Operator peek() {
        if(length > 0)
            return head.getNext().getOperator();
        else
            throw new EmptyStackException();
    }

    @Override
    public void push(Operator item) {
        head.setNext(new Node(item, head.getNext()));
        length++;
    }

    private class Node {
        private Operator operator;
        private Node next;

        public Node(Operator op, Node nx) {
            operator = op;
            next = nx;
        }

        public void setOperator(Operator op) {
            operator = op;
        }

        public void setNext(Node nx) {
            next = nx;
        }

        public Operator getOperator() {
            return operator;
        }

        public Node getNext() {
            return next;
        }
    }
}
