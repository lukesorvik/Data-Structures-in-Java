package p2.sorts;

import cse332.exceptions.NotYetImplementedException;

import java.util.Comparator;

/*
 * Best case : O(nlogn)
 * Worst case: O(n^2) depends on pivot choice
 * -sorts in place (no new array created)
 * -not stable (identicle elements do not keep original order)
 * 
 * 
 *  * 
 * -pick pivot (pick roughly the middle element as the pivot )
 * -everything < pivot : partition to the left of the pivot
 * -everything > pivot : partition to the right of the pivot
 * -pivot will now be in final sorted location
 * -recursively solve for the rest of the partitions
 * 
 * 
 * can pick piviot using:
 * random value (might take too long)
 * median of start index, end index, so only have to compare 3 values each partition instead of finding
 * true median which would take n time
 * 
 * 
 */


public class QuickSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        QuickSort.sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        quickSort(array, 0, array.length-1, comparator);
    }

    //start = start index, end = endindex
    public static <E> void quickSort(E[] array, int startIndex, int endIndex, Comparator<E> comparator ) {
        
        //base case we are given a partion of 1 or less.
        if (startIndex >= endIndex) {
            return;
        }
        
        int pivot = findPivot(array, startIndex, endIndex, comparator); //gets the pivot index based off the median of 3 values
        //pivot should be the median of which we move to the endIndex (so we know where it is)

        int leftpointer = startIndex;
        int rightpointer = endIndex;


        //begin partitioning
        //once our leftpointer runs into our right pointer end the loop
        while (leftpointer < rightpointer) {
            //walk the left pointer from left to right, until we find a number that is higher than the pivot or we hit our right pointer
            //if element at left pointer is less than the pivot
            //check if anything the left pointer points to is greater than pivot, if so keep pointer to swap, else increment keep looking
            while ((comparator.compare(array[leftpointer], array[pivot]) < 0) && (leftpointer < rightpointer)) {
                leftpointer++;
            }
            //walk the right pointer backwards until it finds a value < pivot or it collides with the left pointer 
            //if the element at the rightpointer is greaterthan or equal to the pivot
            //check if anything the right pointer points to is less than the pivot if so keep to swap, else increment keep looking
            while ((comparator.compare(array[rightpointer], array[pivot]) >= 0) && (leftpointer < rightpointer)) {
                rightpointer--;
            }

            //check if still in bounds 
            if (leftpointer < rightpointer) {
            //swap the values since left pointer > pivot, and rightpointer < pivot 
            swap(array, leftpointer, rightpointer);
            }
            
            //out of bounds
            else{
                break; 
            }


        }

        swap(array, leftpointer, endIndex); //restore pivot back to where it should be 
        //(since where left and right pointer collide should be where the pivot goes, and we know we left the pivot at the end )

        quickSort(array, startIndex, leftpointer-1, comparator); //get the partition to the left of the pivot
        quickSort(array, leftpointer+1, endIndex, comparator); //get the partition to the right of the pivot 

    }

    

    //given an array and the index of x, and the index of y, swap x and y
    public static <E> void swap(E[] array, int x, int y) {
        E temp = array[x];
        array[x] = array[y]; //set x = y
        array[y] = temp;  //set y = x 
    }



    /*
    using the find median
    start = start index of partition, and end = end index of partition
    for the center to be the median:
    start< center < end
    So we check 3 things
    if (start > center) swap so start < center
    if (end < start) swap so end > start
    if (end < center) swap so center < end
     */
    public static <E> int findPivot(E[] array, int start, int end, Comparator<E> comparator) {
        int center = (start + end) /2;

        //sort the 3 values put the smallest in a[start], put the largest in a[end]

        //if the center is less than the start, swap
        if (comparator.compare(array[center], array[start]) < 0) {
            swap(array, start, center ); //swap center and start
        }
        //if end is less than start, swap
        if (comparator.compare(array[end], array[start]) < 0) {
            swap(array, start, end );
        }
        //if the center is greater than the end
        if (comparator.compare(array[center], array[end]) > 0) {
            swap(array, center, end );
        }

        //at this point the pivot should be at the center
        swap(array, end, center); //pivot should be at center, put it at the end index!
        return end;

    }
}
