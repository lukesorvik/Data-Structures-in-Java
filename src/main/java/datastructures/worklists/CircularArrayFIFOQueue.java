package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FixedSizeFIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FixedSizeFIFOWorkList.java
 * for method specifications.
 */
public class CircularArrayFIFOQueue<E> extends FixedSizeFIFOWorkList<E> {

    /*
    * Array under the hood, represented kinda like a donut
    * ints for front and back
    *
    * */

    int capacity; //how many elements can fit in the array
    E[] array; //array for the class
    public int front = 0; //pointer to the index of the oldest item in queue "front", points to front of worklist
    public int back = 0;//pointer to the index of the most recently added item in queue "back", points to back of worklist
    int size = 0; //keeps track of how many elements are added, worklist size



    public CircularArrayFIFOQueue(int capacity) {
        super(capacity); //calls the superclasses constructor method with the parameter (capacity)
        //^ this should cause an exception if the user inputs 0

        this.capacity = capacity; //sets the capacity variable for this class
        this.array = (E[])new Comparable[capacity];


    }

    /**
     * Adds work to the worklist. This method should conform to any additional
     * contracts that the particular type of worklist has.
     *
     * @param work the work to add to the worklist
     * @throws IllegalStateException iff isFull()
     * @precondition isFull() is false
     */
    @Override
    public void add(E work) {
        //add to the back of the queue

        //if is full (size >= capacity then throw exception)
        //uses isfull() method from superclass
        if(isFull()) {
            throw new IllegalStateException();
        }
        //need to check if front is empty tho

        array[back] = work;
        size++;
        back = (back+1) % capacity; //sets the back pointer to the mod of the array
    }



    /**
     * Returns the next element of the worklist.
     *
     * @return the next element in this worklist
     * @throws NoSuchElementException if hasWork() is false
     * @precondition hasWork() is true
     * @postcondition return(peek ()) is return(next())
     * @postcondition the structure of this worklist remains unchanged.
     */
    @Override
    public E peek() {
        //if has work is false
        if (!hasWork()) { //checks if this.size = 0
            throw new NoSuchElementException();
        }

        return array[front]; //gets the element next to leave
    }


    /**
     * Returns a view of the ith element of the worklist. Since this worklist is
     * a FIFO worklist, it has a well-defined order.
     *
     * @param i the index of the element to peek at
     * @return the ith element in this worklist
     * @throws NoSuchElementException    if hasWork() is false (this exception takes precedence over
     *                                   all others)
     * @throws IndexOutOfBoundsException if i < 0 or i >= size()
     * @precondition 0 <= i < size()
     * @postcondition the structure of this worklist remains unchanged
     */

    @Override
    public E peek(int i) {
        //if has work is false
        if (this.size<= 0) { //checks if this.size = 0
            throw new NoSuchElementException();
        }

        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException();
        }

        //we want the ith element from the front of the work order
        //since circular we could have first=9 and back =3

        return array[(front+i) % capacity]; //gets the remainder of the start of the queue / size of array
        //should work if we wrap around to front
    }


    /**
     * Returns and removes the next element of the worklist
     *
     * @return the next element in this worklist
     * @throws NoSuchElementException if hasWork() is false
     * @precondition hasWork() is true
     * @postcondition return(next ()) ++ after(next()) == before(next())
     * @postcondition after(size ()) + 1 == before(size())
     */
    @Override
    public E next() {
        if (this.size<= 0) {
            throw new NoSuchElementException();
        }

        //we want the element in the front of the line
        E result = array[front];
        front = (front+1) % capacity; //mod capacity since it may be circular so we want to set the front accurately
        size--;//decrement the size of the worklist

        return result;


    }


    /**
     * Replaces the ith element of this worklist with value. Since this worklist
     * is a FIFO worklist it has a well-defined order.
     *
     * @param i     the index of the element to update
     * @param value the value to update index i with
     * @throws NoSuchElementException    if hasWork() is false (this exception takes precedence over
     *                                   all others)
     * @throws IndexOutOfBoundsException if i < 0 or i >= size()
     * @precondition 0 <= i < size()
     * @postcondition only the ith element of the structure is changed
     */
    @Override
    public void update(int i, E value) {
        if (!hasWork()) { //checks if this.size = 0
            throw new NoSuchElementException();
        }

        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException();
        }

        array[(front+i) % capacity] = value;
    }

    /**
     * Returns the number of elements of work remaining in this worklist
     *
     * @return the size of this worklist
     */
    @Override
    public int size() {
        return size;
    }


    /**
     * Resets this worklist to the same state it was in right after
     * construction.
     */
    @Override
    public void clear() {
        E[] temp = (E[])new Comparable[capacity]; //create new empty list
        array = temp; //set our main list to the empty one
        size = 0; //update working size to zero since we no longer have any valid elements
        front = 0;
        back = 0;
    }










    @Override
    public int compareTo(FixedSizeFIFOWorkList<E> other) {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        throw new NotYetImplementedException();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        // You will finish implementing this method in project 2. Leave this method unchanged for project 1.
        if (this == obj) {
            return true;
        } else if (!(obj instanceof FixedSizeFIFOWorkList<?>)) {
            return false;
        } else {
            // Uncomment the line below for p2 when you implement equals
            // FixedSizeFIFOWorkList<E> other = (FixedSizeFIFOWorkList<E>) obj;

            // Your code goes here

            throw new NotYetImplementedException();
        }
    }

    @Override
    public int hashCode() {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        throw new NotYetImplementedException();
    }
}
