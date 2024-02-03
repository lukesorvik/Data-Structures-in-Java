package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;

import java.util.Iterator;

/**
 * 1. The list is typically not sorted.
 * 2. Add new items to the front of the list.
 * 3. Whenever find or insert is called on an existing key, move it
 * to the front of the list. This means you remove the node from its
 * current position and make it the first node in the list.
 * 4. You need to implement an iterator. The iterator SHOULD NOT move
 * elements to the front.  The iterator should return elements in
 * the order they are stored in the list, starting with the first
 * element in the list. When implementing your iterator, you should
 * NOT copy every item to another dictionary/list and return that
 * dictionary/list's iterator.
 */
public class MoveToFrontList<K, V> extends DeletelessDictionary<K, V> {
    /*
     * could use an array with front and end pointers
     * since it is an array i can just swap the elements in place
     *when using find can just iterate through the array and swap the elements
     * sorted does not matter yippeeee
     * add to the back of the array
     * maybe use the data structure i implemented before (fifo queue??
     * also look at the notes
     * 
     * 
     * [1,2,0,0,0,0]
     * F  B
     * 
     * not sure how to store multiple elements in the array?
     * 
     * 
     * 
     * 
     */


    @Override
    public V insert(K key, V value) {
        throw new NotYetImplementedException();
    }

    @Override
    public V find(K key) {
        throw new NotYetImplementedException();
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        throw new NotYetImplementedException();
    }
}
