package datastructures.dictionaries;

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
 * children array or left and right fields in AVLNode. This will
 * instead mask the super-class fields (i.e., the resulting node
 * would actually have multiple copies of the node fields, with
 * code accessing one pair or the other depending on the type of
 * the references used to access the instance). Such masking will
 * lead to highly perplexing and erroneous behavior. Instead,
 * continue using the existing BSTNode children array.
 * 4. Ensure that the class does not have redundant methods
 * 5. Cast a BSTNode to an AVLNode whenever necessary in your AVLTree.
 * This will result a lot of casts, so we recommend you make private methods
 * that encapsulate those casts.
 * 6. Do NOT override the toString method. It is used for grading.
 * 7. The internal structure of your AVLTree (from this.root to the leaves) must
 * be correct
 */

public class AVLTree<K extends Comparable<? super K>, V> extends BinarySearchTree<K, V> {

    public class AVLNode extends BSTNode {
        public int height;// height of the node
        // public AVLNode[] children; inherited from BSTNode
        // public K key; inherited from Item
        // public V value; inherited from Item
        // also inherited.equals and hashCode methods from Item

        public AVLNode(K key, V value) {
            super(key, value); // call BSTNode constructor, which then calls item constructor
            // initializes to a new AVLNode with key and value, with an empty children array
            // that can store 2 bst nodes
            this.height = 0; // initialize height to 0 of current node
        }

    }

    // BSTNode root; inherited from BinarySearchTree
    // int size; inherited from BinarySearchTree, need to adjust size for each node
    // added or removed

    public AVLTree() {
        super();
        this.size = 0; // initialize with empty root
    }

    public int height(AVLNode node) {
        if (node == null) { // if node is null, return -1
            return -1;
        }
        return node.height;
    }


    // override insert method to create AVLNode instances instead of BSTNode
    // instances
    // find method to return AVLNode instead of BSTNode
    // find the insertion location based off the tree
    // if we find the key, we return the node so we can overwrite the value
    // if we don't find the key, we create a new node and insert it into the tree at
    // the correct location so that
    // all keys to the left are less than the current node, and all keys to the
    // right are greater than the current node
    /**
=
    // overwrite the insert method
    // return the previous value associated with value, or null if there was no
    // mapping for key
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
        if (key == null) {
            throw new IllegalArgumentException();
        }

        V oldval = find(key); // get the old value of the key
        this.root = insertRec((AVLNode) this.root, key, value); // passes our root node, key, and value to the recursive insert method
        //returns the root after the insert and after the tree has been balanced

        return oldval; // return the old value

    }

    // recursively find the node to insert, insert it, then on way back up, check if
    // the tree is balanced
    // returns the old value if we find the key, or null if we don't find the key
    public AVLNode insertRec(AVLNode node, K key, V value) {
        // search for the key in the tree
        // if we find the key, overwrite the value, save the old value, and return it
        // check if the tree is balanced on recursive up

        // base cases
        // --------------------------
        // did not find the key, so we insert the new node
        if (node == null) {
            node = new AVLNode(key, value); // create a new node with the key and value
            this.size++; // increment the size of the tree
            return node; // return the current node
        }
        // found the key, so we overwrite the value
        if (key.compareTo(node.key) == 0) {
            node.value = value; // overwrite the old value with the new value
            return node; // return the current node
        }
        // --------------------------
        // recursive cases
        // should not reach this point if we found the key, so direction should not be 0,-1,1
        


        int direction = Integer.signum(key.compareTo(node.key)); // compare the key to the current node and call recursively in that direction
        //if -1 then key <node.key, if 1 then key > node.key, if 0 then key = node.key

        int child = Integer.signum(direction + 1); //to get the direction to be [0,1] instead of [-1,1], signum(-1+1) = 0, signum(1+1) = 1, any positive value = 1
        AVLNode next = (AVLNode) node.children[child]; //get the next node to go to based off the direction
        node.children[child] = insertRec(next, key, value); //call the child node to insert, update this node's child with the new node returned


        // --------------------------
        // this is after it has been inserted, so we need to check if the tree is balanced, starting from the node we just inserted from
        // check if the tree is balanced, working from the bottom up using the path we took to insert the node

        node.height = 1 + Math.max(height((AVLNode) node.children[0]), height((AVLNode) node.children[1])); //set the height of the current node to 1 + the max height of the left and right children
        int balance = getBalance(node);  //Check the current node if any .right or .left children differ by a size of 1
        //-1 means the right child is larger, 0 means they are the same, 1 means the left child is larger

        // If this node becomes unbalanced, then there are 4 cases:

        // Left Left Case
        //balance > 1 means the left child was imbalanced
        //&& if the key is less than the left child's key, then we need to rotate right (we added in the left subtree of left child)
        if (balance > 1 && key.compareTo(((AVLNode) node.children[0]).key) < 0) {
            return rotateRight(node);
        }


        // Right Right Case
        //balance < -1 means one the right child was imbalanced
        //&& if the key is greater than the right child's key, then we need to rotate left (we added in the right subtree of right child)
        if (balance < -1 && key.compareTo(node.children[1].key) > 0) {
            return rotateLeft(node);
        }


        // Left Right Case
        //balance > 1 means the left child was imbalanced
        //&& if the key is greater than the left child's key, then we need to we added in the right subtree of left child
        if (balance > 1 && key.compareTo(node.children[0].key) > 0) {
            node.children[0] = rotateLeft((AVLNode) node.children[0]);
            return rotateRight(node);
        }

        // Right Left Case
        //balance < -1 means one the right child was imbalanced
        //&& if the key is less than the right child's key, then we need to rotate right (we added in the left subtree of right child)
        if (balance < -1 && key.compareTo(node.children[1].key) < 0) {
            node.children[1] = rotateRight((AVLNode) node.children[1]);
            return rotateLeft(node);
        }

        // no need to balance the tree
        return node;
    }

    public AVLNode rotateRight(AVLNode node) {
        AVLNode left = (AVLNode) node.children[0];
        AVLNode leftright = (AVLNode) left.children[1];

        left.children[1] = node;
        node.children[0] = leftright;

        node.height = Math.max(height((AVLNode) node.children[0]), height((AVLNode) node.children[1])) + 1;
        left.height = Math.max(height((AVLNode) left.children[0]), height((AVLNode) left.children[1])) + 1;

        return left;
    }

    public AVLNode rotateLeft(AVLNode node) {
        AVLNode right = (AVLNode) node.children[1];
        AVLNode rightleft = (AVLNode) right.children[0];

        right.children[0] = node;
        node.children[1] = rightleft;

        node.height = Math.max(height((AVLNode) node.children[0]), height((AVLNode) node.children[1])) + 1;
        right.height = Math.max(height((AVLNode) right.children[0]), height((AVLNode) right.children[1])) + 1;

        return right;
    }

    // Get the difference in height between the left and right children
    //-1 means the right child is larger, 0 means they are the same, 1 means the left child is larger
    int getBalance(AVLNode N) {
        if (N == null)
            return 0;

        return height((AVLNode) N.children[0]) - height((AVLNode) N.children[1]);
    }

}
