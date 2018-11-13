import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Your implementation of a binary search tree.
 *
 * @author Zheyuan Xu
 * @userid zxu322
 * @GTID 903132413
 * @version 1.0
 */

public class BST<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private BSTNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the BST with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular
     * for loop will not work here. What other type of loop would work?
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null!");
        }
        for (T d: data) {
            add(d);
        }
    }

    /**
     * Add the data as a leaf in the BST. Should traverse the tree to find the
     * appropriate location. If the data is already in the tree, then nothing
     * should be done (the duplicate shouldn't get added, and size should not be
     * incremented).
     * 
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null!");
        }
        root = add(data, root);
    }
    
    /**
     * helper function of add, takes in data and node
     * to perform recursive call
     * @param data the data to add
     * @param node the current node
     * @return the copy of a generic type BSTNode prior to add
     */
    private BSTNode<T> add(T data, BSTNode<T> node) {
        if (node == null) {
            size++;
            return new BSTNode<T>(data);
        }
        if (data.compareTo(node.getData()) < 0) {
            node.setLeft(add(data, node.getLeft()));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(add(data, node.getRight()));
        }
        return node;
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the successor to replace the data.
     * You must use recursion to find and remove the successor (you will likely
     * need an additional helper method to handle this case efficiently).
     *
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null!");
        }
        BSTNode<T> rm = new BSTNode<>(null);
        root = remove(data, root, rm);
        return rm.getData();
    }

    /**
     * helper function of remove, can take in data, node, and rm
     * to perform recursive call
     * @param data     the data to be removed
     * @param node   the current node
     * @param rm   the BSTNode to be removed
     * @return the BSTNode removed
     */
    private BSTNode<T> remove(T data, BSTNode<T> node, BSTNode<T> rm) {
        if (node == null) {
            throw new NoSuchElementException("No such element found");
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(remove(data, node.getRight(), rm));
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(remove(data, node.getLeft(), rm));
        } else {
            rm.setData(node.getData());
            if (node.getLeft() == null) {
                size--;
                return node.getRight();
            } else if (node.getRight() == null) {
                size--;
                return node.getLeft();
            } else {
                size--;
                BSTNode<T> rp = new BSTNode<>(null);
                node.setLeft(getAncestor(node.getLeft(), rp));
                node.setData(rp.getData());
            }
        }
        return node;
    }

    /**
     * helper function of remove, takes in node and the replaced node
     * and finds the previous node
     * @param node the current node
     * @param rp the node that is to be replaced
     * @return the BSTNode at previous location
     */
    private BSTNode<T> getAncestor(BSTNode<T> node, BSTNode<T> rp) {
        if (node.getRight() == null) {
            rp.setData(node.getData());
            return node.getLeft();
        } else {
            node.setRight(getAncestor(node.getRight(), rp));
            return node;
        }
    }
    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null!");
        }
        return get(data, root).getData();
    }
    
    /**
     * helper function of get, takes in data and current node
     * @param data the data to search
     * @param node the current node
     * @return BSTNode of generic type, the desired output
     */
    private BSTNode<T> get(T data, BSTNode<T> node) {
        if (node == null) {
            throw new NoSuchElementException("No such element found!");
        } else if (data.compareTo(node.getData()) > 0) {
            return get(data, node.getRight());
        } else if (data.compareTo(node.getData()) < 0) {
            return get(data, node.getLeft());
        } else {
            return node;
        }
    }

    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        return contains(data, root);
    }

    /**
     * helper function of contains, takes in data and node, to recursively come
     * out of the result
     * @param data the data to search
     * @param node the current node
     * @return whether BST contains the data or not
     */
    private boolean contains(T data, BSTNode<T> node) {
        if (node == null) {
            return false;
        } else if (data.compareTo(node.getData()) > 0) {
            return contains(data, node.getRight());
        } else if (data.compareTo(node.getData()) < 0) {
            return contains(data, node.getLeft());
        } else {
            return true;
        }
    }
    /**
     * Should run in O(n).
     *
     * @return a preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> list = new ArrayList<>();
        preorder(root, list);
        return list;
    }
    
    /**
     * helper function of preorder, takes in node and list, to recursively come
     * out of the result
     * @param node the current node
     * @param list the list that contains the elements traversed
     */
    private void preorder(BSTNode<T> node, List<T> list) {
        if (node != null) {
            list.add(node.getData());
            preorder(node.getLeft(), list);
            preorder(node.getRight(), list);
        }
    }

    /**
     * Should run in O(n).
     * @return an inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> list = new ArrayList<>();
        inorder(root, list);
        return list;
    }
    
    /**
     * helper function of inorder, takes in node and list to perform
     * recursive operation
     * @param node the current node
     * @param list the list that contains the elements traversed
     */
    private void inorder(BSTNode<T> node, List<T> list) {
        if (node != null) {
            inorder(node.getLeft(), list);
            list.add(node.getData());
            inorder(node.getRight(), list);
        }
    }
    
    /**
     * helper function of postorder, takes in node and list to perform
     * recursive operation
     * @param node the current node
     * @param list the list that contains the elements traversed
     */
    private void postorder(BSTNode<T> node, List<T> list) {
        if (node != null) {
            postorder(node.getLeft(), list);
            postorder(node.getRight(), list);
            list.add(node.getData());
        }
    }

    /**
     * Should run in O(n).
     * @return a postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> list = new ArrayList<>();
        postorder(root, list);
        return list;
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * To do this, add the root node to a queue. Then, while the queue isn't
     * empty, remove one node, add its data to the list being returned, and add
     * its left and right child nodes to the queue. If what you just removed is
     * {@code null}, ignore it and continue with the rest of the nodes.
     *
     * Should run in O(n).
     *
     * @return a level order traversal of the tree
     */
    public List<T> levelorder() {
        BSTNode<T> node = new BSTNode<T>(null);
        List<T> list = new ArrayList<>();
        Queue<BSTNode<T>> queue = new LinkedList<>();
        if (size == 0) {
            return list;
        }
        queue.add(root);
        while (!queue.isEmpty()) {
            node = queue.remove();
            list.add(node.getData());
            if (node.getLeft() != null) {
                queue.add(node.getLeft());
            }
            if (node.getRight() != null) {
                queue.add(node.getRight());
            }
        }
        return list;
    }

    /**
     * Finds and retrieves the k-largest elements from the BST in sorted order,
     * least to greatest.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in the efficiency penalty.
     *
     * EXAMPLE: Given the BST below composed of Integers:
     *
     *                50
     *              /    \
     *            25      75
     *           /  \
     *          12   37
     *         /  \    \
     *        10  15    40
     *           /
     *          13
     *
     * kLargest(5) should return the list [25, 37, 40, 50, 75].
     * kLargest(3) should return the list [40, 50, 75].
     *
     * Should have a running time of O(log(n) + k) for a balanced tree and a
     * worst case of O(n + k).
     *
     * @throws java.lang.IllegalArgumentException if k > n, the number of data
     * in the BST
     * @param k the number of largest elements to return
     * @return sorted list consisting of the k largest elements
     */
    public List<T> kLargest(int k) {
        int c = 0;
    	List<T> list = new ArrayList<T>();
    	List<T> list2 = new ArrayList<T>();
    	findLarge(root, k, c, list);
        for (int i = k - 1; i >= 0; i--) {
            list2.add(list.get(i));
        }
        return list2;
    }
    
    /**
     * helper function of kLargest, takes in node, k, c and list to perform
     * recursive operation; it performs inorder traversal from right to left
     * to find out the largest elements.
     * @param node the current node
     * @param k the number of largest elements to return
     * @param c the current count
     * @param list the list that contains the elements traversed
     */
    private void findLarge(BSTNode<T> node, int k, int c, List<T> list) {
        if (node == null) {
            return;
        }
        if (c > k) {
            return; 
        }
        findLarge(node.getRight(), k, c, list); 
        if (c >= k) {
            return;
        }
        list.add(node.getData());
        c++; 
        if (c >= k) {
            return; 
        }
        findLarge(node.getLeft(), k, c, list); 
    } 


    /**
     * Clears the tree.
     *
     * Should run in O(1).
     */
    public void clear() {
        size = 0;
        root = null;
    }

    /**
     * Calculate and return the height of the root of the tree. A node's
     * height is defined as {@code max(left.height, right.height) + 1}. A leaf
     * node has a height of 0 and a null child should be -1.
     *
     * Should be calculated in O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
    	if (size == 0) {
            return -1;
        } else {
            return height(root);
        }
    }
    
    /**
     * helper function of height, takes in node and determines
     * the largest height of left/right subtree
     * @param node the current node
     * @return an integer which is the height of the subtree
     * under current node
     */
    private int height(BSTNode<T> node) {
    	if (node.getLeft() == null && node.getRight() == null) {
            return 0;
        } else if (node.getLeft() == null && node.getRight() != null) {
            return height(node.getRight()) + 1;
        } else if (node.getLeft() != null && node.getRight() == null) {
            return height(node.getLeft()) + 1;
        } else {
            int l = height(node.getLeft());
            int r = height(node.getRight());
            if (l > r) {
            	return l + 1;
            } else {
            	return r + 1;
            }
        }
    }

    /**
     * THIS METHOD IS ONLY FOR TESTING PURPOSES.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the number of elements in the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * THIS METHOD IS ONLY FOR TESTING PURPOSES.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}
