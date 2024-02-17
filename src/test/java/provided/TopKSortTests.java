package provided;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import p2.sorts.TopKSort;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TopKSortTests {

	@Test()
    @Timeout(value = 3000, unit = TimeUnit.MILLISECONDS)
	public void test_sort_integerSorted_correctSort() {
		int K = 4;
		Integer[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		Integer[] arr_sorted = {7, 8, 9, 10};
		TopKSort.sort(arr, K, Integer::compareTo);
		for(int i = 0; i < K; i++) {
			assertEquals(arr_sorted[i], arr[i]);
		}
	}

	@Test()
    @Timeout(value = 3000, unit = TimeUnit.MILLISECONDS)
	public void test_sort_integerRandom_correctSort() {
		int K = 4;
		Integer[] arr = {3, 1, 4, 5, 9, 2, 6, 7, 8};
		Integer[] arr_sorted = {6, 7, 8, 9};
		TopKSort.sort(arr, K, Integer::compareTo);
		for(int i = 0; i < K; i++) {
			assertEquals(arr_sorted[i], arr[i]);
		}
	}

	@Test()
    @Timeout(value = 3000, unit = TimeUnit.MILLISECONDS)
	public void kgreaterarray() {
		int K = 4;
		Integer[] arr = {3, 1};
		Integer[] arr_sorted = {1,3};
		TopKSort.sort(arr, K, Integer::compareTo);
		for(int i = 0; i < 2; i++) {
			assertEquals(arr_sorted[i], arr[i]);
		}
	}

	@Test()
    @Timeout(value = 3000, unit = TimeUnit.MILLISECONDS)
	public void arrayzero() {
		int K = 2;
		Integer[] arr = {};
		Integer[] arr_sorted = {};
		TopKSort.sort(arr, K, Integer::compareTo);

		assertEquals(arr_sorted.length, arr.length);
		
	}

	//test for when k is greater than the array size
	//run 100 times
	//each with random ints and random array size
	@Test()
	@Timeout(value = 3000, unit = TimeUnit.MILLISECONDS)
	public void fuzzy() {
		for (int j = 0; j < 100; j++) {
			
		Random rand = new Random();

		int K = rand.nextInt(10)+1; //random K

		int arraySize = rand.nextInt(20) +10; //lower bound of 10, k is always less than array size
		Integer[] arr = new Integer[arraySize]; //create an array of random size

		//fill the array with random integers
		for (int i = 0; i < arraySize; i++) {
			arr[i] = rand.nextInt(100);
		}
		Integer[] arr_sorted = arr.clone(); //clone the array
		Arrays.sort(arr_sorted); // sort the array in descending order

		Integer[] old = arr_sorted.clone();

		// Cut the array to size K
		arr_sorted = Arrays.copyOfRange(arr_sorted, arr_sorted.length-K, arr_sorted.length);

		//System.out.println(Arrays.toString(arr));

		TopKSort.sort(arr, K, Integer::compareTo);

		for (int i = 0; i < K; i++) {
			assertEquals(arr_sorted[i], arr[i]);
		}

		}
	}


	//test for when k is greater than the array size
	//run 100 times
	//each with random ints and random array size
	@Test()
	@Timeout(value = 3000, unit = TimeUnit.MILLISECONDS)
	public void fuzzyKgreater() {
		for (int j = 0; j < 100; j++) {
			
		Random rand = new Random();

		int K = rand.nextInt(10)+100; //random K

		int arraySize = rand.nextInt(20) +10; //lower bound of 10, k is always less than array size
		Integer[] arr = new Integer[arraySize]; //create an array of random size

		//fill the array with random integers
		for (int i = 0; i < arraySize; i++) {
			arr[i] = rand.nextInt(100);
		}
		Integer[] arr_sorted = arr.clone(); //clone the array
		Arrays.sort(arr_sorted); // sort the array in descending order

		Integer[] old = arr_sorted.clone();

		

		// Cut the array to size K
		arr_sorted = Arrays.copyOfRange(arr_sorted, 0, arr_sorted.length);

		//System.out.println(Arrays.toString(arr));

		TopKSort.sort(arr, K, Integer::compareTo);

		for (int i = 0; i < arraySize; i++) {
			assertEquals(arr_sorted[i], arr[i]);
		}

		}
	}
}
