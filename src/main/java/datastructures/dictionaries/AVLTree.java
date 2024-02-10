package datastructures.dictionaries;

import java.lang.reflect.Array;

import cse332.datastructures.trees.BinarySearchTree;

/**
 * AVLTree must be a subclass of BinarySearchTree<E> and must use
 * inheritance and calls to superclass methods to avoid unnecessary
 * duplication or copying of functionality.
 * <p>
 * 1. Create a subclass of BSTNode, perhaps named AVLNode.
 * 2. Override the insert method such that it creates AVLNode instances
 * instead of BSTNode instances.
 * 3. Do NOT "replace" the children array in BSTNode with a new
 * children array or left and right fields in AVLNode.  This will
 * instead mask the super-class fields (i.e., the resulting node
 * would actually have multiple copies of the node fields, with
 * code accessing one pair or the other depending on the type of
 * the references used to access the instance).  Such masking will
 * lead to highly perplexing and erroneous behavior. Instead,
 * continue using the existing BSTNode children array.
 * 4. Ensure that the class does not have redundant methods
 * 5. Cast a BSTNode to an AVLNode whenever necessary in your AVLTree.
 * This will result a lot of casts, so we recommend you make private methods
 * that encapsulate those casts.
 * 6. Do NOT override the toString method. It is used for grading.
 * 7. The internal structure of your AVLTree (from this.root to the leaves) must be correct
 */

public class AVLTree<K extends Comparable<? super K>, V> extends BinarySearchTree<K, V> {
 

    public class AVLNode extends BSTNode {
        public int height;//height of the node
        //public AVLNode[] children; inherited from BSTNode
        //public K key; inherited from Item
        //public V value; inherited from Item
        //also inherited.equals and hashCode methods from Item

        public AVLNode(K key, V value) {
            super(key, value); //call BSTNode constructor, which then calls item constructor
            //initializes to a new AVLNode with key and value, with an empty children array that can store 2 bst nodes
            this.height = 0; //initialize height to 0 of current node
        }


    }

    //BSTNode root; inherited from BinarySearchTree
    //int size; inherited from BinarySearchTree, need to adjust size for each node added or removed


    public AVLTree() {
        super();
        this.size =0; //initialize with empty root
    }

    public int height(AVLNode node) {
        if (node == null) { //if node is null, return -1
            return -1;
        }
        return node.height;
    }


    //override insert method to create AVLNode instances instead of BSTNode instances
    //find method to return AVLNode instead of BSTNode
    //find the insertion location based off the tree
    //if we find the key, we return the node so we can overwrite the value
    //if we don't find the key, we create a new node and insert it into the tree at the correct location so that
    // all keys to the left are less than the current node, and all keys to the right are greater than the current node
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
    protected AVLNode find(K key, V value) {

        if(key == null) {
            throw new IllegalArgumentException();
        }

        AVLNode prev = null;
        AVLNode current = (AVLNode) this.root;

        int child = -1;

        while (current != null) { //while we haven't reached the end of the tree
            int direction = Integer.signum(key.compareTo(current.key)); //compare the key we want to add to the current node's key
            //if the key is less than the current node's key, direction will be -1, if it's greater, direction will be 1, if it's equal, direction will be 0

            // We found the key!
            if (direction == 0) {
                return current; //return the current node, so we can overwrite the value
            }
            //did not find keep looking
            else {
                // direction + 1 = {0, 2} -> {0, 1}
                child = Integer.signum(direction + 1);
                prev = current;
                current = (AVLNode) current.children[child];
            }
        }

        //did not find the key, so we need to add it to the tree

        // If value is not null, we need to actually add in the new value
        if (value != null) {
            current = new AVLNode(key, null);
            if (this.root == null) {
                this.root = current;
            }
            else {
                assert(child >= 0); // child should have been set in the loop
                                    // above
                prev.children[child] = current;
            }
            this.size++;
        }

        return current;
    }



    //overwrite the insert method
    //return the previous value associated with value, or null if there was no mapping for key
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
            throw new IllegalArgumentException();
        }
        AVLNode current = find(key, value); //find the node to insert to, compare sizes of each one
        V oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    /*
     * extended methods from BinarySearchTree:
     * find(K key, V value) :  you should not call the find(K key, V value) method (with a non-null second argument) in BinarySearchTree as part of your insert method.
     *  It’s okay if you end up duplicating some of the find(K key, V value) logic in your insert method. 
     *  find(K key, V value) puts BSTNodes in your AVLTree and returns them to you. 
     * These nodes can’t be cast to AVLNode (because they were initialized as BSTNodes), and, since they are BSTNodes, 
     * they don’t have that all-important height field, so you can’t use them. 

     -will need to override find method to return AVLNode instead of BSTNode
    -You will not need to write a separate find(key) method, though, since the behavior of that method will be the same for both tree types, meaning that the inherited method already behaves correctly. 

     */



}
