package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FIFOWorkList.java
 * for method specifications.
 */
public class ListFIFOQueue<E> extends FIFOWorkList<E> {


    //inner node class, so it can be a linked list
    public class Node {
        public E data; // varaible for storing data
        public Node next; //variable for pointing to next node
        //constructor class
        public Node(E data) {
            this.data = data;
            this.next = null;
        }
    }

    //pointers for front and back of queue
    Node Front = null; //points to the node at the front of the queue
    Node Back = null; //points to the node at the back of the queue
    int size = 0; //keeps track of the size of the queue

    //constructer method, gets called when we create new object
    public ListFIFOQueue() {

    }

    @Override
    public void add(E work) {
        if (Front == null) { //if the list is empty
            Node last = new Node(work); //make new node initialized with data
            Front= last; //point front node to new node
            Back = last;
            size++; //increment size
        }
        else { //if the list is not empty, there is at least one node in it 
            Node last = new Node(work); //make new node initialized with data
            Back.next = last; //point the node back is pointing to, to our new node
            Back = last; //point our back pointer to this new node
            size++; //increment size
        }
    }

    //return the next element in the worklist (so element front points to), like peeking at the top of the stack
    @Override
    public E peek() {
        if (Front == null) { //if front points to nothing
            throw new NoSuchElementException(); //has no work to return
        }

        E top = Front.data; //gets the data from the node top is pointing to
        return top;
    }

    //returns and removes the next element of the worklist, so front of queue?
    //so
    @Override
    public E next() {
        if ((Front == null) ) { //if there is less than 2 nodes
            throw new NoSuchElementException(); //has no work to return
        }

        Node last = Front; //gets the first (last) node in the queue

        //if the node to be removed is the last one in the queue
        if(last.next == null) {
            Front = null;
            Back = null;
            
        }

        //set the front of the queue to the 2nd to last one added
        else {
            Front = last.next; //move front to the next node
        }

        size--; //increment size
        return last.data; //return the data of the node removed
    }

    //returns the number of elements left remaining (excluding first and back pointers)
    //need to be O(1) implementation
    //could iterate through or could have the fronts val be the # of nodes in the thing?
    @Override
    public int size() {
        return size;
    }

    //resets the list to same state after creation
    @Override
    public void clear() {
        //reset pointers
        //java should handle garbage collection
        Front = null;
        Back = null;
        size = 0; //reset size
    }
}
