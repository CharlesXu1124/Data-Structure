import java.util.Collection;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Zheyuan Xu
 * @userid zxu322
 * @GTID 903132413
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it appears in the Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular
     * for loop will not work here. What other type of loop would work?
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }
        for (T d: data) {
            add(d);
        }
    }

    /**
     * Add the data to the AVL. Start by adding it as a leaf and rotate the tree
     * as needed. Should traverse the tree to find the appropriate location.
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors going up the tree,
     * rebalancing if necessary.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Error,data is null");
        }
        root = addHelp(root, data);
    }
    
    /**
     * helper method of add, performs recursively
     * @param t the AVLNode parsed
     * @param data the data to add
     * @return the AVLNode
     */
    private AVLNode<T> addHelp(AVLNode<T> t, T data) {
        if (t == null) {
            size++;
            t = new AVLNode<T>(data);
        }
        if (data.compareTo(t.getData()) < 0) {
            t.setLeft(addHelp(t.getLeft(), data));
            t.setBalanceFactor(getHeight(t.getLeft())
                - getHeight(t.getRight()));
            t = balance(t);
        } else if (data.compareTo(t.getData()) > 0) {
            t.setRight(addHelp(t.getRight(), data));
            t.setBalanceFactor(getHeight(t.getLeft())
                - getHeight(t.getRight()));
            t = balance(t);
        }
        t.setHeight(Math.max(getHeight(t.getRight()),
            getHeight(t.getLeft())) + 1);
        t.setBalanceFactor(getHeight(t.getLeft())
            - getHeight(t.getRight()));
        return t;
    }

    /**
     * helps balance the AVL tree after each add/remove operation
     * @param node the AVLNode to be referenced at
     * @return the AVLNode
     */
    private AVLNode<T> balance(AVLNode<T> node) {
        if (node.getBalanceFactor() > 1) {
            if (node.getLeft() != null
                && node.getLeft().getBalanceFactor() >= 0) {
                node = rightR(node);
            } else {
                node = leftrightR(node);
            }
        } else if (node.getBalanceFactor() < -1) {
            if (node.getRight() != null
                && node.getRight().getBalanceFactor() <= 0) {
                node = leftR(node);
            } else {
                node = rightleftR(node);
            }
        }
        return node;
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the predecessor to replace the data,
     * not the successor.
     * You must use recursion to find and remove the predecessor (you will
     * likely need an additional helper method to handle this case efficiently).
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null!");
        }
        AVLNode<T> n = new AVLNode<T>(null);
        root = removeHelp(root, data, n);
        return n.getData();
    }

    /**
     * helper method of remove, removes recursively
     * @param t the AVLNode parsed
     * @param data the data to remove
     * @param n the AVLNode as intermediate
     * @return the removed node
     */
    private AVLNode<T> removeHelp(AVLNode<T> t, T data, AVLNode<T> n) {
        AVLNode<T> none = new AVLNode<T>(null);
        if (t == null) {
            throw new java.util.NoSuchElementException("data is not found");
        } else if (data.compareTo(t.getData()) < 0) {
            t.setLeft(removeHelp(t.getLeft(), data, n));
            t.setHeight(Math.max(getHeight(t.getRight()),
                getHeight(t.getLeft())) + 1);
            t.setBalanceFactor(getHeight(t.getLeft()) 
                - getHeight(t.getRight()));
            t = balance(t);

        } else if (data.compareTo(t.getData()) > 0) {
            t.setRight(removeHelp(t.getRight(), data, n));
            t.setHeight(Math.max(getHeight(t.getRight()),
                getHeight(t.getLeft())) + 1);
            t.setBalanceFactor(getHeight(t.getLeft())
                - getHeight(t.getRight()));
            t = balance(t);
        } else {
            if (t.getLeft() == null) {
                size--;
                n.setData(t.getData());
                t.setBalanceFactor(getHeight(t.getLeft())
                    - getHeight(t.getRight()));
                return t.getRight();
            } else if (t.getRight() == null) {
                size--;
                n.setData(t.getData());
                t.setBalanceFactor(getHeight(t.getLeft())
                    - getHeight(t.getRight()));
                return t.getLeft();
            } else {
                n.setData(t.getData());
                t.setData(largest(t.getLeft()));
                t.setLeft(removeHelp(t.getLeft(), t.getData(), none));
                t.setHeight(Math.max(getHeight(t.getRight()),
                    getHeight(t.getLeft())) + 1);
                t.setBalanceFactor(getHeight(t.getLeft())
                    - getHeight(t.getRight()));
                t = balance(t);
            }
        }
        t.setBalanceFactor(getHeight(t.getLeft())
            - getHeight(t.getRight()));
        return t;
    }
    
    /**
     * helper method, finds the largest node
     * @param node the node to be parsed
     * @return the largest node
     */
    private T largest(AVLNode<T> node) {
        while (node.getRight() != null) {
            node = node.getRight();
        }
        return node.getData();
    }
    
    /**
     * performs right rotation
     * @param node the node parsed
     * @return the node after rotation
     */
    private AVLNode<T> rightR(AVLNode<T> node) {
        AVLNode<T> t = node.getLeft();
        node.setLeft(t.getRight());
        t.setRight(node);
        node.setHeight(Math.max(getHeight(node.getRight()),
            getHeight(node.getLeft())) + 1);
        t.setHeight(Math.max(getHeight(t.getRight()),
            getHeight(t.getLeft())) + 1);
        node.setBalanceFactor(getHeight(node.getLeft())
            - getHeight(node.getRight()));
        t.setBalanceFactor(getHeight(t.getLeft()) - getHeight(t.getRight()));
        return t;

    }
    
    /**
     * performs left rotation
     * @param node the node parsed
     * @return the node after rotation
     */
    private AVLNode<T> leftR(AVLNode<T> node) {
        AVLNode<T> t = node.getRight();
        node.setRight(t.getLeft());
        t.setLeft(node);
        node.setHeight(Math.max(getHeight(node.getRight()),
            getHeight(node.getLeft())) + 1);
        t.setHeight(Math.max(getHeight(t.getRight()),
            getHeight(t.getLeft())) + 1);
        node.setBalanceFactor(getHeight(node.getLeft())
            - getHeight(node.getRight()));
        t.setBalanceFactor(getHeight(t.getLeft())
            - getHeight(t.getRight()));

        return t;
    }
    
    /**
     * performs right-left rotation
     * @param node the node to be parsed
     * @return the node after rotation
     */
    private AVLNode<T> rightleftR(AVLNode<T> node) {
        node.setRight(rightR(node.getRight()));
        return leftR(node);
    }
    
    /**
     * performs left-right rotation
     * @param node the node parsed
     * @return the node after rotation
     */
    private AVLNode<T> leftrightR(AVLNode<T> node) {
        node.setLeft(leftR(node.getLeft()));
        return rightR(node);
    }
    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
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
            throw new IllegalArgumentException("Error,data is null");
        }
        return getHelp(root, data);
    }

    /**
     * helper method of get, get recursively
     * @param t the node parsed
     * @param data the data to get
     * @return the data to be returned
     */
    private T getHelp(AVLNode<T> t, T data) {
        if (t == null) {
            throw new java.util.NoSuchElementException("data is not found!");
        }
        if (data.compareTo(t.getData()) == 0) {
            return t.getData();
        } else if (data.compareTo(t.getData()) < 0) {
            return getHelp(t.getLeft(), data);
        } else {
            return getHelp(t.getRight(), data);
        }

    }
    
    /**
     * hepler method of height, gets height recursively
     * @param t the node to be parsed
     * @return the height
     */
    private int getHeight(AVLNode<T> t) {
        if (t == null) {
            return -1;
        } else {
            return t.getHeight();
        }
    }
    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Error,data is null");
        }
        return containsHelp(root, data);
    }
    
    /**
     * helper method of contains, runs recursively
     * @param t the data to be parsed
     * @param data the data to be contained
     * @return whether contains or not
     */
    private boolean containsHelp(AVLNode<T> t, T data) {
        if (t == null) {
            return false;
        }
        if (data.compareTo(t.getData()) == 0) {
            return true;
        } else if (data.compareTo(t.getData()) < 0) {
            return containsHelp(t.getLeft(), data);
        } else {
            return containsHelp(t.getRight(), data);
        }


    }

    /**
     * Returns the data in the deepest node. If there are more than one node
     * with the same deepest depth, return the right most node with the
     * deepest depth.
     *
     * Must run in O(log n) for all cases
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      3
     *        \
     *         1
     * Max Deepest Node:
     * 1 because it is the deepest node
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      4
     *        \    /
     *         1  3
     * Max Deepest Node:
     * 3 because it is the maximum deepest node (1 has the same depth but 3 > 1)
     *
     * @return the data in the maximum deepest node or null if the tree is empty
     */
    public T maxDeepestNode() {
        AVLNode<T> res = find(root);
        return res.getData();
    }
    
    /**
     * helper method of maxDeepestNode, runs recursively
     * @param root the node to be parsed
     * @return the maxDeepestNode
     */
    private AVLNode<T> find(AVLNode<T> root) {
    	if (root.getLeft() == null && root.getRight() == null) {
            return root;
    	} else if (root.getLeft() == null) {
            return root.getRight();
    	} else if (root.getRight() == null) {
            return root.getLeft();
    	}
        if (root.getLeft().getHeight() > root.getRight().getHeight()) {
            return find(root.getLeft());
        } else if (root.getLeft().getHeight() < root.getRight().getHeight()) {
            return find(root.getRight());
        } else {
            if ((find(root.getRight()).getData()).
                compareTo((find(root.getLeft())).getData()) > 0) {
                return find(root.getRight()); 
            } else {
                return find(root.getLeft());
            }
        }
    } 

    /**
     * Returns the data of the deepest common ancestor between two nodes with
     * the given data. The deepest common ancestor is the lowest node (i.e.
     * deepest) node that has both data1 and data2 as descendants.
     * If the data are the same, the deepest common ancestor is simply the node
     * that contains the data. You may not assume data1 < data2.
     * (think carefully: should you use value equality or reference equality?).
     *
     * Must run in O(log n) for all cases
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      3
     *        \
     *         1
     * deepestCommonAncestor(3, 1): 2
     *
     * Example
     * Tree:
     *           3
     *        /    \
     *       1      4
     *      / \
     *     0   2
     * deepestCommonAncestor(0, 2): 1
     *
     * @param data1 the first data
     * @param data2 the second data
     * @throws java.lang.IllegalArgumentException if one or more of the data
     *          are null
     * @throws java.util.NoSuchElementException if one or more of the data are
     *          not in the tree
     * @return the data of the deepest common ancestor
     */
    public T deepestCommonAncestor(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("at least one data is null!");
        }
        return lcaHelp(root, data1, data2).getData();
    }

    /**
     * helper method of deepestCommonAncestor, runs recursively
     * @param root the node to be parsed
     * @param data1 data1 parsed
     * @param data2 data2 parsed
     * @return the lowest common ancestor
     */
    private AVLNode<T> lcaHelp(AVLNode<T> root, T data1, T data2) {
    	if (data1 == null || data2 == null) {
            throw new
            java.util.NoSuchElementException(
            		"one or more data are not in the tree");
    	}
        if (root == data1 || root == data2) {
            return root;
        }
        if ((root.getData().compareTo(data1)) > 0
        		&& (root.getData().compareTo(data2)) > 0) {
            return lcaHelp(root.getLeft(), data1, data2);
        } else if ((root.getData().compareTo(data1)) < 0
        		&& (root.getData().compareTo(data2)) < 0) {
            return lcaHelp(root.getRight(), data1, data2);
        } else {
            return root;
        }
    }

    /**
     * Clear the tree.
     */
    public void clear() {
    	root = null;
        size = 0;
    }

    /**
     * Return the height of the root of the tree.
     *
     * Since this is an AVL, this method does not need to traverse the tree
     * and should be O(1)
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
    	return heightHelp(root);
    }

    /**
     * helper method of height, gets height recursively
     * @param t the node parsed
     * @return the height
     */
    private int heightHelp(AVLNode<T> t) {
        if (t == null) {
            return -1;
        } else {
            return 1 + Math.max(heightHelp(t.getLeft()),
                                heightHelp(t.getRight()));
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
     * DO NOT USE IT IN YOUR CODE
     * DO NOT CHANGE THIS METHOD
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}
