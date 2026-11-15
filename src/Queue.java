import java.util.function.Predicate;

public class Queue<T> {

    private LinkedElement<T> head;
    private LinkedElement<T> tail;

    public Queue() {}

    public T peek() {
        if (head == null) {
            return null;
        }
        return head.getValue();
    }

    public void add(T val) {
        LinkedElement<T> linkedElement = new LinkedElement<>(val);
        if(tail == null) {
            head = tail = linkedElement;
        }else {
            tail.setNext(linkedElement);
            tail = linkedElement;
        }
    }

    public T poll() {
        if(head == null) {
            return null;
        }
        T ret = head.getValue();
        head = head.getNext();
        if(head == null) {
            tail = null;
        }
        return ret;
    }
}
