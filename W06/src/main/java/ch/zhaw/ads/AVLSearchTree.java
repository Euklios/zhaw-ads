/**
 * Implements an AVL tree.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 * Generic K.Rege
 */
package ch.zhaw.ads;

public class AVLSearchTree<T extends Comparable<T>> extends SortedBinaryTree<T> {

    private boolean balanced(TreeNode<T> node) {
        if (node == null) {
            return true;
        }

        if (balanced(node.right) && balanced(node.left)) {
            int leftSize = calcHeight(node.left);
            int rightSite = calcHeight(node.right);

            return Math.abs(leftSize - rightSite) <= 1;
        }

        return false;
    }

    public boolean balanced() {
        return balanced(root);
    }

    @Override
    protected int calcSize(TreeNode<T> p) {
        if (p == null) {
            return 0;
        }

        return calcSize(p.left) + calcSize(p.right) + p.values.size();
    }

    /**
     * Return the height of node t, or 0, if null.
     */
    private static int height(TreeNode<?> t) {
        return t == null ? 0 : t.height;
    }

    /**
     * Insert into the tree; duplicates are ignored.
     * @param element the item to insert.
     */
    public void add(T element) {
        root = insertAt(root, element);
    }

    private TreeNode<T> balance(TreeNode<T> p) {
        if (p == null) {
            return null;
        } else if (height(p.left) - height(p.right) == 2) {
            if (height(p.left.left) >= height(p.left.right)) {
                p = rotateR(p);
            } else {
                p = rotateLR(p);
            }
        } else if (height(p.right) - height(p.left) == 2) {
            if (height(p.right.right) >= height(p.right.left)) {
                p = rotateL(p);
            } else {
                p = rotateRL(p);
            }
        }
        p.height = Math.max(height(p.left), height(p.right)) + 1;
        return p;
    }

    /**
     * Internal method to insert into a subtree.
     * @param p the item to insert.
     * @param element the node that roots the tree.
     * @return the new root.
     */
    private TreeNode<T> insertAt(TreeNode<T> p, T element) {
        if (p == null) {
            p = new TreeNode<>(element);
            p.height = 1;
            return p;
        } else {
            int c = element.compareTo(p.getValue());
            if (c == 0) {
                p.values.add(element);
            }
            else if (c < 0) {
                p.left = insertAt(p.left, element);
            } else {
                p.right = insertAt(p.right, element);
            }
        }
        p = balance(p);
        return p;
    }

    // find node to replace
    private TreeNode<T> rep;
    private TreeNode<T> findRepAt(TreeNode<T> node) {
        if (node.right != null) {
            node.right = findRepAt(node.right);
            node = balance(node);
        } else {
            rep = node;
            node = node.left;
        }
        return node;
    }

    private T removed;
    // remove node
    private TreeNode<T> removeAt(TreeNode<T> node, T x) {
        if (node == null) {
            return null;
        } else {
            if (x.compareTo(node.getValue()) == 0) {
                // found
                removed = node.getValue();
                if (node.values.size() > 1) {
                    node.values.remove(0);
                    return node;
                } else if (node.left == null) {
                    node = node.right;
                } else if (node.right == null) {
                    node = node.left;
                } else {
                    node.left = findRepAt(node.left);
                    rep.left = node.left;
                    rep.right = node.right;
                    node = rep;
                }
            } else if (x.compareTo(node.getValue()) <= 0) {
                // search left
                node.left = removeAt(node.left, x);
            } else {
                // search right
                node.right = removeAt(node.right, x);
            }
            return balance(node);
        }
    }

    /**
     * Remove from the tree. Nothing is done if x is not found.
     * @param x the item to remove.
     */
    public T remove(T x) {
        removed = null;
        root = removeAt(root, x);
        return removed;
    }

    public Traversal<T> traversal() {
        return new AVLTreeTraversal<>(root);
    }

    /**
     * Rotate binary tree node with left child.
     * For AVL trees, this is a single rotation for case 1.
     * Update heights, then return new root.
     */
    private static <T extends Comparable<T>> TreeNode<T> rotateR(TreeNode<T> k2) {
        TreeNode<T> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
        k1.height = Math.max(height(k1.left), k2.height) + 1;
        return k1;
    }

    /**
     * Rotate binary tree node with right child.
     * For AVL trees, this is a single rotation for case 4.
     * Update heights, then return new root.
     */
    private static <T extends Comparable<T>> TreeNode<T> rotateL(TreeNode<T> k1) {
        TreeNode<T> k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.height = Math.max(height(k1.left), height(k1.right)) + 1;
        k2.height = Math.max(height(k2.right), k1.height) + 1;
        return k2;
    }

    /**
     * Double rotate binary tree node: first left child
     * with its right child; then node k3 with new left child.
     * For AVL trees, this is a double rotation for case 2.
     * Update heights, then return new root.
     */
    private static <T extends Comparable<T>> TreeNode<T> rotateLR(TreeNode<T> k3) {
        k3.left = rotateL(k3.left);
        return rotateR(k3);
    }

    /**
     * Double rotate binary tree node: first right child
     * with its left child; then node k1 with new right child.
     * For AVL trees, this is a double rotation for case 3.
     * Update heights, then return new root.
     */
    private static <T extends Comparable<T>> TreeNode<T> rotateRL(TreeNode<T> k1) {
        k1.right = rotateR(k1.right);
        return rotateL(k1);
    }

}
