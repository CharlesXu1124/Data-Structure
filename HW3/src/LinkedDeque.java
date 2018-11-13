import java.util.NoSuchElementException;

/**
 * Your implementation of a linked deque.
 *
 * @author Zheyuan Xu
 * @userid zxu322
 * @GTID 903132413
 * @version 1.0
 */
public class LinkedDeque<T> {
    // Do not add new instance variables and do not add a new constructor.
    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    /**
     * Adds the data to the front of the deque.
     *
     * This method must run in O(1) time.
     *
     * @param data the data to add to the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void addFirst(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null!");
        }
        LinkedNode temp = new LinkedNode(null, data, head);
        if (size == 0) {
            head = temp;
            tail = temp;
        } else {
            head.setPrevious(temp);
            head = temp;
        }
        size++;
    }

    /**
     * Adds the data to the back of the deque.
     *
     * This method must run in O(1) time.
     *
     * @param data the data to add to the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void addLast(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null!");
        }
        LinkedNode temp = new LinkedNode(tail, data, null);
        if (size == 0) {
            head = temp;
            tail = temp;
        } else {
            tail.setNext(temp);
            tail = temp;
        }
        size++;
    }

    /**
     * Removes the data at the front of the deque.
     *
     * This method must run in O(1) time.
     *
     * @return the data formerly at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public T removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("deque is empty!");
        }
        LinkedNode<T> temp = new LinkedNode(head);
        head = head.getNext();
        head.setPrevious(null);
        size--;
        return temp.getData();
    }

    /**
     * Removes the data at the back of the deque.
     *
     * This method must run in O(1) time.
     *
     * @return the data formerly at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    @SuppressWarnings("unchecked")
    public T removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("deque is empty!");
        }
        @SuppressWarnings("rawtypes")
        LinkedNode<T> temp = new LinkedNode(tail);
        tail = tail.getPrevious();
        tail.setNext(null);
        size--;
        return temp.getData();
    }

    /**
     * Returns the number of elements in the deque.
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
     * Returns the head node of the linked deque.
     * Normally, you would not do this, but it's necessary for testing purposes.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return node at the head of the linked deque
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the linked deque.
     * Normally, you would not do this, but it's necessary for testing purposes.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return node at the tail of the linked deque
     */
    public LinkedNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }
}