public class AvlTree<T extends Comparable<? super T>> {

    private AvlNode<T> root;

    public AvlTree() {}

    public int getHeight() {
        return getHeight(root);
    }

    private int getHeight(AvlNode<T> node) {
        if(node == null) {
            return 0;
        }
        return node.getHeight();
    }

    private AvlNode<T> rightRotate(AvlNode<T> node) {
        AvlNode<T> left = node.getLeft();
        AvlNode<T> temp = left.getRight();
        left.setRight(node);
        node.setLeft(temp);
        node.setHeight(Math.max(getHeight(node.getLeft()), getHeight(node.getRight()))+1);
        left.setHeight(Math.max(getHeight(left.getLeft()), getHeight(left.getRight()))+1);
        return left;
    }

    private AvlNode<T> leftRotate(AvlNode<T> node) {
        AvlNode<T> right = node.getRight();
        AvlNode<T> temp = right.getLeft();
        right.setLeft(node);
        node.setRight(temp);
        node.setHeight(Math.max(getHeight(node.getLeft()), getHeight(node.getRight()))+1);
        right.setHeight(Math.max(getHeight(right.getLeft()), getHeight(right.getRight()))+1);
        return right;
    }

    int getBalance(AvlNode<T> node) {
        if (node == null) {
            return 0;
        }
        return getHeight(node.getLeft()) - getHeight(node.getRight());
    }

    private boolean rotated;
    private boolean repeated;
    public void insert(T val) {
        rotated = false;
        repeated = false;
        root = insertRecursive(val, root);
        if(!rotated && !repeated) {
            System.out.println("SEM ROTACAO");
        }
    }

    private AvlNode<T> insertRecursive(T val, AvlNode<T> node) {
        if(node == null) {
            return new AvlNode<>(val);
        }
        int cmp = val.compareTo(node.getItem());
        if(cmp < 0) {
            node.setLeft(insertRecursive(val, node.getLeft()));
        }else if(cmp > 0) {
            node.setRight(insertRecursive(val, node.getRight()));
        }else {
            repeated = true;
            System.out.println("REPETIDO");
            return node;
        }
        node.setHeight(Math.max(getHeight(node.getLeft()), getHeight(node.getRight()))+1);
        int balance = getBalance(node);
        if(balance > 1 && val.compareTo(node.getLeft().getItem()) < 0) {
            System.out.println("ESQUERDA-ESQUERDA");
            rotated = true;
            return rightRotate(node);
        }else if(balance < -1 && val.compareTo(node.getRight().getItem()) > 0) {
            System.out.println("DIREITA-DIREITA");
            rotated = true;
            return leftRotate(node);
        }else if(balance > 1 && val.compareTo(node.getLeft().getItem()) > 0) {
            System.out.println("ESQUERDA-DIREITA");
            node.setLeft(leftRotate(node.getLeft()));
            rotated = true;
            return rightRotate(node);
        }else if(balance < -1 && val.compareTo(node.getRight().getItem()) < 0) {
            System.out.println("DIREITA-ESQUERDA");
            node.setRight(rightRotate(node.getRight()));
            rotated = true;
            return leftRotate(node);
        }
        return node;
    }

    public void preorder() {
        if(root == null) {
            System.out.println("VAZIA");
            return;
        }
        preorderRecursive(root);
        System.out.println();
    }

    private void preorderRecursive(AvlNode<T> node) {
        if (node != null) {
            System.out.print(node.getItem().toString() + " ");
            preorderRecursive(node.getLeft());
            preorderRecursive(node.getRight());
        }
    }
}
