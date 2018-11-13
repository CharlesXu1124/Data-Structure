import java.util.ArrayList;
/**
 * Your implementation of a min heap.
 *
 * @author Zheyuan Xu
 * @userid zxu322
 * @GTID 903132413
 * @version 1.0
 */
public class MinHeap<T extends Comparable<? super T>> {

    public static final int INITIAL_CAPACITY = 13;

    private T[] backingArray;
    private int size;
    // Do not add any more instance variables. Do not change the declaration
    // of the instance variables above.

    /**
     * Creates a Heap with an initial capacity of {@code INITIAL_CAPACITY}
     * for the backing array.
     *
     * Use the constant field provided. Do not use magic numbers!
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
        backingArray[0] = null;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the Build Heap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     *
     * The data in the backingArray should be in the same order as it appears
     * in the ArrayList before you start the Build Heap Algorithm.
     *
     * The {@code backingArray} should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY from
     * the interface). Index 0 should remain empty, indices 1 to n should
     * contain the data in proper order, and the rest of the indices should
     * be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null!");
        }
        backingArray = (T[]) new Comparable[data.size() * 2 + 1];
        backingArray[0] = null;
        for (T d: data) {
            this.add(d);
        }
        T[] arr = (T[]) new Comparable[data.size()];
        for (int i = 0; i < data.size(); i++) {
            arr[i] = data.get(i);
        }
        int n = arr.length;  
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }
        for (int i = n - 1; i >= 0; i--) {
            T temp = arr[0]; 
            arr[0] = arr[i]; 
            arr[i] = temp;  
            heapify(arr, i, 0); 
        } 
        for (int i = 0; i < arr.length; i++) {
            backingArray[i + 1] = arr[i];
        }
    }

    /**
     * heapify the subtree at root i, n is the size of heap
     * @param data --the array containing the data to be heapified
     * @param n --the size of heap
     * @param i --the index of root in the heap
     */
    void heapify(T[] data, int n, int i) {
        int min = i; 
        int left = 2 * i + 1; 
        int right = 2 * i + 2;

        if (left < n && data[left].compareTo(data[min]) > 0) {
            min = left;
        }
        if (right < n && data[right].compareTo(data[min]) > 0) {
            min = right;
        }
        if (min != i) {
            T t = data[i]; 
            data[i] = data[min]; 
            data[min] = t; 
            heapify(data, n, min); 
        } 
    } 
    /**
     * Adds an item to the heap. If the backing array is full and you're trying
     * to add a new item, then double its capacity.
     *
     * @throws IllegalArgumentException if the item is null
     * @param item the item to be added to the heap
     */
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Item is null!");
        }
        int lower = 0;
        int parent = 0;
        T t;
        if (size >= backingArray.length - 1) {
            T[] temp = (T[]) new Comparable[(int) (2 * backingArray.length)];
            for (int i = 1; i < backingArray.length; i++) {
                temp[i] = backingArray[i];
            }
            backingArray = temp;
        }
        backingArray[++size] = item;
        lower = size;
        parent = (int) lower / 2;
        while (lower > 1 
            && backingArray[parent].compareTo(backingArray[lower]) > 0) {
            t = backingArray[lower];
            backingArray[lower] = backingArray[parent];
            backingArray[parent] = t;
            lower = parent;
            parent = (int) lower / 2;
        }
    }

    /**
     * Removes and returns the min item of the heap. Null out all elements not
     * existing in the heap after this operation. Do not decrease the capacity
     * of the backing array.
     *
     * @throws java.util.NoSuchElementException if the heap is empty
     * @return the removed item
     */
    public T remove() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("Heap is empty!");
        }
        int min;
        T t = backingArray[1];
        T swaptemp;
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;
        int index = 1;
        while (index * 2 <= size) {
            if (backingArray[index * 2 + 1] != null) {
                if (backingArray[index * 2].compareTo(backingArray[index
                    * 2 + 1]) < 0) {
                    min = index * 2;
                } else {
                    min = index * 2 + 1;
                }
            } else {
                min = index * 2;
            }

            if (backingArray[index].compareTo(backingArray[min])
                    > 0) {
                T temp = backingArray[index];
                backingArray[index] = backingArray[min];
                backingArray[min] = temp;
                index = min;
            }
            index = min;
        }
        return t;
    }

    
    
    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element, null if the heap is empty
     */
    public T getMin() {
        if (isEmpty()) {
            return null;
        }
        if (backingArray[1].compareTo(backingArray[2]) < 0) {
            return backingArray[1];
        }
        return backingArray[2];
    }

    /**
     * Returns if the heap is empty or not.
     *
     * @return true if the heap is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap and returns array to {@code INITIAL_CAPACITY}.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * THIS METHOD IS ONLY FOR TESTING PURPOSES.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the number of elements in the heap
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Return the backing array for the heap.
     *
     * For grading purposes only. DO NOT USE THIS METHOD IN YOUR CODE!
     *
     * @return the backing array for the heap
     */
    public Object[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

}