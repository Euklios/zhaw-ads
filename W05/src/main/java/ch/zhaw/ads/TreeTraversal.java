package ch.zhaw.ads;

public class TreeTraversal<T extends Comparable<T>> implements Traversal<T> {

    private TreeNode<T> root;

    public TreeTraversal(TreeNode<T> root) {
        this.root = root;
    }

    public void preorder(Visitor<T> vis) {
        preorderTraversal(root, vis);
    }

    private void preorderTraversal(TreeNode<T> node, Visitor<T> vis) {
        if (node == null) {
            return;
        }

        vis.visit(node.getValue());
        preorderTraversal(node.left, vis);
        preorderTraversal(node.right, vis);
    }

    public void inorder(Visitor<T> vis) {
        inorderTraversal(root, vis);
    }

    private void inorderTraversal(TreeNode<T> node, Visitor<T> vis) {
        if (node == null) {
            return;
        }

        inorderTraversal(node.left, vis);
        vis.visit(node.getValue());
        inorderTraversal(node.right, vis);
    }

    public void postorder(Visitor<T> vis) {
        postorderTraversal(root, vis);
    }

    private void postorderTraversal(TreeNode<T> node, Visitor<T> vis) {
        if (node == null) {
            return;
        }

        postorderTraversal(node.left, vis);
        postorderTraversal(node.right, vis);
        vis.visit(node.getValue());
    }

    @Override
    public void levelorder(Visitor<T> visitor) {
        levelorderTraversal(root, visitor);
    }

    private void levelorderTraversal(TreeNode<T> node, Visitor<T> vis) {
        if (node == null) {
            return;
        }

        vis.visit(node.getValue());
        levelorderTraversal(node.left, vis);
        levelorderTraversal(node.right, vis);
    }

    @Override
    public void interval(T min, T max, Visitor<T> visitor) {
        intervalTraversal(root, min, max, visitor);
    }

    private void intervalTraversal(TreeNode<T> node, T min, T max, Visitor<T> vis) {
        if (node == null) {
            return;
        }

        if (node.getValue().compareTo(min) < 0) {
            intervalTraversal(node.right, min, max, vis);
        }

        else if (node.getValue().compareTo(max) > 0) {
            intervalTraversal(node.left, min, max, vis);
        }

        else {
            vis.visit(node.getValue());
            intervalTraversal(node.left, min, max, vis);
            intervalTraversal(node.right, min, max, vis);
        }
    }

}