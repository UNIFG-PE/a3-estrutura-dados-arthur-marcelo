import java.util.Objects;

public class LinkedList<T> {

    private LinkedElement<T> start;
    private int length = 0;

    public LinkedList() {}

    private LinkedElement<T> getLinked(int position) {
        LinkedElement<T> cur = start;
        for(int i = 0; i < position; ++i) {
            cur = cur.getNext();
            if(cur == null) return null;
        }
        return cur;
    }

    public T get(int position) {
        if(position < 0 || position >= length) {
            throw new IndexOutOfBoundsException();
        }
        LinkedElement<T> linkedElement = getLinked(position);
        assert linkedElement != null;
        return linkedElement.getValue();
    }

    public void add(int position, T val) {
        LinkedElement<T> linkedElement = new LinkedElement<>(val);
        if(position == 0) {
            linkedElement.setNext(start);
            start = linkedElement;
            ++length;
            return;
        }
        LinkedElement<T> prev = getLinked(position-1);
        if(prev == null) {
            throw new IndexOutOfBoundsException();
        }
        linkedElement.setNext(prev.getNext());
        prev.setNext(linkedElement);
        ++length;
    }

    public boolean removeElement(T val) {
        LinkedElement<T> cur = start;
        LinkedElement<T> prev = null;
        while(cur != null) {
            if(Objects.equals(val, cur.getValue())) {
                if(prev == null) {
                    start = cur.getNext();
                }else {
                    prev.setNext(cur.getNext());
                }
                --length;
                return true;
            }
            prev = cur;
            cur = cur.getNext();
        }
        return false;
    }

    public T remove(int position) {
        if(start == null) {
            throw new IndexOutOfBoundsException();
        }
        if(position == 0) {
            T ret = start.getValue();
            start = start.getNext();
            --length;
            return ret;
        }
        LinkedElement<T> prev = getLinked(position-1);
        if(prev == null || prev.getNext() == null) {
            throw new IndexOutOfBoundsException();
        }
        T ret = prev.getNext().getValue();
        prev.setNext(prev.getNext().getNext());
        --length;
        return ret;
    }

    public boolean contains(T val) {
        LinkedElement<T> cur = start;
        for(int i = 0; i < length; ++i) {
            if(Objects.equals(cur.getValue(), val)) return true;
            cur = cur.getNext();
        }
        return false;
    }

    public LinkedElement<T> iterate() {
        return start;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public LinkedElement<T> getStart(int length) {
        return start;
    }

    public void setStart(LinkedElement<T> start) {
        this.start = start;
    }
}
