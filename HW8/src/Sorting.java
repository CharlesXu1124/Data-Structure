import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Zheyuan Xu
 * @userid zxu322
 * @GTID 903132413
 * @version 1.0
 */
public class Sorting {

    /**
     * Implement cocktail sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * NOTE: See pdf for last swap optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
    	if (arr == null) {
            throw new IllegalArgumentException("Error: null array");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Error: null comparator");
        }
        boolean swapped = true;
        int start = -1;
        int end = arr.length;
        while (swapped == true && start + 1 < end) {
            swapped = false;
            for (int i = start + 1; i < end - 1; i++) { 
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped)
                break;
            swapped = false; 
            end--;
            start++;
            for (int i = end - 2; i > start + 1; i--) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) {
            	break;
            }
        }
    }

    /**
     * Implement insertion sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
    	if (arr == null) {
            throw new IllegalArgumentException("Error,arr is null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Error,comparator is null");
        }
        T temp;
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0; j--) {
                if (comparator.compare(arr[j], arr[j - 1]) < 0) {
                    temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                } else {
                    j = -1;
                }
            }
        }
    }

    /**
     * Implement selection sort.
     *
     * It should be:
     *  in-place
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n^2)
     *
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * Note that there may be duplicates in the array.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
    	if (arr == null) {
            throw new IllegalArgumentException("Error,arr is null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Error,comparator is null");
        }
        int minindex;
        for (int i = 0; i < arr.length; i++) {
            minindex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (comparator.compare(arr[j], arr[minindex]) < 0) {
                    minindex = j;
                }
            }
            T temp = arr[minindex];
            arr[minindex] = arr[i];
            arr[i] = temp;
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(n log n)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * You can create more arrays to run mergesort, but at the end,
     * everything should be merged back into the original T[]
     * which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
    	if (arr == null) {
            throw new IllegalArgumentException("Error,arr is null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Error,comparator is null");
        }
        if (arr.length > 1) {

            int middleindex = (int) (arr.length / 2);
            int leftsize = (int) (arr.length / 2);
            int rightsize = arr.length - leftsize;
            T[] leftarr = (T[]) new Object[leftsize];
            T[] rightarr = (T[]) new Object[rightsize];
            for (int i = 0; i < leftsize; i++) {
                leftarr[i] = arr[i];
            }
            for (int i = leftsize; i < arr.length; i++) {
                rightarr[i - leftsize] = arr[i];
            }

            mergeSort(leftarr, comparator);

            mergeSort(rightarr, comparator);

            int leftindex = 0;
            int rightindex = 0;
            int currentindex = 0;
            while (leftindex < middleindex
                    && rightindex < arr.length - middleindex) {
                if (comparator.compare(leftarr[leftindex],
                                       rightarr[rightindex]) <= 0) {
                    arr[currentindex] = leftarr[leftindex];
                    leftindex++;
                } else {
                    arr[currentindex] = rightarr[rightindex];
                    rightindex++;
                }
                currentindex++;
            }

            while (leftindex < middleindex) {
                arr[currentindex] = leftarr[leftindex];
                leftindex++;
                currentindex++;
            }
            while (rightindex < arr.length - middleindex) {
                arr[currentindex] = rightarr[rightindex];
                rightindex++;
                currentindex++;
            }
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(kn)
     *
     * And a best case running time of:
     *  O(kn)
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use {@code java.util.ArrayList} or {@code java.util.LinkedList}
     * if you wish, but it may only be used inside radix sort and any radix sort
     * helpers. Do NOT use these classes with other sorts.
     *
     * Do NOT use anything from the Math class except Math.abs
     *
     * @throws IllegalArgumentException if the array is null
     * @param arr the array to be sorted
     */
    public static void lsdRadixSort(int[] arr) {
    	if (arr == null) {
            throw new IllegalArgumentException("Error,arr is null");
        }
        int maxnumber = arr[0];
        int maxlength = 1;
        for (int i = 0; i < arr.length; i++) {
            if (Math.abs(arr[i]) > maxnumber) {
                maxnumber = Math.abs(arr[i]);
            }
        }
        while ((maxnumber) >= 10) {
            maxlength++;
            maxnumber = maxnumber / 10;
        }

        List<Integer>[] buckets = new ArrayList[19];
        for (int i = 0; i < 19; i++) {
            buckets[i] = new ArrayList<Integer>();
        }
        int divnumber = 1;

        for (int i = 0; i < maxlength; i++) {
            for (Integer num: arr) {

                buckets[((num / divnumber) % 10) + 9].add(num);

            }
            int index = 0;
            for (int k = 0; k < buckets.length; k++) {
                for (Integer xx: buckets[k]) {
                    arr[index++] = xx;
                }
                buckets[k].clear();
            }
            divnumber = divnumber * 10;
        }
    }

    /**
     * Implement kth select.
     *
     * Use the provided random object to select your pivots.
     * For example if you need a pivot between a (inclusive)
     * and b (exclusive) where b > a, use the following code:
     *
     * int pivotIndex = r.nextInt(b - a) + a;
     *
     * It should be:
     *  in-place
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * @throws IllegalArgumentException if the array or comparator or rand is
     * null or k is not in the range of 1 to arr.length
     * @param <T> data type to sort
     * @param k the index to retrieve data from + 1 (due to 0-indexing) if
     *          the array was sorted; the 'k' in "kth select"; e.g. if k ==
     *          1, return the smallest element in the array
     * @param arr the array that should be modified after the method
     * is finished executing as needed
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     * @return the kth smallest element
     */
    public static <T> T kthSelect(int k, T[] arr, Comparator<T> comparator,
                                  Random rand) {
    	return null;
    }
}
