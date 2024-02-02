package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.PriorityWorkList;

import java.util.NoSuchElementException;
import java.lang.Math;

/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeapComparable<E extends Comparable<E>> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data; //array used to store the data of the heap
    private int size; //stores the amount of stuff in the array
    private int maxsize;
    //no pointers needed since it is all stored within an array


    /*
     * Binary min-heap:
     * -every node is less than or equal to its children
     * -the tree is complete such that, everylevel is filled and the last level is filled from left to right
     * 
     */

    //initialize the heap, static size of 10?
    public MinFourHeapComparable() {
        data = (E[]) new Comparable[10]; //initialize array to size 10
        maxsize = 10;
        size = 0;
    }

    /**
     * Returns true iff this worklist has any remaining work
     *
     * @return true iff there is at least one piece of work in the worklist.
     */
    @Override
    public boolean hasWork() {
        if (size > 0) {
            return true;
        }
        else {
            return false;
        }
    }


    /**
     * Adds work to the worklist. This method should conform to any additional
     * contracts that the particular type of worklist has.
     *
     * @param work
     *            the work to add to the worklist
     */

    //add to the end, perculate (follow insert procedure)
    @Override
    public void add(E work) {
        //if the array cant fit the new element, double the size of the array
        if (size == maxsize) {
            E[] temp = (E[]) new Comparable[maxsize * 2];
            //copy the old array into the new array
            for (int i = 0; i < maxsize; i++) {
                temp[i] = data[i];
            }
            data = temp;
            maxsize = maxsize * 2;
        }
        data[size] = work; //add the new element to the end of the array
        size++; //increment size 
        //percolateUp(size - 1);
    }

    //percolate up the element at the given index
    public void percolateUp(int index) {
        
        //use -1 since we start the heap at index 0
        int parent = ((index + 1)/2) - 1; //find the parent of the index, java rounds down so no need to use Math.floor
        E temp = data[index]; //store the value at the index

        //while the index is not the root and the value of the index is less than the value of the parent
        while (index > 0 && temp.compareTo(data[parent]) < 0) { 
            data[index] = data[parent]; //move the current parent down
            data[parent] = temp; //set the parent to the value of the thing perculating up
            index = parent; //set the index to the index of the parent
            parent = ((index + 1)/2) - 1; //find the new parent
        }
        
    }


    /**
     * Returns a view to the next element of the worklist.
     *
     * @precondition hasWork() is true
     * @postcondition return(peek()) is return(next())
     * @postcondition the structure of this worklist remains unchanged.
     * @throws NoSuchElementException
     *             if hasWork() is false
     * @return the next element in this worklist
     */

    //Peek would return but not remove the smallest element/root.
    @Override
    public E peek() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return data[0];
    }


    /**
     * Returns and removes the next element of the worklist
     *
     * @precondition hasWork() is true
     * @postcondition return(next()) + after(next()) == before(next())
     * @postcondition after(size()) + 1 == before(size())
     * @throws NoSuchElementException
     *             if hasWork() is false
     * @return the next element in this worklist
     */

    //Next returns and removes the smallest element so this is where you’d need to do a percolate operation. The next element refers to the root.
    //Duplicates are allowed. You don’t really need to do anything special to handle duplicate values. You can just treat them as any other element
    //similar to getmin() function
    @Override
    public E next() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        E theMin = data[0]; //store the root/min value 
        

        return
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

        data = (E[]) new Comparable[10]; //initialize array to size 10
        maxsize = 10;
        size = 0;
    }
}
