package acc240.utils;

/**
 * A simple dictionary interface.
 *
 * A dictionary is a set of key and value pairs.
 *
 * @author Aaron Councilman
 */
public interface Dictionary<E, J> {

    /**
     * Add a key-value pair to the dictionary.
     *
     * This method can also be used to update the value of a key.
     *
     * @param key The key value of the pair
     * @param val The data of the pair
     */
    void add(E key, J val);

    /**
     * Get the value of a pair from the key.
     *
     * If the key does not exist the function throws an exception.
     *
     * @param key The key to find
     * @return The value of the key pair
     */
    J getValue(E key);

    /**
     * Checks to see whether or not a key pair exists.
     *
     * @param key The key to check
     * @return true if the key exists, false otherwise
     */
    boolean exists(E key);
}
