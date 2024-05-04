import java.util.LinkedList;
import java.util.Queue;

class TreeNode<T> {
    T data;
    TreeNode<T> left, right;

    public TreeNode(T data) {
        this.data = data;
        left = right = null;
    }
}

class BinaryTree<T> {
    TreeNode<T> root;

    public BinaryTree() {
        root = null;
    }

    public void insert(T data) {
        root = insertRecursive(root, data);
    }

    private TreeNode<T> insertRecursive(TreeNode<T> root, T data) {
        if (root == null) {
            root = new TreeNode<>(data);
            return root;
        }

        if (countNodes(root.left) <= countNodes(root.right)) {
            root.left = insertRecursive(root.left, data);
        } 
        else {
            root.right = insertRecursive(root.right, data);
        }

        return root;
    }

    private int countNodes(TreeNode<T> node) {
        if (node == null) {
            return 0;
        }
        return 1 + countNodes(node.left) + countNodes(node.right);
    }

    public void printInOrder() {
        printInOrderRecursive(root);
    }

    private void printInOrderRecursive(TreeNode<T> node) {
        if (node != null) {
            printInOrderRecursive(node.left);
            System.out.print(node.data + " ");
            printInOrderRecursive(node.right);
        }
    }
}