public class Stack<T> {

    public Stack() {}

    private LinkedElement<T> head;

    public T peek() {
        if(head == null) {
            return null;
        }
        return head.getValue();
    }

    public void push(T val) {
        LinkedElement<T> linkedElement = new LinkedElement<>(val);
        linkedElement.setNext(head);
        head = linkedElement;
    }

    public T pop() {
        if(head == null) {
            return null;
        }
        T ret = head.getValue();
        head = head.getNext();
        return ret;
    }
}
