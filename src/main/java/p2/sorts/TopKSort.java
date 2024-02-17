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
     * finds the k largest items in O(nlogn)
     * so if k=3 returns the 3 largest items from the array in any order
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
        WorkList<E> maxHeap = new MinFourHeap<>(comparator.reversed()); //reverse the comparator so that if x<y if will thing y<x
        //this should give it a max heap

        for (E element: array) {

        }
    }

    public void add(E element) {
        
    }
}
