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
     * [0
     * 1,2,3,4
     * 5,6,7,8  9,10,11,12  13,14,15,16  17,18,19,20  ]
     * 
     * 
     * 
     */


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
            E[] temp = (E[]) new Comparable[maxsize +10];
            //copy the old array into the new array
            for (int i = 0; i < maxsize; i++) {
                temp[i] = data[i];
            }
            data = temp;
            maxsize = maxsize * 2;
        }
        data[size] = work; //add the new element to the end of the array
        size++; //increment size 
        percolateUp(size); //perculate up the new element at index size (if two in array, 0,1 and size = 2)
    }

    //percolate up the element at the given index
    //index is the index of the element that needs to be perculated up
    public void percolateUp(int childIndex) {
        if (childIndex < 0) {
            return; // Exit the method if the index is out of bounds
        }
        //use -1 since we start the heap at index 0
        //divided by 4 since we are branching by 4 each time
        int parent = ((childIndex + 1)/4) - 1; //find the parent of the child java rounds down so no need to use Math.floor
        E temp = data[childIndex]; //store the value at the child

        //while the childIndex is not the root and the value of the childIndex is less than the value of the parent
        while (parent >= 0 && childIndex > 0 && temp.compareTo(data[parent]) < 0) { 
            data[childIndex] = data[parent]; //set the element at childIndex to the value of the parent
            data[parent] = temp; //set the parent to the value of the child
            childIndex = parent; //set the childIndex to the index of the parent
            parent = ((childIndex + 1)/4) - 1; //find the new parent of the childIndex
        }
        
    }

    //percolate down the element at the given parentIndex
    public void percolateDown(int parentIndex) {
        int leftChild = 4 * parentIndex + 1; //find the leftmost child of the parentIndex
        E val = data[parentIndex]; //store the value at the parentIndex

        while (leftChild <= size) { //while the leftmost child is still in bounds of the array

            leftChild = 4 * parentIndex + 1; //find the leftmost child of the parentIndex
            //since 4 nodes we know the rightmost child node will be at 4 * parentIndex + 4

            val = data[parentIndex]; //store the value at the parentIndex

            int toSwap = leftChild; //set the swap index to the left child by default (in case no others to swap)
            int child = leftChild + 1; //start with the second child
            
            //get the smallest of the children
            //this gets the min out of the 4 children, my using a running minimum(toswap)
            //compare the value of each child with the value of the current smallest child
            //3 comparisons out of 4 things so run 4-1 times
            for (int i = 0; i < 3; i++) {
                //if child in bounds, and the value of the child is less than the value of the current smallest child
                if (child <= size && data[child].compareTo(data[toSwap]) < 0) {
                    toSwap = child; //update the swap index if a smaller child is found
                }
                child++; //move to the next child
            }

            //if the smallest child is smaller than the parent, swap the parent and the smallest child
            if (data[toSwap].compareTo(val) < 0) { 
                //swap the parent and the smallest child
                data[parentIndex] = data[toSwap]; //set the parent value to the value of the smallest child
                data[toSwap] = val; //set the smallest child value to the value of the parent

                parentIndex = toSwap; //update the parent index to the index of the smallest child
                leftChild = 4 * parentIndex + 1; //update the left child index
                }
                
                //none of the children were smaller than the parent, so the parent is in the correct position
                else {
                break; //if the parent is smaller than all its children, stop percolating down
            }
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
        data[0] = data[size - 1]; //set the root to the last element in the array
        size--; //decrement the size

        percolateDown(0); //perculate down the root to get the tree in the correct order

        //now we want to get the last element in the array and set it to the root to keep the complete tree property
        //then perculate it down to get the tree in the correct order


        return theMin;
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
