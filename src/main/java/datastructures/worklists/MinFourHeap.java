package datastructures.worklists;

import cse332.interfaces.worklists.PriorityWorkList;

import java.util.Comparator;
import java.util.NoSuchElementException;


/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 * 
 * 
 * fourheap = each node has up to 4 children
 */
public class MinFourHeap<E> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data; //array used to store the data of the heap
    private int size; //stores the amount of stuff in the array
    private int maxsize;

     /**
     * The comparator, or null if priority queue uses elements'
     * natural ordering.
     */
    private Comparator<E> comparator;


    //the constructor with the parameter of a comparator with tells us how to compare the data given
    public MinFourHeap(Comparator<E> c) { 
        data = (E[]) new Object[10]; //initialize array to size 10
        maxsize = 10;
        size = 0; 
        this.comparator = c; //set the comparator object to the one the user inputs 
    }

    @Override
    public boolean hasWork() {
        if (size > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void add(E work) {
         
        //if the array cant fit the new element, double the size of the array
        if (size == maxsize) {
            E[] temp = (E[]) new Object[maxsize *2];
            //copy the old array into the new array
            for (int i = 0; i < maxsize; i++) {
                temp[i] = data[i];
            }
            data = temp;
            maxsize = maxsize * 2;
        }
        data[size] = work; //add the new element to the end of the array
        
        percolateUp(size); //perculate up the new element at index size (if two in array, 0,1 and size = 2)
        size++; //increment size 
    }

    @Override
    public E peek() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return data[0];
    }

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

    @Override
    public int size() {
        return size;
    }

    //going to try to just not reset the size to default size, but to keep at max size
    @Override
    public void clear() {
        data = (E[]) new Object[maxsize]; //initialize array to size 10
        //maxsize = 10;
        size = 0;
    }


    //log4(n) since we are doing one comparison for each level of the tree
    //percolate up the element at the given index
    //index is the index of the element that needs to be perculated up
    public void percolateUp(int childIndex) {
        if (childIndex <= 0) {
            return; // Exit the method if the index is out of bounds
        }
        //use -1 since we start the heap at index 0
        //divided by 4 since we are branching by 4 each time
        int parent = ((childIndex - 1)/4); //find the parent of the child java rounds down so no need to use Math.floor
        E temp = data[childIndex]; //store the value at the child

        //while the childIndex is not the root and the value of the childIndex is less than the value of the parent
        while (parent >= 0 && childIndex > 0 && (comparator.compare(temp, data[parent]) < 0)) { 
            data[childIndex] = data[parent]; //set the element at childIndex to the value of the parent
            data[parent] = temp; //set the parent to the value of the child
            childIndex = parent; //set the childIndex to the index of the parent
            parent = ((childIndex - 1)/4) ; //find the new parent of the childIndex
        }
        
    }

    //4 comparisons per level * o(log4(n)) levels = o(log(n))
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
                if (child <= size && (comparator.compare(data[child], data[toSwap]) < 0) )  {
                    toSwap = child; //update the swap index if a smaller child is found
                }
                child++; //move to the next child
            }

            //if the smallest child is smaller than the parent, swap the parent and the smallest child
            if (comparator.compare(data[toSwap], val) < 0 ) { 
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

}
