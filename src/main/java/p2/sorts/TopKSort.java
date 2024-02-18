package p2.sorts;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.WorkList;
import datastructures.worklists.MinFourHeap;

import java.util.Comparator;

public class TopKSort {
    public static <E extends Comparable<E>> void sort(E[] array, int k) {
        sort(array, k, (x, y) -> x.compareTo(y));
    }

    /* 
     * finds the k largest items in O(nlogk)
     * so if k=3 returns the 3 largest items from the array in any order
     * 
     * O(N) since we need to iterate through the array to heapify all the elements
     * O(logK) since the heap never goes past size k, we only need to iterate logK levels to get k largest items
     * 
     * 
     * solution:
     * heapify the array, keeping the heap at a size of k
     * pop the k largest items and put into the array
     * 
     * so we need to turn our min heap into a max heap
     * we need to put the top k elements in the first k spots in the array and all the other indices should be null
     * 
     * for each element in the array
     * add to the heap
     * set current value to null
     * 
     * then pop k elements from the max heap and add to the first k-1 indicews 
    */
    public static <E> void sort(E[] array, int k, Comparator<E> comparator) {
        WorkList<E> minHeap = new MinFourHeap<>(comparator); 
        //this should give it a max heap

        if (k>array.length) {
            k = array.length;
        }

        //add each element to the minheap of size k, set the current index to null
        for (int i =0; i< array.length; i++) {
            
            add(minHeap, array[i], k);
            array[i] = null; //set the element to null
        }



        //iterate through the min heap, pop the top element (should be the least i+! largest element until i<k)
        //run as long as i is less than k, and as long as minheap has elements (in case k > array.length)
        for(int i=0; i<k ; i++) {
            array[i] = minHeap.next(); //set the ith index to the ith largest element

        }
    }

    //for each element add log(n), and remove log(n) time, do for n items so O(n2logn)
    //add the element to the heap, keeping the size of the heap at size<k
    public static <E> void add(WorkList<E> minheap, E element, int k) {
        minheap.add(element); //add the element to the heap, smallest item guarenteed to be at the top

        //if the size of the heap is greater than how many greatest elements we want, remove the minimum, keep size k
        if (minheap.size() > k) {
            minheap.next(); //return and remove min element
        }
    }
}
