package acc240.utils;

/**
 * A basic interface for a stack.
 *
 * @author Aaron Councilman
 */
public interface Stack<E> {

    /**
     * Returns the number of items in the stack.
     *
     * If the pop() method is called this number of times, the stack
     * should be left empty.
     *
     * @return The number of elements in the stack
     */
    int length();

    /**
     * Remove and return the top item in the stack.
     *
     * The item is removed and returned. If there is no item, an exception
     * should be thrown.
     *
     * @return The value of the top item that was removed
     */
    E pop();

    /**
     * Returns the top item in the stack without removing it.
     *
     * The item is returned but remains on top of the stack. If there is no item
     * an exception should be thrown.
     *
     * @return The top item on the stack
     */
    E peek();

    /**
     * Add an item to the top of the stack.
     *
     * @param item The item to be placed on the top of the stack
     */
    void push(E item);
}
