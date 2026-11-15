import java.util.NoSuchElementException;

public class BinarySearchTree<T extends Comparable<? super T>> {

    private Node<T> root;

    public BinarySearchTree() {}

    public void insert(T val) {
        root = insertRecursive(val, root);
    }

    private Node<T> insertRecursive(T val, Node<T> node) {
        if(node == null) {
            node = new Node<>(val);
            return node;
        }
        int cmp = val.compareTo(node.getItem());
        if(cmp < 0) {
            node.setLeft(insertRecursive(val, node.getLeft()));
        }else if(cmp > 0) {
            node.setRight(insertRecursive(val, node.getRight()));
        }
        return node;
    }

    public void remove(T val) {
        root = removeRecursive(val, root);
    }

    private Node<T> removeRecursive(T val, Node<T> node) {
        if(node == null) {
            throw new NoSuchElementException();
        }
        int cmp = val.compareTo(node.getItem());
        if(cmp < 0) {
            node.setLeft(removeRecursive(val, node.getLeft()));
        }else if(cmp > 0) {
            node.setRight(removeRecursive(val, node.getRight()));
        }else {
            if(node.getLeft() == null) {
                return node.getRight();
            }else if(node.getRight() == null) {
                return node.getLeft();
            }
            node.setItem(minValue(node.getRight()));
            node.setRight(removeRecursive(node.getItem(), node.getRight()));
        }
        return node;
    }

    public void searchAndPrint(T val) {
        searchAndPrintRecursive(val, root);
        System.out.println();
    }

    private void searchAndPrintRecursive(T val, Node<T> node) {
        if(node == null) {
            System.out.print("NAO");
            return;
        }
        System.out.print(node.getItem().toString()+" ");
        int cmp = val.compareTo(node.getItem());
        if(cmp < 0) {
            searchAndPrintRecursive(val, node.getLeft());
        }else if(cmp > 0) {
            searchAndPrintRecursive(val, node.getRight());
        }else {
            System.out.print("SIM");
        }
    }

    public int height() {
        return heightRecursive(root);
    }

    private int heightRecursive(Node<T> node) {
        if(node == null) {
            return -1;
        }
        return 1 + Math.max(heightRecursive(node.getLeft()), heightRecursive(node.getRight()));
    }

    public void inorder() {
        if(root == null) {
            System.out.println("VAZIA");
            return;
        }
        inorderRecursive(root);
        System.out.println();
    }

    private void inorderRecursive(Node<T> node) {
        if (node != null) {
            inorderRecursive(node.getLeft());
            System.out.print(node.getItem().toString() + " ");
            inorderRecursive(node.getRight());
        }
    }

    public void preorder() {
        if(root == null) {
            System.out.println("VAZIA");
            return;
        }
        preorderRecursive(root);
        System.out.println();
    }

    private void preorderRecursive(Node<T> node) {
        if (node != null) {
            System.out.print(node.getItem().toString() + " ");
            preorderRecursive(node.getLeft());
            preorderRecursive(node.getRight());
        }
    }

    private T minValue(Node<T> node) {
        while(node.getLeft() != null) {
            node = node.getLeft();
        }
        return node.getItem();
    }
}
