package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.LIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/LIFOWorkList.java
 * for method specifications.
 */
public class ArrayStack<E> extends LIFOWorkList<E> {


    int Default_size = 10;
    E[] array = (E[])new Object[Default_size]; //generic type array of size 10
    int size = 0; //keeps track of elements in the array
    int maxSize = 10; //max size of the array currently

    //constructor method
    public ArrayStack() {

    }




    //add element to stack
    @Override
    public void add(E work) {

        //if the size is equal to the max size
        if (size == maxSize) { //make array bigger and add
            //make array bigger
            //create new area
            //iterate through and copy each item
            maxSize += 10; //add 10 to max size
            E[] temp = (E[])new Object[maxSize]; //new array

            for(int i = 0; i < size; i++) {
                temp[i] = array[i]; //set the element at the index of the new array to the element at the index of old array
            }
            array = temp; //point old array to temp

        }
        //add to the back normally
        array[size] = work; //adds the work to the current size of index
        size++; //add one to the size

    }

    //peek at top of stack
    @Override
    public E peek() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return array[size-1]; //returns element at top of stack (end of array)
    }

    //same as pop() method
    //return and remove from top of stack
    @Override
    public E next() {
        if (size == 0) {
           throw new NoSuchElementException();
        }

        E result = array[size-1]; //store item

        array[size-1] = null; //remove item
        size--; //decrease size

        return result; //return item
    }

    //get the size of the stack
    @Override
    public int size() {
        return size;
    }

    //clear the stack
    @Override
    public void clear() {
        E[] temp = (E[])new Object[maxSize];
        array = temp;
        size = 0;
    }
}
