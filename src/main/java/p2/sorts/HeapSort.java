package p2.sorts;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.WorkList;
import datastructures.worklists.MinFourHeap;

import java.util.Comparator;



/*
 * the basic strategy is to build a binary heap of N elements.
This stage takes O(N) time. We then perform N deleteMin operations. The elements leave
the heap smallest first, in sorted order. By recording these elements in a second array and
then copying the array back, we sort N elements. Since each deleteMin takes O(logN) time,
the total running time is O(N logN).
 *
 *
 * idea:
 * -add each element to the min heap, constructed with our custom comparator
 * -the min heap auto sorts the array as it is added and remove (top is guarenteed to be the smallest)
 * -then for the length of the heap, remove the top (min) and put it into place of our inputed array
 * -since everytime we remove the heap perculates up the min we will always pop in the correct order
 *
 * 
 * 
 * 
 * 
 */
public class HeapSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        sort(array, (x, y) -> x.compareTo(y));
    }


    //use our min four heap to heapify the given array (since it will sort itself as we add)
    public static <E> void sort(E[] array, Comparator<E> comparator) {


         WorkList<E> minHeap = new MinFourHeap<>(comparator); 
         //builds a minfour heap of type<E>, initializes it with the comparator that tells it how to compare objects

        //build min heap from array
        for (E ele: array) {
            minHeap.add(ele); //add the element, heap will auto sort
        }

        int n = minHeap.size(); //need to get the size before we start popping from min heap since the size of minheap updates in real time

        //remove from heap starting at top and place into original array
        for (int i =0; i < n; i++) {
            E top = minHeap.next(); //returns and removes the top element from the heap
            array[i] = top;

        }

    }
}
