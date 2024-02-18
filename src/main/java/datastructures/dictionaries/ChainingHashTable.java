package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.Dictionary;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

/**
 * - You must implement a generic chaining hashtable. You may not
 * restrict the size of the input domain (i.e., it must accept
 * any key) or the number of inputs (i.e., it must grow as necessary).
 *
 * - ChainingHashTable should rehash as appropriate (use load factor as shown in
 * lecture!).
 *
 * - ChainingHashTable must resize its capacity into prime numbers via given
 * PRIME_SIZES list.
 * Past this, it should continue to resize using some other mechanism (primes
 * not necessary).
 *
 * - When implementing your iterator, you should NOT copy every item to another
 * dictionary/list and return that dictionary/list's iterator.
 */
// the type of type of the chain should be generic, so we can use any data
// structure that implements the dictionary interface to chain the keys in each
// bucket (index)
public class ChainingHashTable<K, V> extends DeletelessDictionary<K, V> {
    private Supplier<Dictionary<K, V>> newChain; // a supplier of new chains
    int size; // the number of elements in the hashtable
    int capacity; // the capacity of the hashtable, used for hashing, needs to be a prime number,
                  // and will be updated when rehashing
    int loadFactor; // the load factor of the hashtable
    private Dictionary<K, V>[] bucket; // an array where each element is a dictionary that will store the keys that
                                       // hash to that index
    int primeIndex = 0; // the index of the prime number in the PRIME_SIZES array that we are currently
                        // using

    static final int[] PRIME_SIZES = { 11, 23, 47, 97, 193, 389, 773, 1549, 3089, 6173, 12347, 24697, 49393, 98779,
            197573, 395147 };

    public ChainingHashTable(Supplier<Dictionary<K, V>> newChain) {
        this.newChain = newChain;
        // supplier can be any data structure that implements the dictionary interface,
        // such as AVLTree or MoveToFrontList
        // doesnt have to be a linked list
        // supplier is expected to return a new instance of the dictionary each time we
        // use .get
        bucket = new Dictionary[11]; // initialize the bucket with a size of 11 (the first prime number in the
                                     // PRIME_SIZES array)
        capacity = PRIME_SIZES[primeIndex]; // used for hashing
        size = 0;
        loadFactor = 0;
    }

  
    /**
     * Associates the specified value with the specified key in this map. If the
     * map previously contained a mapping for the key, the old value is
     * replaced.
     *
     * @param key
     *              key with which the specified value is to be associated
     * @param value
     *              value to be associated with the specified key
     * @return the previous value associated with <tt>key</tt>, or <tt>null</tt>
     *         if there was no mapping for <tt>key</tt>.
     * @throws IllegalArgumentException
     *                                  if either key or value is null.
     */
    @Override
    public V insert(K key, V value) {

        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }

        // compute the index using i=hash(key)
        // call find with the key on the data structure at the index i (bucket[i]) find
        // if the key is already in the dictionary, if it is, update the value
        // if it is not, insert the key value pair into the dictionary

        int i = hash(key); // get the index where the key should hash to
        if (bucket[i] == null) {
            bucket[i] = newChain.get(); // if the dictionary at the index i is null, create a new dictionary using the supplier
        }

        // there is a dictionary at the index i, so we can call insert on it
        V oldValue = bucket[i].insert(key, value); // insert the key value pair into the dictionary at the index i,
                                                   // calling insert returns the old value
        if (oldValue == null) {
            size++; // if the old value is null, that means the key was not in the dictionary, so we
                    // have added a new key value pair
        }

        this.loadFactor = size / capacity; // update the load factor
        // check if the load factor is greater than 2, if it is, rehash
        if (loadFactor >= 2) {
            rehash();
        }

        this.loadFactor = size / capacity; // update the load factor
        return oldValue; // return the old value

    }

    /**
     * Returns the value to which the specified key is mapped, or {@code null}
     * if this map contains no mapping for the key.
     *
     * @param key
     *            the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or {@code null}
     *         if this map contains no mapping for the key
     * @throws IllegalArgumentException
     *                                  if key is null.
     */
    @Override
    public V find(K key) {

        if (key == null) {
            throw new IllegalArgumentException();
        }

        int index = hash(key); // get the index of the key

        // iterate through the dictionary there to find the key? or use the .find of the
        // dictionary

        if (bucket[index] == null) { // if the dictionary at the index i is null
            return null; // return null
        }

        // else the dictionary at the index i is not null, so we can call find on it

        V value = bucket[index].find(key); // use the dictionaries implementation of find to get the value from the dictionary

        return value; // return the value

    }

    /**
     * An iterator over the keys of the map
     * iterate over the chaining hashmap?
     * d
     * go to index 0, iterate through all keys in that dictionary in the bucket,
     * then go to the next index
     */
    @Override
    public Iterator<Item<K, V>> iterator() {

        return new ChainingHashTableIterator(); 
        // return a new instance of the ChainingHashTableIterator, which is an iterator over the array of dictionaries and the keys in each dictionary

    }

    private class ChainingHashTableIterator implements Iterator<Item<K, V>> {
        int bucketIndex = -1; // the index of the bucket we are currently at, initialize at 0 so we can tell if it is the first time we are calling the iterator
        Iterator<Item<K, V>> bucketIterator; // the iterator of the dictionary at the current index

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            // if we haven't looked for a bucket or the current buck has no more elements
            if (bucketIndex == -1 || bucketIterator.hasNext() == false) {
                getNextBucket(); // get the next bucket
                // should go to the next bucket that has an object, if not next bucket then will
                // end on the last bucket
            }

            // if the current iterator is null, return false, we are at the last bucket and
            // it is empty
            if (bucketIterator == null) {
                return false;
            }

            // else the next bucket has an object, check if that object has a next element
            // should get the next bucket that has an object, if no next bucket exists then
            // it will end on the last bucket
            return bucketIterator.hasNext(); // return true if the current iterator has more elements

        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public Item<K, V> next() {

            // if the iterator has no more elements
            if (hasNext() == false) {
                throw new NoSuchElementException(); // throw an exception
            }

            return bucketIterator.next(); // return the next element in the iteration

        }

        // gets the next bucket that has an object, if no next bucket exists then it
        // will end on the last bucket
        public void getNextBucket() {
            // this runs when bucketIndex is -1, will find the first bucket that has an
            // object
            if (bucketIndex < capacity || bucketIterator == null || bucketIterator.hasNext() == false) { 
                // if the current index is less than the capacity, or the current iterator is null, or the current iterator has no more elements
                bucketIndex++;
                // while we are in the bouunds of array, and the current index does not have a
                // chain or the current chain is empty
                while (bucketIndex < capacity
                        && (bucket[bucketIndex] == null || bucket[bucketIndex].isEmpty() == true)) {
                    bucketIndex++; // go to the next index

                }
                if (bucketIndex >= capacity) { // if the index is greater than the capacity
                    bucketIterator = null; // set the iterator to null, so we dont do a null pointer moment
                    return; // get outa here
                }
                bucketIterator = bucket[bucketIndex].iterator(); // set the iterator of the current bucket

            }

        }

    }

    /**
     * Temporary fix so that you can debug on IntelliJ properly despite a broken
     * iterator
     * Remove to see proper String representation (inherited from Dictionary)
     */
    /*
     * @Override
     * public String toString() {
     * return "ChainingHashTable String representation goes here.";
     * }
     */

    // hash function,
    // uses the .hashcode of the key % the capacity of our current array
    // returns the index where the key should hash to
    public int hash(K key) {
        int hashval = key.hashCode(); // gets the hashcode of the key
        hashval = hashval % capacity; // mod the hashcode by the capacity of the hashtable

        if (hashval < 0) {
            hashval += capacity; // if the hashval is negative, add the capacity to it
        }

        return hashval;

    }

    public void rehash() {

        Dictionary<K, V>[] oldArray = bucket; // get the old array of dictionaries
        int oldCapacity = capacity; // get the old capacity of old array
        primeIndex++; // increment the prime index

        if(this.primeIndex < PRIME_SIZES.length) {
            capacity = PRIME_SIZES[primeIndex];
        } else {
            capacity = capacity * 2; //if the prime index is greater than the length of the PRIME_SIZES array, double the capacity
        }


        Dictionary<K, V>[] temp = new Dictionary[capacity]; // create a new bucket with the new capacity

        // copy the old dictionary into the new dictionary
        for (int i = 0; i < oldCapacity; i++) {
            if (oldArray[i] != null) { // if the dictionary at the index i is not null
                for (Item<K, V> item : oldArray[i]) { // iterate through the dictionary at the index i to get each item
                    K key = item.key;
                    V value = item.value;
                    int index = hash(key); // get the index where the key should hash to
                    if (temp[index] == null) { // if the dictionary at the index i is null
                       temp[index] = newChain.get(); // if the dictionary at the index i is null, create a new dictionary using the supplier
                    }

                    temp[index].insert(key, value); // insert the key value pair into the dictionary at the index i
                }
            }
        }
        this.bucket = temp; // set the bucket to the new bucket 

    }


    public int size() {
        return this.size;
    }

  

}
