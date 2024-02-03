package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.datastructures.trees.BinarySearchTree;
import cse332.datastructures.trees.BinarySearchTree.BSTNode;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.worklists.WorkList;
import datastructures.worklists.ArrayStack;

import java.util.Iterator;
import java.util.NoSuchElementException;

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

    private Node head; //points to first node
    private Node tail; //points to last node
    private int size; //number of nodes in the linked list

    class Node {
        K key;
        V value;
        Node next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    
    /**
     * Associates the specified value with the specified key in this map. If the
     * map previously contained a mapping for the key, the old value is
     * replaced.
     *
     * @param key
     *            key with which the specified value is to be associated
     * @param value
     *            value to be associated with the specified key
     * @return the previous value associated with <tt>key</tt>, or <tt>null</tt>
     *         if there was no mapping for <tt>key</tt>.
     * @throws IllegalArgumentException
     *             if either key or value is null.
     */
    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key or value cannot be null");
        }

         // Check if the key already exists in the list
         Node current = head;
         Node prev = null;

         //check if key exists
         // Iterate through the list to end
         while (current != null) {
            //if we find the key we are inserting, move it to the front
             if (current.key.equals(key)) {

                 // Move the existing node to the front, if the node is not the first node
                 if (prev != null) { 
                     prev.next = current.next; //set the previous node's next to the current node's next
                     current.next = head; //point the current node to the old node at the head
                     head = current; //set the head to point to the current node(new head)
                 }

                 //it is at the front already

                 V oldValue = current.value; //value of the current node before we change it
                 current.value = value; //change the value of the current node to the new value
                 return oldValue; //return the old value
             }
             prev = current; //remember the previous node
             current = current.next; //move to the next node
         }
         
         size++; //increment the size of the list
         //old key not found
         Node newNode = new Node(key, value); //create a new node with the key and value
         newNode.next = head; //set the new node's next to the old head
         head = newNode; //point the head pointer to the new node
 
         // Update tail if the list was empty
         if (tail == null) {
             tail = newNode;
         }
         //return null if the key was not found, no old value replaced
         return null;
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
     *             if key is null.
     */
    @Override
    public V find(K key) {
        
        if(key == null) {
            throw new IllegalArgumentException();
        }

        // Check if the key already exists in the list
        Node current = head;

        while (current != null) {
            //if we find the key we are inserting, move it to the front
             if (current.key.equals(key)) {

                 return current.value; //return the value of the current node
             }
             current = current.next; //move to the next node
         }

         //did not find key

         return null; //return null if the key was not found


    }

        /**
     * An iterator over the keys of the map
     */
    @Override
    public Iterator<Item<K, V>> iterator() {
        return new MoveToFrontListIterator();
    }


    private class MoveToFrontListIterator implements Iterator<Item<K, V>> {
        private Node current; //start at the head of the list
        
        public MoveToFrontListIterator() {
            if (head != null) {
                this.current = head;
            }
        }
        
          /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return current != null; //if the next node is not null, there is a next node
        }


        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public Item<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Item<K, V> item = new Item<>(current.key, current.value); //create a new item with the current node's key and value
            current = current.next; //move to the next node
            return item; //return the item
        }
    }





}
