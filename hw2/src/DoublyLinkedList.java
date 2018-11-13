/**
 * Your implementation of a non-circular doubly linked list with a tail pointer.
 *
 * @author Zheyuan Xu
 * @userid zxu322
 * @GTID 903132413
 * @version 1.0
 */
public class DoublyLinkedList<T> {
    // Do not add new instance variables or modify existing ones.
    private LinkedListNode<T> head;
    private LinkedListNode<T> tail;
    private int size;

    /**
     * Adds the element to the index specified.
     *
     * Adding to indices 0 and {@code size} should be O(1), all other cases are
     * O(n).
     *
     * @param index the requested index for the new element
     * @param data the data for the new element
     * @throws java.lang.IndexOutOfBoundsException if index is negative or
     * index > size
     * @throws java.lang.IllegalArgumentException if data is null
     */
    @SuppressWarnings("rawtypes")
    public void addAtIndex(int index, T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null!");
        }
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("index is out of bounds!");
        }
        @SuppressWarnings("unchecked")
        LinkedListNode<T> temp = new LinkedListNode(data);
        if (index == 0) {
            if (head == null) {
                head = temp;
                tail = temp;
            } else {
                temp.setNext(head);
                head.setPrevious(temp);
                temp.setPrevious(null);
                head = temp;
            }
        } else if (index == size) {
            if (tail == null) {
                head = temp;
                tail = temp;
            } else {
                temp.setNext(null);
                temp.setPrevious(tail);
                tail.setNext(temp);
                tail = temp;
            }
        } else {
            LinkedListNode<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
            temp.setNext(current);
            temp.setPrevious(current.getPrevious());
            (current.getPrevious()).setNext(temp);
            current.setPrevious(temp);
        }
        size++;
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1) for all cases.
     *
     * @param data the data for the new element
     * @throws java.lang.IllegalArgumentException if data is null.
     */
    @SuppressWarnings("rawtypes")
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null!");
        }
        @SuppressWarnings("unchecked")
        LinkedListNode<T> temp = new LinkedListNode(data);
        if (head == null) {
            head = temp;
            tail = temp;
        } else {
            temp.setNext(head);
            temp.setPrevious(null);
            head.setPrevious(temp);
            head = temp;
        }
        size++;
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1) for all cases.
     *
     * @param data the data for the new element
     * @throws java.lang.IllegalArgumentException if data is null.
     */
    @SuppressWarnings("rawtypes")
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null!");
        }
        @SuppressWarnings("unchecked")
        LinkedListNode<T> temp = new LinkedListNode(data);
        if (tail == null) {
            head = temp;
            tail = temp;
        } else {
            temp.setNext(null);
            tail.setNext(temp);
            temp.setPrevious(tail);
            tail = temp;
        }
        size++;
    }

    /**
     * Removes and returns the element from the index specified.
     *
     * Removing from index 0 and {@code size - 1} should be O(1), all other
     * cases are O(n).
     *
     * @param index the requested index to be removed
     * @return the data formerly located at index
     * @throws java.lang.IndexOutOfBoundsException if index is negative or
     * index >= size
     */
    public T removeAtIndex(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("index is out of bounds!");
        }
        if (index == 0) {
            LinkedListNode<T> curr = head;
            if (size == 1) {
                head = null;
                tail = null;
            } else {
                head.getNext().setPrevious(null);
                head = head.getNext();
            }
            size--;
            return curr.getData();
        } else if (index == size - 1) {
            LinkedListNode<T> curr = tail;
            if (size == 1) {
                head = null;
                tail = null;
            } else {
                tail.getPrevious().setNext(null);
                tail = tail.getPrevious();
            }
            size--;
            return curr.getData();
        } else {
            LinkedListNode<T> curr = head;
            for (int i = 0; i < index; i++) {
                curr = curr.getNext();
            }
            curr.getNext().setPrevious(curr.getPrevious());
            curr.getPrevious().setNext(curr.getNext());
            size--;
            return curr.getData();
        }
    }

    /**
     * Removes and returns the element at the front of the list. If the list is
     * empty, return {@code null}.
     *
     * Must be O(1) for all cases.
     *
     * @return the data formerly located at the front, null if empty list
     */
    public T removeFromFront() {
        if (size == 0) {
            return null;
        }
        LinkedListNode<T> curr = head;
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            head.getNext().setPrevious(null);
            head = head.getNext();
        }
        size--;
        return curr.getData();
    }

    /**
     * Removes and returns the element at the back of the list. If the list is
     * empty, return {@code null}.
     *
     * Must be O(1) for all cases.
     *
     * @return the data formerly located at the back, null if empty list
     */
    public T removeFromBack() {
        if (isEmpty()) {
            return null;
        }
        LinkedListNode<T> curr = tail;
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            tail.getPrevious().setNext(null);
            tail = tail.getPrevious();
        }
        size--;
        return curr.getData();
    }

    /**
     * Returns the index of the last occurrence of the passed in data in the
     * list or -1 if it is not in the list.
     *
     * If data is in the tail, should be O(1). In all other cases, O(n).
     *
     * @param data the data to search for
     * @throws java.lang.IllegalArgumentException if data is null
     * @return the index of the last occurrence or -1 if not in the list
     */
    public int lastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null!");
        }
        int index = 0;
        int count = 0;
        if (isEmpty()) {
            return -1;
        }
        LinkedListNode<T> curr = head;
        for (int i = 0; i < size; i++) {
            if (curr.getData().equals(data)) {
                count = index;
            }
            index++;
            curr = curr.getNext();
        }
        if (count == 0) {
            return -1;
        } else {
            return count;
        }
    }

    /**
     * Returns the element at the specified index.
     *
     * Getting the head and tail should be O(1), all other cases are O(n).
     *
     * @param index the index of the requested element
     * @return the object stored at index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or
     * index >= size
     */
    public T get(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("index is out of bounds!");
        }
        LinkedListNode<T> curr = head;
        for (int i = 0; i < index; i++) {
            curr = curr.getNext();
        }
        return curr.getData();
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length {@code size} holding all of the objects in
     * this list in the same order from head to tail
     */
    public Object[] toArray() {
        LinkedListNode<T> curr = head;
        Object[] arr = (Object[]) new Object[size];
        for (int i = 0; i < size; i++) {
            arr[i] = curr.getData();
            curr = curr.getNext();
        }
        return arr;
    }

    /**
     * Returns a boolean value indicating if the list is empty.
     *
     * Must be O(1) for all cases.
     *
     * @return true if empty; false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list of all data and resets the size.
     *
     * Must be O(1) for all cases.
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Returns the number of elements in the list.
     *
     * Runs in O(1) for all cases.
     * 
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }

    /**
     * Returns the head node of the linked list.
     * Normally, you would not do this, but it's necessary for testing purposes.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return node at the head of the linked list
     */
    public LinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the linked list.
     * Normally, you would not do this, but it's necessary for testing purposes.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return node at the tail of the linked list
     */
    public LinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }
}