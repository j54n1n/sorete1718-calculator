package acc240.utils;

/**
 * A collection of variable names and their values.
 *
 * All variables must be single lowercase characters. Any other
 * keys will cause exceptions.
 *
 * @author Aaron Councilman
 */
public class Variables implements Dictionary<Character, Double> {

    private double[] values;
    private boolean[] set;

    public Variables() {
        values = new double[26];
        set = new boolean[26];
    }

    /**
     * Add a key-value pair to the dictionary.
     * <p>
     * This method can also be used to update the value of a key.
     *
     * @param key The key value of the pair
     * @param val The data of the pair
     */
    @Override
    public void add(Character key, Double val) {
        values[key - 'a'] = val;
        set[key - 'a'] = true;
    }

    /**
     * Get the value of a pair from the key.
     * <p>
     * If the key does not exist the function throws an exception.
     *
     * @param key The key to find
     * @return The value of the key pair
     */
    @Override
    public Double getValue(Character key) {
        return values[key - 'a'];
    }

    /**
     * Checks to see whether or not a key pair exists.
     *
     * @param key The key to check
     * @return true if the key exists, false otherwise
     */
    @Override
    public boolean exists(Character key) {
        return set[key - 'a'];
    }
}
