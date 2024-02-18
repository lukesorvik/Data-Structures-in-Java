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
        // public BSTNode[] children; inherited from BSTNode
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



    public V insert(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        V oldval = find(key); // get the old value of the key
        AVLNode newNode = new AVLNode(key,value);

        //if we did no
        if(oldval == null) {
            this.size++; //we are going to add a new vals
        }


        this.root = insertRec(newNode, (AVLNode) this.root); // passes our root node, key, and value to the recursive insert method

        //returns the root after the insert and after the tree has been balanced

        return oldval; // return the old value

    }


    public AVLNode insertRec(AVLNode newNode, AVLNode root) {

        // base cases
        // --------------------------
        // did not find the key, so we insert the new node
        if (root == null) {
            return newNode;
        }

        int comparison = root.key.compareTo(newNode.key); //if we have to iterate through a list, only do it once instead of twice
        
        //key > node.key then go right
        if(comparison >  0) {
            root.children[0] = insertRec(newNode, (AVLNode) root.children[0]); //call the left child to insert, update this node's child with the new node returned
        } 
        
        //root.key < newNode.key then go right, since new key is greater than the current node's key
        else if(comparison <  0) {
            root.children[1] = insertRec(newNode, (AVLNode) root.children[1]); //call the right child to insert, update this node's child with the new node returned
        }

        //have same key values
        else {
            root.value = newNode.value; //update the value of the current node
        }
       //after inserting, check if the tree is balanced, starting from the node we just inserted from
        return balance(root, newNode.key);

        
    }

    public AVLNode balance(AVLNode node, K key) {
        if (node == null) {
            return node;
        }

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
        node.height = 1 + Math.max(height((AVLNode) node.children[0]), height((AVLNode) node.children[1])); //set the height of the current node to 1 + the max height of the left and right children
        return node;
    }

    public AVLNode rotateRight(AVLNode node) {
        AVLNode left = (AVLNode) node.children[0]; //left child of node
        AVLNode leftright = (AVLNode) left.children[1]; //right child of left node

        left.children[1] = node; //set .left.right = root
        node.children[0] = leftright; //set root.left = left.right

        node.height = Math.max(height((AVLNode) node.children[0]), height((AVLNode) node.children[1])) + 1;
        left.height = Math.max(height((AVLNode) left.children[0]), height((AVLNode) left.children[1])) + 1;

        return left;
    }


    //at current node rotate left
    public AVLNode rotateLeft(AVLNode node) {
        AVLNode right = (AVLNode) node.children[1]; //get the right child
        AVLNode rightleft = (AVLNode) right.children[0]; //get the left child of the right child

        right.children[0] = node; //set the left = root
        node.children[1] = rightleft; //set the 

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
