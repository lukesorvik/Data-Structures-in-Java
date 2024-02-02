package datastructures.dictionaries;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.trie.TrieMap;
import cse332.types.BString;

/**
 * See cse332/interfaces/trie/TrieMap.java
 * and cse332/interfaces/misc/Dictionary.java
 * for method specifications.
 */
public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {
    public class HashTrieNode extends TrieNode<Map<A, HashTrieNode>, HashTrieNode> {

        // constructor if they do not insert any parameters
        public HashTrieNode() {
            this(null);
        }

        public HashTrieNode(V value) {
            this.pointers = new HashMap<A, HashTrieNode>(); // java hashmap where the key is of generic type A, and the
                                                            // value is a pointer to another hashTrieNode
            // this can have as many pointers to other nodes as we want
            this.value = value; // value of the node, we use this to store the value of the node
        }

        @Override
        public Iterator<Entry<A, HashTrieMap<A, K, V>.HashTrieNode>> iterator() {
            return pointers.entrySet().iterator();
        }
    }

    // constructor four our HashTrieMap
    public HashTrieMap(Class<K> KClass) {
        super(KClass);
        this.root = new HashTrieNode();
    }

    /**
     * Associates the specified value with the specified key in this map. If the
     * map previously contained a mapping for the key, the old value is
     * replaced.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with <tt>key</tt>, or <tt>null</tt>
     *         if there was no mapping for <tt>key</tt>.
     * @throws IllegalArgumentException if either key or value is null.
     */
    @Override
    public V insert(K key, V value) {

        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }

        HashTrieNode current = (HashTrieNode) this.root; // start at the root node

        for (A element : key) {
            // iterate through the key and check if the current node has a pointer to the
            // next node
            if (!current.pointers.containsKey(element)) { // if the current char does not exist as a key
                // create a mapping for that character, point to a new hashnode
                current.pointers.put(element, new HashTrieNode());
            }
            // move the next node current char is pointing to
            current = current.pointers.get(element); // if the current char(key) does have a mapping, then move to the next node and test the next char
        }

        V oldValue = current.value; // store the old value of the node
        if (oldValue == null) { // cannot insert null, so if it is a new node, will have node value
            this.size++; // this was correct!!!
        }
        current.value = value; // set the value of the node to the new value
        return oldValue;
    }

    /**
     * Returns the value to which the specified key is mapped, or {@code null}
     * if this map contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or {@code null}
     *         if this map contains no mapping for the key
     * @throws IllegalArgumentException if key is null.
     */
    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        HashTrieNode current = (HashTrieNode) root;

        for (A elem : key) { // iterate through the key string "add" for ex
            if (!current.pointers.containsKey(elem)) { // check if current node has a pointer to the next node for "a"
                return null; // Key not found
            }
            current = current.pointers.get(elem); // if the current char(key) does have a mapping, then move to the next node and test the next char
        }
        // reached the end of the key string, so return the value of the node
        return current.value;
    }

    /**
     * Returns <tt>true</tt> if this map contains a mapping for which the key
     * starts with the specified key prefix.
     *
     * @param keyPrefix The prefix of a key whose presence in this map is to be
     *                  tested
     * @return <tt>true</tt> if this map contains a mapping whose key starts
     *         with the specified key prefix.
     * @throws IllegalArgumentException if the key is null.
     */
    @Override
    public boolean findPrefix(K key) {

        if (key == null) {
            throw new IllegalArgumentException();
        }

        HashTrieNode current = (HashTrieNode) root;

        for (A elem : key) {
            if (!current.pointers.containsKey(elem)) { // if current char of key does not have a mapping                                         
                return false; // Prefix not found
            }
            current = current.pointers.get(elem); // if the current char(key) does have a mapping, then move to the next node and test the next char
        }
        // reached the end of the key string, all have mappings pointed to a node
        return true; // Prefix found
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     *
     * @param key key whose mapping is to be removed from the map
     * @throws IllegalArgumentException if key is null.
     */
    @Override
    public void delete(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        // Start the deletion process from the root
        deleteRec((HashTrieNode) root, key.iterator(), key.size());
    }

    // since size of any key's nodes, we must traverse n=size of key nodes
    // traverses recursively to find the node to delete in key,
    //  recursively checks if a child node with no value or pointers needs to be deleted on way out after finding last node
    private void deleteRec(HashTrieNode node, Iterator<A> iterator, int remainingSize) {
        // base case
        if (remainingSize == 0) {
            // Reached the end of the key
            node.value = null; // Set the value of the current node to null
            this.size--; // since we removed the value, not remove a key
        }

        else {
            A elem = iterator.next(); // curent char of the key
            HashTrieNode child = node.pointers.get(elem); // get the child node of the current char

            // if child is null then the path we are trying to remove does not exist
            // will cause null pointer if we try to treat a null object as a node
            if (child != null) { // if the child node exists
                // Recursively delete the next node in the key
                deleteRec(child, iterator, remainingSize - 1);

                // once we reach end of key, this code will run, starting with the last char of
                // the key, and work its way up
                // If the child node has no children and its value is null, remove it from the
                // parent's pointers
                if (child.pointers.isEmpty() && child.value == null) {
                    node.pointers.remove(elem);
                }
                // else: it has children or a value, so do not remove it
            }
        }

    }

    /**
     * Resets the state of this map to be the same as if the constructor were
     * just called.
     */
    @Override
    public void clear() {
        this.root = new HashTrieNode();
        this.size = 0;
    }
}