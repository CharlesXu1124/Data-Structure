import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Your implementation of HashMap.
 * 
 * @author Zheyuan Xu
 * @userid zxu322
 * @GTID 903132413
 * @version 1.0
 */
public class HashMap<K, V> {

    // DO NOT MODIFY OR ADD NEW GLOBAL/INSTANCE VARIABLES
    public static final int INITIAL_CAPACITY = 13;
    public static final double MAX_LOAD_FACTOR = 0.67;
    private MapEntry<K, V>[] table;
    private int size;

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code INITIAL_CAPACITY}.
     *
     * Do not use magic numbers!
     *
     * Use constructor chaining.
     */
    public HashMap() {
        table = (MapEntry<K, V>[]) new MapEntry[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code initialCapacity}.
     *
     * You may assume {@code initialCapacity} will always be positive.
     *
     * @param initialCapacity initial capacity of the backing array
     */
    public HashMap(int initialCapacity) {
        table = (MapEntry<K, V>[]) new MapEntry[initialCapacity];
        size = 0;
    }

    /**
     * Adds the given key-value pair to the HashMap.
     * If an entry in the HashMap already has this key, replace the entry's
     * value with the new one passed in.
     *
     * In the case of a collision, use linear probing as your resolution
     * strategy.
     *
     * At the start of the method, you should check to see if the array would
     * violate the max load factor after adding the data (regardless of
     * duplicates). For example, let's say the array is of length 5 and the
     * current size is 3 (LF = 0.6). For this example, assume that no elements
     * are removed in between steps. If another entry is attempted to be added,
     * before doing anything else, you should check whether (3 + 1) / 5 = 0.8
     * is larger than the max LF. It is, so you would trigger a resize before
     * you even attempt to add the data or figure out if it's a duplicate.
     *
     * When regrowing, resize the length of the backing table to
     * 2 * old length + 1. You must use the resizeBackingTable method to do so.
     *
     * Return null if the key was not already in the map. If it was in the map,
     * return the old value associated with it.
     *
     * @param key key to add into the HashMap
     * @param value value to add into the HashMap
     * @throws IllegalArgumentException if key or value is null
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     */
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("key and value cannot be null!");
        }
        if ((double) (size + 1) / table.length > MAX_LOAD_FACTOR) {
            resizeBackingTable(table.length * 2 + 1);
        }
        MapEntry<K, V> e = new MapEntry<>(key, value);
        int rm = 0;
        boolean deleted = false;
        for (int i = 0; i < table.length; i++) {
            int ind = (Math.abs(key.hashCode())
                % table.length + i) % table.length;
            MapEntry<K, V> m = table[ind];
            if (m == null) {
                if (deleted) {
                    table[rm] = e;
                } else {
                    table[ind] = e;
                }
                size++;
                return null;
            } else {
                if (!m.isRemoved()) {
                    if (m.getKey().equals(key)) {
                        V returned = m.getValue();
                        table[ind] = e;
                        return returned;
                    }
                } else {
                    if (!deleted) {
                        rm = ind;
                        deleted = true;
                    }
                }
            }
        }
        if (deleted) {
            table[rm] = e;
            size++;
        }
        return null;
    }

    /**
     * Removes the entry with a matching key from the HashMap.
     *
     * @param key the key to remove
     * @throws IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException if the key does not exist
     * @return the value previously associated with the key
     */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null!");
        }
        for (int i = 0; i < table.length; i++) {
            int ind = (Math.abs(key.hashCode())
                % table.length + i) % table.length;
            MapEntry<K, V> curr = table[ind];
            if (curr != null) {
                if (!curr.isRemoved()) {
                    if (curr.getKey().equals(key)) {
                        V returned = curr.getValue();
                        table[ind].setKey(null);
                        table[ind].setValue(null);
                        table[ind].setRemoved(true);
                        size--;
                        return returned;
                    }
                }
            } else {
                throw new 
                java.util.NoSuchElementException("Key not exist in hashmap");
            }
        }
        throw new java.util.NoSuchElementException("Key not exist in hashmap");
    }

    /**
     * Gets the value associated with the given key.
     *
     * @param key the key to search for
     * @throws IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException if the key is not in the map
     * @return the value associated with the given key
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null!");
        }
        for (int i = 0; i < table.length; i++) {
            int index = (Math.abs(key.hashCode())
                % table.length + i) % table.length;
            MapEntry<K, V> e = table[index];
            if (e != null) {
                if (!e.isRemoved()) {
                    if (e.getKey().equals(key)) {
                        return e.getValue();
                    }
                }
            } else {
                throw new
                java.util.NoSuchElementException("Key not exist in hashmap");
            }
        }
        throw new java.util.NoSuchElementException("Key not exist in hashmap");
    }

    /**
     * Returns whether or not the key is in the map.
     *
     * @param key the key to search for
     * @throws IllegalArgumentException if key is null
     * @return whether or not the key is in the map
     */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null!");
        }
        for (int i = 0; i < table.length; i++) {
            int index = (Math.abs(key.hashCode())
                % table.length + i) % table.length;
            MapEntry<K, V> e = table[index];
            if (e != null) {
                if (!e.isRemoved()) {
                    if (e.getKey().equals(key)) {
                        return true;
                    }
                }
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * Returns a Set view of the keys contained in this map.
     * Use {@code java.util.HashSet}.
     *
     * @return set of keys in this map
     */
    public Set<K> keySet() {
        Set<K> s = new HashSet<>();
        if (size == 0) {
            return s;
        }
        for (MapEntry<K, V> e : table) {
            if (e != null) {
                if (!e.isRemoved()) {
                    if (e.getKey() != null) {
                        s.add(e.getKey());
                    }
                }
            }
        }
        return s;
    }

    /**
     * Returns a List view of the values contained in this map.
     *
     * Use {@code java.util.ArrayList} or {@code java.util.LinkedList}.
     *
     * You should iterate over the table in order of increasing index and add 
     * entries to the List in the order in which they are traversed.
     *
     * @return list of values in this map
     */
    public List<V> values() {
        List<V> list = new ArrayList<>();
        if (size == 0) {
            return list;
        }
        for (MapEntry<K, V> e : table) {
            if (e != null) {
                if (!e.isRemoved()) {
                    if (e.getValue() != null) {
                        list.add(e.getValue());
                    }
                }
            }
        }
        return list;
    }

    /**
     * Resize the backing table to {@code length}.
     *
     * Disregard the load factor for this method. So, if the passed in length is
     * smaller than the current capacity, and this new length causes the table's
     * load factor to exceed MAX_LOAD_FACTOR, you should still resize the table
     * to the specified length and leave it at that capacity.
     *
     * You should iterate over the old table in order of increasing index and
     * add entries to the new table in the order in which they are traversed.
     *
     * Remember that you cannot just simply copy the entries over to the new
     * array.
     *
     * Also, since resizing the backing table is working with the non-duplicate
     * data already in the table, you shouldn't need to check for duplicates.
     *
     * @param length new length of the backing table
     * @throws IllegalArgumentException if length is less than the number of
     * items in the hash map
     */
    public void resizeBackingTable(int length) {
        if (length < 0 || length < size) {
            throw new
            IllegalArgumentException("length hasn't ..."
                + "reached the threshold for resize");
        }
        MapEntry<K, V>[] t = new MapEntry[length];
        for (int i = 0; i < table.length; i++) {
            MapEntry<K, V> e = table[i];
            if (e != null) {
                if (!e.isRemoved()) {
                    K key = e.getKey();
                    V value = e.getValue();
                    int hashCode = key.hashCode();
                    int hash = Math.abs(hashCode) % length;
                    boolean end = false;
                    int j = 0;
                    while (j < length && !end) {
                        int index = (hash + j) % length;
                        if (t[index] == null) {
                            t[index] = new MapEntry<>(key, value);
                            end = true;
                        }
                        j++;
                    }
                }
            }
        }
        table = t;
    }

    /**
     * Clears the table and resets it to {@code INITIAL_CAPACITY}.
     */
    public void clear() {
        table = (MapEntry<K, V>[]) new MapEntry[INITIAL_CAPACITY];
        size = 0;
    }
    
    /**
     * Returns the number of elements in the map.
     *
     * DO NOT USE OR MODIFY THIS METHOD!
     *
     * @return number of elements in the HashMap
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * DO NOT USE THIS METHOD IN YOUR CODE. IT IS FOR TESTING ONLY.
     *
     * @return the backing array of the data structure, not a copy.
     */
    public MapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

}