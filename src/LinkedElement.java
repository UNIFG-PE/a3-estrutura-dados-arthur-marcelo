public class LinkedElement<T> {

    private T value;
    private LinkedElement<T> next;

    public LinkedElement(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public LinkedElement<T> getNext() {
        return next;
    }

    public void setNext(LinkedElement<T> next) {
        this.next = next;
    }
}
